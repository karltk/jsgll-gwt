package org.spoofax.jsgll.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Grammar {

	private Map<String, Terminal> terminals = new HashMap<String, Terminal>();
	private Map<String, NonTerminal> nonTerminals = new HashMap<String, NonTerminal>();
	private Symbol start;
	private int counter = 0;
	
	public final Terminal EMPTY = new Terminal(this, "EMPTY", Terminal.EMPTY_CHAR);
	public final Terminal EOF = new Terminal(this, "EOF", Terminal.EOF_CHAR);

	Terminal makeTerminal(String name, char ch) {
		Terminal x = new Terminal(this, name, ch);
		terminals.put(name, x);
		return x;
	}
	
	NonTerminal makeNonTerminal(String name) {
		NonTerminal x = new NonTerminal(this, name);
		nonTerminals.put(name, x);
		start = x;
		return x;
	}

	public Collection<NonTerminal> getProductions() {
		return nonTerminals.values();
	}

	public Symbol startProduction() {
		return start;
	}

	public Label freshLabel(String name) {
		return new Label(name + "x" + counter++);
	}
}
