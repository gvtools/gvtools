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
package com.iver.andami.ui.mdiFrame;

import javax.swing.JPopupMenu;

public class JPopUpMenu extends JPopupMenu implements EnableTextSupport {
	private String enableText;
	private String toolTip = super.getToolTipText();

	/**
	 * @return Returns the enableText.
	 */
	public String getEnableText() {
		return enableText;
	}

	/**
	 * @param enableText
	 *            The enableText to set.
	 */
	public void setEnableText(String enableText) {
		this.enableText = enableText;
	}

	/**
	 * @see java.awt.Component#setVisible(boolean)
	 */
	public void setEnabled(boolean aFlag) {
		super.setEnabled(aFlag);
		if (aFlag) {
			setToolTipText(toolTip);
		} else {
			setToolTipText(enableText);
		}
	}

	/**
	 * @see javax.swing.JComponent#setToolTipText(java.lang.String)
	 */
	public void setToolTip(String text) {
		toolTip = text;
	}

	/**
	 * 
	 */
	public JPopUpMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 */
	public JPopUpMenu(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}
}
