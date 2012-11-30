/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Label.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Label.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class Label implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _size
	 */
	private int _size;

	/**
	 * keeps track of state for field: _size
	 */
	private boolean _has_size;

	/**
	 * Field _id
	 */
	private java.lang.String _id;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Label() {
		super();
	} // -- com.iver.andami.plugins.config.generate.Label()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method deleteSize
	 */
	public void deleteSize() {
		this._has_size = false;
	} // -- void deleteSize()

	/**
	 * Returns the value of field 'id'.
	 * 
	 * @return the value of field 'id'.
	 */
	public java.lang.String getId() {
		return this._id;
	} // -- java.lang.String getId()

	/**
	 * Returns the value of field 'size'.
	 * 
	 * @return the value of field 'size'.
	 */
	public int getSize() {
		return this._size;
	} // -- int getSize()

	/**
	 * Method hasSize
	 */
	public boolean hasSize() {
		return this._has_size;
	} // -- boolean hasSize()

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
	 * Sets the value of field 'id'.
	 * 
	 * @param id
	 *            the value of field 'id'.
	 */
	public void setId(java.lang.String id) {
		this._id = id;
	} // -- void setId(java.lang.String)

	/**
	 * Sets the value of field 'size'.
	 * 
	 * @param size
	 *            the value of field 'size'.
	 */
	public void setSize(int size) {
		this._size = size;
		this._has_size = true;
	} // -- void setSize(int)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.Label) Unmarshaller
				.unmarshal(com.iver.andami.plugins.config.generate.Label.class,
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
