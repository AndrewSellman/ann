package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.math.Vector;

public class NetworkInputEvent extends Event {
	private final Vector input;

	public NetworkInputEvent(final Context context, final Vector input) {
		super(context);
		this.input = input;
	}

	public final Vector getInput() {
		return input;
	}

	public final String toString() {
		return "Input" + super.toString() + " is:\n" + input.toString();
	}

}
