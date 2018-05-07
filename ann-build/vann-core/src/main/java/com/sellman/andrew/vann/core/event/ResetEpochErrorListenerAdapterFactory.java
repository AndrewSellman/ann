package com.sellman.andrew.vann.core.event;

public class ResetEpochErrorListenerAdapterFactory extends AbstractEventListenerAdapterFactory<ResetEpochErrorEvent, ResetEpochErrorListener, ResetEpochErrorListenerAdapter> {

	public ResetEpochErrorListenerAdapterFactory() {
		super(ResetEpochErrorEvent.class, ResetEpochErrorListener.class, ResetEpochErrorListenerAdapter.class);
	}

	@Override
	protected ResetEpochErrorListenerAdapter create(Listener listener) {
		return new ResetEpochErrorListenerAdapter(getCompatibleListener(listener));
	}

}
