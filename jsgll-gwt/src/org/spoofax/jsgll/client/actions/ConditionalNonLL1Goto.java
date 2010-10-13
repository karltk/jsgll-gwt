package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Alternative;
import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.NonTerminal;
import org.spoofax.jsgll.client.NotImplementedException;
import org.spoofax.jsgll.client.Parser;

public class ConditionalNonLL1Goto extends Action {

	private final Label next;
	private final Goto fallThrough;
	private final NonTerminal nonTerminal;
	private final Alternative alternative;

	public ConditionalNonLL1Goto(NonTerminal nonTerminal, Alternative alternative, Label next, Label fallThrough) {
		this.nonTerminal = nonTerminal;
		this.alternative = alternative;
		this.next = next;
		this.fallThrough = new Goto(fallThrough);
	}

	@Override
	public void exec(Context context) {
		if(Parser.test(context.getCurrentCharacter(), nonTerminal, alternative)) {
			context.add(next, context.getCurrentNode(), context.getInputPosition());
		}
		fallThrough.exec(context);
	}

	@Override
	public String toString() {
		throw new NotImplementedException();
	}
}
