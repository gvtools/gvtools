/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PopupMenus.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PopupMenus.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class PopupMenus implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _popupMenuList
	 */
	private java.util.Vector _popupMenuList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public PopupMenus() {
		super();
		_popupMenuList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.PopupMenus()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addPopupMenu
	 * 
	 * @param vPopupMenu
	 */
	public void addPopupMenu(
			com.iver.andami.plugins.config.generate.PopupMenu vPopupMenu)
			throws java.lang.IndexOutOfBoundsException {
		_popupMenuList.addElement(vPopupMenu);
	} // -- void addPopupMenu(com.iver.andami.plugins.config.generate.PopupMenu)

	/**
	 * Method addPopupMenu
	 * 
	 * @param index
	 * @param vPopupMenu
	 */
	public void addPopupMenu(int index,
			com.iver.andami.plugins.config.generate.PopupMenu vPopupMenu)
			throws java.lang.IndexOutOfBoundsException {
		_popupMenuList.insertElementAt(vPopupMenu, index);
	} // -- void addPopupMenu(int,
		// com.iver.andami.plugins.config.generate.PopupMenu)

	/**
	 * Method enumeratePopupMenu
	 */
	public java.util.Enumeration enumeratePopupMenu() {
		return _popupMenuList.elements();
	} // -- java.util.Enumeration enumeratePopupMenu()

	/**
	 * Method getPopupMenu
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.PopupMenu getPopupMenu(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _popupMenuList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.PopupMenu) _popupMenuList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.PopupMenu getPopupMenu(int)

	/**
	 * Method getPopupMenu
	 */
	public com.iver.andami.plugins.config.generate.PopupMenu[] getPopupMenu() {
		int size = _popupMenuList.size();
		com.iver.andami.plugins.config.generate.PopupMenu[] mArray = new com.iver.andami.plugins.config.generate.PopupMenu[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.PopupMenu) _popupMenuList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.PopupMenu[] getPopupMenu()

	/**
	 * Method getPopupMenuCount
	 */
	public int getPopupMenuCount() {
		return _popupMenuList.size();
	} // -- int getPopupMenuCount()

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
	 * Method removeAllPopupMenu
	 */
	public void removeAllPopupMenu() {
		_popupMenuList.removeAllElements();
	} // -- void removeAllPopupMenu()

	/**
	 * Method removePopupMenu
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.PopupMenu removePopupMenu(
			int index) {
		java.lang.Object obj = _popupMenuList.elementAt(index);
		_popupMenuList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.PopupMenu) obj;
	} // -- com.iver.andami.plugins.config.generate.PopupMenu
		// removePopupMenu(int)

	/**
	 * Method setPopupMenu
	 * 
	 * @param index
	 * @param vPopupMenu
	 */
	public void setPopupMenu(int index,
			com.iver.andami.plugins.config.generate.PopupMenu vPopupMenu)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _popupMenuList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_popupMenuList.setElementAt(vPopupMenu, index);
	} // -- void setPopupMenu(int,
		// com.iver.andami.plugins.config.generate.PopupMenu)

	/**
	 * Method setPopupMenu
	 * 
	 * @param popupMenuArray
	 */
	public void setPopupMenu(
			com.iver.andami.plugins.config.generate.PopupMenu[] popupMenuArray) {
		// -- copy array
		_popupMenuList.removeAllElements();
		for (int i = 0; i < popupMenuArray.length; i++) {
			_popupMenuList.addElement(popupMenuArray[i]);
		}
	} // -- void setPopupMenu(com.iver.andami.plugins.config.generate.PopupMenu)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.PopupMenus) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.PopupMenus.class,
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
