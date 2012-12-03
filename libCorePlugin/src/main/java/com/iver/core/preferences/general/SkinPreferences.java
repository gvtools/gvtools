package com.iver.core.preferences.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.plugins.config.generate.PluginConfig;
import com.iver.andami.plugins.config.generate.SkinExtension;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;
import com.iver.core.CorePlugin;
import com.iver.core.mdiManager.NewSkin;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.swing.JComboBox;

public class SkinPreferences extends AbstractPreferencePage {

	private String id;
	private ImageIcon icon;
	private Vector<String> listSkinsPlugins;
	private JComboBox comboBox;
	private String skinName = NewSkin.class.getCanonicalName();
	private static Preferences prefs = Preferences.userRoot().node(
			"gvsig.configuration.3D");

	public SkinPreferences() {
		super();
		// TODO Auto-generated constructor stub
		id = this.getClass().getName();
		setParentID(GeneralPage.id);
		icon = PluginServices.getIconTheme().get("gnome-settings-theme");
	}

	public void setChangesApplied() {
		// System.out.println("ESTOY LLAMANDO A setChangesApplied()");

	}

	public void storeValues() throws StoreException {
		// System.out.println("ESTOY LLAMANDO A storeValues()");
		PluginServices ps = PluginServices.getPluginServices(CorePlugin.NAME);
		XMLEntity xml = ps.getPersistentXML();
		xml.putProperty("Skin-Selected", skinName);
	}

	public String getID() {
		return id;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public JPanel getPanel() {

		if (comboBox == null) {
			comboBox = getComboBox();

			addComponent(new JLabel(PluginServices.getText(this, "skin_label")));

			addComponent(comboBox);
		}

		return this;
	}

	private JComboBox getComboBox() {
		comboBox = new JComboBox(listSkinsPlugins);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String newSkinName = (String) cb.getSelectedItem();
				if (newSkinName != null)
					if (!newSkinName.equals(skinName)) {
						skinName = newSkinName;
						JOptionPane.showMessageDialog(null,
								PluginServices.getText(this, "skin_message"));
					}
			}

		});

		comboBox.setSelectedItem(skinName);
		return comboBox;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return PluginServices.getText(this, "skin");
	}

	public void initializeDefaults() {
		// TODO Auto-generated method stub
		// System.out.println("inicialize Defaults");

	}

	public void initializeValues() {
		// TODO Auto-generated method stub
		// System.out.println("inicialize values");

		listSkinsPlugins = new Vector<String>();

		HashMap pluginsConfig = Launcher.getPluginConfig();
		Iterator i = pluginsConfig.keySet().iterator();

		while (i.hasNext()) {
			String name = (String) i.next();
			PluginConfig pc = (PluginConfig) pluginsConfig.get(name);

			if (pc.getExtensions().getSkinExtension() != null) {
				SkinExtension[] se = pc.getExtensions().getSkinExtension();
				for (int j = 0; j < se.length; j++) {

					listSkinsPlugins.add(se[j].getClassName());
					System.out.println("plugin de skin + name");
				}
			}
		}

		PluginServices ps = PluginServices.getPluginServices(CorePlugin.NAME);
		XMLEntity xml = ps.getPersistentXML();
		if (xml.contains("Skin-Selected")) {
			skinName = xml.getStringProperty("Skin-Selected");

		}

	}

	public boolean isValueChanged() {
		// TODO Auto-generated method stub
		// System.out.println("is value changed");
		return true;
	}

}
