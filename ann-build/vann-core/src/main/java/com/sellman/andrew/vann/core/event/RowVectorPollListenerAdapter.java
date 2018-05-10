package com.sellman.andrew.vann.core.event;

class RowVectorPollListenerAdapter implements EventListenerAdapter {
	private final RowVectorPollListener listener;

	public RowVectorPollListenerAdapter(RowVectorPollListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(RowVectorPollEvent.class.cast(event));
	}

	private void onEvent(RowVectorPollEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
