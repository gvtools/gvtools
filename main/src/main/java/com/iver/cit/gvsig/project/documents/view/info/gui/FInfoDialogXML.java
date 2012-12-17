/*
 * Created on 13-sep-2004
 *
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
package com.iver.cit.gvsig.project.documents.view.info.gui;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.utiles.xmlViewer.XMLViewer;

/**
 * Diálogo utilizado para mostrar la infromación.
 * 
 * @author fernando To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FInfoDialogXML extends XMLViewer implements IWindow {

	public FInfoDialogXML() {
		super();
		setNamesColumn(new String[] { PluginServices.getText(this, "Atributo"),
				PluginServices.getText(this, "Valor") });
		setSize(500, 375);
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.View#getViewInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODELESSDIALOG
				| WindowInfo.RESIZABLE | WindowInfo.PALETTE);
		m_viewinfo.setWidth(getWidth() + 8);
		m_viewinfo.setHeight(getHeight());
		m_viewinfo.setTitle(PluginServices.getText(this,
				"Identificar_Resultados"));

		return m_viewinfo;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.SingletonWindow#getWindowModel()
	 */
	public Object getViewModel() {
		return "FInfoDialogXML";
	}

	public Object getWindowProfile() {
		return WindowInfo.PROPERTIES_PROFILE;
	}
}
