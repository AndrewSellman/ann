package com.sellman.andrew.vann.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.MathOperationsFactory;
import com.sellman.andrew.vann.core.math.RowVectorFactory;
import com.sellman.andrew.vann.core.math.add.AdditionFactory;
import com.sellman.andrew.vann.core.math.advice.AdviceKey;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation2Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation3Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation4Advisor;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation5Advisor;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.scale.ScalerFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.vann.core.math.sum.SummationFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.vann.core.math.update.UpdationFactory;
import com.sellman.andrew.vann.core.cache.Cache;
import com.sellman.andrew.vann.core.cache.CacheBuilder;

@Configuration
public class MathConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	private TaskService highPriorityWaitForCompletionMultiThreadedTaskService;

	@Bean(name = MathBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
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
		return new SummationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation4Advisor(getSummationAdviceCache()));
	}
	
	@Bean
	public Cache<AdviceKey, Boolean> getSummationAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("addition").build();
	}

	private TranspositionFactory getTranspositionFactory() {
		return new TranspositionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation3Advisor(getTranspositionAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getTranspositionAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("transposition").build();
	}

	private UpdationFactory getUpdationFactory() {
		return new UpdationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation5Advisor(getUpdationnAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getUpdationnAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("updation").build();
	}

	private AdditionFactory getAdditionFactory() {
		return new AdditionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor(getAdditionAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getAdditionAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("addition").build();
	}

	private ScalerFactory getScalerFactory() {
		return new ScalerFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation2Advisor(getScalerAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getScalerAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("scaler").build();
	}

	private SubtractionFactory getSubtractionFactory() {
		return new SubtractionFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor(getSubtractionAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getSubtractionAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("subtraction").build();
	}

	private HadamardMultiplicationFactory getHadamardMultiplicationFactory() {
		return new HadamardMultiplicationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor(getHadamardMultiplicationAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getHadamardMultiplicationAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("hadamardMultiplcation").build();
	}

	private StandardMultiplicationFactory getStandardMultiplicationFactory() {
		return new StandardMultiplicationFactory(highPriorityWaitForCompletionMultiThreadedTaskService, new ParallelizableOperation1Advisor(getStandardMultiplicationAdviceCache()));
	}

	@Bean
	public Cache<AdviceKey, Boolean> getStandardMultiplicationAdviceCache() {
		return new CacheBuilder<AdviceKey, Boolean>("standardMultiplcation").build();
	}

	@Bean(name = MathBeanNames.INSPECTABLE_MATRIX_FACTORY)
	public InspectableMatrixFactory getInspectableMatrixFactory() {
		return new InspectableMatrixFactory();
	}

	@Bean(name = MathBeanNames.ROW_VECTOR_FACTORY)
	public RowVectorFactory getRowVectorFactory() {
		return new RowVectorFactory();
	}

}
