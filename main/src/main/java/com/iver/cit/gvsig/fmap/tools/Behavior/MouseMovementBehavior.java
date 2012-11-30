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

import java.awt.event.MouseEvent;

import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PointListener;
import com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener;

/**
 * <p>
 * Behavior that permits user to move and drag the mouse on the image of the
 * associated <code>MapControl</code> instance using a {@link PointListener
 * PointListener}.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class MouseMovementBehavior extends Behavior {
	/**
	 * Tool listener used to work with the <code>MapControl</code> object.
	 * 
	 * @see #getListener()
	 * @see #setListener(ToolListener)
	 */
	protected PointListener listener;

	/**
	 * <p>
	 * Creates a new behavior for moving or dragging the mouse on the image of
	 * the associated <code>MapControl</code> instance.
	 * </p>
	 * 
	 * @param mli
	 *            listener used to permit this object to work with the
	 *            associated <code>MapControl</code>
	 */
	public MouseMovementBehavior(PointListener mli) {
		listener = mli;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#mouseDragged(java.awt
	 * .event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) throws BehaviorException {
		mouseMoved(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Behavior.Behavior#mouseMoved(java.awt.event
	 * .MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) throws BehaviorException {
		PointEvent event = new PointEvent(e.getPoint(), e);
		listener.point(event);
	}

	/**
	 * <p>
	 * Sets a tool listener to work with the <code>MapControl</code> using this
	 * behavior.
	 * </p>
	 * 
	 * @param listener
	 *            a <code>PointListener</code> object for this behavior
	 */
	public void setListener(ToolListener listener) {
		this.listener = (PointListener) listener;
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
