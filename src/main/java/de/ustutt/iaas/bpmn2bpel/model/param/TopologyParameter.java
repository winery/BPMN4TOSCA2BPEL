package de.ustutt.iaas.bpmn2bpel.model.param;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * Represents a topology parameter.<br>
 * To initialize the fields nodeType and property accordingly, the expected value parameter format is <code>$NodeTypeName.$PropertyName</code>, 
 * e.g. <code>UbuntuVM.ImageName</code>
 *  
 * @author Sebastian Wagner
 *
 */
public class TopologyParameter extends Parameter {

	private String nodeTemplate;
	
	private String property;
	
		public String getNodeTemplate() {
		return nodeTemplate;
	}

	public void setNodeTemplate(String id) {
		this.nodeTemplate = id;
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
		/* Decompose String into NodeTemplate name and property name, e.g. value "UbuntuVM.ImageID" will be decomposed in   */
		String nodeTypeProperty[] = value.split("\\.", 0);
		
		if (nodeTypeProperty.length != 2) {
			throw new RuntimeException(TopologyParameter.class + " expects value in format '$NodeTypeName.$PropertyName' but invalid value " + value +  " was provided.");
		}
		
		setNodeTemplate(nodeTypeProperty[0]);
		setProperty(nodeTypeProperty[1]);
		super.setValue(value);
		
	}
}
