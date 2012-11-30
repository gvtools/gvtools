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
package com.iver.cit.gvsig.project;

import com.iver.cit.gvsig.project.documents.ProjectDocumentFactory;
import com.iver.cit.gvsig.project.documents.view.ProjectView;
import com.iver.cit.gvsig.project.documents.view.ProjectViewFactory;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

public class ProjectFactory {

	// gtrefactor We don't want maps yet
	// public static ProjectMap createMap(String baseName) {
	// ExtensionPoints extensionPoints = ExtensionPointsSingleton
	// .getInstance();
	// ExtensionPoint extPoint = ((ExtensionPoint) extensionPoints
	// .get("Documents"));
	// ProjectDocumentFactory pdf = null;
	// try {
	// pdf = (ProjectDocumentFactory) extPoint
	// .create(ProjectMapFactory.registerName);
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// ProjectMap pm = (ProjectMap) pdf.create((Project) null);
	// pm.setProjectDocumentFactory(pdf);
	// pm.setName(baseName);
	// return pm;
	// }

	// gtrefactor we don't want tables yet
	// public static ProjectTable createTable(String name, IEditableSource ies)
	// {
	// ExtensionPoints extensionPoints = ExtensionPointsSingleton
	// .getInstance();
	// ExtensionPoint extPoint = ((ExtensionPoint) extensionPoints
	// .get("Documents"));
	// ProjectDocumentFactory pdf = null;
	// try {
	// pdf = (ProjectDocumentFactory) extPoint
	// .create(ProjectTableFactory.registerName);
	// } catch (InstantiationException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	//
	// ProjectTable pt = ProjectTableFactory.createTable(name, ies);
	// pt.setProjectDocumentFactory(pdf);
	// return pt;
	// }

	// TODO implementar bien
	/*
	 * public static ProjectTable createTable(String viewName, FTable ftable){
	 * return Table.createTable(viewName, ftable); }
	 */
	public static ProjectView createView(String viewName) {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();
		ExtensionPoint extPoint = extensionPoints.get("Documents");
		ProjectDocumentFactory pdf = null;
		try {
			pdf = (ProjectDocumentFactory) extPoint
					.create(ProjectViewFactory.registerName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		ProjectView pv = (ProjectView) pdf.create((Project) null);
		pv.setProjectDocumentFactory(pdf);
		pv.setName(viewName);
		return pv;
	}

	public static Project createProject() {
		return new Project();
	}

	public static ProjectExtent createExtent() {
		return new ProjectExtent();
	}

}
