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

import org.gvsig.util.EnvelopeUtils;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.Events.RectangleEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.RectangleListener;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * <p>
 * Listener that selects all features of the active and vector layers which
 * intersect with the defined rectangle area in the associated
 * {@link MapControl MapControl} object.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class RectangleSelectionListener implements RectangleListener {
	/**
	 * The image to display when the cursor is active.
	 */
	private final Image img = new ImageIcon(
			MapControl.class.getResource("/images/RectSelectCursor.gif"))
			.getImage();

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
	 * <p>
	 * Creates a new <code>RectangleSelectionListener</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> where is defined the rectangle
	 */
	public RectangleSelectionListener(MapControl mc) {
		this.mapCtrl = mc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.RectangleListener#rectangle(com
	 * .iver.cit.gvsig.fmap.tools.Events.RectangleEvent)
	 */
	public void rectangle(RectangleEvent event) throws BehaviorException {
		Rectangle2D rect = event.getWorldCoordRect();
		GeometryFactory gf = new GeometryFactory();
		Geometry geom = gf.toGeometry(EnvelopeUtils.toEnvelope(rect));
		PointSelectionListener.selectByGeometry(event.getEvent()
				.isControlDown(), geom, mapCtrl);
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
}
