package com.sellman.andrew.vann.core.event;

class MatrixPollListenerAdapter implements EventListenerAdapter {
	private final MatrixPollListener listener;

	public MatrixPollListenerAdapter(MatrixPollListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(MatrixPollEvent.class.cast(event));
	}

	private void onEvent(MatrixPollEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
