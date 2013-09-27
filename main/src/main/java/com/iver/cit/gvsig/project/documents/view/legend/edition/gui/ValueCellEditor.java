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
package com.iver.cit.gvsig.project.documents.view.legend.edition.gui;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/**
 * Cell Editor sobre los valores �nicos.
 * 
 * @author Vicente Caballero Navarro
 */
public class ValueCellEditor extends JTextField implements TableCellEditor {
	private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();

	public ValueCellEditor() {
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					stopCellEditing();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancelCellEditing();
				}
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		String value = getText();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}

		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException e) {
		}

		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
		}

		return value;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null) {
			setText("");
		} else {
			setText(value.toString());
		}

		return this;
	}

	@Override
	public void cancelCellEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).editingCanceled(new ChangeEvent(this));
		}
	}

	@Override
	public boolean stopCellEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).editingStopped(new ChangeEvent(this));
		}

		return true;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		listeners.remove(l);
	}
}
