package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MathOperationsFactory {

	public MathOperations getOperations(final TaskService taskService) {
		Multiplier multiplier = new Multiplier(taskService);
		Adder adder = new Adder(taskService);
		Subtractor subtractor = new Subtractor(taskService);
		Scaler scaler = new Scaler(taskService);
		Manipulator manipulator = new Manipulator(taskService);
		return new MathOperationsImpl(multiplier, adder, subtractor, scaler, manipulator);
	}

}
