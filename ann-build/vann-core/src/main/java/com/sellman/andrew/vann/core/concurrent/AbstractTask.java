package com.sellman.andrew.vann.core.concurrent;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractTask implements Runnable, Task {
	private CountDownLatch taskGroup;

	public final void setTaskGroup(final CountDownLatch taskGroup) {
		this.taskGroup = taskGroup;
	}

	public final CountDownLatch getTaskGroup() {
		return taskGroup;
	}

	@Override
	public final void run() {
		try {
			execute();
		} finally {
			if (taskGroup != null) {
				taskGroup.countDown();
			}
		}
	}
	
	public void recycle() {
		this.taskGroup = null;
	}

}
