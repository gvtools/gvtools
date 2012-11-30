package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.map.MapContext;

public interface DrawingErrorHandler extends EventHandler {

	void error(MapContext source, String message, Throwable problem);

}
