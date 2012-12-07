package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class LayerVisibilityChangeEvent implements
		Event<LayerVisibilityChangeHandler> {

	private Layer source;

	public LayerVisibilityChangeEvent(Layer layer) {
		this.source = layer;
	}

	@Override
	public void dispatch(LayerVisibilityChangeHandler handler) {
		handler.visibilityChanged(source);
	}

}
