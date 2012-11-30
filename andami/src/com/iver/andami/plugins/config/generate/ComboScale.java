/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ComboScale.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ComboScale.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ComboScale implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	/**
	 * Field _label
	 */
	private java.lang.String _label;

	/**
	 * Field _elements
	 */
	private java.lang.Object _elements;

	/**
	 * Field _value
	 */
	private java.lang.Object _value;

	/**
	 * Field _actionCommand
	 */
	private java.lang.String _actionCommand;

	/**
	 * Field _position
	 */
	private java.lang.Object _position;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ComboScale() {
		super();
	} // -- com.iver.andami.plugins.config.generate.ComboScale()

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
	 * Returns the value of field 'elements'.
	 * 
	 * @return the value of field 'elements'.
	 */
	public java.lang.Object getElements() {
		return this._elements;
	} // -- java.lang.Object getElements()

	/**
	 * Returns the value of field 'label'.
	 * 
	 * @return the value of field 'label'.
	 */
	public java.lang.String getLabel() {
		return this._label;
	} // -- java.lang.String getLabel()

	/**
	 * Returns the value of field 'name'.
	 * 
	 * @return the value of field 'name'.
	 */
	public java.lang.String getName() {
		return this._name;
	} // -- java.lang.String getName()

	/**
	 * Returns the value of field 'position'.
	 * 
	 * @return the value of field 'position'.
	 */
	public java.lang.Object getPosition() {
		return this._position;
	} // -- java.lang.Object getPosition()

	/**
	 * Returns the value of field 'value'.
	 * 
	 * @return the value of field 'value'.
	 */
	public java.lang.Object getValue() {
		return this._value;
	} // -- java.lang.Object getValue()

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
	 * Sets the value of field 'elements'.
	 * 
	 * @param elements
	 *            the value of field 'elements'.
	 */
	public void setElements(java.lang.Object elements) {
		this._elements = elements;
	} // -- void setElements(java.lang.Object)

	/**
	 * Sets the value of field 'label'.
	 * 
	 * @param label
	 *            the value of field 'label'.
	 */
	public void setLabel(java.lang.String label) {
		this._label = label;
	} // -- void setLabel(java.lang.String)

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
	 * Sets the value of field 'position'.
	 * 
	 * @param position
	 *            the value of field 'position'.
	 */
	public void setPosition(java.lang.Object position) {
		this._position = position;
	} // -- void setPosition(java.lang.Object)

	/**
	 * Sets the value of field 'value'.
	 * 
	 * @param value
	 *            the value of field 'value'.
	 */
	public void setValue(java.lang.Object value) {
		this._value = value;
	} // -- void setValue(java.lang.Object)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ComboScale) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ComboScale.class,
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
