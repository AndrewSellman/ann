package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkLayerBiasedWeightedInputEvent extends MatrixEvent {

	public NetworkLayerBiasedWeightedInputEvent(final Context context, final Matrix input) {
		super(context, input);
	}

	public final Matrix getLayerBiasedWeightedInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer biased weighted input" + super.toString();
	}

}
