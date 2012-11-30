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
package com.iver.cit.gvsig.fmap;

import java.awt.Color;

/**
 * <p>
 * Event produced when changes the background color of the <i>view port</i>.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class ColorEvent extends FMapEvent {
	/**
	 * <p>
	 * Identifier of this kind of event.
	 * </p>
	 */
	private static final int COLOR_EVENT = 0;

	/**
	 * <p>
	 * Reference to the new color
	 * </p>
	 */
	private Color color;

	/**
	 * <p>
	 * Returns a new color event.
	 * </p>
	 * 
	 * @param c
	 *            the new color
	 * 
	 * @return a new color event
	 */
	public static ColorEvent createColorEvent(Color c) {
		return new ColorEvent(c, COLOR_EVENT);
	}

	/**
	 * <p>
	 * Creates a new color event.
	 * </p>
	 * 
	 * @param c
	 *            the new color
	 * @param eventType
	 *            identifier of this kind of event
	 */
	private ColorEvent(Color c, int eventType) {
		color = c;
		setEventType(eventType);
	}

	/**
	 * <p>
	 * Gets the new color.
	 * </p>
	 * 
	 * @return the new color
	 */
	public Color getNewColor() {
		return color;
	}
}
