package com.iver.cit.gvsig.gui.styling;

import com.iver.andami.PluginServices;
import com.iver.utiles.swing.JComboBox;

/**
 * JComboBox to all reference systems
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class JComboBoxUnitsReferenceSystem extends JComboBox {

	public JComboBoxUnitsReferenceSystem() {
		super();
		addItem(PluginServices.getText(this, "in_the_world"));
		addItem(PluginServices.getText(this, "in_the_paper"));
	}
}
