package com.sellman.andrew.vann.core.event;

public class NetworkLayerBiasedWeightedInputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkLayerBiasedWeightedInputEvent, NetworkLayerBiasedWeightedInputListener, NetworkLayerBiasedWeightedInputListenerAdapter> {

	public NetworkLayerBiasedWeightedInputListenerAdapterFactory() {
		super(NetworkLayerBiasedWeightedInputEvent.class, NetworkLayerBiasedWeightedInputListener.class, NetworkLayerBiasedWeightedInputListenerAdapter.class);
	}

	@Override
	protected NetworkLayerBiasedWeightedInputListenerAdapter create(Listener listener) {
		return new NetworkLayerBiasedWeightedInputListenerAdapter(getCompatibleListener(listener));
	}

}
