package com.sellman.andrew.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.MathOperationsFactory;

@Configuration
public class MathOperationsConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	private TaskService highPriorityWaitForCompletionMultiThreadedTaskService;
	
	@Bean(name = MathOperationsBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	public MathOperations getHighPriorityWaitForCompletionMathOperations() {
		return new MathOperationsFactory().getOperations(highPriorityWaitForCompletionMultiThreadedTaskService);
	}

}
