package com.sellman.andrew.vann.core.event;

class ResetBatchErrorListenerAdapter implements EventListenerAdapter {
	private final ResetBatchErrorListener listener;

	public ResetBatchErrorListenerAdapter(ResetBatchErrorListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(ResetBatchErrorEvent.class.cast(event));
	}

	private void onEvent(ResetBatchErrorEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
