/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
 */

/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
package com.iver.cit.gvsig.gui.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Displays a colour.
 */
public class ColorPanel extends JPanel {
	private Color fillColor = Color.red;
	private Color lineColor = Color.green;
	private int margin = 0;

	public ColorPanel() {
		setBackground(Color.white);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Before I simply set the ColorPanel's background colour. But I found
		// this
		// caused weird paint effects e.g. a second copy of the panel appearing
		// at the top left corner of the rendered image. [Jon Aquino].
		// <<TODO:DESIGN>> Use the GraphicsState class [Jon Aquino]
		Color originalColor = g.getColor();
		g.setColor(getBackground());
		((Graphics2D) g).setStroke(fillStroke);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(fillColor);
		g.fillRect(margin, margin, getWidth() - 1 - margin - margin,
				getHeight() - 1 - margin - margin);

		if (lineColor != null) {
			g.setColor(lineColor);
			((Graphics2D) g).setStroke(lineStroke);

			// -1 to ensure the rectangle doesn't extend past the panel [Jon
			// Aquino]
			g.drawRect(margin, margin, getWidth() - 1 - margin - margin,
					getHeight() - 1 - margin - margin);
		}

		// <<TODO:DESIGN>> Put the next line in a finally block [Jon Aquino]
		g.setColor(originalColor);
	}

	/** Workaround for bug 4238829 in the Java bug database */
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		validate();
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * @param lineColor
	 *            the new line colour, or null to not draw the line
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	private BasicStroke fillStroke = new BasicStroke(1);
	private int lineWidth = 1;
	private BasicStroke lineStroke = new BasicStroke(lineWidth);

	public void setLineWidth(int lineWidth) {
		lineStroke = new BasicStroke(lineWidth);
		this.lineWidth = lineWidth;
	}

	public int getLineWidth() {
		return lineWidth;
	}

}
