package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Alternative;
import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.NonTerminal;
import org.spoofax.jsgll.client.Parser;

public class ConditionalLL1Goto extends Action {

	private final Goto fallThrough;
	private final Goto next;
	private final NonTerminal nonTerminal;
	private final Alternative alternative;

	public ConditionalLL1Goto(NonTerminal nonTerminal, Alternative alternative, Label next, Label fallThrough) {
		this.nonTerminal = nonTerminal;
		this.alternative = alternative;
		this.next = new Goto(next);
		this.fallThrough = new Goto(fallThrough);
		// 
	}

	@Override
	public void exec(Context context) {
		if(Parser.test(context.getCurrentCharacter(), nonTerminal, alternative)) {
			next.exec(context);
		}
		fallThrough.exec(context);
	}

	@Override
	public String toString() {
		return "  if(test(I[j], " + nonTerminal.getName() +  ", a?)) {" + next.toString() + "}";
	}
}
