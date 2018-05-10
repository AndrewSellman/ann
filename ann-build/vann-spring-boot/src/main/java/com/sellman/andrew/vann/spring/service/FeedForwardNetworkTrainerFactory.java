package com.sellman.andrew.vann.spring.service;

import org.springframework.stereotype.Service;

import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.FeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.GradientDescentType;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;

@Service
public class FeedForwardNetworkTrainerFactory {

	public FeedForwardNetworkTrainer create(FeedForwardNetworkTrainerConfig config, GradientDescentType type, FeedForwardNetwork network) {
		return create(config, network, type);
	}

	private FeedForwardNetworkTrainer create(FeedForwardNetworkTrainerConfig config, FeedForwardNetwork network, GradientDescentType trainerType) {
		switch (trainerType) {
		case BATCH:
			return new FeedForwardNetworkTrainer(config, network);
		case MINI_BATCH:
			break; // TODO ... implement mini batch...
		case STOCHASTIC:
			return new FeedForwardNetworkTrainer(config, network);
		}

		throw new IllegalStateException("Unknwon or unsupported gradient descent trainer type: " + trainerType);
	}

}
