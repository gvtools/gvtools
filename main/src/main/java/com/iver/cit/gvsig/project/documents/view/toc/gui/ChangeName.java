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
package com.iver.cit.gvsig.project.documents.view.toc.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gvsig.gui.beans.AcceptCancelPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * Diálogo para cambiar el nombre de una capa.
 * 
 * @author Vicente Caballero Navarro
 */
public class ChangeName extends JPanel implements IWindow {
	private JPanel jContentPane = null;
	private JLabel lblName = null;
	private JTextField txtName = null;
	private String name;
	private JPanel jPanel = null;
	private AcceptCancelPanel jPanel1 = null;
	private boolean isAccepted = false;

	/**
	 * This is the default constructor
	 * 
	 * @param layout
	 *            Referencia al Layout.
	 * @param fframe
	 *            Referencia al fframe de imagen.
	 */
	public ChangeName(String n) {
		super();
		if (n == null) {
			name = PluginServices.getText(this, "agrupacion");
		} else {
			name = n;
		}
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(null);
		this.add(getJContentPane(), null);
		this.setSize(381, 77);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setSize(385, 77);
			jContentPane.setLocation(0, 0);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
		}

		return jContentPane;
	}

	/**
	 * This method initializes lFichero
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel();
			lblName.setSize(42, 20);
			lblName.setText(PluginServices.getText(this, "nombre"));
			lblName.setLocation(10, 9);
		}

		return lblName;
	}

	/**
	 * This method initializes tFichero
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setBounds(62, 8, 280, 20);
			txtName.setPreferredSize(new java.awt.Dimension(270, 20));
			txtName.setText(name);
			txtName.addKeyListener(new KeyListener() {

				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						acceptAction();
					} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						cancelAction();
					}

				}

				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

			});
		}
		return txtName;
	}

	/**
	 * Inserta el path al TFichero.
	 * 
	 * @param val
	 *            path del fichero.
	 */
	public void setText(String val) {
		getTxtName().setText(val);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.View#getViewInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
		m_viewinfo.setTitle(PluginServices.getText(this, "cambio_nombre"));
		return m_viewinfo;
	}

	public String getName() {
		return name;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			jPanel.add(getLblName(), null);
			jPanel.add(getTxtName(), null);

			jPanel.setBounds(new java.awt.Rectangle(4, 3, 329, 35));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private AcceptCancelPanel getJPanel1() {
		if (jPanel1 == null) {
			ActionListener okActionListener, cancelActionListener;
			okActionListener = new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					acceptAction();
				}
			};
			cancelActionListener = new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelAction();
				}
			};
			jPanel1 = new AcceptCancelPanel(okActionListener,
					cancelActionListener);
			jPanel1.setBounds(new java.awt.Rectangle(3, 39, 374, 35));
		}
		return jPanel1;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	protected void acceptAction() {
		name = getTxtName().getText();
		isAccepted = true;
		PluginServices.getMDIManager().closeWindow(ChangeName.this);

	}

	protected void cancelAction() {
		isAccepted = false;
		PluginServices.getMDIManager().closeWindow(ChangeName.this);

	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

