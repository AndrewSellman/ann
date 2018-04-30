package com.sellman.andrew.vann.core.concurrent;

import java.util.List;

public interface TaskService extends AutoCloseable {

	public void runTask(AbstractTask task);

	public void runTasks(List<? extends AbstractTask> tasks);

}
