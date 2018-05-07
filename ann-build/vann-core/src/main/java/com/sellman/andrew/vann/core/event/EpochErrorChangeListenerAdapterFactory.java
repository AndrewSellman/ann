package com.sellman.andrew.vann.core.event;

public class EpochErrorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<EpochErrorChangeEvent, EpochErrorChangeListener, EpochErrorChangeListenerAdapter> {

	public EpochErrorChangeListenerAdapterFactory() {
		super(EpochErrorChangeEvent.class, EpochErrorChangeListener.class, EpochErrorChangeListenerAdapter.class);
	}

	@Override
	protected EpochErrorChangeListenerAdapter create(Listener listener) {
		return new EpochErrorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
