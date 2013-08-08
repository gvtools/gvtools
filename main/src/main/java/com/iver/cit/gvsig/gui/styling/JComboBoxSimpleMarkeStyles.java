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
package com.iver.cit.gvsig.gui.styling;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import org.geotools.filter.FilterFactoryImpl;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyleFactoryImpl;

import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolPreviewer;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils.PointStyle;

;

/**
 * JComboBox used by the user to select the different styles of simple marker
 * symbols. The available options are: circle style,square style,cross style,
 * diamond style,x style and triangle style.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class JComboBoxSimpleMarkeStyles extends JComboBox<PointStyle> {
	private Color symColor = Color.BLACK;
	private Color outlineColor = Color.BLACK;
	private boolean outlined = false;

	/**
	 * Constructor method
	 * 
	 */

	public JComboBoxSimpleMarkeStyles() {
		super();
		removeAllItems();
		for (PointStyle pointStyle : PointStyle.values()) {
			addItem(pointStyle);
		}

		setEditable(false);
		setRenderer(new ListCellRenderer<PointStyle>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends PointStyle> list, PointStyle value,
					int index, boolean isSelected, boolean cellHasFocus) {
				StyleFactory styleFactory = new StyleFactoryImpl();
				FilterFactoryImpl filterFactory = new FilterFactoryImpl();

				Mark mark = SymbologyUtils.createMarkFromPointStyle(value);
				mark.setStroke(styleFactory.createStroke(
						filterFactory.literal(outlineColor),
						filterFactory.literal(1)));
				mark.setFill(styleFactory.createFill(filterFactory
						.literal(symColor)));
				if (!outlined) {
					mark.getStroke().setOpacity(filterFactory.literal(0));
				}

				Graphic gr = styleFactory.createDefaultGraphic();
				gr.graphicalSymbols().clear();
				gr.graphicalSymbols().add(mark);
				gr.setSize(filterFactory.literal(10));

				PointSymbolizer sym = styleFactory.createPointSymbolizer(gr,
						null);
				Rule rule = styleFactory.createRule();
				rule.symbolizers().add(sym);

				SymbolPreviewer preview = new SymbolPreviewer();
				preview.setForeground(UIManager
						.getColor(isSelected ? "ComboBox.selectionForeground"
								: "ComboBox.foreground"));
				preview.setBackground(UIManager
						.getColor(isSelected ? "ComboBox.selectionBackground"
								: "ComboBox.background"));
				preview.setSymbol(sym);
				preview.setSize(preview.getWidth(), 20);
				preview.setPreferredSize(new Dimension(preview.getWidth(), 20));
				return preview;
			}

		});
	}

	/**
	 * Establishes the color of the simple marker symbol.
	 * 
	 * @param c
	 *            ,Color
	 */
	public void setSymbolColor(Color c) {
		this.symColor = c;
	}

	/**
	 * Sets the color for the outline of the simple marker symbol
	 * 
	 * @param c
	 *            ,Color
	 */
	public void setOutlineColor(Color c) {
		outlined = c != null;
		outlineColor = c;
	}
}
