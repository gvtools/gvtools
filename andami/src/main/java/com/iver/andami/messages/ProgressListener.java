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

import java.util.EventListener;

/**
 * Interfaz implementada por las extensiones que quieran informar del estado de
 * su ejecuci�n. Por ejemplo un extension que tarda mucho en ejecutarse puede
 * implementar esta interfaz y a�adirse como ProgressListener de la aplicacion.
 * De esta manera la aplicaci�n le pedir� su estado a intervalos regulares de
 * tiempo y mostrar� esta informaci�n en la barra de estado
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public interface ProgressListener extends EventListener {
	/**
	 * M�todo invocado a intervalos regulares por la aplicaci�n y en el que se
	 * debe devolver el estado de una supuesta ejecuci�n
	 * 
	 * @return Cadena que se env�a a la aplicaci�n
	 */
	public String getProgress();

	/**
	 * Devuelve un porcentaje que indica el progreso de la tarea
	 * 
	 * @return n�mero del 0 al 100
	 */
	public int getProgressValue();
}
