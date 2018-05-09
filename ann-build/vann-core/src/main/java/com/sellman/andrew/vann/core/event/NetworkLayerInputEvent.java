package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkLayerInputEvent extends MatrixEvent {

	public NetworkLayerInputEvent(final Context context, final Matrix input) {
		super(context, input);
	}

	public final Matrix getLayerInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer input" + super.toString();
	}

}
