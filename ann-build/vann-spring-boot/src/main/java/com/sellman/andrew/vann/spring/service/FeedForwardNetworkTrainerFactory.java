package com.sellman.andrew.vann.spring.service;

import org.springframework.stereotype.Service;

import com.sellman.andrew.vann.core.AbstractFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.BatchGradientDescentFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.GradientDescentType;
import com.sellman.andrew.vann.core.StochasticGradientDescentFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.training.FeedForwardNetworkTrainerConfig;

@Service
public class FeedForwardNetworkTrainerFactory {

	public AbstractFeedForwardNetworkTrainer create(FeedForwardNetworkTrainerConfig config, GradientDescentType type, FeedForwardNetwork network) {
		return create(config, network, type);
	}

	private AbstractFeedForwardNetworkTrainer create(FeedForwardNetworkTrainerConfig config, FeedForwardNetwork network, GradientDescentType trainerType) {
		switch (trainerType) {
		case BATCH:
			return new BatchGradientDescentFeedForwardNetworkTrainer(config, network);
		case MINI_BATCH:
			break; // TODO ... implement mini batch...
		case STOCHASTIC:
			return new StochasticGradientDescentFeedForwardNetworkTrainer(config, network);
		}

		throw new IllegalStateException("Unknwon or unsupported gradient descent trainer type: " + trainerType);
	}

}
