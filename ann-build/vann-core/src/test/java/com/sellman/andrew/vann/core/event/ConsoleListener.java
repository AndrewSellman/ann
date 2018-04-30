package com.sellman.andrew.vann.core.event;

import com.sellman.andrew.vann.core.event.Event;
import com.sellman.andrew.vann.core.event.Listener;

public class ConsoleListener<T extends Event> extends Listener<T> {

	@Override
	public void onEvent(T event) {
		System.out.println(event.toString());
	}

}
