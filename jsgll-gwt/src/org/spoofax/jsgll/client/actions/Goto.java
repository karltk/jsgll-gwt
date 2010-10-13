package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;

public class Goto extends Action {


	private final String targetLabel;
	private Action cachedTarget;
	
	public Goto(String labelName) {
		this.targetLabel = labelName;
	}

	public Goto(Label label) {
		this.targetLabel = label.getName();
	}

	@Override
	public void exec(Context context) {
		if(cachedTarget == null)
			cachedTarget = context.lookupActionByLabel(targetLabel);
		cachedTarget.exec(context);
	}

	@Override
	public String toString() {
		return "  goto " + targetLabel;
	}

}
