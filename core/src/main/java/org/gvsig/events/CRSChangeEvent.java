package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.map.MapContext;

public class CRSChangeEvent implements Event<CRSChangeHandler> {

	private MapContext source;

	public CRSChangeEvent(MapContext source) {
		this.source = source;
	}

	@Override
	public void dispatch(CRSChangeHandler handler) {
		handler.crsChanged(source);
	}
}
