package org.gvsig.layer.filter;

import org.gvsig.layer.Layer;

/**
 * Filters the layers that matches all the referenced filters
 */
public class AndLayerFilter implements LayerFilter {
	private LayerFilter[] filters;

	public AndLayerFilter(LayerFilter... filters) {
		this.filters = filters;
	}

	@Override
	public boolean accepts(Layer layer) {
		for (LayerFilter filter : filters) {
			if (!filter.accepts(layer)) {
				return false;
			}
		}
		return true;
	}
}
