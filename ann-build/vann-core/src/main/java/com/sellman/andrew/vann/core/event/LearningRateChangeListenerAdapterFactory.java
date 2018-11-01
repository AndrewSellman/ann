package com.sellman.andrew.vann.core.event;

public class LearningRateChangeListenerAdapterFactory extends AbstractEventListenerAdapterFactory<LearningRateChangeEvent, LearningRateChangeListener, LearningRateChangeListenerAdapter> {

	public LearningRateChangeListenerAdapterFactory() {
		super(LearningRateChangeEvent.class, LearningRateChangeListener.class, LearningRateChangeListenerAdapter.class);
	}

	@Override
	protected LearningRateChangeListenerAdapter create(Listener listener) {
		return new LearningRateChangeListenerAdapter(getCompatibleListener(listener));
	}

}
