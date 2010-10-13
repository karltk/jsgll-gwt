package org.spoofax.jsgll.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;

public class JSGLLEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(new Label("JSGLL"));
	}
}
