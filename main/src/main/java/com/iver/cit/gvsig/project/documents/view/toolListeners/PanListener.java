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
package com.iver.cit.gvsig.project.documents.view.toolListeners;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.PanListenerImpl;
import com.iver.cit.gvsig.fmap.tools.Events.MoveEvent;

/**
 * <p>
 * Inherits {@link PanListener PanListener} enabling/disabling special controls
 * for managing the data.
 * </p>
 * 
 * @see PanListenerImpl
 * 
 * @author Vicente Caballero Navarro
 */
public class PanListener extends PanListenerImpl {
	/**
	 * <p>
	 * Creates a new <code>PanListener</code> object.
	 * </p>
	 * 
	 * @param mapCtrl
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public PanListener(MapControl mapCtrl) {
		super(mapCtrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.PanListenerImpl#move(com.iver.cit.gvsig
	 * .fmap.tools.Events.MoveEvent)
	 */
	public void move(MoveEvent event) {
		super.move(event);
		PluginServices.getMainFrame().enableControls();
	}
}
