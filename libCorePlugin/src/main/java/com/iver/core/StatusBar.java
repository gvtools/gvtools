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
package com.iver.core;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.MessageEvent;
import com.iver.andami.messages.NotificationListener;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;

/**
 * Plugin que escucha las notificaciones que recive la aplicaci�n y las muestra
 * en la barra de estado
 */
public class StatusBar extends Extension implements NotificationListener {
	private int i;
	private int pr;

	/**
	 * @see com.iver.mdiApp.IExtension#initialize()
	 */
	public void initialize() {
		NotificationManager.addNotificationListener(this);
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#errorEvent(java.lang.String)
	 */
	public void errorEvent(MessageEvent e) {
		PluginServices.getMainFrame().getStatusBar()
				.setErrorText(e.getMessages()[0]);
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#warningEvent(java.lang.String)
	 */
	public void warningEvent(MessageEvent e) {
		PluginServices.getMainFrame().getStatusBar()
				.setWarningText(e.getMessages()[0]);
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#infoEvent(java.lang.String)
	 */
	public void infoEvent(MessageEvent e) {
		PluginServices.getMainFrame().getStatusBar()
				.setInfoText(e.getMessages()[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#execute(java.lang.String)
	 */
	public void execute(String actionCommand) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isEnabled()
	 */
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isVisible()
	 */
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

}
