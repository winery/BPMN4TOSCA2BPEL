package de.ustutt.iaas.bpmn2bpel.parser;

public interface JsonKeys {
	
	
	/*
	 * Field names of BPMN4Tosca Model
	 */
	
	public static final String NAME = "name";
	
	public static final String ID = "id";
	
	public static final String TYPE = "type";
	
	public static final String INPUT = "input";
	
	public static final String OUTPUT = "output";
	
	public static final String VALUE = "value";
	
	public static final String NODE_TEMPLATE = "node_template";
	
	public static final String NODE_OPERATION = "node_operation";
	
	public static final String NODE_INTERFACE_NAME = "node_interface";
	
	public static final String CONNECTIONS = "connections";
	
	
	/*
	 * Event, Management-Task Types 
	 * 
	 */
	public static final String TASK_TYPE_MGMT_TASK = "ToscaNodeManagementTask";
	
	public static final String TASK_TYPE_START_EVENT = "StartEvent";
	
	public static final String TASK_TYPE_END_EVENT = "EndEvent";
	
	
	/*
	 * Parameter Types
	 */
	public static final String PARAM_TYPE_VALUE_STRING = "string";
	
	public static final String PARAM_TYPE_VALUE_TOPOLOGY = "topology";
	
	public static final String PARAM_TYPE_VALUE_PLAN = "plan";
	
	public static final String PARAM_TYPE_VALUE_CONCAT = "concat";
	
	public static final String PARAM_TYPE_VALUE_IA = "implementation_artifact";
	
	public static final String PARAM_TYPE_VALUE_DA = "deployment_artifact";

}
