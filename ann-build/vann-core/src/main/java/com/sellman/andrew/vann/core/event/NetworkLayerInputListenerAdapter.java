package com.sellman.andrew.vann.core.event;

class NetworkLayerInputListenerAdapter implements EventListenerAdapter {
	private final NetworkLayerInputListener listener;

	public NetworkLayerInputListenerAdapter(NetworkLayerInputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkLayerInputEvent.class.cast(event));
	}

	private void onEvent(NetworkLayerInputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
