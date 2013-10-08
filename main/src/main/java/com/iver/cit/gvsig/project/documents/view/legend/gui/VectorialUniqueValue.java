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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JBlank;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Legend;
import org.gvsig.legend.LegendFactory;
import org.gvsig.legend.impl.UniqueValueLegend;
import org.gvsig.raster.datastruct.ColorItem;
import org.opengis.feature.simple.SimpleFeatureType;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.gui.styling.JComboBoxColorScheme;
import com.vividsolutions.jts.geom.Geometry;

/**
 * DOCUMENT ME!
 * 
 * @author fjp To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VectorialUniqueValue extends JPanel implements ILegendPanel,
		ActionListener {
	private static final ImageIcon ICON = new ImageIcon(
			VectorialUniqueValue.class.getClassLoader().getResource(
					"images/ValoresUnicos.png"));

	private static final Logger logger = Logger
			.getLogger(VectorialUniqueValue.class);

	private Layer layer;
	private SymbolTable symbolTable;
	protected JComboBox<String> cmbFields;
	private JButton btnRemoveAll;
	private JButton btnRemove;
	private JButton moveUp;
	private JButton moveDown;
	private JCheckBox chbUseDefault = null;
	private JSymbolPreviewButton defaultSymbolPrev;
	private JPanel pnlCenter;
	private JPanel pnlMovBut;
	private JButton btnOpenSymbolLevelsEditor;
	private GridBagLayoutPanel defaultSymbolPanel = new GridBagLayoutPanel();

	private JComboBoxColorScheme cmbColorScheme;

	@Inject
	private LegendFactory legendFactory;

	@Inject
	private DefaultSymbols defaultSymbols;

	public VectorialUniqueValue() {
		super();
		initComponents();
	}

	protected void initComponents() {

		JPanel pnlButtons = new JPanel();

		JButton btnAddAll = new JButton(PluginServices.getText(this,
				"Anadir_todos"));
		btnAddAll.setActionCommand("ADD_ALL_VALUES");
		btnAddAll.addActionListener(this);
		pnlButtons.add(btnAddAll);

		JButton btnAdd = new JButton(PluginServices.getText(this, "Anadir"));
		btnAdd.setActionCommand("ADD_VALUE");
		btnAdd.addActionListener(this);
		pnlButtons.add(btnAdd);

		btnRemoveAll = new JButton(PluginServices.getText(this, "Quitar_todos"));
		btnRemoveAll.setActionCommand("REMOVE_ALL");
		btnRemoveAll.addActionListener(this);
		pnlButtons.add(btnRemoveAll);

		btnRemove = new JButton(PluginServices.getText(this, "Quitar"));
		btnRemove.setActionCommand("REMOVE");
		btnRemove.addActionListener(this);
		pnlButtons.add(btnRemove);

		btnOpenSymbolLevelsEditor = new JButton(PluginServices.getText(this,
				"symbol_levels"));
		btnOpenSymbolLevelsEditor.addActionListener(this);
		btnOpenSymbolLevelsEditor.setActionCommand("OPEN_SYMBOL_LEVEL_EDITOR");
		// TODO gtintegration
		// pnlButtons.add(btnOpenSymbolLevelsEditor);
		btnOpenSymbolLevelsEditor.setEnabled(symbolTable != null
				&& symbolTable.getRowCount() > 0);

		pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());

		cmbFields = new JComboBox<String>();
		cmbFields.setActionCommand("FIELD_SELECTED");
		cmbFields.addActionListener(this);
		cmbFields.setVisible(true);

		JPanel pnlNorth = new JPanel();
		pnlNorth.setLayout(new GridLayout(0, 2));

		GridBagLayoutPanel auxPanel = new GridBagLayoutPanel();
		JLabel lblFieldClassification = new JLabel(PluginServices.getText(this,
				"Campo_de_clasificacion") + ": ");
		auxPanel.add(lblFieldClassification);
		auxPanel.add(cmbFields);
		pnlNorth.add(auxPanel);

		auxPanel = new GridBagLayoutPanel();
		auxPanel.add(new JLabel(PluginServices.getText(this, "color_scheme")
				+ ": "));
		cmbColorScheme = new JComboBoxColorScheme(false);
		cmbColorScheme.addActionListener(this);
		auxPanel.add(cmbColorScheme);
		pnlNorth.add(auxPanel);

		defaultSymbolPanel.add(getChbUseDefault(), null);
		pnlNorth.add(defaultSymbolPanel);
		pnlNorth.add(new JBlank(0, 30));

		this.setLayout(new BorderLayout());
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.add(pnlButtons, BorderLayout.SOUTH);

	}

	private void fillTableValues() {
		String fieldName = (String) cmbFields.getSelectedItem();
		if (fieldName == null) {
			JOptionPane.showMessageDialog(
					(Component) PluginServices.getMainFrame(),
					PluginServices.getText(this, "no_hay_campo_seleccionado"));
			return;
		}

		SimpleFeatureSource source;
		try {
			source = layer.getFeatureSource();
		} catch (Exception e) {
			logger.error("Cannot create legend", e);
			NotificationManager.addWarning(
					PluginServices.getText(this, "error_adding_leyend"), null);
			return;
		}

		if (source.getSchema().getDescriptor(fieldName) == null) {
			NotificationManager.addWarning(
					PluginServices.getText(this, "unrecognized_field_name")
							+ " " + fieldName, null);
			return;
		}

		symbolTable.removeAllItems();

		UniqueValueLegend legend;
		Comparator<Object> defaultComparator = new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		};
		legend = legendFactory.createUniqueValueLegend(layer, fieldName,
				defaultSymbolPrev.getSymbol(), chbUseDefault.isSelected(),
				getColorScheme(), defaultComparator);
		try {
			symbolTable.fillTable(legend.getSymbols(), legend.getValues());
		} catch (IOException e) {
			logger.error("Cannot create legend", e);
			NotificationManager.addWarning(
					PluginServices.getText(this, "error_adding_leyend"), null);
			return;
		}

		btnRemoveAll.setEnabled(true);
		btnRemove.setEnabled(true);
	}

	private Color[] getColorScheme() {
		ColorItem[] colorScheme = cmbColorScheme.getSelectedColors();
		Color[] colors = new Color[colorScheme.length];
		for (int i = 0; i < colorScheme.length; i++) {
			colors[i] = colorScheme[i].getColor();
		}

		return colors;
	}

	private void fillFieldNames() {
		try {
			SimpleFeatureType schema = layer.getFeatureSource().getSchema();
			int fieldCount = schema.getAttributeCount();
			String[] nomFields = new String[fieldCount];
			for (int i = 0; i < fieldCount; i++) {
				nomFields[i] = schema.getDescriptor(i).getLocalName();
			}

			DefaultComboBoxModel<String> cM = new DefaultComboBoxModel<String>(
					nomFields);
			cmbFields.setModel(cM);
		} catch (IOException e) {
			logger.error("Cannot create legend", e);
			NotificationManager.addWarning(
					PluginServices.getText(this, "error_adding_leyend"), null);
		}
	}

	@Override
	public void setData(Layer layer, Legend l) throws IOException {
		this.layer = layer;
		Class<? extends Geometry> shapeType = layer.getShapeType();

		getDefaultSymbolPrev();

		if (symbolTable != null)
			pnlCenter.remove(symbolTable);
		if (pnlMovBut != null)
			pnlCenter.remove(pnlMovBut);

		symbolTable = new SymbolTable(SymbolTable.VALUES_TYPE, shapeType);
		pnlCenter.add(symbolTable, BorderLayout.CENTER);
		pnlCenter.add(getPnlMovBut(), BorderLayout.EAST);

		fillFieldNames();
		symbolTable.removeAllItems();

		Symbolizer defaultSymbol;
		if (l instanceof UniqueValueLegend) {
			UniqueValueLegend legend = (UniqueValueLegend) l;
			getChbUseDefault().setSelected(legend.useDefaultSymbol());
			cmbFields.getModel().setSelectedItem(legend.getFieldName());

			Color[] scheme = legend.getColorScheme();
			ColorItem[] colors = new ColorItem[scheme.length];
			for (int i = 0; i < scheme.length; i++) {
				colors[i] = new ColorItem();
				colors[i].setColor(scheme[i]);
			}
			cmbColorScheme.setSelectedColors(colors);

			symbolTable.fillTable(legend.getSymbols(), legend.getValues());
			chbUseDefault.setSelected(legend.useDefaultSymbol());
			defaultSymbol = legend.getDefaultSymbol();
		} else {
			defaultSymbol = defaultSymbols.createDefaultSymbol(shapeType,
					Color.black, "");
		}
		defaultSymbolPrev.setSymbol(defaultSymbol);
		btnOpenSymbolLevelsEditor.setEnabled(symbolTable != null
				&& symbolTable.getRowCount() > 0);
	}

	private JPanel getPnlMovBut() {
		if (pnlMovBut == null) {
			pnlMovBut = new JPanel();
			pnlMovBut.setLayout(new BoxLayout(pnlMovBut, BoxLayout.Y_AXIS));
			pnlMovBut.add(new JBlank(1, 70));
			pnlMovBut.add(moveUp = new JButton(PluginServices.getIconTheme()
					.get("up-arrow")));
			moveUp.setSize(new Dimension(15, 15));
			pnlMovBut.add(new JBlank(1, 10));
			pnlMovBut.add(moveDown = new JButton(PluginServices.getIconTheme()
					.get("down-arrow")));
			pnlMovBut.add(new JBlank(1, 70));
			moveDown.setActionCommand("MOVE-DOWN");
			moveUp.setActionCommand("MOVE-UP");
			moveDown.addActionListener(this);
			moveUp.addActionListener(this);
		}
		return pnlMovBut;
	}

	private void getDefaultSymbolPrev() {
		if (defaultSymbolPrev == null) {
			defaultSymbolPrev = new JSymbolPreviewButton(null);
			defaultSymbolPrev.setPreferredSize(new Dimension(110, 20));
			defaultSymbolPrev.addActionListener(this);
			defaultSymbolPanel.add(defaultSymbolPrev, null);
		}
	}

	@Override
	public UniqueValueLegend getLegend() {
		Map<Object, Symbolizer> symbols = new HashMap<Object, Symbolizer>();
		for (int i = 0; i < symbolTable.getRowCount(); i++) {
			Object value = symbolTable.getValue(i);
			String description = symbolTable.getLabel(i);
			Symbolizer symbol = symbolTable.getSymbol(i);
			symbol.setDescription(new DescriptionImpl(description, description));
			symbols.put(value, symbol);
		}

		String field = cmbFields.getItemAt(cmbFields.getSelectedIndex());
		Symbolizer defaultSymbol = defaultSymbolPrev.getSymbol();
		boolean useDefaultSymbol = chbUseDefault.isSelected();
		return legendFactory.createUniqueValueLegend(layer, field,
				defaultSymbol, useDefaultSymbol, getColorScheme(), symbols,
				getOrder());
	}

	private Comparator<Object> getOrder() {
		final List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < symbolTable.getRowCount(); i++) {
			values.add(symbolTable.getValue(i));
		}
		return new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				int i1 = values.indexOf(o1);
				int i2 = values.indexOf(o2);
				return i1 - i2;
			}
		};
	}

	private JCheckBox getChbUseDefault() {
		if (chbUseDefault == null) {
			chbUseDefault = new JCheckBox();
			chbUseDefault.setSelected(false);
			chbUseDefault.setText(PluginServices.getText(this, "resto_valores")
					+ ": ");
		}

		return chbUseDefault;
	}

	public void actionPerformed(ActionEvent e) {
		int[] indices = null;

		if (e.getActionCommand() == "MOVE-UP"
				|| e.getActionCommand() == "MOVE-DOWN") {
			indices = symbolTable.getSelectedRows();
		}

		if (e.getActionCommand() == "MOVE-UP") {
			if (indices.length > 0) {
				int classIndex = indices[0];
				int targetPos = Math.max(0, classIndex - 1);
				symbolTable.moveUpRows(classIndex, targetPos, indices.length);
			}
		}

		if (e.getActionCommand() == "MOVE-DOWN") {
			if (indices.length > 0) {
				int classIndex = indices[indices.length - 1];
				int targetPos = Math.min(symbolTable.getRowCount() - 1,
						classIndex + 1);
				symbolTable.moveDownRows(classIndex, targetPos, indices.length);
			}
		}

		// modificar el combobox de valor
		if (e.getActionCommand() == "FIELD_SELECTED") {
			symbolTable.removeAllItems();
			btnOpenSymbolLevelsEditor.setEnabled(false);
		}

		// add all elements by value
		if (e.getActionCommand() == "ADD_ALL_VALUES") {
			fillTableValues();
			btnOpenSymbolLevelsEditor.setEnabled(symbolTable != null
					&& symbolTable.getRowCount() > 0);
		}

		// add only one value
		if (e.getActionCommand() == "ADD_VALUE") {
			Symbolizer symbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), Color.blue, "0 - 0");
			symbolTable.addRow(symbol, 0.0);
			btnOpenSymbolLevelsEditor.setEnabled(true);
		}

		// Vacia la tabla
		if (e.getActionCommand() == "REMOVE_ALL") {
			symbolTable.removeAllItems();
			btnOpenSymbolLevelsEditor.setEnabled(false);
		}

		// Quitar solo el elemento seleccionado
		if (e.getActionCommand() == "REMOVE") {
			symbolTable.removeSelectedRows();
			btnOpenSymbolLevelsEditor.setEnabled(symbolTable.getRowCount() > 0);
		}

		if (e.getActionCommand() == "OPEN_SYMBOL_LEVEL_EDITOR") {
			throw new UnsupportedOperationException("GT integration!");
			// TODO gtintegration
			// ZSort myZSort = null;
			// if (auxLegend != null) {
			// myZSort = ((AbstractClassifiedVectorLegend) getLegend())
			// .getZSort();
			// if (myZSort == null) {
			// myZSort = new ZSort(auxLegend);
			// }
			// }
			// if (myZSort == null && legend != null) {
			// myZSort = new ZSort(legend);
			// }
			// SymbolLevelsWindow sl = new SymbolLevelsWindow(myZSort);
			// PluginServices.getMDIManager().addWindow(sl);
			// auxLegend.setZSort(sl.getZSort());
		}
	}

	public String getDescription() {
		return PluginServices.getText(this, "Dado_un_campo_de_atributos")
				+ ", "
				+ PluginServices
						.getText(this,
								"muestra_los_elementos_de_la_capa_usando_un_simbolo_por_cada_valor_unico")
						.toLowerCase() + ".";
	}

	public ImageIcon getIcon() {
		return ICON;
	}

	public Class<? extends ILegendPanel> getParentClass() {
		return Categories.class;
	}

	public String getTitle() {
		return PluginServices.getText(this, "Valores_unicos");
	}

	public JPanel getPanel() {
		return this;
	}

	public Class<? extends Legend> getLegendClass() {
		return UniqueValueLegend.class;
	}

	public boolean isSuitableFor(Layer layer) {
		return layer.hasFeatures();
	}
}
