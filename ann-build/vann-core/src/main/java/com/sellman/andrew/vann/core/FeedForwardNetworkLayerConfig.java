package com.sellman.andrew.vann.core;

import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.math.InspectableMatrixFactory;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.function.Function;
import com.sellman.andrew.vann.core.math.function.FunctionGroup;

public class FeedForwardNetworkLayerConfig {
	private final Context context;
	private final EventManager eventManager;
	private final MathOperations mathOps;
	private final FunctionGroup functionGroup;
	private final Matrix weights;
	private final RowVector bias;
	private final InspectableMatrixFactory matrixFactory;
	
	public FeedForwardNetworkLayerConfig(final Context context, final EventManager eventManager, final MathOperations mathOps, final FunctionGroup functionGroup, final Matrix weights, final RowVector bias, InspectableMatrixFactory matrixFactory) {
		this.context = context;
		this.eventManager = eventManager;
		this.mathOps = mathOps;
		this.weights = weights;
		this.bias = bias;
		this.functionGroup = functionGroup;
		this.matrixFactory = matrixFactory;
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

	protected MathOperations getMathOps() {
		return mathOps;
	}

	protected EventManager getEventManager() {
		return eventManager;
	}

	protected Context getContext() {
		return context;
	}
	
	protected InspectableMatrixFactory getMatrixFactory() {
		return matrixFactory;
	}

}
