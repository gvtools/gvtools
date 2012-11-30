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
 * <code>MoveEvent</code> is used to notify a movement of the mouse between two
 * points 2D.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class MoveEvent {
	/**
	 * <p>
	 * Initial position of the drag & drop movement.
	 * </p>
	 */
	private Point2D from;

	/**
	 * <p>
	 * Final position of the drag & drop movement.
	 * </p>
	 */
	private Point2D to;

	/**
	 * <p>
	 * Mouse event that has been the cause of creating this one.
	 * </p>
	 */
	private MouseEvent event;

	/**
	 * <p>
	 * Creates a new <code>MoveEvent</code> with all necessary data.
	 * </p>
	 * 
	 * @param from
	 *            initial 2D position of the movement
	 * @param to
	 *            final 2D position of the movement
	 * @param e
	 *            mouse event that has been the cause of creating this one
	 */
	public MoveEvent(Point2D from, Point2D to, MouseEvent e) {
		this.from = from;
		this.to = to;
		event = e;
	}

	/**
	 * <p>
	 * Gets the initial 2D position of the movement.
	 * </p>
	 * 
	 * @return initial 2D position of the movement
	 */
	public Point2D getFrom() {
		return from;
	}

	/**
	 * <p>
	 * Gets the final 2D position of the movement.
	 * </p>
	 * 
	 * @return final 2D position of the movement
	 */
	public Point2D getTo() {
		return to;
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
}
