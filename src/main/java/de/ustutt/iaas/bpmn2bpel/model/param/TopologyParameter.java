package de.ustutt.iaas.bpmn2bpel.model.param;

import javax.xml.namespace.QName;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * Represents a topology parameter.<br>
 * To initialize the fields nodeType and property accordingly, the expected
 * value parameter format is <code>$NodeTypeName.$PropertyName</code>, e.g.
 * <code>UbuntuVM.ImageName</code>
 * 
 * @author Sebastian Wagner
 *		
 */
public class TopologyParameter extends Parameter {
	
	private QName nodeTemplateId;
	
	private String property;
	
	
	public QName getNodeTemplateId() {
		return nodeTemplateId;
	}
	
	/**
	 * Set the node template id
	 * @param name - the node template id with template prefix. 
	 */
	public void setNodeTemplateId(QName name) {
		this.nodeTemplateId = name;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String name) {
		this.property = name;
	}
	
	@Override
	public ParamType getType() {
		return ParamType.TOPOLOGY;
	}
	
	@Override
	public void setValue(String value) {
		/*
		 * Decompose String into fully qualified NodeTemplate name and property
		 * name, e.g. value "{http://example.com}UbuntuVM.ImageID" will be
		 * decomposed into {http://example.com}UbuntuVM and ImageId
		 */
		int idx = value.lastIndexOf(".");
		
		if (idx == -1) {
			throw new RuntimeException(TopologyParameter.class + " expects value in format '$QualifiedNodeTypeName.$PropertyName' but invalid value " + value + " was provided.");
		}
		
		String nodeTemplateName = value.substring(0, idx);
		String properyName = value.substring(idx + 1);
		 
		setNodeTemplateId(QName.valueOf(nodeTemplateName));
		setProperty(properyName);
		super.setValue(value);
		
	}
}
