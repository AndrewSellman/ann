package com.sellman.andrew.ann.core.event;

public abstract class Listener<T extends Event> {
	
	public abstract void onEvent(T event);

}
