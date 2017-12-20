package com.sellman.andrew.ann.core.event;

public class DoNothingListener<T extends Event> extends Listener<T> {

	@Override
	public void onEvent(T event) {
		return;
	}

}
