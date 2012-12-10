package com.iver.cit.gvsig.project.documents.view.toc.actions;

import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.project.documents.view.toc.gui.ILayerAction;

public abstract class AbstractLayerAction implements ILayerAction {

	protected Layer[] getSelected(MapContext mapContext) {
		return mapContext.getRootLayer().filter(LayerFilter.SELECTED);
	}

}
