package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Alternative;
import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.NonTerminal;
import org.spoofax.jsgll.client.Parser;

public class ConditionalLL1Goto extends Action {

	private final Goto next;
	private final NonTerminal nonTerminal;
	private final Alternative alternative;

	public ConditionalLL1Goto(NonTerminal nonTerminal, Alternative alternative, Label next) {
		this.nonTerminal = nonTerminal;
		this.alternative = alternative;
		this.next = new Goto(next);
	}

	@Override
	public Action exec(Context context) {
		System.out.println(context.getInputPosition() + " : cond ll(1) goto");
		if(Parser.test(context.getCurrentCharacter(), nonTerminal, alternative)) {
			System.out.println(" -> " + next.getTarget());
			return next;
		}
		System.out.println(" -> fallthrough");
		return fallThrough;
	}

	@Override
	public String toString() {
		return "  if(test(I[j], " + nonTerminal.getName() +  ", " + alternative.toString() + ")) {" + next.toString() + "}";
	}
}
