package org.spoofax.jsgll.client;

import com.google.gwt.junit.client.GWTTestCase;

public class TestSGLL extends GWTTestCase {

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void test1() {
		
		Grammar g = new Grammar();
		
		Symbol a = g.makeTerminal("a", 'a');
		Symbol b = g.makeTerminal("b", 'b');
		Symbol c = g.makeTerminal("c", 'c');
		Symbol d = g.makeTerminal("d", 'd');
		Symbol B = g.makeNonTerminal("B").alt(a).alt(b);
		Symbol A = g.makeNonTerminal("A").alt(a).alt(c);
		NonTerminal S = g.makeNonTerminal("S");
		
		S.alt(A, S, d).alt(B, S).alt(g.EMPTY);
		
		SGLL pg = new SGLL();
		
		assertNotNull("Parse failed", pg.parse(g, "abd"));
	}
}
