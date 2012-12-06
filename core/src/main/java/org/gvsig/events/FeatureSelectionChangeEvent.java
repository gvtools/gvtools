package org.gvsig.events;

import geomatico.events.Event;

import org.gvsig.layer.Layer;

public class FeatureSelectionChangeEvent implements
		Event<FeatureSelectionChangeHandler> {

	private Layer source;

	public FeatureSelectionChangeEvent(Layer source) {
		this.source = source;
	}

	@Override
	public void dispatch(FeatureSelectionChangeHandler handler) {
		handler.featureSelectionChange(source);
	}
}
