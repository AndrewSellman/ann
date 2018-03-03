package com.sellman.andrew.ann.core.math;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.add.AdditionFactory;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.ann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.scale.ScalerFactory;
import com.sellman.andrew.ann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.ann.core.math.sum.SummationFactory;
import com.sellman.andrew.ann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.ann.core.math.update.UpdationFactory;

@RunWith(MockitoJUnitRunner.class)
public class MathOperationsFactoryTest {

	private MathOperationsFactory mathOperationsFactory;

	@Mock
	private TaskService taskService;
	
	@Mock
	private ParallelizableOperation1Advisor advisor1;
	
	@Mock
	private ParallelizableOperation2Advisor advisor2;

	@Mock
	private ParallelizableOperation3Advisor advisor3;

	@Mock
	private ParallelizableOperation4Advisor advisor4;

	@Mock
	private ParallelizableOperation5Advisor advisor5;

	private AdditionFactory additionFactory;
	private SummationFactory summationFactory;
	private SubtractionFactory subtractionFactory;
	private ScalerFactory scalerFactory;
	private StandardMultiplicationFactory standardMultiplicationFactory;
	private HadamardMultiplicationFactory hadamardMultiplicationFactory;
	private TranspositionFactory transpositionFactory;
	private UpdationFactory updationFactory;

	@Before
	public void prepareTest() {
		additionFactory = new AdditionFactory(taskService, advisor1); 
		summationFactory = new SummationFactory(taskService, advisor4);
		subtractionFactory = new SubtractionFactory(taskService, advisor1);
		scalerFactory = new ScalerFactory(taskService, advisor2);
		transpositionFactory = new TranspositionFactory(taskService, advisor3);
		standardMultiplicationFactory = new StandardMultiplicationFactory(taskService, advisor1);
		hadamardMultiplicationFactory = new HadamardMultiplicationFactory(taskService, advisor1);
		updationFactory = new UpdationFactory(taskService, advisor5);
		mathOperationsFactory = new MathOperationsFactory(additionFactory, summationFactory, subtractionFactory, scalerFactory, transpositionFactory, standardMultiplicationFactory, hadamardMultiplicationFactory, updationFactory);
	}

	@Test
	public void test() {
		MathOperations mathOps = mathOperationsFactory.getOperations();
		assertNotNull(mathOps);
	}

}
