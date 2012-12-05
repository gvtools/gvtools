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

import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.gui.View;

/**
 * Extensi�n que cambia el extent de la vista al extent anterior.
 * 
 * @author Vicente Caballero Navarro
 */
public class ZoomPrev extends Extension {
	/**
	 * @see com.iver.mdiApp.plugins.IExtension#isEnabled()
	 */
	public boolean isEnabled() {
		View vista = (View) PluginServices.getMDIManager().getActiveWindow();
		ViewPort vp = vista.getMapControl().getViewPort();

		return vp.getExtents().hasPrevious();
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
			View v = (View) f;

			MapContext mapa = v.getModel().getMapContext();

			return mapa.getRootLayer().getChildren().length > 0;
		}
		return false;
	}

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#updateUI(java.lang.String)
	 */
	public void execute(String arg0) {
		View vista = (View) PluginServices.getMDIManager().getActiveWindow();
		ViewPort vp = vista.getMapControl().getViewPort();
		vp.setPreviousExtent();
		((ProjectDocument) vista.getModel()).setModified(true);
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#initialize()
	 */
	public void initialize() {

		PluginServices.getIconTheme().registerDefault(
				"view-zoom-back",
				this.getClass().getClassLoader()
						.getResource("images/ZoomPrevio.png"));
	}
}
