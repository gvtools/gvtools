package org.gvsig.events;

import org.gvsig.layer.Layer;

import geomatico.events.Event;

public class LayerLegendChangeEvent implements Event<LayerLegendChangeHandler> {
	private Layer layer;

	public LayerLegendChangeEvent(Layer layer) {
		this.layer = layer;
	}

	@Override
	public void dispatch(LayerLegendChangeHandler handler) {
		handler.legendChanged(layer);
	}
}
