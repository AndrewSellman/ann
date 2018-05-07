package com.sellman.andrew.vann.core.event;

class BatchErrorChangeListenerAdapter implements EventListenerAdapter {
	private final BatchErrorChangeListener listener;

	public BatchErrorChangeListenerAdapter(BatchErrorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(BatchErrorChangeEvent.class.cast(event));
	}

	private void onEvent(BatchErrorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
