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
package com.iver.cit.gvsig.fmap.tools;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.Events.MeasureEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PolylineListener;

/**
 * <p>
 * Listener that selects all features of the active and vector layers which
 * intersect with the defined polygon area in the associated {@link MapControl
 * MapControl} object.
 * </p>
 * 
 * <p>
 * The selection will be produced after user finishes the creation of the
 * polyline.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class PolygonSelectionListener implements PolylineListener {
	/**
	 * The image to display when the cursor is active.
	 */
	private final Image img = new ImageIcon(
			MapControl.class.getResource("/images/PoligonCursor.png"))
			.getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(img,
			new Point(16, 16), "");

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private MapControl mapCtrl;

	/**
	 * <p>
	 * Creates a new <code>PolygonSelectionListener</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> where is drawn the polyline
	 */
	public PolygonSelectionListener(MapControl mc) {
		this.mapCtrl = mc;
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
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PolylineListener#points(com.iver
	 * .cit.gvsig.fmap.tools.Events.MeasureEvent)
	 */
	public void points(MeasureEvent event) throws BehaviorException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PolylineListener#points(com.iver
	 * .cit.gvsig.fmap.tools.Events.MeasureEvent)
	 */
	public void pointFixed(MeasureEvent event) throws BehaviorException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PolylineListener#polylineFinished
	 * (com.iver.cit.gvsig.fmap.tools.Events.MeasureEvent)
	 */
	public void polylineFinished(MeasureEvent event) throws BehaviorException {
		// try {
		// ViewPort vp = mapCtrl.getMapContext().getViewPort();
		//
		// GeneralPathX gp = event.getGP();
		// IGeometry geom = ShapeFactory.createPolygon2D(gp);
		// FLayer[] actives = mapCtrl.getMapContext().getLayers().getActives();
		// for (int i = 0; i < actives.length; i++) {
		// if (actives[i] instanceof FLyrVect) {
		// FLyrVect lyrVect = (FLyrVect) actives[i];
		// FBitSet oldBitSet = lyrVect.getSource().getRecordset()
		// .getSelection();
		// FBitSet newBitSet = lyrVect.queryByShape(geom,
		// DefaultStrategy.INTERSECTS);
		// if (event.getEvent().isControlDown())
		// newBitSet.xor(oldBitSet);
		// lyrVect.getRecordset().setSelection(newBitSet);
		// }
		// }
		//
		// } catch (ReadDriverException e) {
		// throw new BehaviorException("No se pudo hacer la selecci�n");
		// } catch (VisitorException e) {
		// throw new BehaviorException("No se pudo hacer la selecci�n");
		// }
	}
}
