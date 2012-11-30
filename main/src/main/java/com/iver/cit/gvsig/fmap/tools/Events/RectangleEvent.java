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
import java.awt.geom.Rectangle2D;

/**
 * <p>
 * <code>RectangleEvent</code> is used to notify a selection of a rectangular
 * area in a view, with the mouse.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class RectangleEvent {
	/**
	 * <p>
	 * Rectangle selected in world coordinates.
	 * </p>
	 */
	private Rectangle2D rect;

	/**
	 * <p>
	 * Rectangle selected in view (pixel) coordinates.
	 * </p>
	 */
	private Rectangle2D pixRect;

	/**
	 * <p>
	 * Mouse event that has been the cause of creating this event.
	 * </p>
	 */
	private MouseEvent event;

	/**
	 * <p>
	 * Creates a new <code>RectangleEvent</code> with all necessary data.
	 * </p>
	 * 
	 * @param worldRect
	 *            rectangle selected in world coordinates
	 * @param pixelRect
	 *            rectangle selected in view (pixel) coordinates
	 * @param e
	 *            mouse event that has been the cause of creating this event
	 */
	public RectangleEvent(Rectangle2D worldRect, MouseEvent e,
			Rectangle2D pixelRect) {
		rect = worldRect;
		event = e;
		pixRect = pixelRect;
	}

	/**
	 * <p>
	 * Gets the rectangle selected in world coordinates.
	 * </p>
	 * 
	 * @return rectangle selected in world coordinates
	 */
	public Rectangle2D getWorldCoordRect() {
		return rect;
	}

	/**
	 * <p>
	 * Gets the rectangle selected in pixel coordinates.
	 * </p>
	 * 
	 * <p>
	 * This is useful for doing some verifications, like if rectangle is thinner
	 * than 3 pixels of width and height, keeping the zoom instead of reducing
	 * it.
	 * </p>
	 * 
	 * @return rectangle selected in view (pixel) coordinates
	 */
	public Rectangle2D getPixelCoordRect() {
		return pixRect;
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
