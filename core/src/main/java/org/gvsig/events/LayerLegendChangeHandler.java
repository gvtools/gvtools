package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface LayerLegendChangeHandler extends EventHandler {
	void legendChanged(Layer layer);
}
