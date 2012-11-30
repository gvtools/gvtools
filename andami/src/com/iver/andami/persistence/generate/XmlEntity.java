/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: XmlEntity.java 5275 2006-05-19 06:41:50Z jaume $
 */

/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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
package com.iver.andami.persistence.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class XmlEntity.
 * 
 * @version $Revision: 5275 $ $Date: 2006-05-19 08:41:50 +0200 (Fri, 19 May
 *          2006) $
 */
public class XmlEntity implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _propertyList
	 */
	private java.util.Vector _propertyList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public XmlEntity() {
		super();
		_propertyList = new Vector();
	} // -- com.iver.andami.persistence.generate.XmlEntity()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addProperty
	 * 
	 * @param vProperty
	 */
	public void addProperty(
			com.iver.andami.persistence.generate.Property vProperty)
			throws java.lang.IndexOutOfBoundsException {
		_propertyList.addElement(vProperty);
	} // -- void addProperty(com.iver.andami.persistence.generate.Property)

	/**
	 * Method addProperty
	 * 
	 * @param index
	 * @param vProperty
	 */
	public void addProperty(int index,
			com.iver.andami.persistence.generate.Property vProperty)
			throws java.lang.IndexOutOfBoundsException {
		_propertyList.insertElementAt(vProperty, index);
	} // -- void addProperty(int, com.iver.andami.persistence.generate.Property)

	/**
	 * Method enumerateProperty
	 */
	public java.util.Enumeration enumerateProperty() {
		return _propertyList.elements();
	} // -- java.util.Enumeration enumerateProperty()

	/**
	 * Method getProperty
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.Property getProperty(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _propertyList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.persistence.generate.Property) _propertyList
				.elementAt(index);
	} // -- com.iver.andami.persistence.generate.Property getProperty(int)

	/**
	 * Method getProperty
	 */
	public com.iver.andami.persistence.generate.Property[] getProperty() {
		int size = _propertyList.size();
		com.iver.andami.persistence.generate.Property[] mArray = new com.iver.andami.persistence.generate.Property[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.persistence.generate.Property) _propertyList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.persistence.generate.Property[] getProperty()

	/**
	 * Method getPropertyCount
	 */
	public int getPropertyCount() {
		return _propertyList.size();
	} // -- int getPropertyCount()

	/**
	 * Method isValid
	 */
	public boolean isValid() {
		try {
			validate();
		} catch (org.exolab.castor.xml.ValidationException vex) {
			return false;
		}
		return true;
	} // -- boolean isValid()

	/**
	 * Method marshal
	 * 
	 * @param out
	 */
	public void marshal(java.io.Writer out)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {

		Marshaller.marshal(this, out);
	} // -- void marshal(java.io.Writer)

	/**
	 * Method marshal
	 * 
	 * @param handler
	 */
	public void marshal(org.xml.sax.ContentHandler handler)
			throws java.io.IOException, org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {

		Marshaller.marshal(this, handler);
	} // -- void marshal(org.xml.sax.ContentHandler)

	/**
	 * Method removeAllProperty
	 */
	public void removeAllProperty() {
		_propertyList.removeAllElements();
	} // -- void removeAllProperty()

	/**
	 * Method removeProperty
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.Property removeProperty(
			int index) {
		java.lang.Object obj = _propertyList.elementAt(index);
		_propertyList.removeElementAt(index);
		return (com.iver.andami.persistence.generate.Property) obj;
	} // -- com.iver.andami.persistence.generate.Property removeProperty(int)

	/**
	 * Method setProperty
	 * 
	 * @param index
	 * @param vProperty
	 */
	public void setProperty(int index,
			com.iver.andami.persistence.generate.Property vProperty)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _propertyList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_propertyList.setElementAt(vProperty, index);
	} // -- void setProperty(int, com.iver.andami.persistence.generate.Property)

	/**
	 * Method setProperty
	 * 
	 * @param propertyArray
	 */
	public void setProperty(
			com.iver.andami.persistence.generate.Property[] propertyArray) {
		// -- copy array
		_propertyList.removeAllElements();
		for (int i = 0; i < propertyArray.length; i++) {
			_propertyList.addElement(propertyArray[i]);
		}
	} // -- void setProperty(com.iver.andami.persistence.generate.Property)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.persistence.generate.XmlEntity) Unmarshaller
				.unmarshal(
						com.iver.andami.persistence.generate.XmlEntity.class,
						reader);
	} // -- java.lang.Object unmarshal(java.io.Reader)

	/**
	 * Method validate
	 */
	public void validate() throws org.exolab.castor.xml.ValidationException {
		org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
		validator.validate(this);
	} // -- void validate()

}
