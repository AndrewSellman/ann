package com.sellman.andrew.vann.core.event;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sellman.andrew.vann.core.concurrent.AbstractTask;

class EventDispatcher extends AbstractTask {
	private final Map<Class<? extends Event>, List<Listener<? extends Event>>> eventListeners;
	private final Queue<? extends Event> pendingEvents;
	private final AtomicBoolean keepRunning;
	private final Semaphore arbitrator;

	public EventDispatcher(Map<Class<? extends Event>, List<Listener<? extends Event>>> eventListeners, Queue<? extends Event> pendingEvents, AtomicBoolean keepRunning, Semaphore arbitrator) {
		this.eventListeners = eventListeners;
		this.pendingEvents = pendingEvents;
		this.keepRunning = keepRunning;
		this.arbitrator = arbitrator;
	}

	@Override
	public void execute() {
		while (keepRunning.get()) {
			try {
				arbitrator.acquire();
			} catch (InterruptedException e) {
				continue;
			}
			dispatch();
		}
	}

	private void dispatch() {
		if (pendingEvents.isEmpty()) {
			return;
		}

		Event event = pendingEvents.poll();
		dispatch(event);
	}

	@SuppressWarnings("unchecked")
	private <T extends Event> void dispatch(T event) {
		List<Listener<? extends Event>> listeners = getListenersFor(event.getClass());
		if (listeners != null) {
			synchronized (listeners) {
				for (Listener<? extends Event> listener : listeners) {
					dispatch(listener.getClass().cast(listener), event);
				}
			}
		}
	}

	private <T extends Event> void dispatch(Listener<T> listener, T event) {
		listener.onEvent(event);
	}

	private <T extends Event> List<Listener<? extends Event>> getListenersFor(Class<T> eventType) {
		return eventListeners.get(eventType);
	}

}
