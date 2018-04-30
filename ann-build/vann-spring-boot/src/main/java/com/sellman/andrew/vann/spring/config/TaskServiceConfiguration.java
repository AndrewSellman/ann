package com.sellman.andrew.vann.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.concurrent.TaskServiceBuilder;

@Configuration
public class TaskServiceConfiguration {
	private int threadCount;
	
	public TaskServiceConfiguration() {
		threadCount = Runtime.getRuntime().availableProcessors();
	}

	@Bean(name = TaskServiceBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION_MUTLI_THREADED)
	public TaskService getHighPriorityWaitForCompletionMultiThreadedTaskService() {
		return new TaskServiceBuilder().highPriority().waitForCompletion().setThreadCount(threadCount).build();
	}

	@Bean(name = TaskServiceBeanNames.LOW_PRIORITY_FIRE_AND_FORGET_SINGLE_THREAD)
	public TaskService getLowPriorityFireAndForgetSingleThreadTaskService() {
		return new TaskServiceBuilder().lowPriority().fireAndForget().setThreadCount(1).build();
	}

}
