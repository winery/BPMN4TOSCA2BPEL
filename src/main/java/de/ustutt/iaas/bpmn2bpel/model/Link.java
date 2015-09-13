package de.ustutt.iaas.bpmn2bpel.model;

import org.jgrapht.graph.DefaultEdge;

public class Link extends DefaultEdge {
	
	public Node getSource() {
		return (Node) super.getSource();
	}
	
	public Node getTarget() {
		return (Node) super.getTarget();
	}

}
