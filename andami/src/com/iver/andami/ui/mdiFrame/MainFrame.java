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
package com.iver.andami.ui.mdiFrame;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.gvsig.gui.beans.controls.IControl;

import com.iver.andami.plugins.PluginClassLoader;
import com.iver.andami.plugins.config.generate.Label;
import com.iver.andami.plugins.config.generate.Menu;

/**
 * This interface represents the main application's window. It allow to access
 * the menus, the tool bars and the status bar.
 */
public interface MainFrame {
	/**
	 * Adds the provided menu to the menu bar.
	 * 
	 * @param menu
	 *            A Menu object containing the menu definition
	 * @param listener
	 *            Object which receives the menu events
	 * @param loader
	 *            ClassLoader of the plug-in that installs this menu
	 */
	public void addMenu(Menu menu, ActionListener listener,
			PluginClassLoader loader);

	/**
	 * Changes the menu name, and thus also its location, as the name determines
	 * the location of the menu.
	 * 
	 * @param menu
	 *            An array of Strings which represents the full menu path, for
	 *            example, {"Vista", "export", "imagen"} is a reference to the
	 *            menu "Vista/export/imagen" (that is, the menu Image within the
	 *            submenu View-Export). Menu names are translation keys,
	 *            "Vista", "export", etc will be translated to the suitable
	 *            language when they are displayed.
	 * @param newName
	 *            New menu's name, in the syntax "Vista/export/symbols". Each
	 *            part of the name is a translation key.
	 * @param loader
	 *            ClassLoader of the plug-in that added the menu
	 * 
	 * @throws NoSuchMenuException
	 *             If there is no menu matching the provided menu path
	 */
	public void changeMenuName(String[] menu, String newName,
			PluginClassLoader loader) throws NoSuchMenuException;

	/**
	 * Deletes the provided menu, if found.
	 * 
	 * @param menu
	 *            The menu to delete from the menu bar
	 */
	public void removeMenu(Menu menu);

	/**
	 * It checks whether each extension is enabled and visible, in order to
	 * enable/disable and show/hide their associated controls.
	 */
	public void enableControls();

	/**
	 * Gets the status bar, the bar located in the bottom part of the main
	 * window. It can be used to show messages, show progress bars, or change
	 * the status.
	 * 
	 * @return The main application's status bar.
	 */
	public NewStatusBar getStatusBar();

	/**
	 * Sets the main window title.
	 * 
	 * @param titulo
	 *            The title to be set in the main window
	 */
	public void setTitle(String titulo);

	/**
	 * Gets a previously added JComponent by name (including tool bars, buttons
	 * from tool bars, status bar controls and menus. For example you can use it
	 * if you need to obtain an status bar control or a JToolBar to add some
	 * customized component
	 * 
	 * @param name
	 * @return the JComponent or null if none has been found
	 */
	public JComponent getComponentByName(String name);

	/**
	 * Sets the tool associated to the provided actionComand as the selected
	 * tool for the currently selected Andami window.
	 */
	public void setSelectedTool(String actionCommand);

	/**
	 * Gets an array containing all the toolbars.
	 * 
	 * @return An array containing all the toolbars.
	 */
	public SelectableToolBar[] getToolbars();

	/**
	 * Gets wheter or not this toolbar should be shown by Andami. Note that this
	 * does not reflect the actual visibility of the toolbar, because it also
	 * depends on other conditions (the toolbar should contain at lest a
	 * currently visible button).
	 * 
	 * @param name
	 *            The toolbar's name
	 * @return
	 */
	public boolean getToolbarVisibility(String name);

	/**
	 * Sets wheter or not this toolbar should be shown by Andami. This is useful
	 * if to hide some toolbars when they are not going to be used. If it's
	 * false, the toolbar will be hidden even if its associated extensions are
	 * visible. Note that setting visibility to true does not automatically show
	 * the toolbar, because it also depends on other conditions (the toolbar
	 * should contain at lest a currently visible button). However, it allows
	 * the toolbar to be visible when necessary conditions are fulfilled.
	 * 
	 * @param name
	 *            The toolbar's name.
	 * @param visibility
	 * @return
	 */
	public boolean setToolbarVisibility(String name, boolean visibility);

	/**
	 * Gets the menu entry corresponding the provided menu path.
	 * 
	 * @param menuPath
	 *            The menu path to the menu entry that we want to retrieve. For
	 *            example, if we want to retrieve the menu entry corresponding
	 *            to the XML menu "Layer/Export/Export_to_PDF" we will provide
	 *            an array containing ["Layer", "Export", "Export_to_PDF"].
	 * 
	 * @return The menu entry corresponding the provided menu path. Note that
	 *         the menu entry may be an instance of
	 *         <code>javax.swing.JMenuItem</code>,
	 *         <code>javax.swing.JMenu</code> or
	 *         <code>com.iver.andami.ui.mdiFrame.JMenuItem</code>.
	 */
	public javax.swing.JMenuItem getMenuEntry(String[] menuPath);

	/**
	 * Adds a control to the status bar and associate it with the provided
	 * extension. The control will be enabled and visible when the extension is
	 * enabled and visible.
	 * 
	 * @param extensionClass
	 *            Extension which will determine whether the control is enabled
	 *            and visible.
	 * @param control
	 *            The control to add.
	 */
	public void addStatusBarControl(Class<?> extensionClass, IControl control);

	/**
	 * Removes the providedcontrol from the status bar.
	 * 
	 * @param name
	 *            The name of the control to remove
	 */
	public void removeStatusBarControl(String name);

	/**
	 * Sets the provided label-set as the labels associated with the provided
	 * class. The labels will be visible in the status bar if the currently
	 * selected Andami window is an instance of the provided class.
	 * 
	 * @param clase
	 *            The class which will be associated to the label-set. The
	 *            labels will be visible if the currently selected Andami window
	 *            is an instance of this class.
	 * 
	 * @param label
	 *            An array of Labels. Each label has an ID which will be used to
	 *            write text on them.
	 */
	public void setStatusBarLabels(Class<?> clase, Label[] label);

	/**
	 * Removes the labels associated with the provided class.
	 * 
	 * @param clase
	 *            The class whose associated labels are to be removed.
	 */
	public void removeStatusBarLabels(Class<?> clase);

}
