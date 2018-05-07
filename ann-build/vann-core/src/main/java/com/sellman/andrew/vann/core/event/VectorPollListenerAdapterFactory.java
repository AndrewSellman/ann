package com.sellman.andrew.vann.core.event;

public class VectorPollListenerAdapterFactory extends AbstractEventListenerAdapterFactory<VectorPollEvent, VectorPollListener, VectorPollListenerAdapter> {

	public VectorPollListenerAdapterFactory() {
		super(VectorPollEvent.class, VectorPollListener.class, VectorPollListenerAdapter.class);
	}

	@Override
	protected VectorPollListenerAdapter create(Listener listener) {
		return new VectorPollListenerAdapter(getCompatibleListener(listener));
	}

}
