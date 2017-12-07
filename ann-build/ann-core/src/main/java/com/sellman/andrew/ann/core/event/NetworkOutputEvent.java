package com.sellman.andrew.ann.core.event;

import com.sellman.andrew.ann.core.math.Vector;

public class NetworkOutputEvent extends Event {
	private final Vector output;

	public NetworkOutputEvent(final Context context, final Vector output) {
		super(context);
		this.output = output;
	}

	public Vector getOutput() {
		return output;
	}

	public String toString() {
		return "Output" + super.toString() + " is:\n" + output.toString();
	}

}
