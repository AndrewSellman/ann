package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class NetworkLayerOutputEvent extends MatrixEvent {

	public NetworkLayerOutputEvent(final Context context, final InspectableMatrix output) {
		super(context, output);
	}

	public final InspectableMatrix getOutput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer output" + super.toString();
	}

}
