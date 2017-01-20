package org.eclipse.winery.bpmn2bpel.parser;

import java.net.URI;

import org.eclipse.winery.bpmn2bpel.model.ManagementFlow;

public abstract class Parser {

	public abstract ManagementFlow parse(URI input) throws ParseException;

}
