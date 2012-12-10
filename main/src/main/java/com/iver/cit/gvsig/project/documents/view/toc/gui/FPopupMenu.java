/*
 * Created on 02-mar-2004
 *
 * gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
package com.iver.cit.gvsig.project.documents.view.toc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.view.toc.actions.ChangeNameTocMenuEntry;
import com.iver.cit.gvsig.project.documents.view.toc.actions.EliminarCapaTocMenuEntry;
import com.iver.cit.gvsig.project.documents.view.toc.actions.LayersGroupTocMenuEntry;
import com.iver.cit.gvsig.project.documents.view.toc.actions.LayersUngroupTocMenuEntry;
import com.iver.cit.gvsig.project.documents.view.toc.actions.ZoomAlTemaTocMenuEntry;

public class FPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;

	private ArrayList<ILayerAction> layerActions = new ArrayList<ILayerAction>();

	public FPopupMenu() {
		layerActions.add(new ChangeNameTocMenuEntry());
		// extensionPoints.add("View_TocActions", "FSymbolChangeColor",
		// new ChangeSymbolTocMenuEntry());
		// extensionPoints.add("View_TocActions", "FLyrVectEditProperties",
		// new FLyrVectEditPropertiesTocMenuEntry());
		layerActions.add(new ZoomAlTemaTocMenuEntry());
		// extensionPoints.add("View_TocActions", "AttTable",
		// new AttTableTocMenuEntry());
		// extensionPoints.add("View_TocActions", "AttFilter",
		// new AttFilterTocMenuEntry());
		// extensionPoints.add("View_TocActions", "AttJoin",
		// new AttJoinTocMenuEntry());
		// // extensionPoints.add("View_TocActions","ZoomPixelCursor",new
		// // ZoomPixelCursorTocMenuEntry());
		layerActions.add(new EliminarCapaTocMenuEntry());
		// extensionPoints.add("View_TocActions", "VerErroresCapa",
		// new ShowLayerErrorsTocMenuEntry());
		// extensionPoints.add("View_TocActions", "ReloadLayer",
		// new ReloadLayerTocMenuEntry());
		layerActions.add(new LayersGroupTocMenuEntry());
		layerActions.add(new LayersUngroupTocMenuEntry());
		// extensionPoints.add("View_TocActions", "FirstLayer",
		// new FirstLayerTocMenuEntry());
		//
		// extensionPoints.add("View_TocActions", "Copy",
		// new CopyLayersTocMenuEntry());
		// extensionPoints.add("View_TocActions", "Cut",
		// new CutLayersTocMenuEntry());
		// extensionPoints.add("View_TocActions", "Paste",
		// new PasteLayersTocMenuEntry());
		// // extensionPoints.add("View_TocActions","RasterProperties",new
		// // FLyrRasterAdjustPropertiesTocMenuEntry());
		// // extensionPoints.add("View_TocActions","RasterProperties",new
		// // RasterPropertiesTocMenuEntry());
	}

	public void update(MapContext mapContext, MapControl mapControl) {
		// filter and sort the items
		TreeSet<ILayerAction> ret = new TreeSet<ILayerAction>(
				new LayerActionComparator());
		for (ILayerAction layerAction : this.layerActions) {
			if (layerAction.isVisible(mapContext)) {
				ret.add(layerAction);
			}
		}
		ILayerAction[] actions = ret.toArray(new ILayerAction[ret.size()]);

		// Add the items
		this.removeAll();
		String group = null;
		for (int i = 0; i < actions.length; i++) {
			ILayerAction action = actions[i];
			LayerActionMenuItem item = new LayerActionMenuItem(mapContext,
					mapControl, action.getText(), action);
			item.setEnabled(action.isEnabled(mapContext));
			if (!action.getGroup().equals(group)) {
				if (group != null)
					this.addSeparator();
				group = action.getGroup();
			}
			this.add(item);
		}
	}

	public class LayerActionComparator implements Comparator<ILayerAction> {
		@Override
		public int compare(ILayerAction o1, ILayerAction o2) {
			NumberFormat formater = NumberFormat.getInstance();
			formater.setMinimumIntegerDigits(3);
			String key1 = "" + formater.format(o1.getGroupOrder())
					+ o1.getGroup() + formater.format(o1.getOrder());
			String key2 = "" + formater.format(o2.getGroupOrder())
					+ o2.getGroup() + formater.format(o2.getOrder());
			return key1.compareTo(key2);
		}
	}

	public class LayerActionMenuItem extends JMenuItem implements
			ActionListener {
		private static final long serialVersionUID = 1L;
		private ILayerAction action;
		private MapContext mapContext;
		private MapControl mapControl;

		public LayerActionMenuItem(MapContext mapContext,
				MapControl mapControl, String text, ILayerAction documentAction) {
			super(text);
			this.mapControl = mapControl;
			this.mapContext = mapContext;
			this.action = documentAction;
			String tip = this.action.getDescription();
			if (tip != null && tip.length() > 0) {
				this.setToolTipText(tip);
			}
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			this.action.execute(mapContext, mapControl);
		}
	}

}
