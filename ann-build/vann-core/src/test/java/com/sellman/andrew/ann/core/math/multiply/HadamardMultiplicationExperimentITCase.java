package com.sellman.andrew.ann.core.math.multiply;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1Experiment;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;

public class HadamardMultiplicationExperimentITCase extends AdvisableParallelizableOperation1Experiment {

	@Override
	protected AdvisableParallelizabeOperation1Factory getOpFactory(TaskService taskService, ParallelizableOperation1Advisor advisor) {
		return new HadamardMultiplicationFactory(taskService, advisor);
	}

}
