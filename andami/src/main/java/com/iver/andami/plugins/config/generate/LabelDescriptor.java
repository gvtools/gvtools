/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: LabelDescriptor.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.NameValidator;

/**
 * Class LabelDescriptor.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class LabelDescriptor extends
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

	public LabelDescriptor() {
		super();
		xmlName = "label";
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _size
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Integer.TYPE, "_size", "size",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				Label target = (Label) object;
				if (!target.hasSize())
					return null;
				return new java.lang.Integer(target.getSize());
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					Label target = (Label) object;
					// ignore null values for non optional primitives
					if (value == null)
						return;

					target.setSize(((java.lang.Integer) value).intValue());
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

		// -- validation code for: _size
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			IntegerValidator typeValidator = new IntegerValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _id
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_id", "id",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				Label target = (Label) object;
				return target.getId();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					Label target = (Label) object;
					target.setId((java.lang.String) value);
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

		// -- validation code for: _id
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			NameValidator typeValidator = new NameValidator(
					NameValidator.NMTOKEN);
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

	} // -- com.iver.andami.plugins.config.generate.LabelDescriptor()

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
		return com.iver.andami.plugins.config.generate.Label.class;
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
