package com.sellman.andrew.vann.core.event;

public class VectorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<VectorChangeEvent, VectorChangeListener, VectorChangeListenerAdapter> {

	public VectorChangeListenerAdapterFactory() {
		super(VectorChangeEvent.class, VectorChangeListener.class, VectorChangeListenerAdapter.class);
	}

	@Override
	protected VectorChangeListenerAdapter create(Listener listener) {
		return new VectorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
