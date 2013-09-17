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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import org.gvsig.legend.Interval;

import com.iver.andami.PluginServices;

/**
 * Cell Editor de intervalos. Controla los eventos de edici�n que se realicen
 * sobre la columna de intervalos.
 * 
 * @author Vicente Caballero Navarro
 */
public class IntervalCellEditor extends JButton implements TableCellEditor {
	private ArrayList listeners = new ArrayList();
	private Interval interval;
	private PanelEditInterval editPanel;

	/**
	 * Crea un nuevo FIntervalCellEditor.
	 */
	public IntervalCellEditor() {
		addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					editPanel.setInterval(interval);
					PluginServices.getMDIManager().addWindow(editPanel);
					if (editPanel.isOK()) {
						interval = editPanel.getInterval();
						setBackground(Color.white);
						setText(interval.toString());
						stopCellEditing();
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		});
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					stopCellEditing();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancelCellEditing();
				}
			}
		});
		editPanel = new PanelEditInterval();
		this.setBackground(Color.white);

	}

	/**
	 * Inserta el intervalo.
	 * 
	 * @param i
	 *            DOCUMENT ME!
	 */
	public void setCurrentInterval(Interval i) {
		interval = i;
		this.setText(i.toString());
	}

	// Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return interval;
	}

	// Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		interval = (Interval) value;
		this.setText(interval.toString());
		editPanel.setInterval(interval);
		return this;
	}

	public void cancelCellEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			CellEditorListener l = (CellEditorListener) listeners.get(i);
			ChangeEvent evt = new ChangeEvent(this);
			l.editingCanceled(evt);
		}
	}

	public boolean stopCellEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			CellEditorListener l = (CellEditorListener) listeners.get(i);
			ChangeEvent evt = new ChangeEvent(this);
			l.editingStopped(evt);
		}
		return true;
	}

	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	public void addCellEditorListener(CellEditorListener l) {
		listeners.add(l);
	}

	public void removeCellEditorListener(CellEditorListener l) {
		listeners.remove(l);
	}
}
