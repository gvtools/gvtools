package com.iver.cit.gvsig.project.documents.view.toc.gui;

import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.fmap.MapControl;

public interface ILayerAction {

	String getText();

	String getDescription();

	boolean isVisible(MapContext mapContext);

	boolean isEnabled(MapContext mapContext);

	void execute(MapContext mapContext, MapControl mapControl);

	int getGroupOrder();

	String getGroup();

	int getOrder();

}
