package com.sellman.andrew.vann.core.event;

public class RowVectorPollListenerAdapterFactory extends AbstractEventListenerAdapterFactory<RowVectorPollEvent, RowVectorPollListener, RowVectorPollListenerAdapter> {

	public RowVectorPollListenerAdapterFactory() {
		super(RowVectorPollEvent.class, RowVectorPollListener.class, RowVectorPollListenerAdapter.class);
	}

	@Override
	protected RowVectorPollListenerAdapter create(Listener listener) {
		return new RowVectorPollListenerAdapter(getCompatibleListener(listener));
	}

}
