package com.sellman.andrew.vann.core.math;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.RowVectorChangeEvent;
import com.sellman.andrew.vann.core.event.RowVectorPollEvent;

public class RowVector extends Vector implements InspectableRowVector {
	private static final int ROW_COUNT = 1;
	private static final int ROW_INDEX = 0;

	protected RowVector(Matrix matrix, Context context, EventManager eventManager) {
		super(matrix, context, eventManager);
	}

	public RowVector(int columnCount, Context context, EventManager eventManager) {
		this(new Matrix(ROW_COUNT, columnCount), context, eventManager);
	}

	public RowVector(double[] data, Context context, EventManager eventManager) {
		this(data.length, context, eventManager);
		populate(data);
	}

	protected RowVector(Matrix matrix) {
		this(matrix, null, null);
	}

	public RowVector(int columnCount) {
		this(new Matrix(ROW_COUNT, columnCount));
	}

	public RowVector(double[] data) {
		this(data.length);
		populate(data);
	}

	public int getColumnCount() {
		return getMatrix().getColumnCount();
	}

	public double getValue(int columnIndex) {
		double currentValue = getMatrix().getValue(ROW_INDEX, columnIndex);
		firePollEvent(columnIndex, currentValue);
		return currentValue;
	}

	protected double[] getValues() {
		return getMatrix().getRowValues(ROW_INDEX);
	}

	public void setValue(int columnIndex, double value) {
		getMatrix().setValue(ROW_INDEX, columnIndex, value);
		fireChangeEvent(columnIndex, value);
	}

	private void populate(double[] data) {
		getMatrix().setRowValues(ROW_INDEX, data);
	}

	private void fireChangeEvent(int columnIndex, double newValue) {
		if (!isAnyListener(RowVectorChangeEvent.class)) {
			return;
		}

		RowVectorChangeEvent event = new RowVectorChangeEvent(getContext(), columnIndex, newValue);
		fire(event);
	}

	private void firePollEvent(int columnIndex, double currentValue) {
		if (!isAnyListener(RowVectorPollEvent.class)) {
			return;
		}

		RowVectorPollEvent event = new RowVectorPollEvent(getContext(), columnIndex, currentValue);
		fire(event);
	}

}
