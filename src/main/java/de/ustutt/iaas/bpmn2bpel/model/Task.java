package de.ustutt.iaas.bpmn2bpel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ustutt.iaas.bpmn2bpel.model.param.Parameter;



public abstract class Task extends Node {
	
	private String name;
	
	private String type;
	
	private Map<String, Parameter> inputParams = new HashMap<String, Parameter>();
	
	private Map<String, Parameter> outputParams = new HashMap<String, Parameter>();
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	
	public void addInput(Parameter param) {
		inputParams.put(param.getName(), param);
	}
	
	public Parameter getInput(String name) {
		return inputParams.get(name);
	}
	
	public List<Parameter> getInputs() {
		return new ArrayList<Parameter>(inputParams.values());
	}
	
	public void addOutput(Parameter param) {
		outputParams.put(param.getName(), param);
	}
	
	public Parameter getOutput(String name) {
		return outputParams.get(name);
	}
	
	public List<Parameter> getOutputs() {
		return new ArrayList<Parameter>(outputParams.values());
	}



}
