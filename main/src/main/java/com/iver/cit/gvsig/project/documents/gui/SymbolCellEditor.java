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
package com.iver.cit.gvsig.project.documents.gui;

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

import org.geotools.styling.Symbolizer;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.legend.gui.PanelEditSymbol;

/**
 * Cell Editor de ISymbols. Controla los eventos de edici�n que se realicen
 * sobre la columna de s�mbolos.
 * 
 * @author Vicente Caballero Navarro
 */
public class SymbolCellEditor extends JButton implements TableCellEditor {
	private ArrayList listeners = new ArrayList();
	private Symbolizer symbol;
	private PanelEditSymbol symbolPanel;

	public SymbolCellEditor() {
		addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					symbolPanel.setSymbol(symbol);
					// TODO gtintegration
					// symbolPanel.setShapeType(symbol.getSymbolType());
					PluginServices.getMDIManager().addWindow(symbolPanel);
					if (symbolPanel.isOK()) {
						symbol = symbolPanel.getSymbol();
						symbol.setDescription((symbolPanel.getSymbol())
								.getDescription());
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
		symbolPanel = new PanelEditSymbol();

	}

	// Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return symbol;
	}

	// Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		symbol = (Symbolizer) value;
		// setBackground(symbol.getColor());

		return this;
	}

	/**
	 * DOCUMENT ME!
	 */
	public void cancelCellEditing() {
		if (symbol != null) {
			// setBackground(symbol.getColor());
		}

		for (int i = 0; i < listeners.size(); i++) {
			CellEditorListener l = (CellEditorListener) listeners.get(i);
			ChangeEvent evt = new ChangeEvent(this);
			l.editingCanceled(evt);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean stopCellEditing() {
		for (int i = 0; i < listeners.size(); i++) {
			CellEditorListener l = (CellEditorListener) listeners.get(i);
			ChangeEvent evt = new ChangeEvent(this);
			l.editingStopped(evt);
		}

		return true;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param anEvent
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param anEvent
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param l
	 *            DOCUMENT ME!
	 */
	public void addCellEditorListener(CellEditorListener l) {
		listeners.add(l);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param l
	 *            DOCUMENT ME!
	 */
	public void removeCellEditorListener(CellEditorListener l) {
		listeners.remove(l);
	}
 }
