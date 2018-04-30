package com.sellman.andrew.ann.core.math.add;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizabeOperation1Factory;
import com.sellman.andrew.ann.core.math.advice.AdvisableParallelizableOperation1Experiment;
import com.sellman.andrew.ann.core.math.advice.ParallelizableOperation1Advisor;

public class AdditionExperimentITCase extends AdvisableParallelizableOperation1Experiment {

	@Override
	protected AdvisableParallelizabeOperation1Factory getOpFactory(TaskService taskService, ParallelizableOperation1Advisor advisor) {
		return new AdditionFactory(taskService, advisor);
	}

}
