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

/* CVS MESSAGES:
 *
 * $Id: AbstractTypeSymbolEditor.java 22343 2008-07-18 07:28:13Z vcaballero $
 * $Log$
 * Revision 1.3  2007-08-08 11:45:38  jaume
 * some bugs fixed
 *
 * Revision 1.1  2007/08/03 11:29:13  jaume
 * refactored AbstractTypeSymbolEditorPanel class name to AbastractTypeSymbolEditor
 *
 * Revision 1.12  2007/08/03 09:20:47  jaume
 * refactored class names
 *
 * Revision 1.11  2007/08/03 09:07:01  jaume
 * javadoc
 *
 * Revision 1.10  2007/08/03 07:34:15  jaume
 * javadoc
 *
 * Revision 1.9  2007/07/30 12:56:04  jaume
 * organize imports, java 5 code downgraded to 1.4 and added PictureFillSymbol
 *
 * Revision 1.8  2007/07/18 06:56:03  jaume
 * continuing with cartographic support
 *
 * Revision 1.7  2007/07/12 10:43:55  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/06/29 13:07:33  jaume
 * +PictureLineSymbol
 *
 * Revision 1.5  2007/05/08 15:44:07  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/04/20 07:54:38  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/04/05 16:08:34  jaume
 * Styled labeling stuff
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.3  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/08 15:42:13  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/16 11:52:11  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2006/11/13 09:15:23  jaume
 * javadoc and some clean-up
 *
 * Revision 1.5  2006/11/06 16:06:52  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/10/31 16:16:34  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/30 19:30:35  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/10/29 23:53:49  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/27 12:41:09  jaume
 * GUI
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import javax.swing.JPanel;

import org.geotools.styling.Symbolizer;

import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;

/**
 * <p>
 * Abstract class that all the Symbol settings GUI's must extend. <b>This is not
 * Component by it self, but a bag of JPanels with the required interface to be
 * operable from the SymbolEditor</b>. The panels provide by this are the tabs
 * of the JTabbedPane of the options area in the SymbolEditor.
 * </p>
 * <p>
 * The components contained by this panelset are automatically placed in the
 * <b>SymbolEditor</b>'s symbol options area. In case other panel of this was
 * already placed, then the old one is replaced by this.
 * </p>
 * <p>
 * In order to the <b>SymbolEditor</b> owner of this to decide which
 * <b>AbstractTypeSymbolEditorPanel</b> will be placed each symbol editor panel
 * must define the type of the symbol it is able to configure. To do this,
 * <b>AbstractTypeSymbolEditorPanel</b> includes the abstract method
 * <b>getSymbolClass()</b> which will return the <b>Class</b> of the
 * corresponding symbol. The symbol class must define an implementation of
 * ISymbol in general, and one of IMarkerSymbol, ILineSymbol or IFillSymbol in
 * particular, depending of the context of the symbology being edited.
 * </p>
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public abstract class AbstractTypeSymbolEditor {
	protected SymbolEditor owner;
	private boolean applying = false;

	/**
	 * Produces and returns the ISymbol according with the user settings.
	 * 
	 * @return XMLEntity defining the ISymbol.
	 * @see getSymbolClass() method
	 */
	public abstract Symbolizer getStyle();

	/**
	 * Creates a new instance of this type symbol editor panel associated to its
	 * SymbolEditor parent which will be notified of the changes in order to
	 * keep the symbol preview in sync with the user settings.
	 * 
	 * @param owner
	 *            , the SymbolEditor which created this.
	 */
	public AbstractTypeSymbolEditor(SymbolEditor owner) {
		this.owner = owner;
	}

	/**
	 * <p>
	 * Returns the name of the config tabs that will be shown in the selector
	 * combo box. This is typically a human-readable (and also translatable)
	 * name for the symbol that this TypeEditorPanel deals with, but maybe you
	 * prefer to use any other one.<br>
	 * </p>
	 * <p>
	 * The order of the entries in the combo is alphabetically-based. So you can
	 * force a position by defining a name that suits your needs.
	 * </p>
	 * 
	 * @return A human-readable text naming this panel
	 */
	public abstract String getName();

	/**
	 * <p>
	 * Due to the complexity that many symbols settings can reach, the
	 * SymbolEditorPanel is designed in a tabbed-based fashion. So, you can use
	 * as many of pages you want to put your components. This pages are regular
	 * JPanels that will be automatically added to the SymbolEditor dialog.<br>
	 * </p>
	 * <p>
	 * In case you need only one page, just return a JPanel array with a length
	 * of 1.
	 * </p>
	 * 
	 * @return An array of JPanel containing the setting's interface.
	 */
	public abstract JPanel[] getTabs();

	/**
	 * Invoked when the user selects or adds a new layer. This method fills up
	 * the components on the right according on the layer properties
	 * 
	 * @param <b>ISymbol</b> layer, the symbol
	 */
	public abstract void refreshControls(Symbolizer layer);

	/**
	 * This overrided method returns the text shown at the SymbolEditorPanel
	 * select combobox.
	 */
	public final String toString() {
		return getName();
	}

	protected final void fireSymbolChangedEvent() {
		if (!applying) { // avoid unnecessary events
			applying = true;
			Symbolizer sym = getStyle();

			// TODO gtintegration
			// sym.setReferenceSystem(owner.getUnitsReferenceSystem());
			sym.setUnitOfMeasure(SymbologyUtils.convert2JavaUnits(owner
					.getUnit()));
			owner.setLayerToSymbol(sym);
			owner.refresh();
			applying = false;
		}
	}

	/**
	 * Returns the symbol class that is handled by this configuration panel.
	 * 
	 * @return <b>Class</b> (of the concrete ISymbol that this configuration
	 *         panel deals with)
	 */
	public abstract Class<? extends Symbolizer> getSymbolClass();

	/**
	 * Returns the editor tools that are handled by this configuration panel.
	 * 
	 * @return <b>EditorTool</b>
	 */
	public abstract EditorTool[] getEditorTools();

}
