package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.function.FunctionGroup;

public class FeedForwardNetworkLayerConfig {
	private final Context context;
	private final EventManager eventManager;
	private final MathOperations matrixOps;
	private final FunctionGroup functionGroup;
	private final Matrix weights;
	private final RowVector bias;
	
	public FeedForwardNetworkLayerConfig(final Context context, final EventManager eventManager, final MathOperations matrixOps, final FunctionGroup functionGroup, final Matrix weights, final RowVector bias) {
		this.context = context;
		this.eventManager = eventManager;
		this.matrixOps = matrixOps;
		this.weights = weights;
		this.bias = bias;
		this.functionGroup = functionGroup;
	}

	protected Matrix getWeights() {
		return weights;
	}
	
	protected RowVector getBias() {
		return bias;
	}

	protected Function getActivationFunction() {
		return functionGroup.getFunction();
	}

	protected Function getActivationPrimeFunction() {
		return functionGroup.getPrime();
	}

	protected MathOperations getMatrixOps() {
		return matrixOps;
	}

	protected EventManager getEventManager() {
		return eventManager;
	}

	protected Context getContext() {
		return context;
	}

}
