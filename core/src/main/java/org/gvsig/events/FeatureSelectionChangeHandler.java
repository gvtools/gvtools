package org.gvsig.events;

import geomatico.events.EventHandler;

import org.gvsig.layer.Layer;

public interface FeatureSelectionChangeHandler extends EventHandler {

	void featureSelectionChange(Layer source);
}
