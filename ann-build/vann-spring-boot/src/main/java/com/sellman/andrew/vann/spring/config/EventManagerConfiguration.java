package com.sellman.andrew.vann.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.event.BatchChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.BatchErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EpochChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EpochErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.MatrixChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.MatrixPollListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkOutputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ResetBatchErrorListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ResetEpochErrorListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.VectorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.VectorPollListenerAdapterFactory;

@Configuration
public class EventManagerConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.LOW_PRIORITY_FIRE_AND_FORGET_SINGLE_THREAD)
	private TaskService lowPriorityFireAndForgetSingleThreadTaskService;

	@SessionScope
	@Bean(name = EventManagerBeanNames.EVENT_MANAGER)
	public EventManager getEventManager() {
		return new EventManager(lowPriorityFireAndForgetSingleThreadTaskService, getEventListenerAdapterFactory());
	}

	@Bean(name = EventManagerBeanNames.EVENT_LISTENER_ADAPATER_FACTORY)
	public EventListenerAdapterFactory getEventListenerAdapterFactory() {
		EventListenerAdapterFactory factory = new EventListenerAdapterFactory();
		factory.register(getBatchChangeListenerAdapterFactory());
		factory.register(getBatchErrorChangeListenerAdapterFactory());
		factory.register(getEpochChangeListenerAdapterFactory());
		factory.register(getEpochErrorChangeListenerAdapterFactory());
		factory.register(getMatrixChangeListenerAdapterFactory());
		factory.register(getMatrixPollListenerAdapterFactory());
		factory.register(getNetworkInputListenerAdapterFactory());
		factory.register(getNetworkLayerInputListenerAdapterFactory());
		factory.register(getNetworkLayerOutputListenerAdapterFactory());
		factory.register(getNetworkOutputListenerAdapterFactory());
		factory.register(getResetBatchErrorListenerAdapterFactory());
		factory.register(getResetEpochErrorListenerAdapterFactory());
		factory.register(getValidationErrorChangeListenerAdapterFactory());
		factory.register(getVectorChangeListenerAdapterFactory());
		factory.register(getVectorPollListenerAdapterFactory());
		return factory;
	}

	@Bean(name = EventManagerBeanNames.BATCH_CHANGE_LISTENER_ADAPTER_FACTORY)
	public BatchChangeListenerAdapterFactory getBatchChangeListenerAdapterFactory() {
		return new BatchChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.BATCH_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public BatchErrorChangeListenerAdapterFactory getBatchErrorChangeListenerAdapterFactory() {
		return new BatchErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.EPOCH_CHANGE_LISTENER_ADAPTER_FACTORY)
	public EpochChangeListenerAdapterFactory getEpochChangeListenerAdapterFactory() {
		return new EpochChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.EPOCH_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public EpochErrorChangeListenerAdapterFactory getEpochErrorChangeListenerAdapterFactory() {
		return new EpochErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.MATRIX_CHANGE_LISTENER_ADAPTER_FACTORY)
	public MatrixChangeListenerAdapterFactory getMatrixChangeListenerAdapterFactory() {
		return new MatrixChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.MATRIX_POLL_LISTENER_ADAPTER_FACTORY)
	public MatrixPollListenerAdapterFactory getMatrixPollListenerAdapterFactory() {
		return new MatrixPollListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.NETWORK_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkInputListenerAdapterFactory getNetworkInputListenerAdapterFactory() {
		return new NetworkInputListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.NETWORK_LAYER_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerInputListenerAdapterFactory getNetworkLayerInputListenerAdapterFactory() {
		return new NetworkLayerInputListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.NETWORK_LAYER_OUTPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerOutputListenerAdapterFactory getNetworkLayerOutputListenerAdapterFactory() {
		return new NetworkLayerOutputListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.NETWORK_OUTPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkOutputListenerAdapterFactory getNetworkOutputListenerAdapterFactory() {
		return new NetworkOutputListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.RESET_BATCH_ERROR_LISTENER_ADAPTER_FACTORY)
	public ResetBatchErrorListenerAdapterFactory getResetBatchErrorListenerAdapterFactory() {
		return new ResetBatchErrorListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.RESET_EPOCH_ERROR_LISTENER_ADAPTER_FACTORY)
	public ResetEpochErrorListenerAdapterFactory getResetEpochErrorListenerAdapterFactory() {
		return new ResetEpochErrorListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.VALIDATION_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public ValidationErrorChangeListenerAdapterFactory getValidationErrorChangeListenerAdapterFactory() {
		return new ValidationErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.VECTOR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public VectorChangeListenerAdapterFactory getVectorChangeListenerAdapterFactory() {
		return new VectorChangeListenerAdapterFactory();
	}

	@Bean(name = EventManagerBeanNames.VECTOR_POLL_LISTENER_ADAPTER_FACTORY)
	public VectorPollListenerAdapterFactory getVectorPollListenerAdapterFactory() {
		return new VectorPollListenerAdapterFactory();
	}

}
