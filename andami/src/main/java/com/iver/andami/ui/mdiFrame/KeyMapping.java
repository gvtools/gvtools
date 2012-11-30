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
package com.iver.andami.ui.mdiFrame;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import com.iver.andami.messages.Messages;

/**
 * Clase que sirve para obtener los codigos de las teclas de la manera que las
 * requieren los JMenu a partir de un caracter que representa dicha tecla
 * 
 * @author Fernando González Cortés
 */
public class KeyMapping {
	/** Asocia caracteres con KeyEvents */
	private static HashMap<Character, Integer> key = new HashMap<Character, Integer>();

	static {
		key.put(new Character('a'), new Integer(KeyEvent.VK_A));
		key.put(new Character('b'), new Integer(KeyEvent.VK_B));
		key.put(new Character('c'), new Integer(KeyEvent.VK_C));
		key.put(new Character('d'), new Integer(KeyEvent.VK_D));
		key.put(new Character('e'), new Integer(KeyEvent.VK_E));
		key.put(new Character('f'), new Integer(KeyEvent.VK_F));
		key.put(new Character('g'), new Integer(KeyEvent.VK_G));
		key.put(new Character('h'), new Integer(KeyEvent.VK_H));
		key.put(new Character('i'), new Integer(KeyEvent.VK_I));
		key.put(new Character('j'), new Integer(KeyEvent.VK_J));
		key.put(new Character('k'), new Integer(KeyEvent.VK_K));
		key.put(new Character('l'), new Integer(KeyEvent.VK_L));
		key.put(new Character('m'), new Integer(KeyEvent.VK_M));
		key.put(new Character('n'), new Integer(KeyEvent.VK_N));
		key.put(new Character('o'), new Integer(KeyEvent.VK_O));
		key.put(new Character('p'), new Integer(KeyEvent.VK_P));
		key.put(new Character('q'), new Integer(KeyEvent.VK_Q));
		key.put(new Character('r'), new Integer(KeyEvent.VK_R));
		key.put(new Character('s'), new Integer(KeyEvent.VK_S));
		key.put(new Character('t'), new Integer(KeyEvent.VK_T));
		key.put(new Character('u'), new Integer(KeyEvent.VK_U));
		key.put(new Character('v'), new Integer(KeyEvent.VK_V));
		key.put(new Character('w'), new Integer(KeyEvent.VK_W));
		key.put(new Character('x'), new Integer(KeyEvent.VK_X));
		key.put(new Character('y'), new Integer(KeyEvent.VK_Y));
		key.put(new Character('z'), new Integer(KeyEvent.VK_Z));
		key.put(new Character('0'), new Integer(KeyEvent.VK_0));
		key.put(new Character('1'), new Integer(KeyEvent.VK_1));
		key.put(new Character('2'), new Integer(KeyEvent.VK_2));
		key.put(new Character('3'), new Integer(KeyEvent.VK_3));
		key.put(new Character('4'), new Integer(KeyEvent.VK_4));
		key.put(new Character('5'), new Integer(KeyEvent.VK_5));
		key.put(new Character('6'), new Integer(KeyEvent.VK_6));
		key.put(new Character('7'), new Integer(KeyEvent.VK_7));
		key.put(new Character('8'), new Integer(KeyEvent.VK_8));
		key.put(new Character('9'), new Integer(KeyEvent.VK_9));
		key.put(new Character('+'), new Integer(KeyEvent.VK_PLUS));
		key.put(new Character('-'), new Integer(KeyEvent.VK_MINUS));
	}

	/**
	 * Obtiene dado un caracter el entero correspondiente al codigo que tiene la
	 * tecla que produce dicho caracter
	 * 
	 * @param a
	 *            caracter
	 * 
	 * @return Codigo de la tecla asociada
	 * 
	 * @throws RuntimeException
	 *             Si el caracter no tiene una tecla asociada
	 */
	public static int getKey(char a) {
		Integer ret = key.get(new Character(a));

		if (ret == null) {
			throw new RuntimeException(
					Messages.getString("KeyMapping.Caracter_no_valido") + a); //$NON-NLS-1$
		}

		return ret.intValue();
	}
}
