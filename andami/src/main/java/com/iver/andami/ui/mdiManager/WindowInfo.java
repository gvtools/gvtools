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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

import org.exolab.castor.xml.XMLException;

import com.iver.andami.PluginServices;
import com.iver.utiles.XMLEntity;

/**
 * This class represents the state of the associated window. The
 * <code>set</code> methods (<code>setX</code>, <code>setY</code>,
 * <code>setHeight</code>, etc) are immediately reflected on the window.
 * 
 * The <code>update</code> methods doesn't update the window, because they are
 * used to update the WindowInfo object when the associated window was modified
 * by user interaction.
 * 
 * @author Fernando González Cortés
 */
public class WindowInfo {
	/** Specifies that the window is resizable */
	public static final int RESIZABLE = 1;

	/** Specifies that the window is maximizable */
	public static final int MAXIMIZABLE = 2;

	/** Specifies that the window is iconifiable */
	public static final int ICONIFIABLE = 4;

	/** Specifies that the window is modal */
	public static final int MODALDIALOG = 8;
	/**
	 * Specifies that the window is modeless (it's on the top but doesn't block
	 * any other window)
	 */
	public static final int MODELESSDIALOG = 16;
	/** Specifies that the window may be docked inside another window */
	public static final int PALETTE = 32;
	/** Specifies that the window may be closed */
	public static final int NOTCLOSABLE = 64;

	/** Specifies that this window has an editor profile */
	/** To be used for View Windows */
	public static final Integer EDITOR_PROFILE = new Integer(1);
	/** Specifies that this window has Tool profile */
	/** To be used for toolbox windows */
	public static final Integer TOOL_PROFILE = new Integer(2);
	/** Specifies that this window has a Project profile */
	/** To be used only in the project windows */
	public static final Integer PROJECT_PROFILE = new Integer(3);
	/** Specifies that this window has a Properties profile */
	/** To be used for general property panels */
	public static final Integer PROPERTIES_PROFILE = new Integer(4);
	/** Specifies that this window has an Dialog profile */
	public static final Integer DIALOG_PROFILE = new Integer(5);

	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	/** DOCUMENT ME! */
	private boolean resizable = false;

	/** DOCUMENT ME! */
	private boolean maximizable = false;

	/** DOCUMENT ME! */
	private boolean iconifiable = false;

	/** DOCUMENT ME! */
	private boolean modal = false;
	private boolean modeless = false;
	private boolean notclosable = false;

	/**
	 * Do we want to persist the geometry of this window in the project file?
	 */
	private boolean persistWindow = true;

	/**
	 * Se usa para poner una ventana de tipo paleta, por encima de las demás.
	 * Equivale a poner usar JDesktopPane.PALETTE_LAYER
	 */
	private boolean palette = false;

	private String additionalInfo = null;

	/** These properties store the dimension and position of the frame */
	private int width = -1;
	private int height = -1;
	private int x = -1;
	private int y = -1;
	/**
	 * These properties store the position and dimension of the frame when it is
	 * not maximized (so that it can be restored to its original size). They are
	 * equal to the not-normal properties when the frame is not maximized, and
	 * different when the frame is maximized.
	 */
	private int normalX = 0;
	private int normalY = 0;
	private int normalHeight = -1;
	private int normalWidth = -1;

	/** The minimum allowed size for this window. */
	private Dimension minSize = null;

	/* Whether the window is maximized */
	private boolean isMaximized = false;
	private boolean visible = true;
	/* Whether the window is closed */
	private boolean isClosed = false;

	/** DOCUMENT ME! */
	private String title;

	private int id;

	/**
	 * ActionCommand del tool seleccionado. Lo usamos para activar el tool que
	 * estaba seleccionado en la vista, cuando volvemos a ella.
	 */
	private HashMap<String, String> selectedTools = null;
	// this should be the same value defined at plugin-config.xsd
	private String defaultGroup = "unico";

	/**
	 * Adds a PropertyChangeListener to the listener list. The listener will be
	 * notified about changes in this object.
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a PropertyChangeListener from the listener list. The listener will
	 * not be notified anymore about changes in this object.
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * Creates a new WindowInfo object with the provided properties. Valid
	 * properties include:
	 * <ul>
	 * <li>WindowInfo.MODALDIALOG</li>
	 * <li>WindowInfo.MODELESSDIALOG</li>
	 * <li>WindowInfo.PALETTE</li>
	 * <li>WindowInfo.ICONIFIABLE</li>
	 * <li>WindowInfo.RESIZABLE</li>
	 * <li>WindowInfo.MAXIMIZABLE</li>
	 * 
	 * <p>
	 * Properties can be set together by using the binary OR operator
	 * "<b>|</b>". For example:
	 * </p>
	 * 
	 * <p>
	 * <code>WindowInfo wi = new WindowInfo(WindowInfo.MODELESSDIALOG
	 *   |WindowInfo.ICONIFIABLE|WindowInfo.RESIZABLE).</code>
	 * </p>
	 * 
	 * <p>
	 * The WindowInfo.MODELESSDIALOG and WindowInfo.MODALDIALOG properties
	 * cannot be set at the same time
	 * </p>
	 * 
	 * @param code
	 *            Bit-or of the window properties.
	 * 
	 * @throws IllegalStateException
	 *             If incompatible properties are set together, for example, if
	 *             MODALDIALOG and MODELESSDIALGO are set together.
	 */
	public WindowInfo(int code) {
		resizable = (code % 2) > 0;
		code = code / 2;
		maximizable = (code % 2) > 0;
		code = code / 2;
		iconifiable = (code % 2) > 0;
		code = code / 2;
		modal = (code % 2) > 0;
		code = code / 2;
		modeless = (code % 2) > 0;
		code = code / 2;
		palette = (code % 2) > 0;
		code = code / 2;
		notclosable = (code % 2) > 0;

		if (modal && modeless) {
			throw new IllegalStateException("modal && modeless");
		}
	}

	/**
	 * Creates a new WindowInfo object with the default properties:
	 * <ul>
	 * <li>not resizable</li>
	 * <li>not maximizable</li>
	 * <li>not iconifiable</li>
	 * <li>not modal</li>
	 * <li>not modeless</li>
	 * <li>not palette</li> </uil>
	 * 
	 */
	public WindowInfo() {

	}

	/**
	 * Returns the current x coordinate of the window's origin (left-upper
	 * corner of the window).
	 * 
	 * @return Returns the value (in pixels) of the x coordinate of the window's
	 *         origin.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate of the window's origin (left-upper corner of the
	 * window).
	 * 
	 * @param x
	 *            The value (in pixels) of the x coordinate to set.
	 */
	public void setX(int x) {
		support.firePropertyChange("x", this.x, x);
		this.x = x;
		if (!isMaximized)
			this.normalX = x;
	}

	/**
	 * Updates the value of the x coordinate for this WindowInfo object. It
	 * doesn't get reflected on the window (use setX for that).
	 * 
	 * @param x
	 *            The value (in pixels) of the x coordinate
	 */
	public void updateX(int x) {
		this.x = x;
		if (!isMaximized)
			this.normalX = x;
	}

	/**
	 * Gets the value of the y coordinate for the origin (left-upper corner of
	 * the window) of the associated window.
	 * 
	 * @return Returns the y coordinate (in pixels).
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the value of the y coordinate for the origin (left-upper corner of
	 * the window) of the associated window.
	 * 
	 * @param y
	 *            The value (in pixels) of the y coordinate
	 */
	public void setY(int y) {
		support.firePropertyChange("y", this.y, y);
		this.y = y;
		if (!isMaximized)
			this.normalY = y;
	}

	/**
	 * Updates the value of the y coordinate for this WindowInfo object. It
	 * doesn't get reflected on the window (use setY for that).
	 * 
	 * @param y
	 *            The value (in pixels) of the y coordinate
	 */
	public void updateY(int y) {
		this.y = y;
		if (!isMaximized)
			this.normalY = y;
	}

	/**
	 * Determines whether the associated window is closable or not
	 * 
	 * @return
	 */
	public boolean isNotClosable() {
		return notclosable;
	}

	/**
	 * Determines whether the associated window is iconifiable or not
	 * 
	 * @return
	 */
	public boolean isIconifiable() {
		return iconifiable;
	}

	/**
	 * Determines whether the associated window is maximizable or not
	 * 
	 * @return
	 */
	public boolean isMaximizable() {
		return maximizable;
	}

	/**
	 * Determines whether the associated window is resizable or not
	 * 
	 * @return
	 */
	public boolean isResizable() {
		return resizable;
	}

	/**
	 * Determines whether the associated window is modal or not
	 * 
	 * @return
	 */
	public boolean isModal() {
		return modal;
	}

	/**
	 * Gets the window height.
	 * 
	 * @return The window height (in pixels).
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the window width.
	 * 
	 * @return The window width (in pixels).
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the window height.
	 * 
	 * @param The
	 *            window height (in pixels)
	 */
	public void setHeight(int height) {
		if (height != -1)
			support.firePropertyChange("height", this.height, height);
		this.height = height;
		if (!isMaximized)
			this.normalHeight = height;
	}

	/**
	 * Updates the height property for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setHeight</code> for that).
	 * 
	 * @param height
	 *            The height value for this WindowInfo object
	 */
	public void updateHeight(int height) {
		this.height = height;
		if (!isMaximized)
			this.normalHeight = height;
	}

	/**
	 * Sets the width property for the associated Window.
	 * 
	 * @param w
	 *            The new width.
	 */
	public void setWidth(int w) {
		if (w != -1)
			support.firePropertyChange("width", this.width, w);
		width = w;
		if (!isMaximized)
			this.normalWidth = w;
	}

	/**
	 * Updates the width property for for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setWidth</code> for that).
	 * 
	 * @param height
	 *            The height value for this WindowInfo object
	 */
	public void updateWidth(int width) {
		this.width = width;
		if (!isMaximized)
			this.normalWidth = width;
	}

	/**
	 * Gets the title property
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title property.
	 * 
	 * @param title
	 *            The new title.
	 */
	public void setTitle(String title) {
		support.firePropertyChange("title", this.title, title);
		this.title = title;
	}

	/**
	 * Updates the title property for for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setTitle</code> for that).
	 * 
	 * @param title
	 *            The title value for this WindowInfo object
	 */
	public void updateTitle(String title) {
		this.title = title;
	}

	/**
	 * Determines whether the associated window is modeless (it is on the top
	 * but does not block any other window)
	 * 
	 * @return
	 */
	public boolean isModeless() {
		return modeless;
	}

	/**
	 * Determines whether the associated window is visible
	 * 
	 * @return true if the associated window is visible, false if it is hidden
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Determines whether the associated dockable window is currently in palette
	 * status or docked.
	 * 
	 * @return true if the window is currently in palette status, false if it is
	 *         docked in another window
	 */
	public boolean isPalette() {
		return palette;
	}

	/**
	 * Sets the window's ID.
	 * 
	 * @param id
	 *            An integer to identify the window. Different windows must have
	 *            different IDs.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the window ID.
	 * 
	 * @return id An integer to identify the window. Different windows must have
	 *         different IDs.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Updates all the properties in this object so that they match the
	 * properties of the provided <code>wi</code> object. The changes are not
	 * reflected on the window, just the WindowInfo object is updated.
	 * 
	 * @param vi
	 *            A WindowInfo object containing the new properties of the
	 *            window.
	 */
	public void setWindowInfo(WindowInfo vi) {
		this.resizable = vi.resizable;
		this.maximizable = vi.maximizable;
		this.isMaximized = vi.isMaximized;
		this.iconifiable = vi.iconifiable;
		this.additionalInfo = vi.additionalInfo;
		this.modal = vi.modal;
		this.modeless = vi.modeless;
		this.notclosable = vi.notclosable;
		if (vi.width != -1)
			this.width = vi.width;
		if (vi.height != -1)
			this.height = vi.height;
		this.x = vi.x;
		this.y = vi.y;
		this.visible = vi.visible;
		this.title = vi.title;
		this.id = vi.id;
		if (vi.normalHeight != -1)
			this.normalHeight = vi.normalHeight;
		if (vi.normalWidth != -1)
			this.normalWidth = vi.normalWidth;
		this.normalX = vi.normalX;
		this.normalY = vi.normalY;
		this.isClosed = vi.isClosed;
		this.persistWindow = vi.persistWindow;
	}

	/**
	 * Sets whether the window is in palette mode or is in docked mode. This
	 * method <b>does not</b> update the window status, it just updates the
	 * WindowInfo object. Use the IWindowTransform interface to actually change
	 * the window status.
	 * 
	 * @param b
	 */
	public void toPalette(boolean b) {
		this.palette = b;
	}

	/**
	 * Gets the list of selected tools for this window.
	 * 
	 * @return A HashMap containing pairs (group, actionCommand), which are the
	 *         selected tools for this window.
	 */
	public HashMap<String, String> getSelectedTools() {
		return selectedTools;
	}

	/**
	 * Use {@link setSelectedTools(HashMap selectedTool)}
	 * 
	 * @param selectedTool
	 * @deprecated
	 */
	public void setSelectedTool(String selectedTool) {
		if (selectedTool != null)
			selectedTools.put(defaultGroup, selectedTool);
	}

	/**
	 * Sets the list of selected tools for this window.
	 * 
	 * @param selectedTools
	 *            A HashMap containing pairs (group, actionCommand), which will
	 *            be set as the selected tools for this window.
	 */
	public void setSelectedTools(HashMap<String, String> selectedTools) {
		if (selectedTools != null)
			this.selectedTools = selectedTools;
	}

	/** Finds out whether a view is open (showing) or closed */
	public boolean isClosed() {
		return isClosed;
	}

	/** Specifies whether a view is open (showing) or closed */
	public void setClosed(boolean closed) {
		support.firePropertyChange("closed", this.isClosed, closed);
		this.isClosed = closed;
	}

	/**
	 * Updates the closed property for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setClosed</code> for that).
	 * 
	 * @param closed
	 *            The new closed property for this WindowInfo object
	 */
	public void updateClosed(boolean closed) {
		this.isClosed = closed;
	}

	/**
	 * Sets the normalX property for this WindowInfo object. This sets the X
	 * position that the window would have when it gets restored from the
	 * maximized state.
	 * 
	 * @param normalX
	 *            The new normalX property for this WindowInfo object
	 */
	public void setNormalX(int normalX) {
		if (!isMaximized()) {
			setX(normalX);
		} else {
			support.firePropertyChange("normalX", this.normalX, normalX);
			this.normalX = normalX;
		}
	}

	/**
	 * Updates the normalX property for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setNormalX</code> for that).
	 * 
	 * @param normalX
	 *            The new normalX property for this WindowInfo object
	 */
	public void updateNormalX(int normalX) {
		this.normalX = normalX;
		if (!isMaximized())
			x = normalX;
	}

	/**
	 * Sets the normalY property for this WindowInfo object. This sets the Y
	 * position that the window would have when it gets restored from the
	 * maximized state.
	 * 
	 * @param normalY
	 *            The new normalY property for this WindowInfo object
	 */
	public void setNormalY(int normalY) {
		if (!isMaximized()) {
			setY(normalY);
		} else {
			support.firePropertyChange("normalY", this.normalY, normalY);
			this.normalY = normalY;
		}
	}

	/**
	 * Updates the normalY property for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setNormalY</code> for that).
	 * 
	 * @param normalY
	 *            The new normalY property for this WindowInfo object
	 */
	public void updateNormalY(int normalY) {
		this.normalY = normalY;
		if (!isMaximized())
			y = normalY;
	}

	/**
	 * Sets the normalHeight property for this WindowInfo object. This sets the
	 * height position that the window would have when it gets restored from the
	 * maximized state.
	 * 
	 * @param normalY
	 *            The new normalHeight property for this WindowInfo object
	 */
	public void setNormalHeight(int normalHeight) {
		if (!isMaximized) {
			setHeight(normalHeight);
		} else {
			support.firePropertyChange("normalHeight", this.normalHeight,
					normalHeight);
			this.normalHeight = normalHeight;
		}
	}

	/**
	 * Updates the normalHeight property for this WindowInfo object. It doesn't
	 * get reflected on the window (use <code>setNormalHeight</code> for that).
	 * 
	 * @param normalHeight
	 *            The new normalHeight property for this WindowInfo object
	 */
	public void updateNormalHeight(int normalHeight) {
		this.normalHeight = normalHeight;
		if (!isMaximized) {
			this.height = normalHeight;
		}
	}

	/**
	 * Sets the normalWidth property for this WindowInfo object. This sets the
	 * width position that the window would have when it gets restored from the
	 * maximized state.
	 * 
	 * @param normalY
	 *            The new normalWidth property for this WindowInfo object
	 */
	public void setNormalWidth(int normalWidth) {
		if (!isMaximized()) {
			setWidth(normalWidth);
		} else {
			support.firePropertyChange("normalWidth", this.normalWidth,
					normalWidth);
			this.normalWidth = normalWidth;
		}
	}

	/**
	 * Sets the minimum allowed size for the associated window. If null is
	 * provided, the minimum size is disabled (and thus the window can be
	 * resized to any size).
	 * 
	 * @param minSize
	 *            The minimum allowed size for the associated window.
	 */
	public void setMinimumSize(Dimension minSize) {
		support.firePropertyChange("minimumSize", this.minSize, minSize);
		this.minSize = minSize;
	}

	/**
	 * Updates the minimum allowed size for the associated window. It doesn't
	 * get reflected on the window (use <code>setMinimumSize</code> for that).
	 * 
	 * @param minSize
	 *            The minimum allowed size for the associated window.
	 */
	public void updateMinimumSize(Dimension minSize) {
		this.minSize = minSize;
	}

	/**
	 * Gets the minimum allowed size for the associated window.
	 * 
	 * @return minSize The minimum allowed size for the associated window.
	 */
	public Dimension getMinimumSize() {
		return minSize;
	}

	/**
	 * Updates the normalWidth property for this WindowInfo object. It doesn't
	 * get reflected on the window (use <code>setNormalWidth</code> for that).
	 * 
	 * @param normalWidth
	 *            The new normalHeight property for this WindowInfo object
	 */
	public void updateNormalWidth(int normalWidth) {
		this.normalWidth = normalWidth;
		if (!isMaximized())
			this.width = normalWidth;
	}

	/**
	 * Maximize the associated window
	 * 
	 * @param maximized
	 */
	public void setMaximized(boolean maximized) {
		support.firePropertyChange("maximized", this.isMaximized, maximized);
		this.isMaximized = maximized;
	}

	/**
	 * Updates the maximized property for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setMaximized</code> for that).
	 * 
	 * @param maximized
	 *            The new maximized property for this WindowInfo object
	 */
	public void updateMaximized(boolean maximized) {
		this.isMaximized = maximized;
	}

	public void setMaximizable(boolean maximizable) {
		this.maximizable = maximizable;
	}

	/**
	 * Sets the bounds of the associated window.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void setBounds(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Updates the bounds for this WindowInfo object. It doesn't get reflected
	 * on the window (use <code>setBounds</code> for that).
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void updateBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		if (!isMaximized()) {
			this.normalX = x;
			this.normalY = y;
			this.normalWidth = width;
			this.normalHeight = height;
		}
	}

	/**
	 * Sets the normal bounds of the associated window. This sets the bounds
	 * that the window would have when it gets restored from the maximized
	 * state.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setNormalBounds(int x, int y, int width, int height) {
		setNormalX(x);
		setNormalY(y);
		setNormalWidth(width);
		setNormalHeight(height);
	}

	/**
	 * Updates the normal bounds for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setNormalBounds</code> for that).
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void updateNormalBounds(int x, int y, int width, int height) {
		this.normalX = x;
		this.normalY = y;
		this.normalWidth = width;
		this.normalHeight = height;
		if (!isMaximized()) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * Gets the normal bounds of the associated window. This gets the bounds
	 * that the window would have when it gets restored from the maximized
	 * state.
	 * 
	 * @return The normal bounds of the associated window.
	 */
	public Rectangle getNormalBounds() {
		return new Rectangle(getNormalX(), getNormalY(), getNormalWidth(),
				getNormalHeight());
	}

	/**
	 * Sets the normal bounds of the associated window. This sets the bounds
	 * that the window would have when it gets restored from the maximized
	 * state.
	 * 
	 * @param normalBounds
	 */
	public void setNormalBounds(Rectangle normalBounds) {
		setNormalBounds(normalBounds.x, normalBounds.y, normalBounds.width,
				normalBounds.height);
	}

	/**
	 * Updates the normal bounds for this WindowInfo object. It doesn't get
	 * reflected on the window (use <code>setNormalBounds</code> for that).
	 * 
	 * @param normalBounds
	 */
	public void updateNormalBounds(Rectangle normalBounds) {
		updateNormalBounds(normalBounds.x, normalBounds.y, normalBounds.width,
				normalBounds.height);
	}

	/**
	 * Sets the bounds of the associated window.
	 * 
	 * @param bounds
	 */
	public void setBounds(Rectangle bounds) {
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	/**
	 * Updates the bounds for this WindowInfo object. It doesn't get reflected
	 * on the window (use <code>setBounds</code> for that).
	 * 
	 * @param bounds
	 */
	public void updateBounds(Rectangle bounds) {
		updateBounds(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	/**
	 * Gets the bounds of the associated window.
	 * 
	 * @return The bounds of the associated window.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getNormalX() {
		if (normalX != 0)
			return this.normalX;
		else
			return x;
	}

	/**
	 * Gets the x coordinate of the window's origin when the window is not
	 * maximized. When the window is maximized, gets the y coordinate that it
	 * will have when it gets restored
	 * 
	 * @return the normal y coordinate of the window's origin
	 */
	public int getNormalY() {
		if (normalY != 0)
			return this.normalY;
		else
			return y;
	}

	/**
	 * Gets the height of the window's origin when the window is not maximized.
	 * When the window is maximized, gets the height that it will have when it
	 * gets restored
	 * 
	 * @return the normal height of the window
	 */
	public int getNormalHeight() {
		if (normalHeight != 0)
			return this.normalHeight;
		else
			return height;
	}

	/**
	 * Gets the width of the window's origin when the window is not maximized.
	 * When the window is maximized, gets the width that it will have when it
	 * gets restored
	 * 
	 * @return the normal width of the window
	 */
	public int getNormalWidth() {
		if (normalWidth != 0)
			return this.normalWidth;
		else
			return width;
	}

	/**
	 * Determines whether the window is maximized or not
	 * 
	 * @return true if the window is maximized, false otherwise
	 */
	public boolean isMaximized() {
		return this.isMaximized;
	}

	/**
	 * Checks if the geometry of this window should be persisted in the project
	 * file. This is set by the persistWindow(boolean) method, and the default
	 * value is true.
	 * 
	 * @return True if the geometry of this window should be persisted in the
	 *         project files, false otherwise.
	 */
	public boolean checkPersistence() {
		return this.persistWindow;
	}

	/**
	 * Set whether the geometry of this window should be persisted in the
	 * project files.
	 * 
	 * @param persist
	 */
	public void setPersistence(boolean persist) {
		this.persistWindow = persist;
	}

	/**
	 * Gets the window properties in an XMLEntity object, suitable for
	 * persistence.
	 * 
	 * @return An XMLEntity object containing the window properties, or null if
	 *         the window was set as not persistent
	 * @throws SaveException
	 * @throws XMLException
	 */
	public XMLEntity getXMLEntity() {
		if (checkPersistence() == false) {
			return null;
		}
		XMLEntity xml = new XMLEntity();
		xml.setName("ViewInfoProperties");
		xml.putProperty("X", this.getX(), false);
		xml.putProperty("Y", this.getY(), false);
		xml.putProperty("Width", this.getWidth(), false);
		xml.putProperty("Height", this.getHeight(), false);
		xml.putProperty("isVisible", this.isVisible(), false);
		xml.putProperty("isResizable", this.isResizable(), false);
		xml.putProperty("isMaximizable", this.isMaximizable(), false);
		xml.putProperty("isModal", this.isModal(), false);
		xml.putProperty("isModeless", this.isModeless(), false);
		xml.putProperty("isClosed", this.isClosed(), false);
		xml.putProperty("isNotClosable", this.isNotClosable(), false);
		xml.putProperty("AdditionalInfo", this.getAdditionalInfo(), false);
		if (this.isMaximized() == true) {
			xml.putProperty("isMaximized", this.isMaximized(), false);
			xml.putProperty("normalX", this.getNormalX(), false);
			xml.putProperty("normalY", this.getNormalY(), false);
			xml.putProperty("normalWidth", this.getNormalWidth(), false);
			xml.putProperty("normalHeight", this.getNormalHeight(), false);
		}
		return xml;
	}

	/**
	 * Creates a WindowInfo object from an XMLEntity containing the window
	 * properties.
	 * 
	 * @param xml
	 *            An XMLEntity object containing the window properties
	 * 
	 * @return A new WindowInfo object, containing the properties specified in
	 *         the XMLEntity parameters
	 */
	public static WindowInfo createFromXMLEntity(XMLEntity xml) {
		WindowInfo result = new WindowInfo();
		try {
			result.setX(xml.getIntProperty("X"));
			result.setY(xml.getIntProperty("Y"));
			result.setHeight(xml.getIntProperty("Height"));
			result.setWidth(xml.getIntProperty("Width"));
			result.setClosed(xml.getBooleanProperty("isClosed"));
			result.setNotClosable(xml.getBooleanProperty("isNotClosable"));
			result.setAdditionalInfo(xml.getStringProperty("AdditionalInfo"));
			if (xml.contains("isMaximized")) {
				boolean maximized = xml.getBooleanProperty("isMaximized");
				result.setMaximized(maximized);
				if (maximized == true) {
					result.setNormalBounds(xml.getIntProperty("normalX"),
							xml.getIntProperty("normalY"),
							xml.getIntProperty("normalWidth"),
							xml.getIntProperty("normalHeight"));
				} else {
					result.setNormalBounds(result.getBounds());
				}
			}
		} catch (com.iver.utiles.NotExistInXMLEntity ex) {
			PluginServices
					.getLogger()
					.warn(PluginServices
							.getText(null,
									"Window_properties_not_stored_correctly_Window_state_will_not_be_restored"));
		}

		return result;
	}

	public void setNotClosable(boolean b) {
		notclosable = b;
	}

	/**
	 * Updates this WindowInfo object according to the properties provided by
	 * the XMLEntity parameter.
	 * 
	 * @param xml
	 *            An XMLEntity object containing the window properties
	 */
	public void getPropertiesFromXMLEntity(XMLEntity xml) {
		this.x = xml.getIntProperty("X");
		this.y = xml.getIntProperty("Y");
		this.height = xml.getIntProperty("Height");
		this.width = xml.getIntProperty("Width");
		this.isClosed = xml.getBooleanProperty("isClosed");
		this.notclosable = xml.getBooleanProperty("isNotClosable");
		this.additionalInfo = xml.getStringProperty("AdditionalInfo");
		if (xml.contains("isMaximized")) {
			boolean maximized = xml.getBooleanProperty("isMaximized");
			this.isMaximized = maximized;
			if (maximized == true) {
				this.setNormalBounds(xml.getIntProperty("normalX"),
						xml.getIntProperty("normalY"),
						xml.getIntProperty("normalWidth"),
						xml.getIntProperty("normalHeight"));
			}
		}
	}

	/**
	 * Obtiene información adicional de la ventana. Esta etiqueta podrá llevar
	 * cualquier tipo de información que queramos almacenar.
	 * 
	 * @return Información adicional
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * Asigna información adicional de la ventana. Esta etiqueta podrá llevar
	 * cualquier tipo de información que queramos almacenar.
	 * 
	 * @return Información adicional
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}
