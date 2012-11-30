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
package com.iver.andami.plugins;

import com.iver.andami.plugins.status.IExtensionStatus;

/**
 * This class extends the functionality of Extension class to let the programmer
 * set an extension visible or not on-the-fly.
 * 
 * @autor Jaume Dominguez Faus - jaume.dominguez@iver.es
 */
public class ExtensionDecorator implements HiddableExtension {
	public static final int INACTIVE = 0;
	public static final int ALWAYS_VISIBLE = 1;
	public static final int ALWAYS_INVISIBLE = 2;
	int alwaysVisible;
	IExtension extension;

	public ExtensionDecorator(IExtension e, int visibilityControl) {
		setExtension(e);
		setVisibility(visibilityControl);
	}

	public void setExtension(IExtension e) {
		this.extension = e;
	}

	public void setVisibility(int state) {
		this.alwaysVisible = state;
	}

	public int getVisibility() {
		return alwaysVisible;
	}

	public IExtension getExtension() {
		return extension;
	}

	public void initialize() {
		extension.initialize();
	}

	public void terminate() {
		// TODO
	}

	public void execute(String actionCommand) {
		extension.execute(actionCommand);
	}

	public boolean isEnabled() {
		return extension.isEnabled();
	}

	public boolean isVisible() {
		if (alwaysVisible == INACTIVE)
			return extension.isVisible();
		else if (alwaysVisible == ALWAYS_VISIBLE)
			return true;
		else
			return false;
	}

	public void postInitialize() {
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#getStatus()
	 */
	public IExtensionStatus getStatus() {
		return extension.getStatus();
	}

	public IExtensionStatus getStatus(IExtension extension) {
		return this.extension.getStatus(extension);
	}
}
