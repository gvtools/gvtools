/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Plugin.java 5275 2006-05-19 06:41:50Z jaume $
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
 * Class Plugin.
 * 
 * @version $Revision: 5275 $ $Date: 2006-05-19 08:41:50 +0200 (Fri, 19 May
 *          2006) $
 */
public class Plugin implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	/**
	 * Field _lastUpdate
	 */
	private java.lang.String _lastUpdate;

	/**
	 * Field _xmlEntity
	 */
	private com.iver.andami.persistence.generate.XmlEntity _xmlEntity;

	/**
	 * Field _bookmarks
	 */
	private com.iver.andami.persistence.generate.Bookmarks _bookmarks;

	/**
	 * Field _windows
	 */
	private com.iver.andami.persistence.generate.Windows _windows;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Plugin() {
		super();
	} // -- com.iver.andami.persistence.generate.Plugin()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'bookmarks'.
	 * 
	 * @return the value of field 'bookmarks'.
	 */
	public com.iver.andami.persistence.generate.Bookmarks getBookmarks() {
		return this._bookmarks;
	} // -- com.iver.andami.persistence.generate.Bookmarks getBookmarks()

	/**
	 * Returns the value of field 'lastUpdate'.
	 * 
	 * @return the value of field 'lastUpdate'.
	 */
	public java.lang.String getLastUpdate() {
		return this._lastUpdate;
	} // -- java.lang.String getLastUpdate()

	/**
	 * Returns the value of field 'name'.
	 * 
	 * @return the value of field 'name'.
	 */
	public java.lang.String getName() {
		return this._name;
	} // -- java.lang.String getName()

	/**
	 * Returns the value of field 'windows'.
	 * 
	 * @return the value of field 'windows'.
	 */
	public com.iver.andami.persistence.generate.Windows getWindows() {
		return this._windows;
	} // -- com.iver.andami.persistence.generate.Windows getWindows()

	/**
	 * Returns the value of field 'xmlEntity'.
	 * 
	 * @return the value of field 'xmlEntity'.
	 */
	public com.iver.andami.persistence.generate.XmlEntity getXmlEntity() {
		return this._xmlEntity;
	} // -- com.iver.andami.persistence.generate.XmlEntity getXmlEntity()

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
	 * Sets the value of field 'bookmarks'.
	 * 
	 * @param bookmarks
	 *            the value of field 'bookmarks'.
	 */
	public void setBookmarks(
			com.iver.andami.persistence.generate.Bookmarks bookmarks) {
		this._bookmarks = bookmarks;
	} // -- void setBookmarks(com.iver.andami.persistence.generate.Bookmarks)

	/**
	 * Sets the value of field 'lastUpdate'.
	 * 
	 * @param lastUpdate
	 *            the value of field 'lastUpdate'.
	 */
	public void setLastUpdate(java.lang.String lastUpdate) {
		this._lastUpdate = lastUpdate;
	} // -- void setLastUpdate(java.lang.String)

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
	 * Sets the value of field 'windows'.
	 * 
	 * @param windows
	 *            the value of field 'windows'.
	 */
	public void setWindows(com.iver.andami.persistence.generate.Windows windows) {
		this._windows = windows;
	} // -- void setWindows(com.iver.andami.persistence.generate.Windows)

	/**
	 * Sets the value of field 'xmlEntity'.
	 * 
	 * @param xmlEntity
	 *            the value of field 'xmlEntity'.
	 */
	public void setXmlEntity(
			com.iver.andami.persistence.generate.XmlEntity xmlEntity) {
		this._xmlEntity = xmlEntity;
	} // -- void setXmlEntity(com.iver.andami.persistence.generate.XmlEntity)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.persistence.generate.Plugin) Unmarshaller
				.unmarshal(com.iver.andami.persistence.generate.Plugin.class,
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
