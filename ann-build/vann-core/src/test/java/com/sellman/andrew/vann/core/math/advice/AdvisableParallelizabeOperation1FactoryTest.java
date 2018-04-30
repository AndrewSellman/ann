package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizabeOperation1FactoryTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizabeOperation1Factory factory;

	@Mock
	private ParallelizableOperation1Advisor advisor;

	@Before
	public void prepareTest() {
		Whitebox.setInternalState(factory, ParallelizableOperation1Advisor.class, advisor);
	}

	@Test
	public void getOperationAdvisor() {
		assertEquals(advisor, factory.getOperationAdvisor());
	}

}
