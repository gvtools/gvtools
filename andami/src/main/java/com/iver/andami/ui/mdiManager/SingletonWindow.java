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
package com.iver.andami.ui.mdiManager;

/**
 * <p>
 * Interface which should be implemented by those windows which have an identity
 * in the application. Each SingletonWindow has an associated model, which
 * identifies the window, and thus it is not possible to create two
 * SingletonWindow with the same model.
 * </p>
 * 
 * <p>
 * For example, if a window contains a text file, maybe it is not suitable to
 * open two windows to edit the same file. To accomplish this, the edit window
 * should implement this interface, and the model associated with the window
 * will be the path to the text file. Then, if the user tries to open an already
 * open file, the existing window containing the file will be shown to the user,
 * instead of opening a new one.
 * </p>
 * 
 * <p>
 * When opening a new SingletonWindow, the framework works in the following way:
 * all the already open SingletonWindow are searched to try to find a window
 * which has the same model as the new one. If such window is found, it is sent
 * to the foreground and no new window is opened; otherwise, the new window is
 * shown.
 * </p>
 * 
 * @author Fernando González Cortés
 */
public interface SingletonWindow extends IWindow {
	/**
	 * Gets the window model, the identity, the object which will be used to
	 * identify te window.
	 * 
	 * @return Object
	 */
	public Object getWindowModel();
}
