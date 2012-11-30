/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id$
 */

package com.iver.andami.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class IconTheme.
 * 
 * @version $Revision$ $Date$
 */
public class IconTheme implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	/**
	 * Field _description
	 */
	private java.lang.String _description;

	/**
	 * Field _version
	 */
	private java.lang.String _version;

	/**
	 * Field _resource
	 */
	private java.lang.String _resource;

	/**
	 * Field _basedir
	 */
	private java.lang.String _basedir;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public IconTheme() {
		super();
	} // -- com.iver.andami.config.generate.IconTheme()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'basedir'.
	 * 
	 * @return the value of field 'basedir'.
	 */
	public java.lang.String getBasedir() {
		return this._basedir;
	} // -- java.lang.String getBasedir()

	/**
	 * Returns the value of field 'description'.
	 * 
	 * @return the value of field 'description'.
	 */
	public java.lang.String getDescription() {
		return this._description;
	} // -- java.lang.String getDescription()

	/**
	 * Returns the value of field 'name'.
	 * 
	 * @return the value of field 'name'.
	 */
	public java.lang.String getName() {
		return this._name;
	} // -- java.lang.String getName()

	/**
	 * Returns the value of field 'resource'.
	 * 
	 * @return the value of field 'resource'.
	 */
	public java.lang.String getResource() {
		return this._resource;
	} // -- java.lang.String getResource()

	/**
	 * Returns the value of field 'version'.
	 * 
	 * @return the value of field 'version'.
	 */
	public java.lang.String getVersion() {
		return this._version;
	} // -- java.lang.String getVersion()

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
	 * Sets the value of field 'basedir'.
	 * 
	 * @param basedir
	 *            the value of field 'basedir'.
	 */
	public void setBasedir(java.lang.String basedir) {
		this._basedir = basedir;
	} // -- void setBasedir(java.lang.String)

	/**
	 * Sets the value of field 'description'.
	 * 
	 * @param description
	 *            the value of field 'description'.
	 */
	public void setDescription(java.lang.String description) {
		this._description = description;
	} // -- void setDescription(java.lang.String)

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
	 * Sets the value of field 'resource'.
	 * 
	 * @param resource
	 *            the value of field 'resource'.
	 */
	public void setResource(java.lang.String resource) {
		this._resource = resource;
	} // -- void setResource(java.lang.String)

	/**
	 * Sets the value of field 'version'.
	 * 
	 * @param version
	 *            the value of field 'version'.
	 */
	public void setVersion(java.lang.String version) {
		this._version = version;
	} // -- void setVersion(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.config.generate.IconTheme) Unmarshaller
				.unmarshal(com.iver.andami.config.generate.IconTheme.class,
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
