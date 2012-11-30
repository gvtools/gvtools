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
package com.iver.andami.ui.mdiManager;

import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.iver.andami.ui.mdiFrame.MDIFrame;

/**
 * <p>
 * This interface acts as window manager. It is the place to create new windows,
 * close existing ones or modify their properties (size, position, etc).
 * </p>
 * <p>
 * Any class extending from JPanel and implementing the {@link IWindow}
 * interface may become an Andami window. Andami will create a real window
 * (normally a JInternalFrame) and put the JPanel inside the real window. In
 * order to differentiate the contents (panels extending JPanel and implementing
 * IWindow) from the real window (normally JInternalFrames or JDialogs), we will
 * use <i>window</i> to refer to the contents and <i>frame</i> to refer to the
 * real window.
 * </p>
 * <p>
 * This class is implemented by the Andami Skin (currently libCorePlugin), which
 * will decide the final implementation of frames. A different frame
 * implementation could be used by switching the Skin.
 * </p>
 * 
 * @see IWindow
 * @see WindowInfo
 * @see SingletonWindow
 * @see com.iver.core.mdiManager.NewSkin
 * 
 * @author Fernando González Cortés
 */
public interface MDIManager {
	/**
	 * Initializes the MDIFrame. It must be called before starting to use it. It
	 * receives the application's main frame (MDIFrame) as parameter.
	 * 
	 * @param f
	 *            Application's main frame.
	 */
	public void init(MDIFrame f);

	/**
	 * <p>
	 * Creates a new frame with the provided contents, and shows this new
	 * window. The new frame's properties are set according to the WindowInfo
	 * object from IWindow's <code>getWindowInfo()</code> method. The new frame
	 * is disposed when closed.
	 * </p>
	 * <p>
	 * If the provided IWindow also implements SingletonWindow, and another
	 * SingletonWindow already exists and uses the same model, this later window
	 * will be sent to the foreground and no new window will be created.
	 * </p>
	 * 
	 * @param p
	 *            Panel with the contents of the new window.
	 * 
	 * @return Returns the added IWindow, or in case it is a SingletonWindow and
	 *         there is another SingletonWindow with the same model that it is
	 *         already shown, returns this later SingletonWindow.
	 */
	public IWindow addWindow(IWindow p)
			throws SingletonDialogAlreadyShownException;

	/**
	 * <p>
	 * Creates a new frame with the provided contents, and shows this new
	 * window. The new frame will be centered, regardless the position specified
	 * in the WindowInfo object from IWindow's <code>getWindowInfo()</code>
	 * method. The new frame is disposed when closed.
	 * </p>
	 * <p>
	 * If the provided IWindow also implements SingletonWindow, and another
	 * SingletonWindow already exists and uses the same model, this later window
	 * will be sent to the foreground and no new window will be created.
	 * </p>
	 * 
	 * @param p
	 *            Panel with the contents of the new window.
	 * 
	 * @return Returns the added IWindow, or in case it is a SingletonWindow and
	 *         there is another SingletonWindow with the same model that it is
	 *         already shown, returns this later SingletonWindow.
	 * 
	 * @author Pablo Piqueras Bartolomé
	 */
	public IWindow addCentredWindow(IWindow p)
			throws SingletonDialogAlreadyShownException;

	/**
	 * <p>
	 * Returns the currently active window, excluding the modal windows and the
	 * PALETTE windows. If the currently active window is modal or PALETTE type,
	 * the previous non-modal and non-PALETTE active window is returned.
	 * </p>
	 * <p>
	 * Modal windows and PALETTE windows are considered to be auxiliary windows,
	 * that is the reason why they are not returned.
	 * </p>
	 * 
	 * @return A reference to the active window, or null if there is no active
	 *         window
	 */
	public IWindow getActiveWindow();

	/**
	 * Useful to add a menuBar to a window
	 * 
	 * @param w
	 * @param menuBar
	 */
	public void addJMenuBarToWindow(IWindow w, JMenuBar menuBar);

	/**
	 * <p>
	 * Returns the currently focused window, excluding the modal windows. If the
	 * currently focused window is modal, the previous non-modal focused window
	 * is returned.
	 * </p>
	 * 
	 * @return A reference to the focused window, or null if there is no focused
	 *         window
	 */
	public IWindow getFocusWindow();

	/**
	 * Gets all the open windows. Minimized and maximized windows are included.
	 * The application's main frame is excluded; it can be accessed using
	 * <code>PluginServices.getMainFrame()</code>.
	 * 
	 * @return An IWindow array containing all the open windows.
	 */
	public IWindow[] getAllWindows();

	/**
	 * Gets all the open windows (as {@link #getAllWindows()}), but in this
	 * method the windows are returned in the same deepness order that they have
	 * in the application.
	 * 
	 * @return An ordered array containing all the panels in the application.
	 *         The first element of the array is the topmost (foreground) window
	 *         in the application. The last element is the bottom (background)
	 *         window.
	 */
	public IWindow[] getOrderedWindows();

	/**
	 * Close the SingletonWindow whose class and model are provided as
	 * parameters.
	 * 
	 * @param viewClass
	 *            Class of the window which is to be closed
	 * @param model
	 *            Model of the window which is to be closed
	 * 
	 * @return true if there is an open window whose class and model match the
	 *         provided parameteres, false otherwise.
	 */
	public boolean closeSingletonWindow(Class<?> viewClass, Object model);

	/**
	 * Close the SingletonWindow whose model is provided as parameter.
	 * 
	 * @param model
	 *            Model of the window which is to be closed
	 * 
	 * @return true if there is an open window whose model matchs the provided
	 *         one, false otherwise.
	 */
	public boolean closeSingletonWindow(Object model);

	/**
	 * Close the provided window.
	 * 
	 * @param p
	 *            window to be closed
	 */
	public void closeWindow(IWindow p);

	/**
	 * Close all the currently open windows
	 */
	public void closeAllWindows();

	/**
	 * Gets the WindowInfo object associated with the provided window.
	 * 
	 * @param v
	 *            window whose information is to be retrieved
	 * 
	 * @return WindowInfo The WindowInfo object containing the information about
	 *         the provided window
	 * 
	 * @see WindowInfo
	 */
	public WindowInfo getWindowInfo(IWindow v);

	/**
	 * Shows the wait cursor and blocks all the events from main window until
	 * {@link #restoreCursor()} is called.
	 */
	public void setWaitCursor();

	/**
	 * Sets the normal cursor and unblocks events from main window.
	 * 
	 * @see #setWaitCursor()
	 */
	public void restoreCursor();

	/**
	 * Maximizes or restores the provided window
	 * 
	 * @param v
	 *            The window to be maximized or restored
	 * @param bMaximum
	 *            If true, the window will be maximized, if false, it will be
	 *            restored
	 * @throws PropertyVetoException
	 */
	public void setMaximum(IWindow v, boolean bMaximum)
			throws PropertyVetoException;

	/**
	 * Updates the window properties (size, location, etc) according to the
	 * provided WindowInfo object.
	 * 
	 * @param v
	 *            The window whose properties are to be changed
	 * @param vi
	 *            The WindowInfo object containing the new properties to be set
	 */
	public void changeWindowInfo(IWindow v, WindowInfo vi);

	/**
	 * Forces a window to be repainted. Normally, this is not necessary, as
	 * windows are refreshed when necessary.
	 * 
	 * @param win
	 *            The window to be refreshed.
	 */
	public void refresh(IWindow win);

	/**
	 * Sets the provided image as background image in the main window. The image
	 * will be centered, set in mosaic or expanded to fill the full window,
	 * depending on the <code>typeDesktop</code> argument.
	 * 
	 * @param image
	 *            The image to be set as background image
	 * @param typeDesktop
	 *            Decides whether the image should be centered, set in mosaic or
	 *            expanded. Accepted values are: Theme.CENTERED, Theme.MOSAIC
	 *            and Theme.EXPAND.
	 */
	public void setBackgroundImage(ImageIcon image, String typeDesktop);
}
