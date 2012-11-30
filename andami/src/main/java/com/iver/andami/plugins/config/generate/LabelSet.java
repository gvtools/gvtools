/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: LabelSet.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class LabelSet.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class LabelSet implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _className
	 */
	private java.lang.String _className;

	/**
	 * Field _labelList
	 */
	private java.util.Vector _labelList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public LabelSet() {
		super();
		_labelList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.LabelSet()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addLabel
	 * 
	 * @param vLabel
	 */
	public void addLabel(com.iver.andami.plugins.config.generate.Label vLabel)
			throws java.lang.IndexOutOfBoundsException {
		_labelList.addElement(vLabel);
	} // -- void addLabel(com.iver.andami.plugins.config.generate.Label)

	/**
	 * Method addLabel
	 * 
	 * @param index
	 * @param vLabel
	 */
	public void addLabel(int index,
			com.iver.andami.plugins.config.generate.Label vLabel)
			throws java.lang.IndexOutOfBoundsException {
		_labelList.insertElementAt(vLabel, index);
	} // -- void addLabel(int, com.iver.andami.plugins.config.generate.Label)

	/**
	 * Method enumerateLabel
	 */
	public java.util.Enumeration enumerateLabel() {
		return _labelList.elements();
	} // -- java.util.Enumeration enumerateLabel()

	/**
	 * Returns the value of field 'className'.
	 * 
	 * @return the value of field 'className'.
	 */
	public java.lang.String getClassName() {
		return this._className;
	} // -- java.lang.String getClassName()

	/**
	 * Method getLabel
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Label getLabel(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _labelList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.Label) _labelList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.Label getLabel(int)

	/**
	 * Method getLabel
	 */
	public com.iver.andami.plugins.config.generate.Label[] getLabel() {
		int size = _labelList.size();
		com.iver.andami.plugins.config.generate.Label[] mArray = new com.iver.andami.plugins.config.generate.Label[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.Label) _labelList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.Label[] getLabel()

	/**
	 * Method getLabelCount
	 */
	public int getLabelCount() {
		return _labelList.size();
	} // -- int getLabelCount()

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
	 * Method removeAllLabel
	 */
	public void removeAllLabel() {
		_labelList.removeAllElements();
	} // -- void removeAllLabel()

	/**
	 * Method removeLabel
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Label removeLabel(int index) {
		java.lang.Object obj = _labelList.elementAt(index);
		_labelList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.Label) obj;
	} // -- com.iver.andami.plugins.config.generate.Label removeLabel(int)

	/**
	 * Sets the value of field 'className'.
	 * 
	 * @param className
	 *            the value of field 'className'.
	 */
	public void setClassName(java.lang.String className) {
		this._className = className;
	} // -- void setClassName(java.lang.String)

	/**
	 * Method setLabel
	 * 
	 * @param index
	 * @param vLabel
	 */
	public void setLabel(int index,
			com.iver.andami.plugins.config.generate.Label vLabel)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _labelList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_labelList.setElementAt(vLabel, index);
	} // -- void setLabel(int, com.iver.andami.plugins.config.generate.Label)

	/**
	 * Method setLabel
	 * 
	 * @param labelArray
	 */
	public void setLabel(
			com.iver.andami.plugins.config.generate.Label[] labelArray) {
		// -- copy array
		_labelList.removeAllElements();
		for (int i = 0; i < labelArray.length; i++) {
			_labelList.addElement(labelArray[i]);
		}
	} // -- void setLabel(com.iver.andami.plugins.config.generate.Label)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.LabelSet) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.LabelSet.class,
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
