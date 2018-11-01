package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class NetworkLayerInputEvent extends MatrixEvent {

	public NetworkLayerInputEvent(final Context context, final InspectableMatrix input) {
		super(context, input);
	}

	public final InspectableMatrix getInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer input" + super.toString();
	}

}
