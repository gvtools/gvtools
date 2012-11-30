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

/**
 * <p>
 * All events produced on a layer must be of a particular type.
 * </p>
 * 
 * <p>
 * <code>FMapEvent</code> defines the least information that can have an event
 * produced on a layer, its <i>type</i>.
 * </p>
 */
public class FMapEvent {
	/**
	 * Kind of event type.
	 */
	private int eventType;

	/**
	 * <p>
	 * Gets the type of this event.
	 * </p>
	 * 
	 * @return the type of this event
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * <p>
	 * Sets the type of the event.
	 * </p>
	 * 
	 * @param eventType
	 *            the number that identifies this event's type
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
