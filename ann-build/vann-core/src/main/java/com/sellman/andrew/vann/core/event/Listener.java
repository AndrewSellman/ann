package com.sellman.andrew.vann.core.event;

public abstract class Listener<T extends Event> {
	
	public abstract void onEvent(T event);

}
