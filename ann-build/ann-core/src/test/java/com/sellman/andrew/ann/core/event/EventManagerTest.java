package com.sellman.andrew.ann.core.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.concurrent.TaskServiceBuilder;

public class EventManagerTest {
	private TaskService eventDispatcher;
	private EventManager manager;
	private Listener<EpochChangeEvent> listener1;
	private Listener<EpochErrorChangeEvent> listener2;
	private Event event1;
	private Event event2;
	private Context context;

	@Before
	public void prepareTest() {
		context = new Context("test");
		eventDispatcher = new TaskServiceBuilder().fireAndForget().setThreadCount(1).lowPriority().build();
		manager = new EventManager(eventDispatcher);
		event1 = new EpochChangeEvent(context, 0, 1);
		event2 = new EpochErrorChangeEvent(context, 0.0, 1.0);
		listener1 = new ConsoleListener<EpochChangeEvent>();
		listener2 = new ConsoleListener<EpochErrorChangeEvent>();
	}

	@Test
	public void fire() {
		assertFalse(manager.fire(event1));
		manager.register(listener1, EpochChangeEvent.class);
		assertTrue(manager.fire(event1));

		assertFalse(manager.fire(event2));
		manager.register(listener2, EpochErrorChangeEvent.class);
		assertTrue(manager.fire(event2));

		manager.unregister(listener1, EpochChangeEvent.class);
		assertFalse(manager.fire(event1));

		manager.unregister(listener2, EpochErrorChangeEvent.class);
		assertFalse(manager.fire(event2));
	}

	@Test
	public void registerAndUnregister() {
		assertFalse(manager.isAnyRegisteredListenerFor(event1.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(event2.getClass()));

		assertTrue(manager.register(listener1, EpochChangeEvent.class));
		assertFalse(manager.register(listener1, EpochChangeEvent.class));

		assertTrue(manager.register(listener2, EpochErrorChangeEvent.class));
		assertFalse(manager.register(listener2, EpochErrorChangeEvent.class));

		assertTrue(manager.isAnyRegisteredListenerFor(event1.getClass()));
		assertTrue(manager.unregister(listener1, EpochChangeEvent.class));
		assertFalse(manager.unregister(listener1, EpochChangeEvent.class));
		assertFalse(manager.isAnyRegisteredListenerFor(event1.getClass()));

		assertTrue(manager.isAnyRegisteredListenerFor(event2.getClass()));
		assertTrue(manager.unregister(listener2, EpochErrorChangeEvent.class));
		assertFalse(manager.unregister(listener2, EpochErrorChangeEvent.class));
		assertFalse(manager.isAnyRegisteredListenerFor(event2.getClass()));
	}

}
