/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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

/* CVS MESSAGES:
 *
 * $Id: SymbolPreviewer.java 28368 2009-05-04 15:30:35Z vcaballero $
 * $Log: SymbolPreviewer.java,v $
 * Revision 1.6  2007/08/16 06:54:35  jvidal
 * javadoc updated
 *
 * Revision 1.5  2007/08/07 11:41:15  jvidal
 * javadoc
 *
 * Revision 1.4  2007/04/05 16:08:34  jaume
 * Styled labeling stuff
 *
 * Revision 1.3  2007/04/04 16:01:14  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.3  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/08 15:43:05  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/01/16 11:52:11  jaume
 * *** empty log message ***
 *
 * Revision 1.8  2007/01/10 17:05:05  jaume
 * moved to FMap and gvSIG
 *
 * Revision 1.7  2006/10/30 19:30:35  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2006/10/29 23:53:49  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/10/29 21:45:12  jaume
 * centers in x the "none selected message"
 *
 * Revision 1.4  2006/10/29 21:43:34  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/29 21:40:29  jaume
 * centers in x the "none selected message"
 *
 * Revision 1.2  2006/10/26 07:46:58  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/25 10:50:41  jaume
 * movement of classes and gui stuff
 *
 * Revision 1.2  2006/10/24 19:54:55  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/24 16:31:12  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.geotools.styling.Symbolizer;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.styling.EditorTool;

/**
 * SymbolPreviewer creates a JPanel used for the user to watch the preview of a
 * symbol.It has an square form with a white background and the symbol is
 * inserted in the middle.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class SymbolPreviewer extends JPanel {
	private int hGap = 5, vGap = 5;
	private Symbolizer symbol;
	private EditorTool prevTool;

	/**
	 * constructor method
	 * 
	 */
	public SymbolPreviewer() {
		super(true);
		setBackground(Color.WHITE);
	}

	/**
	 * Returns the symbol printed inside
	 * 
	 * @return symbol
	 */
	public Symbolizer getSymbol() {
		return symbol;
	}

	/**
	 * Establishes the symbol to be showed in the symbolpreviewer panel
	 * 
	 * @param symbol
	 */
	public void setSymbol(Symbolizer symbol) {
		this.symbol = symbol;
		repaint();
	}

	/**
	 * Draws the symbol in the middle of the pane in order to create a preview
	 * of the final one.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints old = g2.getRenderingHints();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.translate(hGap, vGap);
		Rectangle r = getBounds();
		r = new Rectangle(0, 0, (int) (r.getWidth() - (hGap * 2)),
				(int) (r.getHeight() - (vGap * 2)));

		if (symbol != null) {
			SymbologyUtils.drawInsideRectangle(symbol, g2, r);
		} else {
			String noneSelected = "["
					+ PluginServices.getText(this, "preview_not_available")
					+ "]";
			FontMetrics fm = g2.getFontMetrics();
			int lineWidth = fm.stringWidth(noneSelected);
			float scale = (float) r.getWidth() / lineWidth;
			Font f = g2.getFont();
			float fontSize = f.getSize() * scale;
			g2.setFont(f.deriveFont(fontSize));

			g2.drawString(noneSelected, (r.x * scale) - (hGap / 2), r.height
					/ 2 + vGap * scale);
		}
		g2.setRenderingHints(old);
	}

	/**
	 * Sets the EditorTool for the pane.
	 * 
	 * @param l
	 *            ,EditorTool
	 */
	public EditorTool setEditorTool(EditorTool tool) {
		EditorTool previous = prevTool;

		MouseListener[] ml = getMouseListeners();
		for (int i = 0; i < ml.length; i++) {
			super.removeMouseListener(ml[i]);
		}
		MouseMotionListener[] mml = getMouseMotionListeners();
		for (int i = 0; i < mml.length; i++) {
			super.removeMouseMotionListener(mml[i]);
		}

		if (tool != null) {
			super.addMouseListener(tool);
			setCursor(tool.getCursor());
			super.addMouseMotionListener(tool);
		}
		prevTool = tool;
		return previous;
	}

}
