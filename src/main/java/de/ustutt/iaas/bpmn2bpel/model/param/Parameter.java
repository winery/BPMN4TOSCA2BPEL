package de.ustutt.iaas.bpmn2bpel.model.param;

public abstract class Parameter {
	
	private String name;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract ParamType getType();

}
