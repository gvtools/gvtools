/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ToolBar.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ToolBar.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ToolBar implements java.io.Serializable {

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
	private int _position = 50;

	/**
	 * keeps track of state for field: _position
	 */
	private boolean _has_position;

	/**
	 * Field _isVisible
	 */
	private boolean _isVisible = true;

	/**
	 * keeps track of state for field: _isVisible
	 */
	private boolean _has_isVisible;

	/**
	 * Field _actionToolList
	 */
	private java.util.Vector _actionToolList;

	/**
	 * Field _selectableToolList
	 */
	private java.util.Vector _selectableToolList;

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

	public ToolBar() {
		super();
		_actionToolList = new Vector();
		_selectableToolList = new Vector();
		_comboButtonList = new Vector();
		_comboScaleList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.ToolBar()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addActionTool
	 * 
	 * @param vActionTool
	 */
	public void addActionTool(
			com.iver.andami.plugins.config.generate.ActionTool vActionTool)
			throws java.lang.IndexOutOfBoundsException {
		_actionToolList.addElement(vActionTool);
	} // -- void
		// addActionTool(com.iver.andami.plugins.config.generate.ActionTool)

	/**
	 * Method addActionTool
	 * 
	 * @param index
	 * @param vActionTool
	 */
	public void addActionTool(int index,
			com.iver.andami.plugins.config.generate.ActionTool vActionTool)
			throws java.lang.IndexOutOfBoundsException {
		_actionToolList.insertElementAt(vActionTool, index);
	} // -- void addActionTool(int,
		// com.iver.andami.plugins.config.generate.ActionTool)

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
	 * Method addSelectableTool
	 * 
	 * @param vSelectableTool
	 */
	public void addSelectableTool(
			com.iver.andami.plugins.config.generate.SelectableTool vSelectableTool)
			throws java.lang.IndexOutOfBoundsException {
		_selectableToolList.addElement(vSelectableTool);
	} // -- void
		// addSelectableTool(com.iver.andami.plugins.config.generate.SelectableTool)

	/**
	 * Method addSelectableTool
	 * 
	 * @param index
	 * @param vSelectableTool
	 */
	public void addSelectableTool(
			int index,
			com.iver.andami.plugins.config.generate.SelectableTool vSelectableTool)
			throws java.lang.IndexOutOfBoundsException {
		_selectableToolList.insertElementAt(vSelectableTool, index);
	} // -- void addSelectableTool(int,
		// com.iver.andami.plugins.config.generate.SelectableTool)

	/**
	 * Method deleteIsVisible
	 */
	public void deleteIsVisible() {
		this._has_isVisible = false;
	} // -- void deleteIsVisible()

	/**
	 * Method deletePosition
	 */
	public void deletePosition() {
		this._has_position = false;
	} // -- void deletePosition()

	/**
	 * Method enumerateActionTool
	 */
	public java.util.Enumeration enumerateActionTool() {
		return _actionToolList.elements();
	} // -- java.util.Enumeration enumerateActionTool()

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
	 * Method enumerateSelectableTool
	 */
	public java.util.Enumeration enumerateSelectableTool() {
		return _selectableToolList.elements();
	} // -- java.util.Enumeration enumerateSelectableTool()

	/**
	 * Method getActionTool
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ActionTool getActionTool(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _actionToolList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.ActionTool) _actionToolList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.ActionTool
		// getActionTool(int)

	/**
	 * Method getActionTool
	 */
	public com.iver.andami.plugins.config.generate.ActionTool[] getActionTool() {
		int size = _actionToolList.size();
		com.iver.andami.plugins.config.generate.ActionTool[] mArray = new com.iver.andami.plugins.config.generate.ActionTool[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.ActionTool) _actionToolList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.ActionTool[] getActionTool()

	/**
	 * Method getActionToolCount
	 */
	public int getActionToolCount() {
		return _actionToolList.size();
	} // -- int getActionToolCount()

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
	 * Returns the value of field 'isVisible'.
	 * 
	 * @return the value of field 'isVisible'.
	 */
	public boolean getIsVisible() {
		return this._isVisible;
	} // -- boolean getIsVisible()

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
	public int getPosition() {
		return this._position;
	} // -- int getPosition()

	/**
	 * Method getSelectableTool
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.SelectableTool getSelectableTool(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _selectableToolList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.SelectableTool) _selectableToolList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.SelectableTool
		// getSelectableTool(int)

	/**
	 * Method getSelectableTool
	 */
	public com.iver.andami.plugins.config.generate.SelectableTool[] getSelectableTool() {
		int size = _selectableToolList.size();
		com.iver.andami.plugins.config.generate.SelectableTool[] mArray = new com.iver.andami.plugins.config.generate.SelectableTool[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.SelectableTool) _selectableToolList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.SelectableTool[]
		// getSelectableTool()

	/**
	 * Method getSelectableToolCount
	 */
	public int getSelectableToolCount() {
		return _selectableToolList.size();
	} // -- int getSelectableToolCount()

	/**
	 * Method hasIsVisible
	 */
	public boolean hasIsVisible() {
		return this._has_isVisible;
	} // -- boolean hasIsVisible()

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
	 * Method removeActionTool
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.ActionTool removeActionTool(
			int index) {
		java.lang.Object obj = _actionToolList.elementAt(index);
		_actionToolList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.ActionTool) obj;
	} // -- com.iver.andami.plugins.config.generate.ActionTool
		// removeActionTool(int)

	/**
	 * Method removeAllActionTool
	 */
	public void removeAllActionTool() {
		_actionToolList.removeAllElements();
	} // -- void removeAllActionTool()

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
	 * Method removeAllSelectableTool
	 */
	public void removeAllSelectableTool() {
		_selectableToolList.removeAllElements();
	} // -- void removeAllSelectableTool()

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
	 * Method removeSelectableTool
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.SelectableTool removeSelectableTool(
			int index) {
		java.lang.Object obj = _selectableToolList.elementAt(index);
		_selectableToolList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.SelectableTool) obj;
	} // -- com.iver.andami.plugins.config.generate.SelectableTool
		// removeSelectableTool(int)

	/**
	 * Method setActionTool
	 * 
	 * @param index
	 * @param vActionTool
	 */
	public void setActionTool(int index,
			com.iver.andami.plugins.config.generate.ActionTool vActionTool)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _actionToolList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_actionToolList.setElementAt(vActionTool, index);
	} // -- void setActionTool(int,
		// com.iver.andami.plugins.config.generate.ActionTool)

	/**
	 * Method setActionTool
	 * 
	 * @param actionToolArray
	 */
	public void setActionTool(
			com.iver.andami.plugins.config.generate.ActionTool[] actionToolArray) {
		// -- copy array
		_actionToolList.removeAllElements();
		for (int i = 0; i < actionToolArray.length; i++) {
			_actionToolList.addElement(actionToolArray[i]);
		}
	} // -- void
		// setActionTool(com.iver.andami.plugins.config.generate.ActionTool)

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
	 * Sets the value of field 'isVisible'.
	 * 
	 * @param isVisible
	 *            the value of field 'isVisible'.
	 */
	public void setIsVisible(boolean isVisible) {
		this._isVisible = isVisible;
		this._has_isVisible = true;
	} // -- void setIsVisible(boolean)

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
	public void setPosition(int position) {
		this._position = position;
		this._has_position = true;
	} // -- void setPosition(int)

	/**
	 * Method setSelectableTool
	 * 
	 * @param index
	 * @param vSelectableTool
	 */
	public void setSelectableTool(
			int index,
			com.iver.andami.plugins.config.generate.SelectableTool vSelectableTool)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _selectableToolList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_selectableToolList.setElementAt(vSelectableTool, index);
	} // -- void setSelectableTool(int,
		// com.iver.andami.plugins.config.generate.SelectableTool)

	/**
	 * Method setSelectableTool
	 * 
	 * @param selectableToolArray
	 */
	public void setSelectableTool(
			com.iver.andami.plugins.config.generate.SelectableTool[] selectableToolArray) {
		// -- copy array
		_selectableToolList.removeAllElements();
		for (int i = 0; i < selectableToolArray.length; i++) {
			_selectableToolList.addElement(selectableToolArray[i]);
		}
	} // -- void
		// setSelectableTool(com.iver.andami.plugins.config.generate.SelectableTool)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ToolBar) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ToolBar.class,
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
