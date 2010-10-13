package org.spoofax.jsgll.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.spoofax.jsgll.client.actions.Action;
import org.spoofax.jsgll.client.actions.ConditionalFork;
import org.spoofax.jsgll.client.actions.ConditionalLL1Goto;
import org.spoofax.jsgll.client.actions.ConditionalNonLL1Goto;
import org.spoofax.jsgll.client.actions.Consume;
import org.spoofax.jsgll.client.actions.CreateNode;
import org.spoofax.jsgll.client.actions.Goto;
import org.spoofax.jsgll.client.actions.IncreaseInputPosition;
import org.spoofax.jsgll.client.actions.L0;
import org.spoofax.jsgll.client.actions.Pop;
import org.spoofax.jsgll.client.actions.ProgramLabel;

public class SGLL {

	private static int counter;

	public static void main(String[] args) {
		
		Grammar g = new Grammar();
		
		Symbol a = g.makeTerminal("a", 'a');
		Symbol b = g.makeTerminal("b", 'b');
		Symbol c = g.makeTerminal("c", 'c');
		Symbol d = g.makeTerminal("d", 'd');
		Symbol B = g.makeNonTerminal("B").alt(a).alt(b);
		Symbol A = g.makeNonTerminal("A").alt(a).alt(c);
		NonTerminal S = g.makeNonTerminal("S");
		
		S.alt(A, S, d).alt(B, S).alt(g.EMPTY);
		
		System.out.println(new SGLL().parse(g, "abd"));
	}
	
	public ParseTree parse(Grammar g, String string) {
		Parser p = code(g);
		p.debugDump();
		return p.parse(string);
	}

	
	private Parser code(Grammar g) {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ProgramLabel(freshLabel()));
		actions.add(new L0());
		
		for(NonTerminal nt : g.getProductions()) {
			if(nt.isLL1NonTerminal()) {
				codeLL1(actions, nt);
			} else {
				codeNonLL1(actions, nt);
			}
		}
		return new Parser(actions, g);
	}
	
	private void codeLL1(List<Action> actions, NonTerminal nt) {
		
		Label[] labels = new Label[nt.getAlternativeCount()];
		Alternative[] alternatives = nt.getAlternatives().toArray(new Alternative[0]);
		
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new Label(nt.getName() + i);
		}
		
		actions.add(new ProgramLabel(nt.getLabel()));
		
		for(int i = 0; i < alternatives.length - 1; i++) {
			actions.add(new ConditionalLL1Goto(nt, alternatives[i], labels[i], labels[i+1]));
		}
		
		actions.add(new ConditionalLL1Goto(nt, alternatives[labels.length - 1], labels[labels.length - 1], labels[0]));
		
		for(int i = 0; i < alternatives.length; i++) {
			actions.add(new ProgramLabel(labels[i]));
			code(actions, alternatives[i]);
		}
		
	}

	private void codeNonLL1(List<Action> actions, NonTerminal nt) {
		Label[] labels = new Label[nt.getAlternativeCount()];
		Alternative[] alternatives = nt.getAlternatives().toArray(new Alternative[0]);
		
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new Label(nt.getName() + i);
		}
		
		for(int i = 0; i < alternatives.length - 1; i++) {
			actions.add(new ConditionalNonLL1Goto(nt, alternatives[i], labels[i], labels[i+1]));
		}
		
		actions.add(new ConditionalNonLL1Goto(nt, alternatives[labels.length - 1], labels[labels.length - 1], labels[0]));
		
		for(int i = 0; i < alternatives.length; i++) {
			actions.add(new ProgramLabel(labels[i]));
			code(actions, alternatives[i]);
		}
	}

	private void code(Collection<Action> actions, Alternative a) {
		if(a.getSymbols().length == 0) {
			// code(A ::= e, j)
			actions.add(new Pop());
			actions.add(new Goto("L0"));
		} else if (a.getSymbols()[0] instanceof Terminal) {
			// code(A ::= αk, j)
			actions.add(new IncreaseInputPosition());
			Symbol[] sx = a.getSymbols();
			for(int k = 1; k < sx.length; k++) {
				code(actions, sx[k], a);
			}
			actions.add(new Pop());
			actions.add(new Goto("L0"));
		} else {
			// code(A ::= αk, j)
			Symbol[] sx = a.getSymbols();
			Symbol s = sx[0];
			Label l = freshLabel();
			actions.add(new CreateNode(l));
			actions.add(new Goto(s.getLabel()));
			actions.add(new ProgramLabel(l));
			for(int k = 1; k < sx.length; k++) {
				Symbol[] nsx = new Symbol[sx.length - k];
				System.arraycopy(sx, k, nsx, 0, nsx.length);
				code(actions, new Alternative(a.getGrammar(), nsx));
			}
			// pop + goto L0
		}
	}
	
	
	private void code(Collection<Action> actions, Symbol x, Alternative alt) {
		if(x instanceof Terminal) {
			// code(aα, j, X)
			actions.add(new Consume(((Terminal) x).getChar()));
		} else if(x instanceof NonTerminal) {
			// code(Akα, j, X)
			Label l = freshLabel();
			actions.add(new ConditionalFork(l, (NonTerminal)x, alt));
			actions.add(new ProgramLabel(l));
		} else {
			throw new RuntimeException("Only accepts NonTerminal and Terminal");
		}
		
	}

	private static Label freshLabel() {
		return new Label("L" + counter++);
	}

}
