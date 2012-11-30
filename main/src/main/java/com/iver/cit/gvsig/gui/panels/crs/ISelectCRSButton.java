package com.iver.cit.gvsig.gui.panels.crs;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

public interface ISelectCRSButton {
	public boolean isTransPanelActive();

	public void setTransPanelActive(boolean transPanelActive);

	public CoordinateReferenceSystem getCurrentCrs();

	public boolean isOkPressed();

	public void setCurrentCrs(CoordinateReferenceSystem curProj);
}
