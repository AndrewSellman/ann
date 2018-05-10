package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class NetworkInputEvent extends MatrixEvent {

	public NetworkInputEvent(final Context context, final InspectableMatrix input) {
		super(context, input);
	}

	public final InspectableMatrix getInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network input" + super.toString();
	}

}
