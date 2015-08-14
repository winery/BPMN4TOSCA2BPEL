package de.ustutt.iaas.bpmn2bpel.model;


public class ManagementTask extends Task {
	
	private String nodeTemplate;
	
	private String nodeOperation;

	public String getNodeTemplate() {
		return nodeTemplate;
	}

	public void setNodeTemplate(String nodeTemplate) {
		this.nodeTemplate = nodeTemplate;
	}

	public String getNodeOperation() {
		return nodeOperation;
	}

	public void setNodeOperation(String nodeOperation) {
		this.nodeOperation = nodeOperation;
	}

}
