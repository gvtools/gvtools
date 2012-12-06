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
package com.iver.cit.gvsig.gui.selectionByTheme;

import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.AndLayerFilter;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;

public class DefaultSelectionByThemeModel implements SelectionByThemeModel {

	/**
	 * @see com.iver.cit.gvsig.gui.selectionByTheme.SelectionByThemeModel#getViews()
	 */
	public Layer[] getLayers() {
		MapContext mapContext = getCurrentMapContext();
		if (mapContext != null) {
			return mapContext.getRootLayer().filter(LayerFilter.FEATURE);
		} else {
			return new Layer[0];
		}
	}

	private MapContext getCurrentMapContext() {
		MapContext mapContext = null;
		IWindow v = PluginServices.getMDIManager().getActiveWindow();
		if (v instanceof com.iver.cit.gvsig.project.documents.view.gui.View) {
			com.iver.cit.gvsig.project.documents.view.gui.View vista = (com.iver.cit.gvsig.project.documents.view.gui.View) v;

			mapContext = vista.getModel().getMapContext();
		}
		return mapContext;
	}

	@Override
	public Layer[] getSelected() {
		MapContext mapContext = getCurrentMapContext();
		if (mapContext != null) {
			return mapContext.getRootLayer().filter(
					new AndLayerFilter(LayerFilter.FEATURE,
							LayerFilter.SELECTED));
		} else {
			return new Layer[0];
		}
	}

}
