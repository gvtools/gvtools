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
package com.iver.andami;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import javax.swing.KeyStroke;

public class GlobalKeyEventDispatcher implements KeyEventDispatcher {
	private static GlobalKeyEventDispatcher globalKeyDispatcher = null;
	private static Hashtable<KeyStroke, KeyEventDispatcher> registeredKeys = new Hashtable<KeyStroke, KeyEventDispatcher>();

	static GlobalKeyEventDispatcher getInstance() {
		if (globalKeyDispatcher == null)
			globalKeyDispatcher = new GlobalKeyEventDispatcher();
		return globalKeyDispatcher;
	}

	public void registerKeyStroke(KeyStroke key, KeyEventDispatcher disp)
			throws RuntimeException {
		if (registeredKeys.containsKey(key)) {
			KeyEventDispatcher a = registeredKeys.get(key);
			throw new RuntimeException("Error: La tecla " + key
					+ " ya está asignada al action" + a);
		}
		registeredKeys.put(key, disp);
	}

	public void removeKeyStrokeBinding(KeyStroke key) {
		registeredKeys.remove(key);
	}

	public void removeAll() {
		registeredKeys.clear();
	}

	public KeyEventDispatcher getListenerAssignedTo(KeyStroke key) {
		return registeredKeys.get(key);
	}

	private GlobalKeyEventDispatcher() {
	}

	public boolean dispatchKeyEvent(KeyEvent e) {
		KeyStroke key = KeyStroke
				.getKeyStroke(e.getKeyCode(), e.getModifiers());
		KeyEventDispatcher a = getListenerAssignedTo(key);

		if (a != null) {
			// System.err.println("DEBUG: KeyEvent " + e +
			// ".Antes de ejecutar action=" + a);
			return a.dispatchKeyEvent(e);
		}
		return false;
	}

}
