/*
 * Created on 30-abr-2004
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.geotools.styling.Description;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.styling.SymbolSelector;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SingleSymbol extends JPanel implements ILegendPanel,
		ActionListener {
	private JPanel symbolPanel = null;
	private Class<? extends Geometry> shapeType;
	private GridBagLayoutPanel legendPanel = null;
	private SymbolPreviewer symbolPreviewComponent;
	private JButton btnOpenSymbolSelector;
	private JTextField txtLabel;
	private JButton btnOpenSymbolLevelsEditor;
	private Symbolizer style;

	public SingleSymbol() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(new java.awt.Dimension(490, 379));
		this.add(getSymbolPanel(), null);
		this.add(getLegendPanel(), null);

	}

	public void setData(Layer lyr, Symbolizer style) {
		shapeType = lyr.getShapeType();
		setSymbol(style);
		this.style = SymbologyUtils.clone(style);
		getSymbolPreviewPanel().setSymbol(this.style);
		getBtnOpenSymbolLevelsEditor().setEnabled(style != null);
		this.txtLabel.setText(style.getDescription().getAbstract().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.gui.legendmanager.panels.ILegendPanel#getLegend()
	 */
	public Symbolizer getLegend() {
		if (this.style == null) {
			this.style = getSymbolPreviewPanel().getSymbol();
			this.style.getDescription().setAbstract(txtLabel.getText());
			this.style.getDescription().setTitle(txtLabel.getText());
		}

		return SymbologyUtils.clone(style);
	}

	public String getDescription() {
		return PluginServices
				.getText(this,
						"Muestra_todos_los_elementos_de_una_capa_usando_el_mismo_simbolo");
	}

	public Class<? extends ILegendPanel> getParentClass() {
		return Features.class;
	}

	public String getTitle() {
		return PluginServices.getText(this, "Simbolo_unico");
	}

	public JPanel getPanel() {
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(this.getClass().getClassLoader()
				.getResource("images/single-symbol.png"));
	}

	public Class getLegendClass() {
		return Style.class;
	}

	/**
	 * This method initializes symbolPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSymbolPanel() {
		if (symbolPanel == null) {
			symbolPanel = new JPanel();
			symbolPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "symbol"),
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			symbolPanel.add(getSymbolPreviewPanel());
			symbolPanel.add(getBtnOpenSymbolSelector());
			symbolPanel.add(getBtnOpenSymbolLevelsEditor());
		}
		return symbolPanel;
	}

	private JButton getBtnOpenSymbolLevelsEditor() {
		if (btnOpenSymbolLevelsEditor == null) {
			btnOpenSymbolLevelsEditor = new JButton(PluginServices.getText(
					this, "symbol_levels"));
			btnOpenSymbolLevelsEditor.addActionListener(this);
			btnOpenSymbolLevelsEditor.setEnabled(style != null);
		}

		return btnOpenSymbolLevelsEditor;
	}

	private JButton getBtnOpenSymbolSelector() {
		if (btnOpenSymbolSelector == null) {
			btnOpenSymbolSelector = new JButton();
			btnOpenSymbolSelector.setText(PluginServices.getText(this,
					"choose_symbol"));
			btnOpenSymbolSelector.addActionListener(this);
		}
		return btnOpenSymbolSelector;
	}

	private SymbolPreviewer getSymbolPreviewPanel() {
		if (symbolPreviewComponent == null) {
			symbolPreviewComponent = new SymbolPreviewer();
			symbolPreviewComponent
					.setBorder(BorderFactory.createBevelBorder(1));
			symbolPreviewComponent.setPreferredSize(new Dimension(120, 40));
		}
		return symbolPreviewComponent;
	}

	/**
	 * This method initializes legendPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private GridBagLayoutPanel getLegendPanel() {
		if (legendPanel == null) {
			legendPanel = new GridBagLayoutPanel();
			legendPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "legend"),
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			legendPanel
					.addComponent(
							PluginServices.getText(this,
									"label_text_in_the_TOC") + ":",
							txtLabel = new JTextField(25));
			txtLabel.addActionListener(this);
		}
		return legendPanel;
	}

	public void setShapeType(Class<? extends Geometry> shapeType) {
		this.shapeType = shapeType;
	}

	public void setSymbol(Symbolizer style) {
		setOnlySymbol(style);
		if (style.getDescription() != null)
			txtLabel.setText(style.getDescription().getTitle().toString());
		// else
		// txtLabel.setText(" ("+PluginServices.getText(this, "current")+")");
	}

	private void setOnlySymbol(Symbolizer symbol) {
		getSymbolPreviewPanel().setSymbol(symbol);
		style = symbol;
	}

	public Symbolizer getSymbol() {
		Symbolizer symbol = getSymbolPreviewPanel().getSymbol();
		symbol.getDescription().setAbstract(txtLabel.getText());
		symbol.getDescription().setTitle(txtLabel.getText());
		return symbol;
	}

	public boolean isSuitableFor(Layer layer) {
		// TODO gtintegration
		// return (layer instanceof FLyrVect);
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		JComponent c = (JComponent) e.getSource();
		if (c.equals(getBtnOpenSymbolSelector())) {
			Class<? extends Symbolizer> symbolType;
			if (Point.class.isAssignableFrom(shapeType)
					|| MultiPoint.class.isAssignableFrom(shapeType)) {
				symbolType = PointSymbolizer.class;
			} else if (LineString.class.isAssignableFrom(shapeType)
					|| MultiLineString.class.isAssignableFrom(shapeType)) {
				symbolType = LineSymbolizer.class;
			} else if (Polygon.class.isAssignableFrom(shapeType)
					|| MultiPolygon.class.isAssignableFrom(shapeType)) {
				symbolType = PolygonSymbolizer.class;
			} else {
				throw new UnsupportedOperationException();
			}
			ISymbolSelector se = SymbolSelector.createSymbolSelector(
					getSymbol(), symbolType);
			PluginServices.getMDIManager().addWindow(se);
			Symbolizer sym = se.getSelectedObject();
			if (sym != null) {
				// no symbol, no changes
				setOnlySymbol(sym);
			}
		} else if (c.equals(getBtnOpenSymbolLevelsEditor())) {
			// TODO gtintegration
			throw new UnsupportedOperationException("Unsupported symbol levels");
			// ZSort myZSort = null;
			// if (style != null) {
			// myZSort = style.getZSort();
			// if (myZSort == null) {
			// myZSort = new ZSort(style);
			// }
			// SymbolLevelsWindow sl = new SymbolLevelsWindow(myZSort);
			// PluginServices.getMDIManager().addWindow(sl);
			// this.style.setZSort(sl.getZSort());
			// }
		} else if (c.equals(txtLabel)) {
			Description description = getSymbolPreviewPanel().getSymbol()
					.getDescription();
			description.setAbstract(txtLabel.getText());
			description.setTitle(txtLabel.getText());
		}
	}
}