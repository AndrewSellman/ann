package com.sellman.andrew.vann.core.event;

class NetworkLayerOutputListenerAdapter implements EventListenerAdapter {
	private final NetworkLayerOutputListener listener;

	public NetworkLayerOutputListenerAdapter(NetworkLayerOutputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkLayerOutputEvent.class.cast(event));
	}

	private void onEvent(NetworkLayerOutputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
