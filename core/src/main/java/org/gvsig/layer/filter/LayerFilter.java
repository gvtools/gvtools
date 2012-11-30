package org.gvsig.layer.filter;

import org.gvsig.layer.Layer;

public interface LayerFilter {

	LayerFilter FEATURE = new FeatureFilter();

	LayerFilter FEATURE_EDITING = new EditingFilter();

	LayerFilter ACTIVE = new ActiveFilter();

	boolean accepts(Layer layer);
}
