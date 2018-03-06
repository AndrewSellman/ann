package com.sellman.andrew.ann.core.concurrent;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractTask implements Runnable, Task {
	private CountDownLatch taskGroup;

//TODO remove these constructors after all operations changed to use task pools
	public AbstractTask(final CountDownLatch taskGroup) {
		this.taskGroup = taskGroup;
	}

	public AbstractTask() {
		this(null);
	}
	
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
