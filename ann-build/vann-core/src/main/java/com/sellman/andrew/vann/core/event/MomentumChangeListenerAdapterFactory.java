package com.sellman.andrew.vann.core.event;

public class MomentumChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<MomentumChangeEvent, MomentumChangeListener, MomentumChangeListenerAdapter> {

	public MomentumChangeListenerAdapterFactory() {
		super(MomentumChangeEvent.class, MomentumChangeListener.class, MomentumChangeListenerAdapter.class);
	}

	@Override
	protected MomentumChangeListenerAdapter create(Listener listener) {
		return new MomentumChangeListenerAdapter(getCompatibleListener(listener));
	}

}
