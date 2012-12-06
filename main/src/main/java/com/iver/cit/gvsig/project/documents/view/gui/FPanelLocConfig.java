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
package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gvsig.NotAvailableLabel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * @author FJP
 * 
 *         Dialog to config the locator
 */
public class FPanelLocConfig extends NotAvailableLabel implements
		ActionListener, IWindow {

	private static final long serialVersionUID = -2271914332135260143L;
	private MapOverview mapCtrl;
	private WindowInfo m_viewinfo = null;

	public FPanelLocConfig(MapOverview mc) {
		super();
	}

	public javax.swing.JList getJList() {
		throw new RuntimeException();
	}

	public void actionPerformed(ActionEvent e) {
		throw new RuntimeException();
	}

	public WindowInfo getWindowInfo() {
		if (m_viewinfo == null) {
			m_viewinfo = new WindowInfo(WindowInfo.MODELESSDIALOG);
			m_viewinfo.setTitle(PluginServices.getText(this,
					"Configurar_localizador"));
			m_viewinfo.setWidth(this.getWidth() + 8);
			m_viewinfo.setHeight(this.getHeight() + 8);
		}
		return m_viewinfo;
	}

	public void viewActivated() {
	}

	public MapOverview getMapCtrl() {
		return mapCtrl;
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

}
