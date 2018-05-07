package com.sellman.andrew.vann.core.event;

class ValidationErrorChangeListenerAdapter implements EventListenerAdapter {
	private final ValidationErrorChangeListener listener;

	public ValidationErrorChangeListenerAdapter(ValidationErrorChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void onEvent(Event event) {
		onEvent(ValidationErrorChangeEvent.class.cast(event));
	}

	private void onEvent(ValidationErrorChangeEvent event) {
		listener.onEvent(event);
	}

	@Override
	public Class<? extends Listener> getListenerClass() {
		return listener.getClass();
	}

}
