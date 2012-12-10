package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerRemovedHandler extends EventHandler {

	void layerRemoved(Layer parent, Layer removed);

}
