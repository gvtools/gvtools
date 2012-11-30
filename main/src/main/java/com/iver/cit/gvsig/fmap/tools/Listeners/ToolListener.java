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

import java.awt.Cursor;

/**
 * <p>
 * User can interact with a {@link MapControl MapControl} instance, working with
 * a tool that applies changes on it processing the events produced by the
 * current {@link Behavior Behavior} of <code>MapControl</code>.
 * </p>
 * 
 * <p>
 * The process that the tool applies on the <code>MapControl</code> is a
 * consequence of the mouse actions that user has done on that area.
 * </p>
 * 
 * <p>
 * Only some tool listener can be cancelled.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public interface ToolListener {
	/**
	 * <p>
	 * Gets the <code>Cursor</code> associated to this tool.
	 * </p>
	 * 
	 * @return component with the bitmap associated to this tool
	 */
	public Cursor getCursor();

	/**
	 * <p>
	 * Determines if the drawing process that this tool executes on the
	 * <code>MapControl</code> instance could be canceled or not.
	 * </p>
	 * 
	 * @return <code>true</code> if is cancellable; otherwise returns
	 *         <code>false</code>
	 */
	public boolean cancelDrawing();
}
