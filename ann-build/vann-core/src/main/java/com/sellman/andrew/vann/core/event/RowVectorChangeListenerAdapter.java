package com.sellman.andrew.vann.core.event;

class RowVectorChangeListenerAdapter implements EventListenerAdapter {
	private final RowVectorChangeListener listener;

	public RowVectorChangeListenerAdapter(RowVectorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(RowVectorChangeEvent.class.cast(event));
	}

	private void onEvent(RowVectorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
