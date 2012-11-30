/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2004-2007 IVER T.I. and Generalitat Valenciana.
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
package com.iver.andami.ui.mdiManager;

/**
 * The IWindow interface should be implemented by any panel which is intended to
 * be an Andami window. The JPanel implementing IWindow will be the contents of
 * the window, and the frame containing it will be created by Andami.
 * 
 * @see MDIManager
 * @see WindowInfo
 * @see SingletonWindow
 * @see IWindowListener
 * 
 * @author Fernando González Cortés
 */
public interface IWindow {
	/**
	 * <p>
	 * Gets the initial window properties. It must be called just once from the
	 * framework. To get the current WindowInfo from this window, use
	 * <code>PluginServices.getMDIManager.getWindowInfo(window);</code>
	 * </p>
	 * 
	 * @return A WindowInfo object, containing the properties that this window
	 *         should have when created
	 */
	public WindowInfo getWindowInfo();

	/**
	 * <p>
	 * Gets the profile for this window
	 * 
	 * @return An object with the profile.
	 */
	public Object getWindowProfile();
}
