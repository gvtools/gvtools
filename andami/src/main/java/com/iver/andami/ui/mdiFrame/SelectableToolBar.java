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
package com.iver.andami.ui.mdiFrame;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * Caja de herramientas seleccionables
 */
public class SelectableToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	/**
	 * Andami visibility: determines whether this toolbar should be shown by
	 * Andami. If it's false, the toolbar will be hidden even if its associated
	 * extension is visible.
	 */
	private boolean _visible;

	/**
	 * Creates a new SelectableToolBar object.
	 * 
	 * @param titulo
	 *            T�tulo de la barra
	 */
	public SelectableToolBar(String titulo) {
		super(titulo);
	}

	/**
	 * A�ade un boton a la caja
	 * 
	 * @param btn
	 *            bot�n a a�adir.
	 */
	public void addButton(ButtonGroup group, JToggleButton btn) {
		group.add(btn);
		add(btn);
	}

	/**
	 * Selects a toggleButton in this toolBar by ActionCommand
	 * 
	 * @param actionCommand
	 */
	public void setSelectedTool(String actionCommand) {
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i) instanceof JToggleButton) {
				JToggleButton toggleButton = (JToggleButton) getComponent(i);
				String aux = toggleButton.getActionCommand();
				if (aux != null)
					if (aux.equals(actionCommand)) {
						toggleButton.setSelected(true);
					}
			}
		}

	}

	/**
	 * Sets whether or not this toolbar should be shown by Andami. If it's
	 * false, the toolbar will be hidden even if its associated extension is
	 * visible.
	 * 
	 * @param visible
	 */
	public void setAndamiVisibility(boolean visible) {
		_visible = visible;
	}

	/**
	 * Gets whether this toolbar should be shown by Andami. If it's false, the
	 * toolbar will be hidden even if its associated extension is visible.
	 * 
	 * @return <code>true</code> if the toolbar should be shown when it has some
	 *         visible button, <code>false</code> if the toolbar should be
	 *         hidden even it it contains some buttons that should be visible
	 *         according to its associated extension.
	 */
	public boolean getAndamiVisibility() {
		return _visible;
	}
}
