package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class Pop extends Action {

	@Override
	public Action exec(Context context) {
		System.out.println("pop");
		context.pop(context.getCurrentNode(), context.getInputPosition());
		return fallThrough;
	}

	@Override
	public String toString() {
		return "  pop(cu, j)";
	}
}
