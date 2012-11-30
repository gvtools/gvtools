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
import java.awt.geom.Point2D;

/**
 * <p>
 * <code>PointEvent</code> is used to notify a selection of a point with the
 * mouse.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class PointEvent {
	/**
	 * <p>
	 * The point 2D associated to this event.
	 * </p>
	 */
	private Point2D p;

	/**
	 * <p>
	 * The point 2D associated to this event.
	 * </p>
	 */
	private MouseEvent e;

	/**
	 * <p>
	 * Creates a new <code>PointEvent</code> with all necessary data.
	 * </p>
	 * 
	 * @param p
	 *            the point 2D associated to this event
	 * @param e
	 *            event that has been the cause of creating this one
	 */
	public PointEvent(Point2D p, MouseEvent e) {
		this.p = p;
		this.e = e;
	}

	/**
	 * <p>
	 * Gets the point 2D where this event was produced.
	 * </p>
	 * 
	 * @return the point 2D associated to this event
	 */
	public Point2D getPoint() {
		return p;
	}

	/**
	 * <p>
	 * Sets the point 2D where this event was produced.
	 * </p>
	 * 
	 * @param the
	 *            point 2D associated to this event
	 */
	public void setPoint(Point2D p) {
		this.p = p;
	}

	/**
	 * <p>
	 * Gets the event that has been the cause of creating this one.
	 * </p>
	 * 
	 * @return mouse event that has been the cause of creating this one
	 */
	public MouseEvent getEvent() {
		return e;
	}
}
