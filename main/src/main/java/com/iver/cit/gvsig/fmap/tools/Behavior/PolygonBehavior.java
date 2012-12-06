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

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.MeasureEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PolylineListener;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * <p>
 * Behavior that permits user to draw a polygon by its vertexes on the image of
 * the associated <code>MapControl</code> using a {@link PolylineListener
 * PolylineListener}.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 * @author Pablo Piqueras Bartolom�
 */
public class PolygonBehavior extends PolylineBehavior {
	/**
	 * <p>
	 * Creates a new behavior for drawing a polygon by its vertexes.
	 * </p>
	 * 
	 * @param ali
	 *            tool listener used to permit this object to work with the
	 *            associated <code>MapControl</code>
	 */
	public PolygonBehavior(PolylineListener ali) {
		super(ali);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.PolylineBehavior#paintComponent
	 * (java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.PolylineBehavior#mousePressed(
	 * java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) throws BehaviorException {
		if (e.getClickCount() == 2) {
			listener.polylineFinished(newMeasureEvent(e));

			coordinates.clear();

			isClicked = false;
		} else {
			isClicked = true;

			if (coordinates.size() == 0) {
				addPoint(getMapControl().getViewPort().toMapPoint(e.getPoint()));
				addPoint(getMapControl().getViewPort().toMapPoint(e.getPoint()));
				addPoint(getMapControl().getViewPort().toMapPoint(e.getPoint()));
			} else {
				addAntPoint(getMapControl().getViewPort().toMapPoint(
						e.getPoint()));
			}

			listener.pointFixed(newMeasureEvent(e));
		}
	}

	/**
	 * <p>
	 * Adds a new vertex to the polygon.
	 * </p>
	 * 
	 * @param p
	 *            a new vertex to the polygon
	 */
	private void addAntPoint(Point2D p) {
		coordinates.add(coordinates.size() - 1,
				new Coordinate(p.getX(), p.getY()));
	}

	/**
	 * <p>
	 * Changes the last vertex added to the polygon.
	 * </p>
	 * 
	 * @param p
	 *            a new vertex to the polygon
	 */
	private void changeAntPoint(Point2D p) {
		if (coordinates.size() > 2) {
			coordinates.set(coordinates.size() - 2,
					new Coordinate(p.getX(), p.getY()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.PolylineBehavior#mouseMoved(java
	 * .awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) throws BehaviorException {
		if (isClicked) {
			changeAntPoint(getMapControl().getViewPort().toMapPoint(
					e.getPoint()));

			MeasureEvent event = newMeasureEvent(e);

			listener.points(event);

			getMapControl().repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.PolylineBehavior#mouseDragged(
	 * java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) throws BehaviorException {
		mouseMoved(e);
	}
}
