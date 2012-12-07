package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerSelectionChangeHandler extends EventHandler {

	void layerSelectionChanged(Layer source);

}
