package com.sellman.andrew.vann.core.event;

class BatchIndexChangeListenerAdapter implements EventListenerAdapter {
	private final BatchIndexChangeListener listener;

	public BatchIndexChangeListenerAdapter(BatchIndexChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(BatchIndexChangeEvent.class.cast(event));
	}

	private void onEvent(BatchIndexChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
