/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindowListener;
import com.iver.andami.ui.mdiManager.IWindowTransform;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.gui.IDocumentWindow;
import com.iver.cit.gvsig.project.documents.gui.WindowData;
import com.iver.cit.gvsig.project.documents.view.IProjectView;
import com.iver.utiles.exceptionHandling.ExceptionListener;

//import javax.swing.JSplitPane;
//import com.iver.cit.gvsig.gui.panels.MapOverViewPalette;
//import java.util.HashMap;

public abstract class BaseView extends JPanel implements IView,
		IWindowListener, IWindowTransform, SingletonWindow, IDocumentWindow {

	protected MapControl m_MapControl;
	protected MapOverview m_MapLoc;

	// store the properties of the window
	protected WindowInfo m_viewInfo = null;

	/** DOCUMENT ME! */
	protected TOC m_TOC;
	protected IProjectView modelo;
	protected ViewExceptionListener mapControlExceptionListener = new ViewExceptionListener();

	protected boolean isPalette = false;
	protected MapOverViewPalette movp;
	protected ViewSplitPane tempMainSplit = null;
	protected JSplitPane tempSplitToc = null;

	protected WindowData windowData = null;

	// OVERRIDE THESE
	public void windowActivated() {
	}

	public void toPalette() {
	}

	public void restore() {
	}

	protected class ViewSplitPane extends JSplitPane {
		private int lastDivider = 0;

		public ViewSplitPane(int horizontal_split) {
			super(horizontal_split);
		}

		protected void paintChildren(Graphics g) {
			if (lastDivider != lastDividerLocation) {
				System.out.println("paintChildren = "
						+ this.lastDividerLocation);
				// getMapControl().getMapContext().clearAllCachingImageDrawnLayers();
				lastDivider = lastDividerLocation;
			}
			super.paintChildren(g);
		}

	}

	/**
	 * Creates a new View object. Before using it, it must be initialized using
	 * the <code>initialize()</code> method.
	 * 
	 * @see initialize()
	 */
	public BaseView() {

	}

	/**
	 * Create the internal componentes and populate the window with them. If the
	 * layout properties were set using the
	 * <code>setWindowData(WindowData)</code> method, the window will be
	 * populated according to this properties.
	 */
	public void initialize() {
	}

	/**
	 * This method is used to get <strong>an initial</strong> ViewInfo object
	 * for this View. It is not intended to retrieve the ViewInfo object in a
	 * later time. <strong>Use PluginServices.getMDIManager().getViewInfo(view)
	 * to retrieve the ViewInfo object at any time after the creation of the
	 * object.
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		if (m_viewInfo == null) {
			m_viewInfo = new WindowInfo(WindowInfo.ICONIFIABLE
					| WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE);

			m_viewInfo.setWidth(500);
			m_viewInfo.setHeight(300);
			m_viewInfo.setTitle(PluginServices.getText(this, "Vista") + " : "
					+ modelo.getName());
		}
		return m_viewInfo;
	}

	/**
	 * @see com.iver.andami.ui.mdiManager.IWindowListener#windowClosed()
	 */
	public void windowClosed() {
		if (movp != null)
			PluginServices.getMDIManager().closeWindow(movp);
		// /PluginServices.getMainFrame().getStatusBar().setMessage("1","");

		if (modelo != null)
			modelo.storeWindowData(getWindowData());
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public IProjectView getModel() {
		return modelo;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public MapOverview getMapOverview() {
		return m_MapLoc;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public MapControl getMapControl() {
		return m_MapControl;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public TOC getTOC() {
		return m_TOC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.SingletonView#getModel()
	 */

	public Object getWindowModel() {
		return modelo;
	}

	/**
	 * This method is used to get <strong>an initial</strong> ViewInfo object
	 * for this View. It is not intended to retrieve the ViewInfo object in a
	 * later time. <strong>Use PluginServices.getMDIManager().getViewInfo(view)
	 * to retrieve the ViewInfo object at any time after the creation of the
	 * object.
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */

	public boolean isPalette() {
		return isPalette;
	}

	/**
	 * DOCUMENT ME!
	 */
	public void repaintMap() {
		m_MapControl.drawMap(false);
	}

	public class ViewExceptionListener implements ExceptionListener {

		/**
		 * @see com.iver.cit.gvsig.fmap.ExceptionListener#exceptionThrown(java.lang.Throwable)
		 */
		public void exceptionThrown(Throwable t) {
			NotificationManager.addError(t.getMessage(), t);
		}

	}

	/**
	 * @return
	 */
	public BufferedImage getImage() {
		return m_MapControl.getImage();
	}

	public void setCrs(CoordinateReferenceSystem proj) {
		getMapControl().setCrs(proj);
	}

	public CoordinateReferenceSystem getCrs() {
		return getMapControl().getCrs();
	}

	public WindowData getWindowData() {
		updateWindowData();
		return windowData;
	}

	private void updateWindowData() {
		if (windowData == null)
			windowData = new WindowData();
		windowData.set("MainWindow.X", Integer.toString(this.getX()));
		windowData.set("MainWindow.Y", Integer.toString(this.getY()));
		windowData.set("MainWindow.Width", Integer.toString(this.getWidth()));
		windowData.set("MainWindow.Height", Integer.toString(this.getHeight()));

		windowData.set("MainDivider.Location",
				Integer.toString(tempMainSplit.getDividerLocation()));
		windowData.set("MainDivider.X", Integer.toString(tempMainSplit.getX()));
		windowData.set("MainDivider.Y", Integer.toString(tempMainSplit.getY()));
		windowData.set("MainDivider.Width",
				Integer.toString(tempMainSplit.getWidth()));
		windowData.set("MainDivider.Height",
				Integer.toString(tempMainSplit.getHeight()));

		if (isPalette())
			windowData.set("GraphicLocator.isPalette", "true");
		else {
			windowData.set("GraphicLocator.isPalette", "false");
			if (tempSplitToc != null) {
				windowData.set("TOCDivider.Location",
						Integer.toString(tempSplitToc.getDividerLocation()));
				windowData.set("TOCDivider.X",
						Integer.toString(tempSplitToc.getX()));
				windowData.set("TOCDivider.Y",
						Integer.toString(tempSplitToc.getY()));
				windowData.set("TOCDivider.Width",
						Integer.toString(tempSplitToc.getWidth()));
				windowData.set("TOCDivider.Height",
						Integer.toString(tempSplitToc.getHeight()));
			}
		}
		if (m_TOC != null) {
			windowData.set("TOC.Width", Integer.toString(m_TOC.getWidth()));
			windowData.set("TOC.Height", Integer.toString(m_TOC.getHeight()));
		}
		if (m_MapControl != null) {
			windowData.set("MapControl.Width",
					Integer.toString(m_MapControl.getWidth()));
			windowData.set("MapControl.Height",
					Integer.toString(m_MapControl.getHeight()));
		}
		if (m_MapLoc != null) {
			windowData.set("GraphicLocator.Width",
					Integer.toString(m_MapLoc.getWidth()));
			windowData.set("GraphicLocator.Height",
					Integer.toString(m_MapLoc.getHeight()));
		}

	}

	public void setWindowData(WindowData data) {
		windowData = data;
	}
}
