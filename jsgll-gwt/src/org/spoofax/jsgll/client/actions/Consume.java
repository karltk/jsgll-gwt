package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class Consume extends Action {

	private char ch;
	private Goto l0;

	public Consume(char ch) {
		this.ch = ch;
		l0 = new Goto("L0");
	}

	@Override
	public void exec(Context context) {
		if(context.getCurrentCharacter() == ch) {
			context.setInputPosition(context.getInputPosition() + 1);
		} else {
			l0.exec(context);
		}
	}
	
	@Override
	public String toString() {
		return "  if (I[j] == " + ch + ") { j += 1 } else { goto L0 }";
	}
	

}
