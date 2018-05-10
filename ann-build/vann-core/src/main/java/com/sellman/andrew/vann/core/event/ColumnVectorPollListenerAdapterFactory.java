package com.sellman.andrew.vann.core.event;

public class ColumnVectorPollListenerAdapterFactory extends AbstractEventListenerAdapterFactory<ColumnVectorPollEvent, ColumnVectorPollListener, ColumnVectorPollListenerAdapter> {

	public ColumnVectorPollListenerAdapterFactory() {
		super(ColumnVectorPollEvent.class, ColumnVectorPollListener.class, ColumnVectorPollListenerAdapter.class);
	}

	@Override
	protected ColumnVectorPollListenerAdapter create(Listener listener) {
		return new ColumnVectorPollListenerAdapter(getCompatibleListener(listener));
	}

}
