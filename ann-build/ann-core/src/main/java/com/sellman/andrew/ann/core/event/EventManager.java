package com.sellman.andrew.ann.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class EventManager implements AutoCloseable {
	private final Map<Class<? extends Event>, List<Listener<? extends Event>>> eventListeners;
	private final Queue<Event> pendingEvents;
	private final Semaphore arbitrator;
	private final Object lock;
	private final EventDispatcher dispatcher;
	private final AtomicBoolean keepDispatchingEvents;
	
	public EventManager(TaskService taskService) {
		eventListeners = new ConcurrentHashMap<Class<? extends Event>, List<Listener<? extends Event>>>();
		pendingEvents = new ConcurrentLinkedQueue<Event>();
		keepDispatchingEvents = new AtomicBoolean(true);
		arbitrator = new Semaphore(0);
		lock = new Object();
		dispatcher = new EventDispatcher(eventListeners, pendingEvents, keepDispatchingEvents, arbitrator);
		taskService.runTask(dispatcher);
	}

	public <T extends Event> boolean fire(final T event) {
		List<Listener<? extends Event>> listeners = getListenersFor(event.getClass());
		if (listeners.isEmpty()) {
			return false;
		}

		pendingEvents.add(event);
		if (arbitrator.availablePermits() == 0) {
			arbitrator.release();	
		}
		
		return true;
	}

	public <T extends Event> boolean register(final Listener<T> listener, final Class<T> eventType) {
		List<Listener<? extends Event>> listeners = getListenersFor(eventType);

		if (listeners.contains(listener)) {
			return false;
		}

		listeners.add(listener);

		return true;
	}

	public <T extends Event> boolean unregister(final Listener<T> listener, final Class<T> eventType) {
		List<Listener<? extends Event>> listeners = getListenersFor(eventType);
		synchronized (listeners) {
			return listeners.removeIf(x -> x.getClass().equals(listener.getClass()));
		}
	}

	public <T extends Event> boolean isAnyRegisteredListenerFor(final Class<T> eventType) {
		return !getListenersFor(eventType).isEmpty();
	}

	private <T extends Event> List<Listener<? extends Event>> getListenersFor(final Class<T> eventType) {
		List<Listener<? extends Event>> listeners = eventListeners.get(eventType);
		if (listeners == null) {
			synchronized (lock) {
				listeners = eventListeners.get(eventType);
				if (listeners == null) {
					listeners = Collections.synchronizedList(new ArrayList<Listener<? extends Event>>());
					eventListeners.put(eventType, listeners);
				}
			}
		}

		return listeners;
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("EventManager " + toString() + "was created.");
	}
	
	@PreDestroy
	@Override
	public void close() {
		System.out.println("Destroying EventManager " + toString() + "...");
		keepDispatchingEvents.set(false);
		arbitrator.release();
		System.out.println("Destroyed EventManager " + toString());
	}

}
