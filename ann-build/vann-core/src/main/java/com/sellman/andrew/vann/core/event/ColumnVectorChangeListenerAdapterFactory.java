package com.sellman.andrew.vann.core.event;

public class ColumnVectorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<ColumnVectorChangeEvent, ColumnVectorChangeListener, ColumnVectorChangeListenerAdapter> {

	public ColumnVectorChangeListenerAdapterFactory() {
		super(ColumnVectorChangeEvent.class, ColumnVectorChangeListener.class, ColumnVectorChangeListenerAdapter.class);
	}

	@Override
	protected ColumnVectorChangeListenerAdapter create(Listener listener) {
		return new ColumnVectorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
