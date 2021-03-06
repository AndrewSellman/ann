package com.sellman.andrew.vann.core.math.advice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation2Factory;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;

@RunWith(MockitoJUnitRunner.class)
public class AdvisableParallelizabeOperation2FactoryTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private AdvisableParallelizabeOperation2Factory factory;

	@Mock
	private ParallelizableOperation2Advisor advisor;

	@Before
	public void prepareTest() {
		Whitebox.setInternalState(factory, ParallelizableOperation2Advisor.class, advisor);
	}

	@Test
	public void getOperationAdvisor() {
		assertEquals(advisor, factory.getOperationAdvisor());
	}

}
