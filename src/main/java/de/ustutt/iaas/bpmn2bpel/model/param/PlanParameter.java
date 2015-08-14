package de.ustutt.iaas.bpmn2bpel.model.param;


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
