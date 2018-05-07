package com.sellman.andrew.vann.core.event;

public class NetworkOutputListenerAdapterFactory extends AbstractEventListenerAdapterFactory<NetworkOutputEvent, NetworkOutputListener, NetworkOutputListenerAdapter> {

	public NetworkOutputListenerAdapterFactory() {
		super(NetworkOutputEvent.class, NetworkOutputListener.class, NetworkOutputListenerAdapter.class);
	}

	@Override
	protected NetworkOutputListenerAdapter create(Listener listener) {
		return new NetworkOutputListenerAdapter(getCompatibleListener(listener));
	}

}
