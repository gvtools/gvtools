/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Menu.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Menu.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class Menu implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _actionCommand
	 */
	private java.lang.String _actionCommand;

	/**
	 * Field _key
	 */
	private java.lang.String _key;

	/**
	 * Field _icon
	 */
	private java.lang.String _icon;

	/**
	 * Field _tooltip
	 */
	private java.lang.String _tooltip;

	/**
	 * Field _enableText
	 */
	private java.lang.String _enableText;

	/**
	 * Field _mnemonic
	 */
	private java.lang.String _mnemonic;

	/**
	 * Field _text
	 */
	private java.lang.String _text;

	/**
	 * Field _position
	 */
	private int _position;

	/**
	 * keeps track of state for field: _position
	 */
	private boolean _has_position;

	/**
	 * Field _is_separator
	 */
	private boolean _is_separator;

	/**
	 * keeps track of state for field: _is_separator
	 */
	private boolean _has_is_separator;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Menu() {
		super();
	} // -- com.iver.andami.plugins.config.generate.Menu()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method deleteIs_separator
	 */
	public void deleteIs_separator() {
		this._has_is_separator = false;
	} // -- void deleteIs_separator()

	/**
	 * Method deletePosition
	 */
	public void deletePosition() {
		this._has_position = false;
	} // -- void deletePosition()

	/**
	 * Returns the value of field 'actionCommand'.
	 * 
	 * @return the value of field 'actionCommand'.
	 */
	public java.lang.String getActionCommand() {
		return this._actionCommand;
	} // -- java.lang.String getActionCommand()

	/**
	 * Returns the value of field 'enableText'.
	 * 
	 * @return the value of field 'enableText'.
	 */
	public java.lang.String getEnableText() {
		return this._enableText;
	} // -- java.lang.String getEnableText()

	/**
	 * Returns the value of field 'icon'.
	 * 
	 * @return the value of field 'icon'.
	 */
	public java.lang.String getIcon() {
		return this._icon;
	} // -- java.lang.String getIcon()

	/**
	 * Returns the value of field 'is_separator'.
	 * 
	 * @return the value of field 'is_separator'.
	 */
	public boolean getIs_separator() {
		return this._is_separator;
	} // -- boolean getIs_separator()

	/**
	 * Returns the value of field 'key'.
	 * 
	 * @return the value of field 'key'.
	 */
	public java.lang.String getKey() {
		return this._key;
	} // -- java.lang.String getKey()

	/**
	 * Returns the value of field 'mnemonic'.
	 * 
	 * @return the value of field 'mnemonic'.
	 */
	public java.lang.String getMnemonic() {
		return this._mnemonic;
	} // -- java.lang.String getMnemonic()

	/**
	 * Returns the value of field 'position'.
	 * 
	 * @return the value of field 'position'.
	 */
	public int getPosition() {
		return this._position;
	} // -- int getPosition()

	/**
	 * Returns the value of field 'text'.
	 * 
	 * @return the value of field 'text'.
	 */
	public java.lang.String getText() {
		return this._text;
	} // -- java.lang.String getText()

	/**
	 * Returns the value of field 'tooltip'.
	 * 
	 * @return the value of field 'tooltip'.
	 */
	public java.lang.String getTooltip() {
		return this._tooltip;
	} // -- java.lang.String getTooltip()

	/**
	 * Method hasIs_separator
	 */
	public boolean hasIs_separator() {
		return this._has_is_separator;
	} // -- boolean hasIs_separator()

	/**
	 * Method hasPosition
	 */
	public boolean hasPosition() {
		return this._has_position;
	} // -- boolean hasPosition()

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
	 * Sets the value of field 'enableText'.
	 * 
	 * @param enableText
	 *            the value of field 'enableText'.
	 */
	public void setEnableText(java.lang.String enableText) {
		this._enableText = enableText;
	} // -- void setEnableText(java.lang.String)

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
	 * Sets the value of field 'is_separator'.
	 * 
	 * @param is_separator
	 *            the value of field 'is_separator'.
	 */
	public void setIs_separator(boolean is_separator) {
		this._is_separator = is_separator;
		this._has_is_separator = true;
	} // -- void setIs_separator(boolean)

	/**
	 * Sets the value of field 'key'.
	 * 
	 * @param key
	 *            the value of field 'key'.
	 */
	public void setKey(java.lang.String key) {
		this._key = key;
	} // -- void setKey(java.lang.String)

	/**
	 * Sets the value of field 'mnemonic'.
	 * 
	 * @param mnemonic
	 *            the value of field 'mnemonic'.
	 */
	public void setMnemonic(java.lang.String mnemonic) {
		this._mnemonic = mnemonic;
	} // -- void setMnemonic(java.lang.String)

	/**
	 * Sets the value of field 'position'.
	 * 
	 * @param position
	 *            the value of field 'position'.
	 */
	public void setPosition(int position) {
		this._position = position;
		this._has_position = true;
	} // -- void setPosition(int)

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
	 * Sets the value of field 'tooltip'.
	 * 
	 * @param tooltip
	 *            the value of field 'tooltip'.
	 */
	public void setTooltip(java.lang.String tooltip) {
		this._tooltip = tooltip;
	} // -- void setTooltip(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.Menu) Unmarshaller
				.unmarshal(com.iver.andami.plugins.config.generate.Menu.class,
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
