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
package com.iver.cit.gvsig.fmap.tools.Events;

import java.awt.event.MouseEvent;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * <p>
 * <code>MeasureEvent</code> is used to notify the selection of a polyline.
 * </p>
 * 
 * <p>
 * Stores the information about the 2D vertexes and the {@link GeneralPathX
 * GeneralPathX} between them.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class MeasureEvent {

	/**
	 * Vector with the X coordinates.
	 */
	private Coordinate[] coordinates;

	/**
	 * Mouse event that has been the cause of creating this event.
	 */
	private MouseEvent event;

	/**
	 * <p>
	 * Creates a new <code>MeasureEvent</code> with all necessary data.
	 * </p>
	 * <p>
	 * The general path is calculated according the enclosed regions of the path
	 * alternate between interior and exterior areas are traversed from the
	 * outside of the path towards a point inside the region.
	 * </p>
	 * 
	 * @param x
	 *            vector with the X coordinates
	 * @param y
	 *            vector with the Y coordinates
	 * @param e
	 *            event that has been the cause of creating this one
	 */
	public MeasureEvent(Coordinate[] coords, MouseEvent e) {
		this.coordinates = coords;
		this.event = e;
	}

	/**
	 * <p>
	 * Gets the event that has been the cause of creating this one.
	 * </p>
	 * 
	 * @return mouse event that has been the cause of creating this one
	 */
	public MouseEvent getEvent() {
		return event;
	}

	public Coordinate[] getCoordinates() {
		return coordinates;
	}
}
