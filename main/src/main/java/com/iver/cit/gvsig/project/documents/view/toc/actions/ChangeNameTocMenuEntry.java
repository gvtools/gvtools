package com.iver.cit.gvsig.project.documents.view.toc.actions;

import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.ProjectExtension;
import com.iver.cit.gvsig.project.Project;
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
 * $Id: ChangeNameTocMenuEntry.java 9532 2007-01-04 07:24:32Z caballero $
 * $Log$
 * Revision 1.2  2007-01-04 07:24:31  caballero
 * isModified
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
 * Realiza un cambio de nombre en la capa seleccionada
 * 
 * @author Vicente Caballero Navarro
 */
public class ChangeNameTocMenuEntry implements ILayerAction {
	public String getGroup() {
		return "group1"; // FIXME
	}

	public int getGroupOrder() {
		return 10;
	}

	public int getOrder() {
		return 1;
	}

	public String getText() {
		return PluginServices.getText(this, "cambio_nombre");
	}

	public boolean isEnabled(MapContext mapContext) {
		return getSelected(mapContext).length == 1;
	}

	private Layer[] getSelected(MapContext mapContext) {
		return mapContext.getRootLayer().filter(LayerFilter.SELECTED);
	}

	public boolean isVisible(MapContext mapContext) {
		return true;
	}

	public void execute(MapContext mapContext) {
		Layer lyr = getSelected(mapContext)[0];
		ChangeName chn = new ChangeName(lyr.getName());
		PluginServices.getMDIManager().addWindow(chn);
		lyr.setName(chn.getName());
		Project project = ((ProjectExtension) PluginServices
				.getExtension(ProjectExtension.class)).getProject();
		project.setModified(true);
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this, "name_change_tooltip");
	}
}
