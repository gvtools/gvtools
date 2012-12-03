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
 * $Id: AppearancePage.java 30046 2009-07-20 13:14:21Z vcaballero $
 * $Log$
 * Revision 1.11  2006-09-12 12:09:51  jaume
 * *** empty log message ***
 *
 * Revision 1.10  2006/09/12 11:49:04  jaume
 * *** empty log message ***
 *
 * Revision 1.9  2006/09/12 10:11:25  jaume
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
 * Revision 1.5  2006/08/04 11:44:55  caballero
 * lanzo una excepción cuando falla el método storeValues
 *
 * Revision 1.4  2006/08/04 11:03:43  cesar
 * Set the selected appearance after the combo box is filled (it had no effect otherwise)
 *
 * Revision 1.3  2006/08/02 12:49:07  cesar
 * Install Plastic look and feel before getting the LAF-combo. Hide installed but non-supported themes from the list.
 *
 * Revision 1.2  2006/07/31 10:02:31  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/06/29 16:15:06  jaume
 * Appearance now configurable
 *
 *
 */
package com.iver.core.preferences.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;

/**
 * Appearance page. Where the user can choose Look&Feels and maybe some more
 * stuff.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class AppearancePage extends AbstractPreferencePage {
	private JComboBox lookAndFeelCombo;
	private String id;
	private ImageIcon icon;
	private String lookAndFeel;
	private boolean changed = false;
	private Logger logger = PluginServices.getLogger();
	private PluginServices ps = PluginServices.getPluginServices(this);
	private ActionListener myAction;

	public AppearancePage() {
		super();
		id = this.getClass().getName();
		icon = PluginServices.getIconTheme().get("gnome-settings-theme");
		setParentID(GeneralPage.id);
		// install the plastic look and feel before getting the laf combobox
		UIManager.installLookAndFeel("Plastic XP",
				"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		// install the extra LAF's before getting the LAF combobox
		String osName = (String) System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("mac os x"))
			UIManager.installLookAndFeel("Quaqua",
					"ch.randelshofer.quaqua.QuaquaLookAndFeel");

		addComponent(
				PluginServices.getText(this, "options.general.select_theme"),
				getLookAndFeelComboBox());
		myAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lookAndFeel = ((LF) lookAndFeelCombo.getSelectedItem())
						.getClassName();
				changed = true;
			}
		};

	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "pref.appearance");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		getLookAndFeelComboBox().removeActionListener(myAction);
		lookAndFeel = Launcher.getAndamiConfig().getLookAndFeel();
		if (lookAndFeel == null) {
			lookAndFeel = Launcher.getDefaultLookAndFeel();
		}

		for (int i = 0; i < getLookAndFeelComboBox().getModel().getSize(); i++) {
			LF element = (LF) getLookAndFeelComboBox().getModel().getElementAt(
					i);
			if (element.getClassName().equals(lookAndFeel)) {
				getLookAndFeelComboBox().setSelectedIndex(i);
				break;
			}
		}
		getLookAndFeelComboBox().addActionListener(myAction);
	}

	public void storeValues() {
		Launcher.getAndamiConfig().setLookAndFeel(lookAndFeel);
	}

	public void initializeDefaults() {
		// getLookAndFeelComboBox().removeActionListener(myAction);

		final String defaultLookAndFeel = Launcher.getDefaultLookAndFeel();
		for (int i = 0; i < getLookAndFeelComboBox().getModel().getSize(); i++) {
			LF lf = (LF) getLookAndFeelComboBox().getModel().getElementAt(i);
			if (defaultLookAndFeel.equals(lf.getClassName())) {
				getLookAndFeelComboBox().setSelectedIndex(i);
				break;
			}
		}
		// getLookAndFeelComboBox().addActionListener(myAction);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	private JComboBox getLookAndFeelComboBox() {
		if (lookAndFeelCombo == null) {
			LookAndFeelInfo[] lfs = UIManager.getInstalledLookAndFeels();
			ArrayList a = new ArrayList();
			for (int i = 0; i < lfs.length; i++) {
				LF lf = new LF(lfs[i]);

				// test if the look and feel is supported in this platform
				// before adding it to the list
				Class lafClassDef;
				try {
					lafClassDef = Class.forName(lfs[i].getClassName());
					LookAndFeel laf;
					laf = (LookAndFeel) lafClassDef.newInstance();

					if (laf.isSupportedLookAndFeel())
						a.add(lf);

				} catch (ClassNotFoundException e2) {
					logger.error(
							ps.getText("error_loading_look_and_feel_"
									+ lfs[i].getName()), e2);
				} catch (InstantiationException e1) {
					logger.error(
							ps.getText("error_loading_look_and_feel_"
									+ lfs[i].getName()), e1);
				} catch (IllegalAccessException e1) {
					logger.error(
							ps.getText("error_loading_look_and_feel_"
									+ lfs[i].getName()), e1);
				}
			}
			lookAndFeelCombo = new JComboBox((LF[]) a.toArray(new LF[a.size()]));

		}
		return lookAndFeelCombo;
	}

	private class LF {
		LookAndFeelInfo lfi;

		public LF(LookAndFeelInfo lfi) {
			this.lfi = lfi;
		}

		public String getClassName() {
			return lfi.getClassName();
		}

		public String toString() {
			return lfi.getName();
		}
	}

	public boolean isValueChanged() {
		return changed;
	}

	public void setChangesApplied() {
		changed = false;
	}
}
