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
 * <p>
 * This interface represents some unsaved data, associated to one extension.
 * There are methods to get the associated extension, to get info about these
 * data, to get the type of the data and a suitable icon for this type, and a
 * method to save the data.
 * </p>
 * 
 * <p>
 * It is used during the Andami termination process, to construct the dialog of
 * unsaved data, although it can be used at any time.
 * </p>
 * 
 * <p>
 * Normally, it should not be directly implemented, use the convenience
 * UnsavedData class.
 * </p>
 * 
 * @see IExtensionStatus
 * @see UnsavedData
 * @see IExtension
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 */
public interface IUnsavedData {

	/**
	 * Gets the resource name of this unsaved data. Normally, this will be a
	 * file path, but it may be different for certain type of unsaved data (for
	 * example, database connections).
	 * 
	 * @return The resource name of this unsaved data
	 */
	public String getResourceName();

	/**
	 * <p>
	 * Gets a description of this unsaved data. This would be combined with the
	 * resource name to show a coherent information to the user.
	 * </p>
	 * 
	 * <p>
	 * Examples of descriptions:
	 * <ul>
	 * <li>Modified SHP Layer</li>
	 * <li>Modified gvSIG project</li>
	 * </ul>
	 * 
	 * @return A description for this unsaved data, probably containing the type
	 *         of data and the kind of modification.
	 */
	public String getDescription();

	/**
	 * <p>
	 * Save the existing changes for this resource (for example, save the layer
	 * to disk or to the database, etc). The resource should not be closed at
	 * this point (files, database connections, etc should no be closed), as
	 * they may be still needed by other extensions. Resources should be closed
	 * at {@link IExtension#terminate()}.
	 * </p>
	 * 
	 * @return true if the data was correctly saved, false if it was not saved
	 *         (there are many reasons for this: there was an error, the user
	 *         cancelled the process, etc).
	 */
	public boolean saveData();

	/**
	 * Each IUnsavedData object is associated with one extension, which is in
	 * charge of this data. This method gets the extension associated with this
	 * object.
	 * 
	 * @return The extension associated with this IUnsavedData object.
	 */
	public IExtension getExtension();

	/**
	 * Gets an icon suitable to represent this type of data.
	 * 
	 * @return An icon representing this type of data, or null if no icon is
	 *         available
	 */
	public ImageIcon getIcon();

}
