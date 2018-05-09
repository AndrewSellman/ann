package com.sellman.andrew.vann.core.event;

public class NetworkLayerWeightedInputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkLayerWeightedInputEvent, NetworkLayerWeightedInputListener, NetworkLayerWeightedInputListenerAdapter> {

	public NetworkLayerWeightedInputListenerAdapterFactory() {
		super(NetworkLayerWeightedInputEvent.class, NetworkLayerWeightedInputListener.class, NetworkLayerWeightedInputListenerAdapter.class);
	}

	@Override
	protected NetworkLayerWeightedInputListenerAdapter create(Listener listener) {
		return new NetworkLayerWeightedInputListenerAdapter(getCompatibleListener(listener));
	}

}
