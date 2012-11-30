/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ResourceBundle.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ResourceBundle.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ResourceBundle implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ResourceBundle() {
		super();
	} // -- com.iver.andami.plugins.config.generate.ResourceBundle()

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
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ResourceBundle) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ResourceBundle.class,
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
