/* gvSIG. Sistema de Informaciï¿½n Geogrï¿½fica de la Generalitat Valenciana
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
 *   Av. Blasco Ibï¿½ï¿½ez, 50
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;

public class LanguagePage extends AbstractPreferencePage {
	private static LanguageItem DEFAULT_LANGUAGE;
	private ImageIcon icon;

	private String id;

	private JPanel pN = null;

	private JPanel pC = null;

	private JPanel pS = null;

	private JPanel jPanel = null;

	private JComboBox cmbIdioma = null;

	private JLabel label = null;

	private int langIndex;
	private boolean changed = false;

	public LanguagePage() {
		super();
		initialize();
		id = this.getClass().getName();
		setParentID(GeneralPage.class.getName());
	}

	private void initialize() {
		icon = PluginServices.getIconTheme().get(
				"aplication-preferences-language");
		this.setLayout(new BorderLayout());
		this.setSize(new java.awt.Dimension(386, 177));
		this.add(getPN(), java.awt.BorderLayout.NORTH);
		this.add(getPC(), java.awt.BorderLayout.CENTER);
		this.add(getPS(), java.awt.BorderLayout.SOUTH);
		langIndex = getJComboBox().getSelectedIndex();
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "idioma");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
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

	public void storeValues() {
		// Se escribe el idioma
		LanguageItem sel = (LanguageItem) cmbIdioma.getSelectedItem();
		Launcher.getAndamiConfig().setLocaleLanguage(sel.locale.getLanguage());
		Launcher.getAndamiConfig().setLocaleCountry(sel.locale.getCountry());
		Launcher.getAndamiConfig().setLocaleVariant(sel.locale.getVariant());
		langIndex = getJComboBox().getSelectedIndex();

	}

	public void initializeDefaults() {
		getJComboBox().setSelectedItem(DEFAULT_LANGUAGE);
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
	 * This method initializes pS
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPS() {
		if (pS == null) {
			pS = new JPanel();
			pS.add(getLabel(), BorderLayout.SOUTH);
		}
		return pS;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "idioma"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getJComboBox(), BorderLayout.NORTH);

		}
		return jPanel;
	}

	/**
	 * This method initializes Label
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel(
					PluginServices
							.getText(
									null,
									"Los_cambios_efectuados_sobre_estos_valores_se_aplicaran_al_reiniciar_la_aplicacion"));
		}
		return label;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cmbIdioma == null) {
			cmbIdioma = new JComboBox();
			cmbIdioma.setPreferredSize(new java.awt.Dimension(195, 20));

			Locale esp = new Locale("es");
			Locale eng = new Locale("en");
			Locale usa = new Locale("en", "US"); // English US
			Locale fra = new Locale("fr");
			Locale ita = new Locale("it");
			Locale val = new Locale("ca");
			Locale cs = new Locale("cs"); // Checo
			Locale eu = new Locale("eu"); // euskera
			Locale pt = new Locale("pt");
			Locale brasil = new Locale("pt", "BR");
			Locale de = new Locale("de"); // Alemï¿½n
			Locale gl = new Locale("gl", "GL"); // Gallego
			Locale zh = new Locale("zh", "ZH"); // Chino
			Locale ru = new Locale("ru", "RU"); // Ruso
			Locale el = new Locale("el", "GR"); // Griego
			Locale ro = new Locale("ro"); // Romanian
			Locale pl = new Locale("pl"); // Polish

			// Default language

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

			// English as default language
			DEFAULT_LANGUAGE = new LanguageItem(eng, eng.getDisplayLanguage());

			LanguageItem[] lenguajes = new LanguageItem[] {
					new LanguageItem(esp, esp.getDisplayLanguage()),
					DEFAULT_LANGUAGE,
					new LanguageItem(usa, usa.getDisplayLanguage() + "-"
							+ usa.getCountry()),
					new LanguageItem(fra, fra.getDisplayLanguage()),
					new LanguageItem(ita, ita.getDisplayLanguage()),
					new LanguageItem(val, strValenciano),
					new LanguageItem(cs, cs.getDisplayLanguage()),
					new LanguageItem(eu, strEuskera),
					new LanguageItem(pt, pt.getDisplayLanguage()),
					new LanguageItem(brasil, brasil.getDisplayLanguage() + "-"
							+ brasil.getCountry()),
					new LanguageItem(de, de.getDisplayLanguage()),
					new LanguageItem(gl, gl.getDisplayLanguage()),
					new LanguageItem(zh, zh.getDisplayLanguage()),
					new LanguageItem(ru, ru.getDisplayLanguage()),
					new LanguageItem(el, el.getDisplayLanguage()),
					new LanguageItem(ro, ro.getDisplayLanguage()),
					new LanguageItem(pl, pl.getDisplayLanguage()) };

			DefaultComboBoxModel model = new DefaultComboBoxModel(lenguajes);

			/*
			 * Comparamos primero con los "Locales" completos para admitir las
			 * variaciones de los paises y si no encontramos la combinacion
			 * idioma-pais entonces buscamos unicamente por el nombre del
			 * idioma, y si seguimos sin encontrarlo seleccionamos el idioma por
			 * defecto.
			 */
			boolean foundLanguage = false;
			for (int i = 0; i < lenguajes.length; i++) {
				if (lenguajes[i].locale.equals(Locale.getDefault())) {
					model.setSelectedItem(lenguajes[i]);
					foundLanguage = true;
					break;
				}
			}
			if (!foundLanguage) {
				for (int i = 0; i < lenguajes.length; i++) {
					if (lenguajes[i].locale.getISO3Language().equals(
							Locale.getDefault().getISO3Language())) {
						model.setSelectedItem(lenguajes[i]);
						foundLanguage = true;
						break;
					}
				}
			}
			if (!foundLanguage) {
				model.setSelectedItem(DEFAULT_LANGUAGE);
			}
			cmbIdioma.setModel(model);
			cmbIdioma.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changed = true;
				}
			});
		}
		return cmbIdioma;
	}

	public boolean isValueChanged() {
		return changed;
	}

	public void setChangesApplied() {
		changed = false;

	}
} // @jve:decl-index=0:visual-constraint="10,10"
