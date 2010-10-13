package org.spoofax.jsgll.client;

public class Terminal extends Symbol {

	public final static char EMPTY_CHAR = '\u2422';
	public final static char EOF_CHAR = '\u2423'; // Open Box 

	// FIXME perhaps these are grammar-instance specific?
	
	private char ch;

	Terminal(Grammar parent, String name, char ch) {
		super(parent, name);
		this.ch = ch;
	}
	
	public char getChar() {
		return ch;
	}

	@Override
	public SymbolSet first() {
		return new SymbolSet(ch);
	}
}
