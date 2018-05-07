package com.sellman.andrew.vann.core.event;

class BatchChangeListenerAdapter implements EventListenerAdapter {
	private final BatchChangeListener listener;

	public BatchChangeListenerAdapter(BatchChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(BatchChangeEvent.class.cast(event));
	}

	private void onEvent(BatchChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
