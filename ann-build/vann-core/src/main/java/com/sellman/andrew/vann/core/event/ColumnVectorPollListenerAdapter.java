package com.sellman.andrew.vann.core.event;

class ColumnVectorPollListenerAdapter implements EventListenerAdapter {
	private final ColumnVectorPollListener listener;

	public ColumnVectorPollListenerAdapter(ColumnVectorPollListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(ColumnVectorPollEvent.class.cast(event));
	}

	private void onEvent(ColumnVectorPollEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
