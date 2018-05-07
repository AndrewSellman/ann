package com.sellman.andrew.vann.core.event;

class EpochChangeListenerAdapter implements EventListenerAdapter {
	private final EpochChangeListener listener;

	public EpochChangeListenerAdapter(EpochChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(EpochChangeEvent.class.cast(event));
	}

	private void onEvent(EpochChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
