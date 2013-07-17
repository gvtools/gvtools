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
 * $Id: SimpleFill.java 22765 2008-08-08 07:32:52Z vcaballero $
 * $Log$
 * Revision 1.17  2007-09-19 10:45:05  jvidal
 * bug solved
 *
 * Revision 1.16  2007/08/16 06:54:35  jvidal
 * javadoc updated
 *
 * Revision 1.15  2007/08/10 07:28:25  jaume
 * translations
 *
 * Revision 1.14  2007/08/08 11:49:15  jaume
 * refactored to avoid provide more than one EditorTool
 *
 * Revision 1.13  2007/08/08 10:16:53  jvidal
 * javadoc
 *
 * Revision 1.12  2007/08/03 11:29:13  jaume
 * refactored AbstractTypeSymbolEditorPanel class name to AbastractTypeSymbolEditor
 *
 * Revision 1.11  2007/07/23 07:01:13  jaume
 * outline setting bug fixed
 *
 * Revision 1.10  2007/07/12 10:43:55  jaume
 * *** empty log message ***
 *
 * Revision 1.9  2007/06/29 13:07:33  jaume
 * +PictureLineSymbol
 *
 * Revision 1.8  2007/05/21 10:38:27  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/05/09 16:08:14  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/04/05 16:08:34  jaume
 * Styled labeling stuff
 *
 * Revision 1.5  2007/04/04 16:01:14  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/03/30 09:39:45  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/03/28 16:44:08  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.4  2007/02/21 16:09:35  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/08 15:43:05  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/16 11:52:11  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/11/13 09:15:23  jaume
 * javadoc and some clean-up
 *
 * Revision 1.3  2006/11/06 16:06:52  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/10/30 19:30:35  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/27 12:41:09  jaume
 * GUI
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JBlank;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.fmap.core.FShape;
import com.iver.cit.gvsig.fmap.core.symbols.ILineSymbol;
import com.iver.cit.gvsig.fmap.core.symbols.ISymbol;
import com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;
import com.iver.cit.gvsig.project.documents.view.legend.gui.JSymbolPreviewButton;

/**
 * <b>SimpleFill</b> allows to store and modify the properties that fills a
 * polygon with a padding and an outline
 * <p>
 * <p>
 * This functionality is carried out thanks to a tab (simple fill)which is
 * included in the panel to edit the properities of a symbol (SymbolEditor)how
 * is explained in AbstractTypeSymbolEditor.
 * <p>
 * <p>
 * This tab permits the user to change the color of the padding
 * (<b>jccFillColor</b>)and its outline (<b>btnOutline</b>).Also the user has
 * the options to modify the opacity
 * (<b>sldFillTransparency,sldOutlineTransparency</b>) for both attributes and
 * the width <b>txtOutlineWidth</b> (only for the outline).
 * 
 * 
 * @see AbstractTypeSymbolEditor
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SimpleFill extends AbstractTypeSymbolEditor implements
		ActionListener, ChangeListener {
	private ColorChooserPanel jccFillColor;
	private JIncrementalNumberField txtOutlineWidth;
	private ArrayList tabs = new ArrayList();
	private JSymbolPreviewButton btnOutline;
	private JSlider sldOutlineTransparency;
	private int outlineAlpha;
	private ILineSymbol outline;
	private JCheckBox useBorder;

	public SimpleFill(SymbolEditor owner) {
		super(owner);
		initialize();
	}

	public String getName() {
		return PluginServices.getText(SimpleFill.class, "simple_fill_symbol");
	}

	public JPanel[] getTabs() {
		return (JPanel[]) tabs.toArray(new JPanel[0]);
	}

	/**
	 * Initializes the parameters that allows the user to fill the padding of a
	 * polygon with a simplefill style.To do it, a tab (simple fill) is created
	 * inside the SymbolEditor panel with default values for the different
	 * attributes.
	 */
	private void initialize() {
		JPanel myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		myTab.setName(PluginServices.getText(this, "simple_fill"));
		GridBagLayoutPanel aux = new GridBagLayoutPanel();

		jccFillColor = new ColorChooserPanel(true, true);
		jccFillColor.setAlpha(255);
		aux.addComponent(PluginServices.getText(this, "fill_color") + ":",
				jccFillColor);

		JPanel aux2 = new JPanel();
		btnOutline = new JSymbolPreviewButton(FShape.LINE);
		btnOutline.setPreferredSize(new Dimension(100, 35));
		aux2.add(btnOutline);

		aux.addComponent(new JBlank(10, 10));
		useBorder = new JCheckBox(PluginServices.getText(this, "use_outline"));
		aux.addComponent(useBorder, aux2);
		aux.addComponent(new JBlank(10, 10));

		sldOutlineTransparency = new JSlider();
		sldOutlineTransparency.setValue(100);
		aux.addComponent(PluginServices.getText(this, "outline") + ":", aux2);
		aux.addComponent(PluginServices.getText(this, "outline_opacity") + ":",
				sldOutlineTransparency);
		txtOutlineWidth = new JIncrementalNumberField("", 25, 0,
				Double.MAX_VALUE, 1);
		aux.addComponent(PluginServices.getText(this, "outline_width") + ":",
				txtOutlineWidth);
		myTab.add(aux);

		useBorder.addActionListener(this);
		jccFillColor.addActionListener(this);
		btnOutline.addActionListener(this);
		txtOutlineWidth.addActionListener(this);
		sldOutlineTransparency.addChangeListener(this);
		tabs.add(myTab);
	}

	public ISymbol getLayer() {

		SimpleFillSymbol layer = new SimpleFillSymbol();
		layer.setHasFill(false);
		layer.setHasOutline(false);

		layer.setHasOutline(useBorder.isSelected());

		outline = (ILineSymbol) btnOutline.getSymbol();
		if (outline != null) {
			outline.setLineWidth(txtOutlineWidth.getDouble());
			outline.setAlpha(outlineAlpha);
		}
		layer.setOutline(outline);

		layer.setHasFill(jccFillColor.getUseColorisSelected());
		Color c = jccFillColor.getColor();
		if (c != null)
			c = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		layer.setFillColor(c);

		return layer;
	}

	public void refreshControls(ISymbol layer) {
		SimpleFillSymbol sym;
		try {
			if (layer == null) {
				// initialize defaults
				NotificationManager.addWarning(getClass().getName()
						+ ":: should be unreachable code", new Exception());
			} else {

				// fill

				sym = (SimpleFillSymbol) layer;
				jccFillColor.setUseColorIsSelected(sym.hasFill());
				jccFillColor.setColor(sym.getFillColor());
				// outline

				sldOutlineTransparency.removeChangeListener(this);

				useBorder.setSelected(sym.hasOutline());

				if (sym.getOutline() != null) {
					outline = sym.getOutline();
					btnOutline.setSymbol(outline);
				}

				if (outline != null) {
					outlineAlpha = outline.getAlpha();
					sldOutlineTransparency
							.setValue((int) ((outlineAlpha / 255D) * 100));
					txtOutlineWidth.setDouble(outline.getLineWidth());
				} else {
					sldOutlineTransparency.setValue(100);
				}

				sldOutlineTransparency.addChangeListener(this);

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
		return SimpleFillSymbol.class;
	}

	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();

		if (s.equals(btnOutline)) {
			ISymbol sym = btnOutline.getSymbol();
			if (sym instanceof ILineSymbol) {
				ILineSymbol outline = (ILineSymbol) sym;
				if (outline != null)
					txtOutlineWidth.setDouble(outline.getLineWidth());
			}

		}

		fireSymbolChangedEvent();
	}

	public void stateChanged(ChangeEvent e) {
		Object s = e.getSource();

		if (s.equals(sldOutlineTransparency)) {
			outlineAlpha = (int) (255 * (sldOutlineTransparency.getValue() / 100.0));
		}

		if (useBorder.isSelected())
			outline = (ILineSymbol) btnOutline.getSymbol();
		fireSymbolChangedEvent();
	}

	public EditorTool[] getEditorTools() {
		return null;
	}

}