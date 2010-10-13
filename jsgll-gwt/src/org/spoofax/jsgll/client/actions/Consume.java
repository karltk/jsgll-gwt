package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public class Consume extends Action {

	private char ch;
	private Goto l0;

	public Consume(char ch) {
		this.ch = ch;
		l0 = new Goto("#L0");
	}

	@Override
	public Action exec(Context context) {
		System.out.println(context.getInputPosition() + " : consume ");
		if(context.getCurrentCharacter() == ch) {
			context.setInputPosition(context.getInputPosition() + 1);
		} else {
			return l0;
		}
		return fallThrough;
	}
	
	@Override
	public String toString() {
		return "  if (I[j] == " + ch + ") { j += 1 } else { goto #L0 }";
	}
	

}
