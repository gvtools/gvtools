/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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

import java.util.EventListener;

/**
 * Interfaz implementada por las extensiones que quieran informar del estado de
 * su ejecución. Por ejemplo un extension que tarda mucho en ejecutarse puede
 * implementar esta interfaz y añadirse como ProgressListener de la aplicacion.
 * De esta manera la aplicación le pedirá su estado a intervalos regulares de
 * tiempo y mostrará esta información en la barra de estado
 * 
 * @author Fernando González Cortés
 */
public interface ProgressListener extends EventListener {
	/**
	 * Método invocado a intervalos regulares por la aplicación y en el que se
	 * debe devolver el estado de una supuesta ejecución
	 * 
	 * @return Cadena que se envía a la aplicación
	 */
	public String getProgress();

	/**
	 * Devuelve un porcentaje que indica el progreso de la tarea
	 * 
	 * @return número del 0 al 100
	 */
	public int getProgressValue();
}
