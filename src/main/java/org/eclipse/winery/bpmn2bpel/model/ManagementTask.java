package org.eclipse.winery.bpmn2bpel.model;

import javax.xml.namespace.QName;

/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 *
 * @author Sebastian Wagner
 *
 */
public class ManagementTask extends Task {

	private String interfaceName;;

	private QName nodeTemplateId;

	private String nodeOperation;

	public QName getNodeTemplateId() {
		return nodeTemplateId;
	}

	public void setNodeTemplateId(QName nodeTemplateId) {
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
