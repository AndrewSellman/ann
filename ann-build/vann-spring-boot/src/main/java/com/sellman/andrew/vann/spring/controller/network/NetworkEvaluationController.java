package com.sellman.andrew.vann.spring.controller.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.Vector;
import com.sellman.andrew.vann.spring.config.FeedForwardNetworkHarness;

@RestController
public class NetworkEvaluationController {

	@Autowired
	private FeedForwardNetworkHarness harness;

	@RequestMapping(value = "/ann/network/feed/forward/evaluate/manual", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NetworkEvaluationResponse> evaluateNetwork(@RequestBody NetworkEvaluationRequest request) {

		Matrix input = new Matrix(request.getInput());
		FeedForwardNetwork network = harness.getNetwork();

		Matrix networkOutput = network.evaluate(input);
		int outputRowCount = networkOutput.getRowCount();
		int outputColumnCount = networkOutput.getColumnCount();
		double[][] output = new double[outputRowCount][outputColumnCount];
		for (int r = 0; r < outputRowCount; r++) {
			for (int c = 0; c < outputColumnCount; c++) {
				output[r][c] = networkOutput.getValue(r, c);
			}
		}

		return new ResponseEntity<NetworkEvaluationResponse>(new NetworkEvaluationResponse(output), HttpStatus.OK);
	}

}
