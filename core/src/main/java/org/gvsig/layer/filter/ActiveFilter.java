package org.gvsig.layer.filter;

import org.gvsig.layer.Layer;

/**
 * Filters the active layers
 * 
 */
public class ActiveFilter implements LayerFilter {
	@Override
	public boolean accepts(Layer layer) {
		return layer.isActive();
	}
}
