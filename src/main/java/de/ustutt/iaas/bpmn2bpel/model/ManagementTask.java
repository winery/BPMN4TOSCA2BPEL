package de.ustutt.iaas.bpmn2bpel.model;


/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * 
 * @author Sebastian Wagner
 *
 */
public class ManagementTask extends Task {
	
	private String interfaceName;;
	
	private String nodeTemplateId;
	
	private String nodeOperation;

	public String getNodeTemplateId() {
		return nodeTemplateId;
	}

	public void setNodeTemplateId(String nodeTemplateId) {
		this.nodeTemplateId = nodeTemplateId;
	}

	public String getNodeOperation() {
		return nodeOperation;
	}
  
	public void setNodeOperation(String nodeOperation) {
		this.nodeOperation = nodeOperation;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

}
