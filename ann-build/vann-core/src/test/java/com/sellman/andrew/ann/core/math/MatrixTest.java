package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.ann.core.event.Context;
import com.sellman.andrew.ann.core.event.EventManager;
import com.sellman.andrew.ann.core.event.MatrixChangeEvent;
import com.sellman.andrew.ann.core.event.MatrixPollEvent;
import com.sellman.andrew.ann.core.event.Representation;

@RunWith(MockitoJUnitRunner.class)
public class MatrixTest {
	private static final int ROW_COUNT = 5;
	private static final int COLUMN_COUNT = 10;
	private static final int LAYER_INDEX = 1;
	private static final String NETWORK_NAME = "networkName";
	private static final Representation REPRESENTS = Representation.WEIGHTS;
	private double newValue;
	private Context context;

	@Mock
	private EventManager eventManager;

	@Before
	public void prepareTest() {
		newValue = ThreadLocalRandom.current().nextDouble() + 1.0;
		context = new Context(NETWORK_NAME, LAYER_INDEX, REPRESENTS);
	}

	@Test
	public void asString() {
		Matrix m = new Matrix(1, 1);
		m.setValue(0, 0, newValue);
		String asString = m.toString();
		assertTrue(asString.contains("[1x1"));
		assertTrue(asString.contains(Double.toString(newValue)));
	}

	@Test
	public void setValueShouldFireMatrixChangeEvent() {
		ArgumentCaptor<MatrixChangeEvent> eventCaptor = ArgumentCaptor.forClass(MatrixChangeEvent.class);
		doReturn(true).when(eventManager).isAnyRegisteredListenerFor(MatrixChangeEvent.class);

		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT, context, eventManager);
		m.setValue(1, 2, newValue);

		verify(eventManager).fire(eventCaptor.capture());
		MatrixChangeEvent event = eventCaptor.getValue();
		assertEquals(1, event.getRowIndex());
		assertEquals(2, event.getColumnIndex());
		assertEquals(0.0, event.getOriginalValue(), 0.0);
		assertEquals(newValue, event.getNewValue(), 0.0);
		assertEquals(NETWORK_NAME, event.getNetworkName());
		assertEquals(LAYER_INDEX, event.getNetworkLayerIndex());
		assertEquals(REPRESENTS, event.getRepresentation());
	}

	@Test
	public void getValueShouldFireMatrixPollEvent() {
		ArgumentCaptor<MatrixPollEvent> eventCaptor = ArgumentCaptor.forClass(MatrixPollEvent.class);
		doReturn(true).when(eventManager).isAnyRegisteredListenerFor(MatrixPollEvent.class);

		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT, context, eventManager);
		m.setValue(1, 2, newValue);
		double fetchedValue = m.getValue(1, 2);

		assertEquals(newValue, fetchedValue, 0.0);
		verify(eventManager).fire(eventCaptor.capture());
		MatrixPollEvent event = eventCaptor.getValue();
		assertEquals(1, event.getRowIndex());
		assertEquals(2, event.getColumnIndex());
		assertEquals(newValue, event.getCurrentValue(), 0.0);
		assertEquals(NETWORK_NAME, event.getNetworkName());
		assertEquals(LAYER_INDEX, event.getNetworkLayerIndex());
		assertEquals(REPRESENTS, event.getRepresentation());
	}

	@Test
	public void constructorWithData() {
		double[][] data = new double[ROW_COUNT][COLUMN_COUNT];

		Matrix m = new Matrix(data);
		assertMatrix(m, ROW_COUNT, COLUMN_COUNT);
	}

	@Test
	public void constructorWithDimensions() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);
		assertMatrix(m, ROW_COUNT, COLUMN_COUNT);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidRowReference() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);
		m.getValue(ROW_COUNT, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidColumnReference() {
		Matrix m = new Matrix(ROW_COUNT, COLUMN_COUNT);
		m.getValue(0, COLUMN_COUNT);
	}

	private void assertMatrix(Matrix m, int expectedRowCount, int expectedColumnCount) {
		assertEquals(expectedRowCount, m.getRowCount());
		assertEquals(expectedColumnCount, m.getColumnCount());
		assertEquals(expectedRowCount * expectedColumnCount, m.getCellCount());

		assertMatrix(m, 0.0);
		updateMatrix(m);
		assertMatrix(m, newValue);
	}

	private void assertMatrix(Matrix m, double exepctedValue) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				assertEquals(exepctedValue, m.getValue(rowIndex, columnIndex), 0.0);
			}
		}
	}

	private void updateMatrix(Matrix m) {
		for (int rowIndex = 0; rowIndex < m.getRowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < m.getColumnCount(); columnIndex++) {
				m.setValue(rowIndex, columnIndex, newValue);
			}
		}
	}

}
