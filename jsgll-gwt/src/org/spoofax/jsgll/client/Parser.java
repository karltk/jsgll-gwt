package org.spoofax.jsgll.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.spoofax.jsgll.client.actions.Action;
import org.spoofax.jsgll.client.actions.Goto;
import org.spoofax.jsgll.client.actions.ProgramLabel;
import org.spoofax.jsgll.shared.ArrayDeque;

public class Parser implements Context {

	private final List<Action> actions;
	private final Grammar grammar;

	private final Map<GSSNode, Collection<Integer>> P; // popped pairs
	private Set<Pair<Label, GSSNode>>[] U; // updated pairs?
	private final Queue<Triplet<Label, GSSNode, Integer>> R; // rest -- current descriptors

	private GSSNode u0;
	private GSSNode u1;
	private GSSNode currentNode;
	private int inputPosition;
	private int maxInputPosition;
	private String inputText;
	private Label L0;
	private Map<String, ProgramLabel> labels;
	private ParseTree tree;
	
	public Parser(List<Action> actions, Grammar grammar) {
		this.actions = actions;
		this.grammar = grammar;

		labels = new HashMap<String, ProgramLabel>();
		
		processLabels();
		
		inputPosition = 1;

		P = new HashMap<GSSNode, Collection<Integer>>();
		U = null;
		R = new ArrayDeque<Triplet<Label,GSSNode,Integer>>();
		L0 = new Label("L0");
		
		tree = null;
	}

	private void processLabels() {
		ProgramLabel needsPatching = null;
		for(Action a : actions) {
			if(a instanceof ProgramLabel) {
				needsPatching = (ProgramLabel) a;
			} else if(needsPatching != null) {
				needsPatching.backpatchNextAction(a);
				labels.put(needsPatching.getLabel(), needsPatching);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public ParseTree parse(String input) {
		inputText = input;

		U = new Set[inputText.length()+1];
		u1 = new GSSNode();
		u0 = new GSSNode();
		currentNode = u1;
		inputPosition = 0;
		maxInputPosition = inputText.length();
		for(int j = 0; j < U.length; j++)
			U[j] = new HashSet<Pair<Label, GSSNode>>();
		R.clear();
		P.clear();
		if(first(new Alternative(grammar, new Symbol[] { grammar.startProduction(), grammar.EOF })).contains(inputText.charAt(inputPosition))) {
			new Goto(grammar.startProduction().getLabel()).exec(this);
		} else {
			throwParseFailure();
		}
		return tree;
	}
	
	private void throwParseFailure() {
		String ch = "<outside range>";
		if(inputPosition < inputText.length())
			ch = "" + inputText.charAt(inputPosition);
		throw new RuntimeException("Parse error at character " + inputPosition + "'" + ch + "'");
	}

	public static boolean test(char ch, NonTerminal nt, Alternative alt) {
		return first(alt).contains(ch) || 
		(first(alt).contains(Terminal.EMPTY_CHAR) && follow(nt).contains(ch)); 
	}

	public void add(Label label, GSSNode node, int inputPosition) {
		Pair<Label, GSSNode> pair = new Pair<Label, GSSNode>(label, node);
		Set<Pair<Label, GSSNode>> Uj = ensureU(inputPosition);
		if(!Uj.contains(pair)) {
			Uj.add(pair);
			R.add(new Triplet<Label, GSSNode, Integer>(label, node, inputPosition));
		}
	}
	
	public void pop(GSSNode u, int inputPosition) {
		if(u != u0) {
			addToP(u, inputPosition);
			for(GSSNode v : u.children()) {
				add(u.getLabel(), v, inputPosition);
			}
		}
	}

	private void addToP(GSSNode u, int inputPosition) {
		Collection<Integer> c = P.get(u);
		if(c == null) {
			c = new LinkedList<Integer>();
			c.add(inputPosition);
			P.put(u, c);
		}
	}

	private Set<Pair<Label, GSSNode>> ensureU(int inputPosition) {
		return U[inputPosition];
	}

	public GSSNode create(Label label, GSSNode u, int inputPosition) {
		GSSNode v = findOrCreate(label, inputPosition);
		if(!v.hasEdgeTo(u)) {
			v.addEdgeTo(u);
			for(int k : P.get(v))
				add(label, u, k);
		}
		return v;
	}

	private GSSNode findOrCreate(Label label, int inputPosition) {
		throw new NotImplementedException();
	}

	static SymbolSet follow(NonTerminal nt) {
		return nt.follow();
	}

	static SymbolSet first(Alternative alt) {
		return alt.first();
	}

	@Override
	public Queue<Triplet<Label, GSSNode, Integer>> R() {
		return R;
	}

	@Override
	public GSSNode getCurrentNode() {
		return currentNode;
	}

	@Override
	public void setCurrentNode(GSSNode u) {
		currentNode = u;
	}

	@Override
	public int getInputPosition() {
		return inputPosition;
	}

	@Override
	public void setInputPosition(Integer j) {
		inputPosition = j;
	}

	@Override
	public char getCurrentCharacter() {
		return inputText.charAt(inputPosition);
	}

	@Override
	public void parseFinished() {
		if(U[maxInputPosition].contains(new Pair<Label, GSSNode>(L0, u0))) {
			System.out.println("Successful parse");
			tree = new ParseTree();
		} else {
			System.out.println("Parse failed");
		}
	}

	@Override
	public Action lookupActionByLabel(String targetLabel) {
		return labels.get(targetLabel);
	}

	public void debugDump() {
		for(Action a : actions) {
			System.out.println(a.toString());
		}
	}


}
