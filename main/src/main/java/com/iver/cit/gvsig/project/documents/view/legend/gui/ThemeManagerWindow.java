/*
 *
 * gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * 700
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * @version 01-feb-2007
 */
public final class ThemeManagerWindow extends JPanel implements IWindow,
		ActionListener {
	private static final long serialVersionUID = 4650656815369149211L;
	private static int selectedTabIndex = 0;
	private static ArrayList<Class<? extends AbstractThemeManagerPage>> pages = new ArrayList<Class<? extends AbstractThemeManagerPage>>();
	private Layer layer;
	private JTabbedPane tabs = new JTabbedPane(); // @jve:decl-index=0:
	private JPanel panelButtons;

	/**
	 * Sobrecarga del constructor. Este se utiliza cuando se llama a este
	 * di�logo desde la barra de comandos.
	 * 
	 */
	public ThemeManagerWindow(Layer l) {
		this.layer = l;

		// TODO falta definir leyenda para cualquier tipo de capa
		// y decidir entonces qu� opciones soporta cada una para
		// que el di�logo se autorrellene
		initialize();
	}

	private void initialize() {
		for (int i = 0; i < pages.size(); i++) {
			Class<? extends AbstractThemeManagerPage> pageClass = pages.get(i);
			AbstractThemeManagerPage tab;
			try {
				tab = (AbstractThemeManagerPage) pageClass.newInstance();
				// if (tab.isSuitableFor(layer)) {
				if (tab.accepts(layer)) {
					tab.setModel(layer);
					tabs.addTab(tab.getName(), tab);
				}
			} catch (InstantiationException e) {
				NotificationManager
						.addError(
								"Trying to instantiate an interface"
										+ " or abstract class + "
										+ pageClass.getName(), e);
			} catch (IllegalAccessException e) {
				NotificationManager.addError("IllegalAccessException: does "
						+ pageClass.getName() + " class have an anonymous"
						+ " constructor?", e);
			}
		}

		if (tabs.getComponentCount() > selectedTabIndex) {
			tabs.setSelectedIndex(selectedTabIndex);
		}
		tabs.setPreferredSize(new java.awt.Dimension(640, 370));

		setLayout(new BorderLayout());
		add(tabs, java.awt.BorderLayout.CENTER);

		// The listener must be added after the tabs are added to the window
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				// remember the visible tab
				selectedTabIndex = tabs.getSelectedIndex();
				if (selectedTabIndex < 0)
					selectedTabIndex = 0;
			};
		});
		add(getPanelButtons(), java.awt.BorderLayout.SOUTH);
		setSize(new Dimension(790, 490));
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

			JButton btnAceptar = new JButton(PluginServices.getText(this,
					"Aceptar"));
			btnAceptar.setActionCommand("OK");
			btnAceptar.addActionListener(this);

			JButton btnApply = new JButton(
					PluginServices.getText(this, "Apply"));
			btnApply.setActionCommand("APPLY");
			btnApply.addActionListener(this);

			JButton btnCancelar = new JButton(PluginServices.getText(this,
					"Cerrar"));
			btnCancelar.setActionCommand("CANCEL");
			btnCancelar.addActionListener(this);
			panelButtons.setPreferredSize(new java.awt.Dimension(493, 33));
			panelButtons.add(btnCancelar);
			panelButtons.add(btnApply);
			panelButtons.add(btnAceptar);
		}
		return panelButtons;
	}

	public void actionPerformed(ActionEvent e) {

		String actionCommand = e.getActionCommand();

		if (actionCommand == "OK") {
			/*
			 * Causes all the tabs in the ThemeManagerWindow to perform THEIR
			 * apply-action then fires the LegendChanged event that causes the
			 * view to be refreshed. After that, it closes the window.
			 */
			this.applyChanges();
			close();
		} else if (actionCommand == "CANCEL") {
			/*
			 * Causes all the tabs in the ThemeManagerWindow to perform THEIR
			 * cancel-action then closes the window.
			 */
			for (int i = 0; i < tabs.getTabCount(); i++) {
				AbstractThemeManagerPage tab = (AbstractThemeManagerPage) tabs
						.getComponentAt(i);
				tab.cancelAction();
			}
			close();
		} else if (actionCommand == "APPLY") {
			/*
			 * Causes all the tabs in the ThemeManagerWindow to perform ITS
			 * specific apply-action.
			 */
			this.applyChanges();
		} else {
		}
		// Lots of temporary objects were create.
		// let's try some memory cleanup
		System.gc();
	}

	private void applyChanges() {
		for (int i = 0; i < tabs.getTabCount(); i++) {
			AbstractThemeManagerPage tab = (AbstractThemeManagerPage) tabs
					.getComponentAt(i);
			tab.applyAction();
		}
	}

	private void close() {
		PluginServices.getMDIManager().closeWindow(ThemeManagerWindow.this);
	}

	public WindowInfo getWindowInfo() {
		WindowInfo viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG
				| WindowInfo.RESIZABLE);
		viewInfo.setWidth(getWidth());
		viewInfo.setHeight(getHeight());
		viewInfo.setTitle(PluginServices
				.getText(this, "propiedades_de_la_capa"));
		return viewInfo;
	}

	public static void addPage(
			Class<? extends AbstractThemeManagerPage> abstractThemeManagerPageClass) {
		pages.add(abstractThemeManagerPageClass);
	}

	public Object getWindowProfile() {
		return WindowInfo.PROPERTIES_PROFILE;
	};
} // @jve:decl-index=0:visual-constraint="10,10"
