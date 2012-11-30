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

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class AndamiOptions.
 * 
 * @version $Revision$ $Date$
 */
public class AndamiOptions implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _optionList
	 */
	private java.util.Vector _optionList;

	/**
	 * Field _iconTheme
	 */
	private com.iver.andami.config.generate.IconTheme _iconTheme;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public AndamiOptions() {
		super();
		_optionList = new Vector();
	} // -- com.iver.andami.config.generate.AndamiOptions()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addOption
	 * 
	 * @param vOption
	 */
	public void addOption(com.iver.andami.config.generate.Option vOption)
			throws java.lang.IndexOutOfBoundsException {
		_optionList.addElement(vOption);
	} // -- void addOption(com.iver.andami.config.generate.Option)

	/**
	 * Method addOption
	 * 
	 * @param index
	 * @param vOption
	 */
	public void addOption(int index,
			com.iver.andami.config.generate.Option vOption)
			throws java.lang.IndexOutOfBoundsException {
		_optionList.insertElementAt(vOption, index);
	} // -- void addOption(int, com.iver.andami.config.generate.Option)

	/**
	 * Method enumerateOption
	 */
	public java.util.Enumeration enumerateOption() {
		return _optionList.elements();
	} // -- java.util.Enumeration enumerateOption()

	/**
	 * Returns the value of field 'iconTheme'.
	 * 
	 * @return the value of field 'iconTheme'.
	 */
	public com.iver.andami.config.generate.IconTheme getIconTheme() {
		return this._iconTheme;
	} // -- com.iver.andami.config.generate.IconTheme getIconTheme()

	/**
	 * Method getOption
	 * 
	 * @param index
	 */
	public com.iver.andami.config.generate.Option getOption(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _optionList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.config.generate.Option) _optionList
				.elementAt(index);
	} // -- com.iver.andami.config.generate.Option getOption(int)

	/**
	 * Method getOption
	 */
	public com.iver.andami.config.generate.Option[] getOption() {
		int size = _optionList.size();
		com.iver.andami.config.generate.Option[] mArray = new com.iver.andami.config.generate.Option[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.config.generate.Option) _optionList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.config.generate.Option[] getOption()

	/**
	 * Method getOptionCount
	 */
	public int getOptionCount() {
		return _optionList.size();
	} // -- int getOptionCount()

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
	 * Method removeAllOption
	 */
	public void removeAllOption() {
		_optionList.removeAllElements();
	} // -- void removeAllOption()

	/**
	 * Method removeOption
	 * 
	 * @param index
	 */
	public com.iver.andami.config.generate.Option removeOption(int index) {
		java.lang.Object obj = _optionList.elementAt(index);
		_optionList.removeElementAt(index);
		return (com.iver.andami.config.generate.Option) obj;
	} // -- com.iver.andami.config.generate.Option removeOption(int)

	/**
	 * Sets the value of field 'iconTheme'.
	 * 
	 * @param iconTheme
	 *            the value of field 'iconTheme'.
	 */
	public void setIconTheme(com.iver.andami.config.generate.IconTheme iconTheme) {
		this._iconTheme = iconTheme;
	} // -- void setIconTheme(com.iver.andami.config.generate.IconTheme)

	/**
	 * Method setOption
	 * 
	 * @param index
	 * @param vOption
	 */
	public void setOption(int index,
			com.iver.andami.config.generate.Option vOption)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _optionList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_optionList.setElementAt(vOption, index);
	} // -- void setOption(int, com.iver.andami.config.generate.Option)

	/**
	 * Method setOption
	 * 
	 * @param optionArray
	 */
	public void setOption(com.iver.andami.config.generate.Option[] optionArray) {
		// -- copy array
		_optionList.removeAllElements();
		for (int i = 0; i < optionArray.length; i++) {
			_optionList.addElement(optionArray[i]);
		}
	} // -- void setOption(com.iver.andami.config.generate.Option)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.config.generate.AndamiOptions) Unmarshaller
				.unmarshal(com.iver.andami.config.generate.AndamiOptions.class,
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
