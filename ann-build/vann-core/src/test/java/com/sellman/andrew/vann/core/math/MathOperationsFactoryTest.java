package com.sellman.andrew.vann.core.math;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sellman.andrew.vann.core.math.MathOperations;
import com.sellman.andrew.vann.core.math.MathOperationsFactory;
import com.sellman.andrew.vann.core.math.add.AdditionFactory;
import com.sellman.andrew.vann.core.math.multiply.HadamardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.multiply.StandardMultiplicationFactory;
import com.sellman.andrew.vann.core.math.scale.ScalerFactory;
import com.sellman.andrew.vann.core.math.subtract.SubtractionFactory;
import com.sellman.andrew.vann.core.math.sum.SummationFactory;
import com.sellman.andrew.vann.core.math.transpose.TranspositionFactory;
import com.sellman.andrew.vann.core.math.update.UpdationFactory;

@RunWith(MockitoJUnitRunner.class)
public class MathOperationsFactoryTest {

	private MathOperationsFactory mathOperationsFactory;

	@Mock
	private AdditionFactory additionFactory;

	@Mock
	private SummationFactory summationFactory;

	@Mock
	private SubtractionFactory subtractionFactory;

	@Mock
	private ScalerFactory scalerFactory;

	@Mock
	private StandardMultiplicationFactory standardMultiplicationFactory;

	@Mock
	private HadamardMultiplicationFactory hadamardMultiplicationFactory;

	@Mock
	private TranspositionFactory transpositionFactory;

	@Mock
	private UpdationFactory updationFactory;

	@Before
	public void prepareTest() {
		mathOperationsFactory = new MathOperationsFactory(additionFactory, summationFactory, subtractionFactory, scalerFactory, transpositionFactory, standardMultiplicationFactory, hadamardMultiplicationFactory, updationFactory);
	}

	@Test
	public void test() {
		MathOperations mathOps = mathOperationsFactory.getOperations();
		assertNotNull(mathOps);
	}

}
