/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ExtensionType.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ExtensionType.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ExtensionType extends
		com.iver.andami.plugins.config.generate.SkinExtensionType implements
		java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _priority
	 */
	private int _priority;

	/**
	 * keeps track of state for field: _priority
	 */
	private boolean _has_priority;

	/**
	 * Field _active
	 */
	private boolean _active;

	/**
	 * keeps track of state for field: _active
	 */
	private boolean _has_active;

	/**
	 * Field _description
	 */
	private java.lang.String _description;

	/**
	 * Field _alwaysvisible
	 */
	private boolean _alwaysvisible;

	/**
	 * keeps track of state for field: _alwaysvisible
	 */
	private boolean _has_alwaysvisible;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public ExtensionType() {
		super();
	} // -- com.iver.andami.plugins.config.generate.ExtensionType()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method deleteActive
	 */
	public void deleteActive() {
		this._has_active = false;
	} // -- void deleteActive()

	/**
	 * Method deleteAlwaysvisible
	 */
	public void deleteAlwaysvisible() {
		this._has_alwaysvisible = false;
	} // -- void deleteAlwaysvisible()

	/**
	 * Method deletePriority
	 */
	public void deletePriority() {
		this._has_priority = false;
	} // -- void deletePriority()

	/**
	 * Returns the value of field 'active'.
	 * 
	 * @return the value of field 'active'.
	 */
	public boolean getActive() {
		return this._active;
	} // -- boolean getActive()

	/**
	 * Returns the value of field 'alwaysvisible'.
	 * 
	 * @return the value of field 'alwaysvisible'.
	 */
	public boolean getAlwaysvisible() {
		return this._alwaysvisible;
	} // -- boolean getAlwaysvisible()

	/**
	 * Returns the value of field 'description'.
	 * 
	 * @return the value of field 'description'.
	 */
	public java.lang.String getDescription() {
		return this._description;
	} // -- java.lang.String getDescription()

	/**
	 * Returns the value of field 'priority'.
	 * 
	 * @return the value of field 'priority'.
	 */
	public int getPriority() {
		return this._priority;
	} // -- int getPriority()

	/**
	 * Method hasActive
	 */
	public boolean hasActive() {
		return this._has_active;
	} // -- boolean hasActive()

	/**
	 * Method hasAlwaysvisible
	 */
	public boolean hasAlwaysvisible() {
		return this._has_alwaysvisible;
	} // -- boolean hasAlwaysvisible()

	/**
	 * Method hasPriority
	 */
	public boolean hasPriority() {
		return this._has_priority;
	} // -- boolean hasPriority()

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
	 * Sets the value of field 'active'.
	 * 
	 * @param active
	 *            the value of field 'active'.
	 */
	public void setActive(boolean active) {
		this._active = active;
		this._has_active = true;
	} // -- void setActive(boolean)

	/**
	 * Sets the value of field 'alwaysvisible'.
	 * 
	 * @param alwaysvisible
	 *            the value of field 'alwaysvisible'.
	 */
	public void setAlwaysvisible(boolean alwaysvisible) {
		this._alwaysvisible = alwaysvisible;
		this._has_alwaysvisible = true;
	} // -- void setAlwaysvisible(boolean)

	/**
	 * Sets the value of field 'description'.
	 * 
	 * @param description
	 *            the value of field 'description'.
	 */
	public void setDescription(java.lang.String description) {
		this._description = description;
	} // -- void setDescription(java.lang.String)

	/**
	 * Sets the value of field 'priority'.
	 * 
	 * @param priority
	 *            the value of field 'priority'.
	 */
	public void setPriority(int priority) {
		this._priority = priority;
		this._has_priority = true;
	} // -- void setPriority(int)

	/**
	 * Method unmarshal
	 * 
	 * @param reader
	 */
	public static java.lang.Object unmarshal(java.io.Reader reader)
			throws org.exolab.castor.xml.MarshalException,
			org.exolab.castor.xml.ValidationException {
		return (com.iver.andami.plugins.config.generate.ExtensionType) Unmarshaller
				.unmarshal(
						com.iver.andami.plugins.config.generate.ExtensionType.class,
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
