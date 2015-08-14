package de.ustutt.iaas.bpmn2bpel;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import de.ustutt.iaas.bpmn2bpel.model.EndTask;
import de.ustutt.iaas.bpmn2bpel.model.ManagementFlow;
import de.ustutt.iaas.bpmn2bpel.model.ManagementTask;
import de.ustutt.iaas.bpmn2bpel.model.StartTask;
import de.ustutt.iaas.bpmn2bpel.model.param.ConcatParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.DeploymentArtefactParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.ImplementationArtefactParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.ParamType;
import de.ustutt.iaas.bpmn2bpel.model.param.Parameter;
import de.ustutt.iaas.bpmn2bpel.model.param.PlanParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.StringParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.TopologyParameter;
import de.ustutt.iaas.bpmn2bpel.parser.BPMN4JsonParser;
import de.ustutt.iaas.bpmn2bpel.parser.JsonKeys;
import de.ustutt.iaas.bpmn2bpel.parser.ParseException;

public class JsonParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() throws MalformedURLException, ParseException {
		BPMN4JsonParser parser = new BPMN4JsonParser();
		URL url = new URL("file:c:/temp/bpmn4tosca/bppmn4tosca.json");
		//Path testBpmn4JsonFile = Paths.get("C:/temp/bpmn4tosca/bppmn4tosca.json");
		ManagementFlow actualFlow = parser.parse(url);
		ManagementFlow expectedFlow = createReferenceFlow();
		
		assertEquals(expectedFlow.vertexSet().size(), actualFlow.vertexSet().size());
		assertEquals(expectedFlow.edgeSet().size(), actualFlow.edgeSet().size());
		
		
		
		
	}
	
	private static ManagementFlow createReferenceFlow() {
		ManagementFlow flow = new ManagementFlow();
		
		StartTask startTask = new StartTask();
		startTask.setId("element6");
		startTask.setName("StartEvent");
		startTask.addOutput(createParameter("SSHUserInput", ParamType.TOPOLOGY, ""));
		flow.addVertex(startTask);
		
		
		ManagementTask createEC2Task = new ManagementTask();
		createEC2Task.setId("element10");
		createEC2Task.setName("CreateVMAmazonEC2");
		createEC2Task.setNodeTemplate("AmazonEC2");
		createEC2Task.setNodeOperation("CreateVM");
		createEC2Task.addInput(createParameter("Size", ParamType.STRING ,"t1.medium"));
		createEC2Task.addInput(createParameter("SSHUser", ParamType.PLAN ,"StartEvent.SSHUserInput"));
		createEC2Task.addInput(createParameter("SSHKey", ParamType.STRING ,"myKey"));
		createEC2Task.addInput(createParameter("ImageID", ParamType.TOPOLOGY ,"UbuntuVM.ImageID"));
		createEC2Task.addInput(createParameter("AccountUser", ParamType.STRING ,""));
		createEC2Task.addInput(createParameter("AccountPassword", ParamType.STRING ,""));
		flow.addVertex(createEC2Task);
		
		ManagementTask runUbuntuTask = new ManagementTask();
		runUbuntuTask.setId("element38");
		runUbuntuTask.setName("runScriptUbuntuVM");
		runUbuntuTask.setNodeTemplate("UbuntuVM");
		runUbuntuTask.setNodeOperation("runScript");
		runUbuntuTask.addInput(createParameter("script", ParamType.IA ,"{http://www.opentosca.org}ApacheWebserverInstallImplementation"));
		runUbuntuTask.addOutput(createParameter("result", ParamType.TOPOLOGY ,""));
		flow.addVertex(runUbuntuTask);

		EndTask endTask = new EndTask();
		endTask.setId("element45");
		endTask.setName("EndEvent");
		endTask.addInput(createParameter("AppURL", ParamType.CONCAT ,"http://,UbuntuVM.IPAddress,:8080/,PHPApplication.ID"));
		flow.addVertex(endTask);
		
		
		flow.addEdge(startTask, createEC2Task);
		flow.addEdge(createEC2Task, runUbuntuTask);
		flow.addEdge(runUbuntuTask, endTask);
		
		return flow;
		
		
	}
	
	private static Parameter createParameter(String name, ParamType type, String value) {
		
		Parameter param = null;
		switch (type) {
		case CONCAT:
			param = new ConcatParameter(); // TODO add concat operands
			break;
		case DA:
			param = new DeploymentArtefactParameter();
			break;
		case IA:
			param = new ImplementationArtefactParameter();
			break;
		case PLAN:
			param = new PlanParameter(); // TODO add task name
			break;
		case STRING:
			param = new StringParameter();
			break;
		case TOPOLOGY:
			param = new TopologyParameter();
			break;
		default:
			fail("Invalid paramet type: " + type);
		}
		
		param.setName(name);
		param.setValue(value);
		
		return param;
	}

}
