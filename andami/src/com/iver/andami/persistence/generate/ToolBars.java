/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ToolBars.java 5275 2006-05-19 06:41:50Z jaume $
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
 * Class ToolBars.
 * 
 * @version $Revision: 5275 $ $Date: 2006-05-19 08:41:50 +0200 (Fri, 19 May
 *          2006) $
 */
public class ToolBars implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _toolBarList
	 */
	private java.util.Vector _toolBarList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ToolBars() {
		super();
		_toolBarList = new Vector();
	} // -- com.iver.andami.persistence.generate.ToolBars()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addToolBar
	 * 
	 * @param vToolBar
	 */
	public void addToolBar(com.iver.andami.persistence.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		_toolBarList.addElement(vToolBar);
	} // -- void addToolBar(com.iver.andami.persistence.generate.ToolBar)

	/**
	 * Method addToolBar
	 * 
	 * @param index
	 * @param vToolBar
	 */
	public void addToolBar(int index,
			com.iver.andami.persistence.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		_toolBarList.insertElementAt(vToolBar, index);
	} // -- void addToolBar(int, com.iver.andami.persistence.generate.ToolBar)

	/**
	 * Method enumerateToolBar
	 */
	public java.util.Enumeration enumerateToolBar() {
		return _toolBarList.elements();
	} // -- java.util.Enumeration enumerateToolBar()

	/**
	 * Method getToolBar
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.ToolBar getToolBar(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _toolBarList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.persistence.generate.ToolBar) _toolBarList
				.elementAt(index);
	} // -- com.iver.andami.persistence.generate.ToolBar getToolBar(int)

	/**
	 * Method getToolBar
	 */
	public com.iver.andami.persistence.generate.ToolBar[] getToolBar() {
		int size = _toolBarList.size();
		com.iver.andami.persistence.generate.ToolBar[] mArray = new com.iver.andami.persistence.generate.ToolBar[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.persistence.generate.ToolBar) _toolBarList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.persistence.generate.ToolBar[] getToolBar()

	/**
	 * Method getToolBarCount
	 */
	public int getToolBarCount() {
		return _toolBarList.size();
	} // -- int getToolBarCount()

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
	 * Method removeAllToolBar
	 */
	public void removeAllToolBar() {
		_toolBarList.removeAllElements();
	} // -- void removeAllToolBar()

	/**
	 * Method removeToolBar
	 * 
	 * @param index
	 */
	public com.iver.andami.persistence.generate.ToolBar removeToolBar(int index) {
		java.lang.Object obj = _toolBarList.elementAt(index);
		_toolBarList.removeElementAt(index);
		return (com.iver.andami.persistence.generate.ToolBar) obj;
	} // -- com.iver.andami.persistence.generate.ToolBar removeToolBar(int)

	/**
	 * Method setToolBar
	 * 
	 * @param index
	 * @param vToolBar
	 */
	public void setToolBar(int index,
			com.iver.andami.persistence.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _toolBarList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_toolBarList.setElementAt(vToolBar, index);
	} // -- void setToolBar(int, com.iver.andami.persistence.generate.ToolBar)

	/**
	 * Method setToolBar
	 * 
	 * @param toolBarArray
	 */
	public void setToolBar(
			com.iver.andami.persistence.generate.ToolBar[] toolBarArray) {
		// -- copy array
		_toolBarList.removeAllElements();
		for (int i = 0; i < toolBarArray.length; i++) {
			_toolBarList.addElement(toolBarArray[i]);
		}
	} // -- void setToolBar(com.iver.andami.persistence.generate.ToolBar)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.persistence.generate.ToolBars) Unmarshaller
				.unmarshal(com.iver.andami.persistence.generate.ToolBars.class,
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
