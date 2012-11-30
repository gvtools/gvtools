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
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;

/**
 * <p>
 * Interface for all tools that reply for a {@link PointEvent PointEvent}
 * produced in the associated {@link MapControl MapControl} object, produced by
 * a simple or double click of a button of the mouse.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public interface PointListener extends ToolListener {
	/**
	 * <p>
	 * Called when one click is pressed on the associated
	 * <code>MapControl</code>, or the location of the cursor of the mouse has
	 * changed on it.
	 * </p>
	 * 
	 * @param event
	 *            mouse event with the coordinates of the point selected on the
	 *            associated <code>MapControl</code>
	 * 
	 * @throws BehaviorException
	 *             will be thrown when fails the process of this tool
	 */
	public void point(PointEvent event) throws BehaviorException;

	/**
	 * <p>
	 * Called when a double click is pressed on the associated
	 * <code>MapControl</code>.
	 * </p>
	 * 
	 * @param event
	 *            mouse event and the coordinates of the point selected on the
	 *            associated <code>MapControl</code>
	 * 
	 * @throws BehaviorException
	 *             will be thrown when fails the process of this tool
	 */
	public void pointDoubleClick(PointEvent event) throws BehaviorException;
}
