/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PluginDescriptor.java 13855 2007-09-19 15:36:36Z jaume $
 */

package com.iver.andami.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.BooleanValidator;
import org.exolab.castor.xml.validators.NameValidator;

/**
 * Class PluginDescriptor.
 * 
 * @version $Revision: 13855 $ $Date: 2007-09-19 17:36:36 +0200 (Wed, 19 Sep
 *          2007) $
 */
public class PluginDescriptor extends
		org.exolab.castor.xml.util.XMLClassDescriptorImpl {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field nsPrefix
	 */
	private java.lang.String nsPrefix;

	/**
	 * Field nsURI
	 */
	private java.lang.String nsURI;

	/**
	 * Field xmlName
	 */
	private java.lang.String xmlName;

	/**
	 * Field identity
	 */
	private org.exolab.castor.xml.XMLFieldDescriptor identity;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public PluginDescriptor() {
		super();
		xmlName = "plugin";
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _name
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_name", "name",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				Plugin target = (Plugin) object;
				return target.getName();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					Plugin target = (Plugin) object;
					target.setName((java.lang.String) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new java.lang.String();
			}
		});
		desc.setHandler(handler);
		desc.setRequired(true);
		addFieldDescriptor(desc);

		// -- validation code for: _name
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			NameValidator typeValidator = new NameValidator(
					NameValidator.NMTOKEN);
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _update
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Boolean.TYPE, "_update", "update",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				Plugin target = (Plugin) object;
				if (!target.hasUpdate())
					return null;
				return (target.getUpdate() ? java.lang.Boolean.TRUE
						: java.lang.Boolean.FALSE);
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					Plugin target = (Plugin) object;
					// ignore null values for non optional primitives
					if (value == null)
						return;

					target.setUpdate(((java.lang.Boolean) value).booleanValue());
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return null;
			}
		});
		desc.setHandler(handler);
		desc.setRequired(true);
		addFieldDescriptor(desc);

		// -- validation code for: _update
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			BooleanValidator typeValidator = new BooleanValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

	} // -- com.iver.andami.config.generate.PluginDescriptor()

	// -----------/
	// - Methods -/
	// -----------/

	/**
	 * Method getAccessMode
	 */
	public org.exolab.castor.mapping.AccessMode getAccessMode() {
		return null;
	} // -- org.exolab.castor.mapping.AccessMode getAccessMode()

	/**
	 * Method getExtends
	 */
	public org.exolab.castor.mapping.ClassDescriptor getExtends() {
		return null;
	} // -- org.exolab.castor.mapping.ClassDescriptor getExtends()

	/**
	 * Method getIdentity
	 */
	public org.exolab.castor.mapping.FieldDescriptor getIdentity() {
		return identity;
	} // -- org.exolab.castor.mapping.FieldDescriptor getIdentity()

	/**
	 * Method getJavaClass
	 */
	public java.lang.Class getJavaClass() {
		return com.iver.andami.config.generate.Plugin.class;
	} // -- java.lang.Class getJavaClass()

	/**
	 * Method getNameSpacePrefix
	 */
	public java.lang.String getNameSpacePrefix() {
		return nsPrefix;
	} // -- java.lang.String getNameSpacePrefix()

	/**
	 * Method getNameSpaceURI
	 */
	public java.lang.String getNameSpaceURI() {
		return nsURI;
	} // -- java.lang.String getNameSpaceURI()

	/**
	 * Method getValidator
	 */
	public org.exolab.castor.xml.TypeValidator getValidator() {
		return this;
	} // -- org.exolab.castor.xml.TypeValidator getValidator()

	/**
	 * Method getXMLName
	 */
	public java.lang.String getXMLName() {
		return xmlName;
	} // -- java.lang.String getXMLName()

}
