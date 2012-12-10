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
package com.iver.cit.gvsig.project.documents.view.toc.actions;

import org.gvsig.layer.Layer;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.view.toc.gui.ILayerAction;

/* CVS MESSAGES:
 *
 * $Id: LayersUngroupTocMenuEntry.java 24624 2008-11-03 08:20:00Z vcaballero $
 * $Log$
 * Revision 1.6  2007-01-04 07:24:31  caballero
 * isModified
 *
 * Revision 1.5  2006/10/02 13:52:34  jaume
 * organize impots
 *
 * Revision 1.4  2006/09/25 15:24:26  jmvivo
 * * Modificada la implementacion.
 *
 * Revision 1.3  2006/09/20 12:01:24  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/20 11:41:20  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/09/15 10:41:30  caballero
 * extensibilidad de documentos
 *
 * Revision 1.1  2006/09/12 15:58:14  jorpiell
 * "Sacadas" las opcines del men� de FPopupMenu
 *
 *
 */
/**
 * Realiza una desagrupaci�n de capas, a partir de las capas que se encuentren
 * activas.
 * 
 * @author Vicente Caballero Navarro
 */
public class LayersUngroupTocMenuEntry extends AbstractLayerAction implements
		ILayerAction {
	public String getGroup() {
		return "group4"; // FIXME
	}

	public int getGroupOrder() {
		return 40;
	}

	public int getOrder() {
		return 1;
	}

	public String getText() {
		return PluginServices.getText(this, "desagrupar_capas");
	}

	public boolean isEnabled(MapContext mapContext) {
		return true;
	}

	public boolean isVisible(MapContext mapContext) {
		Layer[] selected = getSelected(mapContext);
		for (Layer layer : selected) {
			if (layer.getChildren().length == 0) {
				return false;
			}
		}
		return selected.length > 0;
	}

	public void execute(MapContext mapContext, MapControl mapControl) {
		Layer[] selected = getSelected(mapContext);
		for (Layer layer : selected) {
			Layer[] toAdd = layer.getChildren();
			Layer parent = layer.getParent();
			int position = parent.indexOf(layer);
			parent.removeLayer(layer);
			for (Layer groupedLayer : toAdd) {
				layer.removeLayer(groupedLayer);
				parent.addLayer(position++, groupedLayer);
			}
		}
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this, "ungroup_layer_tooltip");
	}
}
