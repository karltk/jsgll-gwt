package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;
import org.spoofax.jsgll.client.GSSNode;
import org.spoofax.jsgll.client.Label;
import org.spoofax.jsgll.client.Triplet;

public class L0 extends Action {

	@Override
	public Action exec(Context context) {
		System.out.println("L0");
		if(!context.R().isEmpty()) {
			Triplet<Label, GSSNode, Integer> descriptor = context.R().remove();
			context.setCurrentNode(descriptor.snd);
			context.setInputPosition(descriptor.trd);
			return fallThrough;
		}
		
		context.parseFinished();
		return null;
	}

	@Override
	public String toString() {
		return "  if(R != EMPTY) {\n" 
			+ "    (L, u, j) <- R\n" 
			+ "    cu := u, i := j, goto L\n" 
			+ "  } else if ((L0, u0, m) in Um) { success } else { failure }";
	}
}
