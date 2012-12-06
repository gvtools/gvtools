/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.iver.cit.gvsig.project.Project;

public class ExtentListSelectorModel implements ListModel,
		PropertyChangeListener {

	private Project project;
	private ArrayList listeners = new ArrayList();

	public ExtentListSelectorModel(Project p) {
		project = p;
	}

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return project.getExtents().length;
	}

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int arg0) {
		return project.getExtents()[arg0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener
	 * )
	 */
	public void addListDataListener(ListDataListener arg0) {
		listeners.add(arg0);
	}

	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener arg0) {
		listeners.remove(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent change) {
		if (change.getPropertyName().equals("addExtent")) {
			ListDataEvent event = new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, 0, getSize());
			for (int i = 0; i < listeners.size(); i++) {
				((ListDataListener) listeners.get(i)).intervalAdded(event);
			}
		} else {
			ListDataEvent event = new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, 0, getSize());
			for (int i = 0; i < listeners.size(); i++) {
				((ListDataListener) listeners.get(i)).intervalRemoved(event);
			}
		}
	}

}
