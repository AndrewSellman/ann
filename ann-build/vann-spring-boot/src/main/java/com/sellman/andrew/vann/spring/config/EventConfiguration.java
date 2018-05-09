package com.sellman.andrew.vann.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.event.BatchErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.BatchIndexChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EpochErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EpochIndexChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.MatrixChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.MatrixPollListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerBiasedWeightedInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerOutputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkLayerWeightedInputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.NetworkOutputListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ResetBatchErrorListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ResetEpochErrorListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.ValidationErrorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.VectorChangeListenerAdapterFactory;
import com.sellman.andrew.vann.core.event.VectorPollListenerAdapterFactory;

@Configuration
public class EventConfiguration {

	@Autowired
	@Qualifier(TaskServiceBeanNames.LOW_PRIORITY_FIRE_AND_FORGET_SINGLE_THREAD)
	private TaskService lowPriorityFireAndForgetSingleThreadTaskService;

	@SessionScope
	@Bean(name = EventBeanNames.EVENT_MANAGER)
	public EventManager getEventManager() {
		return new EventManager(lowPriorityFireAndForgetSingleThreadTaskService, getEventListenerAdapterFactory());
	}

	@Bean(name = EventBeanNames.EVENT_LISTENER_ADAPATER_FACTORY)
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
		
		factory.register(getNetworkLayerWeightedInputListenerAdapterFactory());
		factory.register(getNetworkLayerBiasedWeightedInputListenerAdapterFactory());

		return factory;
	}

	@Bean(name = EventBeanNames.BATCH_CHANGE_LISTENER_ADAPTER_FACTORY)
	public BatchIndexChangeListenerAdapterFactory getBatchChangeListenerAdapterFactory() {
		return new BatchIndexChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.BATCH_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public BatchErrorChangeListenerAdapterFactory getBatchErrorChangeListenerAdapterFactory() {
		return new BatchErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.EPOCH_CHANGE_LISTENER_ADAPTER_FACTORY)
	public EpochIndexChangeListenerAdapterFactory getEpochChangeListenerAdapterFactory() {
		return new EpochIndexChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.EPOCH_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public EpochErrorChangeListenerAdapterFactory getEpochErrorChangeListenerAdapterFactory() {
		return new EpochErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.MATRIX_CHANGE_LISTENER_ADAPTER_FACTORY)
	public MatrixChangeListenerAdapterFactory getMatrixChangeListenerAdapterFactory() {
		return new MatrixChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.MATRIX_POLL_LISTENER_ADAPTER_FACTORY)
	public MatrixPollListenerAdapterFactory getMatrixPollListenerAdapterFactory() {
		return new MatrixPollListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkInputListenerAdapterFactory getNetworkInputListenerAdapterFactory() {
		return new NetworkInputListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_LAYER_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerInputListenerAdapterFactory getNetworkLayerInputListenerAdapterFactory() {
		return new NetworkLayerInputListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_LAYER_OUTPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerOutputListenerAdapterFactory getNetworkLayerOutputListenerAdapterFactory() {
		return new NetworkLayerOutputListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_OUTPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkOutputListenerAdapterFactory getNetworkOutputListenerAdapterFactory() {
		return new NetworkOutputListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.RESET_BATCH_ERROR_LISTENER_ADAPTER_FACTORY)
	public ResetBatchErrorListenerAdapterFactory getResetBatchErrorListenerAdapterFactory() {
		return new ResetBatchErrorListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.RESET_EPOCH_ERROR_LISTENER_ADAPTER_FACTORY)
	public ResetEpochErrorListenerAdapterFactory getResetEpochErrorListenerAdapterFactory() {
		return new ResetEpochErrorListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.VALIDATION_ERROR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public ValidationErrorChangeListenerAdapterFactory getValidationErrorChangeListenerAdapterFactory() {
		return new ValidationErrorChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.VECTOR_CHANGE_LISTENER_ADAPTER_FACTORY)
	public VectorChangeListenerAdapterFactory getVectorChangeListenerAdapterFactory() {
		return new VectorChangeListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.VECTOR_POLL_LISTENER_ADAPTER_FACTORY)
	public VectorPollListenerAdapterFactory getVectorPollListenerAdapterFactory() {
		return new VectorPollListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_LAYER_WEIGHTED_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerWeightedInputListenerAdapterFactory getNetworkLayerWeightedInputListenerAdapterFactory() {
		return new NetworkLayerWeightedInputListenerAdapterFactory();
	}

	@Bean(name = EventBeanNames.NETWORK_LAYER_BIASED_WEIGHTED_INPUT_LISTENER_ADAPTER_FACTORY)
	public NetworkLayerBiasedWeightedInputListenerAdapterFactory getNetworkLayerBiasedWeightedInputListenerAdapterFactory() {
		return new NetworkLayerBiasedWeightedInputListenerAdapterFactory();
	}

}
