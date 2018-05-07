package com.sellman.andrew.vann.core.event;

class NetworkOutputListenerAdapter implements EventListenerAdapter {
	private final NetworkOutputListener listener;

	public NetworkOutputListenerAdapter(NetworkOutputListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(NetworkOutputEvent.class.cast(event));
	}

	private void onEvent(NetworkOutputEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
