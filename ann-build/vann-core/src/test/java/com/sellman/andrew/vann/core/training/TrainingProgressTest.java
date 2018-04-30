package com.sellman.andrew.vann.core.training;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.event.BatchChangeEvent;
import com.sellman.andrew.vann.core.event.BatchErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EpochChangeEvent;
import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.ResetBatchErrorEvent;
import com.sellman.andrew.vann.core.event.ResetEpochErrorEvent;
import com.sellman.andrew.vann.core.training.TrainingProgress;

@RunWith(MockitoJUnitRunner.class)
public class TrainingProgressTest {
	private Context context;
	private TrainingProgress progress;

	@Mock
	private EventManager eventManager;

	@Before
	public void prepareTest() {
		context = new Context("test");
		progress = new TrainingProgress(context, eventManager, 42);
	}

	@Test
	public void batchErrorOperationsWithListener() {
		when(eventManager.isAnyRegisteredListenerFor(eq(BatchErrorChangeEvent.class))).thenReturn(true);
		when(eventManager.isAnyRegisteredListenerFor(eq(ResetBatchErrorEvent.class))).thenReturn(true);

		assertBatchErrorOperations();
		verify(eventManager).fire(isA(BatchErrorChangeEvent.class));
		verify(eventManager).fire(isA(BatchErrorChangeEvent.class));
	}

	@Test
	public void batchErrorOperationsWithNoListener() {
		assertBatchErrorOperations();
		verify(eventManager, never()).fire(anyObject());
	}

	@Test
	public void epochErrorOperationsWithListener() {
		when(eventManager.isAnyRegisteredListenerFor(eq(EpochErrorChangeEvent.class))).thenReturn(true);
		when(eventManager.isAnyRegisteredListenerFor(eq(ResetEpochErrorEvent.class))).thenReturn(true);

		assertEpochErrorOperations();
		verify(eventManager).fire(isA(EpochErrorChangeEvent.class));
		verify(eventManager).fire(isA(ResetEpochErrorEvent.class));
	}

	@Test
	public void epochErrorOperationsWithNoListener() {
		assertEpochErrorOperations();
		verify(eventManager, never()).fire(anyObject());
	}

	@Test
	public void batchIndexOperationsWithListener() {
		when(eventManager.isAnyRegisteredListenerFor(eq(BatchChangeEvent.class))).thenReturn(true);

		assertBatchIndexOperations();
		verify(eventManager, times(2)).fire(isA(BatchChangeEvent.class));
	}

	@Test
	public void batchIndexOperationsWithNoListener() {
		assertBatchIndexOperations();
		verify(eventManager, never()).fire(anyObject());
	}

	@Test
	public void epochIndexOperationsWithListener() {
		when(eventManager.isAnyRegisteredListenerFor(eq(EpochChangeEvent.class))).thenReturn(true);

		assertEpochIndexOperations();
		verify(eventManager, times(2)).fire(isA(EpochChangeEvent.class));
	}

	@Test
	public void epochIndexOperationsWithNoListener() {
		assertEpochIndexOperations();
		verify(eventManager, never()).fire(anyObject());
	}

	private void assertBatchErrorOperations() {
		assertEquals(0, progress.getBatchError(), 0.0);
		progress.accumulateBatchError(1.0);
		assertEquals(1.0, progress.getBatchError(), 0.0);
		progress.resetBatchError();
		assertEquals(0, progress.getEpochError(), 0.0);
	}

	private void assertEpochErrorOperations() {
		assertEquals(0, progress.getEpochError(), 0.0);
		progress.setEpochError(1.0);
		assertEquals(1.0, progress.getEpochError(), 0.0);
		progress.resetEpochError();
		assertEquals(0, progress.getEpochError(), 0.0);
	}

	private void assertBatchIndexOperations() {
		assertEquals(0, progress.getBatchIndex());
		progress.incrementBatchIndex();
		assertEquals(1, progress.getBatchIndex());
		progress.resetBatchIndex();
		assertEquals(0, progress.getBatchIndex());
	}

	private void assertEpochIndexOperations() {
		assertEquals(0, progress.getEpochIndex());
		progress.incrementEpochIndex();
		assertEquals(1, progress.getEpochIndex());
		progress.resetEpochIndex();
		assertEquals(0, progress.getEpochIndex());
	}

}
