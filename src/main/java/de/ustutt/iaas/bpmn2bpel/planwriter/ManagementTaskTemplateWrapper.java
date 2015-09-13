package de.ustutt.iaas.bpmn2bpel.planwriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.ustutt.iaas.bpmn2bpel.model.ManagementTask;
import de.ustutt.iaas.bpmn2bpel.model.param.Parameter;
import de.ustutt.iaas.bpmn2bpel.model.param.TopologyParameter;


/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * 
 * Add convenience methods to the {@link ManagementTask} that can be used 
 * in the Velocity template.
 * 
 * @author Sebastian Wagner
 *
 */
public class ManagementTaskTemplateWrapper extends ManagementTask {

	public ManagementTaskTemplateWrapper(ManagementTask task) {
		super();
		setId(task.getId());
		setName(task.getName());
		setInterfaceName(task.getInterfaceName());
		setNodeTemplateId(task.getNodeTemplateId());
		setNodeOperation(task.getNodeOperation());
		setInputParameters(task.getInputParameters());
		setOutputParameters(task.getOutputParameters());
	}
	
	
	
	
	public List<String> getInputNodeTemplateIds() {
		// Velocity does just support java lists in templates but not sets
		return new ArrayList<String>(getNodeTemplateIds(getInputParameters())); 
	}
	
	public List<String> getOutputNodeTemplateIds() {
		// Velocity does just support java lists in templates but not sets
		return new ArrayList<String>(getNodeTemplateIds(getOutputParameters())); 
	}
	
	/**
	 * @return The set union of all ids of node templates used by the task.
	 */
	public List<String> getAllNodeTemplateIds() {
		Set<String> allNodeTemplates = getNodeTemplateIds(getInputParameters());
		allNodeTemplates.addAll(getNodeTemplateIds(getOutputParameters()));
		return new ArrayList<String>(allNodeTemplates);
	}
	
	/**
	 * Returns a set of ids of the node templates used by the parameters.  
	 * @param parameters
	 * @return Set of node template ids
	 */
	private Set<String> getNodeTemplateIds(List<Parameter> parameters) {
		Set<String> nodeTemplateIds = new HashSet<String>();
		
		Iterator<Parameter> iter = getInputParameters().iterator();
		while (iter.hasNext()) {
			Parameter parameter = (Parameter) iter.next();
			/* Just topology parameter refer to node templates */
			if (parameter instanceof TopologyParameter) {
				nodeTemplateIds.add(((TopologyParameter) parameter).getNodeTemplate());
				
			}
			
		}
		return nodeTemplateIds;
	
	}

}
