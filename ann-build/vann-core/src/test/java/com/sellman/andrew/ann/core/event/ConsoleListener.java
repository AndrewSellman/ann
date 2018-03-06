package com.sellman.andrew.ann.core.event;

public class ConsoleListener<T extends Event> extends Listener<T> {

	@Override
	public void onEvent(T event) {
		System.out.println(event.toString());
	}

}
