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
 * $Id: SimpleLine.java 22765 2008-08-08 07:32:52Z vcaballero $
 * $Log$
 * Revision 1.20  2007-08-14 11:10:20  jvidal
 * javadoc updated
 *
 * Revision 1.19  2007/08/08 11:49:15  jaume
 * refactored to avoid provide more than one EditorTool
 *
 * Revision 1.18  2007/08/07 11:19:06  jvidal
 * javadoc
 *
 * Revision 1.17  2007/08/03 11:37:15  jvidal
 * javadoc
 *
 * Revision 1.16  2007/08/03 11:29:13  jaume
 * refactored AbstractTypeSymbolEditorPanel class name to AbastractTypeSymbolEditor
 *
 * Revision 1.15  2007/08/03 09:03:29  jvidal
 * *** empty log message ***
 *
 * Revision 1.14  2007/07/30 06:59:51  jaume
 * finished (maybe) LineFillSymbol
 *
 * Revision 1.13  2007/07/26 12:28:29  jaume
 * maybe finished ArrowMarkerSymbol and ArrowDecoratorStyle
 *
 * Revision 1.12  2007/07/23 07:08:10  jaume
 * Added support for arrow line decorator (start commiting)
 *
 * Revision 1.11  2007/07/12 10:43:55  jaume
 * *** empty log message ***
 *
 * Revision 1.10  2007/06/29 13:07:33  jaume
 * +PictureLineSymbol
 *
 * Revision 1.9  2007/05/31 09:36:22  jaume
 * *** empty log message ***
 *
 * Revision 1.8  2007/05/21 10:38:27  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/04/27 12:10:17  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/04/05 16:08:34  jaume
 * Styled labeling stuff
 *
 * Revision 1.5  2007/04/04 16:01:13  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/03/26 15:05:26  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/03/21 08:03:03  jaume
 * refactored to use ISymbol instead of FSymbol
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.6  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.5  2007/02/12 15:14:41  jaume
 * refactored interval legend and added graduated symbol legend
 *
 * Revision 1.1.2.4  2007/02/09 11:05:16  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/09 11:00:02  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/08 15:43:05  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.gvsig.gui.beans.listeners.BeanListener;
import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.fmap.core.SymbologyFactory;
import com.iver.cit.gvsig.fmap.core.styles.ArrowDecoratorStyle;
import com.iver.cit.gvsig.fmap.core.styles.ILineStyle;
import com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle;
import com.iver.cit.gvsig.fmap.core.symbols.ISymbol;
import com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;

/**
 * SimpleLine allows the user to store and modify the main properties that
 * define a <b>simple line</b>.
 * <p>
 * <p>
 * This functionality is carried out thanks to two tabs (simple line and arrow
 * decorator)which are included in the panel to edit the properities of a symbol
 * (SymbolEditor)how is explained in AbstractTypeSymbolEditor.
 * <p>
 * <p>
 * The first tab (Simple Line)allows the user to change the color
 * (<b>jccColor</b>), the width (<b>txtWidth</b>) and the style of the line
 * (<b>cmbLinStyles</b>).
 * <p>
 * <p>
 * The second tab (<b>arrowDecorator</b>)allows the user to insert a symbol in
 * the line (for example an arrow to specify its orientation)and to modify it.
 * 
 * @see ArrowDecorator
 * @see AbstractTypeSymbolEditor
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SimpleLine extends AbstractTypeSymbolEditor implements
		ActionListener {

	private ColorChooserPanel jccColor;
	private JIncrementalNumberField txtWidth;
	private ArrayList<JPanel> tabs = new ArrayList<JPanel>();
	private ArrowDecorator arrowDecorator;
	private LineProperties lineProperties;
	private JIncrementalNumberField txtOffset;

	public SimpleLine(SymbolEditor owner) {
		super(owner);
		initialize();
	}

	/**
	 * Initializes the parameters that define a simpleline.To do it, two tabs
	 * are created inside the SymbolEditor panel with default values for the
	 * different attributes of the simple line.This two tabs will be simple line
	 * tab (options of color, width and style of the line)and arrow decorator
	 * tab (options to "decorate" the line with a symbol).
	 */
	private void initialize() {
		JPanel myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		myTab.setName(PluginServices.getText(this, "simple_line"));
		GridBagLayoutPanel aux = new GridBagLayoutPanel();

		// color chooser
		jccColor = new ColorChooserPanel(true);
		jccColor.setAlpha(255);
		aux.addComponent(PluginServices.getText(this, "color") + ":", jccColor);

		// line width
		txtWidth = new JIncrementalNumberField("3", 25, 0,
				Double.POSITIVE_INFINITY, 1);
		aux.addComponent(PluginServices.getText(this, "width") + ":", txtWidth);

		// line offset
		txtOffset = new JIncrementalNumberField("0", 25,
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
		aux.addComponent(PluginServices.getText(this, "offset") + ":",
				txtOffset);

		aux.setPreferredSize(new Dimension(300, 300));
		myTab.add(aux);

		// initialize defaults
		jccColor.setColor(Color.BLACK);
		txtWidth.setDouble(1.0);
		jccColor.addActionListener(this);
		txtWidth.addActionListener(this);
		txtOffset.addActionListener(this);
		tabs.add(myTab);

		// Arrow Decorator
		arrowDecorator = new ArrowDecorator();
		arrowDecorator.addListener(new BeanListener() {

			public void beanValueChanged(Object value) {
				fireSymbolChangedEvent();
			}

		});
		tabs.add(arrowDecorator);

		lineProperties = new LineProperties((float) txtWidth.getDouble());
		lineProperties.addListener(new BeanListener() {

			public void beanValueChanged(Object value) {
				fireSymbolChangedEvent();
			}
		});
		tabs.add(lineProperties);
	}

	public ISymbol getLayer() {
		SimpleLineSymbol layer = new SimpleLineSymbol();
		layer.setLineColor(jccColor.getColor());
		layer.setIsShapeVisible(true);
		// clone the selected style in the combo box

		SimpleLineStyle simplLine = new SimpleLineStyle();

		simplLine.setStroke(lineProperties.getLinePropertiesStyle());
		simplLine.setOffset(-txtOffset.getDouble());

		ArrowDecoratorStyle ads = arrowDecorator.getArrowDecoratorStyle();
		if (ads != null) {
			ads.getMarker().setColor(jccColor.getColor());
		}
		simplLine.setArrowDecorator(arrowDecorator.getArrowDecoratorStyle());
		layer.setLineStyle(simplLine);
		layer.setLineWidth((float) txtWidth.getDouble());

		return layer;
	}

	public String getName() {
		return PluginServices.getText(this, "simple_line");
	}

	public JPanel[] getTabs() {
		return (JPanel[]) tabs.toArray(new JPanel[0]);
	}

	public void refreshControls(ISymbol layer) {
		SimpleLineSymbol sym;
		try {
			if (layer == null) {
				// initialize defaults
				System.err.println(getClass().getName()
						+ ":: should be unreachable code");
				jccColor.setColor(Color.BLACK);
				txtWidth.setDouble(1.0);
				txtOffset.setDouble(0);
			} else {
				sym = (SimpleLineSymbol) layer;
				jccColor.setColor(sym.getColor());
				txtWidth.setDouble(sym.getLineStyle().getLineWidth());
				txtOffset.setDouble(sym.getLineStyle().getOffset() == 0 ? 0
						: -sym.getLineStyle().getOffset());
				arrowDecorator.setArrowDecoratorStyle(sym.getLineStyle()
						.getArrowDecorator());
				/*
				 * this line discards any temp changes in the linestyle widths
				 * made by any previous rendering and sets all the values to
				 * those to be persisted.
				 */
				ILineStyle tempLineStyle = (ILineStyle) SymbologyFactory
						.createStyleFromXML(sym.getLineStyle().getXMLEntity(),
								sym.getDescription());
				lineProperties
						.setLinePropertiesStyle((BasicStroke) tempLineStyle
								.getStroke());
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
		return SimpleLineSymbol.class;
	}

	public void actionPerformed(ActionEvent e) {
		fireSymbolChangedEvent();
	}

	public EditorTool[] getEditorTools() {
		return null;
	}
}