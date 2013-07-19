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

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import org.geotools.filter.FilterFactoryImpl;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyleFactoryImpl;

import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolPreviewer;
import com.iver.utiles.swing.JComboBox;

/**
 * JComboBox used by the user to select the different styles of simple marker
 * symbols. The available options are: circle style,square style,cross style,
 * diamond style,x style and triangle style.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class JComboBoxSimpleMarkeStyles extends JComboBox<MyItem> {
	public static final int CIRCLE_STYLE = 0;
	public static final int SQUARE_STYLE = 1;
	public static final int CROSS_STYLE = 2;
	public static final int DIAMOND_STYLE = 3;
	public static final int X_STYLE = 4;
	public static final int TRIANGLE_STYLE = 5;
	public static final int STAR_STYLE = 6;

	private Color symColor = Color.BLACK;
	private Color outlineColor = Color.BLACK;
	private boolean outlined = false;
	static MyItem[] pointTypes = new MyItem[] { new MyItem(CIRCLE_STYLE),
			new MyItem(SQUARE_STYLE), new MyItem(CROSS_STYLE),
			new MyItem(DIAMOND_STYLE), new MyItem(X_STYLE),
			new MyItem(TRIANGLE_STYLE), new MyItem(STAR_STYLE), };

	/**
	 * Constructor method
	 * 
	 */

	public JComboBoxSimpleMarkeStyles() {
		super();
		removeAllItems();
		for (int i = 0; i < pointTypes.length; i++) {
			addItem(pointTypes[i]);
		}

		setEditable(false);
		setRenderer(new ListCellRenderer<MyItem>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends MyItem> list, MyItem value, int index,
					boolean isSelected, boolean cellHasFocus) {
				StyleFactory styleFactory = new StyleFactoryImpl();
				FilterFactoryImpl filterFactory = new FilterFactoryImpl();

				Mark mark = styleFactory.getCircleMark();
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

				// gtintegration: use pointTypes
				// mySymbol.setSymbol(value.style);

				PointSymbolizer sym = styleFactory.createPointSymbolizer(gr,
						null);
				Rule rule = styleFactory.createRule();
				rule.symbolizers().add(sym);
				FeatureTypeStyle fts = styleFactory
						.createFeatureTypeStyle(new Rule[] { rule });
				Style style = styleFactory.createStyle();
				style.featureTypeStyles().add(fts);

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

	public Object getSelectedItem() {
		return new Integer(((MyItem) super.getSelectedItem()).style);
	}

	public void setSelectedItem(Object item) {
		if (item instanceof Integer) {
			int myItem = ((Integer) item).intValue();
			for (int i = 0; i < pointTypes.length; i++) {
				if (myItem == pointTypes[i].style)
					setSelectedIndex(i);
			}
		}
		super.setSelectedItem(item);
	}
}

/**
 * Used to store the different options that the JComboBoxsimplemarkerStyles
 * shows to the user.
 * 
 */
class MyItem {
	int style;

	/**
	 * Constructor method
	 * 
	 * @param style
	 */
	MyItem(int style) {
		this.style = style;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Integer) {
			Integer integer = (Integer) obj;
			return integer.intValue() == style;
		}

		if (obj instanceof MyItem) {
			return ((MyItem) obj).style == style;

		}
		return super.equals(obj);
	}
}
