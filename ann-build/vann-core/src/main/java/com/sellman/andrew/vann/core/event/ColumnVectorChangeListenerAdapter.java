package com.sellman.andrew.vann.core.event;

class ColumnVectorChangeListenerAdapter implements EventListenerAdapter {
	private final ColumnVectorChangeListener listener;

	public ColumnVectorChangeListenerAdapter(ColumnVectorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(ColumnVectorChangeEvent.class.cast(event));
	}

	private void onEvent(ColumnVectorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
