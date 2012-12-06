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
package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ListModel;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.project.documents.view.ListSelectorListener;

/**
 * DOCUMENT ME!
 * 
 * @author Fernando González Cortés
 */
public class FPanelExtentSelector extends JPanel implements IWindow {
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JList lista = null;
	private JButton btnAceptar = null;
	private ListModel modelo = null;
	private ArrayList selectionListeners = new ArrayList();
	private JButton btnEliminar = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField txtGuardar = null;
	private JButton btnGuardar = null;
	private WindowInfo m_viewinfo = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;

	/**
	 * This is the default constructor
	 */
	public FPanelExtentSelector() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(new java.awt.Dimension(530, 320));
		this.add(getJPanel(), java.awt.BorderLayout.NORTH);
		this.add(getJPanel1(), java.awt.BorderLayout.SOUTH);

	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getLista());
			jScrollPane.setBounds(11, 26, 508, 150);
			jScrollPane.setPreferredSize(new java.awt.Dimension(400, 150));
		}

		return jScrollPane;
	}

	/**
	 * This method initializes lista
	 * 
	 * @return javax.swing.JList
	 */
	private javax.swing.JList getLista() {
		if (lista == null) {
			lista = new javax.swing.JList();
		}

		return lista;
	}

	/**
	 * Asigna el el array que se va a representar. Se crea una copia del modelo,
	 * de forma que modificaciones en el modelo fuera de esta clase ya no
	 * afectan al modelo copiado
	 * 
	 * @param model
	 *            DOCUMENT ME!
	 */
	public void setModel(ListModel model) {
		// modelo de la lista
		modelo = model;
		getLista().setModel(modelo);
	}

	public void addSelectionListener(ListSelectorListener listener) {
		getSelectionListeners().add(listener);
	}

	public void removeSelectionListener(ListSelectorListener listener) {
		getSelectionListeners().remove(listener);
	}

	private void callIndexesRemoved(int[] indices) {
		for (int i = 0; i < selectionListeners.size(); i++) {
			((ListSelectorListener) selectionListeners.get(i))
					.indexesRemoved(indices);
		}
	}

	private void callIndexesSelected(int[] indices) {
		for (int i = 0; i < selectionListeners.size(); i++) {
			((ListSelectorListener) selectionListeners.get(i))
					.indexesSelected(indices);
		}
	}

	private void callNewElement(String name) {
		for (int i = 0; i < selectionListeners.size(); i++) {
			((ListSelectorListener) selectionListeners.get(i)).newElement(name);
		}
	}

	/**
	 * This method initializes btnAceptar
	 * 
	 * @return JButton
	 */
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton();
			btnAceptar.setText(PluginServices.getText(this, "Seleccionar"));
			btnAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int[] indices = lista.getSelectedIndices();

					if (indices.length != 0)
						callIndexesSelected(indices);
				}
			});
		}

		return btnAceptar;
	}

	/**
	 * @return
	 */
	private ArrayList getSelectionListeners() {
		return selectionListeners;
	}

	/**
	 * This method initializes btnEliminar
	 * 
	 * @return JButton
	 */
	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton();
			btnEliminar.setText(PluginServices.getText(this, "Eliminar"));
			// btnEliminar.setPreferredSize(new java.awt.Dimension(102,26));
			btnEliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int[] indices = lista.getSelectedIndices();

					if (indices.length != 0)
						callIndexesRemoved(indices);
				}
			});
		}
		return btnEliminar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(null);

			jPanel.add(getJLabel(), null);
			jPanel.add(getTxtGuardar(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(530, 100));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "Guardar_el_zoom_actual"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getJPanel3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(530, 220));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, PluginServices.getText(this,
							"Recuperar_y_eliminar_otros_zoom"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getJPanel2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setBounds(7, 35, 248, 16);
			jLabel.setText(PluginServices.getText(this,
					"Nombre_que_se_le_dara_al_zoom") + ":");
		}
		return jLabel;
	}

	/**
	 * This method initializes txtGuardar
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtGuardar() {
		if (txtGuardar == null) {
			txtGuardar = new javax.swing.JTextField();
			txtGuardar.setBounds(250, 34, 269, 20);
			txtGuardar.setPreferredSize(new java.awt.Dimension(330, 20));
		}
		return txtGuardar;
	}

	/**
	 * This method initializes btnGuardar
	 * 
	 * @return JButton
	 */
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton();
			btnGuardar.setText(PluginServices.getText(this, "Guardar"));
			btnGuardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String text = txtGuardar.getText().trim();
					if (text.length() > 0)
						callNewElement(text);
				}
			});
		}
		return btnGuardar;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		if (m_viewinfo == null) {
			m_viewinfo = new WindowInfo(WindowInfo.MODELESSDIALOG);
			m_viewinfo.setWidth(this.getWidth() + 8);
			m_viewinfo.setHeight(this.getHeight());
			m_viewinfo.setTitle(PluginServices.getText(this, "Encuadre"));
		}
		return m_viewinfo;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
			jPanel2.setBounds(new java.awt.Rectangle(11, 180, 508, 33));
			jPanel2.add(getBtnAceptar(), null);
			jPanel2.add(getBtnEliminar(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
			jPanel3.setBounds(new java.awt.Rectangle(8, 59, 511, 33));
			jPanel3.add(getBtnGuardar(), null);
		}
		return jPanel3;
	}

	public Object getWindowProfile() {
		return WindowInfo.TOOL_PROFILE;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
