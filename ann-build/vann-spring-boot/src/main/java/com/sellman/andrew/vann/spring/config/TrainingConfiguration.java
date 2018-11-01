package com.sellman.andrew.vann.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.training.TrainingBatchFactory;

@Configuration
public class TrainingConfiguration {

	@Autowired
	@Qualifier(MathBeanNames.INSPECTABLE_MATRIX_FACTORY)
	private InspectableMatrixFactory inspectableMatrixFactory;

	@Bean(name = TrainingBeanNames.TRAINING_BATCH_FACTORY)
	public TrainingBatchFactory getTrainingBatchFactory() {
		return new TrainingBatchFactory(inspectableMatrixFactory);
	}

}
