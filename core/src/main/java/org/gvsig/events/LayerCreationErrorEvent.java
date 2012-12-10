package org.gvsig.events;

import geomatico.events.Event;

public class LayerCreationErrorEvent implements
		Event<LayerCreationErrorHandler> {
	private String message;
	private Throwable cause;

	public LayerCreationErrorEvent(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	@Override
	public void dispatch(LayerCreationErrorHandler handler) {
		handler.error(message, cause);
	}
}
