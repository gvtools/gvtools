package com.iver.core.preferences.general;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;

public class ExtensionsPage extends AbstractPreferencePage {

	private JLabel jLabel = null;
	private ImageIcon icon;

	/**
	 * This is the default constructor
	 */
	public ExtensionsPage() {
		super();
		icon = PluginServices.getIconTheme().get(
				"aplication-preferences-extensions");
		setParentID(GeneralPage.class.getName());
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setText(PluginServices.getText(this,
				"configurar_todas_las_extensiones"));
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(jLabel, java.awt.BorderLayout.CENTER);
	}

	public String getID() {
		return this.getClass().getName();
	}

	public String getTitle() {
		return PluginServices.getText(this, "extensiones");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
	}

	public void storeValues() {
	}

	public void initializeDefaults() {
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return false; // Because it does not manage values
	}

	public void setChangesApplied() {
	}
}
