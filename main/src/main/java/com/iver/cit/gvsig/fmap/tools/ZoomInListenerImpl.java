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
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.fmap.tools.Events.RectangleEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.RectangleListener;

/**
 * <p>
 * Listener for doing a <i>zoom in</i> operation of the extent of the
 * <code>ViewPort</code> of the associated {@link MapControl MapControl} object,
 * defining a rectangular area.
 * </p>
 * 
 * <p>
 * If the area defined is smaller than 3 pixels x 3 pixels holds the zoom,
 * otherwise, calculates the new extent <i>r</i> with this equations: <code><br>
 *   double factor = 1/MapContext.ZOOMINFACTOR;<br>
 * 	 Rectangle2D rect = event.getWorldCoordRect();<br>
 *   Rectangle2D.Double r = new Rectangle2D.Double();<br>
 *   ViewPort vp = mapCtrl.getMapContext().getViewPort();<br>
 *   double nuevoX = rect.getMaxX() - ((vp.getExtent().getWidth() * factor) / 2.0);<br>
 *   double nuevoY = rect.getMaxY() - ((vp.getExtent().getHeight() * factor) / 2.0);<br>
 *   Rectangle2D.Double r; // This will be the new extent<br>
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
 * <code>ViewPort</code> bearing in mind the ratio of the available rectangle
 * where display the graphical information.
 * </p>
 * 
 * @see MapContext#ZOOMINFACTOR
 * @see ViewPort#setExtent(Rectangle2D)
 * @see ZoomOutListenerImpl
 * @see ZoomOutRightButtonListener
 * 
 * @author Vicente Caballero Navarro
 */
public class ZoomInListenerImpl implements RectangleListener {
	/**
	 * <p>
	 * Default <i>zoom in</i> factor.
	 * </p>
	 * <p>
	 * Doing a <i>zoom in</i> operation, decreases the focal distance and
	 * increases the eyesight angle to the surface. This allows view an smaller
	 * area but with the items bigger.
	 * </p>
	 */
	public static double ZOOMINFACTOR = 2;

	/**
	 * The image to display when the cursor is active.
	 */
	private final Image izoomin = new ImageIcon(getClass().getResource(
			"/images/ZoomInCursor.gif")).getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(
			izoomin, new Point(16, 16), "");

	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	private MapControl mapCtrl;

	/**
	 * <p>
	 * Creates a new <code>ZoomInListenerImpl</code> object.
	 * </p>
	 * 
	 * @param mapCtrl
	 *            the <code>MapControl</code> where is defined the rectangle
	 */
	public ZoomInListenerImpl(MapControl mapCtrl) {
		this.mapCtrl = mapCtrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.RectangleListener#rectangle(com
	 * .iver.cit.gvsig.fmap.tools.Events.RectangleEvent)
	 */
	public void rectangle(RectangleEvent event) {
		Rectangle2D rect = event.getWorldCoordRect();
		Rectangle2D pixelRect = event.getPixelCoordRect();
		Rectangle2D.Double r = new Rectangle2D.Double();

		ViewPort vp = mapCtrl.getViewPort();

		if ((pixelRect.getWidth() < 3) && (pixelRect.getHeight() < 3)) {
			if (vp.getExtent() != null) {
				double factor = 1 / ZOOMINFACTOR;
				double nuevoX = rect.getMaxX()
						- ((vp.getExtent().getWidth() * factor) / 2.0);
				double nuevoY = rect.getMaxY()
						- ((vp.getExtent().getHeight() * factor) / 2.0);
				r.x = nuevoX;
				r.y = nuevoY;
				r.width = vp.getExtent().getWidth() * factor;
				r.height = vp.getExtent().getHeight() * factor;
				vp.setExtent(r);
			}
		} else {
			vp.setExtent(event.getWorldCoordRect());
		}
		// mapCtrl.getMapContext().clearAllCachingImageDrawnLayers();
		// mapCtrl.drawMap(false);
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
}
