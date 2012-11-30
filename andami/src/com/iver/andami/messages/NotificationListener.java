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
package com.iver.andami.messages;

/**
 * Interfaz que deber�n de implementar todas las clases que quieran recibir
 * informaci�n sobre los mensajes de informaci�n, de warning y de error que
 * recibe la aplicaci�n
 */
public interface NotificationListener {
	/**
	 * M�todo invocado cuando la aplicaci�n recibe mensajes de error
	 * 
	 * @param app
	 *            contexto de la aplicaci�n
	 * @param text
	 *            Texto del mensaje
	 */
	public void errorEvent(MessageEvent e);

	/**
	 * M�todo invocado cuando la aplicaci�n recibe mensajes de warning
	 * 
	 * @param app
	 *            contexto de la aplicaci�n
	 * @param text
	 *            Texto del mensaje
	 */
	public void warningEvent(MessageEvent e);

	/**
	 * M�todo invocado cuando la aplicaci�n recibe mensajes de informaci�n
	 * 
	 * @param app
	 *            contexto de la aplicaci�n
	 * @param text
	 *            Texto del mensaje
	 */
	public void infoEvent(MessageEvent e);
}
