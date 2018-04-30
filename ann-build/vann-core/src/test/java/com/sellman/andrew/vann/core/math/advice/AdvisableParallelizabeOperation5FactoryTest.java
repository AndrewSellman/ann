package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation5Factory;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizabeOperation5FactoryTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizabeOperation5Factory factory;

	@Mock
	private ParallelizableOperation5Advisor advisor;

	@Before
	public void prepareTest() {
		Whitebox.setInternalState(factory, ParallelizableOperation5Advisor.class, advisor);
	}

	@Test
	public void getOperationAdvisor() {
		assertEquals(advisor, factory.getOperationAdvisor());
	}

}
