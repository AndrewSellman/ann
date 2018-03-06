package com.sellman.andrew.spring.controller.network;

import com.sellman.andrew.spring.controller.Response;
import com.sellman.andrew.spring.controller.Status;

public class NetworkEvaluationResponse extends Response {
	private final double[] output;

	public NetworkEvaluationResponse(double[] output) {
		super(Status.SUCCESS, null);
		this.output = output;
	}

	public NetworkEvaluationResponse(String errorMessage) {
		super(Status.ERROR, errorMessage);
		this.output = null;
	}

	public double[] getOutput() {
		return output;
	}

}
