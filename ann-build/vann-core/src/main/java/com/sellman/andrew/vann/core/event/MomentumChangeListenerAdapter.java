package com.sellman.andrew.vann.core.event;

class MomentumChangeListenerAdapter implements EventListenerAdapter {
	private final MomentumChangeListener listener;

	public MomentumChangeListenerAdapter(MomentumChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(MomentumChangeEvent.class.cast(event));
	}

	private void onEvent(MomentumChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
