/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2007 IVER T.I. and Generalitat Valenciana.
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
package com.iver.andami.plugins.status;

import javax.swing.ImageIcon;

import com.iver.andami.plugins.IExtension;

/**
 * Abstract class implementing IUnsavedData. is a convenience class used to
 * allow easier modifications of IUnsaveData interface.
 * 
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 * 
 */
public abstract class UnsavedData implements IUnsavedData {

	/**
	 * This UnsavedData object is associated to this extension.
	 */
	private IExtension ext;

	/**
	 * Creates a new UnsavedData object which will be associated to the provided
	 * extension.
	 * 
	 * @param extension
	 */
	public UnsavedData(IExtension extension) {
		ext = extension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.status.IUnsavedData#getDescription()
	 */
	public abstract String getDescription();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.status.IUnsavedData#getExtension()
	 */
	public IExtension getExtension() {
		return ext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.status.IUnsavedData#getIcon()
	 */
	public ImageIcon getIcon() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.status.IUnsavedData#getResourceName()
	 */
	public abstract String getResourceName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.status.IUnsavedData#saveData()
	 */
	public abstract boolean saveData();

}
