/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: SkinExtensionTypeDescriptor.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.NameValidator;

/**
 * Class SkinExtensionTypeDescriptor.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class SkinExtensionTypeDescriptor extends
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

	public SkinExtensionTypeDescriptor() {
		super();
		xmlName = "skin-extension-type";

		// -- set grouping compositor
		setCompositorAsSequence();
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _className
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_className", "class-name",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SkinExtensionType target = (SkinExtensionType) object;
				return target.getClassName();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SkinExtensionType target = (SkinExtensionType) object;
					target.setClassName((java.lang.String) value);
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

		// -- validation code for: _className
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			NameValidator typeValidator = new NameValidator(
					NameValidator.NMTOKEN);
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

		// -- _menuList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.Menu.class,
				"_menuList", "menu", org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SkinExtensionType target = (SkinExtensionType) object;
				return target.getMenu();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SkinExtensionType target = (SkinExtensionType) object;
					target.addMenu((com.iver.andami.plugins.config.generate.Menu) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.Menu();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _menuList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _toolBarList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.ToolBar.class,
				"_toolBarList", "tool-bar",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SkinExtensionType target = (SkinExtensionType) object;
				return target.getToolBar();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SkinExtensionType target = (SkinExtensionType) object;
					target.addToolBar((com.iver.andami.plugins.config.generate.ToolBar) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.ToolBar();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _toolBarList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _comboButtonList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.ComboButton.class,
				"_comboButtonList", "combo-button",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SkinExtensionType target = (SkinExtensionType) object;
				return target.getComboButton();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SkinExtensionType target = (SkinExtensionType) object;
					target.addComboButton((com.iver.andami.plugins.config.generate.ComboButton) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.ComboButton();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _comboButtonList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _comboScaleList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.ComboScale.class,
				"_comboScaleList", "combo-scale",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SkinExtensionType target = (SkinExtensionType) object;
				return target.getComboScale();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SkinExtensionType target = (SkinExtensionType) object;
					target.addComboScale((com.iver.andami.plugins.config.generate.ComboScale) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.ComboScale();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _comboScaleList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
	} // --
		// com.iver.andami.plugins.config.generate.SkinExtensionTypeDescriptor()

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
		return com.iver.andami.plugins.config.generate.SkinExtensionType.class;
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
