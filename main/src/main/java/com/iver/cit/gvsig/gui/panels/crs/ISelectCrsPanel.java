package com.iver.cit.gvsig.gui.panels.crs;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.ui.mdiManager.IWindow;

public interface ISelectCrsPanel extends IWindow {
	public CoordinateReferenceSystem getCrs();

	public void setCrs(CoordinateReferenceSystem crs);

	public boolean isOkPressed();
}
