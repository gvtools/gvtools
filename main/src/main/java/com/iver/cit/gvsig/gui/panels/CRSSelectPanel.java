/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2006 IVER T.I. and Generalitat Valenciana.
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
package com.iver.cit.gvsig.gui.panels;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gvsig.gui.beans.swing.JButton;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.panels.crs.CrsUIFactory;
import com.iver.cit.gvsig.gui.panels.crs.ICrsUIFactory;
import com.iver.cit.gvsig.gui.panels.crs.ISelectCRSButton;

/**
 * 
 * @author Luis W. Sevilla <sevilla_lui@gva.es>
 */
public abstract class CRSSelectPanel extends JPanel implements ISelectCRSButton {
	private static Class<?> panelClass = ProjChooserPanel.class;
	private static Class<?> uiFactory = CrsUIFactory.class;
	private boolean transPanelActive = false;

	protected ActionListener actionListener = null;

	public static void registerPanelClass(Class<?> panelClass) {
		CRSSelectPanel.panelClass = panelClass;
	}

	public static void registerUIFactory(Class<?> uiFactory) {
		CRSSelectPanel.uiFactory = uiFactory;
	}

	public static CRSSelectPanel getPanel(CoordinateReferenceSystem crs) {
		CRSSelectPanel panel = null;
		Class<?>[] args = { CoordinateReferenceSystem.class };
		Object[] params = { crs };
		try {
			panel = (CRSSelectPanel) panelClass.getConstructor(args)
					.newInstance(params);
		} catch (IllegalArgumentException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		} catch (SecurityException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		} catch (InstantiationException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		} catch (IllegalAccessException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		} catch (InvocationTargetException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		} catch (NoSuchMethodException e) {
			PluginServices.getLogger().error(
					"Error creating CRS selection button", e);
		}
		return panel;
	}

	public CRSSelectPanel(CoordinateReferenceSystem crs) {
		super();
	}

	abstract public JButton getJBtnChangeProj();

	abstract public JLabel getJLabel();

	abstract public CoordinateReferenceSystem getCurrentCrs();

	abstract public boolean isOkPressed();

	/**
	 * @param actionListener
	 *            The actionListener to set.
	 */
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public boolean isTransPanelActive() {
		return transPanelActive;
	}

	public void setTransPanelActive(boolean transPanelActive) {
		this.transPanelActive = transPanelActive;
	}

	public static ICrsUIFactory getUIFactory() {
		ICrsUIFactory factory;
		try {
			factory = (ICrsUIFactory) uiFactory.newInstance();
		} catch (InstantiationException e) {
			PluginServices
					.getLogger()
					.error("Error creating CRS UI factory. Switching to default factory",
							e);
			factory = new CrsUIFactory();
		} catch (IllegalAccessException e) {
			PluginServices
					.getLogger()
					.error("Error creating CRS UI factory. Switching to default factory",
							e);
			factory = new CrsUIFactory();
		}
		return factory;
	}

}
