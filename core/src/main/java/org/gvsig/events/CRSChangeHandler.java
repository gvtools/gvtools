package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.map.MapContext;

public interface CRSChangeHandler extends EventHandler {

	void crsChanged(MapContext source);

}
