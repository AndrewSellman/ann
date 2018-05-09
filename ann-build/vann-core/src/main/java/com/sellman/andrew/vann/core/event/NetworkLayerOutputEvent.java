package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkLayerOutputEvent extends MatrixEvent {

	public NetworkLayerOutputEvent(final Context context, final Matrix output) {
		super(context, output);
	}

	public final Matrix getOutput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer output" + super.toString();
	}

}
