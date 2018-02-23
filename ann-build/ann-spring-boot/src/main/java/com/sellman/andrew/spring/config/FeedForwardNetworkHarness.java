package com.sellman.andrew.spring.config;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.sellman.andrew.ann.core.AbstractFeedForwardNetworkTrainer;
import com.sellman.andrew.ann.core.FeedForwardNetwork;
import com.sellman.andrew.ann.core.GradientDescentType;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;

@Component
@SessionScope
public class FeedForwardNetworkHarness {
	private final String networkName;
	private FeedForwardNetwork network;
	private FeedForwardNetworkTrainerConfig trainerConfiguration;
	private GradientDescentType trainerType;
	private AbstractFeedForwardNetworkTrainer trainer;

	public FeedForwardNetworkHarness() {
		networkName = UUID.randomUUID().toString();
	}

	public FeedForwardNetwork getNetwork() {
		return network;
	}

	public void setNetwork(FeedForwardNetwork network) {
		this.network = network;
	}

	public AbstractFeedForwardNetworkTrainer getTrainer() {
		return trainer;
	}

	public void setTrainer(AbstractFeedForwardNetworkTrainer trainer) {
		this.trainer = trainer;
	}

	public String getNetworkName() {
		return networkName;
	}

	public FeedForwardNetworkTrainerConfig getTrainerConfiguration() {
		return trainerConfiguration;
	}

	public void setTrainerConfiguration(FeedForwardNetworkTrainerConfig trainerConfiguration) {
		this.trainerConfiguration = trainerConfiguration;
	}

	public GradientDescentType getTrainerType() {
		return trainerType;
	}

	public void setTrainerType(GradientDescentType trainerType) {
		this.trainerType = trainerType;
	}

}
