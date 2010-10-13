package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;

public class ProgramLabel extends Action {

	private final String label;
	
	public ProgramLabel(Label label) {
		this.label = label.getName();
	}
	
	@Override
	public Action exec(Context context) {
		System.out.println(context.getInputPosition() + " : label " + label);
		return fallThrough;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return label + ":";
	}
}
