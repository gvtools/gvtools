package org.gvsig.main.events;

import geomatico.events.EventHandler;

import org.gvsig.map.MapContext;

public interface ExtentChangeHandler extends EventHandler {

	/**
	 * An extent changed related to the <code>source</code> {@link MapContext}
	 * 
	 * @param source
	 */
	void extentChanged(MapContext source);

}
