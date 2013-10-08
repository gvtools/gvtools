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

/* CVS MESSAGES:
 *
 * $Id: GraduatedSymbols.java 16232 2007-11-09 13:36:35Z jdominguez $
 * $Log$
 * Revision 1.8  2007-09-19 15:44:44  jaume
 * new signal catched (legend error)
 *
 * Revision 1.7  2007/05/21 10:38:27  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/05/17 09:32:37  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2007/05/10 09:46:45  jaume
 * Refactored legend interface names
 *
 * Revision 1.4  2007/05/08 15:45:31  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/03/21 08:03:03  jaume
 * refactored to use ISymbol instead of FSymbol
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.4  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/14 09:59:17  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/13 16:19:19  jaume
 * graduated symbol legends (start commiting)
 *
 * Revision 1.1.2.1  2007/02/12 15:14:41  jaume
 * refactored interval legend and added graduated symbol legend
 *
 *
 */
package org.gvsig.symbology.gui.layerproperties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Interval;
import org.gvsig.legend.Legend;
import org.gvsig.legend.LegendFactory;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.SizeIntervalLegend;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.legend.gui.ILegendPanel;
import com.iver.cit.gvsig.project.documents.view.legend.gui.JSymbolPreviewButton;
import com.iver.cit.gvsig.project.documents.view.legend.gui.Quantities;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolTable;
import com.iver.cit.gvsig.project.documents.view.legend.gui.VectorialInterval;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Implements the interface that shows the information of a legend which draws
 * quantities using symbol size to show relative values.
 * 
 * @author Pepe Vidal Salvador - jose.vidal.salvador@iver.es
 * 
 */

public class GraduatedSymbols extends VectorialInterval implements ILegendPanel {
	private JIncrementalNumberField txtMinSize, txtMaxSize;
	private JSymbolPreviewButton btnTemplate, btnBackground;
	private Class<? extends Geometry> templateShapeType;
	private GridBagLayoutPanel aux;
	private boolean showBackground = true;

	@Inject
	private DefaultSymbols defaultSymbols;

	@Inject
	private LegendFactory legendFactory;

	public GraduatedSymbols() {
		super();
		this.showBackground = true;
	}

	public GraduatedSymbols(boolean showBackground) {
		super();
		this.showBackground = showBackground;
	}

	@Override
	public JPanel getOptionPanel() {
		if (optionPanel == null) {
			optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			optionPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "symbol")));

			aux = new GridBagLayoutPanel();
			aux.addComponent(new JLabel(PluginServices.getText(this, "size")));
			aux.addComponent(PluginServices.getText(this, "from") + ":",
					getTxtMinSize());
			aux.addComponent(PluginServices.getText(this, "to") + ":",
					getTxtMaxSize());
			aux.addComponent(PluginServices.getText(this, "template"),
					getBtnTemplate());
			optionPanel.add(aux);

		}
		return optionPanel;
	}

	private JSymbolPreviewButton getBtnBackground() {
		if (btnBackground == null) {
			Symbolizer defaultFill = defaultSymbols.createDefaultSymbol(
					Polygon.class, Color.lightGray, "");
			btnBackground = new JSymbolPreviewButton(defaultFill);
			btnBackground.setPreferredSize(new Dimension(100, 35));
		}
		return btnBackground;
	}

	private JSymbolPreviewButton getBtnTemplate() {
		if (btnTemplate == null) {
			btnTemplate = new JSymbolPreviewButton();
			btnTemplate.setPreferredSize(new Dimension(100, 35));
		}
		return btnTemplate;
	}

	private JIncrementalNumberField getTxtMaxSize() {
		if (txtMaxSize == null) {
			txtMaxSize = new JIncrementalNumberField(String.valueOf(25), 7, 0,
					100, 1);
		}
		return txtMaxSize;
	}

	private JIncrementalNumberField getTxtMinSize() {
		if (txtMinSize == null) {
			txtMinSize = new JIncrementalNumberField(String.valueOf(3), 7, 0,
					100, 1);
		}
		return txtMinSize;
	}

	@Override
	public void setData(Layer lyr, Legend l) throws IOException {
		this.layer = lyr;

		Class<? extends Geometry> shapeType = layer.getShapeType();
		boolean isPolygon = Polygon.class.isAssignableFrom(shapeType)
				|| MultiPolygon.class.isAssignableFrom(shapeType);
		templateShapeType = isPolygon ? Point.class : shapeType;
		if (isPolygon && showBackground && btnBackground == null) {
			aux.addComponent(PluginServices.getText(this, "background"),
					getBtnBackground());
		}

		if (symbolTable != null) {
			pnlCenter.remove(symbolTable);
		}

		getDefaultSymbolPrev();

		symbolTable = new SymbolTable(SymbolTable.INTERVALS_TYPE,
				templateShapeType);
		pnlCenter.add(symbolTable);

		fillFieldNames();

		if (l instanceof SizeIntervalLegend) {
			SizeIntervalLegend legend = (SizeIntervalLegend) l;
			Symbolizer[] symbols = legend.getSymbols();

			getChkDefaultvalues().setSelected(legend.useDefaultSymbol());
			cmbField.setSelectedItem(legend.getFieldName());
			getTxtMaxSize().setDouble(legend.getSize().getMax());
			getTxtMinSize().setDouble(legend.getSize().getMin());
			if (symbols.length > 0) {
				getBtnTemplate().setSymbol(symbols[symbols.length - 1]);
			} else {
				Symbolizer defaultTemplate = defaultSymbols
						.createDefaultSymbol(templateShapeType, Color.darkGray,
								"");
				getBtnTemplate().setSymbol(defaultTemplate);
			}
			if (showBackground && isPolygon) {
				getBtnBackground().setSymbol(legend.getBackground());
			}

			txtNumIntervals.setText(String.valueOf(symbols.length));
			if (legend.getType() != null) {
				cmbFieldType.setSelectedIndex(legend.getType().ordinal());
			}
			defaultSymbolPrev.setSymbol(legend.getDefaultSymbol());
			symbolTable.fillTable(symbols, legend.getIntervals());
		} else {
			// Get first numeric field
			String fieldName = null;
			SimpleFeatureType schema = layer.getFeatureSource().getSchema();
			for (AttributeDescriptor attribute : schema
					.getAttributeDescriptors()) {
				Class<?> type = attribute.getType().getBinding();
				if (isNumericField(type)) {
					fieldName = attribute.getLocalName();
					break;
				}
			}

			getChkDefaultvalues().setSelected(false);
			cmbField.setSelectedItem(fieldName);
			getTxtMaxSize().setDouble(7);
			getTxtMinSize().setDouble(1);
			txtNumIntervals.setText(String.valueOf(3));
			cmbFieldType.setSelectedIndex(Type.EQUAL.ordinal());

			Symbolizer template = defaultSymbols.createDefaultSymbol(
					templateShapeType, Color.darkGray, "");
			getBtnTemplate().setSymbol(template);
			if (showBackground && isPolygon) {
				Symbolizer background = defaultSymbols.createDefaultSymbol(
						Polygon.class, Color.white, "background");
				getBtnBackground().setSymbol(background);
			}

			Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
					templateShapeType, Color.black, "");
			defaultSymbolPrev.setSymbol(defaultSymbol);
		}
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this,
				"draw_quantities_using_symbol_size_to_show_relative_values");
	}

	@Override
	public Legend getLegend() throws IOException {
		Map<Interval, Symbolizer> symbols = new HashMap<Interval, Symbolizer>();
		for (int row = 0; row < symbolTable.getRowCount(); row++) {
			Object value = symbolTable.getValue(row);
			if (value instanceof Interval) {
				Symbolizer symbol = symbolTable.getSymbol(row);
				String description = symbolTable.getLabel(row);
				symbol.setDescription(new DescriptionImpl(description,
						description));

				symbols.put((Interval) value, symbol);
			}
		}
		Symbolizer defaultSymbol = defaultSymbolPrev.getSymbol();
		boolean useDefault = chkdefaultvalues.isSelected();
		String fieldName = cmbField.getSelectedItem().toString();
		Symbolizer background = showBackground ? getBtnBackground().getSymbol()
				: null;
		Type type = Type.values()[cmbFieldType.getSelectedIndex()];

		return legendFactory.createSizeIntervalLegend(symbols, type,
				defaultSymbol, useDefault, layer, fieldName, background);
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public Class<? extends ILegendPanel> getParentClass() {
		return Quantities.class;
	}

	@Override
	public String getTitle() {
		return PluginServices.getText(this, "graduated_symbols");
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	@Override
	public Class<SizeIntervalLegend> getLegendClass() {
		return SizeIntervalLegend.class;
	}

	@Override
	protected void fillTableValues() throws IOException {
		symbolTable.removeAllItems();

		int intervalCount;
		// ensure the interval value is an integer greater than 0
		try {
			intervalCount = (int) Double.parseDouble(txtNumIntervals.getText());
			if (intervalCount < 1) {
				throw new Exception();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, PluginServices.getText(this,
					"invalid_interval_count_value"));
			return;
		}

		Interval size = new Interval(getTxtMinSize().getDouble(),
				getTxtMaxSize().getDouble());
		Type intervalType = Type.values()[getCmbIntervalTypes()
				.getSelectedIndex()];
		Symbolizer defaultSymbol = defaultSymbolPrev.getSymbol();
		boolean useDefault = chkdefaultvalues.isSelected();
		String fieldName = cmbField.getSelectedItem().toString();
		Symbolizer background = showBackground ? getBtnBackground().getSymbol()
				: null;

		SizeIntervalLegend legend = legendFactory.createSizeIntervalLegend(
				size, intervalType, defaultSymbol, useDefault, layer,
				fieldName, intervalCount, getBtnTemplate().getSymbol(),
				background);

		symbolTable.fillTable(legend.getSymbols(), legend.getIntervals());

		bDelAll.setEnabled(true);
		bDel.setEnabled(true);
	}

	public void setShowBackground(boolean showBackground) {
		this.showBackground = showBackground;
	}

	public boolean getShowBackground() {
		return this.showBackground;
	}
}
