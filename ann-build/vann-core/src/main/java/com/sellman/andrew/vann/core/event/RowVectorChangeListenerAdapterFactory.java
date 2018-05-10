package com.sellman.andrew.vann.core.event;

public class RowVectorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<RowVectorChangeEvent, RowVectorChangeListener, RowVectorChangeListenerAdapter> {

	public RowVectorChangeListenerAdapterFactory() {
		super(RowVectorChangeEvent.class, RowVectorChangeListener.class, RowVectorChangeListenerAdapter.class);
	}

	@Override
	protected RowVectorChangeListenerAdapter create(Listener listener) {
		return new RowVectorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
