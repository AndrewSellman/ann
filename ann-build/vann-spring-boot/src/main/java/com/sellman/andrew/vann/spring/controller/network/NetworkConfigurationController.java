package com.sellman.andrew.vann.spring.controller.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sellman.andrew.vann.core.AbstractFeedForwardNetworkTrainer;
import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.spring.config.FeedForwardNetworkHarness;
import com.sellman.andrew.vann.spring.controller.Response;
import com.sellman.andrew.vann.spring.service.FeedForwardNetworkFactory;
import com.sellman.andrew.vann.spring.service.FeedForwardNetworkTrainerFactory;

@RestController
public class NetworkConfigurationController {

	@Autowired
	private FeedForwardNetworkFactory networkFactory;

	@Autowired
	private FeedForwardNetworkTrainerFactory networkTrainerFactory;

	@Autowired
	private FeedForwardNetworkHarness harness;

	@RequestMapping(value = "/ann/network/feed/forward/config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> configureNetwork(@RequestBody NetworkConfigRequest request) {

		FeedForwardNetwork network = networkFactory.create(request, harness.getNetworkName());
		harness.setNetwork(network);

		if (harness.getTrainerConfiguration() != null) {
			AbstractFeedForwardNetworkTrainer networkTrainer = networkTrainerFactory.create(harness.getTrainerConfiguration(), harness.getTrainerType(), network);
			harness.setTrainer(networkTrainer);
		}

		return new ResponseEntity<Response>(Response.SUCCESS, HttpStatus.CREATED);
	}

}
