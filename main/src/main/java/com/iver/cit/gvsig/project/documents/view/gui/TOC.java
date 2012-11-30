package com.iver.cit.gvsig.project.documents.view.gui;

import org.gvsig.NotAvailableLabel;
import org.gvsig.map.MapContext;

public class TOC extends NotAvailableLabel {

	public TOC() {
		/*
		 * gtintegration This line was removed from View. I mean: don't forget
		 * to listen legend changes in TOC
		 */
		// m_MapControl.getMapContext().getLayers().addLegendListener(m_TOC);
	}

	public void setMapContext(MapContext fmap) {
	}

}
