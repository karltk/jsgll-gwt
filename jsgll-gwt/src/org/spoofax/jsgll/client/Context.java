package org.spoofax.jsgll.client;

import java.util.Queue;

import org.spoofax.jsgll.client.actions.Action;

public interface Context {

	Queue<Triplet<Label, GSSNode, Integer>> R();

	GSSNode getCurrentNode();
	void setCurrentNode(GSSNode u);
	
	int getInputPosition();
	void setInputPosition(Integer j);

	char getCurrentCharacter();
	

	// FIXME this is code, not runtime state -- into separate interface 
	
	void parseFinished();
	void pop(GSSNode currentNode, int inputPosition);
	Action lookupActionByLabel(String targetLabel);
	GSSNode create(Label label, GSSNode u, int inputPosition);

	void add(Label next, GSSNode currentNode, int inputPosition);

}
