/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
 */
package org.gvsig.raster.datastruct.persistence;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.gvsig.raster.datastruct.ColorItem;
import org.gvsig.raster.datastruct.ColorTable;
import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.xmlpull.v1.XmlPullParserException;

/**
 * La clase ColorTableLibraryPersistence sirve para controlar las tablas de
 * color genericas, como si fuera una libreria de tablas de color. Aqu� se
 * comprueban los cambios de versi�n.
 * 
 * @version 02/07/2007
 * @author BorSanZa - Borja S�nchez Zamorano (borja.sanchez@iver.es)
 */
public class ColorTableLibraryPersistence {

	/**
	 * Devuelve la lista de ficheros de paletas, si no existe el fichero
	 * devolvera una lista vacia
	 * 
	 * @param palettesBasePath
	 * @return
	 */
	private static List<String> getPaletteFileListDisc(String palettesBasePath) {
		updateVersion(palettesBasePath);

		File paletteFiles = new File(palettesBasePath);

		List<String> fileList = new ArrayList<String>();

		if (paletteFiles.exists()) {
			File[] list = paletteFiles.listFiles();
			for (int i = 0; i < list.length; i++)
				fileList.add(list[i].getName());
		}

		return fileList;
	}

	/**
	 * Devuelve la lista de ficheros de paletas, si no existe el fichero creara
	 * una paleta por defecto y la devolvera.
	 * 
	 * @param palettesBasePath
	 * @return
	 */
	public static List<String> getPaletteFileList(String palettesBasePath) {
		return getPaletteFileList(palettesBasePath, true);
	}

	/**
	 * Devuelve la lista de ficheros de paletas, en caso de que no exista la
	 * paleta y se especifique forceCreate a true, se creara la paleta por
	 * defecto.
	 * 
	 * @param palettesBasePath
	 * @param forceCreate
	 * @return
	 */
	public static List<String> getPaletteFileList(String palettesBasePath,
			boolean forceCreate) {
		List<String> fileList = getPaletteFileListDisc(palettesBasePath);

		if (forceCreate && (fileList.size() == 0)) {
			ColorTableLibraryPersistence persistence = new ColorTableLibraryPersistence();
			createVersionFromXML(palettesBasePath,
					persistence.getDefaultPaletteXML());
			fileList = getPaletteFileListDisc(palettesBasePath);
		}

		return fileList;
	}

	/**
	 * Devuelve el XML de una paleta por defecto
	 * 
	 * @return
	 */
	public String getDefaultPaletteXML() {
		URL url = getClass().getResource("xml/palettes.xml");
		StringBuffer contents = new StringBuffer();
		try {
			InputStream inputStream = url.openStream();

			int i;
			while (true) {
				i = inputStream.read();
				if (i == -1)
					break;
				char c = (char) i;
				contents.append(c);
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contents.toString();
	}

	/**
	 * Crea los ficheros que forman la paleta de color de la version 1.1 a
	 * traves de un XML que se le pasa por parametro
	 * 
	 * @param palettesPath
	 */
	private static void createVersionFromXML(String palettesBasePath, String xml) {
		new File(palettesBasePath).mkdir();
		KXmlParser parser = new KXmlParser();
		StringReader reader = new StringReader(xml);
		try {
			parser.setInput(reader);
			int tag = parser.nextTag();

			parser.require(KXmlParser.START_TAG, null, "palettes");
			tag = parser.nextTag();
			parser.require(KXmlParser.START_TAG, null, "palette_list");
			parser.skipSubTree();
			parser.require(KXmlParser.END_TAG, null, "palette_list");
			tag = parser.nextTag();

			while (tag == KXmlParser.START_TAG) {
				parser.require(KXmlParser.START_TAG, null, "palette");
				if (parser.getAttributeCount() == 2) {
					// Generar nuevo fichero
					KXmlSerializer parserOutput = new KXmlSerializer();
					FileOutputStream fileOutputStream = new FileOutputStream(
							palettesBasePath + File.separator
									+ parser.getAttributeValue(0) + ".xml");

					parserOutput.setOutput(fileOutputStream, null);
					parserOutput.startDocument("UTF-8", null);
					parserOutput.startTag(null, "ColorTable");
					parserOutput.attribute(null, "name",
							parser.getAttributeValue(0));
					parserOutput.attribute(null, "version", "1.1");

					tag = parser.nextTag();
					parser.require(KXmlParser.START_TAG, null, "table");
					tag = parser.nextTag();

					parserOutput.text("\n");
					List<ColorItem> items = new ArrayList<ColorItem>();
					while (tag == KXmlParser.START_TAG) {
						parser.require(KXmlParser.START_TAG, null, "entry");
						if (parser.getAttributeCount() == 3) {
							String rgb = parser.getAttributeValue(1)
									.substring(
											parser.getAttributeValue(1)
													.indexOf(",") + 1,
											parser.getAttributeValue(1)
													.length());

							int a = Integer.valueOf(
									parser.getAttributeValue(1).substring(
											0,
											parser.getAttributeValue(1)
													.indexOf(","))).intValue();
							int r = Integer.valueOf(
									rgb.substring(0, rgb.indexOf(",")))
									.intValue();
							int g = Integer.valueOf(
									rgb.substring(rgb.indexOf(",") + 1,
											rgb.lastIndexOf(","))).intValue();
							int b = Integer.valueOf(
									rgb.substring(rgb.lastIndexOf(",") + 1,
											rgb.length())).intValue();

							ColorItem colorItem = new ColorItem();
							colorItem.setColor(new Color(r, g, b, a));
							colorItem.setInterpolated(50);
							colorItem.setNameClass(parser.getAttributeValue(0));
							colorItem.setValue(Double.parseDouble(parser
									.getAttributeValue(2)));
							items.add(colorItem);
						}
						tag = parser.nextTag();
						parser.require(KXmlParser.END_TAG, null, "entry");
						tag = parser.nextTag();
					}
					parser.require(KXmlParser.END_TAG, null, "table");
					tag = parser.nextTag();

					ColorTable colorTable = new ColorTable();
					colorTable.createPaletteFromColorItems(items, true);
					items = colorTable.getColorItems();
					for (int i = 0; i < items.size(); i++) {
						ColorItem colorItem = items.get(i);
						parserOutput.startTag(null, "Color");
						parserOutput.attribute(null, "value",
								String.valueOf(colorItem.getValue()));
						parserOutput.attribute(null, "name",
								String.valueOf(colorItem.getNameClass()));
						Color color = colorItem.getColor();
						parserOutput.attribute(
								null,
								"rgb",
								String.valueOf(color.getRed() + ","
										+ color.getGreen() + ","
										+ color.getBlue()));
						parserOutput.attribute(null, "interpolated",
								String.valueOf(colorItem.getInterpolated()));
						parserOutput.endTag(null, "Color");
						parserOutput.text("\n");
					}

					for (int i = 0; i < items.size(); i++) {
						ColorItem colorItem = items.get(i);
						parserOutput.startTag(null, "Alpha");
						parserOutput.attribute(null, "value",
								String.valueOf(colorItem.getValue()));
						parserOutput
								.attribute(null, "alpha", String
										.valueOf(colorItem.getColor()
												.getAlpha()));
						parserOutput.attribute(null, "interpolated",
								String.valueOf(colorItem.getInterpolated()));
						parserOutput.endTag(null, "Alpha");
						parserOutput.text("\n");
					}

					parserOutput.endTag(null, "ColorTable");
					parserOutput.text("\n");
					parserOutput.endDocument();
					// Cerrar nuevo fichero
					fileOutputStream.close();
				}
				parser.require(KXmlParser.END_TAG, null, "palette");
				tag = parser.nextTag();
			}
			parser.require(KXmlParser.END_TAG, null, "palettes");
		} catch (XmlPullParserException xmlEx) {
			System.out
					.println("El fichero de paletas predeterminadas no tiene la estructura correcta:\n	"
							+ xmlEx.getMessage());
		} catch (IOException e) {
		}
	}

	/**
	 * Si existe la version de paleta 1.1, no lo actualizar�, en caso contrario,
	 * buscara la version 1.0 y si lo encuentra lo subir� a la 1.1
	 * 
	 * @param palettesPath
	 */
	public static void updateVersion_1_0_to_1_1(String palettesBasePath) {
		// Si no existe la paleta antigua, pero si que existe la copia de
		// seguridad,
		// la restauramos
		File palettesFile = new File(new File(palettesBasePath).getParent()
				.toString() + File.separator + "palettes.xml");
		if (!palettesFile.exists()) {
			// Cambiar nombre a antiguo fichero
			File oldFile = new File(new File(palettesBasePath).getParent()
					.toString() + File.separator + "palettes.xml~");
			if (oldFile.exists()) {
				oldFile.renameTo(new File(new File(palettesBasePath)
						.getParent().toString()
						+ File.separator
						+ "palettes.xml"));
			} else {
				ColorTableLibraryPersistence ctlp = new ColorTableLibraryPersistence();
				String text = ctlp.getDefaultPaletteXML();
				palettesFile = new File(new File(palettesBasePath).getParent()
						.toString() + File.separator + "palettes.xml");
				try {
					FileWriter writer = new FileWriter(palettesFile);
					writer.write(text);
					writer.close();
				} catch (IOException e) {
				}
			}
		}

		// Si existe el directorio de la version 1.1 no hacemos la migraci�n
		File directoryFile = new File(palettesBasePath);
		if (directoryFile.exists())
			return;

		// Si no encontramos la paleta antigua, nos olvidamos de continuar
		palettesFile = new File(new File(palettesBasePath).getParent()
				.toString() + File.separator + "palettes.xml");
		if (!palettesFile.exists())
			return;

		// Paleta antigua encontrada, podemos migrar a la versi�n nueva
		try {
			FileInputStream inputStream = new FileInputStream(palettesFile);

			StringBuffer contents = new StringBuffer();
			try {

				int i;
				while (true) {
					i = inputStream.read();
					if (i == -1)
						break;
					char c = (char) i;
					contents.append(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			createVersionFromXML(palettesBasePath, contents.toString());

			inputStream.close();

		} catch (FileNotFoundException fnfEx) {
			fnfEx.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Si existe la version de paleta 1.0, la actualizara a la 1.1 y renombrar�
	 * la antigua version.
	 * 
	 * @param palettesBasePath
	 * @param colorTable
	 */
	public static void save_to_1_1(String palettesBasePath,
			ColorTable colorTable) {
		try {
			// Generar nuevo fichero
			KXmlSerializer parserOutput = new KXmlSerializer();
			FileOutputStream out = new FileOutputStream(palettesBasePath
					+ File.separator + colorTable.getName() + ".xml");
			OutputStreamWriter writer = new OutputStreamWriter(out, "UTF8");
			parserOutput.setOutput(writer);
			parserOutput.startDocument("UTF-8", null);
			parserOutput.startTag(null, "ColorTable");
			parserOutput.attribute(null, "name", colorTable.getName());
			parserOutput.attribute(null, "version", "1.1");
			parserOutput.text("\n");

			List<ColorItem> items = colorTable.getColorItems();
			for (int i = 0; i < items.size(); i++) {
				ColorItem colorItem = items.get(i);
				parserOutput.startTag(null, "Color");
				parserOutput.attribute(null, "value",
						String.valueOf(colorItem.getValue()));
				if (colorItem.getNameClass() != null)
					parserOutput.attribute(null, "name",
							String.valueOf(colorItem.getNameClass()));
				else
					parserOutput.attribute(null, "name", "");
				Color color = colorItem.getColor();
				parserOutput.attribute(
						null,
						"rgb",
						String.valueOf(color.getRed() + "," + color.getGreen()
								+ "," + color.getBlue()));
				parserOutput.attribute(null, "interpolated",
						String.valueOf(colorItem.getInterpolated()));
				parserOutput.endTag(null, "Color");
				parserOutput.text("\n");
			}

			for (int i = 0; i < items.size(); i++) {
				ColorItem colorItem = items.get(i);
				parserOutput.startTag(null, "Alpha");
				parserOutput.attribute(null, "value",
						String.valueOf(colorItem.getValue()));
				parserOutput.attribute(null, "alpha",
						String.valueOf(colorItem.getColor().getAlpha()));
				parserOutput.attribute(null, "interpolated",
						String.valueOf(colorItem.getInterpolated()));
				parserOutput.endTag(null, "Alpha");
				parserOutput.text("\n");
			}

			parserOutput.endTag(null, "ColorTable");
			parserOutput.text("\n");
			parserOutput.endDocument();
			// Cerrar nuevo fichero
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Invocar� todos los metodos de actualizaciones de version
	 * 
	 * @param palettesBasePath
	 */
	public static void updateVersion(String palettesBasePath) {
		updateVersion_1_0_to_1_1(palettesBasePath);
		// updateVersion_1_1_to_1_2(palettesBasePath);
	}

	/**
	 * Devuelve el color si lo encuentra en el arraylist y lo elimina, en caso
	 * contrario devuelve null
	 * 
	 * @param list
	 * @param value
	 * @return
	 */
	public static ColorItem getColorItem(List<ColorItem> list, double value) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getValue() == value) {
				return list.remove(i);
			}
		}
		return null;
	}

	/**
	 * Lee una paleta del fichero xml de paletas y la carga en la tabla del
	 * panel.
	 * 
	 * @param palettesPath
	 *            Camino al fichero de paletas predefinidas.
	 * @param paletteName
	 *            Nombre de paleta a cargar desde el fichero xml.
	 * @return Nombre de la paleta
	 */
	public static String loadPalette(String palettesBasePath,
			String paletteFileName, List<ColorItem> items) {
		updateVersion(palettesBasePath);

		items.clear();

		File palettesFile = new File(palettesBasePath + File.separator
				+ paletteFileName);
		if (!palettesFile.exists())
			return null;

		try {
			String paletteName = "";
			List<ColorItem> rows = new ArrayList<ColorItem>();

			KXmlParser parser = new KXmlParser();
			FileInputStream in = new FileInputStream(palettesBasePath
					+ File.separator + paletteFileName);

			InputStreamReader reader = new InputStreamReader(in, "UTF8");
			parser.setInput(reader);
			int tag = parser.nextTag();

			parser.require(KXmlParser.START_TAG, null, "ColorTable");
			for (int i = 0; i < parser.getAttributeCount(); i++) {
				if (parser.getAttributeName(i).equals("name")) {
					paletteName = parser.getAttributeValue(i);
				}
			}
			tag = parser.nextTag();

			while (!((tag == KXmlParser.END_TAG) && (parser.getName()
					.equals("ColorTable")))) {
				try {
					if (tag == KXmlParser.START_TAG) {
						if (parser.getName().equals("Color")) {
							ColorItem colorItem = new ColorItem();
							int a = 255;
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								if (parser.getAttributeName(i).equals("value")) {
									colorItem.setValue(Double
											.parseDouble(parser
													.getAttributeValue(i)));
									ColorItem aux = getColorItem(rows,
											Double.parseDouble(parser
													.getAttributeValue(i)));
									if (aux != null)
										a = aux.getColor().getAlpha();
								}
								if (parser.getAttributeName(i).equals("name")) {
									colorItem.setNameClass(parser
											.getAttributeValue(i));
								}
								if (parser.getAttributeName(i).equals("rgb")) {
									String rgb = parser.getAttributeValue(i);
									int r = Integer.valueOf(
											rgb.substring(0, rgb.indexOf(",")))
											.intValue();
									int g = Integer.valueOf(
											rgb.substring(rgb.indexOf(",") + 1,
													rgb.lastIndexOf(",")))
											.intValue();
									int b = Integer.valueOf(
											rgb.substring(
													rgb.lastIndexOf(",") + 1,
													rgb.length())).intValue();

									colorItem.setColor(new Color(r, g, b, a));
								}
								if (parser.getAttributeName(i).equals(
										"interpolated")) {
									colorItem.setInterpolated(Double
											.parseDouble(parser
													.getAttributeValue(i)));
								}
							}

							rows.add(colorItem);
							continue;
						}
						if (parser.getName().equals("Alpha")) {
							ColorItem colorItem = new ColorItem();
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								if (parser.getAttributeName(i).equals("value")) {
									colorItem.setValue(Double
											.parseDouble(parser
													.getAttributeValue(i)));
									ColorItem aux = getColorItem(rows,
											Double.parseDouble(parser
													.getAttributeValue(i)));
									if (aux != null) {
										colorItem.setNameClass(aux
												.getNameClass());
										colorItem.setInterpolated(aux
												.getInterpolated());
										colorItem
												.setColor(new Color(aux
														.getColor().getRed(),
														aux.getColor()
																.getGreen(),
														aux.getColor()
																.getBlue(),
														colorItem.getColor()
																.getAlpha()));
									}
								}
								if (parser.getAttributeName(i).equals("alpha")) {
									int a = Integer.parseInt(parser
											.getAttributeValue(i));

									colorItem.setColor(new Color(colorItem
											.getColor().getRed(), colorItem
											.getColor().getGreen(), colorItem
											.getColor().getBlue(), a));
								}
								if (parser.getAttributeName(i).equals(
										"interpolated")) {
									colorItem.setInterpolated(Double
											.parseDouble(parser
													.getAttributeValue(i)));
								}
							}

							rows.add(colorItem);
							continue;
						}
					}
				} finally {
					tag = parser.nextTag();
				}
			}

			for (int i = 0; i < rows.size(); i++)
				items.add(rows.get(i));

			reader.close();
			return paletteName;
		} catch (FileNotFoundException fnfEx) {
			fnfEx.printStackTrace();
		} catch (XmlPullParserException xmlEx) {
			System.out
					.println("El fichero de paletas predeterminadas no tiene la estructura correcta:\n	"
							+ xmlEx.getMessage());
		} catch (IOException e) {
		}
		return null;
	}
}
