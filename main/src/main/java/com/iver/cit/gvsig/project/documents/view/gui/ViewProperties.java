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
package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.gvsig.gui.beans.AcceptCancelPanel;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.units.Unit;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.ProjectExtension;
import com.iver.cit.gvsig.gui.panels.CRSSelectPanel;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.ProjectView;
import com.iver.cit.gvsig.project.documents.view.ProjectViewFactory;

/**
 * Dialogo donde se muestran las propiedades de una vista
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class ViewProperties extends JPanel implements SingletonWindow {
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField txtName = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JTextField txtDate = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField txtOwner = null;
	private javax.swing.JLabel jLabel4 = null;
	private JComboBox<Unit> cmbMapUnits = null;
	private javax.swing.JLabel jLabel5 = null;
	private JComboBox<Unit> cmbDistanceUnits = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JTextArea txtComments = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel lblColor = null;

	private Color backColor = null;

	private JButton btnColor = null;
	private ProjectView view = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;

	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabelSep1 = null;
	protected CRSSelectPanel jPanelProj = null;
	private AcceptCancelPanel okCancelPanel = null;
	private boolean isAcceppted = false;
	private JLabel jLabel8 = null;
	private JComboBox<Unit> cmbDistanceArea = null;

	/**
	 * This is the default constructor
	 * 
	 * @param f
	 *            Frame padre del dialogo
	 * @param v
	 *            Vista que se representa
	 */
	public ViewProperties(ProjectView v) {
		view = v;
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setSize(386, 436);
		java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
		layFlowLayout3.setHgap(0);
		setLayout(layFlowLayout3);
		/*
		 * jLblProjName = new JLabel(); jLblProj = new JLabel();
		 * jLblProj.setText(view.getProjection().getAbrev());
		 * jLblProj.setPreferredSize(new java.awt.Dimension(180,20));
		 * jLblProjName.setText("Proyecci�n actual:");
		 * jLblProjName.setPreferredSize(new java.awt.Dimension(95,15));
		 */
		this.setPreferredSize(new java.awt.Dimension(365, 463));
		add(getJPanel(), null);
		add(getJPanel1(), null);
		add(getJPanel2(), null);
		add(getJPanel3(), null);
		add(getJLabelSep1(), null);
		this.add(getJPanelProj(), null);
		this.add(getJLabel6(), null);
		add(getJScrollPane(), null);

		this.add(getJLabel7(), null);
		this.add(getLblColor(), null);
		this.add(getBtnColor(), null);

		add(getJLabel3(), null);
		this.add(getOkCancelPanel(), null);
		txtName.setText(view.getName());
		txtDate.setText(view.getCreationDate());
		txtOwner.setText(view.getOwner());

		cmbMapUnits.setSelectedItem(view.getMapContext().getMapUnits());
		cmbDistanceUnits.setSelectedItem(view.getMapContext()
				.getDistanceUnits());
		cmbDistanceArea.setSelectedItem(view.getMapContext().getAreaUnits());
		txtComments.setText(view.getComment());

		lblColor.setBackground(view.getMapContext().getBackgroundColor());
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(PluginServices.getText(this, "nombre") + ":");
		}

		return jLabel;
	}

	private javax.swing.JLabel getJLabelSep1() {
		if (jLabelSep1 == null) {
			jLabelSep1 = new javax.swing.JLabel();
			jLabelSep1.setPreferredSize(new java.awt.Dimension(200, 10));
		}
		return jLabelSep1;
	}

	/**
	 * This method initializes txtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtName() {
		if (txtName == null) {
			txtName = new javax.swing.JTextField();
			txtName.setPreferredSize(new java.awt.Dimension(200, 20));
		}

		return txtName;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText(PluginServices.getText(this, "creation_date") + ":");
		}

		return jLabel1;
	}

	/**
	 * This method initializes txtDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtDate() {
		if (txtDate == null) {
			txtDate = new javax.swing.JTextField();
			txtDate.setPreferredSize(new java.awt.Dimension(200, 20));
			txtDate.setEditable(false);
			txtDate.setBackground(java.awt.Color.white);
		}

		return txtDate;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(PluginServices.getText(this, "owner") + ":");
		}

		return jLabel2;
	}

	/**
	 * This method initializes txtOwner
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtOwner() {
		if (txtOwner == null) {
			txtOwner = new javax.swing.JTextField();
			txtOwner.setPreferredSize(new java.awt.Dimension(200, 20));
		}

		return txtOwner;
	}

	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText(PluginServices.getText(this, "map_units") + ":");
			jLabel4.setName("jLabel4");
		}

		return jLabel4;
	}

	/**
	 * This method initializes cmbMapUnits
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox<Unit> getCmbMapUnits() {
		if (cmbMapUnits == null) {
			cmbMapUnits = new JComboBox<Unit>(Unit.values());
			cmbMapUnits.setRenderer(new UnitRenderer(false));
			cmbMapUnits.setPreferredSize(new java.awt.Dimension(200, 20));
			CoordinateReferenceSystem crs = view.getCrs();
			if (!(crs instanceof ProjectedCRS)) {
				cmbMapUnits.setSelectedItem(Unit.DEGREES); // degree
				cmbMapUnits.setEnabled(false);
			} else {
				cmbMapUnits
						.setSelectedItem(view.getMapContext().getMapUnits().name);
				cmbMapUnits.setEnabled(true);
			}
		}

		return cmbMapUnits;
	}

	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText(PluginServices.getText(this, "distance_units")
					+ ":");
			jLabel5.setName("jLabel5");
		}

		return jLabel5;
	}

	/**
	 * This method initializes cmbDistanceUnits
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox<Unit> getCmbDistanceUnits() {
		if (cmbDistanceUnits == null
				|| Unit.getDistanceNames().length > cmbDistanceUnits
						.getItemCount()) {
			cmbDistanceUnits = new JComboBox<Unit>(Unit.values());
			cmbDistanceUnits.setRenderer(new UnitRenderer(false));
			// cmbDistanceUnits = new javax.swing.JComboBox(getUnitsNames());
			cmbDistanceUnits.setPreferredSize(new java.awt.Dimension(200, 20));
			// cmbDistanceUnits.setEditable(false);
			// cmbDistanceUnits.setSelectedIndex(view.getMapContext().getViewPort().getDistanceUnits());
			// cmbDistanceUnits.addActionListener(new
			// java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// //view.getMapContext().getViewPort().setDistanceUnits(cmbDistanceUnits.getSelectedIndex());
			// }
			// });
		}

		return cmbDistanceUnits;
	}

	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText(PluginServices.getText(this, "comentarios") + ":");
			jLabel6.setPreferredSize(new java.awt.Dimension(340, 35));
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		}

		return jLabel6;
	}

	/**
	 * This method initializes txtComments
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getTxtComments() {
		if (txtComments == null) {
			txtComments = new javax.swing.JTextArea();
			txtComments.setRows(1);
			txtComments.setColumns(28);
		}

		return txtComments;
	}

	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText(PluginServices.getText(this, "background_color")
					+ ":");
			jLabel7.setPreferredSize(new java.awt.Dimension(190, 16));
		}

		return jLabel7;
	}

	/**
	 * This method initializes lblColor
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getLblColor() {
		if (lblColor == null) {
			lblColor = new javax.swing.JLabel();
			lblColor.setText("");
			lblColor.setPreferredSize(new java.awt.Dimension(30, 16));
			Color theColor = view.getMapContext().getBackgroundColor();
			backColor = theColor;
			if (theColor == null)
				theColor = Color.WHITE;
			lblColor.setBackground(theColor);
			lblColor.setOpaque(true);
		}

		return lblColor;
	}

	/**
	 * This method initializes btnColor
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColor() {
		if (btnColor == null) {
			btnColor = new JButton();

			btnColor.setText("...");

			btnColor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color ret = JColorChooser.showDialog(ViewProperties.this,
							PluginServices.getText(this, "background_color"),
							lblColor.getBackground());

					if (ret != null) {
						lblColor.setBackground(ret);
						backColor = ret;
					} else
						lblColor.setBackground(Color.WHITE);
				}
			});
		}

		return btnColor;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getTxtComments());
			jScrollPane.setPreferredSize(new java.awt.Dimension(340, 70));
		}

		return jScrollPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();

			/*
			 * java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			 * layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			 * layFlowLayout4.setVgap(9); jPanel.setLayout(layFlowLayout4);
			 */

			GridLayout layout = new GridLayout(3, 1);
			layout.setVgap(5);
			jPanel.setLayout(layout);

			jPanel.add(getJLabel(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(140, 80));
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

			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setHgap(5);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setLayout(layFlowLayout2);
			jPanel1.add(getTxtName(), null);
			jPanel1.add(getTxtDate(), null);
			jPanel1.add(getTxtOwner(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(210, 80));
		}

		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText(PluginServices.getText(this, "unidades_area"));
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(3);
			jPanel2 = new javax.swing.JPanel();

			jPanel2.setLayout(gridLayout);
			jPanel2.setPreferredSize(new java.awt.Dimension(140, 80));
			jPanel2.add(getJLabel4(), null);
			jPanel2.add(getJLabel5(), null);
			jPanel2.add(jLabel8, null);
		}

		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();

			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel3.setLayout(layFlowLayout6);
			jPanel3.add(getCmbMapUnits(), null);
			jPanel3.add(getCmbDistanceUnits(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(210, 80));
			jPanel3.add(getCmbDistanceArea(), null);
		}

		return jPanel3;
	}

	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("");
			jLabel3.setPreferredSize(new java.awt.Dimension(30, 0));
		}

		return jLabel3;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo();
		m_viewinfo.setTitle(PluginServices.getText(this, "propiedades_vista"));
		m_viewinfo.setHeight(410);
		m_viewinfo.setWidth(386);
		return m_viewinfo;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton / private JButton getJBtnChangeProj() { if
	 *         (jBtnChangeProj == null) { jBtnChangeProj = new JButton();
	 *         jBtnChangeProj.setText("...");
	 *         jBtnChangeProj.setPreferredSize(new java.awt.Dimension(34,16));
	 *         jBtnChangeProj.addActionListener(new
	 *         java.awt.event.ActionListener() { public void
	 *         actionPerformed(java.awt.event.ActionEvent e) { CSSelectionDialog
	 *         csSelect = new CSSelectionDialog();
	 *         csSelect.setProjection(view.getProjection());
	 * 
	 *         PluginServices.getMDIManager().addView(csSelect);
	 * 
	 *         if (csSelect.isOkPressed()) {
	 *         view.getMapContext().setProjection(csSelect.getProjection());
	 *         jLblProj.setText(view.getProjection().getAbrev()); } } }); }
	 *         return jBtnChangeProj; }
	 */
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private CRSSelectPanel getJPanelProj() {
		if (jPanelProj == null) {
			jPanelProj = CRSSelectPanel.getPanel(view.getCrs());
			jPanelProj.setPreferredSize(new java.awt.Dimension(330, 35));
			jPanelProj.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jPanelProj.isOkPressed()) {
						if (!(jPanelProj.getCurrentCrs() instanceof ProjectedCRS)) {
							// if
							// (getCmbMapUnits().getItemCount()==MapContext.NAMES.length)
							// {
							// getCmbMapUnits().addItem(PluginServices.getText(this,
							// Attributes.DEGREES));
							// }
							assert false;
							// gtrefactor
							// getCmbMapUnits().setSelectedItem(
							// PluginServices.getText(this,
							// Attributes.DEGREES));
							// getCmbMapUnits().setEnabled(false);
						} else {
							assert false;
							// gtrefactor
							// if (getCmbMapUnits().getSelectedItem().equals(
							// PluginServices.getText(this,
							// Attributes.DEGREES))) {
							// getCmbMapUnits().setSelectedIndex(1);
							// }
							// getCmbMapUnits().setEnabled(true);
						}
						view.setCrs(jPanelProj.getCurrentCrs());
					}
				}
			});

			// jPanelProj.add(jLblProjName, null);
			// jPanelProj.add(jLblProj, null);
			// jPanelProj.add(getJBtnChangeProj(), null);
		}
		return jPanelProj;
	}

	/**
	 * @see com.iver.andami.ui.mdiManager.SingletonWindow#getWindowModel()
	 */
	public Object getWindowModel() {
		return view;
	}

	/**
	 * This method initializes okCancelPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private AcceptCancelPanel getOkCancelPanel() {
		if (okCancelPanel == null) {
			ActionListener okAction, cancelAction;
			okAction = new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name = txtName.getText();
					if (name == null || name.length() == 0) {
						return;
					}
					Project project = ((ProjectExtension) PluginServices
							.getExtension(ProjectExtension.class)).getProject();
					ArrayList<ProjectDocument> views = project
							.getDocumentsByType(ProjectViewFactory.registerName);

					for (int i = 0; i < views.size(); i++) {
						if (view.equals(views.get(i))) {
							continue;
						}
						if (views.get(i).getName().equals(name)) {
							JOptionPane.showMessageDialog(
									(Component) PluginServices.getMainFrame(),
									PluginServices.getText(this,
											"elemento_ya_existe"));
							return;
						}

					}

					view.setName(name);
					view.setCreationDate(txtDate.getText());
					view.setOwner(txtOwner.getText());
					view.setComment(txtComments.getText());
					view.getMapContext().setMapUnits(
							(Unit) cmbMapUnits.getSelectedItem());
					view.getMapContext().setDistanceUnits(
							(Unit) cmbDistanceUnits.getSelectedItem());
					view.getMapContext().setAreaUnits(
							(Unit) cmbDistanceArea.getSelectedItem());
					view.setBackColor(backColor);
					isAcceppted = true;
					PluginServices.getMDIManager().closeWindow(
							ViewProperties.this);
				}
			};
			cancelAction = new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					isAcceppted = false;
					PluginServices.getMDIManager().closeWindow(
							ViewProperties.this);
				}
			};
			okCancelPanel = new AcceptCancelPanel(okAction, cancelAction);
			Dimension sz = this.getSize();
			sz.setSize((int) sz.getWidth() - 20, (int) sz.getHeight());
			okCancelPanel.setPreferredSize(sz);
		}
		return okCancelPanel;
	}

	// private String[] getUnitsNames() {
	// if (unitsNames == null) {
	// unitsNames = new String[MapContext.NAMES.length];
	// int i=0;
	// for (i=0;i<MapContext.NAMES.length;i++) {
	// unitsNames[i]=PluginServices.getText(this, MapContext.NAMES[i]);
	// }
	// //unitsNames[i]=PluginServices.getText(this, Attributes.DEGREES);
	// }
	// return unitsNames;
	// }

	public boolean isAcceppted() {
		return isAcceppted;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox<Unit> getCmbDistanceArea() {
		String[] names = Unit.getDistanceNames();
		if (cmbDistanceArea == null
				|| names.length > cmbDistanceArea.getItemCount()) {
			cmbDistanceArea = new JComboBox<Unit>(Unit.values());
			cmbDistanceArea.setRenderer(new UnitRenderer(true));
			cmbDistanceArea.setPreferredSize(new java.awt.Dimension(200, 20));
			cmbDistanceArea.setEditable(false);
			cmbDistanceArea
					.setSelectedItem(view.getMapContext().getAreaUnits().name);
		}

		return cmbDistanceArea;

	}

	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return WindowInfo.PROPERTIES_PROFILE;
	}

	private class UnitRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;
		private boolean square;

		public UnitRenderer(boolean square) {
			this.square = square;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Unit unit = (Unit) value;
			String text = PluginServices.getText(this, unit.name);
			if (square) {
				text += unit.getSquareSuffix();
			}

			return super.getListCellRendererComponent(list, text, index,
					isSelected, cellHasFocus);
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"