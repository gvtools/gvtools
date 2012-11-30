package com.iver.andami.iconthemes;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;

/**
 * This class extends AbstractIconTheme and implements the abstract methods of
 * this class. This methods are <code>loadIcon</code> and <code>load</code>.
 * This methods allows load one icon or all icons in the resource.
 */
public class IconThemeZip extends AbstractIconTheme {

	/**
	 * Constructor.Constructs an Icon Theme with a default one.
	 * 
	 * @param def
	 *            . The default icon theme
	 */
	public IconThemeZip(IIconTheme def) {
		setDefault(def);
	}

	/**
	 * Allows load a icon when this is inside in a Zip file. IconName is the
	 * name or key of the icon and "resource"
	 */
	@Override
	protected ImageIcon loadIcon(String iconName, Object resource) {
		if (resource instanceof ZipEntry && getResource() instanceof ZipFile) {
			ZipEntry entry = (ZipEntry) resource;
			ZipFile file = (ZipFile) getResource();
			try {
				InputStream is = file.getInputStream(entry);
				int size = (int) entry.getSize();
				if (size == -1)
					return null;
				byte[] b = new byte[size];
				int offset = 0;
				int chunk = 0;
				while ((size - offset) > 0) {
					chunk = is.read(b, offset, size - offset);
					if (chunk == -1) {
						break;
					}
					offset += chunk;
				}
				return new ImageIcon(b);
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * Allows load all icons in the zip file.
	 */
	@Override
	public void load() {
		if (getResource() instanceof ZipFile) {
			ZipFile zipFile = (ZipFile) getResource();
			ImageFileFilter filter = new ImageFileFilter();
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (!entry.isDirectory()) {
					if (filter.accept(new File(zipFile.getName()),
							entry.getName())) {
						register(computeKey(entry.getName()), entry);
					}
				}
			}

		}
	}

	/**
	 * This class allows filter the images in the zip file. Allows to load the
	 * images with a apropiate extension.
	 */
	class ImageFileFilter implements FilenameFilter {
		public boolean accept(File dir, String fileName) {
			String extension = "";
			int pointPos = fileName.lastIndexOf(".");
			if (pointPos > 0 && pointPos < (fileName.length() - 1))
				extension = fileName.substring(pointPos + 1).toLowerCase();
			if (extension.equals("jpg") || extension.equals("jpeg")
					|| extension.equals("png") || extension.equals("gif"))
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
