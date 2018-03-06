package com.sellman.andrew.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.event.EventManager;

@Configuration
public class EventManagerConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.LOW_PRIORITY_FIRE_AND_FORGET_SINGLE_THREAD)
	private TaskService lowPriorityFireAndForgetSingleThreadTaskService;

	@SessionScope
	@Bean(name = EventManagerBeanNames.EVENT_MANAGER)
	public EventManager getEventManager() {
		return new EventManager(lowPriorityFireAndForgetSingleThreadTaskService);
	}

}
