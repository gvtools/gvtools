package com.iver.core.preferences.general;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.utiles.GenericFileFilter;

public class DirExtensionsPage extends AbstractPreferencePage {
	private ImageIcon icon;
	private String id;
	private JPanel pN = null;
	private JPanel pC = null;
	private boolean changed = false;
	private JPanel jPanel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private String pluginDirectory;

	public DirExtensionsPage() {
		super();
		initialize();
		id = this.getClass().getName();
		setParentID(GeneralPage.class.getName());
	}

	private void initialize() {
		icon = PluginServices.getIconTheme().get("file-manager");
		this.setLayout(new BorderLayout());
		this.setSize(new java.awt.Dimension(386, 177));
		this.add(getPN(), java.awt.BorderLayout.NORTH);
		this.add(getPC(), java.awt.BorderLayout.CENTER);
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "directorio_extensiones");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		pluginDirectory = Launcher.getAndamiConfig().getPluginsDirectory();
		getJTextField().setText(pluginDirectory);
	}

	public void storeValues() {
		// Se escribe el directorio de los plugins
		pluginDirectory = getJTextField().getText();
		Launcher.getAndamiConfig().setPluginsDirectory(pluginDirectory);
	}

	public void initializeDefaults() {

	}

	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * This method initializes pN
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPN() {
		if (pN == null) {
			pN = new JPanel();
		}
		return pN;
	}

	/**
	 * This method initializes pC
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPC() {
		if (pC == null) {
			pC = new JPanel();
			pC.add(getJPanel(), null);
		}
		return pC;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "directorio"),
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(200, 20));
			jTextField.setText(pluginDirectory);
			jTextField.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					changed = true;
				}

				public void keyReleased(KeyEvent e) {
					changed = true;
				}

				public void keyTyped(KeyEvent e) {
					changed = true;
				}
			});
		}
		return jTextField;
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
						changed = true;
					}
				}
			});

		}
		return jButton;
	}

	public boolean isValueChanged() {
		return changed;
	}

	public void setChangesApplied() {
		changed = false;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

