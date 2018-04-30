package com.sellman.andrew.vann.core.math.multiply;

import com.sellman.andrew.vann.core.concurrent.TaskService;
import com.sellman.andrew.vann.core.math.Matrix;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.vann.core.math.advice.AdvisableParallelizableOperation1Experiment;
import com.sellman.andrew.vann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationFactory;

public class StandardMultiplicationExperimentITCase extends AdvisableParallelizableOperation1Experiment {

	@Override
	protected AdvisableParallelizabeOperation1Factory getOpFactory(TaskService taskService, ParallelizableOperation1Advisor advisor) {
		return new StandardMultiplicationFactory(taskService, advisor);
	}

	@Override
	protected Matrix getNewMatrixB(int rowCount, int columnCount) {
		return new Matrix(columnCount, rowCount);
	}

}
