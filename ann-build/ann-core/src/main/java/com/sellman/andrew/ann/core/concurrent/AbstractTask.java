package com.sellman.andrew.ann.core.concurrent;

import java.util.concurrent.Callable;

public abstract class AbstractTask implements Callable<Void>, Task {
	
	public final Void call() throws Exception {
		execute();
		return null;
	}

}
