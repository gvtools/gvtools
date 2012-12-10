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
package com.iver.cit.gvsig.project.documents.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.utiles.listManager.ListManager;

public class ListManagerSkin extends JPanel {

	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JButton jButtonDown = null;
	private JButton jButtonUp = null;
	private JButton jButtonAdd = null;
	private JButton jButtonDel = null;

	private ListManager listManager;// = new ListManager();

	private JList jList = null;
	private JPanel jPanel1 = null;

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			jPanel.setLayout(flowLayout);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton("abajo");
			jButtonDown.setText(PluginServices.getText(this, "abajo"));
		}
		return jButtonDown;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton("arriba");
			jButtonUp.setText(PluginServices.getText(this, "arriba"));
		}
		return jButtonUp;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton("A�adir");
			jButtonAdd.setText(PluginServices.getText(this, "Anadir"));
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDel() {
		if (jButtonDel == null) {
			jButtonDel = new JButton("Eliminar");
			jButtonDel.setText(PluginServices.getText(this, "Eliminar"));
		}
		return jButtonDel;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
		}
		return jList;
	}

	/**
	 * This is the default constructor
	 */
	public ListManagerSkin(boolean down) {
		super();
		listManager = new ListManager(down);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(421, 349);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.EAST);
		listManager.setBtnDown(getJButtonDown());
		listManager.setBtnUp(getJButtonUp());
		listManager.setBtnAdd(getJButtonAdd());
		listManager.setBtnDel(getJButtonDel());
		listManager.setList(getJList());
		listManager.initialize();

	}

	/**
	 * @return Returns the listManager.
	 */
	public ListManager getListManager() {
		return listManager;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel(new GridLayout(6, 1, 5, 5));

			jPanel1.setPreferredSize(new Dimension(100, 180));
			jPanel1.add(getJButtonAdd(), null);
			jPanel1.add(getJButtonDel(), null);
			jPanel1.add(getJButtonUp(), null);
			jPanel1.add(getJButtonDown(), null);
		}
		return jPanel1;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
