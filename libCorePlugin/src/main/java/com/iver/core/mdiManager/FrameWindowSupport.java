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
package com.iver.core.mdiManager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.core.mdiManager.frames.ExternalFrame;
import com.iver.core.mdiManager.frames.IFrame;
import com.iver.core.mdiManager.frames.InternalFrame;

/**
 *
 */
public class FrameWindowSupport {
	private Hashtable frameView = new Hashtable();
	private Hashtable viewFrame = new Hashtable();
	private Image icon;
	private WindowInfoSupport vis;
	private JFrame mainFrame;

	/**
	 * Creates a new FrameViewSupport object.
	 * 
	 * @param i
	 *            DOCUMENT ME!
	 */
	public FrameWindowSupport(MDIFrame mainFrame) {
		this.mainFrame = mainFrame;
		icon = mainFrame.getIconImage();
	}

	public Iterator getWindowIterator() {
		return viewFrame.keySet().iterator();
	}

	public boolean contains(IWindow v) {
		return viewFrame.containsKey(v);
	}

	/**
	 * @param wnd
	 * @return
	 */
	public boolean contains(JInternalFrame wnd) {
		return frameView.contains(wnd);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param p
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JDialog getJDialog(IWindow p) {
		JDialog dlg = (JDialog) viewFrame.get(p);

		if (dlg == null) {
			WindowInfo vi = vis.getWindowInfo(p);
			ExternalFrame nuevo = new ExternalFrame(mainFrame);

			nuevo.getContentPane().add((JPanel) p);
			nuevo.setSize(getWidth(p, vi), getHeight(p, vi) + 30);
			nuevo.setTitle(vi.getTitle());
			nuevo.setResizable(vi.isResizable());
			nuevo.setMinimumSize(vi.getMinimumSize());

			viewFrame.put(p, nuevo);
			frameView.put(nuevo, p);

			nuevo.setModal(vi.isModal());
			return nuevo;
		} else {
			return dlg;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param p
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public JInternalFrame getJInternalFrame(IWindow p) {
		JInternalFrame frame = (JInternalFrame) viewFrame.get(p);

		if (frame == null) {
			// ViewInfo vi = vis.getViewInfo(p);
			JInternalFrame nuevo = createJInternalFrame(p);
			viewFrame.put(p, nuevo);
			frameView.put(nuevo, p);

			return nuevo;
		} else {
			return frame;
		}
	}

	/**
	 * Gets the frame associated to the provided IWindow panel. The frame will
	 * usually be a JInternalFrame or a JDialog.
	 * 
	 * @param panel
	 *            The IWindow panel whose frame wants to be retrieved.
	 * 
	 * @return The associated frame, it will usually be a JInternalFrame or a
	 *         JDialog.
	 */
	public Component getFrame(IWindow panel) {
		Object object = viewFrame.get(panel);
		if (object != null && object instanceof Component) {
			return (Component) object;
		} else {
			PluginServices.getLogger().error(
					"window_not_found_" + panel.getWindowInfo().getTitle());
			return null;
		}
	}

	public JInternalFrame createJInternalFrame(IWindow p) {
		WindowInfo wi = vis.getWindowInfo(p);
		JInternalFrame nuevo = new InternalFrame();
		if (icon != null) {
			nuevo.setFrameIcon(new ImageIcon(icon));
		}

		nuevo.getContentPane().add((JPanel) p);
		nuevo.setClosable(!wi.isNotClosable());
		nuevo.setSize(getWidth(p, wi), getHeight(p, wi));
		nuevo.setTitle(wi.getTitle());
		nuevo.setVisible(wi.isVisible());
		nuevo.setResizable(wi.isResizable());
		nuevo.setIconifiable(wi.isIconifiable());
		nuevo.setMaximizable(wi.isMaximizable());
		nuevo.setLocation(wi.getX(), wi.getY());
		nuevo.setMinimumSize(wi.getMinimumSize());

		nuevo.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		return nuevo;
	}

	public IWindow getWindow(Component dlg) {
		return (IWindow) frameView.get(dlg);
	}

	public void closeWindow(IWindow v) {
		Object c = viewFrame.remove(v);
		frameView.remove(c);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * @param x
	 *            DOCUMENT ME!
	 */
	public void setX(IWindow win, int x) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setX(x);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * @param y
	 *            DOCUMENT ME!
	 */
	public void setY(IWindow win, int y) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setY(y);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * @param height
	 *            DOCUMENT ME!
	 */
	public void setHeight(IWindow win, int height) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setHeight(height);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * @param width
	 *            DOCUMENT ME!
	 */
	public void setWidth(IWindow win, int width) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setWidth(width);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * @param title
	 *            DOCUMENT ME!
	 */
	public void setTitle(IWindow win, String title) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setTitle(title);
	}

	/**
	 * Sets the minimum allowed size for the provided window.
	 * 
	 * @param sw
	 * @param minSize
	 */
	public void setMinimumSize(IWindow win, Dimension minSize) {
		IFrame frame = (IFrame) viewFrame.get(win);
		frame.setMinimumSize(minSize);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param vis
	 *            The vis to set.
	 */
	public void setVis(WindowInfoSupport vis) {
		this.vis = vis;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private int getWidth(IWindow v) {
		WindowInfo vi = vis.getWindowInfo(v);

		if (vi.getWidth() == -1) {
			JPanel p = (JPanel) v;

			return p.getSize().width;
		} else {
			return vi.getWidth();
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private int getWidth(IWindow v, WindowInfo wi) {
		if (wi.getWidth() == -1) {
			JPanel p = (JPanel) v;

			return p.getSize().width;
		} else {
			return wi.getWidth();
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private int getHeight(IWindow v) {
		WindowInfo vi = vis.getWindowInfo(v);

		if (vi.getHeight() == -1) {
			JPanel p = (JPanel) v;

			return p.getSize().height;
		} else {
			return vi.getHeight();
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	private int getHeight(IWindow v, WindowInfo wi) {
		if (wi.getHeight() == -1) {
			JPanel p = (JPanel) v;

			return p.getSize().height;
		} else {
			return wi.getHeight();
		}
	}

	public void updateWindowInfo(IWindow win, WindowInfo windowInfo) {
		Object o = viewFrame.get(win);
		if (windowInfo != null && o != null) {
			if (o instanceof JComponent) {
				JComponent component = (JComponent) o;
				windowInfo.updateWidth(component.getWidth());
				windowInfo.updateHeight(component.getHeight());
				windowInfo.updateX(component.getX());
				windowInfo.updateY(component.getY());
				windowInfo.updateClosed(!component.isShowing());
				if (component instanceof JInternalFrame) {
					JInternalFrame iframe = (JInternalFrame) component;
					windowInfo.updateNormalBounds(iframe.getNormalBounds());
					windowInfo.updateMaximized(iframe.isMaximum());
				}
			}
		}
	}

}
