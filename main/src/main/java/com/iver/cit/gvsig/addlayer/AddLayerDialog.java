/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
 */
package com.iver.cit.gvsig.addlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gvsig.gui.beans.AcceptCancelPanel;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.gui.AddLayerWizard;
import com.iver.cit.gvsig.gui.wizards.WizardListener;
import com.iver.cit.gvsig.project.Project;

/**
 * Frame del cuadro de dialogo que contendra los tabs de aperturas de ficheros
 * 
 * @version 04/09/2007
 * @author BorSanZa - Borja S�nchez Zamorano (borja.sanchez@iver.es)
 */
public class AddLayerDialog extends JPanel implements
		com.iver.andami.ui.mdiManager.IWindow {
	static private CoordinateReferenceSystem crs = null;
	private JTabbedPane jTabbedPane = null;
	private AcceptCancelPanel jPanel = null;

	private boolean accepted = false;

	private String title = PluginServices.getText(this, "add_layer");
	private WizardListener wizardListener = new DialogWizardListener();

	/**
	 * Creates a new FOpenDialog object.
	 * 
	 * @param view
	 *            Vista que vamos a refrescar
	 * @param mapControl
	 *            MapControl que recibe la capa (te puede interesar a�adirla al
	 *            principal o al Overview.
	 */
	public AddLayerDialog() {
		initialize();
	}

	/**
	 * Constructor con un nombre de Frame
	 * 
	 * @param title
	 */
	public AddLayerDialog(String title) {
		this.title = title;
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(523, 385);
		this.setPreferredSize(new Dimension(523, 385));
		this.add(getJTabbedPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(0, 0, getWindowInfo().getWidth() - 10,
					getWindowInfo().getHeight() - 10);
			jTabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					getJPanel().setOkButtonEnabled(false);
				}
			});
		}

		return jTabbedPane;
	}

	/**
	 * A�ade en una pesta�a un Jpanel con un titulo
	 * 
	 * @param title
	 * @param panel
	 */
	public void addTab(String title, JPanel panel) {
		getJTabbedPane().addTab(title, panel);
	}

	/**
	 * A�ade en una pesta�a un WizardPanel con un titulo
	 * 
	 * @param title
	 * @param panel
	 */
	public void addWizardTab(String title, AddLayerWizard panel) {
		panel.addWizardListener(wizardListener);
		getJTabbedPane().addTab(title, panel);
	}

	/**
	 * Devuelve el JPanel seleccionado en las pesta�as
	 * 
	 * @return
	 */
	public AddLayerWizard getSelectedTab() {
		return (AddLayerWizard) getJTabbedPane().getSelectedComponent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
		m_viewinfo.setTitle(this.title);
		m_viewinfo.setHeight(500);
		m_viewinfo.setWidth(520);
		return m_viewinfo;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	public AcceptCancelPanel getJPanel() {
		if (jPanel == null) {
			ActionListener okAction = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// if not wizard panel, simply close
					if (!(getSelectedTab() instanceof AddLayerWizard)) {
						accepted = true;
						if (PluginServices.getMainFrame() == null) {
							((JDialog) (getParent().getParent().getParent()
									.getParent())).dispose();
						} else {
							PluginServices.getMDIManager().closeWindow(
									(IWindow) AddLayerDialog.this);
						}
					} else {
						// // if wizard panel, validated settings first
						AddLayerWizard wp = (AddLayerWizard) getSelectedTab();
						String[] invalid_layer_settings_message = wp
								.validateLayerSettings();
						if (invalid_layer_settings_message == null) {
							accepted = true;
							if (PluginServices.getMainFrame() == null) {
								((JDialog) (getParent().getParent().getParent()
										.getParent())).dispose();
							} else {
								PluginServices.getMDIManager().closeWindow(
										(IWindow) AddLayerDialog.this);
							}
						} else {
							JOptionPane
									.showMessageDialog(
											(Component) PluginServices
													.getMainFrame(),
											PluginServices
													.getText(this,
															"Error_reported_check_also_restrictions")
													+ "\n"
													+ appendStringArray(invalid_layer_settings_message),
											PluginServices.getText(this,
													"panel_loading_exception"),
											JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			};

			ActionListener cancelAction = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					accepted = false;
					if (PluginServices.getMainFrame() != null) {
						PluginServices.getMDIManager().closeWindow(
								(IWindow) AddLayerDialog.this);
					} else {
						((JDialog) (getParent().getParent().getParent()
								.getParent())).dispose();
					}
				}
			};
			jPanel = new AcceptCancelPanel(okAction, cancelAction);
		}
		return jPanel;
	}

	/**
	 * Listener para el Wizard de apertura de fichero
	 * 
	 * @version 05/09/2007
	 * @author BorSanZa - Borja S�nchez Zamorano (borja.sanchez@iver.es)
	 */
	public class DialogWizardListener implements WizardListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.iver.cit.gvsig.gui.wizards.WizardListener#error(java.lang.Exception
		 * )
		 */
		public void error(Exception e) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.iver.cit.gvsig.gui.wizards.WizardListener#wizardStateChanged(
		 * boolean)
		 */
		public void wizardStateChanged(boolean finishable) {
			getJPanel().setOkButtonEnabled(finishable);
		}
	}

	/**
	 * Devuelve la ultima proyecci�n usada
	 * 
	 * @return
	 */
	public static CoordinateReferenceSystem getLastCrs() {
		if (crs == null)
			crs = Project.getDefaultCrs();
		return crs;
	}

	/**
	 * Define la ultima proyeccion
	 * 
	 * @param crs
	 */
	public static void setLastCrs(CoordinateReferenceSystem crs) {
		AddLayerDialog.crs = crs;
	}

	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return WindowInfo.DIALOG_PROFILE;
	}

	public boolean isAccepted() {
		return accepted;
	}

	private String appendStringArray(String[] strarr) {

		String resp = "";
		int len = strarr.length;
		for (int i = 0; i < len; i++) {
			resp = "\n" + strarr[i] + "\n";
		}
		return resp;
	}
}

// [eiel-gestion-conexiones]