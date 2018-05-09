package com.sellman.andrew.vann.core.event;

class EpochIndexChangeListenerAdapter implements EventListenerAdapter {
	private final EpochIndexChangeListener listener;

	public EpochIndexChangeListenerAdapter(EpochIndexChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(EpochIndexChangeEvent.class.cast(event));
	}

	private void onEvent(EpochIndexChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
