package de.ustutt.iaas.bpmn2bpel.model.param;

public class StringParameter extends Parameter {
	
	@Override
	public ParamType getType() {
		return ParamType.STRING;
	}

}
