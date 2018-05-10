package com.sellman.andrew.vann.core.math;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;

abstract class Vector {
	private final Matrix matrix;
	private final Context context;
	private final EventManager eventManager;

	protected Vector(Matrix matrix, Context context, EventManager eventManager) {
		this.matrix = matrix;
		this.context = context;
		this.eventManager = eventManager;
	}

	protected final Matrix getMatrix() {
		return matrix;
	}

	protected final Context getContext() {
		return context;
	}

	@Override
	public String toString() {
		return matrix.toString();
	}
	
	protected final boolean isAnyListener(Class<? extends Event> eventType) {
		return eventManager != null && eventManager.isAnyRegisteredListenerFor(eventType);
	}

	protected final void fire(Event event) {
		eventManager.fire(event);
	}

}
