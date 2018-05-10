package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.InspectableMatrix;

public class NetworkOutputEvent extends MatrixEvent {

	public NetworkOutputEvent(final Context context, final InspectableMatrix output) {
		super(context, output);
	}

	public final InspectableMatrix getOutput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network output" + super.toString();
	}

}
