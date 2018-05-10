package com.sellman.andrew.vann.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sellman.andrew.vann.core.FeedForwardNetwork;
import com.sellman.andrew.vann.core.FeedForwardNetworkConfig;
import com.sellman.andrew.vann.core.FeedForwardNetworkLayer;
import com.sellman.andrew.vann.core.FeedForwardNetworkLayerConfig;
import com.sellman.andrew.vann.core.event.Context;
import com.sellman.andrew.vann.core.event.EventManager;
import com.sellman.andrew.vann.core.event.Representation;
import com.sellman.andrew.vann.core.math.ColumnVector;
import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.RowVector;
import com.sellman.andrew.vann.core.math.util.IntervalScale;
import com.sellman.andrew.vann.spring.config.EventBeanNames;
import com.sellman.andrew.vann.spring.config.MathBeanNames;
import com.sellman.andrew.vann.spring.controller.network.NetworkConfigRequest;

@Service
public class FeedForwardNetworkFactory {

	@Autowired
	@Qualifier(value = MathBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	private MathOperations mathOps;

	@Autowired
	@Qualifier(value = EventBeanNames.EVENT_MANAGER)
	private EventManager eventManager;

	public FeedForwardNetwork create(NetworkConfigRequest request, String networkName) {
		FeedForwardNetworkConfig config = getNetworkConfiguration(request, networkName);
		return new FeedForwardNetwork(config);
	}

	private FeedForwardNetworkConfig getNetworkConfiguration(NetworkConfigRequest request, String networkName) {
		Context context = new Context(networkName);
		List<FeedForwardNetworkLayer> layers = getLayers(request, networkName);
		return new FeedForwardNetworkConfig(context, eventManager, layers);
	}

	private List<FeedForwardNetworkLayer> getLayers(NetworkConfigRequest request, String networkName) {
		List<FeedForwardNetworkLayer> layers = new ArrayList<>();

		int currentLayerAttributeCount = request.getInputAttributeCount();
		int nextLayerAttributeCount = getAttributeCountForNextLayer(request);

		Matrix weights = getWeights(networkName, 0, nextLayerAttributeCount, currentLayerAttributeCount);
		RowVector bias = getBias(networkName, 0, nextLayerAttributeCount);
		Context context = new Context(networkName, 0);
		FeedForwardNetworkLayerConfig config = new FeedForwardNetworkLayerConfig(context, eventManager, mathOps, request.getFunctionType(), weights, bias);
		layers.add(new FeedForwardNetworkLayer(config));

		for (int h = 0; h < request.getHiddenLayerAttributeCounts().size(); h++) {
			currentLayerAttributeCount = request.getHiddenLayerAttributeCounts().get(h);
			nextLayerAttributeCount = getAttributeCountForNextLayer(request, h);
			int layerIndex = h + 1;
			
			weights = getWeights(networkName, layerIndex, nextLayerAttributeCount, currentLayerAttributeCount);
			bias = getBias(networkName, layerIndex, nextLayerAttributeCount);
			context = new Context(networkName, layerIndex);
			config = new FeedForwardNetworkLayerConfig(context, eventManager, mathOps, request.getFunctionType(), weights, bias);
			layers.add(new FeedForwardNetworkLayer(config));
		}

		return layers;
	}

	private int getAttributeCountForNextLayer(NetworkConfigRequest request, int currentHiddenLayerIndex) {
		if (currentHiddenLayerIndex >= request.getHiddenLayerAttributeCounts().size() - 1) {
			return request.getOutputAttributeCount();
		}

		return request.getHiddenLayerAttributeCounts().get(currentHiddenLayerIndex + 1);
	}

	private int getAttributeCountForNextLayer(NetworkConfigRequest request) {
		if (request.getHiddenLayerAttributeCounts().isEmpty()) {
			return request.getOutputAttributeCount();
		}

		return request.getHiddenLayerAttributeCounts().get(request.getHiddenLayerAttributeCounts().size() - 1);
	}

//TODO allow different ways to generate random values and specify the range.
	private Matrix getWeights(String networkName, int layerIndex, int rowCount, int columnCount) {
		IntervalScale scale = new IntervalScale(-1, 1, -1/Math.sqrt(columnCount), 1/Math.sqrt(columnCount));
		Context context = getWeightsContext(networkName, layerIndex);
		Matrix m = new Matrix(rowCount, columnCount, context, eventManager);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				double weight = nextGaussian();
				weight = scale.down(weight);
				m.setValue(r, c, weight);
			}
		}
		return m;
	}

	private double nextGaussian() {
		return ThreadLocalRandom.current().nextGaussian();
	}
	
	private Context getWeightsContext(String networkName, int layerIndex) {
		return new Context(networkName, layerIndex, Representation.WEIGHTS);
	}

	private RowVector getBias(String networkName, int layerIndex, int rowCount) {
		Context context = getBiasContext(networkName, layerIndex);
		return new RowVector(rowCount, context, eventManager);
	}

	private Context getBiasContext(String networkName, int layerIndex) {
		return new Context(networkName, layerIndex, Representation.BIAS);
	}

}
