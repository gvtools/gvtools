package com.iver.cit.gvsig.project.documents.view.toc.actions;

import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.project.documents.view.toc.gui.ILayerAction;

public abstract class AbstractLayerAction implements ILayerAction {

	/**
	 * Gets the layers that have the selected attribute set to true
	 * 
	 * @param mapContext
	 * @return The array of layers. Never null
	 */
	protected Layer[] getSelected(MapContext mapContext) {
		return mapContext.getRootLayer().filter(LayerFilter.SELECTED);
	}

}
