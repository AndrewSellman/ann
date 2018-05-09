package com.sellman.andrew.vann.core.event;

public class EpochIndexChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<EpochIndexChangeEvent, EpochIndexChangeListener, EpochIndexChangeListenerAdapter> {

	public EpochIndexChangeListenerAdapterFactory() {
		super(EpochIndexChangeEvent.class, EpochIndexChangeListener.class, EpochIndexChangeListenerAdapter.class);
	}

	@Override
	protected EpochIndexChangeListenerAdapter create(Listener listener) {
		return new EpochIndexChangeListenerAdapter(getCompatibleListener(listener));
	}

}
