package com.sellman.andrew.vann.core.event;

class EpochErrorChangeListenerAdapter implements EventListenerAdapter {
	private final EpochErrorChangeListener listener;

	public EpochErrorChangeListenerAdapter(EpochErrorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(EpochErrorChangeEvent.class.cast(event));
	}

	private void onEvent(EpochErrorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
