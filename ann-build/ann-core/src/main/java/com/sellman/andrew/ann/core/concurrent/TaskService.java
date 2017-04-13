package com.sellman.andrew.ann.core.concurrent;

import java.util.Collection;

public interface TaskService extends AutoCloseable {

	public void runTask(AbstractTask task);

	public void runTasks(Collection<AbstractTask> tasks);

}
