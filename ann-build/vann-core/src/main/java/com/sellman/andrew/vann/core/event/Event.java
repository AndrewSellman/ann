package com.sellman.andrew.vann.core.event;

public abstract class Event {
	private final Context context;

	public Event(final Context context) {
		this.context = context;
	}

	public final String getNetworkName() {
		return context.getNetworkName();
	}

	public final int getNetworkLayerIndex() {
		return context.getNetworkLayerIndex();
	}

	public final Representation getRepresentation() {
		return context.getRepresents();
	}

	public String toString() {
		return " representing " + getRepresentation() + " for network layer index: <" + getNetworkLayerIndex() + "> of network: <" + getNetworkName() + ">";
	}

}
