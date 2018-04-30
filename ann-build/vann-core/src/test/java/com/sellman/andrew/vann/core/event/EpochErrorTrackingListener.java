package com.sellman.andrew.vann.core.event;

import java.util.List;

import com.sellman.andrew.vann.core.event.EpochErrorChangeEvent;
import com.sellman.andrew.vann.core.event.Listener;

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
