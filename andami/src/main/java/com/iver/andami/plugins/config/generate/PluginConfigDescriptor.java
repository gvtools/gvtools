/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: PluginConfigDescriptor.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class PluginConfigDescriptor.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class PluginConfigDescriptor extends
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

	public PluginConfigDescriptor() {
		super();
		xmlName = "plugin-config";

		// -- set grouping compositor
		setCompositorAsSequence();
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _updateUrl
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_updateUrl", "update-url",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getUpdateUrl();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setUpdateUrl((java.lang.String) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return null;
			}
		});
		desc.setHandler(handler);
		addFieldDescriptor(desc);

		// -- validation code for: _updateUrl
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

		// -- _icon
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.Icon.class, "_icon",
				"icon", org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getIcon();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setIcon((com.iver.andami.plugins.config.generate.Icon) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.Icon();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(false);
		addFieldDescriptor(desc);

		// -- validation code for: _icon
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _dependsList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.Depends.class,
				"_dependsList", "depends",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getDepends();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.addDepends((com.iver.andami.plugins.config.generate.Depends) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.Depends();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _dependsList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _resourceBundle
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.ResourceBundle.class,
				"_resourceBundle", "resourceBundle",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getResourceBundle();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setResourceBundle((com.iver.andami.plugins.config.generate.ResourceBundle) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.ResourceBundle();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(false);
		addFieldDescriptor(desc);

		// -- validation code for: _resourceBundle
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _labelSetList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.LabelSet.class,
				"_labelSetList", "label-set",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getLabelSet();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.addLabelSet((com.iver.andami.plugins.config.generate.LabelSet) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.LabelSet();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _labelSetList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _libraries
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.Libraries.class,
				"_libraries", "libraries",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getLibraries();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setLibraries((com.iver.andami.plugins.config.generate.Libraries) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.Libraries();
			}
		});
		desc.setHandler(handler);
		desc.setRequired(true);
		desc.setMultivalued(false);
		addFieldDescriptor(desc);

		// -- validation code for: _libraries
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _popupMenus
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.PopupMenus.class,
				"_popupMenus", "popup-menus",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getPopupMenus();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setPopupMenus((com.iver.andami.plugins.config.generate.PopupMenus) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.PopupMenus();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(false);
		addFieldDescriptor(desc);

		// -- validation code for: _popupMenus
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _extensions
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.Extensions.class,
				"_extensions", "extensions",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				PluginConfig target = (PluginConfig) object;
				return target.getExtensions();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					PluginConfig target = (PluginConfig) object;
					target.setExtensions((com.iver.andami.plugins.config.generate.Extensions) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.Extensions();
			}
		});
		desc.setHandler(handler);
		desc.setRequired(true);
		desc.setMultivalued(false);
		addFieldDescriptor(desc);

		// -- validation code for: _extensions
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
	} // -- com.iver.andami.plugins.config.generate.PluginConfigDescriptor()

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
		return com.iver.andami.plugins.config.generate.PluginConfig.class;
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
