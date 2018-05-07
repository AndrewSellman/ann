package com.sellman.andrew.vann.core.event;

public class EpochChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<EpochChangeEvent, EpochChangeListener, EpochChangeListenerAdapter> {

	public EpochChangeListenerAdapterFactory() {
		super(EpochChangeEvent.class, EpochChangeListener.class, EpochChangeListenerAdapter.class);
	}

	@Override
	protected EpochChangeListenerAdapter create(Listener listener) {
		return new EpochChangeListenerAdapter(getCompatibleListener(listener));
	}

}
