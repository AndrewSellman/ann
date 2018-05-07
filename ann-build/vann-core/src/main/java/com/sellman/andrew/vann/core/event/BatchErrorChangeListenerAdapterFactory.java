package com.sellman.andrew.vann.core.event;

public class BatchErrorChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<BatchErrorChangeEvent, BatchErrorChangeListener, BatchErrorChangeListenerAdapter> {

	public BatchErrorChangeListenerAdapterFactory() {
		super(BatchErrorChangeEvent.class, BatchErrorChangeListener.class, BatchErrorChangeListenerAdapter.class);
	}

	@Override
	protected BatchErrorChangeListenerAdapter create(Listener listener) {
		return new BatchErrorChangeListenerAdapter(getCompatibleListener(listener));
	}

}
