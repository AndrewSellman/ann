package com.sellman.andrew.ann.core.math.multiply;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.Matrix;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1Experiment;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;
import com.sellman.andrew.ann.core.math.factory.AdvisableParallelizabeOperation1Factory;

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
