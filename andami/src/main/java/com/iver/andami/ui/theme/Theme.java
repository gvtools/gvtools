package com.iver.andami.ui.theme;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;

/**
 * Personalization of the program according to the file xml with the necessary
 * information.
 * 
 * @author Vicente Caballero Navarro
 */
public class Theme {
	public static final String CENTERED = "CENTERED";
	public static final String EXPAND = "EXPAND";
	public static final String MOSAIC = "MOSAIC";

	private static String encoding = "UTF-8";
	private static final String ANDAMI_PROPERTIES = "AndamiProperties";
	private static final String APPLICATION_IMAGES = "ApplicationImages";
	private static final String SPLASH_IMAGES = "SplashImages";
	private static final String SPLASH = "Splash";
	private static final String PATH = "path";
	private static final String TIMER = "timer";
	private static final String ICON = "Icon";
	private static final String APPLICATION_NAME = "ApplicationName";
	private static final String VALUE = "value";
	private static final String BACKGROUND_IMAGE = "BackgroundImage";
	private static final String WALLPAPER_TYPE = "WallpaperType";

	private static final String VERSION = "version";
	private static final String FONTPOSITIONX = "x";
	private static final String FONTPOSITIONY = "y";

	private static final String FONTSIZE = "fontsize";
	private static final String FONTCOLOR = "color";

	private ArrayList<String> images = new ArrayList<String>();
	private String icon;
	private ArrayList<String> timers = new ArrayList<String>();
	private ArrayList<String> versions = new ArrayList<String>();
	private ArrayList<String> fontColors = new ArrayList<String>();
	private ArrayList<String> fontSizes = new ArrayList<String>();
	private ArrayList<String> fontpositionsX = new ArrayList<String>();
	private ArrayList<String> fontpositionsY = new ArrayList<String>();

	private String name = null;
	private String backgroundimage;
	private String wallpaperType = CENTERED;

	public static void main(String[] args) {
		Theme theme = new Theme();
		theme.readTheme(new File(
				"c:/workspace/_fwAndami/theme/andami-theme.xml"));
	}

	/**
	 * Read the file xml with the personalization.
	 * 
	 * @param file
	 *            xml
	 */
	public void readTheme(File file) {
		try {
			FileReader fr;

			try {
				fr = new FileReader(file);

				BufferedReader br = new BufferedReader(fr);
				char[] buffer = new char[100];
				br.read(buffer);

				StringBuffer st = new StringBuffer(new String(buffer));
				String searchText = "encoding=\"";
				int index = st.indexOf(searchText);

				if (index > -1) {
					st.delete(0, index + searchText.length());
					encoding = st.substring(0, st.indexOf("\""));
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			fr = new FileReader(file);

			KXmlParser parser = new KXmlParser();
			parser.setInput(new FileInputStream(file), encoding);

			int tag = parser.nextTag();

			if (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
				parser.require(XmlPullParser.START_TAG, null, ANDAMI_PROPERTIES);

				while (tag != XmlPullParser.END_DOCUMENT) {
					// parser.next();
					switch (tag) {
					case XmlPullParser.START_TAG:

						if (parser.getName().compareTo(ANDAMI_PROPERTIES) == 0) {
							parser.nextTag();
							if (parser.getName().compareTo(APPLICATION_IMAGES) == 0) {
								int splashtag = parser.nextTag();
								if (parser.getName().compareTo(SPLASH_IMAGES) == 0) {
									splashtag = parser.nextTag();
									boolean endSplash = false;

									// parser.require(KXmlParser.START_TAG,
									// null, SPLASH);
									while ((splashtag != XmlPullParser.END_DOCUMENT)
											&& !endSplash) {
										if (splashtag == XmlPullParser.END_TAG) {
											splashtag = parser.nextTag();

											continue;
										}

										if (parser.getName().compareTo(SPLASH) == 0) {
											images.add(parser
													.getAttributeValue("", PATH));
											timers.add(parser
													.getAttributeValue("",
															TIMER));
											versions.add(parser
													.getAttributeValue("",
															VERSION));
											fontpositionsX.add(parser
													.getAttributeValue("",
															FONTPOSITIONX));
											fontpositionsY.add(parser
													.getAttributeValue("",
															FONTPOSITIONY));
											fontSizes.add(parser
													.getAttributeValue("",
															FONTSIZE));
											fontColors.add(parser
													.getAttributeValue("",
															FONTCOLOR));

											splashtag = parser.nextTag();
										} else {
											endSplash = true;
										}
									}
								}
								int tagOptions = XmlPullParser.START_TAG;
								while ((tagOptions != XmlPullParser.END_TAG)) {
									if (parser.getName().compareTo(
											BACKGROUND_IMAGE) == 0) {
										backgroundimage = parser
												.getAttributeValue("", PATH);
										tag = parser.next();
									} else if (parser.getName().compareTo(ICON) == 0) {
										icon = parser.getAttributeValue("",
												PATH);
										tag = parser.next();
									} else if (parser.getName().compareTo(
											WALLPAPER_TYPE) == 0) {
										wallpaperType = parser
												.getAttributeValue("", VALUE);
										tag = parser.next();
									}
									tagOptions = parser.nextTag();

								}
							}
						}
						if (parser.getName().compareTo(APPLICATION_NAME) == 0) {
							name = parser.getAttributeValue("", VALUE);
							tag = parser.next();
						}
						break;

					case XmlPullParser.END_TAG:
						break;

					case XmlPullParser.TEXT:

						// System.out.println("[TEXT]["+kxmlParser.getText()+"]");
						break;
					}

					tag = parser.next();
				}
			}

			parser.require(XmlPullParser.END_DOCUMENT, null, null);

			// }
		} catch (Exception e) {
			NotificationManager.addError(
					"Error reading theme: " + file.getAbsolutePath(), e);
		}
	}

	/**
	 * Returns image to the splashwindow.
	 * 
	 * @return ImageIcon[]
	 */
	public ImageIcon[] getSplashImages() {
		ImageIcon[] imgs = new ImageIcon[images.size()];

		for (int i = 0; i < images.size(); i++) {
			imgs[i] = new ImageIcon(images.get(i));
		}

		return imgs;
	}

	public String getTypeDesktop() {
		return wallpaperType;
	}

	/**
	 * Return the icon.
	 * 
	 * @return ImageIcon
	 */
	public ImageIcon getIcon() {
		if (icon == null) {
			return null;
		}

		try {
			return new ImageIcon(icon);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return the backgroundimage.
	 * 
	 * @return ImageIcon
	 */
	public ImageIcon getBackgroundImage() {
		if (backgroundimage == null) {
			return null;
		}

		try {
			return new ImageIcon(backgroundimage);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return the time of the splash images.
	 * 
	 * @return long[]
	 */
	public long[] getTimers() {
		long[] tms = new long[timers.size()];

		for (int i = 0; i < tms.length; i++) {
			tms[i] = Long.parseLong(timers.get(i));
		}

		return tms;
	}

	/**
	 * Return the name of program.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the text to overwrite the images.
	 * 
	 * @return String[]
	 */
	public String[] getVersions() {
		return versions.toArray(new String[0]);
	}

	/**
	 * Return the position of text to overwrite the images.
	 * 
	 * @return Point[]
	 */
	public Point[] getPositions() {
		Point[] points = new Point[fontpositionsX.size()];
		for (int i = 0; i < points.length; i++) {
			try {
				points[i] = new Point(Integer.valueOf(fontpositionsX.get(i)),
						Integer.valueOf(fontpositionsY.get(i)));
			} catch (NumberFormatException e) {
				NotificationManager.addInfo(
						PluginServices.getText(this, "incorrect_position"), e);
			}
		}
		return points;
	}

	/**
	 * Return the font size text to overwrite the images.
	 * 
	 * @return int[]
	 */
	public int[] getFontSizes() {
		int[] sizes = new int[fontSizes.size()];
		for (int i = 0; i < sizes.length; i++) {
			try {
				sizes[i] = Integer.valueOf(fontSizes.get(i));
			} catch (NumberFormatException e) {
				NotificationManager.addInfo(
						PluginServices.getText(this, "incorrect_size"), e);
			}
		}
		return sizes;
	}

	/**
	 * Return the font color of text to overwrite the images.
	 * 
	 * @return Color[]
	 */
	public Color[] getFontColors() {
		Color[] colors = new Color[fontColors.size()];
		for (int i = 0; i < colors.length; i++) {
			try {
				String s = fontColors.get(i);
				String[] rgb = s.split(",");
				colors[i] = new Color(Integer.valueOf(rgb[0]),
						Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2]));
			} catch (Exception e) {
				NotificationManager.addInfo(
						PluginServices.getText(this, "incorrect_color"), e);
			}
		}
		return colors;
	}
}
