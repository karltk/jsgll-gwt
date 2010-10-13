package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;

public class CreateNode extends Action {

	private final Label next;

	public CreateNode(Label next) {
		this.next = next;
	}

	@Override
	public void exec(Context context) {
		context.setCurrentNode(context.create(next, context.getCurrentNode(), context.getInputPosition()));
	}

	@Override
	public String toString() {
		return "  create(" + next.getName() + ", cu, j)";
	}
}
