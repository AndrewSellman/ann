package com.sellman.andrew.ann.core.event;

public class Context {
	private final String networkName;
	private final int networkLayerIndex;
	private final Representation represents;
	
	public Context(final String networkName, int networkLayerIndex, Representation represents) {
		this.networkName = networkName;
		this.networkLayerIndex = networkLayerIndex;
		this.represents = represents;
	}

	public Context(final String networkName, int networkLayerIndex) {
		this(networkName, networkLayerIndex, null);
	}

	public Context(final String networkName) {
		this(networkName, -1);
	}

	public final String getNetworkName() {
		return networkName;
	}
	
	public final int getNetworkLayerIndex() {
		return networkLayerIndex;
	}

	public Representation getRepresents() {
		return represents;
	}
	
}
