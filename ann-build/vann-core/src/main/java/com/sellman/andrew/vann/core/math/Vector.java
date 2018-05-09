package com.sellman.andrew.vann.core.math;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.VectorChangeEvent;
import com.sellman.andrew.vann.core.event.VectorPollEvent;

public class Vector {
	private static final int COLUMN_COUNT = 1;
	private static final int COLUMN_INDEX = 0;
	private final Matrix matrix;
	private final Context context;
	private final EventManager eventManager;

	protected Vector(Matrix matrix, Context context, EventManager eventManager) {
		this.matrix = matrix;
		this.context = context;
		this.eventManager = eventManager;
	}

	public Vector(int rowCount, Context context, EventManager eventManager) {
		this(new Matrix(rowCount, COLUMN_COUNT), context, eventManager);
	}

	public Vector(double[] data, Context context, EventManager eventManager) {
		this(data.length, context, eventManager);
		populate(data);
	}

	protected Vector(Matrix matrix) {
		this(matrix, null, null);
	}

	public Vector(int rowCount) {
		this(new Matrix(rowCount, COLUMN_COUNT));
	}

	public Vector(double[] data) {
		this(data.length);
		populate(data);
	}

	public int getRowCount() {
		return matrix.getRowCount();
	}

	public double getValue(int rowIndex) {
		double currentValue = matrix.getValue(rowIndex, COLUMN_INDEX);
		firePollEvent(rowIndex, currentValue);
		return currentValue;
	}

	public void setValue(int rowIndex, double value) {
		matrix.setValue(rowIndex, COLUMN_INDEX, value);
		fireChangeEvent(rowIndex, value);
	}

	protected Matrix getMatrix() {
		return matrix;
	}

	@Override
	public String toString() {
		return matrix.toString();
	}

	private void populate(double[] data) {
		for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
			matrix.setValue(rowIndex, COLUMN_INDEX, data[rowIndex]);
		}
	}

	private void fireChangeEvent(int rowIndex, double newValue) {
		if (!isAnyListener(VectorChangeEvent.class)) {
			return;
		}

		VectorChangeEvent event = new VectorChangeEvent(context, rowIndex, newValue);
		fire(event);
	}

	private void firePollEvent(int rowIndex, double currentValue) {
		if (!isAnyListener(VectorPollEvent.class)) {
			return;
		}

		VectorPollEvent event = new VectorPollEvent(context, rowIndex, currentValue);
		fire(event);
	}

	private boolean isAnyListener(Class<? extends Event> eventType) {
		return eventManager != null && eventManager.isAnyRegisteredListenerFor(eventType);
	}

	private void fire(Event event) {
		eventManager.fire(event);
	}

}
