package com.sellman.andrew.ann.core.math;

import com.sellman.andrew.ann.core.concurrent.TaskService;
import com.sellman.andrew.ann.core.math.add.AdditionFactory;
import com.sellman.andrew.ann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.ann.core.math.scale.ScalerFactory;
import com.sellman.andrew.ann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.ann.core.math.sum.SummationFactory;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByColumnTask;
import com.sellman.andrew.ann.core.math.task.AbstractOperationByRowTask;
import com.sellman.andrew.ann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.ann.core.math.update.UpdationFactory;

public class MathOperationsFactory {
	private final AdditionFactory additionFactory;
	private final SummationFactory summationFactory;
	private final SubtractionFactory subtractionFactory;
	private final ScalerFactory scalerFactory;
	private final StandardMultiplicationFactory standardMultiplicationFactory;
	private final HadamardMultiplicationFactory hadamardMultiplicationFactory;
	private final TranspositionFactory transpositionFactory;
	private final UpdationFactory updationFactory;

	public MathOperationsFactory(final AdditionFactory additionFactory,
			final SummationFactory summationFactory,
			final SubtractionFactory subtractionFactory, 
			final ScalerFactory scalerFactory,
			final TranspositionFactory transpositionFactory,
			final StandardMultiplicationFactory standardMultiplicationFactory,
			final HadamardMultiplicationFactory hadamardMultiplicationFactory, 
			final UpdationFactory updationFactory) {

		this.additionFactory = additionFactory;
		this.summationFactory = summationFactory;
		this.subtractionFactory = subtractionFactory;
		this.scalerFactory = scalerFactory;
		this.transpositionFactory = transpositionFactory;
		this.standardMultiplicationFactory = standardMultiplicationFactory;
		this.hadamardMultiplicationFactory = hadamardMultiplicationFactory;
		this.updationFactory = updationFactory;
	}

	public MathOperations getOperations(final TaskService taskService) {
		ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> standardMultiplier = getStandardMultiplication();
		ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> hadamardMultiplier = getHadamardMultiplication();
		ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> addition = getAddition();
		ParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> summation = getSummation();
		ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> subtraction = getSubtraction();
		ParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> scaler = getScaler();
		ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> transposition = getTransposition();
		ParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> updation = getUpdation();
		return new MathOperationsImpl(standardMultiplier, hadamardMultiplier, addition, summation, subtraction, scaler, transposition, updation);
	}

	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getAddition() {
		return additionFactory.getOperation();
	}

	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getSubtraction() {
		return subtractionFactory.getOperation();
	}

	private ParallelizableOperation2<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getScaler() {
		return scalerFactory.getOperation();
	}

	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getStandardMultiplication() {
		return standardMultiplicationFactory.getOperation();
	}

	private ParallelizableOperation1<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getHadamardMultiplication() {
		return hadamardMultiplicationFactory.getOperation();
	}

	private ParallelizableOperation3<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getTransposition() {
		return transpositionFactory.getOperation();
	}

	private ParallelizableOperation4<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getSummation() {
		return summationFactory.getOperation();
	}

	private ParallelizableOperation5<? extends AbstractOperationByRowTask, ? extends AbstractOperationByColumnTask> getUpdation() {
		return updationFactory.getOperation();
	}

}
