package com.sellman.andrew.vann.core.event;

class VectorPollListenerAdapter implements EventListenerAdapter {
	private final VectorPollListener listener;

	public VectorPollListenerAdapter(VectorPollListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(VectorPollEvent.class.cast(event));
	}

	private void onEvent(VectorPollEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
