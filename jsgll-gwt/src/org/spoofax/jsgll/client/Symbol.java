package org.spoofax.jsgll.client;

public abstract class Symbol {

	protected final Grammar grammar;
	protected final String name;
	private final Label label;

	public Symbol(Grammar parent, String name) {
		this.grammar = parent;
		this.name = name;
		this.label = new Label(name);
	}

	public Label getLabel() {
		return label;
	}

	public abstract SymbolSet first();

	public String getName() {
		return name;
	}
}
