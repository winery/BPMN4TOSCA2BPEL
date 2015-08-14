package de.ustutt.iaas.bpmn2bpel.parser;

import java.net.URL;
import java.nio.file.Path;

import de.ustutt.iaas.bpmn2bpel.model.ManagementFlow;

public abstract class Parser {
	
	public abstract ManagementFlow parse(URL input) throws ParseException;

}
