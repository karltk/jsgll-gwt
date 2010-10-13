package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;

public class ProgramLabel extends Action {

	private Action labeledAction;
	private final String label;
	
	public ProgramLabel(Label label) {
		this.label = label.getName();
	}
	
	@Override
	public void exec(Context context) {
		labeledAction.exec(context);
	}

	public void backpatchNextAction(Action nextAction) {
		this.labeledAction = nextAction;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return label + ":";
	}
}
