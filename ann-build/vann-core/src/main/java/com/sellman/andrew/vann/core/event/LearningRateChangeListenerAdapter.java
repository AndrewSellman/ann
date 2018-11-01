package com.sellman.andrew.vann.core.event;

class LearningRateChangeListenerAdapter implements EventListenerAdapter {
	private final LearningRateChangeListener listener;

	public LearningRateChangeListenerAdapter(LearningRateChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(LearningRateChangeEvent.class.cast(event));
	}

	private void onEvent(LearningRateChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
