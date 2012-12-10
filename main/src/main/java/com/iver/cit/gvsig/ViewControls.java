/*
 * Created on 20-feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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
package com.iver.cit.gvsig;

import geomatico.events.EventBus;
import geomatico.events.ExceptionEvent;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.map.MapContext;
import org.gvsig.util.EnvelopeUtils;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.preferences.IPreference;
import com.iver.andami.preferences.IPreferenceExtension;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.gui.preferencespage.ViewPage;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.Encuadrator;
import com.iver.cit.gvsig.project.documents.view.IProjectView;
import com.iver.cit.gvsig.project.documents.view.gui.ExtentListSelectorModel;
import com.iver.cit.gvsig.project.documents.view.gui.FPanelExtentSelector;
import com.iver.cit.gvsig.project.documents.view.gui.FPanelLocConfig;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

//import com.iver.utiles.FPanelExtentSelector;

/**
 * Extensi�n que controla las operaciones b�sicas realizadas sobre la vista.
 * 
 * @author vcn
 */
public class ViewControls extends Extension implements IPreferenceExtension {
	private static Logger logger = Logger.getLogger(ViewControls.class
			.getName());
	private static ViewPage viewPropertiesPage = new ViewPage();

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#updateUI(java.lang.String)
	 */
	public void execute(String s) {
		com.iver.andami.ui.mdiManager.IWindow view = PluginServices
				.getMDIManager().getActiveWindow();
		if (!(view instanceof View))
			return;
		View vista = (View) view;
		IProjectView model = vista.getModel();
		MapContext mapa = model.getMapContext();
		MapControl mapCtrl = vista.getMapControl();
		logger.debug("Comand : " + s);

		if (s.equals("FULL")) {
			// mapa.beginAtomicEvent();
			try {
				Rectangle2D fullExtent = EnvelopeUtils.toRectangle2D(mapa
						.getRootLayer().getBounds());
				mapCtrl.getViewPort().setExtent(fullExtent);
				((ProjectDocument) vista.getModel()).setModified(true);
			} catch (IOException e) {
				EventBus eventBus = InjectorSingleton.getInjector()
						.getInstance(EventBus.class);
				eventBus.fireEvent(new ExceptionEvent("Cannot calculate "
						+ "the map full extent", e));
			}
			// mapa.endAtomicEvent();
		} else if (s.equals("ENCUADRE")) {
			FPanelExtentSelector l = new FPanelExtentSelector();

			ProjectExtension p = (ProjectExtension) PluginServices
					.getExtension(com.iver.cit.gvsig.ProjectExtension.class);
			Project project = p.getProject();
			ExtentListSelectorModel modelo = new ExtentListSelectorModel(
					project);
			// ProjectExtent[] extents = project.getExtents();
			project.addPropertyChangeListener(modelo);
			l.setModel(modelo);
			l.addSelectionListener(new Encuadrator(project, mapCtrl, vista));
			((ProjectDocument) vista.getModel()).setModified(true);
			PluginServices.getMDIManager().addWindow(l);
		} else if (s.equals("CONFIG_LOCATOR")) {
			// Set up the map overview
			FPanelLocConfig m_panelLoc = new FPanelLocConfig(
					vista.getMapOverview());
			PluginServices.getMDIManager().addWindow(m_panelLoc);
			m_panelLoc.setPreferredSize(m_panelLoc.getSize());
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("PAN")) {
			mapCtrl.setTool("pan");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("ZOOM_IN")) {
			mapCtrl.setTool("zoomIn");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("ZOOM_OUT")) {
			mapCtrl.setTool("zoomOut");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("CAPAS_VISIBLES")) {
			mapa.getRootLayer().setVisible(true);
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("CAPAS_NOVISIBLES")) {
			mapa.getRootLayer().setVisible(false);
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("CAPAS_ACTIVAS")) {
			mapa.getRootLayer().setSelected(true);
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("CAPAS_NOACTIVAS")) {
			mapa.getRootLayer().setSelected(false);
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("SAVERASTER")) {
			mapCtrl.setTool("saveRaster");
		} else if (s.equals("SELECTIMAGE")) {
			mapCtrl.setTool("selectImage");
		} else if (s.equals("ACTION_ZOOM_IN")) {
			mapCtrl.zoomIn();
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.equals("ACTION_ZOOM_OUT")) {
			mapCtrl.zoomOut();
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (s.startsWith("CHANGE_SCALE_")) {
			String scl = s.replaceAll("CHANGE_SCALE_", "");
			long scale = Long.parseLong(scl);
			mapCtrl.getViewPort().setScale(scale);
			((ProjectDocument) vista.getModel()).setModified(true);
		}
	}

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#isVisible()
	 */
	public boolean isVisible() {
		com.iver.andami.ui.mdiManager.IWindow f = PluginServices
				.getMDIManager().getActiveWindow();

		if (f == null) {
			return false;
		}

		if (f instanceof View) {
			View vista = (View) f;
			IProjectView model = vista.getModel();
			MapContext mapa = model.getMapContext();

			return mapa.getRootLayer().getChildren().length > 0;
		}
		return false;
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#initialize()
	 */
	public void initialize() {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();

		if (!extensionPoints.containsKey("View_TocActions")) {
			extensionPoints
					.put(new ExtensionPoint(
							"View_TocActions",
							"Context menu options of the TOC "
									+ " in the view window "
									+ "(register instances of "
									+ "com.iver.cit.gvsig.gui.toc.AbstractTocContextMenuAction)"));
		}

		registerIcons();
	}

	private void registerIcons() {

		PluginServices.getIconTheme().registerDefault(
				"view-zoom-in",
				this.getClass().getClassLoader()
						.getResource("images/ZoomIn.png"));
		PluginServices.getIconTheme().registerDefault(
				"view-zoom-out",
				this.getClass().getClassLoader()
						.getResource("images/ZoomOut.png"));
		PluginServices.getIconTheme().registerDefault(
				"view-zoom-map-contents",
				this.getClass().getClassLoader()
						.getResource("images/MapContents.png"));
		PluginServices.getIconTheme().registerDefault(
				"view-zoom-center-in",
				this.getClass().getClassLoader()
						.getResource("images/zoommas.png"));
		PluginServices.getIconTheme().registerDefault(
				"view-zoom-center-out",
				this.getClass().getClassLoader()
						.getResource("images/zoommenos.png"));

		PluginServices.getIconTheme().registerDefault("view-pan",
				this.getClass().getClassLoader().getResource("images/Pan.png"));

		PluginServices.getIconTheme().registerDefault(
				"view-zoom-manager",
				this.getClass().getClassLoader()
						.getResource("images/encuadre.png"));
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#isEnabled()
	 */
	public boolean isEnabled() {
		return isVisible();
	}

	public IPreference[] getPreferencesPages() {
		IPreference[] preferences = new IPreference[1];
		preferences[0] = viewPropertiesPage;
		return preferences;
	}
}
