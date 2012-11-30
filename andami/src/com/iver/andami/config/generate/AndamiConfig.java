/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: AndamiConfig.java 13855 2007-09-19 15:36:36Z jaume $
 */

package com.iver.andami.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Vector;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class AndamiConfig.
 * 
 * @version $Revision: 13855 $ $Date: 2007-09-19 17:36:36 +0200 (Wed, 19 Sep
 *          2007) $
 */
public class AndamiConfig implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _localeLanguage
	 */
	private java.lang.String _localeLanguage;

	/**
	 * Field _localeCountry
	 */
	private java.lang.String _localeCountry;

	/**
	 * Field _localeVariant
	 */
	private java.lang.String _localeVariant;

	/**
	 * Field _lookAndFeel
	 */
	private java.lang.String _lookAndFeel;

	/**
	 * Field _pluginsDirectory
	 */
	private java.lang.String _pluginsDirectory;

	/**
	 * Field _andami
	 */
	private com.iver.andami.config.generate.Andami _andami;

	/**
	 * Field _pluginList
	 */
	private java.util.Vector _pluginList;

	/**
	 * Field _andamiOptions
	 */
	private com.iver.andami.config.generate.AndamiOptions _andamiOptions;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public AndamiConfig() {
		super();
		_pluginList = new Vector();
	} // -- com.iver.andami.config.generate.AndamiConfig()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method addPlugin
	 * 
	 * @param vPlugin
	 */
	public void addPlugin(com.iver.andami.config.generate.Plugin vPlugin)
			throws java.lang.IndexOutOfBoundsException {
		_pluginList.addElement(vPlugin);
	} // -- void addPlugin(com.iver.andami.config.generate.Plugin)

	/**
	 * Method addPlugin
	 * 
	 * @param index
	 * @param vPlugin
	 */
	public void addPlugin(int index,
			com.iver.andami.config.generate.Plugin vPlugin)
			throws java.lang.IndexOutOfBoundsException {
		_pluginList.insertElementAt(vPlugin, index);
	} // -- void addPlugin(int, com.iver.andami.config.generate.Plugin)

	/**
	 * Method enumeratePlugin
	 */
	public java.util.Enumeration enumeratePlugin() {
		return _pluginList.elements();
	} // -- java.util.Enumeration enumeratePlugin()

	/**
	 * Returns the value of field 'andami'.
	 * 
	 * @return the value of field 'andami'.
	 */
	public com.iver.andami.config.generate.Andami getAndami() {
		return this._andami;
	} // -- com.iver.andami.config.generate.Andami getAndami()

	/**
	 * Returns the value of field 'andamiOptions'.
	 * 
	 * @return the value of field 'andamiOptions'.
	 */
	public com.iver.andami.config.generate.AndamiOptions getAndamiOptions() {
		return this._andamiOptions;
	} // -- com.iver.andami.config.generate.AndamiOptions getAndamiOptions()

	/**
	 * Returns the value of field 'localeCountry'.
	 * 
	 * @return the value of field 'localeCountry'.
	 */
	public java.lang.String getLocaleCountry() {
		return this._localeCountry;
	} // -- java.lang.String getLocaleCountry()

	/**
	 * Returns the value of field 'localeLanguage'.
	 * 
	 * @return the value of field 'localeLanguage'.
	 */
	public java.lang.String getLocaleLanguage() {
		return this._localeLanguage;
	} // -- java.lang.String getLocaleLanguage()

	/**
	 * Returns the value of field 'localeVariant'.
	 * 
	 * @return the value of field 'localeVariant'.
	 */
	public java.lang.String getLocaleVariant() {
		return this._localeVariant;
	} // -- java.lang.String getLocaleVariant()

	/**
	 * Returns the value of field 'lookAndFeel'.
	 * 
	 * @return the value of field 'lookAndFeel'.
	 */
	public java.lang.String getLookAndFeel() {
		return this._lookAndFeel;
	} // -- java.lang.String getLookAndFeel()

	/**
	 * Method getPlugin
	 * 
	 * @param index
	 */
	public com.iver.andami.config.generate.Plugin getPlugin(int index)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _pluginList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.iver.andami.config.generate.Plugin) _pluginList
				.elementAt(index);
	} // -- com.iver.andami.config.generate.Plugin getPlugin(int)

	/**
	 * Method getPlugin
	 */
	public com.iver.andami.config.generate.Plugin[] getPlugin() {
		int size = _pluginList.size();
		com.iver.andami.config.generate.Plugin[] mArray = new com.iver.andami.config.generate.Plugin[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.iver.andami.config.generate.Plugin) _pluginList
					.elementAt(index);
		}
		return mArray;
	} // -- com.iver.andami.config.generate.Plugin[] getPlugin()

	/**
	 * Method getPluginCount
	 */
	public int getPluginCount() {
		return _pluginList.size();
	} // -- int getPluginCount()

	/**
	 * Returns the value of field 'pluginsDirectory'.
	 * 
	 * @return the value of field 'pluginsDirectory'.
	 */
	public java.lang.String getPluginsDirectory() {
		return this._pluginsDirectory;
	} // -- java.lang.String getPluginsDirectory()

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
	 * Method removeAllPlugin
	 */
	public void removeAllPlugin() {
		_pluginList.removeAllElements();
	} // -- void removeAllPlugin()

	/**
	 * Method removePlugin
	 * 
	 * @param index
	 */
	public com.iver.andami.config.generate.Plugin removePlugin(int index) {
		java.lang.Object obj = _pluginList.elementAt(index);
		_pluginList.removeElementAt(index);
		return (com.iver.andami.config.generate.Plugin) obj;
	} // -- com.iver.andami.config.generate.Plugin removePlugin(int)

	/**
	 * Sets the value of field 'andami'.
	 * 
	 * @param andami
	 *            the value of field 'andami'.
	 */
	public void setAndami(com.iver.andami.config.generate.Andami andami) {
		this._andami = andami;
	} // -- void setAndami(com.iver.andami.config.generate.Andami)

	/**
	 * Sets the value of field 'andamiOptions'.
	 * 
	 * @param andamiOptions
	 *            the value of field 'andamiOptions'.
	 */
	public void setAndamiOptions(
			com.iver.andami.config.generate.AndamiOptions andamiOptions) {
		this._andamiOptions = andamiOptions;
	} // -- void setAndamiOptions(com.iver.andami.config.generate.AndamiOptions)

	/**
	 * Sets the value of field 'localeCountry'.
	 * 
	 * @param localeCountry
	 *            the value of field 'localeCountry'.
	 */
	public void setLocaleCountry(java.lang.String localeCountry) {
		this._localeCountry = localeCountry;
	} // -- void setLocaleCountry(java.lang.String)

	/**
	 * Sets the value of field 'localeLanguage'.
	 * 
	 * @param localeLanguage
	 *            the value of field 'localeLanguage'.
	 */
	public void setLocaleLanguage(java.lang.String localeLanguage) {
		this._localeLanguage = localeLanguage;
	} // -- void setLocaleLanguage(java.lang.String)

	/**
	 * Sets the value of field 'localeVariant'.
	 * 
	 * @param localeVariant
	 *            the value of field 'localeVariant'.
	 */
	public void setLocaleVariant(java.lang.String localeVariant) {
		this._localeVariant = localeVariant;
	} // -- void setLocaleVariant(java.lang.String)

	/**
	 * Sets the value of field 'lookAndFeel'.
	 * 
	 * @param lookAndFeel
	 *            the value of field 'lookAndFeel'.
	 */
	public void setLookAndFeel(java.lang.String lookAndFeel) {
		this._lookAndFeel = lookAndFeel;
	} // -- void setLookAndFeel(java.lang.String)

	/**
	 * Method setPlugin
	 * 
	 * @param index
	 * @param vPlugin
	 */
	public void setPlugin(int index,
			com.iver.andami.config.generate.Plugin vPlugin)
			throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _pluginList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_pluginList.setElementAt(vPlugin, index);
	} // -- void setPlugin(int, com.iver.andami.config.generate.Plugin)

	/**
	 * Method setPlugin
	 * 
	 * @param pluginArray
	 */
	public void setPlugin(com.iver.andami.config.generate.Plugin[] pluginArray) {
		// -- copy array
		_pluginList.removeAllElements();
		for (int i = 0; i < pluginArray.length; i++) {
			_pluginList.addElement(pluginArray[i]);
		}
	} // -- void setPlugin(com.iver.andami.config.generate.Plugin)

	/**
	 * Sets the value of field 'pluginsDirectory'.
	 * 
	 * @param pluginsDirectory
	 *            the value of field 'pluginsDirectory'
	 */
	public void setPluginsDirectory(java.lang.String pluginsDirectory) {
		this._pluginsDirectory = pluginsDirectory;
	} // -- void setPluginsDirectory(java.lang.String)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.config.generate.AndamiConfig) Unmarshaller
				.unmarshal(com.iver.andami.config.generate.AndamiConfig.class,
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
