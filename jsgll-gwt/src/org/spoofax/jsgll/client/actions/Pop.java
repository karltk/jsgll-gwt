package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class Pop extends Action {

	@Override
	public void exec(Context context) {
		context.pop(context.getCurrentNode(), context.getInputPosition());
	}

	@Override
	public String toString() {
		return "  pop(cu, j)";
	}
}
