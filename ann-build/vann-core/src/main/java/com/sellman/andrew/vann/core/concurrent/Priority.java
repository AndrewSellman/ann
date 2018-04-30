package com.sellman.andrew.vann.core.concurrent;

enum Priority {
	
	LOW("low", Thread.MIN_PRIORITY), 
	NORMAL("normal", Thread.NORM_PRIORITY), 
	HIGH("high", Thread.MAX_PRIORITY);
	
	private String description;
	private int threadPriority;

	private Priority(String description, int threadPriority) {
		this.description = description;
		this.threadPriority = threadPriority;
	}
	
	public String getDescription() {
		return description;
	}

	public int getThreadPriority() {
		return threadPriority;
	}
	
}
