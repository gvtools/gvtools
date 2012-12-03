/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
package com.iver.core.preferences.general;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;

/**
 * First default page in the Preferences Dialog. It is supposed to be used to
 * hold simple user interaction settings such as:
 * <ol>
 * <li>
 * Window positions.</li>
 * <li>
 * Mouse behaviors (double click vs. simple click).</li>
 * <li>
 * Users' language preferences.</li>
 * <li>
 * Confirm overwriting files.</li>
 * <li>
 * Toolbars config</li>
 * </ol>
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class GeneralPage extends AbstractPreferencePage {
	private ImageIcon icon;
	public static String id;
	private JCheckBox rememberWindowPosition;
	private JCheckBox rememberWindowSize;

	public GeneralPage() {
		super();
		initialize();
		id = this.getClass().getName();
	}

	private void initialize() {
		icon = PluginServices.getIconTheme().get("emblem-work");
		// remember windows position check box
		addComponent(rememberWindowPosition = new JCheckBox(
				PluginServices.getText(this,
						"options.general.remember_windows_pos")));
		// remember windows sizes check box
		addComponent(rememberWindowSize = new JCheckBox(PluginServices.getText(
				this, "options.general.remember_windows_size")));

	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "pref.general");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		// TODO remember window position
		// TODO remember window size
	}

	public void storeValues() {
	}

	public void initializeDefaults() {
		// TODO remember window position
		// TODO remember window size
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return super.hasChanged();
	}

	public void setChangesApplied() {
		setChanged(false);
	}

}
