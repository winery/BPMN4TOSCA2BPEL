package de.ustutt.iaas.bpmn2bpel.parser;

import java.net.URI;

import de.ustutt.iaas.bpmn2bpel.model.ManagementFlow;

public abstract class Parser {
	
	public abstract ManagementFlow parse(URI input) throws ParseException;

}
