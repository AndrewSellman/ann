package com.sellman.andrew.vann.core.event;

class NetworkLayerWeightedInputListenerAdapter implements EventListenerAdapter {
	private final NetworkLayerWeightedInputListener listener;

	public NetworkLayerWeightedInputListenerAdapter(NetworkLayerWeightedInputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkLayerWeightedInputEvent.class.cast(event));
	}

	private void onEvent(NetworkLayerWeightedInputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
