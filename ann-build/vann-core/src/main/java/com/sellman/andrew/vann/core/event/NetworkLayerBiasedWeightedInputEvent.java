package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class NetworkLayerBiasedWeightedInputEvent extends MatrixEvent {

	public NetworkLayerBiasedWeightedInputEvent(final Context context, final InspectableMatrix input) {
		super(context, input);
	}

	public final InspectableMatrix getBiasedWeightedInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network layer biased weighted input" + super.toString();
	}

}
