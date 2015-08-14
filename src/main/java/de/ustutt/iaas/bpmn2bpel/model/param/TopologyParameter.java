package de.ustutt.iaas.bpmn2bpel.model.param;

public class TopologyParameter extends Parameter {
	
	private String nodeType;
	
	private String property;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	@Override
	public ParamType getType() {
		return ParamType.TOPOLOGY;
	}

}
