/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PopupMenu.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PopupMenu.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class PopupMenu implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _name
	 */
	private java.lang.String _name;

	/**
	 * Field _menuList
	 */
	private java.util.Vector _menuList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public PopupMenu() {
		super();
		_menuList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.PopupMenu()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addMenu
	 * 
	 * @param vMenu
	 */
	public void addMenu(com.iver.andami.plugins.config.generate.Menu vMenu)
			throws java.lang.IndexOutOfBoundsException {
		_menuList.addElement(vMenu);
	} // -- void addMenu(com.iver.andami.plugins.config.generate.Menu)

	/**
	 * Method addMenu
	 * 
	 * @param index
	 * @param vMenu
	 */
	public void addMenu(int index,
			com.iver.andami.plugins.config.generate.Menu vMenu)
			throws java.lang.IndexOutOfBoundsException {
		_menuList.insertElementAt(vMenu, index);
	} // -- void addMenu(int, com.iver.andami.plugins.config.generate.Menu)

	/**
	 * Method enumerateMenu
	 */
	public java.util.Enumeration enumerateMenu() {
		return _menuList.elements();
	} // -- java.util.Enumeration enumerateMenu()

	/**
	 * Method getMenu
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Menu getMenu(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _menuList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.Menu) _menuList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.Menu getMenu(int)

	/**
	 * Method getMenu
	 */
	public com.iver.andami.plugins.config.generate.Menu[] getMenu() {
		int size = _menuList.size();
		com.iver.andami.plugins.config.generate.Menu[] mArray = new com.iver.andami.plugins.config.generate.Menu[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.Menu) _menuList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.Menu[] getMenu()

	/**
	 * Method getMenuCount
	 */
	public int getMenuCount() {
		return _menuList.size();
	} // -- int getMenuCount()

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
	 * Method removeAllMenu
	 */
	public void removeAllMenu() {
		_menuList.removeAllElements();
	} // -- void removeAllMenu()

	/**
	 * Method removeMenu
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Menu removeMenu(int index) {
		java.lang.Object obj = _menuList.elementAt(index);
		_menuList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.Menu) obj;
	} // -- com.iver.andami.plugins.config.generate.Menu removeMenu(int)

	/**
	 * Method setMenu
	 * 
	 * @param index
	 * @param vMenu
	 */
	public void setMenu(int index,
			com.iver.andami.plugins.config.generate.Menu vMenu)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _menuList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_menuList.setElementAt(vMenu, index);
	} // -- void setMenu(int, com.iver.andami.plugins.config.generate.Menu)

	/**
	 * Method setMenu
	 * 
	 * @param menuArray
	 */
	public void setMenu(com.iver.andami.plugins.config.generate.Menu[] menuArray) {
		// -- copy array
		_menuList.removeAllElements();
		for (int i = 0; i < menuArray.length; i++) {
			_menuList.addElement(menuArray[i]);
		}
	} // -- void setMenu(com.iver.andami.plugins.config.generate.Menu)

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
		return (com.iver.andami.plugins.config.generate.PopupMenu) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.PopupMenu.class,
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
