package com.sellman.andrew.ann.core.concurrent;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;

public abstract class TaskPool<T extends AbstractTask> implements AutoCloseable {
	private final GenericObjectPool<T> pool;

	public TaskPool(GenericObjectPool<T> pool) {
		this.pool = pool;
	}

	public List<T> borrow(int howManyTasks) throws Exception {
		List<T> borrowedTasks = getNewBorrowList(howManyTasks);
		for (int i = 0; i < howManyTasks; i++) {
			borrowedTasks.add(pool.borrowObject());
		}
		return borrowedTasks;
	}

	public final void recycle(List<T> tasks) {
		for (T task : tasks) {
			recycle(task);
		}
	}

	private void recycle(T task) {
		pool.returnObject(task);
	}

	@Override
	public void close() {
		System.out.println("Closing pool " + toString() + "...");
		pool.close();
		System.out.println("Closed pool " + toString());
	}

	public long getBorrowedCount() {
		return pool.getBorrowedCount();
	}

	public long getRecycledCount() {
		return pool.getReturnedCount();
	}

	protected abstract List<T> getNewBorrowList(int howManyTasks);

}
