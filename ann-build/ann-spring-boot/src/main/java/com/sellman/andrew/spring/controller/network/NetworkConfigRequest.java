package com.sellman.andrew.spring.controller.network;

import java.util.ArrayList;
import java.util.List;

import com.sellman.andrew.ann.core.math.FunctionType;

public class NetworkConfigRequest {
	private int inputAttributeCount;
	private int outputAttributeCount;
	private List<Integer> hiddenLayerAttributeCounts;
	private FunctionType functionType;

	public NetworkConfigRequest() {
		setHiddenLayerAttributeCounts(new ArrayList<>());
	}

	public int getInputAttributeCount() {
		return inputAttributeCount;
	}

	public void setInputAttributeCount(int inputAttributeCount) {
		this.inputAttributeCount = inputAttributeCount;
	}

	public int getOutputAttributeCount() {
		return outputAttributeCount;
	}

	public void setOutputttributeCount(int outputAttributeCount) {
		this.outputAttributeCount = outputAttributeCount;
	}

	public FunctionType getFunctionType() {
		return functionType;
	}

	public void setFunctionType(FunctionType functionType) {
		this.functionType = functionType;
	}

	public List<Integer> getHiddenLayerAttributeCounts() {
		return hiddenLayerAttributeCounts;
	}

	public void setHiddenLayerAttributeCounts(List<Integer> hiddenLayerAttributeCounts) {
		this.hiddenLayerAttributeCounts = hiddenLayerAttributeCounts;
	}

}
