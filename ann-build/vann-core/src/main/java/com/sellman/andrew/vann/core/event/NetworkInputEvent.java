package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkInputEvent extends MatrixEvent {

	public NetworkInputEvent(final Context context, final Matrix input) {
		super(context, input);
	}

	public final Matrix getInput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network input" + super.toString();
	}

}
