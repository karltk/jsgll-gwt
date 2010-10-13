package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.GSSNode;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.Triplet;

public class L0 extends Action {

	@Override
	public void exec(Context context) {
		if(!context.R().isEmpty()) {
			Triplet<Label, GSSNode, Integer> descriptor = context.R().remove();
			context.setCurrentNode(descriptor.snd);
			context.setInputPosition(descriptor.trd);
		} else {
			context.parseFinished();
		}
	}

	@Override
	public String toString() {
		return "  if(R != EMPTY) {\n" 
			+ "    (L, u, j) <- R\n" 
			+ "    cu := u, i := j, goto L\n" 
			+ "  } else if ((L0, u0, m) in Um) { success } else { failure }";
	}
}
