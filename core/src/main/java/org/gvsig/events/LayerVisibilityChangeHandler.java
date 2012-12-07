package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerVisibilityChangeHandler extends EventHandler {

	void visibilityChanged(Layer source);

}
