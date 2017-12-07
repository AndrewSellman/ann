package com.sellman.andrew.ann.core.event;

public class Context {
	private final String networkName;
	private final int networkLayerIndex;
	
	public Context(final String networkName) {
		this(networkName, -1);
	}
	
	public Context(final String networkName, int networkLayerIndex) {
		this.networkName = networkName;
		this.networkLayerIndex = networkLayerIndex;
	}

	public String getNetworkName() {
		return networkName;
	}
	
	public int getNetworkLayerIndex() {
		return networkLayerIndex;
	}
	
}
