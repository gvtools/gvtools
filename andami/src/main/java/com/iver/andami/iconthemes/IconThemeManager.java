package com.iver.andami.iconthemes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParserException;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;

/**
 * This class controls the icon theme. Contains two themes, the first is a
 * default theme and the second is the current theme. When it creates the class
 * the default and the current theme are the same. Allows change the current
 * theme, register a new theme, delete one,... and all the methods that the
 * <code>Collection</code> interface contains. The themes are stored in an
 * ArrayList.
 * 
 * @author eustaquio
 */
public class IconThemeManager implements Collection<IIconTheme> {

	private IIconTheme def;
	private IIconTheme current;
	private File themesDir = null;
	ArrayList<IIconTheme> themes = new ArrayList<IIconTheme>();
	private final String themeDefinitionFile = "theme.xml";
	private static IconThemeManager iconThemeManager = null;

	public static IconThemeManager getIconThemeManager() {
		if (iconThemeManager == null) {
			iconThemeManager = new IconThemeManager();
		}
		return iconThemeManager;
	}

	/**
	 * Default constructor. Creates a iconThemeMemory by default and assigns it
	 * like default and current. The default theme is add to the themes
	 * ArrayList.
	 */
	public IconThemeManager() {
		IconThemeMemory aux = new IconThemeMemory(null);
		def = aux;
		def.setName("Default");
		current = aux;
		themes.add(def);

	}

	/**
	 * Gets the default theme
	 * 
	 * @return the default theme.
	 */
	public IIconTheme getDefault() {
		return def;
	}

	/**
	 * Sets the iconTheme like current theme
	 * 
	 * @param iconTheme
	 */
	public void setCurrent(IIconTheme iconTheme) {
		if (themes.contains(iconTheme)) {
			current = iconTheme;
			// saveConfig(current);
		} else {
			register(iconTheme);
			current = iconTheme;
			// saveConfig(current);
		}
	}

	/**
	 * Gets the current theme
	 * 
	 * @return current the current theme
	 */
	public IIconTheme getCurrent() {
		return current;
	}

	/**
	 * Stores a icon theme that receives like a parametre
	 * 
	 * @param iconTheme
	 */
	public void register(IIconTheme iconTheme) {
		themes.add(iconTheme);
		iconTheme.setDefault(def);
	}

	/**
	 * Returns the theme that has been registered with the name that receives
	 * like parameter
	 * 
	 * @param themeName
	 * @return the theme that contains like name in its properties the parameter
	 */
	public IIconTheme getTheme(String themeName) {
		for (int i = 0; i < themes.size(); i++) {
			if (themes.get(i).getName().equals(themeName))
				return themes.get(i);
		}
		return null;
	}

	/**
	 * Set the directory to read the icon themes.
	 * 
	 * @param themesDir
	 *            The directory in which the icon themes are stored
	 * 
	 * @throws FileNotFoundException
	 *             If the provided directory does not exist or is not a
	 *             directory
	 */
	public void setThemesDir(File themesDir) throws FileNotFoundException {
		if (!themesDir.exists() || !themesDir.isDirectory())
			throw new FileNotFoundException();
		this.themesDir = themesDir;
	}

	/**
	 * Gets the themes directory
	 * 
	 * @return
	 */
	public File getThemesDir() {
		return themesDir;
	}

	/**
	 * Gets the Icon theme stored in the configuration
	 * 
	 * @return IIconTheme
	 */
	public IIconTheme getIconThemeFromConfig() {
		if (Launcher.getAndamiConfig().getAndamiOptions() == null
				|| Launcher.getAndamiConfig().getAndamiOptions().getIconTheme() == null
				|| Launcher.getAndamiConfig().getAndamiOptions().getIconTheme()
						.getResource() == null)
			return null;

		com.iver.andami.config.generate.IconTheme selectedTheme = Launcher
				.getAndamiConfig().getAndamiOptions().getIconTheme();

		try {
			setThemesDir(new File(selectedTheme.getBasedir()));
			File themeFile = new File(selectedTheme.getBasedir()
					+ File.separator + selectedTheme.getResource());
			if (themeFile.exists()) {
				IIconTheme info;
				try {
					// try to load a ZIPPED theme
					ZipFile zipfile = new ZipFile(selectedTheme.getResource());
					info = readInfoFromZip(zipfile);
					return info;
				} catch (ZipException e) {
					// ZIPPED theme failed, try to load from directory
					if (themeFile.isFile() && themeFile.canRead()) {
						info = readInfoFromDir(new File(
								selectedTheme.getResource()));
						return info;
					}
				} catch (IOException e) {
				}
			}
		} catch (FileNotFoundException e1) {
		}
		return null;
	}

	/**
	 * Gets the base name of a entry
	 * 
	 * @param fullname
	 * @return
	 */
	private String basename(String fullname) {
		String[] parts = fullname.split(File.separator + "|/");
		return parts[parts.length - 1];
	}

	/**
	 * Returns an icon theme from a zip file that receives like a parameter
	 * 
	 * @param zipFile
	 * @return IconThemeZip
	 * @throws IOException
	 */
	private IconThemeZip readInfoFromZip(ZipFile zipFile) throws IOException {
		IconThemeZip themeInfo;
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		ZipEntry xmlEntry = null, dirEntry = null;
		// search for theme.xml and the directory names
		while (entries.hasMoreElements()
				&& (xmlEntry == null || dirEntry == null)) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				dirEntry = entry;
			}
			if (basename(entry.getName()).equals(themeDefinitionFile)) {
				xmlEntry = entry;
			}
		}

		try {
			// try with the XML file
			if (xmlEntry != null) {
				themeInfo = readXML(zipFile.getInputStream(xmlEntry));
				if (themeInfo.getDescription() == null)
					themeInfo.setDescription(zipFile.getName());
				if (themeInfo.getName() == null)
					themeInfo.setName(themeInfo.getDescription());
				themeInfo.setResource(zipFile);
				return themeInfo;
			}
		} catch (XmlPullParserException e) {
			PluginServices.getLogger().error(
					"Error loading theme " + zipFile.getName() + ". ", e);
		}

		themeInfo = new IconThemeZip(def);
		themeInfo.setResource(zipFile);
		// now try with the directory
		if (dirEntry != null) {
			themeInfo.setName(dirEntry.getName());
			themeInfo.setDescription(dirEntry.getName());
			return themeInfo;
		} else { // otherwise just use the zipName
			themeInfo.setName(zipFile.getName());
			themeInfo.setDescription(zipFile.getName());
			return themeInfo;
		}
	}

	/**
	 * Read the properties of IconThemeZip from a XML
	 * 
	 * @param xmlStream
	 * @return IconThemeZip
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private IconThemeZip readXML(InputStream xmlStream)
			throws XmlPullParserException, IOException {
		KXmlParser parser = new KXmlParser();
		// we use null encoding, in this way kxml2 tries to detect the encoding
		parser.setInput(xmlStream, null);
		IconThemeZip themeInfo = new IconThemeZip(def);
		for (parser.next(); parser.getEventType() != KXmlParser.END_DOCUMENT; parser
				.next()) {
			// este bucle externo recorre las etiquetas de primer y segundo
			// nivel
			if (parser.getEventType() == KXmlParser.START_TAG) {
				if (parser.getName().equals("name")) {
					themeInfo.setName(parser.nextText());
				} else if (parser.getName().equals("description")) {
					themeInfo.setDescription(parser.nextText());
				} else if (parser.getName().equals("version")) {
					themeInfo.setVersion(parser.nextText());
				}
			}
		}
		return themeInfo;
	}

	/**
	 * Returns a IconThemeDir from a directory
	 * 
	 * @param dir
	 * @return
	 */
	private IconThemeDir readInfoFromDir(File dir) {
		File themeDefinition = new File(dir + File.separator
				+ themeDefinitionFile);
		IconThemeDir themeInfo;
		try {
			// try reading the XML file
			if (themeDefinition.exists() && themeDefinition.isFile()) {
				themeInfo = readXMLDir(new FileInputStream(themeDefinition));
				if (themeInfo.getDescription() == null)
					themeInfo.setDescription(dir.getName());
				if (themeInfo.getName() == null)
					themeInfo.setName(themeInfo.getDescription());
				themeInfo.setResource(dir);
				return themeInfo;
			}
		} catch (IOException e) {
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		// the XML parsing failed, just show the dir name
		themeInfo = new IconThemeDir(def);
		themeInfo.setName(dir.getName());
		themeInfo.setDescription(dir.getName());
		themeInfo.setResource(dir);
		return themeInfo;
	}

	/**
	 * Read the properties of IconThemeDir from a XML
	 * 
	 * @param xmlStream
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private IconThemeDir readXMLDir(InputStream xmlStream)
			throws XmlPullParserException, IOException {
		KXmlParser parser = new KXmlParser();
		// we use null encoding, in this way kxml2 tries to detect the encoding
		parser.setInput(xmlStream, null);
		IconThemeDir themeInfo = new IconThemeDir(def);
		for (parser.next(); parser.getEventType() != KXmlParser.END_DOCUMENT; parser
				.next()) {
			// este bucle externo recorre las etiquetas de primer y segundo
			// nivel
			if (parser.getEventType() == KXmlParser.START_TAG) {
				if (parser.getName().equals("name")) {
					themeInfo.setName(parser.nextText());
				} else if (parser.getName().equals("description")) {
					themeInfo.setDescription(parser.nextText());
				} else if (parser.getName().equals("version")) {
					themeInfo.setVersion(parser.nextText());
				}
			}
		}
		return themeInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return themes.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		if (themes.size() == 0)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return themes.contains(o);
	}

	@Override
	public Iterator<IIconTheme> iterator() {
		return themes.iterator();
	}

	@Override
	public Object[] toArray() {
		return themes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return themes.toArray(a);
	}

	@Override
	public boolean add(IIconTheme o) {
		return themes.add(o);
	}

	@Override
	public boolean remove(Object o) {
		return themes.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return themes.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends IIconTheme> c) {
		return themes.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return themes.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return themes.retainAll(c);
	}

	@Override
	public void clear() {
		themes.clear();
	}
}
