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

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.fmap.tools.Events.MoveEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PanListener;

/**
 * <p>
 * Listener for moving the extent of the associated {@link MapControl
 * MapControl} object according the movement between the initial and final
 * points of line determined by the movement dragging with the third button of
 * the mouse.
 * </p>
 * 
 * <p>
 * Updates the extent of its <code>ViewPort</code> with the new position.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class PanListenerImpl implements PanListener {
	/**
	 * The image to display when the cursor is active.
	 */
	private final Image ipan = new ImageIcon(getClass().getResource(
			"/images/Hand.gif")).getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(ipan,
			new Point(16, 16), "");

	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	private MapControl mapControl;

	/**
	 * <p>
	 * Creates a new listener for changing the position of the extent of the
	 * associated {@link MapControl MapControl} object.
	 * </p>
	 * 
	 * @param mapControl
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public PanListenerImpl(MapControl mapControl) {
		this.mapControl = mapControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PanListener#move(com.iver.cit
	 * .gvsig.fmap.tools.Events.MoveEvent)
	 */
	public void move(MoveEvent event) {
		ViewPort vp = mapControl.getViewPort();

		// System.out.println(vp);
		Point2D from = vp.toMapPoint(event.getFrom());
		Point2D to = vp.toMapPoint(event.getTo());

		Rectangle2D.Double r = new Rectangle2D.Double();
		Rectangle2D extent = vp.getExtent();
		r.x = extent.getX() - (to.getX() - from.getX());
		r.y = extent.getY() - (to.getY() - from.getY());
		r.width = extent.getWidth();
		r.height = extent.getHeight();
		vp.setExtent(r);
		// mapControl.getMapContext().clearAllCachingImageDrawnLayers();
		// mapControl.drawMap();
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
		return true;
	}
}
