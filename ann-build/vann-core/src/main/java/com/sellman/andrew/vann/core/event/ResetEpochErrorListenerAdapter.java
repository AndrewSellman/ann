package com.sellman.andrew.vann.core.event;

class ResetEpochErrorListenerAdapter implements EventListenerAdapter {
	private final ResetEpochErrorListener listener;

	public ResetEpochErrorListenerAdapter(ResetEpochErrorListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(ResetEpochErrorEvent.class.cast(event));
	}

	private void onEvent(ResetEpochErrorEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
