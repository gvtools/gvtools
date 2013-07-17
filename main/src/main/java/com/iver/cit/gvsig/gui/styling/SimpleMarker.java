/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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
 * $Id: SimpleMarker.java 27831 2009-04-06 12:35:11Z vcaballero $
 * $Log$
 * Revision 1.15  2007-08-14 11:10:20  jvidal
 * javadoc updated
 *
 * Revision 1.14  2007/08/08 11:49:15  jaume
 * refactored to avoid provide more than one EditorTool
 *
 * Revision 1.13  2007/08/07 11:20:11  jvidal
 * javadoc
 *
 * Revision 1.12  2007/08/03 11:29:13  jaume
 * refactored AbstractTypeSymbolEditorPanel class name to AbastractTypeSymbolEditor
 *
 * Revision 1.11  2007/07/18 06:56:03  jaume
 * continuing with cartographic support
 *
 * Revision 1.10  2007/07/12 10:43:55  jaume
 * *** empty log message ***
 *
 * Revision 1.9  2007/05/31 09:36:22  jaume
 * *** empty log message ***
 *
 * Revision 1.8  2007/05/29 15:47:06  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/05/21 10:38:27  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/05/09 16:08:14  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2007/04/27 12:10:17  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/04/05 16:08:34  jaume
 * Styled labeling stuff
 *
 * Revision 1.3  2007/04/04 16:01:14  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.4  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/08 15:43:04  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.1.2.2  2007/01/30 18:10:10  jaume
 * start commiting labeling stuff
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.fmap.core.symbols.ISymbol;
import com.iver.cit.gvsig.fmap.core.symbols.SimpleMarkerSymbol;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;

/**
 * SimpleMarker allows the user to store and modify the main properties that
 * define a <b>simple marker</b>.
 * <p>
 * <p>
 * This functionality is carried out thanks to a tab (simple marker and mask)
 * which are included in the panel to edit the properities of a symbol
 * (SymbolEditor) how is explained in AbstractTypeSymbolEditor.
 * <p>
 * <p>
 * This tab (Simple Marker)allows the user to change the different attributes
 * which are color (<b>jccColor</b>),text size (<b>txtSize</b>),text offset
 * (<b>txtXOffset</b> and <b>txtXOffset</b>), style of the marker (<b></b>) the
 * width (<b>txtWidth</b>) and the style of the line (<b>cmbStyle</b>) and the
 * color of the outline(<b>jccOutlineColor</b>).
 * <p>
 * 
 * @see Mask
 * @see AbstractTypeSymbolEditor
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SimpleMarker extends AbstractTypeSymbolEditor implements
		ActionListener, FocusListener {

	private ArrayList tabs = new ArrayList();
	private ColorChooserPanel jccColor;
	private JIncrementalNumberField txtSize;
	private JIncrementalNumberField txtXOffset;
	private JIncrementalNumberField txtYOffset;
	// TODO: Comentarizado hasta que mask esté acabado
	// private Mask mask;
	private JComboBoxSimpleMarkeStyles cmbStyle;
	private JCheckBox chkUseOutline;
	private ColorChooserPanel jccOutlineColor;

	public SimpleMarker(SymbolEditor owner) {
		super(owner);
		initialize();
	}

	/**
	 * Initializes the parameters that define a simplemarker.To do it, a tab is
	 * created inside the SymbolEditor panel with default values for the
	 * different attributes of the simple marker.Also, a mask will be added as a
	 * new tab.
	 */

	private void initialize() {
		JPanel myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		myTab.setName(PluginServices.getText(this, "simple_marker"));
		GridBagLayoutPanel aux = new GridBagLayoutPanel();

		// color chooser
		jccColor = new ColorChooserPanel(true);
		jccColor.setAlpha(255);

		aux.addComponent(PluginServices.getText(this, "color") + ":", jccColor);

		// marker width
		txtSize = new JIncrementalNumberField("5", 25);
		aux.addComponent(PluginServices.getText(this, "size") + ":", txtSize);
		txtSize.setDouble(5);

		// marker xOffset
		txtXOffset = new JIncrementalNumberField("0", 25);
		aux.addComponent(PluginServices.getText(this, "x_offset") + ":",
				txtXOffset);

		// marker width
		txtYOffset = new JIncrementalNumberField("0", 25);
		aux.addComponent(PluginServices.getText(this, "y_offset") + ":",
				txtYOffset);

		// marker style
		cmbStyle = new JComboBoxSimpleMarkeStyles();
		aux.addComponent(PluginServices.getText(this, "marker_style") + ":",
				cmbStyle);

		// use outline
		chkUseOutline = new JCheckBox(PluginServices.getText(this,
				"use_outline"));
		aux.addComponent(chkUseOutline);

		// outline color
		jccOutlineColor = new ColorChooserPanel(true);
		jccOutlineColor.setAlpha(255);
		aux.addComponent(PluginServices.getText(this, "outline_color") + ":",
				jccOutlineColor);

		aux.setPreferredSize(new Dimension(300, 300));
		myTab.add(aux);

		// initialize defaults
		jccColor.setColor(Color.BLACK);
		cmbStyle.setSymbolColor(jccColor.getColor());

		jccColor.addActionListener(this);
		txtSize.addActionListener(this);
		txtSize.addFocusListener(this);
		txtXOffset.addActionListener(this);
		txtXOffset.addFocusListener(this);
		txtYOffset.addActionListener(this);
		txtYOffset.addFocusListener(this);
		cmbStyle.addActionListener(this);
		chkUseOutline.addActionListener(this);
		jccOutlineColor.addActionListener(this);

		tabs.add(myTab);

		// mask = new Mask(this);
		// tabs.add(mask);
	}

	public ISymbol getLayer() {
		SimpleMarkerSymbol layer = new SimpleMarkerSymbol();
		layer.setColor(jccColor.getColor());
		layer.setIsShapeVisible(true);
		layer.setSize(txtSize.getDouble());
		// layer.setUnit(owner.getUnit());
		// layer.setReferenceSystem(owner.getUnitsReferenceSystem());
		layer.setOffset(new Point2D.Double(txtXOffset.getDouble(), txtYOffset
				.getDouble()));
		// layer.setMask(mask.getMask());
		layer.setStyle(((Integer) cmbStyle.getSelectedItem()).intValue());
		layer.setOutlined(chkUseOutline.isSelected());
		layer.setOutlineColor(jccOutlineColor.getColor());
		return layer;
	}

	public String getName() {
		return PluginServices.getText(this, "simple_marker_symbol");
	}

	public JPanel[] getTabs() {
		return (JPanel[]) tabs.toArray(new JPanel[0]);
	}

	public void refreshControls(ISymbol layer) {
		SimpleMarkerSymbol sym;
		try {
			if (layer == null) {
				// initialize defaults
				System.err
						.println("SimpleLine.java:: should be unreachable code");
				jccColor.setColor(Color.BLACK);

				txtSize.setDouble(1.0);
				txtXOffset.setDouble(0.0);
				txtYOffset.setDouble(0.0);
			} else {
				sym = (SimpleMarkerSymbol) layer;
				jccColor.setColor(sym.getColor());

				txtSize.setDouble(sym.getSize());
				txtXOffset.setDouble(sym.getOffset().getX());
				txtYOffset.setDouble(sym.getOffset().getY());
				cmbStyle.setSymbolColor(sym.getColor());
				chkUseOutline.setSelected(sym.hasOutline());
				cmbStyle.setOutlineColor(sym.getOutlineColor());
				cmbStyle.setSelectedItem(new Integer(sym.getStyle()));
			}
		} catch (IndexOutOfBoundsException ioEx) {
			NotificationManager.addWarning("Symbol layer index out of bounds",
					ioEx);
		} catch (ClassCastException ccEx) {
			NotificationManager.addWarning(
					"Illegal casting from " + layer.getClassName() + " to "
							+ getSymbolClass().getName() + ".", ccEx);

		}
	}

	public Class getSymbolClass() {
		return SimpleMarkerSymbol.class;
	}

	public void actionPerformed(ActionEvent e) {
		fireSymbolChangedEvent();
	}

	public EditorTool[] getEditorTools() {
		return null;
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	public void focusLost(FocusEvent e) {
		fireSymbolChangedEvent();

	}
}