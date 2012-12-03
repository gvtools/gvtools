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

import org.apache.log4j.Logger;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;

/**
 * Extensi�n que registra un frame en la aplicaci�n que recibe los eventos de la
 * consola de la aplicaci�n y los muestra en el frame
 */
public class Consola extends Extension {

	private static Logger logger = Logger.getLogger(Consola.class.getName());

	static PluginServices ps;
	public static ConsolaFrame consolaFrame;
	public static NotificationDialog notificationDialog;

	/**
	 * @see com.iver.mdiApp.IExtension#initialize()
	 */
	public void initialize() {
		ps = PluginServices.getPluginServices(this);
		consolaFrame = new ConsolaFrame();
		notificationDialog = new NotificationDialog();
		NotificationManager.addNotificationListener(consolaFrame);
		NotificationManager.addNotificationListener(notificationDialog);

		PluginServices.getIconTheme().registerDefault(
				"application-console",
				this.getClass().getClassLoader()
						.getResource("images/console.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#execute(java.lang.String)
	 */
	public void execute(String actionCommand) {
		consolaFrame.setSize(400, 325);
		consolaFrame.setVisible(true);
		PluginServices.getMDIManager().addWindow(consolaFrame);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isVisible()
	 */
	public boolean isVisible() {
		return true;
	}
}

// [eiel-gestion-excepciones]
