package org.eclipse.winery.bpmn2bpel.model.param;


/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 *
 * @author Sebastian Wagner
 *
 */
public class PlanParameter extends Parameter {

	private String startTaskName;

	private String parameterName;


	public String getStartTaskName() {
		return startTaskName;
	}

	public void setStartTaskName(String startTaskName) {
		this.startTaskName = startTaskName;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}


	@Override
	public ParamType getType() {
		return ParamType.PLAN;
	}

}
