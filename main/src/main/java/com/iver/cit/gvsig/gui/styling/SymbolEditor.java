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
 * $Id: SymbolEditor.java 31310 2009-10-19 10:07:23Z vcaballero $
 * $Log$
 * Revision 1.22  2007-09-19 15:36:36  jaume
 * removed unnecessary imports
 *
 * Revision 1.21  2007/09/17 09:21:45  jaume
 * refactored SymboSelector (added support for multishapedsymbol)
 *
 * Revision 1.20  2007/08/21 09:32:41  jvidal
 * javadoc
 *
 * Revision 1.19  2007/08/10 06:09:57  jaume
 * imports fixed
 *
 * Revision 1.18  2007/08/10 06:06:05  jaume
 * translations and error messages
 *
 * Revision 1.17  2007/08/03 11:29:13  jaume
 * refactored AbstractTypeSymbolEditorPanel class name to AbastractTypeSymbolEditor
 *
 * Revision 1.16  2007/08/01 13:01:43  jaume
 * refactored to be pluggable
 *
 * Revision 1.15  2007/07/30 12:56:04  jaume
 * organize imports, java 5 code downgraded to 1.4 and added PictureFillSymbol
 *
 * Revision 1.14  2007/07/30 06:59:51  jaume
 * finished (maybe) LineFillSymbol
 *
 * Revision 1.13  2007/07/26 12:28:29  jaume
 * maybe finished ArrowMarkerSymbol and ArrowDecoratorStyle
 *
 * Revision 1.12  2007/07/18 06:56:03  jaume
 * continuing with cartographic support
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
 * Revision 1.8  2007/05/29 15:47:06  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/05/09 16:08:14  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/05/08 15:44:07  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2007/04/20 07:54:38  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/03/28 16:44:08  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/03/27 09:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.4  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/09 11:00:03  jaume
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
 * Revision 1.11  2007/01/10 17:05:05  jaume
 * moved to FMap and gvSIG
 *
 * Revision 1.10  2006/11/13 09:15:23  jaume
 * javadoc and some clean-up
 *
 * Revision 1.9  2006/11/06 17:08:45  jaume
 * *** empty log message ***
 *
 * Revision 1.8  2006/11/06 16:06:52  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2006/11/06 07:33:54  jaume
 * javadoc, source style
 *
 * Revision 1.6  2006/11/02 17:19:28  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/10/31 16:16:34  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/10/30 19:30:35  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/29 23:53:49  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/10/27 12:41:09  jaume
 * GUI
 *
 * Revision 1.1  2006/10/26 16:31:21  jaume
 * GUI
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.geotools.styling.StyleFactoryImpl;
import org.geotools.styling.Symbolizer;
import org.geotools.styling.TextSymbolizer;
import org.gvsig.gui.beans.AcceptCancelPanel;
import org.gvsig.units.Unit;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.gui.JComboBoxUnits;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolPreviewer;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.iver.utiles.XMLEntity;

/**
 * Creates the panel that is used to control the properties of a symbol in order
 * to modify or check them and to create a new one.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class SymbolEditor extends JPanel implements IWindow {

	public static final int POLYGON = 1;
	public static final int POINT = 2;
	public static final int LINE = 3;
	public static final int TEXT = 4;
	private static Hashtable<Class<? extends Symbolizer>, ArrayList<Class<? extends AbstractTypeSymbolEditor>>> editorsByType;
	private WindowInfo wi;
	private JPanel pnlWest = null;
	private JPanel pnlCenter = null;
	private JPanel pnlPreview = null;
	private JPanel pnlLayers = null;
	private AcceptCancelPanel okCancelPanel;
	private Symbolizer style;
	private SymbolPreviewer symbolPreview = null;
	private JPanel pnlTypeAndUnits = null;
	private JComboBox<AbstractTypeSymbolEditor> cmbType;
	private JComboBoxUnits cmbUnits;
	private JTabbedPane tabbedPane = null;
	private Class<? extends Symbolizer> shapeType;
	private XMLEntity oldSymbolProperties;
	private ActionListener cmbTypeActionListener;

	private AbstractTypeSymbolEditor[] tabs;
	private SymbolLayerManager layerManager;
	private boolean replacing = false;
	private JComboBoxUnitsReferenceSystem cmbUnitsReferenceSystem;

	/**
	 * Constructor method
	 * 
	 * @param symbol
	 *            ISymbol
	 * @param shapeType
	 *            int
	 */
	public SymbolEditor(Symbolizer symbol, Class<? extends Symbolizer> shapeType) {
		// //////// /-------------------------------------
		if (TextSymbolizer.class.isAssignableFrom(shapeType)) {
			this.style = (symbol == null) ? new StyleFactoryImpl()
					.createTextSymbolizer() : symbol;
		} else {
			this.style = symbol;

			// TODO gtintegration
			// getCmbUnitsReferenceSystem().setSelectedIndex(
			// this.style.getReferenceSystem());
			Unit unit = SymbologyUtils.convert2gvsigUnits(this.style
					.getUnitOfMeasure());
			getCmbUnits().setSelectedUnit(unit);
		}
		this.oldSymbolProperties = SymbologyUtils.getXMLEntity(style);
		this.shapeType = shapeType;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {

		cmbTypeActionListener = new ActionListener() {
			int prevIndex = -2;

			public void actionPerformed(ActionEvent e) {
				int index = getCmbType().getSelectedIndex();
				if (prevIndex != index) {
					// needs to refresh
					prevIndex = index;

					AbstractTypeSymbolEditor options = (AbstractTypeSymbolEditor) getCmbType()
							.getSelectedItem();

					// //////// /-------------------------------------
					if (style != null
							&& options.getSymbolClass().isAssignableFrom(
									style.getClass())) {
						// TODO gtintegration
						// getCmbUnitsReferenceSystem().setSelectedIndex(
						// style.getReferenceSystem());
						Unit unit = SymbologyUtils.convert2gvsigUnits(style
								.getUnitOfMeasure());
						getCmbUnits().setSelectedUnit(unit);
						options.refreshControls(style);

						replaceOptions(options);
						// //////// /-------------------------------------
					} else {
						replaceOptions(options);
					}
					// //////// /-------------------------------------

				}
			}
		};

		Comparator<AbstractTypeSymbolEditor> tabComparator = new Comparator<AbstractTypeSymbolEditor>() {
			public int compare(AbstractTypeSymbolEditor o1,
					AbstractTypeSymbolEditor o2) {
				int result = o1.getName().compareTo(o2.getName());
				if (o1 != o2 && result == 0)
					throw new IllegalArgumentException(PluginServices.getText(
							this, "two_panels_with_the_same_name"));
				return result;
			}
		};

		TreeSet<AbstractTypeSymbolEditor> set = new TreeSet<AbstractTypeSymbolEditor>(
				tabComparator);
		ArrayList<Class<? extends AbstractTypeSymbolEditor>> editors = editorsByType
				.get(shapeType);
		Class[] constrLocator = new Class[] { SymbolEditor.class };
		Object[] constrInitargs = new Object[] { this };
		for (int i = 0; i < editors.size(); i++) {
			Class<? extends AbstractTypeSymbolEditor> editorClass = editors
					.get(i);
			try {
				Constructor c = editorClass.getConstructor(constrLocator);
				set.add((AbstractTypeSymbolEditor) c
						.newInstance(constrInitargs));
			} catch (Exception e) {
				NotificationManager.addError(
						PluginServices.getText(this,
								"failed_installing_symbol_editor")
								+ " "
								+ editorClass.getName(), e);
			}
		}
		;
		tabs = set.toArray(new AbstractTypeSymbolEditor[0]);

		this.setLayout(new BorderLayout());
		this.add(getPnlWest(), BorderLayout.WEST);
		this.add(getPnlCenter(), BorderLayout.CENTER);
		this.add(getOkCancelPanel(), BorderLayout.SOUTH);

		cmbTypeActionListener.actionPerformed(null);
		refresh();
	}

	/**
	 * Returns an array of tabs. The value of this array will depend on the
	 * symbol selected. For example, if the symbol is composed by lines this
	 * method will return tha tabs that allow the user to modify a simple line
	 * symbol(in this case simple line and arrow decorator tabs)
	 * 
	 * @param sym
	 * @return tabs[] AbstractTypeSymbolEditor[]
	 */
	private AbstractTypeSymbolEditor getOptionsForSymbol(Symbolizer sym) {
		if (sym == null)
			return tabs[0];
		for (int i = 0; i < tabs.length; i++)
			if (tabs[i].getSymbolClass().equals(sym.getClass()))
				return tabs[i];
		return null;
	}

	/**
	 * Initializes the OkCancel panel where the accept and cancel buttons will
	 * be placed
	 * 
	 * @return okCancelPanel AcceptCancelPanel
	 */
	private AcceptCancelPanel getOkCancelPanel() {
		if (okCancelPanel == null) {
			ActionListener action = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ("CANCEL".equals(e.getActionCommand())) {
						style = SymbologyUtils.createSymbolFromXML(
								oldSymbolProperties, null);
					}
					PluginServices.getMDIManager().closeWindow(
							SymbolEditor.this);
				}
			};
			okCancelPanel = new AcceptCancelPanel(action, action);
		}
		return okCancelPanel;
	}

	public WindowInfo getWindowInfo() {
		if (wi == null) {
			wi = new WindowInfo(WindowInfo.MODALDIALOG | WindowInfo.RESIZABLE);
			wi.setWidth(780);
			wi.setHeight(400);
			wi.setTitle(PluginServices.getText(this, "symbol_property_editor"));
		}
		return wi;
	}

	public Symbolizer getSymbol() {
		// TODO gtintegration
		// style.setReferenceSystem(getReferenceSystem());
		if (style != null) {
			style.setUnitOfMeasure(SymbologyUtils.convert2JavaUnits(getUnit()));
		}

		// if (symbol instanceof MultiLayerLineSymbol) {
		// MultiLayerLineSymbol mLineSym = (MultiLayerLineSymbol) symbol;
		// double lineWidth = 0;
		// for (int i = 0; i < mLineSym.getLayerCount(); i++) {
		// lineWidth = Math.max(lineWidth, ((ILineSymbol)
		// mLineSym.getLayer(i)).getLineWidth());
		// }
		//
		// if (mLineSym.getLineWidth() != lineWidth)
		// mLineSym.setLineWidth(lineWidth);
		// }
		return style;
	}

	/**
	 * Initializes the west panel
	 * 
	 * @return
	 */
	private JPanel getPnlWest() {
		if (pnlWest == null) {
			pnlWest = new JPanel();
			pnlWest.setLayout(new BorderLayout());
			pnlWest.add(getPnlPreview(), java.awt.BorderLayout.NORTH);
			// TODO gtintegration
			// // //////// /-------------------------------------
			// if (style instanceof IMultiLayerSymbol) {
			// // //////// /-------------------------------------
			//
			// pnlWest.add(getPnlLayers(), java.awt.BorderLayout.SOUTH);
			//
			// // //////// /-------------------------------------
			// } // otherwise, no layer manager needed
			// // //////// /-------------------------------------
		}
		return pnlWest;
	}

	/**
	 * Initializes the center panel that shows the properties of a symbol.
	 * 
	 * @return pnlCenter JPanel
	 */
	private JPanel getPnlCenter() {
		if (pnlCenter == null) {
			pnlCenter = new JPanel(new BorderLayout());
			pnlCenter.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "properties")));
			pnlCenter.add(getPnlTypeAndUnits(), java.awt.BorderLayout.NORTH);
		}
		return pnlCenter;
	}

	/**
	 * Initializes the preview panel that allows the user to see a
	 * previsualization of the final symbol
	 * 
	 * @return pnlPreview JPanel
	 */
	private JPanel getPnlPreview() {
		if (pnlPreview == null) {
			pnlPreview = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnlPreview.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "preview")));
			pnlPreview.add(getSymbolPreviewer());
		}
		return pnlPreview;
	}

	/**
	 * Initializes the Layers panel that shows the different layers created that
	 * compose a symbol.
	 * 
	 * @return pnlLayers JPanel
	 */
	private JPanel getPnlLayers() {
		if (pnlLayers == null) {
			pnlLayers = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnlLayers.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "layers")));
			pnlLayers.add(getLayerManager());
		}
		return pnlLayers;
	}

	/**
	 * Obtains the layer manager used in the panel that shows the different
	 * layers that compose the symbol.
	 * 
	 * @return layerManager SymbolLayerManager
	 */
	private SymbolLayerManager getLayerManager() {
		if (layerManager == null) {
			layerManager = new SymbolLayerManager(this);
		}
		return layerManager;
	}

	/**
	 * Obtains the symbol previewer used in the panel that shows the
	 * previsualization of the final symbol.
	 * 
	 * @return symbolPreview getSymbolPreviewer
	 */
	private SymbolPreviewer getSymbolPreviewer() {
		if (symbolPreview == null) {
			symbolPreview = new SymbolPreviewer();
			symbolPreview.setPreferredSize(new Dimension(150, 100));

		}
		return symbolPreview;
	}

	/**
	 * Initializes the type and units panel where two Jcomboboxes will be placed
	 * in order to change the type and the units used in the map.
	 * 
	 * @return pnlTypeAndUnits JPanel
	 */
	private JPanel getPnlTypeAndUnits() {
		if (pnlTypeAndUnits == null) {
			pnlTypeAndUnits = new JPanel();
			pnlTypeAndUnits.setLayout(new BorderLayout());
			JPanel aux = new JPanel(new FlowLayout(FlowLayout.LEADING));
			aux.add(new JLabel(PluginServices.getText(this, "type")));
			aux.add(getCmbType());
			pnlTypeAndUnits.add(aux, BorderLayout.WEST);

			aux = new JPanel(new FlowLayout(FlowLayout.LEADING));
			aux.add(new JLabel(PluginServices.getText(this, "units")));
			aux.add(getCmbUnits());
			aux.add(getCmbUnitsReferenceSystem());
			pnlTypeAndUnits.add(aux, BorderLayout.EAST);

		}
		return pnlTypeAndUnits;
	}

	/**
	 * Obtains the JCombobox to select the reference unit to be used in the
	 * final representation of the map in this case there are two options (in
	 * the paper and in the map).
	 * 
	 * @return
	 */
	private JComboBoxUnitsReferenceSystem getCmbUnitsReferenceSystem() {
		if (cmbUnitsReferenceSystem == null) {
			cmbUnitsReferenceSystem = new JComboBoxUnitsReferenceSystem();

		}

		return cmbUnitsReferenceSystem;
	}

	/**
	 * Returns the Jcombobox used to select the reference unit (centimeters,
	 * milimeters and so on) to be used in the final representation of the map.
	 * 
	 * @return cmbUnits JUnitsComboBox
	 */
	private JComboBoxUnits getCmbUnits() {
		if (cmbUnits == null) {
			cmbUnits = new JComboBoxUnits();
		}
		return cmbUnits;
	}

	/**
	 * Returns the option selected in the reference unit Jcombobox
	 * 
	 */
	public Unit getUnit() {
		return getCmbUnits().getSelectedUnitIndex();
	}

	public int getReferenceSystem() {
		return getCmbUnitsReferenceSystem().getSelectedIndex();
	}

	/**
	 * Returns the Jcombobox used in the panel to select the type of symbol.
	 * 
	 * @return cmbType JComboBox
	 */
	private JComboBox<AbstractTypeSymbolEditor> getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox<AbstractTypeSymbolEditor>(tabs);
			cmbType.addActionListener(cmbTypeActionListener);
		}
		return cmbType;
	}

	/**
	 * Sets a layer to a symbol in order to create a final symbol composed by
	 * different layers.
	 * 
	 * @param layer
	 */
	protected void setLayerToSymbol(Symbolizer layer) {
		this.style = layer;
		// Initialize layer manager
		getLayerManager();

		// TODO gtintegration
		// IMultiLayerSymbol s = (IMultiLayerSymbol) style;
		// if (i >= 0 && i < s.getLayerCount()) {
		// s.setLayer(s.getLayerCount() - 1 - i, layer);
		//
		// }
		refresh();
	}

	public void refresh() {
		getSymbolPreviewer().setSymbol(style);
		doLayout();
		repaint();
	}

	/**
	 * <p>
	 * Returns the type of the symbol that this panels is created for.<br>
	 * </p>
	 * <p>
	 * Possible values returned by this method are
	 * <ol>
	 * <li><b> FShape.POINT </b>, for maker symbols</li>
	 * <li><b> FShape.POLYGON </b>, for fill symbols</li>
	 * <li><b> FShape.LINE </b>, for line symbols (not yet implemented)</li>
	 * <li><b> FShape.TEXT </b>, for text symbols (not yet implemented)</li>
	 * <li>maybe some other in the future</li>
	 * </ol>
	 * </p>
	 * 
	 * @return
	 */
	public Class<? extends Symbolizer> getShapeType() {
		return shapeType;
	}

	/**
	 * Obtains a new layer
	 * 
	 * @return sym ISymbol
	 */
	public Symbolizer getNewLayer() {
		return getCmbType().getItemAt(getCmbType().getSelectedIndex())
				.getStyle();
	}

	private void replaceOptions(AbstractTypeSymbolEditor options) {
		if (!replacing) {
			replacing = true;
			if (tabbedPane != null)
				getPnlCenter().remove(tabbedPane);
			JPanel[] tabs = options.getTabs();
			tabbedPane = new JTabbedPane();
			tabbedPane.setPreferredSize(new Dimension(300, 300));
			for (int i = 0; i < tabs.length; i++) {
				tabbedPane.addTab(tabs[i].getName(), tabs[i]);
			}
			getPnlCenter().add(tabbedPane, BorderLayout.CENTER);
			getPnlCenter().doLayout();
			replacing = false;
		}
	}

	public void setOptionsPageFor(Symbolizer symbol) {
		AbstractTypeSymbolEditor options = getOptionsForSymbol(symbol);
		if (options == null)
			return;
		options.refreshControls(symbol);
		getCmbType().setSelectedItem(options);
	}

	/**
	 * Obtains the units to be used for the reference system.
	 * 
	 */
	public int getUnitsReferenceSystem() {
		return cmbUnitsReferenceSystem.getSelectedIndex();
	}

	public static void addSymbolEditorPanel(
			Class<? extends AbstractTypeSymbolEditor> abstractTypeSymbolEditorPanelClass,
			Class<? extends Symbolizer> shapeType) {
		if (editorsByType == null) {
			editorsByType = new Hashtable<Class<? extends Symbolizer>, ArrayList<Class<? extends AbstractTypeSymbolEditor>>>();
		}

		ArrayList<Class<? extends AbstractTypeSymbolEditor>> l = editorsByType
				.get(shapeType);
		if (l == null) {
			l = new ArrayList<Class<? extends AbstractTypeSymbolEditor>>();
		}
		l.add(abstractTypeSymbolEditorPanelClass);

		editorsByType.put(shapeType, l);
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}
}
