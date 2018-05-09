package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Matrix;

public class NetworkOutputEvent extends MatrixEvent {

	public NetworkOutputEvent(final Context context, final Matrix output) {
		super(context, output);
	}

	public final Matrix getOutput() {
		return getEventMatrix();
	}

	public final String toString() {
		return "Network output" + super.toString();
	}

}
