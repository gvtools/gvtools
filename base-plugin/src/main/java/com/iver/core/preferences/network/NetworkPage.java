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

/* CVS MESSAGES:
 *
 * $Id$
 * $Log$
 * Revision 1.10  2007/09/19 16:16:52  jaume
 * removed unnecessary imports
 *
 * Revision 1.9  2006/11/20 17:29:43  fjp
 * Fallo proxy con usuario y contraseña
 *
 * Revision 1.8  2006/09/12 10:11:25  jaume
 * *** empty log message ***
 *
 * Revision 1.7.4.1  2006/09/08 11:56:24  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2006/08/22 12:23:05  jaume
 * improved perfomance when saving changes
 *
 * Revision 1.6  2006/08/22 07:37:17  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/08/04 11:45:12  caballero
 * lanzo una excepción cuando falla el método storeValues
 *
 * Revision 1.4  2006/07/31 10:02:31  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/07/03 10:46:01  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/06/13 07:43:08  fjp
 * Ajustes sobre los cuadros de dialogos de preferencias
 *
 * Revision 1.1  2006/06/12 16:04:28  caballero
 * Preferencias
 *
 * Revision 1.6  2006/06/06 10:26:31  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/06/05 17:07:17  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/06/05 10:06:08  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/06/05 09:13:22  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/06/05 08:11:38  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/06/02 10:50:18  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/06/01 15:54:09  jaume
 * added preferences extension
 *
 *
 */
package com.iver.core.preferences.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.ui.mdiFrame.JToolBarButton;

/**
 * General network connection page.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class NetworkPage extends AbstractPreferencePage {
	// private static Preferences prefs = Preferences.userRoot().node(
	// "gvsig.connection" );
	private ImageIcon icon;
	private JLabel lblNetworkStatus;
	private JToolBarButton btnRefresh;
	protected static String id;

	public NetworkPage() {
		super();
		id = this.getClass().getName();
		// icon = new
		// ImageIcon(this.getClass().getClassLoader().getResource("images/network.png"));
		icon = PluginServices.getIconTheme().get(
				"aplication-preferences-network");
		lblNetworkStatus = new JLabel();
		lblNetworkStatus.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD,
				12));
		lblNetworkStatus.setText(PluginServices.getText(this,
				"optinos.network.click_to_test_connection"));

		JPanel aux = new JPanel();
		aux.add(getBtnCheckConnection());
		addComponent(PluginServices.getText(this, "options.network.status")
				+ ":", lblNetworkStatus);
		addComponent("", aux);

	}

	private JToolBarButton getBtnCheckConnection() {
		if (btnRefresh == null) {
			btnRefresh = new JToolBarButton(PluginServices.getText(this,
					"test_now"));
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refreshStatus();
				};
			});
		}
		return btnRefresh;
	}

	private void refreshStatus() {
		boolean connected = isConnected();
		ImageIcon statusIcon;
		String statusText;
		if (connected) {
			statusIcon = PluginServices.getIconTheme().get(
					"kde-network-online-icon");
			statusText = PluginServices.getText(this, "online");
		} else {
			statusIcon = PluginServices.getIconTheme().get(
					"kde-network-offline-icon");
			statusText = PluginServices.getText(this, "offline");
		}
		lblNetworkStatus.setIcon(statusIcon);
		lblNetworkStatus.setText(statusText);

	}

	private boolean isConnected() {
		try {
			URL url = new URL("http://www.google.com");
			url.openConnection();
			url.openStream();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "pref.network");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {

	}

	public void storeValues() {

	}

	public void initializeDefaults() {
		// nothing
	}

	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {

		}
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return hasChanged();
	}

	public void setChangesApplied() {
		setChanged(false);
	}

}
