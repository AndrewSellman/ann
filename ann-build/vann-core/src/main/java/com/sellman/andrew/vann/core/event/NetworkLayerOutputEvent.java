package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Vector;

public class NetworkLayerOutputEvent extends Event {
	private final Vector output;

	public NetworkLayerOutputEvent(final Context context, final Vector output) {
		super(context);
		this.output = output;
	}

	public final Vector getOutput() {
		return output;
	}

	public final String toString() {
		return "Output" + super.toString() + " is:\n" + output.toString();
	}

}
