package com.sellman.andrew.spring.controller.network.trainer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sellman.andrew.ann.core.AbstractFeedForwardNetworkTrainer;
import com.sellman.andrew.ann.core.training.FeedForwardNetworkTrainerConfig;
import com.sellman.andrew.spring.config.FeedForwardNetworkHarness;
import com.sellman.andrew.spring.controller.Response;
import com.sellman.andrew.spring.service.FeedForwardNetworkTrainerConfigFactory;
import com.sellman.andrew.spring.service.FeedForwardNetworkTrainerFactory;

@RestController
public class NetworkTrainerConfigurationController {

	@Autowired
	private FeedForwardNetworkTrainerConfigFactory networkTrainerConfigFactory;

	@Autowired
	private FeedForwardNetworkTrainerFactory networkTrainerFactory;

	@Autowired
	private FeedForwardNetworkHarness harness;

	@RequestMapping(value = "/ann/network/feed/forward/trainer/config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> configureNetworkTrainer(@Valid @RequestBody NetworkTrainerConfigRequest request) {
		harness.setTrainerType(request.getTrainerType());

		FeedForwardNetworkTrainerConfig config = networkTrainerConfigFactory.create(request);
		harness.setTrainerConfiguration(config);

		if (harness.getNetwork() != null) {
			AbstractFeedForwardNetworkTrainer trainer = networkTrainerFactory.create(config, harness.getTrainerType(), harness.getNetwork());
			harness.setTrainer(trainer);
		}

		return new ResponseEntity<Response>(Response.SUCCESS, HttpStatus.CREATED);
	}

}
