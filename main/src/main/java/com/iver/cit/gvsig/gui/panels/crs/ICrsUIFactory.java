package com.iver.cit.gvsig.gui.panels.crs;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

public interface ICrsUIFactory {
	public ISelectCrsPanel getSelectCrsPanel(CoordinateReferenceSystem crs,
			boolean isTransPanelActive);
}
