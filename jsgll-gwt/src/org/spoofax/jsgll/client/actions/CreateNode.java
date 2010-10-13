package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;

public class CreateNode extends Action {

	private final Label next;

	public CreateNode(Label next) {
		this.next = next;
	}

	@Override
	public Action exec(Context context) {
		System.out.println("create node");
		context.setCurrentNode(context.create(next, context.getCurrentNode(), context.getInputPosition()));
		return fallThrough;
	}

	@Override
	public String toString() {
		return "  create(" + next.getName() + ", cu, j)";
	}
}
