package com.sellman.andrew.ann.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class EventManager {
	private final TaskService eventDispatcher;
	private final Map<Class<? extends Event>, List<Listener<? extends Event>>> eventListeners;
	private Object lock;

	public EventManager(TaskService eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		eventListeners = new ConcurrentHashMap<Class<? extends Event>, List<Listener<? extends Event>>>();
		lock = new Object();
	}

	public <T extends Event> boolean fire(T event) {
		List<Listener<? extends Event>> listeners = getListenersFor(event.getClass());
		if (listeners.isEmpty()) {
			return false;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		EventHandlerTask<T> eventTask = new EventHandlerTask(listeners, event);
		eventDispatcher.runTask(eventTask);
		return true;
	}

	public <T extends Event> boolean register(Listener<T> listener, Class<T> eventType) {
		List<Listener<? extends Event>> listeners = getListenersFor(eventType);

		if (listeners.contains(listener)) {
			return false;
		}

		listeners.add(listener);

		return true;
	}

	public <T extends Event> boolean unregister(Listener<T> listener, Class<T> eventType) {
		List<Listener<? extends Event>> listeners = getListenersFor(eventType);
		synchronized (listeners) {
			return listeners.removeIf(x -> x.getClass().equals(listener.getClass()));
		}
	}

	public <T extends Event> boolean isAnyRegisteredListenerFor(Class<T> eventType) {
		return !getListenersFor(eventType).isEmpty();
	}

	private <T extends Event> List<Listener<? extends Event>> getListenersFor(Class<T> eventType) {
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

}
