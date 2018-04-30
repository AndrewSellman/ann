package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.Listener;

public class DoNothingListener<T extends Event> extends Listener<T> {

	@Override
	public void onEvent(T event) {
		return;
	}

}
