package com.sellman.andrew.vann.core.math;

import com.sellman.andrew.vann.core.event.ColumnVectorChangeEvent;
import com.sellman.andrew.vann.core.event.ColumnVectorPollEvent;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;

public class ColumnVector extends Vector {
	private static final int COLUMN_COUNT = 1;
	private static final int COLUMN_INDEX = 0;

	protected ColumnVector(Matrix matrix, Context context, EventManager eventManager) {
		super(matrix, context, eventManager);
	}

	public ColumnVector(int rowCount, Context context, EventManager eventManager) {
		this(new Matrix(rowCount, COLUMN_COUNT), context, eventManager);
	}

	public ColumnVector(double[] data, Context context, EventManager eventManager) {
		this(data.length, context, eventManager);
		populate(data);
	}

	protected ColumnVector(Matrix matrix) {
		this(matrix, null, null);
	}

	public ColumnVector(int rowCount) {
		this(new Matrix(rowCount, COLUMN_COUNT));
	}

	public ColumnVector(double[] data) {
		this(data.length);
		populate(data);
	}

	public int getRowCount() {
		return getMatrix().getRowCount();
	}

	public double getValue(int rowIndex) {
		double currentValue = getMatrix().getValue(rowIndex, COLUMN_INDEX);
		firePollEvent(rowIndex, currentValue);
		return currentValue;
	}

	public void setValue(int rowIndex, double value) {
		getMatrix().setValue(rowIndex, COLUMN_INDEX, value);
		fireChangeEvent(rowIndex, value);
	}

	private void populate(double[] data) {
		for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
			getMatrix().setValue(rowIndex, COLUMN_INDEX, data[rowIndex]);
		}
	}

	private void fireChangeEvent(int rowIndex, double newValue) {
		if (!isAnyListener(ColumnVectorChangeEvent.class)) {
			return;
		}

		ColumnVectorChangeEvent event = new ColumnVectorChangeEvent(getContext(), rowIndex, newValue);
		fire(event);
	}

	private void firePollEvent(int rowIndex, double currentValue) {
		if (!isAnyListener(ColumnVectorPollEvent.class)) {
			return;
		}

		ColumnVectorPollEvent event = new ColumnVectorPollEvent(getContext(), rowIndex, currentValue);
		fire(event);
	}

}
