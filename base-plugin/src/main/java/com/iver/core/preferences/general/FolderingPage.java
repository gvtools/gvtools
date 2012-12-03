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
 * $Id: FolderingPage.java 35999 2011-08-05 10:54:59Z jlopez $
 * $Log$
 * Revision 1.8  2007-01-26 13:35:06  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2006/10/02 07:12:12  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2006/09/13 16:21:00  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/09/12 12:09:51  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/09/12 11:49:04  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/09/12 10:11:25  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2006/09/08 11:56:24  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.core.preferences.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;
import com.iver.utiles.XMLEntity;

/**
 * 
 * In the FolderingPage the user sets which folder paths should be used by
 * default.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class FolderingPage extends AbstractPreferencePage {
	private static Preferences prefs = Preferences.userRoot().node(
			"gvsig.foldering");

	private static final String PROJECTS_FOLDER_PROPERTY_NAME = "ProjectsFolder";
	private static final String DATA_FOLDER_PROPERTY_NAME = "DataFolder";
	private static final String TEMPLATES_FOLDER_PROPERTY_NAME = "TemplatesFolder";
	private static final String SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME = "SymbolLibraryFolder";
	private JTextField txtProjectsFolder;
	private JTextField txtDataFolder;
	private JTextField txtTemplatesFolder;
	private JTextField txtSymbolLibraryFolder;
	private JButton btnSelectProjectsFolder;
	private JButton btnSelectDataFolder;
	private JButton btnSelectTemplatesFolder;
	private JButton btnSelectSymbolLibraryFolder;
	private ImageIcon icon;
	private ActionListener btnFileChooserAction;

	public FolderingPage() {
		super();
		setParentID(GeneralPage.id);
		icon = PluginServices.getIconTheme().get("folder-icon");

		btnFileChooserAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path;
				if (e.getSource().equals(btnSelectProjectsFolder)) {
					path = txtProjectsFolder.getText();
				} else if (e.getSource().equals(btnSelectDataFolder)) {
					path = txtDataFolder.getText();
				} else if (e.getSource().equals(btnSelectSymbolLibraryFolder)) {
					path = txtSymbolLibraryFolder.getText();
				} else {
					path = txtTemplatesFolder.getText();
				}

				// The file filter for the JFileChooser
				FileFilter def = new FileFilter() {
					public boolean accept(File f) {
						return (f.isDirectory());
					}

					public String getDescription() {
						return null;
					}
				};

				File file = new File(path);
				JFileChooser fc;
				if (file.exists()) {
					fc = new JFileChooser(file);
				} else {
					fc = new JFileChooser();
				}

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setMultiSelectionEnabled(false);
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(def);
				int result = fc.showOpenDialog(FolderingPage.this);

				if (result == JFileChooser.APPROVE_OPTION
						&& (file = fc.getSelectedFile()) != null) {
					if (e.getSource().equals(btnSelectProjectsFolder)) {
						txtProjectsFolder.setText(file.getAbsolutePath());
					} else if (e.getSource().equals(btnSelectDataFolder)) {
						txtDataFolder.setText(file.getAbsolutePath());
					} else if (e.getSource().equals(
							btnSelectSymbolLibraryFolder)) {
						txtSymbolLibraryFolder.setText(file.getAbsolutePath());
					} else {
						txtTemplatesFolder.setText(file.getAbsolutePath());
					}
				}
			}

		};
		btnSelectProjectsFolder = new JButton(PluginServices.getText(this,
				"browse"));
		btnSelectProjectsFolder.addActionListener(btnFileChooserAction);
		btnSelectDataFolder = new JButton(
				PluginServices.getText(this, "browse"));
		btnSelectDataFolder.addActionListener(btnFileChooserAction);
		btnSelectTemplatesFolder = new JButton(PluginServices.getText(this,
				"browse"));
		btnSelectTemplatesFolder.addActionListener(btnFileChooserAction);
		btnSelectSymbolLibraryFolder = new JButton(PluginServices.getText(this,
				"browse"));
		btnSelectSymbolLibraryFolder.addActionListener(btnFileChooserAction);

		JLabel lblProjectsFolder = new JLabel("<html><b>"
				+ PluginServices.getText(this,
						"options.foldering.projects_folder") + "</b></html>");
		addComponent(lblProjectsFolder);
		addComponent(txtProjectsFolder = new JTextField(30),
				btnSelectProjectsFolder);
		addComponent(new JLabel(" "));

		JLabel lblDataFolder = new JLabel("<html><b>"
				+ PluginServices.getText(this, "options.foldering.data_folder")
				+ "</b></html>");
		addComponent(lblDataFolder);
		addComponent(txtDataFolder = new JTextField(30), btnSelectDataFolder);
		addComponent(new JLabel(" "));

		JLabel lblTemplatesFolder = new JLabel("<html><b>"
				+ PluginServices.getText(this,
						"options.foldering.template_folder") + "</b></html>");
		addComponent(lblTemplatesFolder);
		addComponent(txtTemplatesFolder = new JTextField(30),
				btnSelectTemplatesFolder);
		addComponent(new JLabel(" "));

		JLabel lblSymbolLibraryFolder = new JLabel("<html><b>"
				+ PluginServices.getText(this,
						"options.foldering.symbol_library_folder")
				+ "</b></html>");
		addComponent(lblSymbolLibraryFolder);
		addComponent(txtSymbolLibraryFolder = new JTextField(30),
				btnSelectSymbolLibraryFolder);
		addComponent(new JLabel(" "));
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();

		if (xml.contains(PROJECTS_FOLDER_PROPERTY_NAME)) {
			prefs.put(PROJECTS_FOLDER_PROPERTY_NAME,
					xml.getStringProperty(PROJECTS_FOLDER_PROPERTY_NAME));
		}

		if (xml.contains(DATA_FOLDER_PROPERTY_NAME)) {
			prefs.put(DATA_FOLDER_PROPERTY_NAME,
					xml.getStringProperty(DATA_FOLDER_PROPERTY_NAME));
		}

		if (xml.contains(TEMPLATES_FOLDER_PROPERTY_NAME)) {
			prefs.put(TEMPLATES_FOLDER_PROPERTY_NAME,
					xml.getStringProperty(TEMPLATES_FOLDER_PROPERTY_NAME));
		}

		if (xml.contains(SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME)) {
			prefs.put(SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME,
					xml.getStringProperty(SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME));
		}

	}

	public void storeValues() throws StoreException {
		File f;
		String path, propertyName;
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();

		// Projects folder
		propertyName = PROJECTS_FOLDER_PROPERTY_NAME;
		path = txtProjectsFolder.getText();

		if (path.equals("")) {
			if (xml.contains(propertyName)) {
				xml.remove(propertyName);
			}
			prefs.remove(propertyName);
		} else {
			f = new File(path);
			if (f.exists()) {
				if (xml.contains(propertyName)) {
					xml.remove(propertyName);
				}
				xml.putProperty(propertyName, f.getAbsolutePath());
				prefs.put(propertyName, f.getAbsolutePath());
			}
		}

		// Data folder
		propertyName = DATA_FOLDER_PROPERTY_NAME;
		path = txtDataFolder.getText();

		if (path.equals("")) {
			if (xml.contains(propertyName)) {
				xml.remove(propertyName);
			}
			prefs.remove(propertyName);
		} else {
			f = new File(path);
			if (f.exists()) {
				if (xml.contains(propertyName)) {
					xml.remove(propertyName);
				}
				xml.putProperty(propertyName, f.getAbsolutePath());
				prefs.put(propertyName, f.getAbsolutePath());

			}
		}

		// Templates folder
		propertyName = TEMPLATES_FOLDER_PROPERTY_NAME;
		path = txtTemplatesFolder.getText();

		if (path.equals("")) {
			if (xml.contains(propertyName)) {
				xml.remove(propertyName);
			}
			prefs.remove(propertyName);
		} else {
			f = new File(path);
			if (f.exists()) {
				if (xml.contains(propertyName)) {
					xml.remove(propertyName);
				}
				xml.putProperty(propertyName, f.getAbsolutePath());
				prefs.put(propertyName, f.getAbsolutePath());

			}
		}

		// Symbol library folder
		propertyName = SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME;
		path = txtSymbolLibraryFolder.getText();

		if (path.equals("")) {
			if (xml.contains(propertyName)) {
				xml.remove(propertyName);
			}
			prefs.remove(propertyName);
		} else {
			f = new File(path);
			if (f.exists()) {
				if (xml.contains(propertyName)) {
					xml.remove(propertyName);
				}
				xml.putProperty(propertyName, f.getAbsolutePath());
				prefs.put(propertyName, f.getAbsolutePath());
			}
		}

	}

	public void setChangesApplied() {
		setChanged(false);
	}

	public String getID() {
		return this.getClass().getName();
	}

	public String getTitle() {
		return PluginServices.getText(this, "options.foldering.title");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();
		if (xml.contains(PROJECTS_FOLDER_PROPERTY_NAME)) {
			txtProjectsFolder.setText(xml
					.getStringProperty(PROJECTS_FOLDER_PROPERTY_NAME));
		}
		if (xml.contains(DATA_FOLDER_PROPERTY_NAME)) {
			txtDataFolder.setText(xml
					.getStringProperty(DATA_FOLDER_PROPERTY_NAME));
		}
		if (xml.contains(TEMPLATES_FOLDER_PROPERTY_NAME)) {
			txtTemplatesFolder.setText(xml
					.getStringProperty(TEMPLATES_FOLDER_PROPERTY_NAME));
		}
		if (xml.contains(SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME)) {
			txtSymbolLibraryFolder.setText(xml
					.getStringProperty(SYMBOL_LIBRARY_FOLDER_PROPERTY_NAME));
		}
	}

	public void initializeDefaults() {
		txtDataFolder.setText("");
		txtTemplatesFolder.setText("");
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return super.hasChanged();
	}

}
