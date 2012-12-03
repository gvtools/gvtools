/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2007 IVER T.I. and Generalitat Valenciana.
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
 *   Av. Blasco Ibáñez, 50
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

/**
 * 
 */
package com.iver.core.mdiManager.frames;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * This interface is a model for CorePlugin windows. When CorePlugin receives an
 * IWindow object, it creates a JInternalFrame or a JDialog, depending on the
 * specified properties.
 * 
 * This interface allows coreplugin to talk to JInternalFrames and JDialogs in a
 * uniform way.
 * 
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 */
public interface IFrame {
	/**
	 * Gets the title property
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * Sets the title property.
	 * 
	 * @param title
	 *            The new title.
	 */
	public void setTitle(String title);

	/**
	 * Returns the current x coordinate of the window's origin.
	 * 
	 * @return Returns the value (in pixels) of the x coordinate of the window's
	 *         origin.
	 */
	public int getX();

	/**
	 * Sets the value of the x coordinate for the origin of the associated
	 * window.
	 * 
	 * @param x
	 *            The value (in pixels) of the x coordinate
	 */
	public void setX(int x);

	/**
	 * Returns the current y coordinate of the window's origin.
	 * 
	 * @return Returns the value (in pixels) of the y coordinate of the window's
	 *         origin.
	 */
	public int getY();

	/**
	 * Sets the value of the y coordinate for the origin of the associated
	 * window.
	 * 
	 * @param y
	 *            The value (in pixels) of the y coordinate
	 */
	public void setY(int y);

	/**
	 * Gets the window height.
	 * 
	 * @return The window height (in pixels).
	 */
	public int getHeight();

	/**
	 * Gets the window width.
	 * 
	 * @return The window width (in pixels).
	 */
	public int getWidth();

	/**
	 * Sets the window height.
	 * 
	 * @param The
	 *            window height (in pixels)
	 */
	public void setHeight(int height);

	/**
	 * Sets the window width.
	 * 
	 * @param The
	 *            window width (in pixels)
	 */
	public void setWidth(int width);

	/**
	 * Gets the minimum allowed size for this window.
	 * 
	 * @return minSize The minimum allowed size for this window.
	 */
	public Dimension getMinimumSize();

	/**
	 * Sets the minimum allowed size for this window. If null is provided, the
	 * minimum size is disabled (and thus the window can be resized to any
	 * size).
	 * 
	 * @param minSize
	 *            The minimum allowed size for this window.
	 */
	public void setMinimumSize(Dimension minSize);

	/**
	 * Gets the window bounds.
	 * 
	 * @return The window bounds.
	 */
	public Rectangle getBounds();

	/**
	 * Sets the window bounds.
	 * 
	 * @param bounds
	 *            The window bounds.
	 */
	public void setBounds(Rectangle bounds);

	public void setLocation(int x, int y);

}
