package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerAddedHandler extends EventHandler {

	void layerAdded(Layer layer);

}
