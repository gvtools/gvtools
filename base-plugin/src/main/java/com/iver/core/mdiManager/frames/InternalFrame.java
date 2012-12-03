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
package com.iver.core.mdiManager.frames;

import javax.swing.JInternalFrame;

/**
 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
 * 
 */
public class InternalFrame extends JInternalFrame implements IFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a non-resizable, non-closable, non-maximizable, non-iconifiable
	 * <code>JInternalFrame</code> with no title.
	 */
	public InternalFrame() {
		super();
	}

	/**
	 * Creates a non-resizable, non-closable, non-maximizable, non-iconifiable
	 * <code>JInternalFrame</code> with the specified title. Note that passing
	 * in a <code>null</code> <code>title</code> results in unspecified behavior
	 * and possibly an exception.
	 * 
	 * @param title
	 *            the non-<code>null</code> <code>String</code> to display in
	 *            the title bar
	 */
	public InternalFrame(String title) {
		super(title);
	}

	/**
	 * Creates a non-closable, non-maximizable, non-iconifiable
	 * <code>JInternalFrame</code> with the specified title and resizability.
	 * 
	 * @param title
	 *            the <code>String</code> to display in the title bar
	 * @param resizable
	 *            if <code>true</code>, the internal frame can be resized
	 */
	public InternalFrame(String title, boolean resizable) {
		super(title, resizable);
	}

	/**
	 * Creates a non-maximizable, non-iconifiable <code>JInternalFrame</code>
	 * with the specified title, resizability, and closability.
	 * 
	 * @param title
	 *            the <code>String</code> to display in the title bar
	 * @param resizable
	 *            if <code>true</code>, the internal frame can be resized
	 * @param closable
	 *            if <code>true</code>, the internal frame can be closed
	 */
	public InternalFrame(String title, boolean resizable, boolean closable) {
		super(title, resizable, closable);
	}

	/**
	 * Creates a non-iconifiable <code>JInternalFrame</code> with the specified
	 * title, resizability, closability, and maximizability.
	 * 
	 * @param title
	 *            the <code>String</code> to display in the title bar
	 * @param resizable
	 *            if <code>true</code>, the internal frame can be resized
	 * @param closable
	 *            if <code>true</code>, the internal frame can be closed
	 * @param maximizable
	 *            if <code>true</code>, the internal frame can be maximized
	 */
	public InternalFrame(String title, boolean resizable, boolean closable,
			boolean maximizable) {
		super(title, resizable, closable, maximizable);
	}

	/**
	 * Creates a <code>JInternalFrame</code> with the specified title,
	 * resizability, closability, maximizability, and iconifiability. All
	 * <code>JInternalFrame</code> constructors use this one.
	 * 
	 * @param title
	 *            the <code>String</code> to display in the title bar
	 * @param resizable
	 *            if <code>true</code>, the internal frame can be resized
	 * @param closable
	 *            if <code>true</code>, the internal frame can be closed
	 * @param maximizable
	 *            if <code>true</code>, the internal frame can be maximized
	 * @param iconifiable
	 *            if <code>true</code>, the internal frame can be iconified
	 */
	public InternalFrame(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {

		super(title, resizable, closable, maximizable, iconifiable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.core.mdiManager.frames.IFrame#setHeight(int)
	 */
	public void setHeight(int height) {
		super.setSize(getWidth(), height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.core.mdiManager.frames.IFrame#setWidth(int)
	 */
	public void setWidth(int width) {
		super.setSize(width, getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.core.mdiManager.frames.IFrame#setX(int)
	 */
	public void setX(int x) {
		super.setLocation(x, getX());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.core.mdiManager.frames.IFrame#setY(int)
	 */
	public void setY(int y) {
		super.setLocation(y, getY());
	}

}
