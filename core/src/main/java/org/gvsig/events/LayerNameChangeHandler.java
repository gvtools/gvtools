package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerNameChangeHandler extends EventHandler {

	void layerNameChanged(Layer source);

}
