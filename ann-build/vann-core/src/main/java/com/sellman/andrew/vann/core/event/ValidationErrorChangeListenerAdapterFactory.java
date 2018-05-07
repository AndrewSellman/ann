package com.sellman.andrew.vann.core.event;

public class ValidationErrorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<ValidationErrorChangeEvent, ValidationErrorChangeListener, ValidationErrorChangeListenerAdapter> {

	public ValidationErrorChangeListenerAdapterFactory() {
		super(ValidationErrorChangeEvent.class, ValidationErrorChangeListener.class, ValidationErrorChangeListenerAdapter.class);
	}

	@Override
	protected ValidationErrorChangeListenerAdapter create(Listener listener) {
		return new ValidationErrorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
