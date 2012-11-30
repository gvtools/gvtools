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

import java.util.Locale;

/**
 * Clase que accede a los recursos para la i18n
 */
public class Messages {
	/**
	 * Inicializa la clase con el locale adecuado
	 * 
	 * @param loc
	 *            Locale de la aplicación
	 */
	public static void init(Locale loc) {
		return;
	}

	/**
	 * @param strLocale
	 *            . Ejemplo: va para valenciano Se trata de permitir coexistir
	 *            el valenciano con el catalán. El método buscará un fichero
	 *            properties llamado "text_va.properties" para usarlo con
	 *            RESOURCE_BUNDLE de donde sacar los mensajes traducidos.
	 */
	public static void init(String strLocale) {
		return;
	}

	/**
	 * Obtiene el valor del recurso con clave 'key'
	 * 
	 * @param key
	 *            clave del recurso que se quiere obtener
	 * 
	 * @return recurso que se quiere obtener o !key! en caso de no encontrarlo.
	 *         En dicho caso no se notifica al framework ya que estos son los
	 *         mensajes propios de la aplicación y deben de estar todos
	 */
	public static String getString(String key) {
		return org.gvsig.i18n.Messages.getText(key,
				"com.iver.andami.messages.Messages");
	}

	public static String get(String key) {
		return org.gvsig.i18n.Messages.getText(key,
				"com.iver.andami.messages.Messages");
	}
}
