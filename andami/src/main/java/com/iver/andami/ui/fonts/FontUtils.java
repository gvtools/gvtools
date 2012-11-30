/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2006 IVER T.I. and Generalitat Valenciana.
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
package com.iver.andami.ui.fonts;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.UIManager;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.messages.Messages;

/**
 * Several methods to manage the user interface fonts.
 * 
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 * 
 */
public class FontUtils {

	/**
	 * Changes the default UIManager font to the provided font.
	 * 
	 * @param fontName
	 */
	public static void setFont(String fontName) {
		String[] fieldList = { "Button.font", "CheckBox.font",
				"CheckBoxMenuItem.font", "ColorChooser.font", "ComboBox.font",
				"DesktopIcon.font", "EditorPane.font",
				"FormattedTextField.font", "Label.font", "List.font",
				"Menu.font", "MenuBar.font", "MenuItem.font",
				"OptionPane.font", "Panel.font", "PasswordField.font",
				"PopupMenu.font", "ProgressBar.font", "RadioButton.font",
				"RadioButtonMenuItem.font", "ScrollPane.font", "Slider.font",
				"Spinner.font", "TabbedPane.font", "Table.font",
				"TableHeader.font", "TextArea.font", "TextField.font",
				"TextPane.font", "TitledBorder.font", "ToggleButton.font",
				"ToolBar.font", "ToolTip.font", "Tree.font", "Viewport.font" };

		Font font;

		for (int i = fieldList.length - 1; i >= 0; i--) {
			font = UIManager.getFont(fieldList[i]);
			if (font != null)
				UIManager.put(fieldList[i],
						new Font(fontName, Font.PLAIN, font.getSize()));
		}
	}

	/**
	 * Inits the UIManager fonts, so that it can display current's language
	 * symbols. The font is only changed when necessary.
	 */
	public static void initFonts() {

		if (Launcher.getAndamiConfig().getLocaleLanguage().equals("km")) {
			FontUtils.initKhmerFonts();
			return;
		}
		// conservative behaviour: we just change the font, if the default one
		// can't correctly display the current language
		Font defaultFont = UIManager.getFont("Label.font");
		if (defaultFont == null)
			return;
		if (defaultFont.canDisplayUpTo(Messages
				.getString("MDIFrame.quiere_salir")) == -1) {
			PluginServices.getLogger().info(
					"Fonts configuration was not necessary");
			// listSystemFonts();
			return;
		}

		Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		HashSet<String> workingFonts = new HashSet<String>();

		for (int i = allfonts.length - 1; i >= 0; i--) {
			if (allfonts[i].canDisplayUpTo(Messages
					.getString("MDIFrame.quiere_salir")) == -1) {
				workingFonts.add(allfonts[i].getName());
			}
		}

		// try to set the preferred font
		String[] preferredFonts = getPreferedFonts(Locale.getDefault()
				.getLanguage());
		for (int i = 0; i < preferredFonts.length; i++) {
			if (workingFonts.contains(preferredFonts[i])) {
				setFont(preferredFonts[i]);
				PluginServices.getLogger()
						.info("FontSet: " + preferredFonts[i]);
				return;
			}
		}

		// try to set any working font
		Iterator<String> iterator = workingFonts.iterator();

		if (iterator.hasNext()) {
			String fontName = iterator.next();
			setFont(fontName);
			PluginServices.getLogger().info("FontSet: " + fontName);
		}
	}

	private static void initKhmerFonts() {
		// Buscar la fuente de sistema para jemer "Khmer OS System" y instalarla
		// Si no se encuentra, instalar la primera fuente que se encuentre que
		// contenga caracteres khmeres.

		Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();

		for (int i = allfonts.length - 1; i >= 0; i--) {
			if (allfonts[i].getName().equals("Khmer OS System")) {
				setFont("Khmer OS System");
				return;
			}
		}

		for (int i = allfonts.length - 1; i >= 0; i--) {
			if (allfonts[i]
					.canDisplayUpTo("\u1785\u17C1\u1789\u200B\u1796\u17B8 gvSIG ?") == -1) {
				setFont(allfonts[i].getName());
				return;
			}

		}
	}

	/**
	 * Returns an ordered list of preferred fonts for the provided language. As
	 * there is several fonts which can be used to display each language, this
	 * list provides a hint about which is the more suitable font for this
	 * language.
	 * 
	 * This method does not check whether the fonts are available in the running
	 * system.
	 * 
	 * @param lang
	 * @return
	 */
	private static String[] getPreferedFonts(String lang) {
		// TODO this should be read from a file, for each language
		if (lang.equals("zh")) {
			String[] preferredChineseFonts = { "AR PL KaitiM GB",
					"AR PL SungtiL GB", "Kochi Gothic", "SimSun" };
			return preferredChineseFonts;
		}
		return new String[0];
	}
}
