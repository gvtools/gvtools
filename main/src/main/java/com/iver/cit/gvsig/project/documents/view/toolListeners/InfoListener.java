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
package com.iver.cit.gvsig.project.documents.view.toolListeners;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.AndLayerFilter;
import org.gvsig.layer.filter.LayerFilter;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.layers.layerOperations.XMLItem;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PointListener;
import com.iver.cit.gvsig.project.documents.view.info.gui.FInfoDialogXML;
import com.iver.utiles.xmlViewer.XMLContent;

/**
 * <p>
 * Listener that looks for alphanumeric information at the point selected by one
 * click of any mouse's button, in the active layers of the associated
 * <code>MapControl</code>, and displays that alphanumeric data on a
 * {@link FInfoDialog FInfoDialog} dialog.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class InfoListener implements PointListener {
	/**
	 * Object used to log messages for this listener.
	 */
	private static Logger logger = Logger.getLogger(InfoListener.class
			.getName());

	/**
	 * The image to display when the cursor is active.
	 */
	private final Image img = PluginServices.getIconTheme()
			.get("cursor-query-information").getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(img,
			new Point(16, 16), "");

	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	private MapControl mapCtrl;

	/**
	 * Radius as tolerance around the selected point, the area will be used to
	 * look for information.
	 */
	private static int TOL = 7;

	/**
	 * <p>
	 * Creates a new <code>InfoListener</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public InfoListener(MapControl mc) {
		this.mapCtrl = mc;
	}

	/**
	 * When user clicks on the associated <code>MapControl</code>'s view, the
	 * point is caught and handled by this method, which will look for
	 * alphanumeric information in features at that position in the active
	 * layers.
	 * 
	 * @param event
	 *            mouse event with the coordinates of the point pressed
	 * 
	 * @throws BehaviorException
	 *             will be thrown when fails this process
	 * @deprecated
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point_(PointEvent event) throws BehaviorException {

		Point2D pReal = mapCtrl.getViewPort().toMapPoint(event.getPoint());
		Point imagePoint = new Point((int) event.getPoint().getX(), (int) event
				.getPoint().getY());

		FInfoDialogXML dlgXML = new FInfoDialogXML();
		int numLayersInfoable = 0;
		double tol = mapCtrl.getViewPort().toMapDistance(3);

		Layer[] sel = mapCtrl
				.getMapContext()
				.getRootLayer()
				.filter(new AndLayerFilter(LayerFilter.FEATURE,
						LayerFilter.SELECTED));
		final XMLItem[] items = new XMLItem[sel.length];

		for (int i = 0; i < sel.length; i++) {
			Layer laCapa = (Layer) sel[i];

			FBitSet newBitSet;
			try {
				newBitSet = lyrVect.queryByPoint(pReal, tol);
				items[i] = new VectorialXMLItem(newBitSet, laCapa);
				numLayersInfoable++;
			} catch (ReadDriverException e) {
				e.printStackTrace();
				throw new BehaviorException("Fallo al consultar "
						+ lyrVect.getName());
			} catch (VisitorException e) {
				e.printStackTrace();
				throw new BehaviorException("Fallo al consultar "
						+ lyrVect.getName());
			}

			assert false : "todo";
			// gtintegration should implement when we have a WMS layer
			// // TODO Hecho para el WMS. No deberia hacer falta
			// String text;
			// try {
			// InfoByPoint layer = (InfoByPoint) laCapa;
			// // text = layer.getInfo(imagePoint, tol);
			// // items[i] = new StringXMLItem(text);
			// items[i] = layer.getInfo(imagePoint, tol, null)[0];
			// numLayersInfoable++;
			// } catch (ReadDriverException e) {
			// throw new BehaviorException("No se pudo procesar la capa", e);
			// } catch (VisitorException e) {
			// throw new BehaviorException("No se pudo procesar la capa", e);
			// } catch (LoadLayerException e) {
			// throw new BehaviorException("No se pudo procesar la capa", e);
			// }
		}

		if (numLayersInfoable > 0) {
			try {
				if (PluginServices.getMainFrame() == null) {
					JDialog dialog = new JDialog();
					dlgXML.setPreferredSize(dlgXML.getSize());
					dialog.getContentPane().add(dlgXML);
					dialog.setModal(false);
					dialog.pack();
					dialog.show();

				} else {
					dlgXML = (FInfoDialogXML) PluginServices.getMDIManager()
							.addWindow(dlgXML);
				}

				dlgXML.setModel(new XMLContent() {
					private ContentHandler handler;

					public void setContentHandler(ContentHandler arg0) {
						handler = arg0;
					}

					public void parse() throws SAXException {
						handler.startDocument();

						for (int i = 0; i < items.length; i++) {
							items[i].parse(handler);
						}

						handler.endDocument();
					}
				});
				dlgXML.getXmlTree().setRootVisible(false);
				DefaultTreeModel treeModel = (DefaultTreeModel) dlgXML
						.getXmlTree().getModel();
				DefaultMutableTreeNode n;
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) dlgXML
						.getXmlTree().getModel().getRoot();
				n = root.getFirstLeaf();
				TreePath path = new TreePath(treeModel.getPathToRoot(n));
				dlgXML.getXmlTree().expandPath(path);

				dlgXML.getXmlTree().setSelectionPath(path);

			} catch (SAXException e) {
				NotificationManager.addError(
						"Error formateando los resultados", e);
			}
		}
	}

	/**
	 * When user clicks on the associated <code>MapControl</code>'s view, the
	 * point is caught and handled by this method, which will look for
	 * alphanumeric information in features at that position in the active
	 * layers.
	 * 
	 * @param event
	 *            mouse event with the coordinates of the point pressed
	 * 
	 * @throws BehaviorException
	 *             will be thrown when fails this process
	 * @deprecated
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point2(PointEvent event) throws BehaviorException {

		Point imagePoint = new Point((int) event.getPoint().getX(), (int) event
				.getPoint().getY());

		FInfoDialogXML dlgXML = new FInfoDialogXML();
		int numLayersInfoable = 0;
		double tol = mapCtrl.getViewPort().toMapDistance(3);

		FLayer[] sel = mapCtrl.getMapContext().getLayers().getActives();
		Vector itemsVector = new Vector();
		XMLItem[] aux;

		for (int i = 0; i < sel.length; i++) {
			FLayer laCapa = sel[i];
			if (laCapa instanceof InfoByPoint) {
				try {
					InfoByPoint layer = (InfoByPoint) laCapa;
					aux = layer.getInfo(imagePoint, tol, null);
					for (int j = 0; j < aux.length; j++) {
						itemsVector.add(aux[j]);
						numLayersInfoable++;
					}
				} catch (ReadDriverException e) {
					throw new BehaviorException("Processing layer", e);
				} catch (VisitorException e) {
					throw new BehaviorException("Processing layer", e);
				} catch (LoadLayerException e) {
					throw new BehaviorException("No se pudo procesar la capa",
							e);
				}
			}
		}
		final XMLItem[] items = (XMLItem[]) itemsVector.toArray(new XMLItem[0]);

		if (numLayersInfoable > 0) {
			try {
				if (PluginServices.getMainFrame() == null) {
					JDialog dialog = new JDialog();
					dlgXML.setPreferredSize(dlgXML.getSize());
					dialog.getContentPane().add(dlgXML);
					dialog.setModal(false);
					dialog.pack();
					dialog.show();

				} else {
					dlgXML = (FInfoDialogXML) PluginServices.getMDIManager()
							.addWindow(dlgXML);
				}

				dlgXML.setModel(new XMLContent() {
					private ContentHandler handler;

					public void setContentHandler(ContentHandler arg0) {
						handler = arg0;
					}

					public void parse() throws SAXException {
						handler.startDocument();

						for (int i = 0; i < items.length; i++) {
							items[i].parse(handler);
						}

						handler.endDocument();
					}
				});
				dlgXML.getXmlTree().setRootVisible(false);
				DefaultTreeModel treeModel = (DefaultTreeModel) dlgXML
						.getXmlTree().getModel();
				DefaultMutableTreeNode n;
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) dlgXML
						.getXmlTree().getModel().getRoot();
				n = root.getFirstLeaf();
				TreePath path = new TreePath(treeModel.getPathToRoot(n));
				dlgXML.getXmlTree().expandPath(path);

				dlgXML.getXmlTree().setSelectionPath(path);

			} catch (SAXException e) {
				NotificationManager.addError(
						"Error formateando los resultados", e);
			}
		}
	}

	/*
	 * (To use the old info tool, use again the point2 method!)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.
	 * cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point(PointEvent event) throws BehaviorException {

		Point imagePoint = new Point((int) event.getPoint().getX(), (int) event
				.getPoint().getY());

		int numLayersInfoable = 0;
		double tol = mapCtrl.getViewPort().toMapDistance(TOL);

		Layer[] sel = mapCtrl.getMapContext().getLayers().getActives();
		Vector<XMLItem> itemsVector = new Vector<XMLItem>();
		XMLItem[] aux;

		for (int i = 0; i < sel.length; i++) {
			FLayer laCapa = sel[i];
			if (laCapa instanceof InfoByPoint) {
				try {
					InfoByPoint layer = (InfoByPoint) laCapa;
					if (!(laCapa.getParentLayer().isActive())) {
						aux = layer.getInfo(imagePoint, tol, null);
						for (int j = 0; j < aux.length; j++) {
							itemsVector.add(aux[j]);
							numLayersInfoable++;
						}
					}
				} catch (ReadDriverException e) {
					throw new BehaviorException("Processing layer", e);
				} catch (VisitorException e) {
					throw new BehaviorException("Processing layer", e);
				} catch (LoadLayerException e) {
					throw new BehaviorException("No se pudo procesar la capa",
							e);
				}
			}
		}
		final XMLItem[] items = (XMLItem[]) itemsVector.toArray(new XMLItem[0]);
		FInfoDialog dlgXML = new FInfoDialog();

		if (numLayersInfoable > 0) {
			try {
				if (PluginServices.getMainFrame() == null) {
					JDialog dialog = new JDialog();
					dlgXML.setPreferredSize(dlgXML.getSize());
					dialog.getContentPane().add(dlgXML);
					dialog.setModal(false);
					dialog.pack();
					dialog.show();

				} else {
					dlgXML = (FInfoDialog) PluginServices.getMDIManager()
							.addWindow(dlgXML);
				}

			} catch (Exception e) {
				NotificationManager.addError("FeatureInfo", e);
				e.printStackTrace();
			}
			dlgXML.setLayers(items);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#getCursor()
	 */
	public Cursor getCursor() {
		return cur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#cancelDrawing()
	 */
	public boolean cancelDrawing() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#pointDoubleClick
	 * (com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void pointDoubleClick(PointEvent event) throws BehaviorException {
		// TODO Auto-generated method stub

	}
}