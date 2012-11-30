package org.gvsig.main.events;

import geomatico.events.Event;

import org.gvsig.map.MapContext;

public class ExtentChangeEvent implements Event<ExtentChangeHandler> {

	private MapContext source;

	public ExtentChangeEvent(MapContext source) {
		this.source = source;
	}

	@Override
	public void dispatch(ExtentChangeHandler handler) {
		handler.extentChanged(source);
	}
}
