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
package com.iver.cit.gvsig.gui.selectionByTheme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.utiles.swing.JComboBox;

public class SelectionByTheme extends JPanel implements IWindow {
	public static final int EQUALS = 0;
	public static final int DISJOINT = 1;
	public static final int INTERSECTS = 2;
	public static final int TOUCHES = 3;
	public static final int CROSSES = 4;
	public static final int WITHIN = 5;
	public static final int CONTAINS = 6;
	public static final int OVERLAPS = 7;
	public final String[] textosAcciones = new String[] {
			PluginServices.getText(this, "Sean_iguales_a"), // "sean iguales a",
			PluginServices.getText(this, "Sean_disjuntos_a"), // "sean disjuntos a",
			PluginServices.getText(this, "Intersecten_con"), // "intersecten con",
			PluginServices.getText(this, "Toquen"), // "toquen",
			PluginServices.getText(this, "Crucen_con"), // "crucen con",
			PluginServices.getText(this, "Contengan"), // "contengan",
			PluginServices.getText(this, "Esten_contenidos_en"), // "est�n contenidos en",
			PluginServices.getText(this, "Se_superponen_a"), // "se superponen a"
	};

	private SelectionByThemeModel dataSource = null;

	private ArrayList listeners = new ArrayList();

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JComboBox cmbAction = null;
	private JLabel jLabel1 = null;
	private JComboBox<Layer> cmbCapas = null;
	private JButton btnNew = null;
	private JButton btnAdd = null;
	private JButton btnFrom = null;
	private JButton btnCancel = null;

	/**
	 * This is the default constructor
	 */
	public SelectionByTheme() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout(10, 10));
		this.add(new JLabel(), BorderLayout.NORTH);
		this.add(new JLabel(), BorderLayout.WEST);
		this.add(getJPanel(), BorderLayout.CENTER);
		this.add(getJPanel1(), BorderLayout.EAST);
		this.add(new JLabel(), BorderLayout.SOUTH);
		this.setSize(540, 95);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setVgap(30);
			jPanel.setLayout(new GridLayout(4, 2, 5, 5));
			jPanel.add(getJLabel(), null);
			jPanel.add(getCmbAction(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getCmbCapas(), null);

		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridLayout(4, 1, 5, 5));
			jPanel1.add(getBtnNew(), null);
			jPanel1.add(getBtnAdd(), null);
			jPanel1.add(getBtnFrom(), null);
			jPanel1.add(getBtnCancel(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(200, 220));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return JLabel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText(PluginServices.getText(this,
					"Seleccionar_de_las_capas_activas_los_elementos_que")
					+ "... ");
		}
		return jLabel;
	}

	/**
	 * This method initializes cmbAction
	 * 
	 * @return JComboBox
	 */
	private JComboBox getCmbAction() {
		if (cmbAction == null) {
			cmbAction = new JComboBox();
			cmbAction.setPreferredSize(new java.awt.Dimension(200, 20));
			DefaultComboBoxModel model = new DefaultComboBoxModel(
					textosAcciones);
			cmbAction.setModel(model);
		}
		return cmbAction;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText(PluginServices.getText(this,
					"Elementos_seleccionados_de_la_capa"));
		}
		return jLabel1;
	}

	/**
	 * This method initializes cmbCapas
	 * 
	 * @return JComboBox
	 */
	private JComboBox<Layer> getCmbCapas() {
		if (cmbCapas == null) {
			cmbCapas = new JComboBox<Layer>();
			cmbCapas.setPreferredSize(new java.awt.Dimension(200, 20));
		}
		return cmbCapas;
	}

	/**
	 * This method initializes btnNew
	 * 
	 * @return JButton
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setText(PluginServices.getText(this, "Nuevo_conjunto"));
			btnNew.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					callNewListeners((Layer) cmbCapas.getSelectedItem(),
							cmbAction.getSelectedIndex());
				}
			});
		}
		return btnNew;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText(PluginServices.getText(this, "Anadir_al_conjunto"));
			btnAdd.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					callAddToListeners((Layer) cmbCapas.getSelectedItem(),
							cmbAction.getSelectedIndex());
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnFrom
	 * 
	 * @return JButton
	 */
	private JButton getBtnFrom() {
		if (btnFrom == null) {
			btnFrom = new JButton();
			btnFrom.setText(PluginServices.getText(this,
					"Seleccionar_del_conjunto"));
			btnFrom.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnFrom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					callFromListeners((Layer) cmbCapas.getSelectedItem(),
							cmbAction.getSelectedIndex());
				}
			});
		}
		return btnFrom;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText(PluginServices.getText(this, "Cancel"));
			btnCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PluginServices.getMDIManager().closeWindow(
							SelectionByTheme.this);
				}
			});
		}
		return btnCancel;
	}

	/**
	 * @return
	 */
	public SelectionByThemeModel getModel() {
		return dataSource;
	}

	/**
	 * @param source
	 */
	public void setModel(SelectionByThemeModel source) {
		dataSource = source;
		DefaultComboBoxModel<Layer> model = new DefaultComboBoxModel<Layer>(
				dataSource.getLayers());
		cmbCapas.setModel(model);
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo vi = new WindowInfo(WindowInfo.MODALDIALOG);
		vi.setWidth(this.getWidth() + 8);
		vi.setHeight(this.getHeight());
		vi.setTitle(PluginServices.getText(this, "Seleccion_por_capa"));
		return vi;
	}

	private void callNewListeners(Layer selection, int actionCode) {
		for (int i = 0; i < listeners.size(); i++) {
			SelectionByThemeListener l = (SelectionByThemeListener) listeners
					.get(i);
			l.newSet(dataSource.getSelected(), selection, actionCode);
		}

	}

	private void callAddToListeners(Layer selection, int actionCode) {
		for (int i = 0; i < listeners.size(); i++) {
			SelectionByThemeListener l = (SelectionByThemeListener) listeners
					.get(i);
			l.addToSet(dataSource.getSelected(), selection, actionCode);
		}

	}

	private void callFromListeners(Layer selection, int actionCode) {
		for (int i = 0; i < listeners.size(); i++) {
			SelectionByThemeListener l = (SelectionByThemeListener) listeners
					.get(i);
			l.fromSet(dataSource.getSelected(), selection, actionCode);
		}

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg0
	 * 
	 * @return
	 */
	public boolean addSelectionListener(SelectionByThemeListener arg0) {
		return listeners.add(arg0);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg0
	 * 
	 * @return
	 */
	public boolean removeSelectionListener(SelectionByThemeListener arg0) {
		return listeners.remove(arg0);
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

} // @jve:visual-info decl-index=0 visual-constraint="10,10"
