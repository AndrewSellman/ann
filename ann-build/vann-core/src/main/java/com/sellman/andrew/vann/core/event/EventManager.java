package com.sellman.andrew.vann.core.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PreDestroy;

import com.sellman.andrew.vann.core.concurrent.TaskService;

public class EventManager implements AutoCloseable {
	private final Map<Class<? extends Event>, List<EventListenerAdapter>> immediatelyNotifiedEventListenerAdapters;
	private final Map<Class<? extends Event>, List<EventListenerAdapter>> dispatchedEventListenerAdapters;
	private final Queue<Event> pendingEvents;
	private final Semaphore arbitrator;
	private final AtomicBoolean keepRunning;
	private final EventDispatcher eventDispatcher;
	private final Object lock;
	private final EventListenerAdapterFactory eventListenerAdapterFactory;

	public EventManager(TaskService taskService, EventListenerAdapterFactory eventListenerAdaptorFactory) {
		this.eventListenerAdapterFactory = eventListenerAdaptorFactory;
		immediatelyNotifiedEventListenerAdapters = new ConcurrentHashMap<>();
		dispatchedEventListenerAdapters = new ConcurrentHashMap<>();
		pendingEvents = new ConcurrentLinkedQueue<>();
		keepRunning = new AtomicBoolean(true);
		arbitrator = new Semaphore(0);
		lock = new Object();
		eventDispatcher = new EventDispatcher(dispatchedEventListenerAdapters, pendingEvents, keepRunning, arbitrator);
		taskService.runTask(eventDispatcher);
	}

	public boolean fire(final Event event) {
		return fireNow(event) | fireLater(event);
	}

	public boolean registerForImmediateNotification(final Listener listener, final Class<? extends Event> eventClass) {
		return register(listener, eventClass, getRegisteredImmediatelyNotifiedEventListenerAdapatersFor(eventClass));
	}

	public boolean registerForDispatchedNotification(final Listener listener, final Class<? extends Event> eventClass) {
		return register(listener, eventClass, getRegisteredDispatchedEventListenerAdapatersFor(eventClass));
	}

	public boolean unregister(final Listener listener, final Class<? extends Event> eventClass) {
		return unregisterImmediatelyNotifiedListener(listener, eventClass) | unregisterDispatchedListener(listener, eventClass);
	}

	public boolean isAnyRegisteredListenerFor(final Class<? extends Event> eventClass) {
		return isAnyRegisteredImmediatelyNotifiedListenerAdaptersFor(eventClass) || isAnyRegisteredDispatchedListenerAdaptersFor(eventClass);
	}

	@PreDestroy
	@Override
	public void close() {
		System.out.println("Destroying EventManager " + toString() + "...");
		keepRunning.set(false);
		arbitrator.release();
		System.out.println("Destroyed EventManager " + toString());
	}

	private boolean isAnyRegisteredImmediatelyNotifiedListenerAdaptersFor(final Class<? extends Event> eventClass) {
		return !getRegisteredImmediatelyNotifiedEventListenerAdapatersFor(eventClass).isEmpty();
	}

	private boolean isAnyRegisteredDispatchedListenerAdaptersFor(final Class<? extends Event> eventClass) {
		return !getRegisteredDispatchedEventListenerAdapatersFor(eventClass).isEmpty();
	}

	private List<EventListenerAdapter> getRegisteredImmediatelyNotifiedEventListenerAdapatersFor(Class<? extends Event> eventClass) {
		return getRegisteredEventListenerAdapaters(eventClass, immediatelyNotifiedEventListenerAdapters);
	}

	private List<EventListenerAdapter> getRegisteredDispatchedEventListenerAdapatersFor(Class<? extends Event> eventClass) {
		return getRegisteredEventListenerAdapaters(eventClass, dispatchedEventListenerAdapters);
	}

	private List<EventListenerAdapter> getRegisteredEventListenerAdapaters(Class<? extends Event> eventClass,
			Map<Class<? extends Event>, List<EventListenerAdapter>> registeredEventListenerAdapters) {
		List<EventListenerAdapter> eventListenerAdapaters = registeredEventListenerAdapters.get(eventClass);
		if (eventListenerAdapaters == null) {
			synchronized (lock) {
				eventListenerAdapaters = registeredEventListenerAdapters.get(eventClass);
				if (eventListenerAdapaters == null) {
					eventListenerAdapaters = new ArrayList<>();
					registeredEventListenerAdapters.put(eventClass, eventListenerAdapaters);
				}
			}
		}

		return eventListenerAdapaters;
	}

	private boolean fireNow(Event event) {
		List<EventListenerAdapter> eventListenerAdapaters = getRegisteredImmediatelyNotifiedEventListenerAdapatersFor(event.getClass());
		if (eventListenerAdapaters.isEmpty()) {
			return false;
		}

		synchronized (eventListenerAdapaters) {
			eventListenerAdapaters.forEach(adapter -> adapter.onEvent(event));
		}

		return true;
	}

	private boolean fireLater(Event event) {
		if (!isAnyRegisteredDispatchedListenerAdaptersFor(event.getClass())) {
			return false;
		}

		pendingEvents.add(event);
		arbitrator.release();
		return true;
	}

	public boolean register(final Listener listener, final Class<? extends Event> eventClass, List<EventListenerAdapter> alreadyRegisteredEventListenerAdapaters) {
		synchronized (alreadyRegisteredEventListenerAdapaters) {
			for (EventListenerAdapter alreadyRegisteredEventListenerAdapter : alreadyRegisteredEventListenerAdapaters) {
				if (alreadyRegisteredEventListenerAdapter.getListenerClass().equals(listener.getClass())) {
					return false;
				}
			}
		}

		EventListenerAdapter eventListenerAdapter = eventListenerAdapterFactory.createFor(eventClass, listener);
		return alreadyRegisteredEventListenerAdapaters.add(eventListenerAdapter);
	}

	private boolean unregisterImmediatelyNotifiedListener(final Listener listener, final Class<? extends Event> eventClass) {
		return unregister(listener, eventClass, getRegisteredImmediatelyNotifiedEventListenerAdapatersFor(eventClass));
	}

	private boolean unregisterDispatchedListener(final Listener listener, final Class<? extends Event> eventClass) {
		return unregister(listener, eventClass, getRegisteredDispatchedEventListenerAdapatersFor(eventClass));
	}

	private boolean unregister(final Listener listener, final Class<? extends Event> eventClass, List<EventListenerAdapter> currentlyRegisteredEventListenerAdapaters) {
		synchronized (currentlyRegisteredEventListenerAdapaters) {
			return currentlyRegisteredEventListenerAdapaters.removeIf(x -> x.getListenerClass().equals(listener.getClass()));
		}
	}

}
