package org.spoofax.jsgll.client;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NonTerminal extends Symbol {

	List<Alternative> alternatives = new LinkedList<Alternative>();
	
	NonTerminal(Grammar parent, String name) {
		super(parent, name);
	}

	public NonTerminal alt(Symbol...symbols) {
		alternatives.add(new Alternative(grammar, symbols));
		return this;
	}

	public Collection<Symbol> getSymbols() {
		throw new NotImplementedException();
	}

	public int getAlternativeCount() {
		return alternatives.size();
	}

	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	public boolean isLL1NonTerminal() {
		SymbolSet w = alternatives.get(0).first();
		for(Alternative a : alternatives)
			w = a.first().intersect(w);

		return w.isEmpty() && isNullable() ? first().intersect(follow()).isEmpty() : true;
	}

	private boolean isNullable() {
		for(Alternative a : alternatives) {
			if(a.isNullable())
				return true;
		}
		return false;
	}

	public SymbolSet follow() {
		SymbolSet follow = new SymbolSet();
		for(NonTerminal nt : grammar.getProductions()) {
			for(Alternative a : nt.getAlternatives()) {
				Symbol[] ss = a.getSymbols();
				for(int i = 0; i < ss.length; i++) {
					if(ss[i].equals(this) && i < ss.length - 1 && ss[i+1] instanceof Terminal) {
						follow = follow.union(ss[i+1].first());
					}
				}
			}
		}
		if(isNullable())
			return follow.union(new SymbolSet(Terminal.EOF_CHAR));
		else
			return follow;
	}

	@Override
	public SymbolSet first() {
		SymbolSet r = new SymbolSet();
		for(Alternative a : alternatives)
			r = r.union(a.first());
		if(isNullable())
			return r.union(new SymbolSet(Terminal.EMPTY_CHAR));
		else 
			return r;
	}

}
