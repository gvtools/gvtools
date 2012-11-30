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

package com.iver.andami.plugins;

/**
 * Extensions implementing this interface are able to take control over the user
 * interface, by deciding which of the other extensions will be enabled/disabled
 * or visible/hidden.
 * 
 * Besides implementing this interface, the extension needs to be set as
 * ExclusiveUIExtension during Andami startup process. This is performed by
 * providing a command line argument to Andami: <br>
 * <code>ExclusiveUIExtension=ExtensionName</code>
 * 
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 */
public interface ExclusiveUIExtension extends IExtension {

	/**
	 * This method is used when this extension is installed as
	 * ExclusiveUIExtension. This extension will be asked for each installed
	 * extension to determine which of them should be enabled.
	 * 
	 * @return true if the provided extension should be enabled, false if it
	 *         should be disabled.
	 */
	public boolean isEnabled(IExtension extension);

	/**
	 * This method is used when this extension is installed as
	 * ExclusiveUIExtension. This extension will be asked for each installed
	 * extension to determine which of them should be visible.
	 * 
	 * @return true if the provided extension should be visible, false if it
	 *         should be hidden.
	 */
	public boolean isVisible(IExtension extension);
}
