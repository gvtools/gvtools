/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
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
package com.iver.cit.gvsig.gui.styling;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

/**
 * Abstract class that specifies the methods that are useful for the edition.
 * Most of them are methods in relation with mouse events in order to control
 * its position, what button is pressed and so on.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public abstract class EditorTool implements MouseListener, MouseMotionListener {

	public static final Dimension SMALL_BTN_SIZE = new Dimension(68, 34);
	protected JComponent owner;

	/**
	 * Constructor method
	 * 
	 * @param targetEditor
	 */
	public EditorTool(JComponent targetEditor) {
		super();
		owner = targetEditor;
	}

	/**
	 * Returns the cursor
	 */
	public abstract Cursor getCursor();

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		owner.setCursor(getCursor());
	}

	public void mouseExited(MouseEvent e) {
		owner.setCursor(Cursor.getDefaultCursor());
	}

	public void mouseMoved(MouseEvent e) {
	}

	public abstract AbstractButton getButton();

	public abstract boolean isSuitableFor(Object obj);

	public abstract String getID();

	public abstract void setModel(Object objectToBeEdited);
}
