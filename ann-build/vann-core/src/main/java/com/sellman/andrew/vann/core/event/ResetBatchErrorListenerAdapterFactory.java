package com.sellman.andrew.vann.core.event;

public class ResetBatchErrorListenerAdapterFactory extends AbstractEventListenerAdapterFactory<ResetBatchErrorEvent, ResetBatchErrorListener, ResetBatchErrorListenerAdapter> {

	public ResetBatchErrorListenerAdapterFactory() {
		super(ResetBatchErrorEvent.class, ResetBatchErrorListener.class, ResetBatchErrorListenerAdapter.class);
	}

	@Override
	protected ResetBatchErrorListenerAdapter create(Listener listener) {
		return new ResetBatchErrorListenerAdapter(getCompatibleListener(listener));
	}

}
