/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.geotools.filter.expression.AbstractExpressionVisitor;
import org.geotools.styling.Fill;
import org.geotools.styling.SLD;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.TextSymbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JBlank;
import org.gvsig.layer.Layer;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.ExpressionVisitor;
import org.opengis.filter.expression.Literal;
import org.opengis.filter.expression.PropertyName;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;
import com.iver.cit.gvsig.gui.utils.FontChooser;
import com.iver.utiles.swing.JComboBox;

public class AttrInTableLabeling extends JPanel {
	private static final long serialVersionUID = 8229927418031917075L;
	private static final String NO_FIELD_ITEM = "-- "
			+ PluginServices.getText(LabelingManager.class, "none") + " --";
	private String[] fieldNames;
	// TODO gtintegration
	private String[] numericFieldNames;
	// private String[] integerFieldNames;

	private JRadioButton rdBtnFixedHeight;
	private JRadioButton rdBtnHeightField;
	private JRadioButton rdBtnFixedColor;
	// TODO gtintegration
	// private JRadioButton rdBtnColorField;
	private JComboBox<String> cmbTextField;
	private JComboBox<String> cmbHeightField;
	// TODO gtintegration
	// private JComboBox<String> cmbRotationField;
	// private JComboBoxUnits cmbUnits;
	// private JComboBoxUnitsReferenceSystem cmbReferenceSystem;
	private JTextField txtHeightField;
	private Layer layer;

	private ColorChooserPanel colorChooser;
	// TODO gtintegration
	// private JComboBox cmbColorField;

	private JButton chooseFontBut;
	private Font labelFont = new Font("Serif", Font.PLAIN, 8);

	@Inject
	private StyleFactory styleFactory;
	@Inject
	private FilterFactory2 filterFactory;

	public AttrInTableLabeling() {
		initialize();
	}

	private void initialize() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		GridBagLayoutPanel panel = new GridBagLayoutPanel();

		GridBagLayoutPanel aux = new GridBagLayoutPanel();
		aux.addComponent(PluginServices.getText(this, "field_to_be_labeled")
				+ ":", getCmbTextField());
		aux.addComponent(getRdBtnHeightField(), getCmbHeightField());
		aux.addComponent(getRdBtnFixedHeight(), getTxtHeightField());
		// aux.addComponent(PluginServices.getText(this, "rotation_height") +
		// ":",
		// getCmbRotationField());
		// aux.addComponent(PluginServices.getText(this, "units") + ":",
		// getCmbUnits());
		// aux.addComponent(PluginServices.getText(this, ""),
		// getCmbReferenceSystem());
		panel.add(aux);

		aux = new GridBagLayoutPanel();
		aux.addComponent(getChooseFontBut(), new JBlank(20, 20));
		GridBagLayoutPanel aux2 = new GridBagLayoutPanel();
		aux2.setBorder(BorderFactory.createTitledBorder(null,
				PluginServices.getText(this, "color")));
		aux2.addComponent(getRdBtnFixedColor(), getColorChooser());

		// TODO gtintegration
		// aux2.addComponent(getRdBtnColorField(), getCmbColorField());
		aux.addComponent(aux2);

		panel.add(new JBlank(20, 20));
		panel.add(aux);

		add(panel);

		ButtonGroup group = new ButtonGroup();
		group.add(getRdBtnFixedHeight());
		group.add(getRdBtnHeightField());

		ButtonGroup colorGroup = new ButtonGroup();
		colorGroup.add(getRdBtnFixedColor());
		// TODO gtintegration
		// colorGroup.add(getRdBtnColorField());

		getRdBtnHeightField().setEnabled(true);
	}

	private ColorChooserPanel getColorChooser() {
		if (colorChooser == null) {
			colorChooser = new ColorChooserPanel(true);
		}
		return colorChooser;
	}

	private JButton getChooseFontBut() {
		if (chooseFontBut == null) {
			chooseFontBut = new JButton(PluginServices.getText(this, "font"));
			chooseFontBut.setPreferredSize(new Dimension(80, 10));
			chooseFontBut.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					Font newFont;

					newFont = FontChooser.showDialog("Choose Font", labelFont);
					if (newFont == null) {
						return;
					}

					labelFont = newFont;
				}

			});
		}
		return chooseFontBut;
	}

	private JRadioButton getRdBtnFixedHeight() {
		if (rdBtnFixedHeight == null) {
			rdBtnFixedHeight = new JRadioButton(PluginServices.getText(this,
					"fixed_height") + ":");
			rdBtnFixedHeight.setSelected(true);
			rdBtnFixedHeight.setName("RDFIXEDHEIGHT");
		}

		return rdBtnFixedHeight;
	}

	private JRadioButton getRdBtnHeightField() {
		if (rdBtnHeightField == null) {
			rdBtnHeightField = new JRadioButton(PluginServices.getText(this,
					"text_height_field") + ":");
			rdBtnHeightField.setSelected(false);
			rdBtnHeightField.setName("RDHEIGHTFIELD");
		}

		return rdBtnHeightField;
	}

	private JRadioButton getRdBtnFixedColor() {
		if (rdBtnFixedColor == null) {
			rdBtnFixedColor = new JRadioButton(PluginServices.getText(this,
					"fixed_color") + ":");
			rdBtnFixedColor.setSelected(true);
			rdBtnFixedColor.setName("RDFIXEDCOLOR");
		}

		return rdBtnFixedColor;
	}

	// TODO gtintegration
	// private JRadioButton getRdBtnColorField() {
	// if (rdBtnColorField == null) {
	// rdBtnColorField = new JRadioButton(PluginServices.getText(this,
	// "color_field") + ":");
	// rdBtnColorField.setSelected(false);
	// rdBtnColorField.setName("RDCOLORFIELD");
	// }
	//
	// return rdBtnColorField;
	// }
	//
	// private JComboBoxUnits getCmbUnits() {
	// if (cmbUnits == null) {
	// cmbUnits = new JComboBoxUnits();
	// cmbUnits.setName("CMBUNITS");
	// }
	//
	// return cmbUnits;
	// }
	//
	// private JComboBoxUnitsReferenceSystem getCmbReferenceSystem() {
	// if (cmbReferenceSystem == null) {
	// cmbReferenceSystem = new JComboBoxUnitsReferenceSystem();
	// cmbReferenceSystem.setName("CMBREFSYST");
	// }
	// return cmbReferenceSystem;
	// }
	//
	// private JComboBox getCmbColorField() {
	// if (cmbColorField == null) {
	// cmbColorField = new JComboBox();
	// cmbColorField.setName("CMBCOLOR");
	// }
	//
	// return cmbColorField;
	// }

	private void refreshControls() {
		// When the attributes are in the table -----
		// field with the text
		refreshCmbTextField();

		// TODO gtintegration
		// field with the rotation
		// refreshCmbRotationField();

		// field with the text height or the text size
		refreshTextHeight();

		// TODO gtintegration
		// the text size unit name
		// refreshCmbUnits();

		// TODO gtintegration
		// refreshCmbRefSystem();
		// the font for the text
		refreshFont();
		// the color for the font
		refreshColorFont();
	}

	// TODO gtintegration
	// private JComboBox<String> getCmbRotationField() {
	// if (cmbRotationField == null) {
	// cmbRotationField = new JComboBox<String>();
	// cmbRotationField.setPreferredSize(new Dimension(200, 20));
	// cmbRotationField.setName("CMBROTATIONFIELD");
	// }
	// return cmbRotationField;
	// }

	private JComboBox<String> getCmbHeightField() {
		if (cmbHeightField == null) {
			cmbHeightField = new JComboBox<String>();
			cmbHeightField.setPreferredSize(new Dimension(200, 20));
			cmbHeightField.setName("CMBHEIGHTFIELD");
		}
		return cmbHeightField;
	}

	private JComboBox<String> getCmbTextField() {
		if (cmbTextField == null) {
			cmbTextField = new JComboBox<String>();
			cmbTextField.setPreferredSize(new Dimension(200, 20));
			cmbTextField.setName("CMBTEXTFIELD");
		}
		return cmbTextField;
	}

	private JTextField getTxtHeightField() {
		if (txtHeightField == null) {
			txtHeightField = new JTextField(10);
			txtHeightField.setText("10");
			txtHeightField.setName("TXTHEIGHTFIELD");
		}

		return txtHeightField;
	}

	public TextSymbolizer getLabelingStyle() {
		String field = getCmbTextField().getItemAt(
				getCmbTextField().getSelectedIndex());
		Color color = getColorChooser().getColor();
		double alpha = getColorChooser().getAlpha() / 255.0;

		TextSymbolizer symbol = styleFactory.createTextSymbolizer();

		symbol.setFill(styleFactory.createFill(filterFactory.literal(color),
				filterFactory.literal(alpha)));
		symbol.setLabel(filterFactory.property(field));
		org.geotools.styling.Font font;
		if (getRdBtnHeightField().isSelected()) {
			Expression family = filterFactory.literal(labelFont.getFamily());
			String fontStyle = labelFont.isItalic() ? org.geotools.styling.Font.Style.ITALIC
					: org.geotools.styling.Font.Style.NORMAL;
			Expression italic = filterFactory.literal(fontStyle);
			String fontWeight = labelFont.isBold() ? org.geotools.styling.Font.Weight.BOLD
					: org.geotools.styling.Font.Weight.NORMAL;
			Expression bold = filterFactory.literal(fontWeight);
			Expression size = filterFactory.property(getCmbHeightField()
					.getItemAt(getCmbHeightField().getSelectedIndex()));
			font = new StyleBuilder().createFont(family, italic, bold, size);
		} else {
			font = new StyleBuilder().createFont(labelFont.getFamily(),
					labelFont.isItalic(), labelFont.isBold(),
					Double.parseDouble(getTxtHeightField().getText()));
		}
		symbol.setFont(font);
		return symbol;
	}

	// TODO gtintegration
	// public ILabelingStrategy getLabelingStrategy() {
	// // user selected to define each label attributes from values
	// // contained in the table for each feature row.
	//
	// double fixedSize;
	// try {
	// fixedSize = Double.parseDouble(getTxtHeightField().getText());
	// } catch (Exception e) {
	// fixedSize = 10;
	// }
	// AttrInTableLabelingStrategy strategy = new AttrInTableLabelingStrategy();
	// strategy.setLayer(layer);
	//
	// if (getCmbHeightField().getItemCount() > 0
	// && !rdBtnFixedHeight.isSelected())
	// strategy.setHeightField((String) getCmbHeightField()
	// .getSelectedItem());
	// if (getCmbRotationField().getItemCount() > 0)
	// if (!getCmbRotationField().getSelectedItem().equals(NO_FIELD_ITEM))
	// strategy.setRotationField((String) getCmbRotationField()
	// .getSelectedItem());
	// else
	// strategy.setRotationField(null);
	//
	// if (getCmbTextField().getItemCount() > 0)
	// strategy.setTextField((String) getCmbTextField().getSelectedItem());
	//
	// strategy.setUsesFixedSize(getRdBtnFixedHeight().isSelected());
	// strategy.setFixedSize(fixedSize);
	//
	// if (getCmbUnits().getItemCount() > 0)
	// strategy.setUnit(getCmbUnits().getSelectedUnitIndex());
	// if (getCmbReferenceSystem().getItemCount() > 0)
	// strategy.setReferenceSystem(getCmbReferenceSystem()
	// .getSelectedIndex());
	//
	// strategy.setUsesFixedColor(getRdBtnFixedColor().isSelected());
	// strategy.setFixedColor(getColorChooser().getColor());
	//
	// if (getCmbColorField().getItemCount() > 0
	// && !rdBtnFixedColor.isSelected())
	// strategy.setColorField((String) getCmbColorField()
	// .getSelectedItem());
	//
	// strategy.setFont(labelFont);
	// return strategy;
	// }

	public void setModel(Layer layer) throws IOException {
		this.layer = layer;
		// to allow the labeling of non-FLyrVect layers
		SimpleFeatureType schema = layer.getFeatureSource().getSchema();
		fieldNames = new String[schema.getAttributeCount()];
		// TODO gtintegration
		List<String> l = new ArrayList<String>();
		// ArrayList<String> lColors = new ArrayList<String>();
		for (int i = 0; i < fieldNames.length; i++) {
			AttributeDescriptor attr = schema.getDescriptor(i);
			fieldNames[i] = attr.getLocalName();

			Class<?> type = attr.getType().getBinding();
			if (Integer.class.isAssignableFrom(type)
					|| Double.class.isAssignableFrom(type)) {
				l.add(attr.getLocalName());
			}
		}
		// TODO gtintegration
		numericFieldNames = l.toArray(new String[l.size()]);
		// integerFieldNames = lColors.toArray(new String[lColors.size()]);

		refreshControls();
	}

	private void refreshColorFont() {
		// TODO gtintegration
		// getCmbColorField().removeAllItems();

		// boolean enabled = integerFieldNames.length > 0;
		// getCmbColorField().setEnabled(enabled);
		// getRdBtnColorField().setEnabled(enabled);

		// if (!enabled) {
		getRdBtnFixedColor().setSelected(true);
		// }

		// for (int i = 0; i < integerFieldNames.length; i++) {
		// getCmbColorField().addItem(integerFieldNames[i]);
		// }

		getRdBtnFixedColor().setSelected(true);
		// TODO gtintegration
		// getRdBtnColorField().setSelected(!aux.usesFixedColor());

		// TODO gtintegration
		// String item = aux.getColorField();
		// getCmbColorField().setSelectedItem(item);
		TextSymbolizer labeling = layer.getLegend().getLabeling();
		if (labeling != null) {
			Fill fill = labeling.getFill();
			getColorChooser().setColor(SLD.color(fill));
			getColorChooser().setAlpha((int) (SLD.opacity(fill) * 255));
		}
	}

	private void refreshFont() {
		TextSymbolizer labeling = layer.getLegend().getLabeling();
		if (labeling != null) {
			org.geotools.styling.Font font = labeling.getFont();
			String family = font.getFamily().get(0).toString();
			int style = Font.PLAIN;
			if (font.getStyle().toString()
					.equals(org.geotools.styling.Font.Style.ITALIC)) {
				style = Font.ITALIC;
			}
			if (font.getWeight().toString()
					.equals(org.geotools.styling.Font.Weight.BOLD)) {
				style = Font.BOLD;
			}
			int size = 10;
			if (font.getSize() != null) {
				Integer eval = font.getSize().evaluate(null, Integer.class);
				if (eval != null) {
					size = eval;
				}
			}
			labelFont = new Font(family, style, size);
		}
	}

	// TODO gtintegration
	// private void refreshCmbUnits() {
	// TextSymbolizer labeling = layer.getLegend().getLabeling();
	// getCmbUnits().setSelectedUnitIndex(aux.getUnit());
	// }
	//
	// private void refreshCmbRefSystem() {
	// TextSymbolizer labeling = layer.getLegend().getLabeling();
	// getCmbReferenceSystem().setSelectedIndex(aux.getReferenceSystem());
	// }

	private void refreshTextHeight() {
		// TODO gtintegration
		getCmbHeightField().removeAllItems();
		boolean enabled = numericFieldNames.length > 0;
		getCmbHeightField().setEnabled(enabled);
		getRdBtnHeightField().setEnabled(enabled);

		if (!enabled) {
			getRdBtnFixedHeight().setSelected(true);
		}

		for (int i = 0; i < numericFieldNames.length; i++) {
			getCmbHeightField().addItem(numericFieldNames[i]);
		}

		final TextSymbolizer labeling = layer.getLegend().getLabeling();
		if (labeling != null) {
			ExpressionVisitor visitor = new AbstractExpressionVisitor() {
				@Override
				public Object visit(Literal expression, Object extraData) {
					getRdBtnHeightField().setSelected(false);
					int size = labeling.getFont().getSize()
							.evaluate(null, Integer.class);
					getTxtHeightField().setText(Integer.toString(size));
					return super.visit(expression, extraData);
				}

				@Override
				public Object visit(PropertyName expr, Object extraData) {
					getRdBtnHeightField().setSelected(true);
					String item = expr.getPropertyName();
					getCmbHeightField().setSelectedItem(item);
					getTxtHeightField().setText(Integer.toString(10));
					return super.visit(expr, extraData);
				}
			};

			labeling.getFont().getSize().accept(visitor, null);
		}
	}

	// TODO gtintegration
	// private void refreshCmbRotationField() {
	// getCmbRotationField().removeAllItems();
	// getCmbRotationField().addItem(NO_FIELD_ITEM);
	// for (int i = 0; i < numericFieldNames.length; i++) {
	// getCmbRotationField().addItem(numericFieldNames[i]);
	// }
	//
	// TextSymbolizer labeling = layer.getLegend().getLabeling();
	// String item = aux.getRotationField();
	// getCmbRotationField().setSelectedItem(
	// item != null ? item : NO_FIELD_ITEM);
	// }

	private void refreshCmbTextField() {
		getCmbTextField().removeAllItems();
		for (int i = 0; i < fieldNames.length; i++) {
			getCmbTextField().addItem(fieldNames[i]);
		}

		TextSymbolizer labeling = layer.getLegend().getLabeling();
		String item;
		if (labeling != null) {
			item = labeling.getLabel().toString();
		} else {
			item = null;
		}
		getCmbTextField().setSelectedItem(item != null ? item : NO_FIELD_ITEM);
	}

	public String getLabelingStrategyName() {
		return PluginServices
				.getText(this, "label_attributes_defined_in_table");
	}

	// TODO gtintegration
	// public Class getLabelingStrategyClass() {
	// return AttrInTableLabelingStrategy.class;
	// }

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		getChooseFontBut().setEnabled(enabled);
		// TODO gtintegration
		// getCmbColorField().setEnabled(enabled);
		getCmbHeightField().setEnabled(enabled);
		// getCmbReferenceSystem().setEnabled(enabled);
		// getCmbRotationField().setEnabled(enabled);
		getCmbTextField().setEnabled(enabled);
		// getCmbUnits().setEnabled(enabled);
		getColorChooser().setEnabled(enabled);
		// TODO gtintegration
		// getRdBtnColorField().setEnabled(enabled);
		getRdBtnFixedColor().setEnabled(enabled);
		getRdBtnFixedHeight().setEnabled(enabled);
		getRdBtnHeightField().setEnabled(enabled);
		getTxtHeightField().setEnabled(enabled);
	}
}
