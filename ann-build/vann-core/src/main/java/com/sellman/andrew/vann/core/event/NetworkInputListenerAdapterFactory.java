package com.sellman.andrew.vann.core.event;

public class NetworkInputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkInputEvent, NetworkInputListener, NetworkInputListenerAdapter> {

	public NetworkInputListenerAdapterFactory() {
		super(NetworkInputEvent.class, NetworkInputListener.class, NetworkInputListenerAdapter.class);
	}

	@Override
	protected NetworkInputListenerAdapter create(Listener listener) {
		return new NetworkInputListenerAdapter(getCompatibleListener(listener));
	}

}
