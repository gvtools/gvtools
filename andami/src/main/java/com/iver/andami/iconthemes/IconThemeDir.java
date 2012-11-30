package com.iver.andami.iconthemes;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * This class extends AbstractIconTheme and implements the abstract methods of
 * this class. This methods are <code>loadIcon</code> and <code>load</code>.
 * This methods allows load one icon or all icons in the resource.
 */
public class IconThemeDir extends AbstractIconTheme {

	/**
	 * Constructor.Constructs an Icon Theme with a default one.
	 * 
	 * @param def
	 *            . The default icon theme
	 */
	public IconThemeDir(IIconTheme def) {
		setDefault(def);
	}

	/**
	 * Allows load a icon when this is inside in a directoyr. IconName is the
	 * name or key of the icon and "resource" is the URL or the icon
	 */
	@Override
	protected ImageIcon loadIcon(String iconName, Object resource) {
		if (resource instanceof URL) {
			// ok, we got an imagePath, let's see if it's valid
			ImageIcon icon = new ImageIcon((URL) resource);
			if (icon.getImage() != null) {
				// the icon was successfully created from the imagePath
				return icon;
			} else {
				log().error(
						"Registered icon does not map to a valid image -- key: "
								+ iconName + " -- URL: " + resource.toString());
				return null;
			}

		} else if (resource instanceof ImageIcon) {
			return (ImageIcon) resource;

		}
		return null;
	}

	/**
	 * Allows to load all icons in the directory.
	 */
	@Override
	public void load() {
		if (getResource() instanceof File) {
			File basedir = (File) getResource();
			if (basedir.isDirectory()) {
				File[] imageList = basedir.listFiles(new ImageFileFilter());
				String name;
				for (int i = imageList.length - 1; i >= 0; i--) {
					name = computeKey(imageList[i].getName());
					try {
						register(name, imageList[i].toURI().toURL());
					} catch (MalformedURLException e) {
					}
				}
			}

		}

	}

	/**
	 * This class allows filter the images in the directory. Allows to load the
	 * images with a apropiate extension.
	 */
	class ImageFileFilter implements FilenameFilter {
		public boolean accept(File dir, String fileName) {
			String extension = "";
			int pointPos = fileName.lastIndexOf(".");
			if (pointPos > 0 && pointPos < (fileName.length() - 1))
				extension = fileName.substring(pointPos + 1).toLowerCase();
			if (extension.equals("jpg") || extension.equals("jpeg")
					|| extension.equals("png") || extension.equals("gif")

			)
				return true;
			else
				return false;
		}
	}

	/**
	 * Returns the key of the image without extension
	 * 
	 * @param fileName
	 * @return string
	 */
	private String computeKey(String fileName) {
		int pointPos = fileName.lastIndexOf(".");
		if (pointPos != -1)
			return fileName.substring(0, pointPos);
		else
			return fileName;
	}

}
