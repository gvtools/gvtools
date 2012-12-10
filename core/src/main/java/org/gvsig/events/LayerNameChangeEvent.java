package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class LayerNameChangeEvent implements Event<LayerNameChangeHandler> {

	private Layer source;

	public LayerNameChangeEvent(Layer source) {
		this.source = source;
	}

	@Override
	public void dispatch(LayerNameChangeHandler handler) {
		handler.layerNameChanged(source);
	}

}
