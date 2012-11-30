package com.iver.andami.iconthemes;

import java.net.URL;

import javax.swing.ImageIcon;

public interface IIconTheme {

	/**
	 * Load all icons from the IconTheme
	 */
	public abstract void load();

	public abstract void setDefault(IIconTheme def);

	public abstract IIconTheme getDefault();

	/**
	 * Returns <code>true</code> if the icon theme contains a mapping for the
	 * specified iconName.
	 * 
	 * @param iconName
	 *            The key to check if it has been registered in this IconTheme
	 * 
	 * @return <code>true</code> if this IconTheme contains
	 *         <code>iconName</code>, <code>false</code> otherwise.
	 */
	public abstract boolean exists(String iconName);

	/**
	 * Gets the ImageIcon associated with the provided key, if the key is
	 * present in the theme, or returns <code>null</code> otherwise.
	 * 
	 * @param iconName
	 *            The key whose associated icon is to be retrieved
	 * 
	 * @return The icon associated with the provided key, or <code>null</code>
	 *         otherwise.
	 */
	public abstract ImageIcon get(String iconName);

	/**
	 * Gets the ImageIcon associated with the provided key, if the key is
	 * present in the theme, or returns <code>null</code> otherwise.
	 * 
	 * @param iconName
	 *            The key whose associated icon is to be retrieved
	 * 
	 * @param loader
	 *            Class loader for localice icons when iconName is a resource
	 *            name.
	 * 
	 * @return The icon associated with the provided key, or
	 *         <code>no-icon</code> otherwise.
	 */
	public abstract ImageIcon get(String iconName, ClassLoader loader);

	/**
	 * <p>
	 * Register in this theme the provided iconName and the associated image.
	 * Developers must not override icons already registered, as this defeats
	 * the purpose of the IconTheme. Therefore, use the <code>exists</code>
	 * method before using <code>register</code>, to ensure the icon is not
	 * previously registered.
	 * </p>
	 * 
	 * @param iconName
	 *            The name of the icon to register. It is the name that will be
	 *            used later to retrieve the icon.
	 * 
	 * @param image
	 *            The image that is going to be associated with the provided
	 *            icon name.
	 */
	public abstract void registerDefault(String iconName, ImageIcon image);

	/**
	 * <p>
	 * Register in this theme the provided iconName and the associated resource.
	 * Developers must not override icons already registered, as this defeats
	 * the purpose of the IconTheme. Therefore, use the <code>exists</code>
	 * method before using <code>register</code>, to ensure the icon is not
	 * previously registered.
	 * </p>
	 * 
	 * @param iconName
	 *            The name of the icon to register. It is the name that will be
	 *            used later to retrieve the icon.
	 * @param resource
	 *            The resource that is going to be asssocioated with the
	 *            providad icon name
	 */
	public abstract void registerDefault(String iconName, Object resource);

	/**
	 * <p>
	 * Register in this theme the provided iconName and the associated image.
	 * Developers must not override icons already registered, as this defeats
	 * the purpose of the IconTheme. Therefore, use the <code>exists</code>
	 * method before using <code>register</code>, to ensure the icon is not
	 * previously registered.
	 * </p>
	 * 
	 * @param iconName
	 *            The name of the icon to register. It is the name that will be
	 *            used later to retrieve the icon.
	 * 
	 * @param image
	 *            The image that is going to be associated with the provided
	 *            icon name.
	 */
	public abstract void register(String iconName, ImageIcon image);

	/**
	 * <p>
	 * Register in this theme the provided iconName and the associated resource.
	 * Developers must not override icons already registered, as this defeats
	 * the purpose of the IconTheme. Therefore, use the <code>exists</code>
	 * method before using <code>register</code>, to ensure the icon is not
	 * previously registered.
	 * </p>
	 * 
	 * @param iconName
	 *            The name of the icon to register. It is the name that will be
	 *            used later to retrieve the icon.
	 * @param resource
	 *            The resource that is going to be asssocioated with the
	 *            providad icon name
	 */
	public abstract void register(String iconName, Object resource);

	/**
	 * Gets the theme name.
	 * 
	 * @return theme name
	 */
	public abstract String getName();

	/**
	 * Sets the theme name.
	 * 
	 * @param themeName
	 */
	public abstract void setName(String themeName);

	/**
	 * Gets the theme description.
	 * 
	 * @return The description of this theme.
	 */
	public abstract String getDescription();

	/**
	 * Sets the theme description. It should be a short description (around
	 * 20-30 words), including the highlights of the theme, the author and maybe
	 * its email address or a link the the theme's homepage.
	 * 
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
	 * Returns the theme version. It defaults to "1.0".
	 * 
	 * @return The version of this theme.
	 */
	public abstract String getVersion();

	/**
	 * Set the theme version.
	 * 
	 * @param version
	 */
	public abstract void setVersion(String version);

	/**
	 * Gets the Object which contains physically contains this theme on disk. It
	 * may be a ZipFile or JarFile, or a directory.
	 * 
	 * @return
	 */
	public abstract Object getResource();

	/**
	 * Sets the file which contains physically contains this theme on disk. It
	 * may be a ZipFile or JarFile, or a directory.
	 * 
	 * @return
	 */
	public abstract void setResource(Object resource);

	/**
	 * Return the URL which is currently associated with the provided icon name,
	 * if this icon was registered as an URL, or <code>null</code> if it is not
	 * present in the theme or it was registered as an IconImage.
	 * 
	 * @param iconName
	 * @return The URL which is currently associated with the provided icon
	 *         name, if this icon was registered as an URL, or <code>null</code>
	 *         if it is not present in the theme or it was registered as an
	 *         IconImage.
	 * 
	 * @deprecated
	 */
	public abstract URL getURL(String iconName);

	public ImageIcon getNoIcon();

}