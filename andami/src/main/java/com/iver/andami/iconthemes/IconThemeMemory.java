package com.iver.andami.iconthemes;

import javax.swing.ImageIcon;

/**
 * This class is used by the default theme and don�t load icons because they
 * are already in memory.
 */
public class IconThemeMemory extends AbstractIconTheme {

	/**
	 * Constructor
	 * 
	 * @param def
	 *            . The default icon theme
	 */
	public IconThemeMemory(IIconTheme def) {
		setDefault(def);
	}

	/**
	 * Return null, don�t load the icon
	 */
	@Override
	protected ImageIcon loadIcon(String iconName, Object resource) {
		return null;
	}

	/**
	 * Don�t load any icon. They are already in memory
	 */
	@Override
	public void load() {

	}

}
