/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.geotools.filter.FilterFactoryImpl;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyleFactoryImpl;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Interval;
import org.gvsig.legend.Legend;
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.IntervalLegend.Type;
import org.gvsig.legend.impl.LegendFactory;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.FilterFactory;

import com.google.inject.Inject;
import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * DOCUMENT ME!
 * 
 * @author Vicente Caballero Navarro
 */
public class VectorialInterval extends JPanel implements ILegendPanel {
	protected static Logger logger = Logger.getLogger(VectorialInterval.class
			.getName());
	private GridBagLayoutPanel pnlGeneral = null;
	protected JComboBox<String> cmbField = null;
	protected JTextField txtNumIntervals = null;
	protected ColorChooserPanel startColorChooser = null;
	protected ColorChooserPanel endColorChooser = null;
	protected JCheckBox chkdefaultvalues = null;
	protected JComboBox<String> cmbFieldType = null;
	protected JPanel panelS = null;
	private JButton bintervals = null;
	private JButton bInsert = null;
	protected JButton bDelAll = null;
	protected JButton bDel = null;
	protected Layer layer;
	protected IntervalLegend legend = null;
	protected SymbolTable symbolTable;
	protected MyListener listener = new MyListener();
	protected JPanel pnlCenter = null;
	protected JPanel optionPanel;
	private JPanel pnlNorth;
	protected JSymbolPreviewButton defaultSymbolPrev;
	private GridBagLayoutPanel defaultSymbolPanel = new GridBagLayoutPanel();

	@Inject
	private DefaultSymbols defaultSymbols;

	@Inject
	private LegendFactory legendFactory;

	public VectorialInterval() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(700, 350);
		this.add(getPnlNorth(), BorderLayout.NORTH);

		this.add(getPnlButtons(), BorderLayout.SOUTH);
		this.add(getPnlCenter(), BorderLayout.CENTER);
		setOptionPanel(getOptionPanel());
	}

	private JPanel getPnlNorth() {
		if (pnlNorth == null) {
			pnlNorth = new JPanel(new GridLayout(1, 2));
			pnlNorth.add(getGeneralPanel());
		}
		return pnlNorth;
	}

	/**
	 * This method initializes panelN
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getGeneralPanel() {
		if (pnlGeneral == null) {
			pnlGeneral = new GridBagLayoutPanel();
			pnlGeneral.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "fields")));
			pnlGeneral.addComponent(
					PluginServices.getText(this, "Campo_de_clasificacion"),
					getCmbFields());
			pnlGeneral.addComponent(
					PluginServices.getText(this, "tipo_de_intervalo"),
					getCmbIntervalTypes());

			JPanel aux = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
			aux.add(getTxtNumIntervals());
			pnlGeneral.addComponent(
					PluginServices.getText(this, "No_de_intervalos"), aux);

			defaultSymbolPanel.add(getChkDefaultvalues(), null);
			pnlGeneral.addComponent(defaultSymbolPanel);

		}
		return pnlGeneral;
	}

	public JPanel getOptionPanel() {
		if (optionPanel == null) {
			optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			optionPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "color_ramp")));

			GridBagLayoutPanel aux = new GridBagLayoutPanel();
			aux.addComponent(PluginServices.getText(this, "Color_inicio"),
					getColorChooserPanel());
			aux.addComponent(PluginServices.getText(this, "Color_final"),
					getColorChooserPanel1());
			optionPanel.add(aux);
		}
		return optionPanel;
	}

	private void setOptionPanel(JPanel p) {
		getPnlNorth().remove(getOptionPanel());
		getPnlNorth().add(p, BorderLayout.NORTH);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbFields() {
		if (cmbField == null) {
			cmbField = new JComboBox<String>();
			cmbField.setActionCommand("FIELD_SELECTED");
			cmbField.addActionListener(listener);
			cmbField.setVisible(true);
		}

		return cmbField;
	}

	/**
	 * This method initializes txtNumIntervals
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtNumIntervals() {
		if (txtNumIntervals == null) {
			txtNumIntervals = new JTextField(5);
			txtNumIntervals.setText("5");
		}

		return txtNumIntervals;
	}

	/**
	 * This method initializes colorChooserPanel
	 * 
	 * @return com.iver.cit.gvsig.gui.Panels.ColorChooserPanel
	 */
	private ColorChooserPanel getColorChooserPanel() {
		if (startColorChooser == null) {
			startColorChooser = new ColorChooserPanel();
			startColorChooser
					.setBounds(new java.awt.Rectangle(108, 49, 54, 20));
			startColorChooser.setAlpha(255);
			startColorChooser.setColor(Color.red);
		}

		return startColorChooser;
	}

	/**
	 * This method initializes colorChooserPanel1
	 * 
	 * @return com.iver.cit.gvsig.gui.Panels.ColorChooserPanel
	 */
	private ColorChooserPanel getColorChooserPanel1() {
		if (endColorChooser == null) {
			endColorChooser = new ColorChooserPanel();
			endColorChooser.setBounds(new java.awt.Rectangle(251, 49, 54, 20));
			endColorChooser.setAlpha(255);
			endColorChooser.setColor(Color.blue);
		}

		return endColorChooser;
	}

	/**
	 * This method initializes chkdefaultvalues
	 * 
	 * @return javax.swing.JCheckBox
	 */
	protected JCheckBox getChkDefaultvalues() {
		if (chkdefaultvalues == null) {
			chkdefaultvalues = new JCheckBox();
			chkdefaultvalues.setText(PluginServices.getText(this,
					"resto_valores") + ": ");
			chkdefaultvalues
					.setBounds(new java.awt.Rectangle(342, 26, 141, 20));
			chkdefaultvalues.setSelected(false);
			chkdefaultvalues
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							legend.setUseDefault(chkdefaultvalues.isSelected());
						}
					});
		}

		return chkdefaultvalues;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox<String> getCmbIntervalTypes() {
		if (cmbFieldType == null) {
			cmbFieldType = new JComboBox<String>();
			cmbFieldType.setActionCommand("INTERVAL_TYPE");
			cmbFieldType.addActionListener(listener);
			cmbFieldType.addItem(PluginServices
					.getText(this, "equal_intervals"));
			cmbFieldType.addItem(PluginServices.getText(this,
					"natural_intervals"));
			cmbFieldType.addItem(PluginServices.getText(this,
					"quantile_intervals"));
			cmbFieldType.setVisible(true);
		}

		return cmbFieldType;
	}

	/**
	 * This method initializes panelS
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getPnlButtons() {
		if (panelS == null) {
			panelS = new JPanel();
			panelS.setPreferredSize(new java.awt.Dimension(417, 32));
			panelS.add(getBintervals(), null);
			panelS.add(getBInsert(), null);
			panelS.add(getBDelAll(), null);
			panelS.add(getBDel(), null);
		}

		return panelS;
	}

	/**
	 * This method initializes bintervals
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBintervals() {
		if (bintervals == null) {
			bintervals = new JButton();
			bintervals.setActionCommand("ADD_ALL_VALUES");
			bintervals.addActionListener(listener);
			bintervals.setText(PluginServices.getText(this,
					"Calcular_intervalos"));
		}

		return bintervals;
	}

	/**
	 * This method initializes bInsert
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBInsert() {
		if (bInsert == null) {
			bInsert = new JButton();
			bInsert.setActionCommand("ADD_VALUE");
			bInsert.addActionListener(listener);
			bInsert.setText(PluginServices.getText(this, "Anadir"));
		}

		return bInsert;
	}

	/**
	 * This method initializes bDelAll
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBDelAll() {
		if (bDelAll == null) {
			bDelAll = new JButton();
			bDelAll.setActionCommand("REMOVE_ALL");
			bDelAll.addActionListener(listener);
			bDelAll.setText(PluginServices.getText(this, "Quitar_todos"));
		}

		return bDelAll;
	}

	/**
	 * This method initializes bDel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBDel() {
		if (bDel == null) {
			bDel = new JButton();
			bDel.setText(PluginServices.getText(this, "Quitar"));
			bDel.setActionCommand("REMOVE");
			bDel.addActionListener(listener);
		}

		return bDel;
	}

	/**
	 * Damos una primera pasada para saber los l�mites inferior y superior y
	 * rellenar un array con los valores. Luego dividimos ese array en
	 * intervalos.
	 * 
	 * @throws IOException
	 */
	protected void fillTableValues() throws IOException {
		symbolTable.removeAllItems();

		int intervalCount = 1;
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

		Color startColor = startColorChooser.getColor();
		Color endColor = endColorChooser.getColor();
		Type intervalType = Type.values()[getCmbIntervalTypes()
				.getSelectedIndex()];
		Symbolizer defaultSymbol = defaultSymbolPrev.getSymbol();
		boolean useDefault = chkdefaultvalues.isSelected();

		legend = legendFactory.createIntervalLegend(startColor, endColor,
				intervalType, defaultSymbol, useDefault, layer, cmbField
						.getSelectedItem().toString(), intervalCount);

		symbolTable.fillTable(legend.getSymbols(), legend.getIntervals());

		bDelAll.setEnabled(true);
		bDel.setEnabled(true);
	}

	@Override
	public void setData(Layer layer, Legend l) {
		if (l instanceof IntervalLegend) {
			this.legend = (IntervalLegend) l;
		} else {
			Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), Color.blue, null);
			try {
				this.legend = legendFactory.createIntervalLegend(
						new HashMap<Interval, Symbolizer>(), defaultSymbol,
						false, layer, layer.getFeatureSource().getSchema()
								.getDescriptor(0).getLocalName());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		this.layer = layer;

		if (symbolTable != null) {
			pnlCenter.remove(symbolTable);
		}

		getDefaultSymbolPrev();

		symbolTable = new SymbolTable(SymbolTable.INTERVALS_TYPE);
		pnlCenter.add(symbolTable);

		try {
			fillFieldNames();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		chkdefaultvalues.setSelected(legend.usesDefaultSymbol());
		// TODO gtintegration
		// cmbField.getModel().setSelectedItem(
		// legend.getClassifyingFieldNames()[0]);
		Symbolizer[] symbols = legend.getSymbols();
		symbolTable.fillTable(symbols, legend.getIntervals());
		startColorChooser.setColor(legend.getStartColor());
		endColorChooser.setColor(legend.getEndColor());
		startColorChooser.repaint();
		endColorChooser.repaint();
		if (legend.usesDefaultSymbol()) {
			txtNumIntervals.setText(String.valueOf(symbols.length - 1));
		} else {
			txtNumIntervals.setText(String.valueOf(symbols.length));
		}

		defaultSymbolPrev.setSymbol(legend.getDefaultSymbol());
		if (legend.getType() != null) {
			cmbFieldType.setSelectedIndex(legend.getType().ordinal());
		}
	}

	public void getDefaultSymbolPrev() {
		if (defaultSymbolPrev == null) {
			defaultSymbolPrev = new JSymbolPreviewButton();
			defaultSymbolPrev.setPreferredSize(new Dimension(110, 20));
			defaultSymbolPanel.add(defaultSymbolPrev, null);
		}
	}

	protected void fillFieldNames() throws UnsupportedOperationException,
			IOException {
		ArrayList<String> nomFields = new ArrayList<String>();
		SimpleFeatureType schema = layer.getFeatureSource().getSchema();
		for (AttributeDescriptor attribute : schema.getAttributeDescriptors()) {
			Class<?> type = attribute.getType().getBinding();
			if (isNumericField(type)) {
				nomFields.add(attribute.getLocalName());
			}
		}

		DefaultComboBoxModel<String> cM = new DefaultComboBoxModel<String>(
				nomFields.toArray(new String[0]));
		cmbField.setModel(cM);

		symbolTable.removeAllItems();
	}

	@Override
	public Legend getLegend() {
		// Fill legend from symbol table
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

		try {
			Symbolizer defaultSymbol = defaultSymbolPrev.getSymbol();
			boolean useDefault = chkdefaultvalues.isSelected();
			String field = cmbField.getSelectedItem().toString();
			legend = legendFactory.createIntervalLegend(symbols, defaultSymbol,
					useDefault, layer, field);
			return legend;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method initializes panelC
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnlCenter() {
		if (pnlCenter == null) {
			pnlCenter = new JPanel();
		}

		return pnlCenter;
	}

	private class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("ActionEvent con " + e.getActionCommand());

			// modificar el combobox de valor
			if (e.getActionCommand() == "FIELD_SELECTED") {
				fieldSelectedActionPerformed((JComboBox) e.getSource());
			} else if (e.getActionCommand() == "INTERVAL_TYPE") {
				JComboBox cb = (JComboBox) e.getSource();

				if (legend != null
						&& cb.getSelectedIndex() != legend.getType().ordinal()) {
					symbolTable.removeAllItems();
				}
			}

			if (e.getActionCommand() == "ADD_ALL_VALUES") {
				try {
					fillTableValues();
				} catch (IOException exception) {
					throw new RuntimeException(exception);
				}
			} else if (e.getActionCommand() == "ADD_VALUE") {
				Symbolizer sym;

				StyleFactory styleFactory = new StyleFactoryImpl();
				FilterFactory filterFactory = new FilterFactoryImpl();
				Color color = Color.blue;
				int width = 3;

				Class<? extends Geometry> shapeType = layer.getShapeType();

				if (Point.class.isAssignableFrom(shapeType)
						|| MultiPoint.class.isAssignableFrom(shapeType)) {
					Mark mark = styleFactory.getCircleMark();
					mark.setStroke(styleFactory.createStroke(
							filterFactory.literal(color),
							filterFactory.literal(0)));
					mark.setFill(styleFactory.createFill(filterFactory
							.literal(color)));

					Graphic graphic = styleFactory.createDefaultGraphic();
					graphic.graphicalSymbols().clear();
					graphic.graphicalSymbols().add(mark);
					graphic.setSize(filterFactory.literal(width));
					sym = styleFactory.createPointSymbolizer(graphic, null);
				} else if (LineString.class.isAssignableFrom(shapeType)
						|| MultiLineString.class.isAssignableFrom(shapeType)) {
					Stroke stroke = styleFactory.createStroke(
							filterFactory.literal(color),
							filterFactory.literal(width));
					sym = styleFactory.createLineSymbolizer(stroke, null);
				} else if (Polygon.class.isAssignableFrom(shapeType)
						|| MultiPolygon.class.isAssignableFrom(shapeType)) {
					Stroke stroke = styleFactory.createStroke(
							filterFactory.literal(color),
							filterFactory.literal(width));
					Fill fill = styleFactory.createFill(
							filterFactory.literal(color),
							filterFactory.literal(0));
					sym = styleFactory.createPolygonSymbolizer(stroke, fill,
							null);
				} else {
					logger.warn("Unsupported shape type for symbology: "
							+ shapeType + ". Falling back to point style.");
					Mark mark = styleFactory.getCircleMark();
					mark.setStroke(styleFactory.createStroke(
							filterFactory.literal(color),
							filterFactory.literal(1)));
					mark.setFill(styleFactory.createFill(filterFactory
							.literal(color)));

					Graphic graphic = styleFactory.createDefaultGraphic();
					graphic.graphicalSymbols().clear();
					graphic.graphicalSymbols().add(mark);
					graphic.setSize(filterFactory.literal(5));
					sym = styleFactory.createPointSymbolizer(graphic, null);
				}
				sym.setDescription(new DescriptionImpl("0 - 0", "0 - 0"));

				symbolTable.addRow(sym, new Interval(0, 0));
			}

			// Vacia la tabla
			if (e.getActionCommand() == "REMOVE_ALL") {
				symbolTable.removeAllItems();
			}

			// Quitar solo el elemento seleccionado
			if (e.getActionCommand() == "REMOVE") {
				symbolTable.removeSelectedRows();
			}
		}
	}

	protected void fieldSelectedActionPerformed(JComboBox cb) {
		String fieldName = (String) cb.getSelectedItem();
		System.out.println("Nombre del campo: " + fieldName);
		symbolTable.removeAllItems();
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this, "Muestra_los_elementos_de_la_capa_"
				+ "usando_una_gama_de_colores_en_"
				+ "funcion_del_valor_de_un_determinado_campo_de_atributos")
				+ ".";
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon(this.getClass().getClassLoader()
				.getResource("images/Intervalos.png"));
	}

	@Override
	public Class<? extends ILegendPanel> getParentClass() {
		return Quantities.class;
	}

	@Override
	public String getTitle() {
		return PluginServices.getText(this, "Intervalos");
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

	public Class<?> getLegendClass() {
		return Style.class;
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
			} catch (Exception e) {
				logger.error("Cannot read layer metadata", e);
				return false;
			}
		}

		return false;
	}
}
