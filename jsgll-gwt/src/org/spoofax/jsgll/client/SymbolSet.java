package org.spoofax.jsgll.client;

import java.util.HashSet;
import java.util.Set;

public class SymbolSet {

	private Set<Character> chars;
	
	public SymbolSet(char ch) {
		this();
		chars.add(ch);
	}

	private SymbolSet(Set<Character> chars) {
		this.chars = chars;
	}
	
	public SymbolSet() {
		chars = new HashSet<Character>();
	}

	public boolean contains(char ch) {
		return chars.contains(ch);
	}

	public SymbolSet union(SymbolSet first) {
		Set<Character> s = new HashSet<Character>();
		s.addAll(chars);
		s.addAll(first.chars);
		return new SymbolSet(s);
	}

	public SymbolSet intersect(SymbolSet w) {
		Set<Character> r = new HashSet<Character>();
		r.addAll(chars);
		chars.retainAll(w.chars);
		return new SymbolSet(r);
	}

	public boolean isEmpty() {
		return chars.isEmpty();
	}
}
