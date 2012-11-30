/*
 * Copyright (C) 2007 IVER T.I. and Generalitat Valenciana.
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
 *   Av. Blasco Ib��ez, 50
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

package com.iver.andami.iconthemes;

import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * <p>
 * This class represents an icon theme, which is basically a mapping of symbolic
 * icon names, and real icons (or icon paths). This is useful to change an
 * application's icons in an easy way. An icon theme is usually read from disk
 * on start up, but can also be created or modified on a later time.
 * </p>
 * 
 * <p>
 * Developers are encouraged to always use the
 * <code>get(iconName, fallbackImage)</code> methods to get icons, as they
 * ensure that the icons are not overwritten in the theme, but it also ensures
 * than an image is got in case the icon was still not registered. Note that in
 * this case, the iconName gets registered (it is associated with the provided
 * fallbackImage).
 * </p>
 * 
 * <p>
 * Developers are encouraged to NOT override icons which are present in the
 * theme, as this defeats the purpose of IconThemes.
 * </p>
 * 
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 */
public abstract class AbstractIconTheme implements IIconTheme {
	HashMap<String, Object> iconList = new HashMap<String, Object>();
	private String name = null;
	private String description = null;
	private String version = "1.0";
	private Object resource = null;
	IIconTheme defaultTheme = null;

	/**
	 * Abstract method that allows load an a icon. This method will be
	 * reimplemented by inherit classes.
	 * 
	 * @param iconName
	 * @param resource
	 * @return
	 */
	protected abstract ImageIcon loadIcon(String iconName, Object resource);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#load()
	 */
	public abstract void load();

	/**
	 * Returns the logger
	 * 
	 * @return
	 */
	protected Logger log() {
		return Logger.getLogger(this.getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.andami.iconthemes.IIconTheme#setDefault(com.iver.andami.iconthemes
	 * .AbstractIconTheme)
	 */
	public void setDefault(IIconTheme def) {
		if (def == this) {
			defaultTheme = null;
		} else {
			defaultTheme = def;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getDefault()
	 */
	public IIconTheme getDefault() {
		return defaultTheme;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#exists(java.lang.String)
	 */
	public boolean exists(String iconName) {
		if (iconList.containsKey(iconName)) {
			return true;
		}
		if (defaultTheme != null && defaultTheme.exists(iconName)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#get(java.lang.String)
	 */
	public ImageIcon get(String iconName) {
		return get(iconName, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#get(java.lang.String,
	 * java.lang.ClassLoader)
	 */
	public ImageIcon get(String iconName, ClassLoader loader) {

		if (loader != null && iconName.contains(".")) {
			Logger.getLogger(this.getClass()).warn(
					"Loading icon from resource: '" + iconName + "'");
			return toImageIcon(loader.getResource(iconName), iconName);
		}

		if (defaultTheme == null) {
			Object object = iconList.get(iconName);
			if (object != null) {
				return toImageIcon(object, iconName);
			}
			return getNoIcon();
		}
		Object object = iconList.get(iconName);
		if (object != null) {
			return toImageIcon(object, iconName);
		}
		if (defaultTheme.exists(iconName)) {
			return defaultTheme.get(iconName, null);
		}
		return getNoIcon();
	}

	public ImageIcon getNoIcon() {
		Object object = iconList.get("no-icon");
		if (object != null) {
			return toImageIcon(object, "no-icon");
		}
		if (defaultTheme == null) {
			return null;
		}
		return defaultTheme.getNoIcon();
	}

	protected ImageIcon toImageIcon(Object object, String iconName) {
		if (object == null) {
			return null;
		}
		if (object instanceof URL) {
			return new ImageIcon((URL) object);
		}
		ImageIcon icon = loadIcon(iconName, object);
		return icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.andami.iconthemes.IIconTheme#registerDefault(java.lang.String,
	 * javax.swing.ImageIcon)
	 */
	public void registerDefault(String iconName, ImageIcon image) {
		if (defaultTheme != null)
			defaultTheme.register(iconName, image);
		else
			register(iconName, image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.andami.iconthemes.IIconTheme#registerDefault(java.lang.String,
	 * java.lang.Object)
	 */
	public void registerDefault(String iconName, Object resource) {
		if (defaultTheme != null)
			defaultTheme.register(iconName, resource);
		else
			register(iconName, resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#register(java.lang.String,
	 * javax.swing.ImageIcon)
	 */
	public void register(String iconName, ImageIcon image) {
		iconList.put(iconName, image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#register(java.lang.String,
	 * java.lang.Object)
	 */
	public void register(String iconName, Object resource) {
		iconList.put(iconName, resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#setName(java.lang.String)
	 */
	public void setName(String themeName) {
		name = themeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.andami.iconthemes.IIconTheme#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getVersion()
	 */
	public String getVersion() {
		return version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#setVersion(java.lang.String)
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getResource()
	 */
	public Object getResource() {
		return resource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#setResource(java.lang.Object)
	 */
	public void setResource(Object resource) {
		this.resource = resource;
	}

	/**
	 * Returns the name of the icon theme
	 */
	public String toString() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.iconthemes.IIconTheme#getURL(java.lang.String)
	 */
	public URL getURL(String iconName) {
		Object object = defaultTheme.get(iconName);
		if (object != null && object instanceof URL)
			return (URL) object;
		return null;
	}

}
