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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PointListener;

/**
 * <p>
 * Listener for doing a <i>zoom out</i> operation on the extent of the
 * <code>ViewPort</code> of the associated {@link MapControl MapControl} object,
 * selecting a point of the <code>MapControl</code> by a single click of any
 * mouse's button.
 * </p>
 * 
 * <p>
 * Calculates the new extent <i>r</i> with this equations: <code><br>
 *   ViewPort vp = mapControl.getMapContext().getViewPort();<br>
 *   Point2D p2 = vp.toMapPoint(event.getPoint());<br>
 *   double factor = 1/MapContext.ZOOMOUTFACTOR;<br>
 *   Rectangle2D.Double r = new Rectangle2D.Double();<br>
 *   double nuevoX = p2.getX() - ((vp.getExtent().getWidth() * factor) / 2.0);<br>
 *   double nuevoY = p2.getY() - ((vp.getExtent().getHeight() * factor) / 2.0);<br>
 *   r.x = nuevoX;<br>
 *   r.y = nuevoY;<br>
 *   r.width = vp.getExtent().getWidth() * factor;<br>
 *   r.height = vp.getExtent().getHeight() * factor;<br>
 *   vp.setExtent(r);
 *  </code>
 * </p>
 * 
 * <p>
 * The ultimately extent will be an adaptation from that, calculated by the
 * <code>ViewPort</code> bearing in mind the ratio of the available rectangle.
 * </p>
 * 
 * @see MapContext#ZOOMOUTFACTOR
 * @see ViewPort#setExtent(Rectangle2D)
 * @see ZoomInListenerImpl
 * @see ZoomOutRightButtonListener
 * 
 * @author Vicente Caballero Navarro
 */
public class ZoomOutListenerImpl implements PointListener {
	/**
	 * <p>
	 * Default <i>zoom out</i> factor.
	 * </p>
	 * <p>
	 * Doing a <i>zoom out</i> operation, increases the focal distance and
	 * decreases the eyesight angle to the surface. This allows view a bigger
	 * area but with the items smaller.
	 * </p>
	 */
	public static double ZOOMOUTFACTOR = 0.5;

	/**
	 * The image to display when the cursor is active.
	 */
	private final Image izoomout = new ImageIcon(getClass().getResource(
			"/images/ZoomOutCursor.gif")).getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(
			izoomout, new Point(16, 16), "");

	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	private MapControl mapControl;

	/**
	 * <p>
	 * Creates a new <code>ZoomOutListenerImpl</code> object.
	 * </p>
	 * 
	 * @param mapControl
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public ZoomOutListenerImpl(MapControl mapControl) {
		this.mapControl = mapControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.
	 * cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point(PointEvent event) {
		ViewPort vp = mapControl.getViewPort();
		Point2D p2 = vp.toMapPoint(event.getPoint());

		double nuevoX;
		double nuevoY;
		double factor = 1 / ZOOMOUTFACTOR;
		Rectangle2D.Double r = new Rectangle2D.Double();

		nuevoX = p2.getX() - ((vp.getExtent().getWidth() * factor) / 2.0);
		nuevoY = p2.getY() - ((vp.getExtent().getHeight() * factor) / 2.0);
		r.x = nuevoX;
		r.y = nuevoY;
		r.width = vp.getExtent().getWidth() * factor;
		r.height = vp.getExtent().getHeight() * factor;

		vp.setExtent(r);
		// mapControl.getMapContext().clearAllCachingImageDrawnLayers();
		// mapControl.drawMap(false);
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
		System.out.println("cancelDrawing del ZoomOutListenerImpl");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#pointDoubleClick
	 * (com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void pointDoubleClick(PointEvent event) {
		// TODO Auto-generated method stub
	}
}
