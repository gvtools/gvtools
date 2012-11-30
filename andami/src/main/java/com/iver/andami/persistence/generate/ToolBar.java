/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ToolBar.java 5275 2006-05-19 06:41:50Z jaume $
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

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ToolBar.
 * 
 * @version $Revision: 5275 $ $Date: 2006-05-19 08:41:50 +0200 (Fri, 19 May
 *          2006) $
 */
public class ToolBar implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _xPosition
	 */
	private java.lang.String _xPosition;

	/**
	 * Field _yPosition
	 */
	private java.lang.String _yPosition;

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ToolBar() {
		super();
	} // -- com.iver.andami.persistence.generate.ToolBar()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'name'.
	 * 
	 * @return the value of field 'name'.
	 */
	public java.lang.String getName() {
		return this._name;
	} // -- java.lang.String getName()

	/**
	 * Returns the value of field 'xPosition'.
	 * 
	 * @return the value of field 'xPosition'.
	 */
	public java.lang.String getXPosition() {
		return this._xPosition;
	} // -- java.lang.String getXPosition()

	/**
	 * Returns the value of field 'yPosition'.
	 * 
	 * @return the value of field 'yPosition'.
	 */
	public java.lang.String getYPosition() {
		return this._yPosition;
	} // -- java.lang.String getYPosition()

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
	 * Sets the value of field 'name'.
	 * 
	 * @param name
	 *            the value of field 'name'.
	 */
	public void setName(java.lang.String name) {
		this._name = name;
	} // -- void setName(java.lang.String)

	/**
	 * Sets the value of field 'xPosition'.
	 * 
	 * @param xPosition
	 *            the value of field 'xPosition'.
	 */
	public void setXPosition(java.lang.String xPosition) {
		this._xPosition = xPosition;
	} // -- void setXPosition(java.lang.String)

	/**
	 * Sets the value of field 'yPosition'.
	 * 
	 * @param yPosition
	 *            the value of field 'yPosition'.
	 */
	public void setYPosition(java.lang.String yPosition) {
		this._yPosition = yPosition;
	} // -- void setYPosition(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.persistence.generate.ToolBar) Unmarshaller
				.unmarshal(com.iver.andami.persistence.generate.ToolBar.class,
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
