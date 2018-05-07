package com.sellman.andrew.vann.core.event;

class VectorChangeListenerAdapter implements EventListenerAdapter {
	private final VectorChangeListener listener;

	public VectorChangeListenerAdapter(VectorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(VectorChangeEvent.class.cast(event));
	}

	private void onEvent(VectorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
