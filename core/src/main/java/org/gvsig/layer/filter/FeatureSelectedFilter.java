package org.gvsig.layer.filter;

import org.gvsig.layer.Layer;

/**
 * True if the layer is a feature layer and has some features selected
 * 
 * @author Fernando González Cortés
 */
public class FeatureSelectedFilter implements LayerFilter {

	@Override
	public boolean accepts(Layer layer) {
		return layer.hasFeatures() && layer.getSelection().size() > 0;
	}

}
