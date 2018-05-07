package com.sellman.andrew.vann.core.event;

public class NetworkLayerInputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkLayerInputEvent, NetworkLayerInputListener, NetworkLayerInputListenerAdapter> {

	public NetworkLayerInputListenerAdapterFactory() {
		super(NetworkLayerInputEvent.class, NetworkLayerInputListener.class, NetworkLayerInputListenerAdapter.class);
	}

	@Override
	protected NetworkLayerInputListenerAdapter create(Listener listener) {
		return new NetworkLayerInputListenerAdapter(getCompatibleListener(listener));
	}

}
