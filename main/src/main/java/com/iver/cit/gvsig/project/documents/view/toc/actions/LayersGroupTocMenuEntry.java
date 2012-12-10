package com.iver.cit.gvsig.project.documents.view.toc.actions;

import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.view.toc.gui.ChangeName;
import com.iver.cit.gvsig.project.documents.view.toc.gui.ILayerAction;

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
/* CVS MESSAGES:
 *
 * $Id: LayersGroupTocMenuEntry.java 24624 2008-11-03 08:20:00Z vcaballero $
 * $Log$
 * Revision 1.7  2007-06-19 08:42:17  jcampos
 * New method to get new group layers
 *
 * Revision 1.6  2007/02/15 11:04:54  caballero
 * cancelar agrupaci�n
 *
 * Revision 1.5  2007/02/14 17:09:43  caballero
 * posici�n layerGroup
 *
 * Revision 1.4  2007/01/04 07:24:31  caballero
 * isModified
 *
 * Revision 1.3  2006/10/02 13:52:34  jaume
 * organize impots
 *
 * Revision 1.2  2006/09/25 15:21:47  jmvivo
 * * Modificada la condicion de visibilidad, no permitir si no tienen el mismo padre.
 * * Modificada la implementacion.
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
 * Realiza una agrupaci�n de capas, a partir de las capas que se encuentren
 * activas.
 * 
 * @author Vicente Caballero Navarro
 */
public class LayersGroupTocMenuEntry extends AbstractLayerAction implements
		ILayerAction {
	public String getGroup() {
		return "group4"; // FIXME
	}

	public int getGroupOrder() {
		return 40;
	}

	public int getOrder() {
		return 0;
	}

	public String getText() {
		return PluginServices.getText(this, "agrupar_capas");
	}

	public boolean isEnabled(MapContext mapContext) {
		return getSelected(mapContext).length > 1;
	}

	public boolean isVisible(MapContext mapContext) {
		Layer[] selectedLayers = getSelected(mapContext);
		if (selectedLayers.length < 2) {
			return false;
		}
		Layer parent = selectedLayers[0].getParent();
		for (int i = 1; i < selectedLayers.length; i++) {
			if (parent != selectedLayers[i].getParent()) {
				return false;
			}
		}
		return true;

	}

	public void execute(MapContext mapContext, MapControl mapControl) {
		Layer[] selectedLayers = getSelected(mapContext);
		// ITocItem tocItem = (ITocItem) getNodeUserObject();
		ChangeName changename = new ChangeName(null);
		PluginServices.getMDIManager().addWindow(changename);
		if (!changename.isAccepted())
			return;
		String nombre = changename.getName();

		if (nombre != null) {

			Layer parent = selectedLayers[0].getParent();
			// FLayers newGroup = new FLayers(getMapContext(),parent);
			LayerFactory layerFactory = InjectorSingleton.getInjector()
					.getInstance(LayerFactory.class);
			Layer newGroup = layerFactory.createLayer(nombre);
			int pos = 0;
			Layer[] children = parent.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i].equals(selectedLayers[0])) {
					pos = i;
					continue;
				}
			}
			for (int j = 0; j < selectedLayers.length; j++) {
				Layer layer = selectedLayers[j];
				parent.removeLayer(layer);
				newGroup.addLayer(layer);
			}
			parent.addLayer(pos, newGroup);
		}
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this, "group_layers_");
	}
}
