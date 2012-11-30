package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class LayerAddedEvent implements Event<LayerAddedHandler> {

	private Layer layer;

	public LayerAddedEvent(Layer layer) {
		this.layer = layer;
	}

	@Override
	public void dispatch(LayerAddedHandler handler) {
		handler.layerAdded(layer);
	}

}
