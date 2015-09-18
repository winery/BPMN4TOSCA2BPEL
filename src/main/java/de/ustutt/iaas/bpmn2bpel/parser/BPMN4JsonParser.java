package de.ustutt.iaas.bpmn2bpel.parser;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.ustutt.iaas.bpmn2bpel.model.EndTask;
import de.ustutt.iaas.bpmn2bpel.model.ManagementFlow;
import de.ustutt.iaas.bpmn2bpel.model.ManagementTask;
import de.ustutt.iaas.bpmn2bpel.model.Node;
import de.ustutt.iaas.bpmn2bpel.model.StartTask;
import de.ustutt.iaas.bpmn2bpel.model.Task;
import de.ustutt.iaas.bpmn2bpel.model.param.ConcatParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.DeploymentArtefactParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.ImplementationArtefactParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.Parameter;
import de.ustutt.iaas.bpmn2bpel.model.param.PlanParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.StringParameter;
import de.ustutt.iaas.bpmn2bpel.model.param.TopologyParameter;

/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 * 
 * TODO describe expected JSON format here
 * 
 * @author Sebastian Wagner
 *
 */
public class BPMN4JsonParser extends Parser {

	private static Logger log = LoggerFactory.getLogger(BPMN4JsonParser.class);

	@Override
	public ManagementFlow parse(URI jsonFileUrl) throws ParseException {

		try {
			// general method, same as with data binding
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// (note: can also use more specific type, like ArrayNode or
			// ObjectNode!)
			JsonNode rootNode = mapper.readValue(jsonFileUrl.toURL(), JsonNode.class);

			String prettyPrintedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
			log.debug("Creating management flow from following Json model:" + prettyPrintedJson);
			
			ManagementFlow managementFlow = new ManagementFlow();
			/* Contains the ids (values) of the target nodes of a certain node
			 * (key is node id of this node) */
			Map<String, Set<String>> nodeWithTargetsMap = new HashMap<String, Set<String>>();

			/* Create model objects from Json nodes */
			log.debug("Creating node models...");
			Iterator<JsonNode> iter = rootNode.iterator();
			while (iter.hasNext()) {
				JsonNode jsonNode = (JsonNode) iter.next();

				/*
				 * As top level elements just start events, end events and
				 * management tasks expected which are transformed to tasks in
				 * our management model
				 */
				Task task = createTaskFromJson(jsonNode);
				/*
				 * Task may be null if it could not be created due to missing or
				 * incorrect fields/values in the Json node
				 */
				if (task != null) {
					managementFlow.addVertex(task);

					// TODO GATEWAAAAYYYYSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
					// !!!!!!!!!!!!!!!!!!

					/*
					 * To create the links later, relate the id of the created
					 * node with its direct successor nodes
					 */
					nodeWithTargetsMap.put(task.getId(), extractNodeTargetIds(jsonNode));
				} else {
					String ignoredJsonNode = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
					log.warn("No model element could be created from following node due to missing or invalid keys/values :" + ignoredJsonNode);
				}
			}

			/* 
			 * Now since all node models are created they can be linked with each other in the management flow 
			 */
			log.debug("Building management flow by relating node models...");
			Iterator<Map.Entry<String, Set<String>>> nodeWithTargetsMapIter = nodeWithTargetsMap.entrySet().iterator();
			while (nodeWithTargetsMapIter.hasNext()) {
				Map.Entry<String, Set<String>> inputParamEntry = (Map.Entry<String, Set<String>>) nodeWithTargetsMapIter
						.next();
				String srcNodeId = inputParamEntry.getKey();
				Node srcNode = managementFlow.getNode(srcNodeId);
				/* Relate the source node with its link targets */
				Iterator<String> nodeTargetIdsIter = inputParamEntry.getValue().iterator();
				while (nodeTargetIdsIter.hasNext()) {
					String targetNodeId = (String) nodeTargetIdsIter.next();
					Node targetNode = managementFlow.getNode(targetNodeId);
					log.debug("Creating link between node with id '" + srcNodeId + "' and target node with id '"
							+ targetNodeId + "'");
					managementFlow.addEdge(srcNode, targetNode);
				}
			}

			return managementFlow;

		} catch (Exception e) {
			log.error("Error while creating management flow : " + e.getMessage());
			throw new ParseException(e);
		}

	}

	protected Task createTaskFromJson(JsonNode jsonNode) {
		// TODO check if type attributes are set and are correct

		if (!hasRequiredFields(jsonNode, Arrays.asList(JsonKeys.TYPE, JsonKeys.NAME, JsonKeys.ID))) {
			log.warn("Ignoring task/event node: One of the fields '" + JsonKeys.TYPE + "', '" + JsonKeys.NAME + "' or '"
					+ JsonKeys.ID + "' is missing");
			return null;
		}

		Task task = null;
		String taskType = jsonNode.get(JsonKeys.TYPE).asText();
		String taskName = jsonNode.get(JsonKeys.NAME).asText();
		String taskId = jsonNode.get(JsonKeys.ID).asText();

		log.debug("Parsing JSON task or event node with id '" + taskId + "', name '" + taskName + "', type '" + taskType
				+ "'");

		switch (taskType) {
		case JsonKeys.TASK_TYPE_START_EVENT:
			task = createStartTaskFromJson(jsonNode);
			break;
		case JsonKeys.TASK_TYPE_MGMT_TASK:
			task = createManagementTaskFromJson(jsonNode);
			break;
		case JsonKeys.TASK_TYPE_END_EVENT:
			task = createEndTaskFromJson(jsonNode);
			break;
		default:
			log.warn("Ignoring node: type '" + taskType + "' is unkown");
			return null;
		}

		/* Set generic task attributes */
		task.setId(taskId);
		task.setName(taskName);

		/* Add input parameters to task */
		JsonNode inputParams = jsonNode.get(JsonKeys.INPUT);

		if (inputParams != null) {
			/*
			 * Iterator map required to retrieve the name of the parameter node
			 * 
			 * @see
			 * http://stackoverflow.com/questions/7653813/jackson-json-get-node-
			 * name-from-json-tree
			 */
			Iterator<Map.Entry<String, JsonNode>> inputParamIter = inputParams.fields();
			while (inputParamIter.hasNext()) {
				Map.Entry<String, JsonNode> inputParamEntry = (Map.Entry<String, JsonNode>) inputParamIter.next();
				Parameter inputParam = createParameterFromJson(inputParamEntry.getKey(), inputParamEntry.getValue());
				task.addInputParameter(inputParam);
			}
		} else {
			log.debug("No input parameters found for node with id '" + taskId + "'");
		}

		/* Add output Parameters to task */
		JsonNode outputParams = jsonNode.get(JsonKeys.OUTPUT);
		if (outputParams != null) {
			Iterator<Map.Entry<String, JsonNode>> outputParamIter = outputParams.fields();
			while (outputParamIter.hasNext()) {
				Map.Entry<String, JsonNode> outputParamEntry = (Map.Entry<String, JsonNode>) outputParamIter.next();
				Parameter outputParam = createParameterFromJson(outputParamEntry.getKey(), outputParamEntry.getValue());
				task.addOutputParameter(outputParam);
			}

		} else {
			log.debug("No output parameters found for node with id '" + taskId + "'");
		}

		return task;
	}

	protected StartTask createStartTaskFromJson(JsonNode startTaskNode) {
		return new StartTask();
	}

	protected EndTask createEndTaskFromJson(JsonNode endTaskNode) {
		return new EndTask();
	}

	protected ManagementTask createManagementTaskFromJson(JsonNode managementTaskNode) {
		
		if (!hasRequiredFields(managementTaskNode, Arrays.asList(JsonKeys.NODE_TEMPLATE, JsonKeys.NODE_OPERATION))) {
			log.warn("Ignoring mangement node: One of the fields '" + JsonKeys.NODE_TEMPLATE +  "' or '"
					+ JsonKeys.NODE_OPERATION + "' is missing");
			return null;
		}
		String nodeTemplate = managementTaskNode.get(JsonKeys.NODE_TEMPLATE).asText();
		String nodeInterfaceName = managementTaskNode.get(JsonKeys.NODE_INTERFACE_NAME).asText();
		String nodeOperation = managementTaskNode.get(JsonKeys.NODE_OPERATION).asText();
		
		log.debug("Creating management task with id '" + managementTaskNode.get(JsonKeys.ID) + "', name '" + managementTaskNode.get(JsonKeys.NAME) 
					+ "', node template '" + nodeTemplate + "', node operation '"+ "', node operation '" + nodeOperation + "'");
		
		ManagementTask task = new ManagementTask();
		task.setNodeTemplateId(nodeTemplate);
		task.setNodeOperation(nodeOperation);
		task.setInterfaceName(nodeInterfaceName);
		
		return task;
		
	}

	protected Parameter createParameterFromJson(String paramName, JsonNode paramNode) {

		if (!hasRequiredFields(paramNode, Arrays.asList(JsonKeys.TYPE, JsonKeys.VALUE))) {
			log.warn("Ignoring parameter node: One of the fields '" + JsonKeys.TYPE +  "' or '"
					+ JsonKeys.VALUE + "' is missing");
			return null;
		}
		String paramType = paramNode.get(JsonKeys.TYPE).asText();
		String paramValue = paramNode.get(JsonKeys.VALUE).asText();

		log.debug("Parsing JSON parameter node with of type '" + paramType + "' and value '" + paramValue + "'");

		Parameter param = null;
		switch (paramType) {
		case JsonKeys.PARAM_TYPE_VALUE_CONCAT:
			param = new ConcatParameter(); // TODO add concat operands
			break;
		case JsonKeys.PARAM_TYPE_VALUE_DA:
			param = new DeploymentArtefactParameter();
			break;
		case JsonKeys.PARAM_TYPE_VALUE_IA:
			param = new ImplementationArtefactParameter();
			break;
		case JsonKeys.PARAM_TYPE_VALUE_PLAN:
			param = new PlanParameter(); // TODO add task name
			break;
		case JsonKeys.PARAM_TYPE_VALUE_STRING:
			param = new StringParameter();
			break;
		case JsonKeys.PARAM_TYPE_VALUE_TOPOLOGY:
			param = new TopologyParameter();
			break;
		default:
			log.warn("JSON parameter type '" + paramType + "' unknown");
			return null;
		}

		/* Set generic parameter attributes */
		param.setName(paramName);
		param.setValue(paramValue);

		return param;

	}

	protected Set<String> extractNodeTargetIds(JsonNode node) {
		Set<String> linkTargetIds = new HashSet<String>();
		/* Look for the 'connections' element within the node or its children */
		JsonNode connectionsNode = node.findValue(JsonKeys.CONNECTIONS);
		/*
		 * The connection node hosts an array of all outgoing connections to
		 * other nodes
		 */
		if (connectionsNode != null && connectionsNode.isArray()) {
			Iterator<JsonNode> iter = connectionsNode.iterator();
			while (iter.hasNext()) {
				JsonNode connectionEntry = (JsonNode) iter.next();
				/*
				 * Should always be true as the connection entry is the id of
				 * the target node
				 */
				if (connectionEntry.isTextual()) {
					linkTargetIds.add(connectionEntry.asText());
				} else {
					// TODO warn
				}

			}
		} else {
			log.debug("Node with id '" + node.get(JsonKeys.ID) + "' has no connections to other nodes");
			return null;
		}
		return linkTargetIds;
	}

	protected boolean hasRequiredFields(JsonNode jsonNode, List<String> reqFieldNames) {
		Iterator<String> fieldNAmeIter = reqFieldNames.iterator();

		/* Returns false if one of the field names is missing */
		while (fieldNAmeIter.hasNext()) {
			String reqField = (String) fieldNAmeIter.next();
			if (jsonNode.get(reqField) == null) {
				return false;
			}

		}
		return true;
	}

}
