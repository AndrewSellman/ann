package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;

public class MatrixOperationsFactory {

	public MatrixOperations getMatrixOperations(TaskService taskService) {
		MatrixMultiplier multiplier = new MatrixMultiplier(taskService);
		MatrixAdder adder = new MatrixAdder(taskService);
		MatrixSubtractor subtractor = new MatrixSubtractor(taskService);
		MatrixScaler scaler = new MatrixScaler(taskService);
		MatrixManipulator manipulator = new MatrixManipulator(taskService);
		return new MatrixOperationsImpl(multiplier, adder, subtractor, scaler, manipulator);
	}

}
