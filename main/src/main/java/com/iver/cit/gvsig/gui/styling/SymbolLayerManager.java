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
 * $Id: SymbolLayerManager.java 30101 2009-07-22 13:30:23Z vcaballero $
 * $Log$
 * Revision 1.8  2007-09-17 09:21:45  jaume
 * refactored SymboSelector (added support for multishapedsymbol)
 *
 * Revision 1.7  2007/08/09 10:39:04  jaume
 * first round of found bugs fixed
 *
 * Revision 1.6  2007/06/29 13:07:33  jaume
 * +PictureLineSymbol
 *
 * Revision 1.5  2007/05/31 09:36:22  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/04/20 07:54:38  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.4  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2007/02/08 15:45:37  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/08 15:43:04  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2007/01/16 11:52:11  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/01/10 17:05:05  jaume
 * moved to FMap and gvSIG
 *
 * Revision 1.6  2006/11/08 10:56:47  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/11/07 08:52:30  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/11/06 17:08:45  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/11/06 16:06:52  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/11/06 07:33:54  jaume
 * javadoc, source style
 *
 * Revision 1.1  2006/11/02 17:19:28  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.documents.view.legend.gui.ISymbolSelector;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbolPreviewer;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.iver.utiles.XMLEntity;

/**
 * A component linked to a SymbolEditor that shows the layers
 * 
 * @author jaume
 * 
 */
public class SymbolLayerManager extends JPanel implements ActionListener {
	private static final long serialVersionUID = -1939951243066481691L;
	private static final Dimension LIST_CELL_SIZE = new Dimension(150, 37);
	private SymbolEditor owner;
	private JList<Symbolizer> jListLayers;
	private Symbolizer activeStyle;
	private List<Symbolizer> symbol;
	private JButton btnAddLayer = null;
	private JPanel pnlButtons = null;
	private JButton btnRemoveLayer = null;
	private JButton btnMoveUpLayer = null;
	private JButton btnMoveDownLayer = null;
	private final Dimension btnDimension = new Dimension(24, 24);
	private JScrollPane scroll;
	private Color cellSelectedBGColor = Color.BLUE;

	public SymbolLayerManager(SymbolEditor owner) {
		this.owner = owner;
		this.symbol = new ArrayList<Symbolizer>();
		this.symbol.add(owner.getSymbol());
		// gtintegration
		// if (symbol.size() == 0) {
		// int shapeType = -1;
		// if (symbol instanceof MultiLayerMarkerSymbol) {
		// shapeType = FShape.POINT;
		// } else if (symbol instanceof MultiLayerLineSymbol) {
		// shapeType = FShape.LINE;
		// } else if (symbol instanceof MultiLayerFillSymbol) {
		// shapeType = FShape.POLYGON;
		// }
		//
		// if (shapeType != -1)
		// symbol.addLayer(SymbologyUtils
		// .createDefaultSymbolByShapeType(shapeType));
		// }
		initialize();

	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		this.add(getPnlButtons(), java.awt.BorderLayout.SOUTH);
		getJListLayers().setSelectedIndex(0);
	}

	private JScrollPane getJScrollPane() {
		if (scroll == null) {
			scroll = new JScrollPane();
			scroll.setPreferredSize(new Dimension(150, 160));
			scroll.setViewportView(getJListLayers());
		}
		return scroll;
	}

	private JList<Symbolizer> getJListLayers() {
		if (jListLayers == null) {
			jListLayers = new JList<Symbolizer>();
			jListLayers
					.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			jListLayers.setLayoutOrientation(JList.HORIZONTAL_WRAP);

			jListLayers.setVisibleRowCount(-1);
			jListLayers.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					setLayer(jListLayers.getSelectedValue());
				}
			});
			jListLayers.setModel(new SymbolLayerListModel());
			jListLayers.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = -2551357351239544039L;

				@Override
				public Component getListCellRendererComponent(JList<?> list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					return new SymbolLayerComponent((Symbolizer) value, index,
							isSelected);
				}
			});
			jListLayers.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2
							&& e.getButton() == MouseEvent.BUTTON1) {
						ISymbolSelector symSel = SymbolSelector
								.createSymbolSelector(activeStyle,
										owner.getShapeType());
						PluginServices.getMDIManager().addWindow(symSel);
						updateSymbol(symSel.getSelectedObject());
						setLayer(symSel.getSelectedObject());
					}

				}
			});
		}
		return jListLayers;
	}

	private void updateSymbol(Symbolizer style) {
		if (style != null) {
			int index = getJListLayers().getSelectedIndex();
			symbol.add(index, style);
			owner.refresh();
			validate();
		}
	}

	private void setLayer(Symbolizer style) {
		if (style != null) {
			activeStyle = style;
			owner.setOptionsPageFor(style);
		}
	}

	private JButton getBtnAddLayer() {
		if (btnAddLayer == null) {
			btnAddLayer = new JButton(PluginServices.getIconTheme().get(
					"add-layer-icono"));
			btnAddLayer.setActionCommand("ADD");
			btnAddLayer.setSize(btnDimension);
			btnAddLayer.setPreferredSize(btnDimension);
			btnAddLayer.addActionListener(this);
		}
		return btnAddLayer;
	}

	private JPanel getPnlButtons() {
		if (pnlButtons == null) {
			pnlButtons = new JPanel();
			pnlButtons.add(getBtnAddLayer(), null);
			pnlButtons.add(getBtnRemoveLayer(), null);
			pnlButtons.add(getBtnMoveUpLayer(), null);
			pnlButtons.add(getBtnMoveDown(), null);
		}
		return pnlButtons;
	}

	private JButton getBtnRemoveLayer() {
		if (btnRemoveLayer == null) {
			btnRemoveLayer = new JButton(PluginServices.getIconTheme().get(
					"delete-icono"));
			btnRemoveLayer.setActionCommand("REMOVE");
			btnRemoveLayer.setSize(btnDimension);
			btnRemoveLayer.setPreferredSize(btnDimension);
			btnRemoveLayer.addActionListener(this);
		}
		return btnRemoveLayer;
	}

	private JButton getBtnMoveUpLayer() {
		if (btnMoveUpLayer == null) {
			btnMoveUpLayer = new JButton(PluginServices.getIconTheme().get(
					"arrow-up-icono"));
			btnMoveUpLayer.setActionCommand("MOVE_UP");
			btnMoveUpLayer.setSize(btnDimension);
			btnMoveUpLayer.setPreferredSize(btnDimension);
			btnMoveUpLayer.addActionListener(this);
		}
		return btnMoveUpLayer;
	}

	private JButton getBtnMoveDown() {
		if (btnMoveDownLayer == null) {
			btnMoveDownLayer = new JButton(PluginServices.getIconTheme().get(
					"arrow-down-icono"));
			btnMoveDownLayer.setActionCommand("MOVE_DOWN");
			btnMoveDownLayer.setSize(btnDimension);
			btnMoveDownLayer.setPreferredSize(btnDimension);
			btnMoveDownLayer.addActionListener(this);
		}
		return btnMoveDownLayer;
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		int jlistIndex = getJListLayers().getSelectedIndex();
		int index = symbol.size() - 1 - jlistIndex;

		if (command.equals("ADD")) {
			symbol.add(owner.getNewLayer());
			index = symbol.size();
		} else if (command.equals("REMOVE")) {
			int layerCount = symbol.size();
			if (layerCount > 1 && index < layerCount)
				symbol.remove(symbol.get(index));
		} else if (command.equals("MOVE_UP")) {
			if (index < symbol.size() - 1) {
				Collections.swap(symbol, index, index + 1);
				jlistIndex--;
			}
		} else if (command.equals("MOVE_DOWN")) {
			if (index > 0) {
				Collections.swap(symbol, index, index - 1);
				jlistIndex++;
			}
		} else if (command.equals("LOCK")) {
			try {
				XMLEntity layerXML = SymbologyUtils.getXMLEntity(symbol
						.get(index));
				boolean locked = !layerXML.contains("locked")
						|| !layerXML.getBooleanProperty("locked");
				layerXML.putProperty("locked", locked);
			} catch (Exception ex) {
				// Index out of bounds or so, we don't care
			}
		}
		owner.refresh();
		validate();
		getJListLayers().setSelectedIndex(jlistIndex);

	}

	private class SymbolLayerListModel extends AbstractListModel<Symbolizer> {
		private static final long serialVersionUID = 4197818146836802855L;
		private ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();

		public int getSize() {
			return symbol.size();
		}

		public Symbolizer getElementAt(int index) {
			return symbol.get(symbol.size() - 1 - index);
		}

		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}

	}

	public Symbolizer getSelectedLayer() {
		return getJListLayers().getSelectedValue();
	}

	public int getSelectedLayerIndex() {
		return getJListLayers().getSelectedIndex();
	}

	public void validate() { // patched to force the list to be painted when it
								// starts empty
		jListLayers = null;
		getJScrollPane().setViewportView(getJListLayers());
		super.validate();
	}

	private class SymbolLayerComponent extends JPanel {
		private static final long serialVersionUID = -3706313315505454031L;

		public SymbolLayerComponent(Symbolizer style, int index,
				boolean isSelected) {
			LayoutManager layout = new FlowLayout(FlowLayout.LEFT, 3, 3);
			setLayout(layout);
			Color bgColor = (isSelected) ? cellSelectedBGColor : Color.WHITE;
			setBackground(bgColor);
			SymbolPreviewer sp = new SymbolPreviewer();
			sp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			sp.setAlignmentX(Component.CENTER_ALIGNMENT);
			sp.setPreferredSize(new Dimension(140, 30));
			sp.setSize(new Dimension(140, 30));
			sp.setSymbol(style);
			sp.setBackground(Color.WHITE);
			add(sp);
			setPreferredSize(LIST_CELL_SIZE);
		}
	}
}
