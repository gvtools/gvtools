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
 * $Id: AbstractParentPanel.java 18623 2008-02-08 11:02:05Z jdominguez $
 * $Log$
 * Revision 1.3  2007-05-10 09:46:45  jaume
 * Refactored legend interface names
 *
 * Revision 1.2  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.1.2.3  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2007/02/09 11:00:03  jaume
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.gvsig.layer.Layer;
import org.gvsig.legend.Legend;

public abstract class AbstractParentPanel implements ILegendPanel {
	@Override
	public Legend getLegend() {
		return null;
	}

	@Override
	public void setData(Layer layer, Legend legend) {
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public Class<? extends ILegendPanel> getParentClass() {
		return null;
	}

	@Override
	public JPanel getPanel() {
		return null;
	}

	@Override
	public Class getLegendClass() {
		return null;
	}
}
