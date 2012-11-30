/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
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
package com.iver.cit.gvsig.project;

import java.awt.geom.Rectangle2D;

import org.gvsig.persistence.generated.LabeledExtentType;

/**
 * DOCUMENT ME!
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class ProjectExtent {
	private Rectangle2D extent = new Rectangle2D.Double();
	private String description;

	/**
	 * DOCUMENT ME!
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return
	 */
	public Rectangle2D getExtent() {
		return extent;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param rectangle2D
	 */
	public void setExtent(Rectangle2D rectangle2D) {
		extent = rectangle2D;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getEncuadre() {
		return extent.getMinX() + "," + extent.getMinY() + ","
				+ extent.getWidth() + "," + extent.getHeight();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param encuadre
	 *            DOCUMENT ME!
	 */
	public void setEncuadre(String encuadre) {
		String[] coords = encuadre.split(",");
		extent = new Rectangle2D.Double(new Double(coords[0]).doubleValue(),
				new Double(coords[1]).doubleValue(),
				new Double(coords[2]).doubleValue(),
				new Double(coords[3]).doubleValue());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return description;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public LabeledExtentType getXMLEntity() {
		LabeledExtentType xml = new LabeledExtentType();
		xml.setComment(description);
		xml.setMinx(extent.getMinX());
		xml.setMiny(extent.getMinY());
		xml.setMaxx(extent.getMaxX());
		xml.setMaxy(extent.getMaxY());

		return xml;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param xml
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static ProjectExtent createFromXML(LabeledExtentType xml) {
		ProjectExtent pe = new ProjectExtent();
		pe.description = xml.getComment();
		pe.extent.setRect(xml.getMinx(), xml.getMiny(),
				xml.getMaxx() - xml.getMinx(), xml.getMaxy() - xml.getMiny());

		return pe;
	}

	// public int computeSignature() {
	// int result = 17;
	//
	// Class clazz = getClass();
	// Field[] fields = clazz.getDeclaredFields();
	// for (int i = 0; i < fields.length; i++) {
	// try {
	// String type = fields[i].getType().getName();
	// if (type.equals("boolean")) {
	// result += 37 + ((fields[i].getBoolean(this)) ? 1 : 0);
	// } else if (type.equals("java.lang.String")) {
	// Object v = fields[i].get(this);
	// if (v == null) {
	// result += 37;
	// continue;
	// }
	// char[] chars = ((String) v).toCharArray();
	// for (int j = 0; j < chars.length; j++) {
	// result += 37 + (int) chars[i];
	// }
	// } else if (type.equals("byte")) {
	// result += 37 + (int) fields[i].getByte(this);
	// } else if (type.equals("char")) {
	// result += 37 + (int) fields[i].getChar(this);
	// } else if (type.equals("short")) {
	// result += 37 + (int) fields[i].getShort(this);
	// } else if (type.equals("int")) {
	// result += 37 + fields[i].getInt(this);
	// } else if (type.equals("long")) {
	// long f = fields[i].getLong(this) ;
	// result += 37 + (f ^ (f >>> 32));
	// } else if (type.equals("float")) {
	// result += 37 + Float.floatToIntBits(fields[i].getFloat(this));
	// } else if (type.equals("double")) {
	// long f = Double.doubleToLongBits(fields[i].getDouble(this));
	// result += 37 + (f ^ (f >>> 32));
	// } else {
	// Object obj = fields[i].get(this);
	// result += 37 + ((obj != null)? obj.hashCode() : 0);
	// }
	// } catch (Exception e) { e.printStackTrace(); }
	//
	// }
	// return result;
	// }
}
