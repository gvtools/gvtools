/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ToolBarDescriptor.java 15983 2007-11-07 11:11:19Z vcaballero $
 */

package com.iver.andami.plugins.config.generate;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.BooleanValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class ToolBarDescriptor.
 * 
 * @version $Revision: 15983 $ $Date: 2007-11-07 12:11:19 +0100 (Wed, 07 Nov
 *          2007) $
 */
public class ToolBarDescriptor extends
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

	public ToolBarDescriptor() {
		super();
		xmlName = "tool-bar";

		// -- set grouping compositor
		setCompositorAsSequence();
		org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
		org.exolab.castor.xml.XMLFieldHandler handler = null;
		org.exolab.castor.xml.FieldValidator fieldValidator = null;
		// -- initialize attribute descriptors

		// -- _name
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.String.class, "_name", "name",
				org.exolab.castor.xml.NodeType.Attribute);
		desc.setImmutable(true);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				ToolBar target = (ToolBar) object;
				return target.getName();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
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
		desc.setRequired(true);
		addFieldDescriptor(desc);

		// -- validation code for: _name
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(1);
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
				ToolBar target = (ToolBar) object;
				if (!target.hasPosition())
					return null;
				return new java.lang.Integer(target.getPosition());
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
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
		// -- _isVisible
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				java.lang.Boolean.TYPE, "_isVisible", "is-visible",
				org.exolab.castor.xml.NodeType.Attribute);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				ToolBar target = (ToolBar) object;
				if (!target.hasIsVisible())
					return null;
				return (target.getIsVisible() ? java.lang.Boolean.TRUE
						: java.lang.Boolean.FALSE);
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
					// if null, use delete method for optional primitives
					if (value == null) {
						target.deleteIsVisible();
						return;
					}
					target.setIsVisible(((java.lang.Boolean) value)
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

		// -- validation code for: _isVisible
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		{ // -- local scope
			BooleanValidator typeValidator = new BooleanValidator();
			fieldValidator.setValidator(typeValidator);
		}
		desc.setValidator(fieldValidator);
		// -- initialize element descriptors

		// -- _actionToolList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.ActionTool.class,
				"_actionToolList", "action-tool",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				ToolBar target = (ToolBar) object;
				return target.getActionTool();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
					target.addActionTool((com.iver.andami.plugins.config.generate.ActionTool) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.ActionTool();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _actionToolList
		fieldValidator = new org.exolab.castor.xml.FieldValidator();
		fieldValidator.setMinOccurs(0);
		{ // -- local scope
		}
		desc.setValidator(fieldValidator);
		// -- _selectableToolList
		desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(
				com.iver.andami.plugins.config.generate.SelectableTool.class,
				"_selectableToolList", "selectable-tool",
				org.exolab.castor.xml.NodeType.Element);
		handler = (new org.exolab.castor.xml.XMLFieldHandler() {
			public java.lang.Object getValue(java.lang.Object object)
					throws IllegalStateException {
				ToolBar target = (ToolBar) object;
				return target.getSelectableTool();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
					target.addSelectableTool((com.iver.andami.plugins.config.generate.SelectableTool) value);
				} catch (java.lang.Exception ex) {
					throw new IllegalStateException(ex.toString());
				}
			}

			public java.lang.Object newInstance(java.lang.Object parent) {
				return new com.iver.andami.plugins.config.generate.SelectableTool();
			}
		});
		desc.setHandler(handler);
		desc.setMultivalued(true);
		addFieldDescriptor(desc);

		// -- validation code for: _selectableToolList
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
				ToolBar target = (ToolBar) object;
				return target.getComboButton();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
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
				ToolBar target = (ToolBar) object;
				return target.getComboScale();
			}

			public void setValue(java.lang.Object object, java.lang.Object value)
					throws IllegalStateException, IllegalArgumentException {
				try {
					ToolBar target = (ToolBar) object;
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
	} // -- com.iver.andami.plugins.config.generate.ToolBarDescriptor()

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
		return com.iver.andami.plugins.config.generate.ToolBar.class;
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
