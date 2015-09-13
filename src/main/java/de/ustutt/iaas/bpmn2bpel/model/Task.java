package de.ustutt.iaas.bpmn2bpel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.ustutt.iaas.bpmn2bpel.model.param.Parameter;


public abstract class Task extends Node {
	
	private String name;
	
	private Map<String, Parameter> inputParams = new HashMap<String, Parameter>();
	
	private Map<String, Parameter> outputParams = new HashMap<String, Parameter>();
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addInputParameter(Parameter param) {
		inputParams.put(param.getName(), param);
	}
	
	public void setInputParameters(List<Parameter> inputParams) {
		Iterator<Parameter> iter = inputParams.iterator();
		while (iter.hasNext()) {
			Parameter param = (Parameter) iter.next();
			this.inputParams.put(param.getName(), param); 
			
		}
	}
	
	public Parameter getInputParameter(String name) {
		return inputParams.get(name);
	}
	
	public List<Parameter> getInputParameters() {
		return new ArrayList<Parameter>(inputParams.values());
	}
	
	public void addOutputParameter(Parameter param) {
		outputParams.put(param.getName(), param);
	}
	
	public Parameter getOutputParameter(String name) {
		return outputParams.get(name);
	}
	
	public List<Parameter> getOutputParameters() {
		return new ArrayList<Parameter>(outputParams.values());
	}
	
	public void setOutputParameters(List<Parameter> outputParams) {
		Iterator<Parameter> iter = outputParams.iterator();
		while (iter.hasNext()) {
			Parameter param = (Parameter) iter.next();
			this.outputParams.put(param.getName(), param); 
			
		}
	}



}
