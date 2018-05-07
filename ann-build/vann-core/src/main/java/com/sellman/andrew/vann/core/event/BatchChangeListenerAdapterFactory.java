package com.sellman.andrew.vann.core.event;

public class BatchChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<BatchChangeEvent, BatchChangeListener, BatchChangeListenerAdapter> {

	public BatchChangeListenerAdapterFactory() {
		super(BatchChangeEvent.class, BatchChangeListener.class, BatchChangeListenerAdapter.class);
	}

	@Override
	protected BatchChangeListenerAdapter create(Listener listener) {
		return new BatchChangeListenerAdapter(getCompatibleListener(listener));
	}

}
