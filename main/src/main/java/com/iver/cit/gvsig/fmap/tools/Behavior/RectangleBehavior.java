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
package com.iver.cit.gvsig.fmap.tools.Behavior;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.iver.cit.gvsig.fmap.ViewPort;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.RectangleEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.RectangleListener;
import com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener;

/**
 * <p>
 * Behavior that permits user to select a rectangular area on the associated
 * <code>MapControl</code> using a {@link RectangleListener RectangleListener}.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 * @author Pablo Piqueras Bartolom�
 */
public class RectangleBehavior extends Behavior {
	/**
	 * First point of the rectangle area, that represents a corner.
	 */
	private Point2D m_FirstPoint;

	/**
	 * Second point of the rectangle area, that represents the opposite corner
	 * of the first, along the diagonal.
	 */
	private Point2D m_LastPoint;

	/**
	 * Tool listener used to work with the <code>MapControl</code> object.
	 * 
	 * @see #getListener()
	 * @see #setListener(ToolListener)
	 */
	private RectangleListener listener;

	/**
	 * <p>
	 * Creates a new behavior for selecting rectangle areas.
	 * </p>
	 * 
	 * @param zili
	 *            listener used to permit this object to work with the
	 *            associated <code>MapControl</code>
	 */
	public RectangleBehavior(RectangleListener zili) {
		listener = zili;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#paintComponent(java.awt
	 * .Graphics)
	 */
	public void paintComponent(Graphics g) {
		BufferedImage img = getMapControl().getImage();
		g.drawImage(img, 0, 0, null);
		g.setColor(Color.black);
		g.setXORMode(Color.white);

		// Borramos el anterior
		Rectangle r = new Rectangle();

		// Dibujamos el actual
		if ((m_FirstPoint != null) && (m_LastPoint != null)) {
			r.setFrameFromDiagonal(m_FirstPoint, m_LastPoint);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
		g.setPaintMode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#mousePressed(java.awt
	 * .event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			m_FirstPoint = e.getPoint();
			getMapControl().repaint();
		}
		if (listener.cancelDrawing()) {
			getMapControl().cancelDrawing();
			getMapControl().repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#mouseReleased(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) throws BehaviorException {
		if (m_FirstPoint == null)
			return;
		Point2D p1;
		Point2D p2;
		Point pScreen = e.getPoint();

		ViewPort vp = getMapControl().getViewPort();

		p1 = vp.toMapPoint(m_FirstPoint);
		p2 = vp.toMapPoint(pScreen);

		if (e.getButton() == MouseEvent.BUTTON1) {
			// Fijamos el nuevo extent
			Rectangle2D.Double r = new Rectangle2D.Double();
			r.setFrameFromDiagonal(p1, p2);

			Rectangle2D rectPixel = new Rectangle();
			rectPixel.setFrameFromDiagonal(m_FirstPoint, pScreen);

			RectangleEvent event = new RectangleEvent(r, e, rectPixel);
			listener.rectangle(event);
		}

		m_FirstPoint = null;
		m_LastPoint = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#mouseDragged(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		m_LastPoint = e.getPoint();
		getMapControl().repaint();
	}

	/**
	 * <p>
	 * Sets a tool listener to work with the <code>MapControl</code> using this
	 * behavior.
	 * </p>
	 * 
	 * @param listener
	 *            a <code>RectangleListener</code> object for this behavior
	 */
	public void setListener(ToolListener listener) {
		this.listener = (RectangleListener) listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#getListener()
	 */
	public ToolListener getListener() {
		return listener;
	}
}
