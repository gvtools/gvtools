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
 * Revision 1.9  2006/11/21 10:59:45  fjp
 * Fallo proxy con usuario y contraseña
 *
 * Revision 1.8  2006/11/20 17:29:43  fjp
 * Fallo proxy con usuario y contraseña
 *
 * Revision 1.7  2006/10/18 07:55:43  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2006/08/22 12:23:05  jaume
 * improved perfomance when saving changes
 *
 * Revision 1.5  2006/08/22 07:37:17  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/08/04 11:45:12  caballero
 * lanzo una excepción cuando falla el método storeValues
 *
 * Revision 1.3  2006/07/31 10:02:31  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/06/13 07:43:08  fjp
 * Ajustes sobre los cuadros de dialogos de preferencias
 *
 * Revision 1.1  2006/06/12 16:04:28  caballero
 * Preferencias
 *
 * Revision 1.11  2006/06/06 10:26:31  jaume
 * *** empty log message ***
 *
 * Revision 1.10  2006/06/05 17:07:17  jaume
 * *** empty log message ***
 *
 * Revision 1.9  2006/06/05 17:00:44  jaume
 * *** empty log message ***
 *
 * Revision 1.8  2006/06/05 16:57:59  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2006/06/05 14:45:06  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2006/06/05 11:00:09  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/06/05 10:39:02  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/06/05 10:13:40  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/06/05 10:06:08  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/06/05 09:51:56  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/06/02 10:50:18  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.core.preferences.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;

public class FirewallPage extends AbstractPreferencePage {

	// Quizá en vez de usar un prefs cada vez deberíamos de tener una clase
	// gvSIG.java
	// donde se guarden todas las preferencias, queda más limpio y más claro si
	// se hace
	// un: gvSIG.getProperty("gvsig.connection") que lo de abajo.
	private static Preferences prefs = Preferences.userRoot().node(
			"gvsig.connection");
	private JCheckBox httpEnabled;
	private JTextField httpHost;
	private JTextField httpPort;
	private JTextField httpUser;
	private JPasswordField httpPass;
	private JTextField httpNonProxy;
	private JCheckBox socksEnabled;
	private JTextField socksHost;
	private JTextField socksPort;
	protected static String id = FirewallPage.class.getName();
	private ImageIcon icon;

	private static final class ProxyAuth extends Authenticator {
		private PasswordAuthentication auth;

		private ProxyAuth(String user, String pass) {
			auth = new PasswordAuthentication(user, pass.toCharArray());
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return auth;
		}
	}

	public FirewallPage() {
		super();
		// icon = new
		// ImageIcon(this.getClass().getClassLoader().getResource("images/shield.png"));
		icon = PluginServices.getIconTheme().get(
				"aplication-preferences-firewall");
		setParentID(NetworkPage.id);
		// checkbox
		addComponent(httpEnabled = new JCheckBox(PluginServices.getText(this,
				"options.firewall.http.enabled")));
		// proxy host
		addComponent(PluginServices.getText(this, "options.firewall.http.host")
				+ ":", httpHost = new JTextField("", 15));
		// proxy port
		addComponent(PluginServices.getText(this, "options.firewall.http.port")
				+ ":", httpPort = new JTextField("", 15));
		// proxy username
		addComponent(PluginServices.getText(this, "options.firewall.http.user")
				+ ":", httpUser = new JTextField("", 15));
		// proxy password
		addComponent(
				PluginServices.getText(this, "options.firewall.http.password")
						+ ":", httpPass = new JPasswordField("", 15));
		// no proxy for
		addComponent(
				PluginServices.getText(this, "options.firewall.http.nonProxy")
						+ ":", httpNonProxy = new JTextField("", 15));

		// checkbox
		addComponent(socksEnabled = new JCheckBox(PluginServices.getText(this,
				"options.firewall.socks.enabled")));
		// proxy host
		addComponent(
				PluginServices.getText(this, "options.firewall.socks.host")
						+ ":", socksHost = new JTextField("", 15));
		// proxy port
		addComponent(
				PluginServices.getText(this, "options.firewall.socks.port")
						+ ":", socksPort = new JTextField("", 15));

	}

	public void initializeValues() {

		boolean enabled = prefs.getBoolean("firewall.http.enabled", false);
		httpEnabled.setSelected(enabled);
		httpHost.setEnabled(enabled);
		httpHost.setText(prefs.get("firewall.http.host", ""));
		httpPort.setEnabled(enabled);
		httpPort.setText(prefs.get("firewall.http.port", ""));
		httpUser.setEnabled(enabled);
		httpUser.setText(prefs.get("firewall.http.user", ""));
		httpPass.setEnabled(enabled);
		httpPass.setText(prefs.get("firewall.http.password", ""));
		httpNonProxy.setEnabled(enabled);
		httpNonProxy.setText(prefs.get("firewall.http.nonProxyHosts", ""));

		httpEnabled.addActionListener(new ActionHandler());

		enabled = prefs.getBoolean("firewall.socks.enabled", false);
		socksEnabled.setSelected(enabled);
		socksHost.setEnabled(enabled);
		socksHost.setText(prefs.get("firewall.socks.host", ""));
		socksPort.setEnabled(enabled);
		socksPort.setText(prefs.get("firewall.socks.port", ""));

		socksEnabled.addActionListener(new ActionHandler());

	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "pref.network.firewall");
	}

	public JPanel getPanel() {
		return this;
	}

	public String createURL(String host, String port) {
		StringBuffer strUrl = new StringBuffer();
		// add "http://" prefix if it wasn't included.
		strUrl.append(host.toLowerCase().startsWith("http://") ? host
				.toLowerCase() : "http://" + host.toLowerCase());

		// add port
		strUrl.append(!port.equals("") ? ":" + port : "");
		return strUrl.toString();
	}

	public void storeValues() throws StoreException {
		Properties systemSettings = System.getProperties();
		URL httpURL, socksURL;

		try {

			httpURL = new URL(createURL(httpHost.getText(), httpPort.getText()));
			prefs.putBoolean("firewall.http.enabled", httpEnabled.isSelected());
			prefs.put("firewall.http.host", httpHost.getText());
			prefs.put("firewall.http.port", httpPort.getText());
			String proxyUser = httpUser.getText();
			String proxyPassword = new String(httpPass.getPassword());
			String httpNonProxyHosts = httpNonProxy.getText();
			prefs.put("firewall.http.user", proxyUser);
			prefs.put("firewall.http.password", proxyPassword);
			prefs.put("firewall.http.nonProxyHosts", httpNonProxyHosts);

			if (httpEnabled.isSelected()) {
				systemSettings.put("http.proxySet", "true");
				systemSettings.put("http.proxyHost", httpURL.getHost());
				systemSettings.put("http.proxyPort", httpURL.getPort() + "");
				systemSettings.put("http.proxyUserName", proxyUser);
				systemSettings.put("http.proxyPassword", proxyPassword);
				systemSettings.put("http.nonProxyHosts", httpNonProxyHosts);
			} else {
				systemSettings.put("http.proxySet", "false");
				systemSettings.remove("http.proxyHost");
				systemSettings.remove("http.proxyPort");
				systemSettings.remove("http.proxyUserName");
				systemSettings.remove("http.proxyPassword");
				systemSettings.remove("http.nonProxyHosts");
				prefs.remove("firewall.http.host");
				prefs.remove("firewall.http.port");
				prefs.remove("firewall.http.user");
				prefs.remove("firewall.http.password");
				prefs.remove("firewall.http.nonProxyHosts");

			}

			System.setProperties(systemSettings);
			if (proxyUser != null) {
				Authenticator
						.setDefault(new ProxyAuth(proxyUser, proxyPassword));
			} else {
				Authenticator.setDefault(new ProxyAuth("", ""));
			}

		} catch (MalformedURLException e) {
			if (httpEnabled.isSelected()) {
				throw new StoreException(PluginServices.getText(this,
						"options.firewall.http.incorrect_host"), e);
			}
		}

		try {
			socksURL = new URL(createURL(socksHost.getText(),
					socksPort.getText()));

			prefs.putBoolean("firewall.socks.enabled",
					socksEnabled.isSelected());
			prefs.put("firewall.socks.host", socksHost.getText());
			prefs.put("firewall.socks.port", socksPort.getText());

			if (socksEnabled.isSelected()) {
				systemSettings.put("socksProxyHost", socksURL.getHost());
				systemSettings.put("socksProxyPort", socksURL.getPort() + "");
			} else {
				systemSettings.remove("socksProxyHost");
				systemSettings.remove("socksProxyPort");
			}

			System.setProperties(systemSettings);

		} catch (MalformedURLException e) {
			if (socksEnabled.isSelected()) {
				throw new StoreException(PluginServices.getText(this,
						"options.firewall.socks.incorrect_host"), e);
			}
		}
	}

	public void initializeDefaults() {
		httpEnabled.setSelected(false);
		httpHost.setText("");
		httpPort.setText("");
		httpUser.setText("");
		httpPass.setText("");
		httpNonProxy.setText("");
		socksEnabled.setSelected(false);
		socksHost.setText("");
		socksPort.setText("");

	}

	private class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			httpHost.setEnabled(httpEnabled.isSelected());
			httpPort.setEnabled(httpEnabled.isSelected());
			httpUser.setEnabled(httpEnabled.isSelected());
			httpPass.setEnabled(httpEnabled.isSelected());
			httpNonProxy.setEnabled(httpEnabled.isSelected());
			socksHost.setEnabled(socksEnabled.isSelected());
			socksPort.setEnabled(socksEnabled.isSelected());
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
