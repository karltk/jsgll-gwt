package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class IncreaseInputPosition extends Action {

	@Override
	public void exec(Context context) {
		context.setInputPosition(context.getInputPosition() + 1);
	}

	@Override
	public String toString() {
		return "  j := j + 1";
	}
	
}
