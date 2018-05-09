package com.sellman.andrew.vann.core.event;

class NetworkLayerBiasedWeightedInputListenerAdapter implements EventListenerAdapter {
	private final NetworkLayerBiasedWeightedInputListener listener;

	public NetworkLayerBiasedWeightedInputListenerAdapter(NetworkLayerBiasedWeightedInputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkLayerBiasedWeightedInputEvent.class.cast(event));
	}

	private void onEvent(NetworkLayerBiasedWeightedInputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
