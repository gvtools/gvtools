/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Windows.java 5275 2006-05-19 06:41:50Z jaume $
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
 * Class Windows.
 * 
 * @version $Revision: 5275 $ $Date: 2006-05-19 08:41:50 +0200 (Fri, 19 May
 *          2006) $
 */
public class Windows implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _windowList
	 */
	private java.util.Vector _windowList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Windows() {
		super();
		_windowList = new Vector();
	} // -- com.iver.andami.persistence.generate.Windows()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addWindow
	 * 
	 * @param vWindow
	 */
	public void addWindow(com.iver.andami.persistence.generate.Window vWindow)
			throws java.lang.IndexOutOfBoundsException {
		_windowList.addElement(vWindow);
	} // -- void addWindow(com.iver.andami.persistence.generate.Window)

	/**
	 * Method addWindow
	 * 
	 * @param index
	 * @param vWindow
	 */
	public void addWindow(int index,
			com.iver.andami.persistence.generate.Window vWindow)
			throws java.lang.IndexOutOfBoundsException {
		_windowList.insertElementAt(vWindow, index);
	} // -- void addWindow(int, com.iver.andami.persistence.generate.Window)

	/**
	 * Method enumerateWindow
	 */
	public java.util.Enumeration enumerateWindow() {
		return _windowList.elements();
	} // -- java.util.Enumeration enumerateWindow()

	/**
	 * Method getWindow
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.Window getWindow(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _windowList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.persistence.generate.Window) _windowList
				.elementAt(index);
	} // -- com.iver.andami.persistence.generate.Window getWindow(int)

	/**
	 * Method getWindow
	 */
	public com.iver.andami.persistence.generate.Window[] getWindow() {
		int size = _windowList.size();
		com.iver.andami.persistence.generate.Window[] mArray = new com.iver.andami.persistence.generate.Window[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.persistence.generate.Window) _windowList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.persistence.generate.Window[] getWindow()

	/**
	 * Method getWindowCount
	 */
	public int getWindowCount() {
		return _windowList.size();
	} // -- int getWindowCount()

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
	 * Method removeAllWindow
	 */
	public void removeAllWindow() {
		_windowList.removeAllElements();
	} // -- void removeAllWindow()

	/**
	 * Method removeWindow
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.Window removeWindow(int index) {
		java.lang.Object obj = _windowList.elementAt(index);
		_windowList.removeElementAt(index);
		return (com.iver.andami.persistence.generate.Window) obj;
	} // -- com.iver.andami.persistence.generate.Window removeWindow(int)

	/**
	 * Method setWindow
	 * 
	 * @param index
	 * @param vWindow
	 */
	public void setWindow(int index,
			com.iver.andami.persistence.generate.Window vWindow)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _windowList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_windowList.setElementAt(vWindow, index);
	} // -- void setWindow(int, com.iver.andami.persistence.generate.Window)

	/**
	 * Method setWindow
	 * 
	 * @param windowArray
	 */
	public void setWindow(
			com.iver.andami.persistence.generate.Window[] windowArray) {
		// -- copy array
		_windowList.removeAllElements();
		for (int i = 0; i < windowArray.length; i++) {
			_windowList.addElement(windowArray[i]);
		}
	} // -- void setWindow(com.iver.andami.persistence.generate.Window)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.persistence.generate.Windows) Unmarshaller
				.unmarshal(com.iver.andami.persistence.generate.Windows.class,
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
