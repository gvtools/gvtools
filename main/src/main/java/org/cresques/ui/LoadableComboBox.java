/*
 * Cresques Mapping Suite. Graphic Library for constructing mapping applications.
 *
 * Copyright (C) 2004-5.
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
 * cresques@gmail.com
 */
package org.cresques.ui;

import javax.swing.JComboBox;

/**
 * @author "Luis W. Sevilla" (sevilla_lui@gva.es)
 */
public class LoadableComboBox extends JComboBox<String> {
	final private static long serialVersionUID = -3370601314380922368L;

	public LoadableComboBox() {
		super();
		initialize();

		// addItemListener(this);
		// setSize(new java.awt.Dimension(185, 23));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new java.awt.Dimension(185, 23));
	}

	/**
	 * Carga el vector de strings data con los valores pasados por par�metro
	 * 
	 * @param data
	 *            vector de strings con los valores
	 */
	public void loadData(String[] data) {
		if (data == null) {
			return;
		}

		for (int i = 0; i < data.length; i++)
			addItem(data[i]);
	}

	/*
	 * public void itemStateChanged(ItemEvent e) { if (e.getStateChange() ==
	 * ItemEvent.SELECTED) { //label.setVisible(true); } else {
	 * //label.setVisible(false); } }
	 */
}
