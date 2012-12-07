package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class LayerSelectionChangeEvent implements
		Event<LayerSelectionChangeHandler> {

	private Layer source;

	public LayerSelectionChangeEvent(Layer source) {
		this.source = source;
	}

	@Override
	public void dispatch(LayerSelectionChangeHandler handler) {
		handler.layerSelectionChanged(source);
	}

}
