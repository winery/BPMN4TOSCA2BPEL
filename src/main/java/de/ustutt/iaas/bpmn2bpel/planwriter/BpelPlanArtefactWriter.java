package de.ustutt.iaas.bpmn2bpel.planwriter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import de.ustutt.iaas.bpmn2bpel.model.Link;
import de.ustutt.iaas.bpmn2bpel.model.ManagementFlow;
import de.ustutt.iaas.bpmn2bpel.model.ManagementTask;
import de.ustutt.iaas.bpmn2bpel.model.Node;

public class BpelPlanArtefactWriter {
	
	private ManagementFlow mangagementFlow;
	
	public static String TEMPLATE_PATH = "./src/main/resources/templates/";
	
	public BpelPlanArtefactWriter(ManagementFlow mangagementFlow) {
		this.mangagementFlow = mangagementFlow;
		Velocity.init();
	}
	
	
	public String completePlanTemplate() {
		
		/* Traverse  the management flow and add the management tasks in the order of their execution to a list */
		List<ManagementTask> managementTaskSeq = new ArrayList<ManagementTask>();
		GraphIterator<Node, Link> iterator = new DepthFirstIterator<Node, Link>(mangagementFlow);
		while (iterator.hasNext()) {
			Node node = iterator.next();
			/* In this version the templates do only support management tasks */
			if (node instanceof ManagementTask) {
				/* Wrapper adds convenience functions that can be accessed from the Velocity template */
				ManagementTaskTemplateWrapper taskWrapper = new ManagementTaskTemplateWrapper((ManagementTask) node); //TODO move to factory and remove setters from constructor
				managementTaskSeq.add(taskWrapper);
			}
		}
		
		
		VelocityContext context = new VelocityContext();
		Template planTemplate = Velocity.getTemplate(TEMPLATE_PATH + "bpel_management_plan_template.xml");
		context.put("mngmtTaskList", managementTaskSeq);
		StringWriter planWriter = new StringWriter();
		planTemplate.merge( context, planWriter );
		
		return planWriter.toString();
		
	}
	
	public String completePlanWsdlTemplate() {
		VelocityContext context = new VelocityContext();
		Template wsdlTemplate = Velocity.getTemplate(TEMPLATE_PATH + "management_plan_wsdl_template.xml");
		
		StringWriter wsdlWriter = new StringWriter();
		wsdlTemplate.merge( context, wsdlWriter );
		
		return wsdlWriter.toString();
	}
	
	public String completeInvokerWsdlTemplate() {
		VelocityContext context = new VelocityContext();
		Template invokerWsdlTemplate = Velocity.getTemplate(TEMPLATE_PATH + "invoker.wsdl");
		
		StringWriter wsdlWriter = new StringWriter();
		invokerWsdlTemplate.merge( context, wsdlWriter );
		
		return wsdlWriter.toString();
	}
	
	public String completeInvokerXsdTemplate() {
		VelocityContext context = new VelocityContext();
		Template invokerXsdTemplate = Velocity.getTemplate(TEMPLATE_PATH + "invoker.xsd");
		
		StringWriter xsdWriter = new StringWriter();
		invokerXsdTemplate.merge( context, xsdWriter );
		
		return xsdWriter.toString();
	}
	
	public String completeDeploymentDescriptorTemplate() {
		VelocityContext context = new VelocityContext();
		Template invokerXsdTemplate = Velocity.getTemplate(TEMPLATE_PATH + "deploy.xml");
		
		StringWriter xsdWriter = new StringWriter();
		invokerXsdTemplate.merge( context, xsdWriter );
		
		return xsdWriter.toString();
	}
	
	

}
