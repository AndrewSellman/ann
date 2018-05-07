package com.sellman.andrew.vann.core.event;

class NetworkInputListenerAdapter implements EventListenerAdapter {
	private final NetworkInputListener listener;

	public NetworkInputListenerAdapter(NetworkInputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkInputEvent.class.cast(event));
	}

	private void onEvent(NetworkInputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
