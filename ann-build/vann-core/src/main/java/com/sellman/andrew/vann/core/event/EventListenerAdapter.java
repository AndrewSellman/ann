package com.sellman.andrew.vann.core.event;

interface EventListenerAdapter {

	public void onEvent(Event event);
	
	public Class<? extends Listener> getListenerClass();

}
