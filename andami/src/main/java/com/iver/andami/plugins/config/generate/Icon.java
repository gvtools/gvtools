/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Icon.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Icon.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class Icon implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _text
	 */
	private java.lang.String _text;

	/**
	 * Field _src
	 */
	private java.lang.String _src;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Icon() {
		super();
	} // -- com.iver.andami.plugins.config.generate.Icon()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'src'.
	 * 
	 * @return the value of field 'src'.
	 */
	public java.lang.String getSrc() {
		return this._src;
	} // -- java.lang.String getSrc()

	/**
	 * Returns the value of field 'text'.
	 * 
	 * @return the value of field 'text'.
	 */
	public java.lang.String getText() {
		return this._text;
	} // -- java.lang.String getText()

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
	 * Sets the value of field 'src'.
	 * 
	 * @param src
	 *            the value of field 'src'.
	 */
	public void setSrc(java.lang.String src) {
		this._src = src;
	} // -- void setSrc(java.lang.String)

	/**
	 * Sets the value of field 'text'.
	 * 
	 * @param text
	 *            the value of field 'text'.
	 */
	public void setText(java.lang.String text) {
		this._text = text;
	} // -- void setText(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.Icon) Unmarshaller
				.unmarshal(com.iver.andami.plugins.config.generate.Icon.class,
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
