package org.spoofax.jsgll.client;

public class Alternative {

	private final Symbol[] symbols;
	private Grammar grammar;

	Alternative(Grammar parent, Symbol[] symbols) {
		this.grammar = parent;
		this.symbols = symbols;
	}

	public Symbol[] getSymbols() {
		return symbols;
	}


	public SymbolSet first() {
		if(symbols.length == 0) 
			return new SymbolSet(Terminal.EMPTY_CHAR);
		return symbols[0].first();
	}

	public boolean isNullable() {
		return symbols.length == 0 || symbols[0] == grammar.EMPTY;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public Label getLabel() {
		throw new NotImplementedException();
	}

}
