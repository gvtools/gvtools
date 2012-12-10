package org.gvsig.events;

import geomatico.events.EventHandler;

public interface LayerCreationErrorHandler extends EventHandler {
	void error(String[] layers);
}
