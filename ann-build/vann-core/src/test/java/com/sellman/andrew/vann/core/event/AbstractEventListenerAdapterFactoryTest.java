package com.sellman.andrew.vann.core.event;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEventListenerAdapterFactoryTest {

	@Mock
	private BatchIndexChangeListener compatibleListener;

	@Mock
	private MatrixChangeListener incompatibleListener;

	private AbstractEventListenerAdapterFactory<? extends Event, ? extends Listener, ? extends EventListenerAdapter> factory;

	@Before
	public void prepareTest() {
		factory = new BatchIndexChangeListenerAdapterFactory();
	}

	@Test
	public void getSupportedEventClass() {
		assertEquals(BatchIndexChangeEvent.class, factory.getSupportedEventClass());
	}

	@Test
	public void createForUsingCompatibleListener() {
		EventListenerAdapter adapter = factory.createFor(compatibleListener);
		assertEquals(compatibleListener.getClass(), adapter.getListenerClass());
	}

	@Test(expected = IllegalStateException.class)
	public void createForUsingIncompatibleListener() {
		factory.createFor(incompatibleListener);
	}

}
