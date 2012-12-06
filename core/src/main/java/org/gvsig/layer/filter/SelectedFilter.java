package org.gvsig.layer.filter;

import org.gvsig.layer.Layer;

public class SelectedFilter implements LayerFilter {

	@Override
	public boolean accepts(Layer layer) {
		return layer.isSelected();
	}

}
