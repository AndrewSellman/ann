package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkLayerWeightedInputEvent extends MatrixEvent {

	public NetworkLayerWeightedInputEvent(final Context context, final Matrix input) {
		super(context, input);
	}

	public final Matrix getLayerWeightedInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer weighted input" + super.toString();
	}

}
