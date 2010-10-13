package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class IncreaseInputPosition extends Action {

	@Override
	public Action exec(Context context) {
		System.out.println(context.getInputPosition() + " : inc input pos");
		context.setInputPosition(context.getInputPosition() + 1);
		return fallThrough;
	}

	@Override
	public String toString() {
		return "  j := j + 1";
	}
	
}
