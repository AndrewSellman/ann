package com.sellman.andrew.vann.core.math;

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

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.Representation;
import com.sellman.andrew.vann.core.event.VectorChangeEvent;
import com.sellman.andrew.vann.core.event.VectorPollEvent;
import com.sellman.andrew.vann.core.math.Vector;

@RunWith(MockitoJUnitRunner.class)
public class VectorTest {
	private static final int LAYER_INDEX = 1;
	private static final String NETWORK_NAME = "networkName";
	private static final Representation REPRESENTS = Representation.BIAS;
	private static final int ROW_COUNT = 5;
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
		Vector v = new Vector(1);
		v.setValue(0, newValue);
		String asString = v.toString();
		assertTrue(asString.contains("[1x1"));
		assertTrue(asString.contains(Double.toString(newValue)));
	}

	@Test
	public void setValueShouldFireMatrixChangeEvent() {
		ArgumentCaptor<VectorChangeEvent> eventCaptor = ArgumentCaptor.forClass(VectorChangeEvent.class);
		doReturn(true).when(eventManager).isAnyRegisteredListenerFor(VectorChangeEvent.class);

		Vector v = new Vector(ROW_COUNT, context, eventManager);
		v.setValue(1, newValue);

		verify(eventManager).fire(eventCaptor.capture());
		VectorChangeEvent event = eventCaptor.getValue();
		assertEquals(1, event.getRowIndex());
		assertEquals(newValue, event.getValue(), 0.0);
		assertEquals(NETWORK_NAME, event.getNetworkName());
		assertEquals(LAYER_INDEX, event.getNetworkLayerIndex());
		assertEquals(REPRESENTS, event.getRepresentation());
	}

	@Test
	public void getValueShouldFireVectorPollEvent() {
		ArgumentCaptor<VectorPollEvent> eventCaptor = ArgumentCaptor.forClass(VectorPollEvent.class);
		doReturn(true).when(eventManager).isAnyRegisteredListenerFor(VectorPollEvent.class);

		Vector v = new Vector(ROW_COUNT, context, eventManager);
		v.setValue(1, newValue);
		double fetchedValue = v.getValue(1);

		assertEquals(newValue, fetchedValue, 0.0);
		verify(eventManager).fire(eventCaptor.capture());
		VectorPollEvent event = eventCaptor.getValue();
		assertEquals(1, event.getRowIndex());
		assertEquals(newValue, event.getValue(), 0.0);
		assertEquals(NETWORK_NAME, event.getNetworkName());
		assertEquals(LAYER_INDEX, event.getNetworkLayerIndex());
		assertEquals(REPRESENTS, event.getRepresentation());
	}

	@Test
	public void constructorWithData() {
		double[] data = new double[ROW_COUNT];
		Vector v = new Vector(data);
		assertVector(v, ROW_COUNT);
	}

	@Test
	public void constructorWithDimension() {
		Vector v = new Vector(ROW_COUNT);
		assertVector(v, ROW_COUNT);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void invalidRowReference() {
		Vector v = new Vector(ROW_COUNT);
		v.getValue(ROW_COUNT);
	}

	private void assertVector(Vector v, int expectedRowCount) {
		assertEquals(expectedRowCount, v.getRowCount());

		assertVector(v, 0.0);
		updateVector(v);
		assertVector(v, newValue);
	}

	private void assertVector(Vector v, double exepctedValue) {
		for (int rowIndex = 0; rowIndex < v.getRowCount(); rowIndex++) {
			assertEquals(exepctedValue, v.getValue(rowIndex), 0.0);
		}
	}

	private void updateVector(Vector v) {
		for (int rowIndex = 0; rowIndex < v.getRowCount(); rowIndex++) {
			v.setValue(rowIndex, newValue);
		}
	}

}
