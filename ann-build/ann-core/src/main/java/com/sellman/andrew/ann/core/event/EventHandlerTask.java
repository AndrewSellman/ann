package com.sellman.andrew.ann.core.event;

import java.util.List;

import com.sellman.andrew.ann.core.concurrent.AbstractTask;

class EventHandlerTask<T extends Event> extends AbstractTask {
	private final List<Listener<T>> listeners;
	private final T event;

	public EventHandlerTask(final List<Listener<T>> listeners, final T event) {
		this.listeners = listeners;
		this.event = event;
	}

	@Override
	public void execute() {
		synchronized (listeners) {
			for (Listener<T> listener : listeners) {
				listener.onEvent(event);
			}
		}
	}

}
