/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
package org.gvsig.raster.datastruct;

import java.awt.Color;

/**
 * Valor minimo para un item de una tabla de color. Este tendrá que pixel
 * afecta, nombre de esa clase, color y como estará interpolado con el
 * siguiente.
 * 
 * @version 04/07/2007
 * @author BorSanZa - Borja Sánchez Zamorano (borja.sanchez@iver.es)
 */
public class ColorItem implements Cloneable {
	private double value = 0.0f;
	private String nameClass = null;
	private Color color = Color.black;
	private double interpolated = 50;

	/**
	 * Devuelve el color
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Definir el color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Devuelve el valor de interpolación con el siguiente color. Límites:
	 * 0..100
	 * 
	 * @return
	 */
	public double getInterpolated() {
		return interpolated;
	}

	/**
	 * Definir el valor de interpolación. Si es mayor a 100 o menor a 0 se pone
	 * entre los valores correctos.
	 * 
	 * @param interpolated
	 */
	public void setInterpolated(double interpolated) {
		if (interpolated > 100)
			this.interpolated = 100;
		else if (interpolated < 0)
			this.interpolated = 0;
		else
			this.interpolated = interpolated;
	}

	/**
	 * Obtener en que valor estará dicho color
	 * 
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Definir el valor del ColorItem.
	 * 
	 * @param value
	 */
	public void setValue(double value) {
		if (Double.isNaN(value))
			return;
		this.value = value;
	}

	/**
	 * Devuelve el nombre de la clase
	 * 
	 * @return
	 */
	public String getNameClass() {
		return nameClass;
	}

	/**
	 * Define el nombre de la clase
	 * 
	 * @param nameClass
	 */
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		ColorItem clone = null;
		try {
			clone = (ColorItem) super.clone();
		} catch (CloneNotSupportedException e) {
		}

		if (color != null)
			clone.color = new Color(color.getRGB(), (color.getAlpha() != 255));

		if (nameClass != null)
			clone.nameClass = new String(nameClass);

		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((color == null) ? 0 : color.hashCode());
		result = PRIME * result + (int) interpolated;
		result = PRIME * result
				+ ((nameClass == null) ? 0 : nameClass.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
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
		final ColorItem other = (ColorItem) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (interpolated != other.interpolated)
			return false;
		if (nameClass == null) {
			if (other.nameClass != null)
				return false;
		} else if (!nameClass.equals(other.nameClass))
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}
}