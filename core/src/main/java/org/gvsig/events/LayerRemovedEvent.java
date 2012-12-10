package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class LayerRemovedEvent implements Event<LayerRemovedHandler> {
	private Layer parent;
	private Layer removed;

	public LayerRemovedEvent(Layer parent, Layer removed) {
		this.parent = parent;
		this.removed = removed;
	}

	@Override
	public void dispatch(LayerRemovedHandler handler) {
		handler.layerRemoved(parent, removed);
	}

}
