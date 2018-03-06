package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.event.Context;
import com.sellman.andrew.ann.core.event.Event;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.event.MatrixChangeEvent;
import com.sellman.andrew.ann.core.event.MatrixPollEvent;

public class Matrix {
	private final double[][] data;
	private final long cellCount;
	private final Context context;
	private final EventManager eventManager;

	public Matrix(double[][] data, Context context, EventManager eventManager) {
		this.data = data;
		cellCount = getRowCount() * getColumnCount();
		this.context = context;
		this.eventManager = eventManager;
	}

	public Matrix(double[][] data) {
		this(data, null, null);
	}

	public Matrix(int rowCount, int columnCount, Context context, EventManager eventManager) {
		this(new double[rowCount][columnCount], context, eventManager);
	}
	
	public Matrix(int rowCount, int columnCount) {
		this(new double[rowCount][columnCount]);
	}

	public int getRowCount() {
		return data.length;
	}

	public int getColumnCount() {
		return data[0].length;
	}

	public long getCellCount() {
		return cellCount;
	}

	public double getValue(int rowIndex, int columnIndex) {
		double currentValue = data[rowIndex][columnIndex];
		firePollEvent(rowIndex, columnIndex, currentValue);
		return currentValue;
	}

	public void setValue(int rowIndex, int columnIndex, double value) {
		double originalValue = data[rowIndex][columnIndex];
		data[rowIndex][columnIndex] = value;
		fireChangeEvent(rowIndex, columnIndex, originalValue, value);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append(getRowCount());
		sb.append("x");
		sb.append(getColumnCount());
		sb.append("]\n");
		for (int r = 0; r < getRowCount(); r++) {
			for (int c = 0; c < getColumnCount(); c++) {
				sb.append(getValue(r, c));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void fireChangeEvent(int rowIndex, int columnIndex, double originalValue, double newValue) {
		if (!isAnyListener(MatrixChangeEvent.class)) {
			return;
		}

		MatrixChangeEvent event = new MatrixChangeEvent(context, rowIndex, columnIndex, originalValue, newValue);
		fire(event);
	}

	private void firePollEvent(int rowIndex, int columnIndex, double currentValue) {
		if (!isAnyListener(MatrixPollEvent.class)) {
			return;
		}

		MatrixPollEvent event = new MatrixPollEvent(context, rowIndex, columnIndex, currentValue);
		fire(event);
	}

	private boolean isAnyListener(Class<? extends Event> eventType) {
		return eventManager != null && eventManager.isAnyRegisteredListenerFor(eventType);
	}

	private void fire(Event event) {
		eventManager.fire(event);
	}

}
