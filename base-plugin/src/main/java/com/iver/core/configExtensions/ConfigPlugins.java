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
package com.iver.core.configExtensions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.plugins.config.generate.Extension;
import com.iver.andami.plugins.config.generate.Extensions;
import com.iver.andami.plugins.config.generate.PluginConfig;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.utiles.GenericFileFilter;

/**
 * Diálogo de Configuración de ANDAMI.
 * 
 * @author Vicente Caballero Navarro
 * @deprecated
 */
public class ConfigPlugins extends JPanel implements IWindow {
	private JTree jTree = null;
	private JPanel jPanel = null; // @jve:decl-index=0:visual-constraint="113,10"
	private JPanel jPanel1 = null;
	private JSplitPane jSplitPane = null;
	private JCheckBox chbActivar = null;
	private JTextField jTextField = null;
	private JPanel pGeneral = null;
	private JButton jButton = null;
	private JPanel pOpciones = null;
	private JTabbedPane jTabbedPane1 = null;
	private JPanel pDescripcion = null;
	private JPanel pDirectorio = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPane1 = null;
	private WindowInfo m_viewinfo = null;
	private JPanel pBotones = null;
	private JButton bAceptar = null;
	private JButton bCancelar = null;
	private HashMap listExt = new HashMap();
	private HashMap listPlugins = new HashMap();
	private JTextArea taDescripcion = null;
	private JScrollPane jScrollPane = null;
	private Extension ext = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel = null;
	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;
	private JLabel jLabel1 = null;
	private JComboBox cmbIdioma = null;

	/**
	 * This is the default constructor
	 */
	public ConfigPlugins() {
		super();
		initialize();
	}

	/**
	 * Añade los nodes al arbol que representa a todos los plugins y extesiones
	 * que hay.
	 * 
	 * @param top
	 */
	private void addNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode nameExtension = null;
		DefaultMutableTreeNode namePlugin = null;
		HashMap pc = Launcher.getPluginConfig();
		ArrayList array = new ArrayList();
		Iterator iter = pc.values().iterator();

		while (iter.hasNext()) {
			array.add(((PluginConfig) iter.next()).getExtensions());
		}

		Extensions[] exts = (Extensions[]) array.toArray(new Extensions[0]);

		for (int i = 0; i < exts.length; i++) {
			if (exts[i].getSkinExtension() != null) {
			}

			for (int j = 0; j < exts[i].getExtensionCount(); j++) {
				Extension ext = exts[i].getExtension(j);
				String pn = null;
				String sExt = ext.getClassName().toString();
				pn = sExt.substring(0, sExt.lastIndexOf("."));
				namePlugin = new DefaultMutableTreeNode(pn);

				if (!listPlugins.containsKey(namePlugin.getUserObject()
						.toString())) {
					listPlugins.put(namePlugin.getUserObject().toString(),
							namePlugin);
					top.add(namePlugin);
					nameExtension = new DefaultMutableTreeNode(sExt.substring(
							sExt.lastIndexOf(".") + 1, sExt.length())); // replaceFirst(namePlugin.getUserObject().toString()+".",""));
					listExt.put(sExt, ext);
					namePlugin.add(nameExtension);
				} else {
					nameExtension = new DefaultMutableTreeNode(sExt.substring(
							sExt.lastIndexOf(".") + 1, sExt.length())); // sExt.replaceFirst(namePlugin.getUserObject().toString()+".",""));
					listExt.put(sExt, ext);
					((DefaultMutableTreeNode) listPlugins.get(namePlugin
							.getUserObject().toString())).add(nameExtension);
				}
			}
		}
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("ANDAMI");
			addNodes(root);
			jTree = new JTree(root);

			jTree.setName("ANDAMI");
			jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					if (e.getPath().getParentPath() != null) {
						ext = (Extension) listExt
								.get(e.getPath().getParentPath()
										.getLastPathComponent().toString()
										+ "."
										+ e.getPath().getLastPathComponent()
												.toString());

						if (ext == null) {
							getChbActivar().setVisible(false);
							getTaDescripcion().setText(
									"Plugin : "
											+ e.getNewLeadSelectionPath()
													.toString());
						} else {
							try {
								getChbActivar().setSelected(
										((Extension) Launcher.getExtension(ext
												.getClassName())).getActive());
								getChbActivar().setVisible(true);
								getTaDescripcion().setText(
										((Extension) Launcher.getExtension(ext
												.getClassName()))
												.getDescription());
								getJTextField1().setText(
										String.valueOf(ext.getPriority()));
							} catch (NullPointerException npe) {
								getChbActivar().setVisible(false);
								getTaDescripcion().setText(
										"Plugin : "
												+ e.getNewLeadSelectionPath()
														.toString());
							}
						}
					}
				}
			});
		}

		return jTree;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setName("jPanel");
			jPanel.setPreferredSize(new java.awt.Dimension(150, 150));
			jPanel.setSize(435, 299);
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jPanel.add(getPBotones(), java.awt.BorderLayout.SOUTH);
		}

		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
			jPanel1.setName("jPanel1");
			jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
			jPanel1.add(getJTabbedPane1(), null);
		}

		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJPanel2());
		}

		return jSplitPane;
	}

	/**
	 * This method initializes chbActivar
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getChbActivar() {
		if (chbActivar == null) {
			chbActivar = new JCheckBox();
			chbActivar.setSelected(true);
			chbActivar.setVisible(false);
			chbActivar.setText(PluginServices.getText(this,
					"extension_activada"));
			chbActivar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ext.setActive(chbActivar.isSelected());
				}
			});
		}

		return chbActivar;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(200, 20));
			jTextField
					.setText(Launcher.getAndamiConfig().getPluginsDirectory());
		}

		return jTextField;
	}

	/**
	 * This method initializes pGeneral
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPGeneral() {
		if (pGeneral == null) {
			pGeneral = new JPanel();
			pGeneral.add(getPDirectorio(), null);
			pGeneral.add(getJPanel4(), null);
		}

		return pGeneral;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(PluginServices.getText(this, "examinar"));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.addChoosableFileFilter(new GenericFileFilter("",
							PluginServices.getText(this,
									"directorio_extensiones")));

					if (jfc.showOpenDialog((Component) PluginServices
							.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
						getJTextField().setText(
								jfc.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}

		return jButton;
	}

	/**
	 * This method initializes pOpciones
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPOpciones() {
		if (pOpciones == null) {
			jLabel = new JLabel();
			pOpciones = new JPanel();
			pOpciones.setLayout(new BorderLayout());
			jLabel.setText(PluginServices.getText(this, "prioridad"));
			pOpciones.add(getChbActivar(), java.awt.BorderLayout.NORTH);
			pOpciones.add(getPDescripcion(), java.awt.BorderLayout.CENTER);
			pOpciones.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
		}

		return pOpciones;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab(PluginServices.getText(this, "opciones"), null,
					getPOpciones(), null);
			jTabbedPane1.addTab(PluginServices.getText(this, "general"), null,
					getPGeneral(), null);
		}

		return jTabbedPane1;
	}

	/**
	 * This method initializes pDescripcion
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPDescripcion() {
		if (pDescripcion == null) {
			pDescripcion = new JPanel();
			pDescripcion.setLayout(new BorderLayout());
			pDescripcion.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			pDescripcion
					.setBorder(javax.swing.BorderFactory.createTitledBorder(
							null,
							PluginServices.getText(this, "descripcion"),
							javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
							javax.swing.border.TitledBorder.DEFAULT_POSITION,
							null, null));
		}

		return pDescripcion;
	}

	/**
	 * This method initializes pDirectorio
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPDirectorio() {
		if (pDirectorio == null) {
			pDirectorio = new JPanel();
			pDirectorio.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, PluginServices.getText(this, "directorio"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			pDirectorio.add(getJTextField(), null);
			pDirectorio.add(getJButton(), null);
		}

		return pDirectorio;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setPreferredSize(new java.awt.Dimension(120, 200));
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}

		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTree());
		}

		return jScrollPane1;
	}

	/**
	 * This method initializes pBotones
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPBotones() {
		if (pBotones == null) {
			pBotones = new JPanel();
			pBotones.add(getBAceptar(), null);
			pBotones.add(getBCancelar(), null);
		}

		return pBotones;
	}

	/**
	 * This method initializes bAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBAceptar() {
		if (bAceptar == null) {
			bAceptar = new JButton();
			bAceptar.setText(PluginServices.getText(this, "aceptar"));
			bAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Se escribe el config de los plugins
					marshalPlugins();

					// Se escribe el directorio de los plugins
					Launcher.getAndamiConfig().setPluginsDirectory(
							getJTextField().getText());

					// Se escribe el idioma
					LanguageItem sel = (LanguageItem) cmbIdioma
							.getSelectedItem();
					Launcher.getAndamiConfig().setLocaleLanguage(
							sel.locale.getLanguage());
					Launcher.getAndamiConfig().setLocaleCountry(
							sel.locale.getCountry());
					Launcher.getAndamiConfig().setLocaleVariant(
							sel.locale.getVariant());

					PluginServices.getMDIManager().closeWindow(
							ConfigPlugins.this);
				}
			});
		}

		return bAceptar;
	}

	/**
	 * This method initializes bCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBCancelar() {
		if (bCancelar == null) {
			bCancelar = new JButton();
			bCancelar.setText(PluginServices.getText(this, "cancelar"));
			bCancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					unmarshalPlugins();
					PluginServices.getMDIManager().closeWindow(
							ConfigPlugins.this);
				}
			});
		}

		return bCancelar;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaDescripcion() {
		if (taDescripcion == null) {
			taDescripcion = new JTextArea();
			taDescripcion.setPreferredSize(new java.awt.Dimension(200, 100));
			taDescripcion.setText("");
			taDescripcion.setEditable(false);
			taDescripcion.setEnabled(false);
			taDescripcion.setLineWrap(true);
		}

		return taDescripcion;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTaDescripcion());
			jScrollPane
					.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return jScrollPane;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(20, 20));

			jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					ext.setPriority(Integer.parseInt(jTextField1.getText()));
				}
			});
		}

		return jTextField1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.add(getJTextField1(), null);
			jPanel3.add(jLabel, null);
		}

		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel1 = new JLabel();
			jPanel4 = new JPanel();
			jLabel1.setText("Idioma:");
			jPanel4.add(jLabel1, null);
			jPanel4.add(getCmbIdioma(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cmbIdioma
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbIdioma() {
		if (cmbIdioma == null) {
			cmbIdioma = new JComboBox();
			cmbIdioma.setPreferredSize(new java.awt.Dimension(195, 20));

			Locale esp = new Locale("es");
			Locale eng = new Locale("en");
			Locale fra = new Locale("fr");
			Locale ita = new Locale("it");
			Locale val = new Locale("ca");
			Locale cs = new Locale("cs"); // Checo
			Locale eu = new Locale("eu"); // euskera
			Locale brasil = new Locale("pt", "BR");
			Locale de = new Locale("de"); // Alemán
			Locale gr = new Locale("el", "GR"); // Griego
			Locale gl = new Locale("gl", "GL"); // Griego

			// Parche para valenciano/catalán valencià/català
			String strValenciano = PluginServices.getText(this, "__valenciano");
			// Parche para euskera

			Locale localeActual = Locale.getDefault(); // Se configura en la
														// clase Launcher
			String strEuskera;
			if (eu.getDisplayLanguage().compareTo("vascuence") == 0)
				strEuskera = "Euskera";
			else
				strEuskera = eu.getDisplayLanguage();

			LanguageItem[] lenguajes = new LanguageItem[] {
					new LanguageItem(esp, esp.getDisplayLanguage()),
					new LanguageItem(eng, eng.getDisplayLanguage()),
					new LanguageItem(fra, fra.getDisplayLanguage()),
					new LanguageItem(ita, ita.getDisplayLanguage()),
					new LanguageItem(val, strValenciano),
					new LanguageItem(cs, cs.getDisplayLanguage()),
					new LanguageItem(eu, strEuskera),
					new LanguageItem(brasil, brasil.getDisplayLanguage()),
					new LanguageItem(de, de.getDisplayLanguage()),
					new LanguageItem(gr, gr.getDisplayLanguage()),
					new LanguageItem(gl, gl.getDisplayLanguage()) };

			DefaultComboBoxModel model = new DefaultComboBoxModel(lenguajes);

			for (int i = 0; i < lenguajes.length; i++) {
				if (lenguajes[i].locale.equals(Locale.getDefault())) {
					model.setSelectedItem(lenguajes[i]);
				}
			}

			cmbIdioma.setModel(model);
		}
		return cmbIdioma;
	}

	private class LanguageItem {
		public Locale locale;
		public String description;

		public LanguageItem(Locale loc, String str) {
			locale = loc;
			description = str;
		}

		public String toString() {
			return description;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 */
	public static void main(String[] args) {
		ConfigPlugins cp = new ConfigPlugins();
		cp.setVisible(true);

		JDialog dialog = new JDialog();
		dialog.getContentPane().add(cp);
		dialog.setSize(400, 300);
		dialog.show(true);
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(443, 299);
		this.setLocation(1, 1);
		this.add(getJPanel(), java.awt.BorderLayout.CENTER);
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		if (m_viewinfo == null) {
			m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
			m_viewinfo.setTitle(PluginServices.getText(this,
					"configurar_ANDAMI"));
		}

		return m_viewinfo;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	/**
	 * Escribe sobre el config.xml, la nueva configuración.
	 */
	public void marshalPlugins() {
		HashMap pc = Launcher.getPluginConfig();
		ArrayList array = new ArrayList();
		Iterator iter = pc.keySet().iterator();

		while (iter.hasNext()) {
			Object obj = iter.next();
			PluginConfig pconfig = (PluginConfig) pc.get(obj);
			FileWriter writer;

			try {
				writer = new FileWriter(Launcher.getAndamiConfig()
						.getPluginsDirectory()
						+ File.separator
						+ (String) obj
						+ File.separator + "config.xml");

				try {
					pconfig.marshal(writer);
				} catch (MarshalException e1) {
					e1.printStackTrace();
				} catch (ValidationException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// hay que refrescar la aplicación
			// /((App)App.instance).run();
		}
	}

	/**
	 * Lee del config.xml la configuración.
	 */
	public void unmarshalPlugins() {
		HashMap pc = Launcher.getPluginConfig();
		ArrayList array = new ArrayList();
		Iterator iter = pc.keySet().iterator();

		while (iter.hasNext()) {
			Object obj = iter.next();
			PluginConfig pconfig = (PluginConfig) pc.get(obj);

			try {
				FileReader reader = new FileReader(Launcher.getAndamiConfig()
						.getPluginsDirectory()
						+ File.separator
						+ (String) obj
						+ File.separator + "config.xml");
				PluginConfig.unmarshal(reader);
			} catch (Exception e) {
				System.out.println("Exception unmarshalPlugin " + e);
			}
		}

		// hay que refrescar la aplicación
		// /((App)App.instance).run();
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
