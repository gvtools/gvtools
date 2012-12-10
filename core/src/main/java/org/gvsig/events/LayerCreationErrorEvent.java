package org.gvsig.events;

import geomatico.events.Event;

public class LayerCreationErrorEvent implements
		Event<LayerCreationErrorHandler> {
	private String[] layers;

	public LayerCreationErrorEvent(String[] layers) {
		this.layers = layers;
	}

	@Override
	public void dispatch(LayerCreationErrorHandler handler) {
		handler.error(layers);
	}
}
