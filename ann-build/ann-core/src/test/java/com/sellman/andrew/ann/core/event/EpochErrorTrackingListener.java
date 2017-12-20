package com.sellman.andrew.ann.core.event;

import java.util.List;

public class EpochErrorTrackingListener extends Listener<EpochErrorChangeEvent> {
	private final List<Double> epochErrors;
	
	public EpochErrorTrackingListener(List<Double> epochErrors) {
		this.epochErrors = epochErrors;
	}

	@Override
	public void onEvent(final EpochErrorChangeEvent event) {
		epochErrors.add(event.getNewError());
	}

}
