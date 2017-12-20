package com.sellman.andrew.ann.core.event;

public abstract class Event {
	private final Context context;

	public Event(final Context context) {
		this.context = context;
	}

	public String getNetworkName() {
		return context.getNetworkName();
	}

	public int getNetworkLayerIndex() {
		return context.getNetworkLayerIndex();
	}

	public String toString() {
		return " for network layer index: " + getNetworkLayerIndex() + " of network: <" + getNetworkName() + ">";
	}

}
