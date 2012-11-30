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

import com.iver.andami.plugins.IExtension;
import com.iver.utiles.swing.threads.IMonitorableTask;

/**
 * <p>
 * This interface provides a framework to query the status of extensions. By
 * default, the interface provides methods to check if the extension has some
 * unsaved data (and save them), and methods to check if the extension has some
 * associated background tasks. However, additional aspects can be included in
 * the status by extending this interface.
 * </p>
 * 
 * <p>
 * Each extension will have an associated IExtensionStatus object, which can be
 * used at any time to check the status. This is specially useful during the
 * Andami termination process, to check if there are unfinished tasks which
 * should be attended before exiting.
 * </p>
 * 
 * @see IUnsavedData
 * @see UnsavedData
 * @see IExtension
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 * 
 */
public interface IExtensionStatus {
	/**
	 * This method is used to check if the extension has some unsaved data. For
	 * example, if the project has been modified, or there is a layer in edition
	 * mode.
	 * 
	 * @return true if the extension has some unsaved data, false otherwise.
	 */
	public boolean hasUnsavedData();

	/**
	 * <p>
	 * Gets an array of the UnsavedData objects, which contain information about
	 * the unsaved data and allows to save it.
	 * </p>
	 * 
	 * @return An array of the associated unsaved data, or null in case the
	 *         extension has not unsaved data.
	 */
	public IUnsavedData[] getUnsavedData();

	/**
	 * This method is used to check if the extension has some associated
	 * background process which is currently running.
	 * 
	 * @return true if the extension has some associated background process,
	 *         false otherwise.
	 */
	public boolean hasRunningProcesses();

	/**
	 * <p>
	 * Gets an array of the traceable background tasks associated with this
	 * extension. These tasks may be tracked, canceled, etc.
	 * </p>
	 * 
	 * @return An array of the associated background tasks, or null in case
	 *         there is no associated background tasks.
	 */
	public IMonitorableTask[] getRunningProcesses();

	// public IStatusGUI getGUI();
}
