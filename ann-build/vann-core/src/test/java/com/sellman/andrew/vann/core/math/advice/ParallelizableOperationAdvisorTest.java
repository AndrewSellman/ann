package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperationAdvisor;

@RunWith(MockitoJUnitRunner.class)
public class ParallelizableOperationAdvisorTest {
	private static final int ROW_COUNT = 1;
	private static final int COLUMN_COUNT = 2;

	private ParallelizableOperationAdvisor advisor;
	private Cache<AdviceKey, Boolean> cache;

	@Mock
	private AdviceKey adviceKey;

	@Before
	public void prepareTest() throws Exception {
		cache = new CacheBuilder<AdviceKey, Boolean>("op").build();
		cache.clear();
		advisor = new ParallelizableOperation2Advisor(cache);
	}

	@Test
	public void getRandomMatrix() {
		Matrix m = advisor.getRandomMatrix(ROW_COUNT, COLUMN_COUNT);
		assertEquals(ROW_COUNT, m.getRowCount());
		assertEquals(COLUMN_COUNT, m.getColumnCount());
	}

	@Test
	public void getAdviceWhereAdviceWasPreviouslyStoredAsTrue() {
		advisor.storeAdvice(adviceKey, true);
		assertTrue(advisor.getAdvice(adviceKey));
	}

	@Test
	public void getAdviceWhereAdviceWasPreviouslyStoredAsFalse() {
		advisor.storeAdvice(adviceKey, false);
		assertFalse(advisor.getAdvice(adviceKey));
	}

	@Test
	public void getAdviceWhereAdviceWasNotYetStored() {
		assertNull(advisor.getAdvice(adviceKey));
	}

	@Test
	public void getOpTestCountWhenUsingSpecificedOpTestCountConstructor() {
		advisor = new ParallelizableOperation2Advisor(42, cache);
		assertEquals(42, advisor.getOpTestCount());
	}

	@Test
	public void getOpTestCountWhenUsingDefaultConstructor() {
		assertEquals(100, advisor.getOpTestCount());
	}

}
