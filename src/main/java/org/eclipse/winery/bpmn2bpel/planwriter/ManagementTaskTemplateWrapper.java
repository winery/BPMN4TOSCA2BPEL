package org.eclipse.winery.bpmn2bpel.planwriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.winery.bpmn2bpel.model.ManagementTask;
import org.eclipse.winery.bpmn2bpel.model.param.TopologyParameter;
import org.eclipse.winery.bpmn2bpel.model.param.Parameter;


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




	public List<QName> getInputNodeTemplateIds() {
		// Velocity does just support java lists in templates but not sets;
		return new ArrayList<QName>(getNodeTemplateIds(getInputParameters()));
	}



	public List<QName> getOutputNodeTemplateIds() {
		// Velocity does just support java lists in templates but not sets;
				return new ArrayList<QName>(getNodeTemplateIds(getOutputParameters()));
	}


	/**
	 * @return The set union of all ids of node templates used by the task.
	 */
	public List<QName> getAllNodeTemplateIds() {
		Set<QName> allNodeTemplates = getNodeTemplateIds(getInputParameters());
		allNodeTemplates.addAll(getNodeTemplateIds(getOutputParameters()));
		return new ArrayList<QName>(allNodeTemplates);
	}

	/**
	 * Helper that returns a set of ids of the node templates used by the parameters.
	 * @param parameters
	 * @return Set of node template ids as QNames
	 */
	private Set<QName> getNodeTemplateIds(List<Parameter> parameters) {
		Set<QName> nodeTemplateIds = new HashSet<QName>();

		Iterator<Parameter> iter = parameters.iterator();
		while (iter.hasNext()) {
			Parameter parameter = (Parameter) iter.next();
			/* Just topology parameter refer to node templates */
			if (parameter instanceof TopologyParameter) {
				nodeTemplateIds.add(((TopologyParameter) parameter).getNodeTemplateId());

			}

		}
		return nodeTemplateIds;

	}

}
