/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: SelectableToolDescriptor.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.BooleanValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class SelectableToolDescriptor.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class SelectableToolDescriptor extends
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

	public SelectableToolDescriptor() {
		super();
		xmlName = "selectable-tool";
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _text
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_text", "text",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getText();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setText((java.lang.String) value);
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

		// -- validation code for: _text
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _name
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_name", "name",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getName();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setName((java.lang.String) value);
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

		// -- validation code for: _name
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _actionCommand
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_actionCommand", "action-command",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getActionCommand();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setActionCommand((java.lang.String) value);
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

		// -- validation code for: _actionCommand
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _isDefault
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Boolean.TYPE, "_isDefault", "is-default",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				if (!target.hasIsDefault())
					return null;
				return (target.getIsDefault() ? java.lang.Boolean.TRUE
						: java.lang.Boolean.FALSE);
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					// if null, use delete method for optional primitives
					if (value == null) {
						target.deleteIsDefault();
						return;
					}
					target.setIsDefault(((java.lang.Boolean) value)
							.booleanValue());
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

		// -- validation code for: _isDefault
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			BooleanValidator typeValidator = new BooleanValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _last
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Boolean.TYPE, "_last", "last",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				if (!target.hasLast())
					return null;
				return (target.getLast() ? java.lang.Boolean.TRUE
						: java.lang.Boolean.FALSE);
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					// if null, use delete method for optional primitives
					if (value == null) {
						target.deleteLast();
						return;
					}
					target.setLast(((java.lang.Boolean) value).booleanValue());
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

		// -- validation code for: _last
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			BooleanValidator typeValidator = new BooleanValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _icon
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_icon", "icon",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getIcon();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setIcon((java.lang.String) value);
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

		// -- validation code for: _icon
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _tooltip
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_tooltip", "tooltip",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getTooltip();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setTooltip((java.lang.String) value);
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

		// -- validation code for: _tooltip
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _enableText
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_enableText", "enable-text",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getEnableText();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setEnableText((java.lang.String) value);
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

		// -- validation code for: _enableText
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _group
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_group", "group",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				return target.getGroup();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					target.setGroup((java.lang.String) value);
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

		// -- validation code for: _group
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			StringValidator typeValidator = new StringValidator();
			typeValidator.setWhiteSpace("preserve");
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- _position
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Integer.TYPE, "_position", "position",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				SelectableTool target = (SelectableTool) object;
				if (!target.hasPosition())
					return null;
				return new java.lang.Integer(target.getPosition());
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					SelectableTool target = (SelectableTool) object;
					// if null, use delete method for optional primitives
					if (value == null) {
						target.deletePosition();
						return;
					}
					target.setPosition(((java.lang.Integer) value).intValue());
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

		// -- validation code for: _position
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			IntegerValidator typeValidator = new IntegerValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

	} // -- com.iver.andami.plugins.config.generate.SelectableToolDescriptor()

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
		return com.iver.andami.plugins.config.generate.SelectableTool.class;
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
