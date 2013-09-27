/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
 */
package org.gvsig.raster.datastruct;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Paleta para raster. Esta consta de los valores RGB de la paleta que son
 * almacenados en un vector donde cada elemento contiene en su interior el RGB
 * completo y del vector de rangos.
 * 
 * @version 04/07/2007
 * @author Nacho Brodin (nachobrodin@gmail.com)
 * @author BorSanZa - Borja S�nchez Zamorano (borja.sanchez@iver.es)
 */
public class ColorTable implements Cloneable {
	private static int DEFAULT_NUMBER_OF_COLORS = 256;

	/**
	 * Lista de ColorItem donde estaran todos los valores de la paleta segun el
	 * interfaz
	 */
	protected List<ColorItem> colorItems = null;

	/**
	 * Booleano que define si se interpolaran los valores de la paleta.
	 */
	protected boolean interpolated = true;

	/**
	 * Lista de rangos para paletas decimales
	 */

	protected double[] range = null;
	/**
	 * Lista de valores RGB
	 */
	protected byte[][] paletteByBand = null;

	/**
	 * Nombre de la clase asociada a la entrada
	 */
	protected String[] nameClass = null;

	/**
	 * Nombre de la paleta
	 */
	protected String name = null;

	/**
	 * Ruta del fichero a la cual se asocia la paleta. Las bandas de un
	 * GeoRasterMultiFile han de saber a que paleta van asociadas.
	 */
	protected String filePath = null;

	private int errorColor = 8;

	/**
	 * Constructor vac�o.
	 * 
	 * @param name
	 */
	public ColorTable() {
		this.name = "";
	}

	/**
	 * Constructor. Asigna el nombre de la paleta.
	 * 
	 * @param name
	 */
	public ColorTable(String name) {
		this.name = name;
	}

	/**
	 * Asigna el nombre de la paleta
	 * 
	 * @param Nombre
	 *            de la paleta
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el nombre de la paleta
	 * 
	 * @return Nombre de la paleta
	 */
	public String getName() {
		return name;
	}

	/**
	 * Crea una paleta a traves de una lista de colores y se le puede
	 * especificar si queremos que la comprima o no.
	 * 
	 * @param colorItems
	 * @param compress
	 */
	public void createPaletteFromColorItems(List<ColorItem> colorItems,
			boolean compress) {
		this.colorItems = colorItems;

		// Ordena la paleta
		sortPalette(colorItems);

		// Mira que valores se pueden descartar y asi dejamos la paleta reducida
		// para poder hacer interpolaciones
		if (compress)
			compressPalette(colorItems);

		// Genera la paleta final para poder ser usada
		applyPalette(colorItems);

		// Borra valores duplicados
		removeDuplicatedValues();
	}

	private boolean isEqualColor(Color c1, Color c2, int error) {
		if ((c2 == null) && (c1 != null))
			return false;
		if ((c1 == null) && (c2 != null))
			return false;
		if (c2.getRed() < (c1.getRed() - error))
			return false;
		if (c2.getGreen() < (c1.getGreen() - error))
			return false;
		if (c2.getBlue() < (c1.getBlue() - error))
			return false;
		if (c2.getAlpha() < (c1.getAlpha() - error))
			return false;

		if (c2.getRed() > (c1.getRed() + error))
			return false;
		if (c2.getGreen() > (c1.getGreen() + error))
			return false;
		if (c2.getBlue() > (c1.getBlue() + error))
			return false;
		if (c2.getAlpha() > (c1.getAlpha() + error))
			return false;

		return true;
	}

	/**
	 * Informa de si el color c3 se encuentra en un rango valido entre c1 y c2.
	 * Para colores con nombre de clase se devolver� false para no eliminar
	 * nunca ese item. Para colores con interpolacion distinta al 50% tambien
	 * devolveremos false.
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @return
	 */
	private boolean isCorrectColor(ColorItem c1, ColorItem c2, ColorItem c3) {
		if ((c3.getNameClass() != null) && (c3.getNameClass().length() > 0))
			return false;
		if (c3.getInterpolated() != 50)
			return false;
		if (c2.getInterpolated() != 50)
			return false;

		double max = c2.getValue() - c1.getValue();
		int r = c1.getColor().getRed()
				+ (int) (((c2.getColor().getRed() - c1.getColor().getRed()) * (c3
						.getValue() - c1.getValue())) / max);
		int g = c1.getColor().getGreen()
				+ (int) (((c2.getColor().getGreen() - c1.getColor().getGreen()) * (c3
						.getValue() - c1.getValue())) / max);
		int b = c1.getColor().getBlue()
				+ (int) (((c2.getColor().getBlue() - c1.getColor().getBlue()) * (c3
						.getValue() - c1.getValue())) / max);
		int a = c1.getColor().getAlpha()
				+ (int) (((c2.getColor().getAlpha() - c1.getColor().getAlpha()) * (c3
						.getValue() - c1.getValue())) / max);
		Color aux = new Color(r & 0xff, g & 0xff, b & 0xff, a & 0xff);

		return isEqualColor(c3.getColor(), aux, errorColor);
	}

	private boolean canDelete(int first, int last) {
		if (first >= getColorItems().size())
			return false;
		if (last >= getColorItems().size())
			return false;
		ColorItem c1 = getColorItems().get(first);
		ColorItem c2 = getColorItems().get(last);
		for (int i = (first + 1); i < last; i++) {
			if (!isCorrectColor(c1, c2, getColorItems().get(i)))
				return false;
		}
		return true;
	}

	/**
	 * Borra valores duplicados de la paleta. Solo aquellos que coincidan en
	 * valor y color.
	 */
	public void removeDuplicatedValues() {
		for (int i = colorItems.size() - 2; i >= 0; i--) {
			if ((i + 1) >= colorItems.size())
				continue;
			ColorItem colorItem = colorItems.get(i + 1);
			ColorItem colorItem2 = colorItems.get(i);
			// Si el valor es distinto no lo borramos
			if (colorItem.getValue() != colorItem2.getValue())
				continue;
			// Si el color es distinto no lo borramos
			if (!colorItem.getColor().equals(colorItem2.getColor()))
				continue;
			// Borraremos siempre el valor que no tenga nombre de clase, es un
			// dato importante
			if ((colorItem.getNameClass() == null)
					|| (colorItem.getNameClass().length() == 0)) {
				colorItems.remove(i + 1);
				continue;
			}
			if ((colorItem2.getNameClass() == null)
					|| (colorItem2.getNameClass().length() == 0)) {
				colorItems.remove(i);
				continue;
			}
			// Borramos solo uno de los dos si el nombre coincide
			if ((colorItem.getNameClass() != null)
					|| (colorItem.getNameClass().equals(colorItem2
							.getNameClass()))) {
				colorItems.remove(i);
				continue;
			}
		}
	}

	public boolean hasAlpha() {
		for (int i = 0; i < colorItems.size(); i++) {
			ColorItem colorItem = colorItems.get(i);
			if (colorItem.getColor().getAlpha() != 255)
				return true;
		}
		return false;
	}

	/**
	 * Comprime la actual tabla de color
	 */
	public void compressPalette() {
		compressPalette(colorItems);
	}

	/**
	 * Informa de si una tabla de color se puede comprimir
	 * 
	 * @return
	 */
	public boolean isCompressible() {
		List<ColorItem> cloneList = new ArrayList<ColorItem>();
		Collections.copy(getColorItems(), cloneList);
		compressPalette(cloneList);
		return (cloneList.size() != getColorItems().size());
	}

	/**
	 * Coge el array pasado por parametro y lo intenta comprimir
	 * 
	 * @param colorItems
	 */
	private void compressPalette(List<ColorItem> colorItems) {
		removeDuplicatedValues();
		int size = -1;

		while (size != colorItems.size()) {
			int init = 0;
			int posMax = 2;

			size = colorItems.size();
			while (init < colorItems.size()) {
				if ((posMax < colorItems.size()) && canDelete(init, posMax)) {
					posMax++;
					continue;
				}
				if ((init + 2) < posMax) {
					if (canDelete(init, posMax - 1))
						for (int i = (posMax - 2); i > init; i--)
							if (i < colorItems.size())
								colorItems.remove(i);
				}
				init++;
				posMax = init + 2;
			}
		}
	}

	/**
	 * Crea una paleta a partir de un objeto GdalColorTable. Esto es necesario
	 * para los ficheros que tienen una paleta asignada, como los gif, y que son
	 * tratados por Gdal. Se pasa la tabla de color le�da desde gdal y se crea
	 * directamente un objeto Palette.
	 * 
	 * @param table
	 */
	public void createPaletteFromGdalColorTable(List<Color> colors) {
		colorItems = new ArrayList<ColorItem>();
		for (int i = 0; i < colors.size(); i++) {
			ColorItem colorItem = new ColorItem();
			colorItem.setNameClass("");
			colorItem.setValue(i);
			colorItem.setColor(colors.get(i));
			colorItems.add(colorItem);
		}

		sortPalette(colorItems);

		setInterpolated(false);
		// compressPalette();
		applyPalette(colorItems);
	}

	/**
	 * Obtiene la tabla de color por banda
	 * 
	 * @return Paleta
	 */
	public byte[][] getColorTableByBand() {
		return paletteByBand;
	}

	/**
	 * Asigna una paleta
	 * 
	 * @param palette
	 *            Paleta
	 */
	public void setColorTable(int[] palette) {
		paletteByBand = new byte[palette.length][3];
		for (int i = 0; i < palette.length; i++) {
			paletteByBand[i][0] = (byte) ((palette[i] & 0x00ff0000) >> 16);
			paletteByBand[i][1] = (byte) ((palette[i] & 0x0000ff00) >> 8);
			paletteByBand[i][2] = (byte) (palette[i] & 0x000000ff);
		}
	}

	/**
	 * Obtiene los nombres de las clases de la paleta
	 * 
	 * @return Array de cadenas. Cada una corresponde con un nombre de clase que
	 *         corresponde a cada rango de tipos.
	 */
	public String[] getNameClass() {
		return nameClass;
	}

	/**
	 * Asigna los nombres de las clases de la paleta
	 * 
	 * @param names
	 *            Array de cadenas. Cada una corresponde con un nombre de clase
	 *            que corresponde a cada rango de tipos.
	 */
	public void setNameClass(String[] names) {
		nameClass = names;
	}

	/**
	 * Obtiene la ruta del fichero al que va asociada la paleta.
	 * 
	 * @return Ruta del fichero al que va asociada la paleta.
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Asigna la ruta del fichero al que va asociada la paleta.
	 * 
	 * @param Ruta
	 *            del fichero al que va asociada la paleta.
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		ColorTable clone = null;
		try {
			clone = (ColorTable) super.clone();
		} catch (CloneNotSupportedException e) {
		}

		if (colorItems != null) {
			clone.colorItems = new ArrayList<ColorItem>();
			for (int i = 0; i < colorItems.size(); i++) {
				clone.colorItems.add((ColorItem) colorItems.get(i).clone());
			}
		}

		clone.interpolated = interpolated;

		if (filePath != null)
			clone.filePath = new String(filePath);

		if (name != null)
			clone.name = new String(name);

		if (nameClass != null) {
			clone.nameClass = new String[nameClass.length];
			for (int i = 0; i < nameClass.length; i++)
				clone.nameClass[i] = nameClass[i];
		}

		if (paletteByBand != null) {
			clone.paletteByBand = paletteByBand.clone();
			for (int i = 0; i < paletteByBand.length; i++)
				clone.paletteByBand[i] = paletteByBand[i].clone();
		}

		if (range != null)
			clone.range = range.clone();

		return clone;
	}

	/**
	 * Devuelve un color de interpolacion entre dos colores
	 * 
	 * @param value
	 * @param pos
	 * @return
	 */
	private Color interpolatedColor(double value, int pos) {
		if (colorItems.size() <= 0)
			return Color.black;

		if ((pos + 1) == colorItems.size())
			return colorItems.get(pos).getColor();

		if (value <= colorItems.get(0).getValue())
			return colorItems.get(0).getColor();

		ColorItem item1 = colorItems.get(pos);
		ColorItem item2 = colorItems.get(pos + 1);

		double percValue = ((value - item1.getValue()) * 100)
				/ (item2.getValue() - item1.getValue());

		Color halfColor = new Color(
				(item2.getColor().getRed() + item1.getColor().getRed()) >> 1,
				(item2.getColor().getGreen() + item1.getColor().getGreen()) >> 1,
				(item2.getColor().getBlue() + item1.getColor().getBlue()) >> 1,
				(item2.getColor().getAlpha() + item1.getColor().getAlpha()) >> 1);

		Color color1, color2;
		double perc1, perc2;

		if (percValue > item2.getInterpolated()) {
			color1 = halfColor;
			color2 = item2.getColor();
			perc1 = item2.getInterpolated();
			perc2 = 100;
		} else {
			color1 = item1.getColor();
			color2 = halfColor;
			perc1 = 0;
			perc2 = item2.getInterpolated();
		}

		double percNew = (percValue - perc1) / (perc2 - perc1);

		Color newColor = new Color(
				(int) (color1.getRed() + ((color2.getRed() - color1.getRed()) * percNew)) & 0xff,
				(int) (color1.getGreen() + ((color2.getGreen() - color1
						.getGreen()) * percNew)) & 0xff,
				(int) (color1.getBlue() + ((color2.getBlue() - color1.getBlue()) * percNew)) & 0xff,
				(int) (color1.getAlpha() + ((color2.getAlpha() - color1
						.getAlpha()) * percNew)) & 0xff);

		return newColor;
	}

	/*
	 * TODO: RENDIMIENTO: Incluir una heuristica que dado un valor se compare
	 * con el valor de la mitad de la tabla y si es menor se empieza a recorrer
	 * desde el principio sino se empieza a recorrer desde la mitad de la tabla
	 * hasta abajo. Esto hace que se reduzca la tabla a la mitad de valores
	 * haciendo solo una comparaci�n.
	 */
	/**
	 * Obtiene el valor RGB para un clave entera pasada por par�metro
	 * 
	 * @param value
	 *            clave de la cual se quiere obtener el valor RGB de la paleta
	 * @return valor RGB
	 */
	public byte[] getRGBAByBand(double value) {
		for (int i = 1; i <= range.length; i++) {
			if (i < range.length) {
				if (value < range[i])
					return paletteByBand[i - 1];
			} else {
				return paletteByBand[i - 1];
			}
		}
		return new byte[4];
	}

	/*
	 * TODO: Usar el m�todo Quicksort para ordenar
	 */
	/**
	 * Ordena el ColorItems de manera ascendente. De momento se usa el m�todo de
	 * ordenaci�n por burbuja.
	 */
	private void sortPalette(List<ColorItem> colorItems) {
		for (int i = 0; i < colorItems.size(); i++) {
			for (int j = i + 1; j < colorItems.size(); j++) {
				if (colorItems.get(j).getValue() < colorItems.get(i).getValue()) {
					colorItems.set(i, colorItems.get(j));
					colorItems.set(j, colorItems.get(i));
				}
			}
		}
	}

	/**
	 * Genera una paleta intermedia para acelerar los calculos.
	 */
	private void applyPalette(List<ColorItem> colorItems) {
		List<ColorItem> arrayColors = new ArrayList<ColorItem>();

		paletteByBand = new byte[0][3];
		range = new double[0];
		nameClass = new String[0];

		if (colorItems.size() == 0)
			return;

		// Nos preparamos para hacer las particiones, sabiendo el minimo y
		// maximo
		double min = colorItems.get(0).getValue();
		double max = colorItems.get(colorItems.size() - 1).getValue();

		if (min > max) {
			double aux = max;
			max = min;
			min = aux;
		}

		Color color = Color.white;
		Color colorOld = null;

		// Hacemos las particiones, metiendo cada item calculado en un array
		int defaultColors = DEFAULT_NUMBER_OF_COLORS;
		for (int i = 0; i < defaultColors; i++) {
			double value = min + ((i * (max - min)) / (defaultColors - 1));
			int pos = 0;
			for (int j = 1; j <= colorItems.size(); j++) {
				if (j < colorItems.size()) {
					if (value < colorItems.get(j).getValue()) {
						pos = j - 1;
						break;
					}
				} else {
					pos = j - 1;
					break;
				}
			}

			// Calculamos el color que corresponde, tanto interpolado como no
			if (interpolated) {
				color = interpolatedColor(value, pos);
			} else {
				if ((pos + 1) < colorItems.size()) {
					double min2 = colorItems.get(pos).getValue();
					double max2 = colorItems.get(pos + 1).getValue();
					if ((min2 + ((max2 - min2)
							* colorItems.get(pos + 1).getInterpolated() / 100)) < value)
						pos++;
				}
				color = colorItems.get(pos).getColor();
			}

			if (!isEqualColor(color, colorOld, 0)) {
				ColorItem colorItem = new ColorItem();
				colorItem.setValue(value);
				colorItem.setColor(color);
				arrayColors.add(colorItem);
			}

			colorOld = color;
		}

		// Una vez tenemos una paleta de 256 colores o inferior, rellenamos
		// los siguientes valores para hacer busquedas rapidas.

		paletteByBand = new byte[arrayColors.size()][4];
		range = new double[arrayColors.size()];
		nameClass = new String[arrayColors.size()];

		for (int i = 0; i < arrayColors.size(); i++) {
			paletteByBand[i][0] = (byte) arrayColors.get(i).getColor().getRed();
			paletteByBand[i][1] = (byte) arrayColors.get(i).getColor()
					.getGreen();
			paletteByBand[i][2] = (byte) arrayColors.get(i).getColor()
					.getBlue();
			paletteByBand[i][3] = (byte) arrayColors.get(i).getColor()
					.getAlpha();
			range[i] = arrayColors.get(i).getValue();
			nameClass[i] = arrayColors.get(i).getNameClass();
		}
	}

	public double[] getRange() {
		return range;
	}

	public void setRange(double[] range) {
		this.range = range;
	}

	/**
	 * Devuelve un ArrayList con cada ColorItem de la tabla de color
	 * 
	 * @return
	 */
	public List<ColorItem> getColorItems() {
		return colorItems;
	}

	/**
	 * Nos indica si la paleta la ha generado con valores interpolados o no.
	 * 
	 * @return
	 */
	public boolean isInterpolated() {
		return interpolated;
	}

	/**
	 * Definir si la paleta tendra los valores interpolados o no
	 * 
	 * @param interpolated
	 */
	public void setInterpolated(boolean interpolated) {
		this.interpolated = interpolated;
		applyPalette(colorItems);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result
				+ ((colorItems == null) ? 0 : colorItems.hashCode());
		result = PRIME * result + errorColor;
		result = PRIME * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		result = PRIME * result + (interpolated ? 1231 : 1237);
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ColorTable.hashCode(nameClass);
		result = PRIME * result + ColorTable.hashCode(paletteByBand);
		result = PRIME * result + ColorTable.hashCode(range);
		return result;
	}

	private static int hashCode(Object[] array) {
		final int PRIME = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (int index = 0; index < array.length; index++) {
			result = PRIME * result
					+ (array[index] == null ? 0 : array[index].hashCode());
		}
		return result;
	}

	private static int hashCode(double[] array) {
		final int PRIME = 31;
		if (array == null)
			return 0;
		int result = 1;
		for (int index = 0; index < array.length; index++) {
			long temp = Double.doubleToLongBits(array[index]);
			result = PRIME * result + (int) (temp ^ (temp >>> 32));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ColorTable other = (ColorTable) obj;

		if (((colorItems == null) && (other.colorItems != null))
				|| ((colorItems != null) && (other.colorItems == null)))
			return false;

		if (colorItems != null) {
			if (colorItems.size() != other.colorItems.size())
				return false;
			for (int i = 0; i < colorItems.size(); i++) {
				if (!colorItems.get(i).equals(other.colorItems.get(i)))
					return false;
			}
		}

		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (nameClass != null) {
			if (other.nameClass == null)
				return false;

			if (nameClass.length != other.nameClass.length)
				return false;

			for (int i = 0; i < nameClass.length; i++) {
				if (nameClass[i] != null) {
					if (!nameClass[i].equals(other.nameClass[i]))
						return false;
				} else {
					if (other.nameClass[i] != null)
						return false;
				}
			}
		}

		if (((paletteByBand == null) && (other.paletteByBand != null))
				|| ((paletteByBand != null) && (other.paletteByBand == null)))
			return false;

		if (paletteByBand != null) {
			for (int i = 0; i < paletteByBand.length; i++) {
				for (int j = 0; j < paletteByBand[i].length; j++) {
					if (paletteByBand[i][j] != other.paletteByBand[i][j])
						return false;
				}
			}
		}

		if (!Arrays.equals(range, other.range))
			return false;

		if (((colorItems == null) && (other.colorItems != null))
				|| ((colorItems != null) && (other.colorItems == null)))
			return false;

		return true;
	}

	/**
	 * Establece la tabla de color actual en los rangos de limite especificados
	 * por parametros, distribuyendolo proporcionalmente.
	 * 
	 * @param min
	 * @param max
	 * @param compress
	 */
	public void createColorTableInRange(double min, double max, boolean compress) {
		ColorItem colorItem;
		double max2 = Double.NEGATIVE_INFINITY;
		double min2 = Double.POSITIVE_INFINITY;

		if (min > max) {
			double aux = min;
			min = max;
			max = aux;
		}

		List<ColorItem> arrayList = new ArrayList<ColorItem>();
		List<ColorItem> items = getColorItems();

		// Actualizamos el maximo y minimo del array
		for (int i = 0; i < items.size(); i++) {
			colorItem = items.get(i);
			if (colorItem.getValue() > max2)
				max2 = colorItem.getValue();
			if (colorItem.getValue() < min2)
				min2 = colorItem.getValue();
		}

		// A�adir el minimo
		if (items.size() > 0)
			colorItem = items.get(0);
		else {
			colorItem = new ColorItem();
			colorItem.setValue(0);
			colorItem.setColor(Color.black);
		}

		arrayList.add(colorItem);

		for (int i = 0; i < items.size(); i++) {
			colorItem = items.get(i);
			colorItem
					.setValue(min
							+ (((colorItem.getValue() - min2) * (max - min)) / (max2 - min2)));
			arrayList.add(colorItem);
		}

		// A�adir el maximo
		if (items.size() > 0)
			colorItem = items.get(items.size() - 1);
		else {
			colorItem = new ColorItem();
			colorItem.setValue(255);
			colorItem.setColor(Color.white);
		}
		arrayList.add(colorItem);

		createPaletteFromColorItems(arrayList, compress);
	}
}