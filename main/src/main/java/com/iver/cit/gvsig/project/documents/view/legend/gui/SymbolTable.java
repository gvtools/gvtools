/*
 * Created on 27-abr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.geotools.styling.Description;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Symbolizer;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.gui.SymbolCellEditor;
import com.iver.cit.gvsig.project.documents.gui.TableSymbolCellRenderer;
import com.iver.cit.gvsig.project.documents.view.legend.edition.gui.IntervalCellEditor;
import com.iver.utiles.swing.jtable.JTable;
import com.iver.utiles.swing.jtable.TextFieldCellEditor;

/**
 * JPanel que contiene la tabla con los s�mbolos intervalos o valores y
 * etiquetado de estos valores.
 * 
 * @author Vicente Caballero Navarro
 */
public class SymbolTable extends JPanel {
	private static final long serialVersionUID = -8694846716328735113L;

	private static final int COLUMN_INDEX_SYMBOL = 0;
	private static final int COLUMN_INDEX_VALUE = 1;
	private static final int COLUMN_INDEX_LABEL = 2;

	public static final String VALUES_TYPE = "values";
	public static final String INTERVALS_TYPE = "intervals";

	private static Hashtable<String, TableCellEditor> cellEditors = new Hashtable<String, TableCellEditor>();

	static {
		cellEditors.put(INTERVALS_TYPE, new IntervalCellEditor());
	}

	private JTable table;
	private SymbolTableModel model;
	private String type;

	/**
	 * Crea un nuevo FSymbolTable.
	 * 
	 * @param type
	 *            tipo de valor si es intervalo: "intervals" y si es por
	 *            valores: "values".
	 */
	public SymbolTable(String type) {
		super(new GridLayout(1, 0));
		this.type = type;

		model = new SymbolTableModel();

		table = new JTable();
		table.setModel(model);
		table.setPreferredScrollableViewportSize(new Dimension(480, 110));

		if (cellEditors.get(type) == null)
			throw new IllegalArgumentException("Symbol table type not set!");

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Set up columns
		setupSymbolColumn();
		setupValueColumn();
		setupLabelColumn();

		// Add the scroll pane to this panel.
		add(scrollPane);
		table.setRowSelectionAllowed(true);
	}

	public int getRowCount() {
		return model.getRowCount();
	}

	public Symbolizer getSymbol(int row) {
		return model.getSymbol(row);
	}

	public Object getValue(int row) {
		return model.getValue(row);
	}

	public String getLabel(int row) {
		return model.getLabel(row);
	}

	public void removeSelectedRows() {
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}

		int[] selectedRows = table.getSelectedRows();

		for (int i = selectedRows.length - 1; i >= 0; i--) {
			model.removeRow(selectedRows[i]);
		}
	}

	public void fillTable(Symbolizer[] symbols, Object[] values) {
		for (int i = 0; i < symbols.length; i++) {
			if (values[i] != null) {
				addRow(symbols[i], values[i]);
			}
		}
	}

	public void addRow(Symbolizer symbolizer, Object value) {
		String desc = "";
		Description description = symbolizer.getDescription();
		if (description != null && description.getTitle() != null) {
			desc = description.getTitle().toString();
		}
		Object[] row = new Object[] { symbolizer, value, desc };
		model.addRow(row);
	}

	public void removeAllItems() {
		model = new SymbolTableModel();
		table.setModel(model);
		setupSymbolColumn();
		setupValueColumn();
		setupLabelColumn();
	}

	private void setupLabelColumn() {
		TableColumn column = table.getColumnModel().getColumn(
				COLUMN_INDEX_LABEL);
		column.setCellEditor(new TextFieldCellEditor());
	}

	private void setupValueColumn() {
		TableColumn column = table.getColumnModel().getColumn(
				COLUMN_INDEX_VALUE);
		column.setCellEditor(cellEditors.get(type));
	}

	private void setupSymbolColumn() {
		TableColumn column = table.getColumnModel().getColumn(
				COLUMN_INDEX_SYMBOL);

		// Set up the editor
		column.setMaxWidth(100);
		column.setWidth(60);
		column.setPreferredWidth(60);
		column.setMinWidth(50);

		SymbolCellEditor symboleditor = new SymbolCellEditor();
		column.setCellEditor(symboleditor);

		TableSymbolCellRenderer renderer = new TableSymbolCellRenderer(true);
		column.setCellRenderer(renderer);
	}

	public void moveDownRows(int startPos, int endPos, int numOfElements) {
		int rowCount = table.getRowCount();

		if (startPos > endPos)
			return;
		if (startPos >= rowCount - 1)
			return;
		if (startPos == rowCount - 1)
			return;

		Object[][] values = new Object[rowCount][3];
		for (int i = 0; i < rowCount; i++) {
			values[i][0] = table.getModel().getValueAt(i, 0);
			values[i][1] = table.getModel().getValueAt(i, 1);
			values[i][2] = table.getModel().getValueAt(i, 2);
		}

		Object[][] aux = new Object[numOfElements][3];
		for (int i = 0; i < numOfElements; i++) {

			aux[numOfElements - i - 1][0] = values[startPos - i][0];
			aux[numOfElements - i - 1][1] = values[startPos - i][1];
			aux[numOfElements - i - 1][2] = values[startPos - i][2];
		}

		Object[][] targetVal = { { values[endPos][0], values[endPos][1],
				values[endPos][2] } };

		values[startPos - numOfElements + 1][0] = targetVal[0][0];
		values[startPos - numOfElements + 1][1] = targetVal[0][1];
		values[startPos - numOfElements + 1][2] = targetVal[0][2];

		for (int i = 0; i < numOfElements; i++) {
			values[endPos - i][0] = aux[numOfElements - i - 1][0];
			values[endPos - i][1] = aux[numOfElements - i - 1][1];
			values[endPos - i][2] = aux[numOfElements - i - 1][2];
		}

		Symbolizer[] symbols = new Symbolizer[rowCount];
		Object[] objects = new Object[rowCount];

		for (int i = 0; i < rowCount; i++) {
			symbols[i] = (Symbolizer) values[i][COLUMN_INDEX_SYMBOL];
			objects[i] = values[i][COLUMN_INDEX_VALUE];
		}

		removeAllItems();
		fillTable(symbols, objects);
		table.addRowSelectionInterval(endPos - numOfElements + 1, endPos);
	}

	public void moveUpRows(int startPos, int endPos, int numOfElements) {

		if (startPos == 0)
			return;
		if (endPos > startPos)
			return;

		int rowCount = model.getRowCount();

		Object[][] values = new Object[rowCount][3];
		for (int i = 0; i < rowCount; i++) {
			values[i][0] = model.getValueAt(i, 0);
			values[i][1] = model.getValueAt(i, 1);
			values[i][2] = model.getValueAt(i, 2);
		}

		Object[][] aux = new Object[numOfElements][3];
		for (int i = 0; i < numOfElements; i++) {

			aux[i][0] = values[startPos + i][0];
			aux[i][1] = values[startPos + i][1];
			aux[i][2] = values[startPos + i][2];
		}

		Object[][] targetVal = { { values[endPos][0], values[endPos][1],
				values[endPos][2] } };

		values[startPos + numOfElements - 1][0] = targetVal[0][0];
		values[startPos + numOfElements - 1][1] = targetVal[0][1];
		values[startPos + numOfElements - 1][2] = targetVal[0][2];

		for (int i = 0; i < numOfElements; i++) {

			values[endPos + i][0] = aux[i][0];
			values[endPos + i][1] = aux[i][1];
			values[endPos + i][2] = aux[i][2];
		}

		Symbolizer[] symbols = new Symbolizer[rowCount];
		Object[] objects = new Object[rowCount];

		for (int i = 0; i < rowCount; i++) {
			symbols[i] = (Symbolizer) values[i][COLUMN_INDEX_SYMBOL];
			objects[i] = values[i][COLUMN_INDEX_VALUE];
		}

		removeAllItems();
		fillTable(symbols, objects);
		table.addRowSelectionInterval(endPos, endPos + numOfElements - 1);
	}

	private class SymbolTableModel extends DefaultTableModel {
		private String[] columnNames = {
				PluginServices.getText(this, "Simbolo"),
				PluginServices.getText(this, "Valor"),
				PluginServices.getText(this, "Etiqueta") };

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		@Override
		public Class<?> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			return true;
		}

		@Override
		public Object getValueAt(int row, int column) {
			if (column == COLUMN_INDEX_LABEL) {
				return getSymbol(row).getDescription().getTitle().toString();
			} else {
				return super.getValueAt(row, column);
			}
		}

		@Override
		public void setValueAt(Object value, int row, int column) {
			if (column == COLUMN_INDEX_SYMBOL) {
				Symbolizer oldSymbol = getSymbol(row);
				Symbolizer newSymbol = (Symbolizer) value;
				newSymbol.setDescription(oldSymbol.getDescription());
				super.setValueAt(newSymbol, row, column);
			} else {
				if (column == COLUMN_INDEX_LABEL) {
					Symbolizer symbol = getSymbol(row);
					symbol.setDescription(new DescriptionImpl((String) value,
							(String) value));
					setValueAt(symbol, row, 0);
				}

				super.setValueAt(value, row, column);
			}
		}

		private Symbolizer getSymbol(int row) {
			return (Symbolizer) model.getValueAt(row, COLUMN_INDEX_SYMBOL);
		}

		private String getLabel(int row) {
			return (String) model.getValueAt(row, COLUMN_INDEX_LABEL);
		}

		private Object getValue(int row) {
			return model.getValueAt(row, COLUMN_INDEX_VALUE);
		}
	}
}
