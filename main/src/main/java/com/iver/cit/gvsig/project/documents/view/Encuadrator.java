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
package com.iver.cit.gvsig.project.documents.view;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.ProjectExtent;
import com.iver.cit.gvsig.project.ProjectFactory;
import com.iver.cit.gvsig.project.documents.view.gui.View;

public class Encuadrator implements ListSelectorListener {

	private Project project;
	private View vista;
	private MapControl mapa;

	public Encuadrator(Project project, MapControl map, View vista) {
		this.project = project;
		this.vista = vista;
		mapa = map;
	}

	/**
	 * @see com.iver.utiles.ListSelectorListener#indexesSelected(int[])
	 */
	public void indexesSelected(int[] indices) {
		mapa.getViewPort().setExtent(
				project.getExtents()[indices[0]].getExtent());
	}

	/**
	 * @see com.iver.utiles.ListSelectorListener#indexesRemoved(int[])
	 */
	public void indexesRemoved(int[] indices) {
		for (int i = 0; i < indices.length; i++) {
			project.removeExtent(indices[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.utiles.ListSelectorListener#newElement(java.lang.String)
	 */
	public void newElement(String name) {
		ProjectExtent extent = ProjectFactory.createExtent();
		extent.setDescription(name);
		extent.setExtent(mapa.getViewPort().getExtent());
		project.addExtent(extent);
	}

}
