package com.sellman.andrew.vann.core.event;

class MatrixChangeListenerAdapter implements EventListenerAdapter {
	private final MatrixChangeListener listener;

	public MatrixChangeListenerAdapter(MatrixChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(MatrixChangeEvent.class.cast(event));
	}

	private void onEvent(MatrixChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
