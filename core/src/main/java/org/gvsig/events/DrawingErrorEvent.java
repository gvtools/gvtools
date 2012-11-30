package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.map.MapContext;

public class DrawingErrorEvent implements Event<DrawingErrorHandler> {

	private String message;
	private Throwable cause;
	private MapContext source;

	public DrawingErrorEvent(MapContext source, String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
		this.source = source;
	}

	@Override
	public void dispatch(DrawingErrorHandler handler) {
		handler.error(source, message, cause);
	}

}
