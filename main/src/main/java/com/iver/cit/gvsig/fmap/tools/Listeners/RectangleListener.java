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
package com.iver.cit.gvsig.fmap.tools.Listeners;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.RectangleEvent;

/**
 * <p>
 * Interface specialized for tools that reply for a {@link RectangleEvent
 * RectangleEvent} produced in the associated {@link MapControl MapControl}
 * object, as a consequence of a 2D rectangle drawn by the mouse.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public interface RectangleListener extends ToolListener {
	/**
	 * <p>
	 * Called when user executes a double click with the mouse, finishing the
	 * drawn of the rectangle.
	 * </p>
	 * 
	 * <p>
	 * All features of the active and vector layers of the associated
	 * <code>MapControl</code> object that their area intersect with the
	 * polygonal area defined in the <i>event</i>, will be selected.
	 * </p>
	 * 
	 * @param event
	 *            mouse event and information about the rectangle defined
	 * 
	 * @throws BehaviorException
	 *             will be thrown when fails the process of this tool
	 */
	public void rectangle(RectangleEvent event) throws BehaviorException;
}
