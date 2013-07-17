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
package com.iver.cit.gvsig.gui.styling;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JComboBoxFontSizes;
import org.gvsig.gui.beans.swing.JComboBoxFonts;
import org.gvsig.gui.beans.swing.JIncrementalNumberField;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.core.symbols.ISymbol;
import com.iver.cit.gvsig.fmap.core.symbols.SimpleTextSymbol;
import com.iver.cit.gvsig.gui.panels.ColorChooserPanel;

/**
 * SimpleText allows the user to store and modify the main properties that
 * define a <b>simple text</b>.
 * <p>
 * <p>
 * This functionality is carried out thanks to three tabs (text, formatted,
 * advanced) and a Mask which are included in the panel to edit the properities
 * of a symbol (SymbolEditor)how is explained in AbstractTypeSymbolEditor.
 * <p>
 * <p>
 * The first tab (text)allows the user to change the font (<b>cmbFonts</b>), the
 * size(<b>cmbFontSize</b>), the style (which can be underlined
 * -<b>btnUnderlined</b>-, italic -<b>btnItalic</b>- or bold
 * -<b>btnBold</b>-),the color (<b>jcc</b>)and the offset of the the
 * text(<b>txtXOffset</b> and <b>txtYOffset</b>).
 * <p>
 * <p>
 * The rest of tabs that are not mask are not yet implemented.
 * 
 * 
 * @see Mask
 * @see AbstractTypeSymbolEditor
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class SimpleText extends AbstractTypeSymbolEditor {

	private ArrayList tabs = new ArrayList();
	// TODO: Comentarizado hasta que mask esté acabado
	// private Mask mask;
	private JComboBoxFonts cmbFonts;
	private JToggleButton btnUnderlined;
	private JToggleButton btnItalic;
	private JToggleButton btnBold;
	private JComboBoxFontSizes cmbFontSize;
	private ColorChooserPanel jcc;
	private JIncrementalNumberField txtXOffset;
	private JIncrementalNumberField txtYOffset;

	public SimpleText(SymbolEditor owner) {
		super(owner);
		initialize();
	}

	/**
	 * Initializes the parameters that define a simpletext.To do it, four tabs
	 * are created (one of them is a mask)inside the SymbolEditor panel with
	 * default values for the different attributes of the simple text.For the
	 * moment only the text tab has been created and it allows the user to
	 * modify the font,color, offset and style of the text.
	 * 
	 */

	private void initialize() {
		JPanel myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		JPanel aux = new JPanel(new GridLayout(1, 2, 15, 0));
		myTab.setName(PluginServices.getText(this, "text"));

		// //------------ Tab Text
		GridBagLayoutPanel leftColumn = new GridBagLayoutPanel();
		leftColumn.addComponent(PluginServices.getText(this, "font") + ": ",
				cmbFonts = new JComboBoxFonts());

		JPanel aux2 = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 1));
		aux2.add(getBtnBold());
		aux2.add(getBtnItalic());
		aux2.add(getBtnUnderlined());
		leftColumn.addComponent(PluginServices.getText(this, "style") + ":",
				aux2);

		jcc = new ColorChooserPanel();
		jcc.setAlpha(255);
		leftColumn.addComponent(PluginServices.getText(this, "color") + ":",
				jcc);
		// vertical alignment stuff
		leftColumn.addComponent(new JLabel(" "));
		// \vertical alignment stuff
		leftColumn.addComponent(PluginServices.getText(this, "x_offset") + ":",
				txtXOffset = new JIncrementalNumberField("0", 3));
		leftColumn.addComponent(PluginServices.getText(this, "x_offset") + ":",
				txtYOffset = new JIncrementalNumberField("0", 3));

		GridBagLayoutPanel rightColumn = new GridBagLayoutPanel();
		aux2 = new JPanel(new FlowLayout(0, 0, FlowLayout.LEFT));
		cmbFontSize = new JComboBoxFontSizes();
		aux2.add(cmbFontSize);
		rightColumn.addComponent(PluginServices.getText(this, "size") + ":",
				aux2);
		// vertical alignment stuff
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		rightColumn.addComponent(new JLabel(" "));
		// \vertical alignment stuff

		aux.add(leftColumn);
		aux.add(rightColumn);

		myTab.add(aux);
		tabs.add(myTab);

		// //------------ Tab FORMATTED
		myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		myTab.setName(PluginServices.getText(this, "formatted"));

		leftColumn = new GridBagLayoutPanel();

		myTab.add(leftColumn);
		tabs.add(myTab);

		// //------------ Tab ADVANCED
		myTab = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
		myTab.setName(PluginServices.getText(this, "advanced"));
		leftColumn = new GridBagLayoutPanel();

		myTab.add(leftColumn);
		tabs.add(myTab);

		// //------------ Tab MASK
		// mask = new Mask(this);
		// tabs.add(mask);
	}

	public ISymbol getLayer() {
		SimpleTextSymbol sts = new SimpleTextSymbol();
		return sts;
	}

	public String getName() {
		return PluginServices.getText(this, "simple_text_symbol");
	}

	public JPanel[] getTabs() {
		return (JPanel[]) tabs.toArray(new JPanel[tabs.size()]);
	}

	public void refreshControls(ISymbol layer) {
		// TODO Implement it
		// throw new Error("Not yet implemented!");

	}

	public Class getSymbolClass() {
		return SimpleTextSymbol.class;
	}

	public EditorTool[] getEditorTools() {
		return null;
	}

	private JToggleButton getBtnUnderlined() {
		if (btnUnderlined == null) {
			btnUnderlined = new JToggleButton(PluginServices.getIconTheme()
					.get("underline-icon"));
		}
		return btnUnderlined;
	}

	private JToggleButton getBtnItalic() {
		if (btnItalic == null) {
			btnItalic = new JToggleButton(PluginServices.getIconTheme().get(
					"italic-icon"));
		}
		return btnItalic;
	}

	private JToggleButton getBtnBold() {
		if (btnBold == null) {
			btnBold = new JToggleButton(PluginServices.getIconTheme().get(
					"bold-icon"));
		}
		return btnBold;
	}
}