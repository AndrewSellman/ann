package com.sellman.andrew.vann.core.event;

public class NetworkLayerOutputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkLayerOutputEvent, NetworkLayerOutputListener, NetworkLayerOutputListenerAdapter> {

	public NetworkLayerOutputListenerAdapterFactory() {
		super(NetworkLayerOutputEvent.class, NetworkLayerOutputListener.class, NetworkLayerOutputListenerAdapter.class);
	}

	@Override
	protected NetworkLayerOutputListenerAdapter create(Listener listener) {
		return new NetworkLayerOutputListenerAdapter(getCompatibleListener(listener));
	}

}
