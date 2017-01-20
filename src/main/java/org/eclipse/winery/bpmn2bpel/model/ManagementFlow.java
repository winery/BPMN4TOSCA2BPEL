package org.eclipse.winery.bpmn2bpel.model;

import java.util.Collection;
import java.util.Iterator;

import org.jgrapht.graph.SimpleDirectedGraph;


@SuppressWarnings("serial")
public class ManagementFlow extends SimpleDirectedGraph<Node, Link> {

	public ManagementFlow() {
		super(Link.class);
	}

//	public void addNode(Node node) {
//		addVertex(node);
//	}
//
//	public Set<Node> getNodes() {
//		return vertexSet();
//	}
//
//	public void addLink(Node src, Node target) {
//		addEdge(src, target);
//	}




	/**
	 * @param id
	 * @return The node with the given id or <code>null</code> if a node with this id cannot be found
	 */
	public Node getNode(String id) {
		return findNodeById(id);
	}


	protected Node findNodeById(String id) {
		Iterator<Node> iter = vertexSet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) iter.next();
			if (node.getId().equals(id))
				return node;
		}
		return null;
	}

	@Override
	protected String toStringFromSets(Collection<? extends Node> vertexSet, Collection<? extends Link> edgeSet,
			boolean directed) {
		// TODO Auto-generated method stub
		return super.toStringFromSets(vertexSet, edgeSet, directed);
	}




}
