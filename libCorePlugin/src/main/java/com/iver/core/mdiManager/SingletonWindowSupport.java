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
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

import com.iver.andami.ui.mdiManager.SingletonDialogAlreadyShownException;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision: 15982 $
 */
public class SingletonWindowSupport {
	private static int singletonViewInfoID = 0;
	/** Hashtable que asocia contenido con vistas */
	private HashMap contentWindowInfo = new HashMap();
	private WindowInfoSupport vis;
	private FrameWindowSupport frameWindowSupport;
	private HashMap contentFrame = new HashMap();

	/**
	 * DOCUMENT ME!
	 * 
	 * @param vis
	 *            DOCUMENT ME!
	 * @param fvs
	 * 
	 * @see com.iver.andami.ui.mdiManager.MDIManager#init(com.iver.andami.ui.mdiFrame.MDIFrame)
	 */
	public SingletonWindowSupport(WindowInfoSupport vis, FrameWindowSupport fvs) {
		this.vis = vis;
		this.frameWindowSupport = fvs;
	}

	/**
	 * Devuelve una referencia a la vista si ya está mostrada o null si la vista
	 * no ha sido añadida o ya fue cerrada
	 * 
	 * @param windowClass
	 *            DOCUMENT ME!
	 * @param model
	 *            DOCUMENT ME!
	 * @param wi
	 *            DOCUMENT ME!
	 * 
	 * @return true si la vista existe ya y false si la vista no existe
	 * 
	 * @throws SingletonDialogAlreadyShownException
	 *             DOCUMENT ME!
	 */
	public boolean registerWindow(Class windowClass, Object model, WindowInfo wi) {
		// Se comprueba si la ventana está siendo mostrada
		SingletonWindowInfo swi = new SingletonWindowInfo(windowClass, model);

		if (contentWindowInfo.containsKey(swi)) {
			if (wi.isModal()) {
				throw new SingletonDialogAlreadyShownException();
			}

			wi.setWindowInfo((WindowInfo) contentWindowInfo.get(swi));

			return true;
		} else {
			// La ventana singleton no estaba mostrada
			// Se asocia el modelo con la vista
			contentWindowInfo.put(swi, wi);
			return false;
		}
	}

	public void openSingletonWindow(SingletonWindow sw, Component frame) {
		SingletonWindowInfo swi = new SingletonWindowInfo(sw.getClass(),
				sw.getWindowModel());
		contentFrame.put(swi, frame);
	}

	public boolean contains(SingletonWindow sw) {
		SingletonWindowInfo swi = new SingletonWindowInfo(sw.getClass(),
				sw.getWindowModel());
		return contentFrame.containsKey(swi);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param sw
	 */
	public void closeWindow(SingletonWindow sw) {
		SingletonWindowInfo swi = new SingletonWindowInfo(sw.getClass(),
				sw.getWindowModel());
		WindowInfo windowInfo = (WindowInfo) contentWindowInfo.get(swi);
		if (windowInfo != null) {
			frameWindowSupport.updateWindowInfo(sw, windowInfo);
		}
		contentFrame.remove(swi);
	}

	/**
	 * Representa una vista singleton manteniendo el modelo y la clase de la
	 * vista que lo muestra
	 * 
	 * @author Fernando González Cortés
	 */
	public class SingletonWindowInfo {

		public int id;

		/** Clase de la vista */
		public Class clase;

		/** Modelo que representa la vista */
		public Object modelo;

		/**
		 * Creates a new SingletonView object.
		 * 
		 * @param clase
		 *            Clase de la vista
		 * @param modelo
		 *            Modelo que representa la vista
		 */
		public SingletonWindowInfo(Class clase, Object modelo) {
			this.clase = clase;
			this.modelo = modelo;
			this.id = singletonViewInfoID;
			singletonViewInfoID++;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			if (obj.getClass() != SingletonWindowInfo.class) {
				throw new IllegalArgumentException();
			}

			SingletonWindowInfo s = (SingletonWindowInfo) obj;

			if ((clase == s.clase) && (modelo == s.modelo)) {
				return true;
			} else {
				return false;
			}
		}
	}

	private Component getFrame(SingletonWindowInfo svi) {
		WindowInfo vi = (WindowInfo) contentWindowInfo.get(svi);
		return (JInternalFrame) contentFrame.get(svi);
	}

	public Component getFrame(Class viewClass, Object model) {
		SingletonWindowInfo svi = new SingletonWindowInfo(viewClass, model);
		return getFrame(svi);
	}

	/**
	 * @param model
	 * @return
	 */
	public Component[] getFrames(Object model) {
		ArrayList ret = new ArrayList();

		ArrayList keys = contentFrame.getKeys();
		for (int i = 0; i < keys.size(); i++) {
			SingletonWindowInfo svi = (SingletonWindowInfo) keys.get(i);

			if (svi.modelo == model) {
				ret.add(contentFrame.get(svi));
			}
		}

		return (JInternalFrame[]) ret.toArray(new JInternalFrame[0]);
	}

	/**
	 * @param view
	 * @return
	 */
	public Component getFrame(SingletonWindow sv) {
		SingletonWindowInfo svi = new SingletonWindowInfo(sv.getClass(),
				sv.getWindowModel());
		return getFrame(svi);
	}

	/**
	 * @param sv
	 * @param i
	 */
	public void setX(SingletonWindow sv, int x) {
		JInternalFrame o = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sv.getClass(), sv.getWindowModel()));

		if (o == null)
			return;
		o.setLocation(x, o.getY());
	}

	/**
	 * @param sv
	 * @param i
	 */
	public void setY(SingletonWindow sv, int y) {
		JInternalFrame o = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sv.getClass(), sv.getWindowModel()));

		if (o == null)
			return;

		o.setLocation(o.getX(), y);
	}

	/**
	 * @param sv
	 * @param i
	 */
	public void setHeight(SingletonWindow sv, int height) {
		JInternalFrame o = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sv.getClass(), sv.getWindowModel()));

		if (o == null)
			return;

		o.setSize(o.getWidth(), height);
	}

	/**
	 * @param sv
	 * @param i
	 */
	public void setWidth(SingletonWindow sv, int width) {
		JInternalFrame o = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sv.getClass(), sv.getWindowModel()));

		if (o == null)
			return;
		o.setSize(width, o.getHeight());
	}

	/**
	 * @param sw
	 * @param maximized
	 */
	public void setMaximized(SingletonWindow sw, boolean maximized) {
		JInternalFrame frame = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sw.getClass(), sw.getWindowModel()));

		if (frame == null)
			return;
		try {
			frame.setMaximum(maximized);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * @param sw
	 * @param maximized
	 */
	public void setNormalBounds(SingletonWindow sw, Rectangle normalBounds) {
		JInternalFrame frame = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sw.getClass(), sw.getWindowModel()));

		if (frame == null)
			return;
		frame.setNormalBounds(normalBounds);
	}

	/**
	 * Sets the minimum allowed size for the provided singleton window.
	 * 
	 * @param sw
	 * @param minSize
	 */
	public void setMinimumSize(SingletonWindow sw, Dimension minSize) {
		JInternalFrame frame = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sw.getClass(), sw.getWindowModel()));

		if (frame == null)
			return;
		frame.setMinimumSize(minSize);
	}

	/**
	 * @param sv
	 * @param string
	 */
	public void setTitle(SingletonWindow sv, String title) {
		JInternalFrame o = (JInternalFrame) contentFrame
				.get(new SingletonWindowInfo(sv.getClass(), sv.getWindowModel()));

		if (o == null)
			return;
		o.setTitle(title);
	}

	private class HashMap {
		private ArrayList keys = new ArrayList();
		private ArrayList values = new ArrayList();

		public void put(SingletonWindowInfo key, Object value) {
			int index = -1;
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					index = i;
					break;
				}
			}

			if (index != -1) {
				keys.add(index, key);
				values.add(index, value);
			} else {
				keys.add(key);
				values.add(value);
			}
		}

		public boolean containsKey(SingletonWindowInfo key) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					return true;
				}
			}

			return false;
		}

		public Object get(SingletonWindowInfo key) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					return values.get(i);
				}
			}

			return null;
		}

		public void remove(SingletonWindowInfo key) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					keys.remove(i);
					values.remove(i);
				}
			}
		}

		public ArrayList getKeys() {
			return keys;
		}
	}
}
