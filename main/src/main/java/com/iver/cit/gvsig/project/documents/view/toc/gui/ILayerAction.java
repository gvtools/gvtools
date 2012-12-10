package com.iver.cit.gvsig.project.documents.view.toc.gui;

import org.gvsig.map.MapContext;

public interface ILayerAction {

	String getText();

	String getDescription();

	boolean isVisible(MapContext mapContext);

	boolean isEnabled(MapContext mapContext);

	void execute(MapContext mapContext);

	int getGroupOrder();

	String getGroup();

	int getOrder();

}
