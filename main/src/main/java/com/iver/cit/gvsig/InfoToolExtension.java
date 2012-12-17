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

import org.apache.log4j.Logger;
import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.IProjectView;
import com.iver.cit.gvsig.project.documents.view.gui.BaseView;

/**
 * Extension that handles the info by point tool for a selected layer set
 * 
 * @author laura
 */
public class InfoToolExtension extends Extension {
	private static Logger logger = Logger.getLogger(InfoToolExtension.class
			.getName());

	public void execute(String s) {
		BaseView vista = (BaseView) PluginServices.getMDIManager()
				.getActiveWindow();
		MapControl mapCtrl = vista.getMapControl();
		logger.debug("Comand : " + s);

		if (s.compareTo("INFO") == 0) {
			mapCtrl.setTool("info");
			((ProjectDocument) vista.getModel()).setModified(true);
		}
	}

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#isVisible()
	 */
	public boolean isEnabled() {
		com.iver.andami.ui.mdiManager.IWindow f = PluginServices
				.getMDIManager().getActiveWindow();

		if (f == null) {
			return false;
		}

		if (f instanceof BaseView) {
			BaseView vista = (BaseView) f;
			IProjectView model = vista.getModel();
			MapContext mapa = model.getMapContext();

			Layer[] layers = mapa.getRootLayer().filter(LayerFilter.SELECTED);
			return layers.length > 0;
		}
		return false;
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#initialize()
	 */
	public void initialize() {
		registerIcons();
	}

	private void registerIcons() {
		PluginServices.getIconTheme().registerDefault(
				"view-query-information",
				InfoToolExtension.class.getClassLoader().getResource(
						"images/Identify.png"));
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#isEnabled()
	 */
	public boolean isVisible() {
		com.iver.andami.ui.mdiManager.IWindow f = PluginServices
				.getMDIManager().getActiveWindow();

		if (f == null) {
			return false;
		}

		if (f instanceof BaseView) {
			BaseView vista = (BaseView) f;
			IProjectView model = vista.getModel();
			MapContext mapa = model.getMapContext();
			return mapa.getRootLayer().getChildren().length > 0;
		} else {
			return false;
		}
	}
}
