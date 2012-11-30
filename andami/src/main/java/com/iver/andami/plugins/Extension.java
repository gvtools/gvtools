package com.iver.andami.plugins;

import com.iver.andami.plugins.status.IExtensionStatus;

/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2004-2007 IVER T.I. and Generalitat Valenciana.
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
/* CVS MESSAGES:
 *
 * $Id: Extension.java 13713 2007-09-17 06:33:34Z caballero $
 * $Log$
 * Revision 1.12  2007-09-17 06:33:34  caballero
 * unsaveddata
 *
 * Revision 1.11  2007/07/19 12:01:06  cesar
 * Add support for the new Andami termination process (and the unsaved data dialog)
 *
 * Revision 1.10  2007/05/31 07:42:17  cesar
 * Add ExclusiveUIExtension interface and clean the IExtension interface; update Extension, ExtensionDecorator and Launcher to reflect this change; add missing comments, translate existing ones to english
 *
 * Revision 1.9  2007/05/31 07:00:47  cesar
 * Add missing comments, translate existing ones to english
 *
 * Revision 1.8  2006/11/27 11:33:05  jmvivo
 * Soporte para que una clase tenga el control de la visibilidad y estado de las acciones del resto de extensiones.
 *
 * Revision 1.7  2006/09/15 10:39:18  caballero
 * multisplah y postInitialize
 *
 * Revision 1.6  2006/07/31 18:22:13  cesar
 * Rename finalize method to terminate method
 *
 * Revision 1.5  2006/05/02 15:53:06  jorpiell
 * Se ha cambiado la interfaz Extension por dos clases: una interfaz (IExtension) y una clase abstract(Extension). A partir de ahora todas las extensiones deben heredar de Extension
 *
 *
 */
/**
 * Extensions are the way in which plugins extend Andami. Extensions can add
 * controls to user interface (GUI) and execute some code when controls are
 * activated. Every class implementing {@link IExtension} is an extension, but
 * directly implementing that interface is discouraged. The preferred way to
 * create an extension is extending this absctract class.
 * 
 * @see IExtension
 * 
 * @author Jorge Piera Llodrá (piera_jor@gva.es)
 */
public abstract class Extension implements IExtension {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#terminate()
	 */
	public void terminate() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#postInitialize()
	 */
	public void postInitialize() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#getStatus()
	 */
	public IExtensionStatus getStatus() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#getStatus(IExtension extension)
	 */
	public IExtensionStatus getStatus(IExtension extension) {
		return extension.getStatus();
	}
}
