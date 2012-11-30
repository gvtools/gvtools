/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ComboButtonElement.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ComboButtonElement.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ComboButtonElement implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _icon
	 */
	private java.lang.String _icon;

	/**
	 * Field _actionCommand
	 */
	private java.lang.String _actionCommand;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ComboButtonElement() {
		super();
	} // -- com.iver.andami.plugins.config.generate.ComboButtonElement()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Returns the value of field 'actionCommand'.
	 * 
	 * @return the value of field 'actionCommand'.
	 */
	public java.lang.String getActionCommand() {
		return this._actionCommand;
	} // -- java.lang.String getActionCommand()

	/**
	 * Returns the value of field 'icon'.
	 * 
	 * @return the value of field 'icon'.
	 */
	public java.lang.String getIcon() {
		return this._icon;
	} // -- java.lang.String getIcon()

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
	 * Sets the value of field 'actionCommand'.
	 * 
	 * @param actionCommand
	 *            the value of field 'actionCommand'.
	 */
	public void setActionCommand(java.lang.String actionCommand) {
		this._actionCommand = actionCommand;
	} // -- void setActionCommand(java.lang.String)

	/**
	 * Sets the value of field 'icon'.
	 * 
	 * @param icon
	 *            the value of field 'icon'.
	 */
	public void setIcon(java.lang.String icon) {
		this._icon = icon;
	} // -- void setIcon(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ComboButtonElement) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ComboButtonElement.class,
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
