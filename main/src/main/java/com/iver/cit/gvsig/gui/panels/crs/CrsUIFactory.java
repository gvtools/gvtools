package com.iver.cit.gvsig.gui.panels.crs;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.cit.gvsig.project.documents.view.info.gui.CSSelectionDialog;

public class CrsUIFactory implements ICrsUIFactory {

	public ISelectCrsPanel getSelectCrsPanel(CoordinateReferenceSystem crs,
			boolean isTransPanelActive) {
		CSSelectionDialog panel = new CSSelectionDialog();
		panel.setCrs(crs);
		return panel;
	}

}
