package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Alternative;
import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.NonTerminal;
import org.spoofax.jsgll.client.NotImplementedException;
import org.spoofax.jsgll.client.Parser;

public class ConditionalFork extends Action {

	private final NonTerminal nonTerminal;
	private final Alternative alternative;
	private Goto l0;
	private Goto la;
	private Label nextAction;
	
	public ConditionalFork(Label nextAction, NonTerminal nonTerminal, Alternative alternative) {
		this.nonTerminal = nonTerminal;
		this.alternative = alternative;
		l0 = new Goto("#L0");
		la = new Goto(alternative.getLabel());
	}
	
	@Override
	public Action exec(Context context) {
		System.out.println(context.getInputPosition() + " : cond fork");
		if(Parser.test(
				context.getCurrentCharacter(), 
				nonTerminal,
				alternative)) {
			context.setCurrentNode(context.create(nextAction, context.getCurrentNode(), context.getInputPosition()));
			return la;
		} else {
			return l0;
		}
	}

	@Override
	public String toString() {
		throw new NotImplementedException();
	}
}
