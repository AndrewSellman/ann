package com.sellman.andrew.vann.core.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;

public class EventManagerTest {
	private TaskService taskService;
	private EventManager manager;
	private ConsoleListener listener;
	private Event epochChangeEvent;
	private Event otherEvent;
	private Context context;
	
	@Before
	public void prepareTest() {
		listener = new ConsoleListener();
		listener = new ConsoleListener();

		context = new Context("test");
		taskService = new TaskServiceBuilder().fireAndForget().setThreadCount(1).lowPriority().build();
		
		EventListenerAdapterFactory eventListenerAdapterFactory = new EventListenerAdapterFactory();
		eventListenerAdapterFactory.register(new EpochIndexChangeListenerAdapterFactory());
		manager = new EventManager(taskService, eventListenerAdapterFactory);
		

		epochChangeEvent = new EpochIndexChangeEvent(context, 1);
		otherEvent = new EpochErrorChangeEvent(context, 1.0);
	}
	
	@After
	public void cleanUpTest() throws Exception {
		manager.close();
		taskService.close();
	}

	@Test
	public void fireForDispatchedNotification() throws InterruptedException {
		assertFalse(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));
		manager.registerForDispatchedNotification(listener, epochChangeEvent.getClass());
		assertTrue(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));

		manager.unregister(listener, epochChangeEvent.getClass());
		assertFalse(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));
	}

	@Test
	public void fireForImmediateNotification() throws InterruptedException {
		assertFalse(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));
		manager.registerForImmediateNotification(listener, epochChangeEvent.getClass());
		assertTrue(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));

		manager.unregister(listener, epochChangeEvent.getClass());
		assertFalse(manager.fire(epochChangeEvent));
		assertFalse(manager.fire(otherEvent));
	}

	@Test
	public void registerForDispatchedNotificationAndUnregister() {
		assertFalse(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(otherEvent.getClass()));

		assertTrue(manager.registerForDispatchedNotification(listener, epochChangeEvent.getClass()));
		assertFalse(manager.registerForDispatchedNotification(listener, epochChangeEvent.getClass()));
		assertTrue(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));

		assertTrue(manager.unregister(listener, epochChangeEvent.getClass()));
		assertFalse(manager.unregister(listener, epochChangeEvent.getClass()));
		assertFalse(manager.unregister(listener, otherEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(otherEvent.getClass()));
	}

	@Test
	public void registerForImmediateNotificationAndUnregister() {
		assertFalse(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(otherEvent.getClass()));

		assertTrue(manager.registerForImmediateNotification(listener, epochChangeEvent.getClass()));
		assertFalse(manager.registerForImmediateNotification(listener, epochChangeEvent.getClass()));
		assertTrue(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));

		assertTrue(manager.unregister(listener, epochChangeEvent.getClass()));
		assertFalse(manager.unregister(listener, epochChangeEvent.getClass()));
		assertFalse(manager.unregister(listener, otherEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(epochChangeEvent.getClass()));
		assertFalse(manager.isAnyRegisteredListenerFor(otherEvent.getClass()));
	}

}
