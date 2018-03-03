package com.sellman.andrew.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.MathOperationsFactory;
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

@Configuration
public class MathOperationsConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	private TaskService highPriorityWaitForCompletionMultiThreadedTaskService;

	@Bean(name = MathOperationsBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	public MathOperations getHighPriorityWaitForCompletionMathOperations() {
		AdditionFactory additionFactory = getAdditionFactory();
		SummationFactory summationFactory = getSummationFactory();
		SubtractionFactory subtractionFactory = getSubtractionFactory();
		ScalerFactory scalerFactory = getScalerFactory();
		TranspositionFactory transpositionFactory = getTranspositionFactory();
		StandardMultiplicationFactory standardMultiplicationFactory = getStandardMultiplicationFactory();
		HadamardMultiplicationFactory hadamardMultiplicationFactory = getHadamardMultiplicationFactory();
		UpdationFactory updationFactory = getUpdationFactory();
		MathOperationsFactory opsFactory = new MathOperationsFactory(additionFactory, summationFactory, subtractionFactory, scalerFactory, transpositionFactory, standardMultiplicationFactory, hadamardMultiplicationFactory, updationFactory);
		return opsFactory.getOperations();
	}

	private SummationFactory getSummationFactory() {
		return new SummationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation4Advisor());
	}

	private TranspositionFactory getTranspositionFactory() {
		return new TranspositionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation3Advisor());
	}

	private UpdationFactory getUpdationFactory() {
		return new UpdationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation5Advisor());
	}

	private AdditionFactory getAdditionFactory() {
		return new AdditionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor());
	}

	private ScalerFactory getScalerFactory() {
		return new ScalerFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation2Advisor());
	}

	private SubtractionFactory getSubtractionFactory() {
		return new SubtractionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor());
	}

	private HadamardMultiplicationFactory getHadamardMultiplicationFactory() {
		return new HadamardMultiplicationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor());
	}

	private StandardMultiplicationFactory getStandardMultiplicationFactory() {
		return new StandardMultiplicationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor());
	}

}
