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
 * $Id: BrowserControlPage.java 15635 2007-10-30 11:58:33Z jmvivo $
 * $Log$
 * Revision 1.3  2007-09-19 16:16:52  jaume
 * removed unnecessary imports
 *
 * Revision 1.2  2007/01/10 18:28:58  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/10 16:55:04  jaume
 * default browser now configurable in linux
 *
 *
 */
package com.iver.core.preferences.general;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;
import com.iver.utiles.BrowserControl;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.swing.JComboBox;

/**
 * <p>
 * <b>Preference page</b> for system browser's properties.<br>
 * </p>
 * 
 * <p>
 * At the moment, this page will allow the <b>linux</b> users to select a web
 * browser and how to open it when the app needs to do such thing.<br>
 * For instance when following a link to a web page.<br>
 * </p>
 * 
 * <p>
 * This is the merely purpose of it, and it makes that this page has no sense
 * for windows users. That is why it does not appear in windows platforms.
 * </p>
 * 
 * <p>
 * If you decide to extend this purpose, then you should enable it for those
 * platforms (or any) you want.
 * </p>
 * TODO to know what about Mac platforms.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class BrowserControlPage extends AbstractPreferencePage implements
		ActionListener {

	private static final String DEFAULT_BROWSER_KEY_NAME = "DefaultBrowser";
	private String id;
	private ImageIcon icon;
	private JRadioButton rdBtnSelectBrowser;
	private JRadioButton rdBtnSpecifyCommand;
	private JComboBox cmbBrowsers;
	private JTextField txtCustomCommand;
	private ArrayList supportedBrowsers = BrowserControl.getSupportedBrowsers();

	public BrowserControlPage() {
		super();
		id = this.getClass().getName();
		icon = PluginServices.getIconTheme().get("browser-icon");
		setParentID(GeneralPage.id);

		JPanel aux;

		ButtonGroup group = new ButtonGroup();
		rdBtnSelectBrowser = new JRadioButton(PluginServices.getText(this,
				"options.general.browser.select_a_known_browser"));
		rdBtnSelectBrowser.addActionListener(this);

		aux = new JPanel(new FlowLayout(FlowLayout.LEADING));
		cmbBrowsers = new JComboBox(
				(String[]) supportedBrowsers.toArray(new String[0]));
		aux.add(cmbBrowsers);

		addComponent(rdBtnSelectBrowser, aux);

		rdBtnSpecifyCommand = new JRadioButton(PluginServices.getText(this,
				"options.general.browser.specify_a_command"));
		rdBtnSpecifyCommand.setVerticalAlignment(SwingConstants.BOTTOM);
		rdBtnSpecifyCommand.addActionListener(this);
		aux = new JPanel(new FlowLayout(FlowLayout.LEADING));
		aux.add(txtCustomCommand = new JTextField(25));
		addComponent(rdBtnSpecifyCommand, aux);
		group.add(rdBtnSelectBrowser);
		group.add(rdBtnSpecifyCommand);

		actionPerformed(null);
	}

	public void storeValues() throws StoreException {
		String cmd = rdBtnSelectBrowser.isSelected() ? (String) cmbBrowsers
				.getSelectedItem() : txtCustomCommand.getText();
		BrowserControl.setBrowserCommand(cmd);

		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();
		xml.putProperty(DEFAULT_BROWSER_KEY_NAME, cmd);
	}

	public void setChangesApplied() {
		setChanged(false);
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "browser");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();
		// Default Projection
		if (xml.contains(DEFAULT_BROWSER_KEY_NAME)) {
			String cmd = xml.getStringProperty(DEFAULT_BROWSER_KEY_NAME);
			boolean b = supportedBrowsers.contains(cmd);
			if (b) {
				cmbBrowsers.setSelectedItem(cmd);
				cmbBrowsers.setEnabled(true);
				txtCustomCommand.setEnabled(false);
			} else {
				txtCustomCommand.setText(cmd);
				txtCustomCommand.setEnabled(true);
				cmbBrowsers.setEnabled(false);
			}
			rdBtnSelectBrowser.setSelected(b);
			rdBtnSpecifyCommand.setSelected(!b);
		} else {
			initializeDefaults();
		}
	}

	public void initializeDefaults() {
		rdBtnSelectBrowser.setSelected(true);
		actionPerformed(null);
		cmbBrowsers.setSelectedItem(BrowserControl.FIREFOX);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return super.hasChanged();
	}

	public void actionPerformed(ActionEvent e) {

		if (rdBtnSelectBrowser.isSelected()) {
			cmbBrowsers.setEnabled(true);
			txtCustomCommand.setEnabled(false);

		} else if (rdBtnSpecifyCommand.isSelected()) {
			txtCustomCommand.setEnabled(true);
			cmbBrowsers.setEnabled(false);
		}
	}

}
