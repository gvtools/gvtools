/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
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
package com.iver.cit.gvsig.gui;

import org.gvsig.units.Unit;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.Project;
import com.iver.utiles.swing.JComboBox;

/**
 * <p>
 * Class representing a JComboBox with the measure units handled by the
 * application. It takes values from Attributes.NAMES and Attributes.CHANGE
 * static fields. So, to add more measure units, you must edit Attributes class
 * and change will be automatically reflected in the combo box.
 * </p>
 * 
 * <p>
 * The internatiolanization of the field is automatically handled by the system
 * </p>
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class JComboBoxUnits extends JComboBox<String> {
	private static final long serialVersionUID = 8015263853737441433L;

	/**
	 * Creates a new instance of JUnitComboBox including "pixel" units and
	 * setting them as automatically pre-selected.
	 */
	public JComboBoxUnits() {
		this(true);
	}

	/**
	 * 
	 * Creates a new instance of JUnitComboBox. If includePixel is true then
	 * pixel units are included in the list and they are automatically
	 * pre-selected. Otherwise, meters are preselected.
	 * 
	 */
	public JComboBoxUnits(boolean includePixel) {
		super();
		String[] names = Unit.getDistanceNames();

		for (int i = 0; i < names.length; i++) {
			super.addItem(PluginServices.getText(this, names[i]));
		}
		if (includePixel) {
			super.addItem(PluginServices.getText(this, "pixels"));
			setSelectedItem(PluginServices.getText(this, "pixels"));
		} else {
			setSelectedItem(Project.getDefaultDistanceUnits().name);
		}
		setMaximumRowCount(10);
	}

	/**
	 * Returns the conversion factor from the <b>unit selected in the combo
	 * box</b> to <b>meters</b> or <b>0</b> if pixels have been selected as the
	 * size unit.
	 * 
	 * @return
	 */
	public double getUnitConversionFactor() {
		double unitFactor;
		try {
			unitFactor = Unit.fromName((String) getSelectedItem()).toMeter();
		} catch (ArrayIndexOutOfBoundsException aioobEx) { // jijiji
			unitFactor = 0; // which represents size in pixel
		}
		return unitFactor;

	}

	public int getSelectedUnitIndex() {
		return getSelectedIndex();
	}

	public void setSelectedUnitIndex(int unitIndex) {
		if (unitIndex == -1) {
			setSelectedIndex(getItemCount() - 1);
		} else {
			setSelectedIndex(unitIndex);
		}
	}

}
