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
package com.iver.andami.plugins;

import com.iver.andami.plugins.status.IExtensionStatus;

/**
 * <p>
 * Extensions are the way in which plugins extend Andami. Every class
 * implementing this interface is an extension (although this class should not
 * be directly implemented, extending the {@link Extension} abstract class is
 * the preferred way). During startup, Andami creates an instance from each
 * extension. Extensions are able to add controls (tools, toolbars and menus) to
 * the user interface (GUI); when these controls are clicked or modified, Andami
 * will call the <code>execute()</code> method from the associated extension.
 * Extensions will also control whether their associated controls are enabled or
 * not, and whether they are visible or hidden.
 * </p>
 * <p>
 * Besides implementing this interface, extensions are required to have an empty
 * constructor, because it will be invoked to create the extension's instance.
 * </p>
 * <p>
 * On correctly installed plugins, an instance of every plugin's extension will
 * be created. Then, the <code>initialize()</code> method will be executed for
 * each extension.
 * </p>
 * <p>
 * Each time the active window is switched and every time the execute method is
 * called, Andami checks the <code>isEnabled()</code> and
 * <code>isVisible()</code> for each extension, to decide whether they will be
 * enabled and visible.
 * </p>
 * <p>
 * The <code>execute()</code> method will be called from the GUI thread, so it
 * will block the GUI until it finishes. Therefore, execution must be fast. If
 * long process must be started from the <code>execute</code> method, a new
 * thread must be created.
 * </p>
 * <p>
 * Normally, extensions should not directly implement this interface, but they
 * should extend the <code>Extension</code> abstract class.
 * </p>
 * 
 * @see Extension
 */
public interface IExtension {
	/**
	 * Extension's initialization code should be here. This method is called for
	 * all the extensions during Andami's initialization.
	 */
	public void initialize();

	/**
	 * Extension's post-initialization code should be here. This method is
	 * called for all the extensions during Andami's initialization, after
	 * <code>initialize()</code> has been called for ALL the extensions.
	 */
	public void postInitialize();

	/**
	 * Extension's termination code should be here. This method is called for
	 * all the extensions during Andami's termination.
	 */
	public void terminate();

	/**
	 * This method is executed when the user clicks on any of the controls
	 * associated with this extension (menus, tools, etc).
	 * 
	 * @param actionCommand
	 *            An String specifying the action to execute. This is useful
	 *            when there are different controls associated with the same
	 *            extension.
	 */
	public void execute(String actionCommand);

	/**
	 * This method is invoked by Andami to check whether the extension (and its
	 * associated controls) is enabled or disabled. Disabled controls (menus and
	 * buttons) are shown in light grey colour, and it is not possible to click
	 * on them.
	 * 
	 * @return true if the extension should be enabled, false otherwise
	 */
	public boolean isEnabled();

	/**
	 * This method is invoked by Andami to check whether the extension (and its
	 * associated controls) is visible or hidden.
	 * 
	 * @return true if the extension should be visible, false otherwise
	 */
	public boolean isVisible();

	/**
	 * Gets the status of the extension, which may be queried to check if the
	 * extension has some unsaved data or some associated background tasks.
	 * 
	 * @see IExtensionStatus
	 * @return An IExtensionStatus object, containing the status of this
	 *         extension.
	 */
	public IExtensionStatus getStatus();

	public IExtensionStatus getStatus(IExtension extension);
}
