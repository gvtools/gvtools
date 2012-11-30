/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ComboButton.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ComboButton.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ComboButton implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	/**
	 * Field _position
	 */
	private java.lang.Object _position;

	/**
	 * Field _comboButtonElementList
	 */
	private java.util.Vector _comboButtonElementList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ComboButton() {
		super();
		_comboButtonElementList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.ComboButton()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addComboButtonElement
	 * 
	 * @param vComboButtonElement
	 */
	public void addComboButtonElement(
			com.iver.andami.plugins.config.generate.ComboButtonElement vComboButtonElement)
			throws java.lang.IndexOutOfBoundsException {
		_comboButtonElementList.addElement(vComboButtonElement);
	} // -- void
		// addComboButtonElement(com.iver.andami.plugins.config.generate.ComboButtonElement)

	/**
	 * Method addComboButtonElement
	 * 
	 * @param index
	 * @param vComboButtonElement
	 */
	public void addComboButtonElement(
			int index,
			com.iver.andami.plugins.config.generate.ComboButtonElement vComboButtonElement)
			throws java.lang.IndexOutOfBoundsException {
		_comboButtonElementList.insertElementAt(vComboButtonElement, index);
	} // -- void addComboButtonElement(int,
		// com.iver.andami.plugins.config.generate.ComboButtonElement)

	/**
	 * Method enumerateComboButtonElement
	 */
	public java.util.Enumeration enumerateComboButtonElement() {
		return _comboButtonElementList.elements();
	} // -- java.util.Enumeration enumerateComboButtonElement()

	/**
	 * Method getComboButtonElement
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboButtonElement getComboButtonElement(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboButtonElementList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.ComboButtonElement) _comboButtonElementList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.ComboButtonElement
		// getComboButtonElement(int)

	/**
	 * Method getComboButtonElement
	 */
	public com.iver.andami.plugins.config.generate.ComboButtonElement[] getComboButtonElement() {
		int size = _comboButtonElementList.size();
		com.iver.andami.plugins.config.generate.ComboButtonElement[] mArray = new com.iver.andami.plugins.config.generate.ComboButtonElement[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.ComboButtonElement) _comboButtonElementList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.ComboButtonElement[]
		// getComboButtonElement()

	/**
	 * Method getComboButtonElementCount
	 */
	public int getComboButtonElementCount() {
		return _comboButtonElementList.size();
	} // -- int getComboButtonElementCount()

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
	 * Method removeAllComboButtonElement
	 */
	public void removeAllComboButtonElement() {
		_comboButtonElementList.removeAllElements();
	} // -- void removeAllComboButtonElement()

	/**
	 * Method removeComboButtonElement
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboButtonElement removeComboButtonElement(
			int index) {
		java.lang.Object obj = _comboButtonElementList.elementAt(index);
		_comboButtonElementList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.ComboButtonElement) obj;
	} // -- com.iver.andami.plugins.config.generate.ComboButtonElement
		// removeComboButtonElement(int)

	/**
	 * Method setComboButtonElement
	 * 
	 * @param index
	 * @param vComboButtonElement
	 */
	public void setComboButtonElement(
			int index,
			com.iver.andami.plugins.config.generate.ComboButtonElement vComboButtonElement)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboButtonElementList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_comboButtonElementList.setElementAt(vComboButtonElement, index);
	} // -- void setComboButtonElement(int,
		// com.iver.andami.plugins.config.generate.ComboButtonElement)

	/**
	 * Method setComboButtonElement
	 * 
	 * @param comboButtonElementArray
	 */
	public void setComboButtonElement(
			com.iver.andami.plugins.config.generate.ComboButtonElement[] comboButtonElementArray) {
		// -- copy array
		_comboButtonElementList.removeAllElements();
		for (int i = 0; i < comboButtonElementArray.length; i++) {
			_comboButtonElementList.addElement(comboButtonElementArray[i]);
		}
	} // -- void
		// setComboButtonElement(com.iver.andami.plugins.config.generate.ComboButtonElement)

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
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ComboButton) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ComboButton.class,
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
