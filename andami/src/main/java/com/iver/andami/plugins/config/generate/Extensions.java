/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Extensions.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Extensions.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class Extensions implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _extensionList
	 */
	private java.util.Vector _extensionList;

	/**
	 * Field _skinExtensionList
	 */
	private java.util.Vector _skinExtensionList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public Extensions() {
		super();
		_extensionList = new Vector();
		_skinExtensionList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.Extensions()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addExtension
	 * 
	 * @param vExtension
	 */
	public void addExtension(
			com.iver.andami.plugins.config.generate.Extension vExtension)
			throws java.lang.IndexOutOfBoundsException {
		_extensionList.addElement(vExtension);
	} // -- void addExtension(com.iver.andami.plugins.config.generate.Extension)

	/**
	 * Method addExtension
	 * 
	 * @param index
	 * @param vExtension
	 */
	public void addExtension(int index,
			com.iver.andami.plugins.config.generate.Extension vExtension)
			throws java.lang.IndexOutOfBoundsException {
		_extensionList.insertElementAt(vExtension, index);
	} // -- void addExtension(int,
		// com.iver.andami.plugins.config.generate.Extension)

	/**
	 * Method addSkinExtension
	 * 
	 * @param vSkinExtension
	 */
	public void addSkinExtension(
			com.iver.andami.plugins.config.generate.SkinExtension vSkinExtension)
			throws java.lang.IndexOutOfBoundsException {
		_skinExtensionList.addElement(vSkinExtension);
	} // -- void
		// addSkinExtension(com.iver.andami.plugins.config.generate.SkinExtension)

	/**
	 * Method addSkinExtension
	 * 
	 * @param index
	 * @param vSkinExtension
	 */
	public void addSkinExtension(int index,
			com.iver.andami.plugins.config.generate.SkinExtension vSkinExtension)
			throws java.lang.IndexOutOfBoundsException {
		_skinExtensionList.insertElementAt(vSkinExtension, index);
	} // -- void addSkinExtension(int,
		// com.iver.andami.plugins.config.generate.SkinExtension)

	/**
	 * Method enumerateExtension
	 */
	public java.util.Enumeration enumerateExtension() {
		return _extensionList.elements();
	} // -- java.util.Enumeration enumerateExtension()

	/**
	 * Method enumerateSkinExtension
	 */
	public java.util.Enumeration enumerateSkinExtension() {
		return _skinExtensionList.elements();
	} // -- java.util.Enumeration enumerateSkinExtension()

	/**
	 * Method getExtension
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Extension getExtension(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _extensionList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.Extension) _extensionList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.Extension getExtension(int)

	/**
	 * Method getExtension
	 */
	public com.iver.andami.plugins.config.generate.Extension[] getExtension() {
		int size = _extensionList.size();
		com.iver.andami.plugins.config.generate.Extension[] mArray = new com.iver.andami.plugins.config.generate.Extension[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.Extension) _extensionList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.Extension[] getExtension()

	/**
	 * Method getExtensionCount
	 */
	public int getExtensionCount() {
		return _extensionList.size();
	} // -- int getExtensionCount()

	/**
	 * Method getSkinExtension
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.SkinExtension getSkinExtension(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _skinExtensionList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.SkinExtension) _skinExtensionList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.SkinExtension
		// getSkinExtension(int)

	/**
	 * Method getSkinExtension
	 */
	public com.iver.andami.plugins.config.generate.SkinExtension[] getSkinExtension() {
		int size = _skinExtensionList.size();
		com.iver.andami.plugins.config.generate.SkinExtension[] mArray = new com.iver.andami.plugins.config.generate.SkinExtension[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.SkinExtension) _skinExtensionList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.SkinExtension[]
		// getSkinExtension()

	/**
	 * Method getSkinExtensionCount
	 */
	public int getSkinExtensionCount() {
		return _skinExtensionList.size();
	} // -- int getSkinExtensionCount()

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
	 * Method removeAllExtension
	 */
	public void removeAllExtension() {
		_extensionList.removeAllElements();
	} // -- void removeAllExtension()

	/**
	 * Method removeAllSkinExtension
	 */
	public void removeAllSkinExtension() {
		_skinExtensionList.removeAllElements();
	} // -- void removeAllSkinExtension()

	/**
	 * Method removeExtension
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Extension removeExtension(
			int index) {
		java.lang.Object obj = _extensionList.elementAt(index);
		_extensionList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.Extension) obj;
	} // -- com.iver.andami.plugins.config.generate.Extension
		// removeExtension(int)

	/**
	 * Method removeSkinExtension
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.SkinExtension removeSkinExtension(
			int index) {
		java.lang.Object obj = _skinExtensionList.elementAt(index);
		_skinExtensionList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.SkinExtension) obj;
	} // -- com.iver.andami.plugins.config.generate.SkinExtension
		// removeSkinExtension(int)

	/**
	 * Method setExtension
	 * 
	 * @param index
	 * @param vExtension
	 */
	public void setExtension(int index,
			com.iver.andami.plugins.config.generate.Extension vExtension)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _extensionList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_extensionList.setElementAt(vExtension, index);
	} // -- void setExtension(int,
		// com.iver.andami.plugins.config.generate.Extension)

	/**
	 * Method setExtension
	 * 
	 * @param extensionArray
	 */
	public void setExtension(
			com.iver.andami.plugins.config.generate.Extension[] extensionArray) {
		// -- copy array
		_extensionList.removeAllElements();
		for (int i = 0; i < extensionArray.length; i++) {
			_extensionList.addElement(extensionArray[i]);
		}
	} // -- void setExtension(com.iver.andami.plugins.config.generate.Extension)

	/**
	 * Method setSkinExtension
	 * 
	 * @param index
	 * @param vSkinExtension
	 */
	public void setSkinExtension(int index,
			com.iver.andami.plugins.config.generate.SkinExtension vSkinExtension)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _skinExtensionList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_skinExtensionList.setElementAt(vSkinExtension, index);
	} // -- void setSkinExtension(int,
		// com.iver.andami.plugins.config.generate.SkinExtension)

	/**
	 * Method setSkinExtension
	 * 
	 * @param skinExtensionArray
	 */
	public void setSkinExtension(
			com.iver.andami.plugins.config.generate.SkinExtension[] skinExtensionArray) {
		// -- copy array
		_skinExtensionList.removeAllElements();
		for (int i = 0; i < skinExtensionArray.length; i++) {
			_skinExtensionList.addElement(skinExtensionArray[i]);
		}
	} // -- void
		// setSkinExtension(com.iver.andami.plugins.config.generate.SkinExtension)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.Extensions) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.Extensions.class,
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
