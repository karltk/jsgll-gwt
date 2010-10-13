package org.spoofax.jsgll.client.actions;

import org.spoofax.jsgll.client.Context;

public abstract class Action {

	protected Action fallThrough;

	public abstract Action exec(Context context);

	public void setFallThroughAction(Action fallThrough) {
		this.fallThrough = fallThrough;
	}
}
