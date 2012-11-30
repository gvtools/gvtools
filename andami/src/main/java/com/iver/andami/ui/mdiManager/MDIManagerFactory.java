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
package com.iver.andami.ui.mdiManager;

import org.apache.log4j.Logger;

import com.iver.andami.messages.Messages;
import com.iver.andami.plugins.PluginClassLoader;
import com.iver.andami.plugins.config.generate.SkinExtension;

/**
 * Se encarga de la creación de la clase Skin.
 * 
 * @author Fernando González Cortés
 */
public class MDIManagerFactory {
	private static SkinExtension skinExtension = null;
	private static PluginClassLoader loader = null;
	private static MDIManager mdiManager = null;
	private static Logger logger = Logger.getLogger(MDIManagerFactory.class
			.getName());

	/**
	 * DOCUMENT ME!
	 * 
	 * @param extension
	 *            DOCUMENT ME!
	 * @param loader
	 *            DOCUMENT ME!
	 */
	public static void setSkinExtension(SkinExtension extension,
			PluginClassLoader loader) {
		MDIManagerFactory.loader = loader;
		MDIManagerFactory.skinExtension = extension;
	}

	/**
	 * Obtiene una referencia al Skin cargado. El skin cargado es un singleton,
	 * se devuelve la misma instancia a todas las invocaciones de éste método
	 * 
	 * @return referencia al skin cargado
	 */
	public static MDIManager createManager() {
		if (mdiManager != null)
			return mdiManager;

		if (skinExtension == null) {
			throw new NoSkinExtensionException(
					Messages.getString("MDIManagerFactory.No_skin_extension_in_the_plugins"));
		} else {
			try {
				mdiManager = (MDIManager) loader.loadClass(
						skinExtension.getClassName()).newInstance();
				return mdiManager;
			} catch (InstantiationException e) {
				logger.error(Messages
						.getString("Launcher.Error_instanciando_la_extension"),
						e);

				// return new NewSkin();
			} catch (IllegalAccessException e) {
				logger.error(Messages
						.getString("Launcher.Error_instanciando_la_extension"),
						e);

				// return new NewSkin();
			} catch (ClassNotFoundException e) {
				logger.error(
						Messages.getString("Launcher.No_se_encontro_la_clase_de_la_extension"),
						e);

				// return new NewSkin();
			}
		}

		return null;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return Returns the skinExtension.
	 */
	public static SkinExtension getSkinExtension() {
		return skinExtension;
	}
}
