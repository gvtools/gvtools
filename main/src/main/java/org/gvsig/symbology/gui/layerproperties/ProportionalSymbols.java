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
package org.gvsig.symbology.gui.layerproperties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JBlank;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Interval;
import org.gvsig.legend.Legend;
import org.gvsig.legend.impl.LegendFactory;
import org.gvsig.legend.impl.ProportionalLegend;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.FilterFactory2;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.legend.gui.ILegendPanel;
import com.iver.cit.gvsig.project.documents.view.legend.gui.JSymbolPreviewButton;
import com.iver.cit.gvsig.project.documents.view.legend.gui.Quantities;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Implements the panel for the legend of proportional symbols.In the interface
 * will be options to select the value field, the normalization field (if the
 * user wants to use it) and options to select the symbol an its minimum and
 * maximum size.
 * 
 * Also there will be possible to select a background symbol (only when the
 * shapetype of the layer is polygonal).
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class ProportionalSymbols extends JPanel implements ILegendPanel {
	private static final ImageIcon ICON = new ImageIcon(
			ProportionalSymbols.class.getClassLoader().getResource(
					"images/ProportionalSymbols.PNG"));

	private static Logger logger = Logger.getLogger(ProportionalSymbols.class
			.getName());

	private static String none;

	private JPanel symbolPanel, backgroundPanel;
	private JSymbolPreviewButton templateSymbol, backSymbol;
	private JComboBox<String> cmbValue, cmbNormalization;
	private JIncrementalNumberField txtMinSize, txtMaxSize;
	private Layer layer;
	private Class<? extends Geometry> shapeType;
	private boolean useNormalization = false;

	@Inject
	private DefaultSymbols defaultSymbols;
	@Inject
	private FilterFactory2 filterFactory;
	@Inject
	private LegendFactory legendFactory;

	/**
	 * Default constructor
	 */
	public ProportionalSymbols() {
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		none = PluginServices.getText(this, "none");

		setLayout(new BorderLayout());
		JPanel aux = new JPanel(new BorderLayout());

		JPanel fieldsPanel = new JPanel(new FlowLayout());
		fieldsPanel.setBorder(BorderFactory.createTitledBorder(null,
				PluginServices.getText(this, "fields")));
		fieldsPanel.setPreferredSize(new Dimension(300, 60));

		cmbValue = new JComboBox<String>();
		cmbValue.setActionCommand("VALUE_SELECTED");

		cmbNormalization = new JComboBox<String>();
		cmbNormalization.setActionCommand("NORMALIZATION_SELECTED");

		fieldsPanel
				.add(new JLabel(PluginServices.getText(this, "value") + ":"));
		fieldsPanel.add(cmbValue);

		fieldsPanel.add(new JLabel(PluginServices
				.getText(this, "normalization") + ":"));
		fieldsPanel.add(cmbNormalization);

		symbolPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 3, 2));
		symbolPanel.setBorder(BorderFactory.createTitledBorder(null,
				PluginServices.getText(this, "symbol")));

		GridBagLayoutPanel aux2 = new GridBagLayoutPanel();
		aux2.addComponent(new JLabel(PluginServices.getText(this, "size")));
		aux2.addComponent(new JBlank(10, 10));
		aux2.addComponent(PluginServices.getText(this, "from") + ":",
				getTxtMinSize());
		aux2.addComponent(PluginServices.getText(this, "to") + ":",
				getTxtMaxSize());

		JPanel templatePanel = new JPanel();
		templatePanel.setBorder(BorderFactory.createTitledBorder(null,
				PluginServices.getText(this, "template")));
		templatePanel.add(getTemplateSymbol());
		symbolPanel.add(new JBlank(10, 10));
		symbolPanel.add(aux2);
		symbolPanel.add(new JBlank(10, 10));
		symbolPanel.add(templatePanel);

		aux.add(fieldsPanel, BorderLayout.NORTH);
		aux.add(symbolPanel, BorderLayout.CENTER);

		this.add(aux, BorderLayout.CENTER);

		cmbValue.addActionListener(action);
		cmbNormalization.addActionListener(action);
		templateSymbol.addActionListener(action);
	}

	private JPanel getBackgroundPanel() {
		if (backgroundPanel == null) {
			backgroundPanel = new JPanel();
			backgroundPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "background")));
			backgroundPanel.add(getBtnBackground());
		}
		return backgroundPanel;
	}

	/**
	 * Creates a JIncrementalNumberField which is used to select the maximum
	 * size for the symbol
	 * 
	 * @return JIncrementalNumberField
	 */
	private JIncrementalNumberField getTxtMaxSize() {
		if (txtMaxSize == null) {
			txtMaxSize = new JIncrementalNumberField(String.valueOf(25), 7, 0,
					100, 1);
			txtMaxSize.addActionListener(action);
		}
		return txtMaxSize;
	}

	/**
	 * Creates a JIncrementalNumberField which is used to select the minimum
	 * size for the symbol
	 * 
	 * @return JIncrementalNumberField
	 */
	private JIncrementalNumberField getTxtMinSize() {
		if (txtMinSize == null) {
			txtMinSize = new JIncrementalNumberField(String.valueOf(3), 7, 0,
					100, 1);
			txtMinSize.addActionListener(action);
		}
		return txtMinSize;
	}

	/**
	 * Creates a JSymbolPreviewButton which is used to select the template
	 * symbol
	 * 
	 * @return JSymbolPreviewButton
	 */
	private JSymbolPreviewButton getTemplateSymbol() {
		if (templateSymbol == null) {
			templateSymbol = new JSymbolPreviewButton();
			templateSymbol.setPreferredSize(new Dimension(100, 45));
		}
		return templateSymbol;
	}

	/**
	 * Creates a JSymbolPreviewButton which is used to select the background
	 * symbol
	 * 
	 * @return JSymbolPreviewButton
	 */
	private JSymbolPreviewButton getBtnBackground() {
		if (backSymbol == null) {
			backSymbol = new JSymbolPreviewButton(
					defaultSymbols.createDefaultSymbol(Polygon.class,
							Color.blue, ""));
			backSymbol.setPreferredSize(new Dimension(100, 45));
		}
		return backSymbol;
	}

	private String[] getNumericFieldNames()
			throws UnsupportedOperationException, IOException {
		SimpleFeatureType schema = layer.getFeatureSource().getSchema();
		List<String> numericFields = new ArrayList<String>();
		List<AttributeDescriptor> attributes = schema.getAttributeDescriptors();
		for (AttributeDescriptor attribute : attributes) {
			if (isNumericField(attribute.getType().getBinding())) {
				numericFields.add(attribute.getLocalName());
			}
		}

		return numericFields.toArray(new String[numericFields.size()]);
	}

	@Override
	public void setData(Layer layer, Legend l) throws IOException {
		this.layer = layer;

		String[] fieldNames = getNumericFieldNames();
		cmbValue.setModel(new DefaultComboBoxModel<String>(fieldNames));
		cmbNormalization.setModel(new DefaultComboBoxModel<String>(fieldNames));
		cmbNormalization.addItem(none);
		cmbNormalization.setSelectedItem(none);

		Class<? extends Geometry> type = layer.getShapeType();
		boolean isLine = MultiLineString.class.isAssignableFrom(type)
				|| LineString.class.isAssignableFrom(type);
		if (isLine) {
			this.shapeType = type;
		} else {
			this.shapeType = Point.class;
		}

		boolean useBackground = MultiPolygon.class.isAssignableFrom(type)
				|| Polygon.class.isAssignableFrom(type);
		if (useBackground) {
			symbolPanel.add(getBackgroundPanel());
		}

		if (l instanceof ProportionalLegend) {
			ProportionalLegend legend = (ProportionalLegend) l;
			cmbValue.setSelectedItem(legend.getValueField());

			String normField = legend.getNormalizationField();
			if (normField != null) {
				cmbNormalization.setSelectedItem(normField);
			} else {
				cmbNormalization.setSelectedItem(none);
			}
			txtMaxSize.setDouble(legend.getSize().getMax());
			txtMinSize.setDouble(legend.getSize().getMin());

			Symbolizer template = legend.getTemplate();
			if (template instanceof LineSymbolizer) {
				LineSymbolizer line = (LineSymbolizer) template;
				line.getStroke().setWidth(filterFactory.literal(2));
			} else if (template instanceof PointSymbolizer) {
				PointSymbolizer point = (PointSymbolizer) template;
				point.getGraphic().setSize(filterFactory.literal(15));
			}

			getTemplateSymbol().setSymbol(template);

			if (useBackground) {
				getBtnBackground().setSymbol(legend.getBackground());
			}
		} else {
			int templateSize = isLine ? 2 : 15;
			Symbolizer template = defaultSymbols.createDefaultSymbol(shapeType,
					Color.darkGray, templateSize, "");
			getTemplateSymbol().setSymbol(template);
			if (isLine) {
				txtMinSize.setDouble(3);
				txtMaxSize.setDouble(3);
			} else {
				txtMinSize.setDouble(10);
				txtMaxSize.setDouble(10);
			}
		}
	}

	@Override
	public Legend getLegend() {
		String valueField = cmbValue.getSelectedItem().toString();
		Interval size = new Interval(txtMinSize.getDouble(),
				txtMaxSize.getDouble());
		Symbolizer template = getTemplateSymbol().getSymbol();
		String desc = getSymbolDescription();
		template.setDescription(new DescriptionImpl(desc, desc));
		Symbolizer background = getBtnBackground().getSymbol();
		if (useNormalization) {
			return legendFactory.createProportionalLegend(layer, valueField,
					cmbNormalization.getSelectedItem().toString(), template,
					background, backgroundPanel != null, size);
		} else {
			return legendFactory.createProportionalLegend(layer, valueField,
					template, background, backgroundPanel != null, size);
		}
	}

	private String getSymbolDescription() {
		String description = "";

		if (cmbValue.getSelectedItem() != null) {
			description += cmbValue.getSelectedItem().toString();
		}

		if (cmbNormalization.getSelectedItem().toString().compareTo(none) != 0) {
			description += " / "
					+ cmbNormalization.getSelectedItem().toString();
		}
		return description;
	}

	public String getDescription() {
		return PluginServices.getText(this,
				"draw_quantities_using_symbol_size_to_show_exact_values");
	}

	public ImageIcon getIcon() {
		return ICON;
	}

	public Class<? extends ILegendPanel> getParentClass() {
		return Quantities.class;
	}

	public String getTitle() {
		return PluginServices.getText(this, "proportional_symbols");
	}

	public Class<? extends Legend> getLegendClass() {
		return ProportionalLegend.class;
	}

	private boolean isNumericField(Class<?> fieldType) {
		return Integer.class.isAssignableFrom(fieldType)
				|| Double.class.isAssignableFrom(fieldType);
	}

	@Override
	public boolean isSuitableFor(Layer layer) {
		if (layer.hasFeatures()) {
			try {
				List<AttributeDescriptor> attributes = layer.getFeatureSource()
						.getSchema().getAttributeDescriptors();
				for (AttributeDescriptor attribute : attributes) {
					if (isNumericField(attribute.getType().getBinding())) {
						return true;
					}
				}
				return false;
			} catch (Exception e) {
				logger.error("Cannot read layer metadata", e);
				return false;
			}
		} else {
			return false;
		}
	}

	public JPanel getPanel() {
		return this;
	}

	private ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(cmbNormalization)) {
				JComboBox cb = (JComboBox) e.getSource();
				useNormalization = cb.getSelectedItem().toString()
						.compareTo(none) != 0;
			} else if (e.getSource().equals(txtMinSize)) {
				if (txtMaxSize.getDouble() < txtMinSize.getDouble()) {
					txtMaxSize.setDouble(txtMinSize.getDouble());
				}
			} else if (e.getSource().equals(txtMaxSize)) {
				if (txtMaxSize.getDouble() < txtMinSize.getDouble()) {
					txtMinSize.setDouble(txtMaxSize.getDouble());
				}
			}
		}
	};
}
