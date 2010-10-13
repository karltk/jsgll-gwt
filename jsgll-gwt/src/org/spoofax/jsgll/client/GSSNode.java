package org.spoofax.jsgll.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GSSNode {

	private final Set<GSSNode> children;
	private final Label label;
	
	public GSSNode(Label label) {
		this.label = label;
		this.children = new HashSet<GSSNode>();
	}
	
	public Collection<GSSNode> children() {
		return children;
	}

	public Label getLabel() {
		return label;
	}

	public boolean hasEdgeTo(GSSNode u) {
		return children.contains(u);
	}

	public void addEdgeTo(GSSNode u) {
		children.add(u);
	}

}
