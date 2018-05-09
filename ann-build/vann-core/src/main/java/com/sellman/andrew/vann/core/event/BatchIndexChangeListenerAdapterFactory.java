package com.sellman.andrew.vann.core.event;

public class BatchIndexChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<BatchIndexChangeEvent, BatchIndexChangeListener, BatchIndexChangeListenerAdapter> {

	public BatchIndexChangeListenerAdapterFactory() {
		super(BatchIndexChangeEvent.class, BatchIndexChangeListener.class, BatchIndexChangeListenerAdapter.class);
	}

	@Override
	protected BatchIndexChangeListenerAdapter create(Listener listener) {
		return new BatchIndexChangeListenerAdapter(getCompatibleListener(listener));
	}

}
