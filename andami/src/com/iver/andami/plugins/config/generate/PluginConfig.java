/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PluginConfig.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PluginConfig.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class PluginConfig implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _updateUrl
	 */
	private java.lang.String _updateUrl;

	/**
	 * Field _icon
	 */
	private com.iver.andami.plugins.config.generate.Icon _icon;

	/**
	 * Field _dependsList
	 */
	private java.util.Vector _dependsList;

	/**
	 * Field _resourceBundle
	 */
	private com.iver.andami.plugins.config.generate.ResourceBundle _resourceBundle;

	/**
	 * Field _labelSetList
	 */
	private java.util.Vector _labelSetList;

	/**
	 * Field _libraries
	 */
	private com.iver.andami.plugins.config.generate.Libraries _libraries;

	/**
	 * Field _popupMenus
	 */
	private com.iver.andami.plugins.config.generate.PopupMenus _popupMenus;

	/**
	 * Field _extensions
	 */
	private com.iver.andami.plugins.config.generate.Extensions _extensions;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public PluginConfig() {
		super();
		_dependsList = new Vector();
		_labelSetList = new Vector();
	} // -- com.iver.andami.plugins.config.generate.PluginConfig()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addDepends
	 * 
	 * @param vDepends
	 */
	public void addDepends(
			com.iver.andami.plugins.config.generate.Depends vDepends)
			throws java.lang.IndexOutOfBoundsException {
		_dependsList.addElement(vDepends);
	} // -- void addDepends(com.iver.andami.plugins.config.generate.Depends)

	/**
	 * Method addDepends
	 * 
	 * @param index
	 * @param vDepends
	 */
	public void addDepends(int index,
			com.iver.andami.plugins.config.generate.Depends vDepends)
			throws java.lang.IndexOutOfBoundsException {
		_dependsList.insertElementAt(vDepends, index);
	} // -- void addDepends(int,
		// com.iver.andami.plugins.config.generate.Depends)

	/**
	 * Method addLabelSet
	 * 
	 * @param vLabelSet
	 */
	public void addLabelSet(
			com.iver.andami.plugins.config.generate.LabelSet vLabelSet)
			throws java.lang.IndexOutOfBoundsException {
		_labelSetList.addElement(vLabelSet);
	} // -- void addLabelSet(com.iver.andami.plugins.config.generate.LabelSet)

	/**
	 * Method addLabelSet
	 * 
	 * @param index
	 * @param vLabelSet
	 */
	public void addLabelSet(int index,
			com.iver.andami.plugins.config.generate.LabelSet vLabelSet)
			throws java.lang.IndexOutOfBoundsException {
		_labelSetList.insertElementAt(vLabelSet, index);
	} // -- void addLabelSet(int,
		// com.iver.andami.plugins.config.generate.LabelSet)

	/**
	 * Method enumerateDepends
	 */
	public java.util.Enumeration enumerateDepends() {
		return _dependsList.elements();
	} // -- java.util.Enumeration enumerateDepends()

	/**
	 * Method enumerateLabelSet
	 */
	public java.util.Enumeration enumerateLabelSet() {
		return _labelSetList.elements();
	} // -- java.util.Enumeration enumerateLabelSet()

	/**
	 * Method getDepends
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Depends getDepends(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _dependsList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.Depends) _dependsList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.Depends getDepends(int)

	/**
	 * Method getDepends
	 */
	public com.iver.andami.plugins.config.generate.Depends[] getDepends() {
		int size = _dependsList.size();
		com.iver.andami.plugins.config.generate.Depends[] mArray = new com.iver.andami.plugins.config.generate.Depends[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.Depends) _dependsList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.Depends[] getDepends()

	/**
	 * Method getDependsCount
	 */
	public int getDependsCount() {
		return _dependsList.size();
	} // -- int getDependsCount()

	/**
	 * Returns the value of field 'extensions'.
	 * 
	 * @return the value of field 'extensions'.
	 */
	public com.iver.andami.plugins.config.generate.Extensions getExtensions() {
		return this._extensions;
	} // -- com.iver.andami.plugins.config.generate.Extensions getExtensions()

	/**
	 * Returns the value of field 'icon'.
	 * 
	 * @return the value of field 'icon'.
	 */
	public com.iver.andami.plugins.config.generate.Icon getIcon() {
		return this._icon;
	} // -- com.iver.andami.plugins.config.generate.Icon getIcon()

	/**
	 * Method getLabelSet
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.LabelSet getLabelSet(
			int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _labelSetList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.plugins.config.generate.LabelSet) _labelSetList
				.elementAt(index);
	} // -- com.iver.andami.plugins.config.generate.LabelSet getLabelSet(int)

	/**
	 * Method getLabelSet
	 */
	public com.iver.andami.plugins.config.generate.LabelSet[] getLabelSet() {
		int size = _labelSetList.size();
		com.iver.andami.plugins.config.generate.LabelSet[] mArray = new com.iver.andami.plugins.config.generate.LabelSet[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.plugins.config.generate.LabelSet) _labelSetList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.plugins.config.generate.LabelSet[] getLabelSet()

	/**
	 * Method getLabelSetCount
	 */
	public int getLabelSetCount() {
		return _labelSetList.size();
	} // -- int getLabelSetCount()

	/**
	 * Returns the value of field 'libraries'.
	 * 
	 * @return the value of field 'libraries'.
	 */
	public com.iver.andami.plugins.config.generate.Libraries getLibraries() {
		return this._libraries;
	} // -- com.iver.andami.plugins.config.generate.Libraries getLibraries()

	/**
	 * Returns the value of field 'popupMenus'.
	 * 
	 * @return the value of field 'popupMenus'.
	 */
	public com.iver.andami.plugins.config.generate.PopupMenus getPopupMenus() {
		return this._popupMenus;
	} // -- com.iver.andami.plugins.config.generate.PopupMenus getPopupMenus()

	/**
	 * Returns the value of field 'resourceBundle'.
	 * 
	 * @return the value of field 'resourceBundle'.
	 */
	public com.iver.andami.plugins.config.generate.ResourceBundle getResourceBundle() {
		return this._resourceBundle;
	} // -- com.iver.andami.plugins.config.generate.ResourceBundle
		// getResourceBundle()

	/**
	 * Returns the value of field 'updateUrl'.
	 * 
	 * @return the value of field 'updateUrl'.
	 */
	public java.lang.String getUpdateUrl() {
		return this._updateUrl;
	} // -- java.lang.String getUpdateUrl()

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
	 * Method removeAllDepends
	 */
	public void removeAllDepends() {
		_dependsList.removeAllElements();
	} // -- void removeAllDepends()

	/**
	 * Method removeAllLabelSet
	 */
	public void removeAllLabelSet() {
		_labelSetList.removeAllElements();
	} // -- void removeAllLabelSet()

	/**
	 * Method removeDepends
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.Depends removeDepends(
			int index) {
		java.lang.Object obj = _dependsList.elementAt(index);
		_dependsList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.Depends) obj;
	} // -- com.iver.andami.plugins.config.generate.Depends removeDepends(int)

	/**
	 * Method removeLabelSet
	 * 
	 * @param index
	 */
	public com.iver.andami.plugins.config.generate.LabelSet removeLabelSet(
			int index) {
		java.lang.Object obj = _labelSetList.elementAt(index);
		_labelSetList.removeElementAt(index);
		return (com.iver.andami.plugins.config.generate.LabelSet) obj;
	} // -- com.iver.andami.plugins.config.generate.LabelSet removeLabelSet(int)

	/**
	 * Method setDepends
	 * 
	 * @param index
	 * @param vDepends
	 */
	public void setDepends(int index,
			com.iver.andami.plugins.config.generate.Depends vDepends)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _dependsList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_dependsList.setElementAt(vDepends, index);
	} // -- void setDepends(int,
		// com.iver.andami.plugins.config.generate.Depends)

	/**
	 * Method setDepends
	 * 
	 * @param dependsArray
	 */
	public void setDepends(
			com.iver.andami.plugins.config.generate.Depends[] dependsArray) {
		// -- copy array
		_dependsList.removeAllElements();
		for (int i = 0; i < dependsArray.length; i++) {
			_dependsList.addElement(dependsArray[i]);
		}
	} // -- void setDepends(com.iver.andami.plugins.config.generate.Depends)

	/**
	 * Sets the value of field 'extensions'.
	 * 
	 * @param extensions
	 *            the value of field 'extensions'.
	 */
	public void setExtensions(
			com.iver.andami.plugins.config.generate.Extensions extensions) {
		this._extensions = extensions;
	} // -- void
		// setExtensions(com.iver.andami.plugins.config.generate.Extensions)

	/**
	 * Sets the value of field 'icon'.
	 * 
	 * @param icon
	 *            the value of field 'icon'.
	 */
	public void setIcon(com.iver.andami.plugins.config.generate.Icon icon) {
		this._icon = icon;
	} // -- void setIcon(com.iver.andami.plugins.config.generate.Icon)

	/**
	 * Method setLabelSet
	 * 
	 * @param index
	 * @param vLabelSet
	 */
	public void setLabelSet(int index,
			com.iver.andami.plugins.config.generate.LabelSet vLabelSet)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _labelSetList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_labelSetList.setElementAt(vLabelSet, index);
	} // -- void setLabelSet(int,
		// com.iver.andami.plugins.config.generate.LabelSet)

	/**
	 * Method setLabelSet
	 * 
	 * @param labelSetArray
	 */
	public void setLabelSet(
			com.iver.andami.plugins.config.generate.LabelSet[] labelSetArray) {
		// -- copy array
		_labelSetList.removeAllElements();
		for (int i = 0; i < labelSetArray.length; i++) {
			_labelSetList.addElement(labelSetArray[i]);
		}
	} // -- void setLabelSet(com.iver.andami.plugins.config.generate.LabelSet)

	/**
	 * Sets the value of field 'libraries'.
	 * 
	 * @param libraries
	 *            the value of field 'libraries'.
	 */
	public void setLibraries(
			com.iver.andami.plugins.config.generate.Libraries libraries) {
		this._libraries = libraries;
	} // -- void setLibraries(com.iver.andami.plugins.config.generate.Libraries)

	/**
	 * Sets the value of field 'popupMenus'.
	 * 
	 * @param popupMenus
	 *            the value of field 'popupMenus'.
	 */
	public void setPopupMenus(
			com.iver.andami.plugins.config.generate.PopupMenus popupMenus) {
		this._popupMenus = popupMenus;
	} // -- void
		// setPopupMenus(com.iver.andami.plugins.config.generate.PopupMenus)

	/**
	 * Sets the value of field 'resourceBundle'.
	 * 
	 * @param resourceBundle
	 *            the value of field 'resourceBundle'.
	 */
	public void setResourceBundle(
			com.iver.andami.plugins.config.generate.ResourceBundle resourceBundle) {
		this._resourceBundle = resourceBundle;
	} // -- void
		// setResourceBundle(com.iver.andami.plugins.config.generate.ResourceBundle)

	/**
	 * Sets the value of field 'updateUrl'.
	 * 
	 * @param updateUrl
	 *            the value of field 'updateUrl'.
	 */
	public void setUpdateUrl(java.lang.String updateUrl) {
		this._updateUrl = updateUrl;
	} // -- void setUpdateUrl(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.PluginConfig) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.PluginConfig.class,
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
