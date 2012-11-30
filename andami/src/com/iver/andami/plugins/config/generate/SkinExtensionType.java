/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: SkinExtensionType.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class SkinExtensionType.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class SkinExtensionType implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _className
	 */
	private java.lang.String _className;

	/**
	 * Field _menuList
	 */
	private java.util.Vector _menuList;

	/**
	 * Field _toolBarList
	 */
	private java.util.Vector _toolBarList;

	/**
	 * Field _comboButtonList
	 */
	private java.util.Vector _comboButtonList;

	/**
	 * Field _comboScaleList
	 */
	private java.util.Vector _comboScaleList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public SkinExtensionType() {
		super();
		_menuList = new Vector();
		_toolBarList = new Vector();
		_comboButtonList = new Vector();
		_comboScaleList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.SkinExtensionType()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addComboButton
	 * 
	 * @param vComboButton
	 */
	public void addComboButton(
			com.iver.andami.plugins.config.generate.ComboButton vComboButton)
			throws java.lang.IndexOutOfBoundsException {
		_comboButtonList.addElement(vComboButton);
	} // -- void
		// addComboButton(com.iver.andami.plugins.config.generate.ComboButton)

	/**
	 * Method addComboButton
	 * 
	 * @param index
	 * @param vComboButton
	 */
	public void addComboButton(int index,
			com.iver.andami.plugins.config.generate.ComboButton vComboButton)
			throws java.lang.IndexOutOfBoundsException {
		_comboButtonList.insertElementAt(vComboButton, index);
	} // -- void addComboButton(int,
		// com.iver.andami.plugins.config.generate.ComboButton)

	/**
	 * Method addComboScale
	 * 
	 * @param vComboScale
	 */
	public void addComboScale(
			com.iver.andami.plugins.config.generate.ComboScale vComboScale)
			throws java.lang.IndexOutOfBoundsException {
		_comboScaleList.addElement(vComboScale);
	} // -- void
		// addComboScale(com.iver.andami.plugins.config.generate.ComboScale)

	/**
	 * Method addComboScale
	 * 
	 * @param index
	 * @param vComboScale
	 */
	public void addComboScale(int index,
			com.iver.andami.plugins.config.generate.ComboScale vComboScale)
			throws java.lang.IndexOutOfBoundsException {
		_comboScaleList.insertElementAt(vComboScale, index);
	} // -- void addComboScale(int,
		// com.iver.andami.plugins.config.generate.ComboScale)

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
	 * Method addToolBar
	 * 
	 * @param vToolBar
	 */
	public void addToolBar(
			com.iver.andami.plugins.config.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		_toolBarList.addElement(vToolBar);
	} // -- void addToolBar(com.iver.andami.plugins.config.generate.ToolBar)

	/**
	 * Method addToolBar
	 * 
	 * @param index
	 * @param vToolBar
	 */
	public void addToolBar(int index,
			com.iver.andami.plugins.config.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		_toolBarList.insertElementAt(vToolBar, index);
	} // -- void addToolBar(int,
		// com.iver.andami.plugins.config.generate.ToolBar)

	/**
	 * Method enumerateComboButton
	 */
	public java.util.Enumeration enumerateComboButton() {
		return _comboButtonList.elements();
	} // -- java.util.Enumeration enumerateComboButton()

	/**
	 * Method enumerateComboScale
	 */
	public java.util.Enumeration enumerateComboScale() {
		return _comboScaleList.elements();
	} // -- java.util.Enumeration enumerateComboScale()

	/**
	 * Method enumerateMenu
	 */
	public java.util.Enumeration enumerateMenu() {
		return _menuList.elements();
	} // -- java.util.Enumeration enumerateMenu()

	/**
	 * Method enumerateToolBar
	 */
	public java.util.Enumeration enumerateToolBar() {
		return _toolBarList.elements();
	} // -- java.util.Enumeration enumerateToolBar()

	/**
	 * Returns the value of field 'className'.
	 * 
	 * @return the value of field 'className'.
	 */
	public java.lang.String getClassName() {
		return this._className;
	} // -- java.lang.String getClassName()

	/**
	 * Method getComboButton
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboButton getComboButton(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboButtonList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.ComboButton) _comboButtonList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.ComboButton
		// getComboButton(int)

	/**
	 * Method getComboButton
	 */
	public com.iver.andami.plugins.config.generate.ComboButton[] getComboButton() {
		int size = _comboButtonList.size();
		com.iver.andami.plugins.config.generate.ComboButton[] mArray = new com.iver.andami.plugins.config.generate.ComboButton[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.ComboButton) _comboButtonList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.ComboButton[]
		// getComboButton()

	/**
	 * Method getComboButtonCount
	 */
	public int getComboButtonCount() {
		return _comboButtonList.size();
	} // -- int getComboButtonCount()

	/**
	 * Method getComboScale
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboScale getComboScale(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboScaleList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.ComboScale) _comboScaleList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.ComboScale
		// getComboScale(int)

	/**
	 * Method getComboScale
	 */
	public com.iver.andami.plugins.config.generate.ComboScale[] getComboScale() {
		int size = _comboScaleList.size();
		com.iver.andami.plugins.config.generate.ComboScale[] mArray = new com.iver.andami.plugins.config.generate.ComboScale[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.ComboScale) _comboScaleList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.ComboScale[] getComboScale()

	/**
	 * Method getComboScaleCount
	 */
	public int getComboScaleCount() {
		return _comboScaleList.size();
	} // -- int getComboScaleCount()

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
	 * Method getToolBar
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ToolBar getToolBar(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _toolBarList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.ToolBar) _toolBarList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.ToolBar getToolBar(int)

	/**
	 * Method getToolBar
	 */
	public com.iver.andami.plugins.config.generate.ToolBar[] getToolBar() {
		int size = _toolBarList.size();
		com.iver.andami.plugins.config.generate.ToolBar[] mArray = new com.iver.andami.plugins.config.generate.ToolBar[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.ToolBar) _toolBarList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.ToolBar[] getToolBar()

	/**
	 * Method getToolBarCount
	 */
	public int getToolBarCount() {
		return _toolBarList.size();
	} // -- int getToolBarCount()

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
	 * Method removeAllComboButton
	 */
	public void removeAllComboButton() {
		_comboButtonList.removeAllElements();
	} // -- void removeAllComboButton()

	/**
	 * Method removeAllComboScale
	 */
	public void removeAllComboScale() {
		_comboScaleList.removeAllElements();
	} // -- void removeAllComboScale()

	/**
	 * Method removeAllMenu
	 */
	public void removeAllMenu() {
		_menuList.removeAllElements();
	} // -- void removeAllMenu()

	/**
	 * Method removeAllToolBar
	 */
	public void removeAllToolBar() {
		_toolBarList.removeAllElements();
	} // -- void removeAllToolBar()

	/**
	 * Method removeComboButton
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboButton removeComboButton(
			int index) {
		java.lang.Object obj = _comboButtonList.elementAt(index);
		_comboButtonList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.ComboButton) obj;
	} // -- com.iver.andami.plugins.config.generate.ComboButton
		// removeComboButton(int)

	/**
	 * Method removeComboScale
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ComboScale removeComboScale(
			int index) {
		java.lang.Object obj = _comboScaleList.elementAt(index);
		_comboScaleList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.ComboScale) obj;
	} // -- com.iver.andami.plugins.config.generate.ComboScale
		// removeComboScale(int)

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
	 * Method removeToolBar
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ToolBar removeToolBar(
			int index) {
		java.lang.Object obj = _toolBarList.elementAt(index);
		_toolBarList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.ToolBar) obj;
	} // -- com.iver.andami.plugins.config.generate.ToolBar removeToolBar(int)

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
	 * Method setComboButton
	 * 
	 * @param index
	 * @param vComboButton
	 */
	public void setComboButton(int index,
			com.iver.andami.plugins.config.generate.ComboButton vComboButton)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboButtonList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_comboButtonList.setElementAt(vComboButton, index);
	} // -- void setComboButton(int,
		// com.iver.andami.plugins.config.generate.ComboButton)

	/**
	 * Method setComboButton
	 * 
	 * @param comboButtonArray
	 */
	public void setComboButton(
			com.iver.andami.plugins.config.generate.ComboButton[] comboButtonArray) {
		// -- copy array
		_comboButtonList.removeAllElements();
		for (int i = 0; i < comboButtonArray.length; i++) {
			_comboButtonList.addElement(comboButtonArray[i]);
		}
	} // -- void
		// setComboButton(com.iver.andami.plugins.config.generate.ComboButton)

	/**
	 * Method setComboScale
	 * 
	 * @param index
	 * @param vComboScale
	 */
	public void setComboScale(int index,
			com.iver.andami.plugins.config.generate.ComboScale vComboScale)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _comboScaleList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_comboScaleList.setElementAt(vComboScale, index);
	} // -- void setComboScale(int,
		// com.iver.andami.plugins.config.generate.ComboScale)

	/**
	 * Method setComboScale
	 * 
	 * @param comboScaleArray
	 */
	public void setComboScale(
			com.iver.andami.plugins.config.generate.ComboScale[] comboScaleArray) {
		// -- copy array
		_comboScaleList.removeAllElements();
		for (int i = 0; i < comboScaleArray.length; i++) {
			_comboScaleList.addElement(comboScaleArray[i]);
		}
	} // -- void
		// setComboScale(com.iver.andami.plugins.config.generate.ComboScale)

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
	 * Method setToolBar
	 * 
	 * @param index
	 * @param vToolBar
	 */
	public void setToolBar(int index,
			com.iver.andami.plugins.config.generate.ToolBar vToolBar)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _toolBarList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_toolBarList.setElementAt(vToolBar, index);
	} // -- void setToolBar(int,
		// com.iver.andami.plugins.config.generate.ToolBar)

	/**
	 * Method setToolBar
	 * 
	 * @param toolBarArray
	 */
	public void setToolBar(
			com.iver.andami.plugins.config.generate.ToolBar[] toolBarArray) {
		// -- copy array
		_toolBarList.removeAllElements();
		for (int i = 0; i < toolBarArray.length; i++) {
			_toolBarList.addElement(toolBarArray[i]);
		}
	} // -- void setToolBar(com.iver.andami.plugins.config.generate.ToolBar)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.SkinExtensionType) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.SkinExtensionType.class,
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
