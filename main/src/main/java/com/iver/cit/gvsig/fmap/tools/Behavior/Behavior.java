/*
 * Created on 28-oct-2004
 */
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

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener;

/**
 * <p>
 * When user is working with a tool on a {@link MapControl MapControl} instance,
 * <code>Behavior</code> defines the basic ways of interacting: selecting a
 * point, a circle, a rectangle, or ...
 * </p>
 * 
 * <p>
 * All events generated will be <code>MouseEvent</code>, and will depend on the
 * nature of the <i>behavior</i>, like the kind of tool for applying the
 * changes.
 * </p>
 * 
 * <p>
 * <code>Behavior</code> defines the common and basic functionality for all
 * kinds of interacting ways with the <code>MapControl</code> object.
 * </p>
 * 
 * @see IBehavior
 * 
 * @author Luis W. Sevilla
 */
public abstract class Behavior implements IBehavior {
	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 * 
	 * @see #getMapControl()
	 * @see #setMapControl(MapControl)
	 */
	private MapControl mapControl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#getListener()
	 */
	public abstract ToolListener getListener();

	/*
	 * <p>Draws as much of the associated <code>MapControl</code> image as is
	 * currently available. The image is drawn with its top-left corner at (0,
	 * 0) in this graphics context's coordinate space. Transparent pixels in the
	 * image do not affect whatever pixels are already there.</p>
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#paintComponent(java.
	 * awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		BufferedImage img = getMapControl().getImage();

		if (img != null) {
			g.drawImage(img, 0, 0, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#setMapControl(com.iver
	 * .cit.gvsig.fmap.MapControl)
	 */
	public void setMapControl(MapControl mc) {
		mapControl = mc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#getCursor()
	 */
	public Cursor getCursor() {
		return getListener().getCursor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#getMapControl()
	 */
	public MapControl getMapControl() {
		return mapControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseClicked(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseEntered(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseExited(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mousePressed(java.awt
	 * .event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseReleased(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseDragged(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseMoved(java.awt.
	 * event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) throws BehaviorException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.IBehavior#mouseWheelMoved(java
	 * .awt.event.MouseWheelEvent)
	 */
	public void mouseWheelMoved(MouseWheelEvent e) throws BehaviorException {
	}
}
