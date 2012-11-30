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
package com.iver.cit.gvsig.project.documents.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.gvsig.gui.beans.AcceptCancelPanel;
import org.gvsig.tools.file.PathGenerator;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.ProjectExtension;
import com.iver.cit.gvsig.project.Project;

/**
 * Propiedades del proyecto
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class ProjectProperties extends JPanel implements IWindow {
	private Project project = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JTextField txtName = null;
	private javax.swing.JTextField txtPath = null;
	private javax.swing.JTextField txtCreationDate = null;
	private javax.swing.JTextField txtModificationDate = null;
	private javax.swing.JTextField txtOwner = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JTextArea txtComments = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel lblColor = null;
	private javax.swing.JButton btnColor = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private JRadioButton rbAbsolutePath = null;

	/**
	 * This is the default constructor
	 * 
	 * @param owner
	 *            Frame padre del di�logo
	 * @param p
	 *            Proyecto cuyos datos se muestran en el di�logo
	 */
	public ProjectProperties(Project p) {
		project = p;
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		setLayout(new java.awt.BorderLayout());
		add(getJPanel(), java.awt.BorderLayout.CENTER);

		getTxtName().setText(project.getName());

		String path = ProjectExtension.getPath();

		if (path != null) {
			File f = new File(path);
			getTxtPath().setText(f.toString());
		} else {
			getTxtPath().setText("");
		}

		getTxtOwner().setText(project.getOwner());
		getTxtComments().setText(project.getComments());
		getTxtCreationDate().setText(project.getCreationDate());
		getTxtModificationDate().setText(project.getModificationDate());
		getRbAbsolutePath().setSelected(project.isAbsolutePath());

		getLblColor().setBackground(project.getSelectionColor());
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.add(getJLabel(), null);
			jPanel.add(getTxtName(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getTxtPath(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getTxtCreationDate(), null);
			jPanel.add(getJLabel3(), null);
			jPanel.add(getTxtModificationDate(), null);
			jPanel.add(getJLabel4(), null);
			jPanel.add(getTxtOwner(), null);
			jPanel.add(getRbAbsolutePath(), null);
			jPanel.add(getJLabel5(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getJLabel6(), null);
			jPanel.add(getLblColor(), null);
			jPanel.add(getBtnColor(), null);
			java.awt.event.ActionListener okAction = new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					project.setName(txtName.getText());
					project.setOwner(txtOwner.getText());
					project.setComments(txtComments.getText());
					project.setSelectionColor(lblColor.getBackground());
					project.setIsAbsolutePath(rbAbsolutePath.isSelected());
					PathGenerator pg = PathGenerator.getInstance();
					pg.setIsAbsolutePath(rbAbsolutePath.isSelected());
					PluginServices.getMDIManager().closeWindow(
							ProjectProperties.this);
				}
			}, cancelAction = new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PluginServices.getMDIManager().closeWindow(
							ProjectProperties.this);
				}
			};
			jPanel.add(new AcceptCancelPanel(okAction, cancelAction));
			jPanel.setPreferredSize(new Dimension(500, 600));

		}

		return jPanel;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(PluginServices.getText(this, "nombre_sesion") + ":");
			jLabel.setPreferredSize(new java.awt.Dimension(199, 16));
		}

		return jLabel;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText(PluginServices.getText(this, "path") + ":");
			jLabel1.setPreferredSize(new java.awt.Dimension(199, 16));
		}

		return jLabel1;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(PluginServices.getText(this, "creation_date") + ":");
			jLabel2.setPreferredSize(new java.awt.Dimension(199, 16));
		}

		return jLabel2;
	}

	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(PluginServices.getText(this, "modification_date")
					+ ":");
			jLabel3.setPreferredSize(new java.awt.Dimension(199, 16));
		}

		return jLabel3;
	}

	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText(PluginServices.getText(this, "owner") + ":");
			jLabel4.setPreferredSize(new java.awt.Dimension(199, 16));
		}

		return jLabel4;
	}

	/**
	 * This method initializes txtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtName() {
		if (txtName == null) {
			txtName = new javax.swing.JTextField();
			txtName.setPreferredSize(new java.awt.Dimension(210, 20));
		}

		return txtName;
	}

	/**
	 * This method initializes txtPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtPath() {
		if (txtPath == null) {
			txtPath = new javax.swing.JTextField();
			txtPath.setPreferredSize(new java.awt.Dimension(210, 20));
			txtPath.setEditable(false);
			txtPath.setBackground(java.awt.Color.white);
		}

		return txtPath;
	}

	/**
	 * This method initializes txtCreationDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCreationDate() {
		if (txtCreationDate == null) {
			txtCreationDate = new javax.swing.JTextField();
			txtCreationDate.setPreferredSize(new java.awt.Dimension(210, 20));
			txtCreationDate.setEditable(false);
			txtCreationDate.setBackground(java.awt.Color.white);
		}

		return txtCreationDate;
	}

	/**
	 * This method initializes txtModificationDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtModificationDate() {
		if (txtModificationDate == null) {
			txtModificationDate = new javax.swing.JTextField();
			txtModificationDate
					.setPreferredSize(new java.awt.Dimension(210, 20));
			txtModificationDate.setEditable(false);
			txtModificationDate.setBackground(java.awt.Color.white);
		}

		return txtModificationDate;
	}

	/**
	 * This method initializes txtOwner
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtOwner() {
		if (txtOwner == null) {
			txtOwner = new javax.swing.JTextField();
			txtOwner.setPreferredSize(new java.awt.Dimension(210, 20));
		}

		return txtOwner;
	}

	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText(PluginServices.getText(this, "comentarios") + ":");
			jLabel5.setPreferredSize(new java.awt.Dimension(119, 16));
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}

		return jLabel5;
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
			txtComments.setColumns(20);
		}

		return txtComments;
	}

	// /**
	// * This method initializes btnOk
	// *
	// * @return javax.swing.JButton
	// */
	// private javax.swing.JButton getBtnOk() {
	// if (btnOk == null) {
	// btnOk = new javax.swing.JButton();
	// btnOk.setPreferredSize(new java.awt.Dimension(100, 30));
	// btnOk.setText(PluginServices.getText(this, "ok") + ":");
	//
	// btnOk.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// project.setName(txtName.getText());
	// project.setOwner(txtOwner.getText());
	// project.setComments(txtComments.getText());
	// project.setSelectionColor(lblColor.getBackground());
	// PluginServices.getMDIManager().closeView(ProjectProperties.this);
	// }
	// });
	// }
	//
	// return btnOk;
	// }
	//
	// /**
	// * This method initializes btnCancel
	// *
	// * @return javax.swing.JButton
	// */
	// private javax.swing.JButton getBtnCancel() {
	// if (btnCancel == null) {
	// btnCancel = new javax.swing.JButton();
	// btnCancel.setPreferredSize(new java.awt.Dimension(100, 30));
	// btnCancel.setText(PluginServices.getText(this, "cancel") + ":");
	//
	// btnCancel.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// PluginServices.getMDIManager().closeView(ProjectProperties.this);
	// }
	// });
	// }
	//
	// return btnCancel;
	// }

	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText(PluginServices.getText(this, "selection_color")
					+ ":");
			jLabel6.setPreferredSize(new java.awt.Dimension(190, 16));
		}

		return jLabel6;
	}

	/**
	 * This method initializes lblColor
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getLblColor() {
		if (lblColor == null) {
			lblColor = new javax.swing.JLabel();

			// lblColor.setText("");
			lblColor.setPreferredSize(new java.awt.Dimension(30, 16));
			lblColor.setOpaque(true);
		}

		return lblColor;
	}

	/**
	 * This method initializes btnColor
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnColor() {
		if (btnColor == null) {
			btnColor = new javax.swing.JButton();
			btnColor.setPreferredSize(new java.awt.Dimension(34, 16));
			btnColor.setText("...");

			btnColor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color ret = JColorChooser.showDialog(
							ProjectProperties.this,
							PluginServices.getText(this, "selection_color"),
							lblColor.getBackground());

					if (ret != null) {
						lblColor.setBackground(ret);
					}
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(420, 70));
		}

		return jScrollPane;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
		m_viewinfo.setWidth(450);
		m_viewinfo.setHeight(300);
		m_viewinfo.setTitle(PluginServices.getText(this, "propiedades_sesion"));
		return m_viewinfo;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

	/**
	 * This method initializes rbAbsolutePath
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAbsolutePath() {
		if (rbAbsolutePath == null) {
			rbAbsolutePath = new JRadioButton();
			rbAbsolutePath.setText(PluginServices.getText(this,
					"save_absolute_path"));
			rbAbsolutePath.setPreferredSize(new Dimension(420, 20));
		}
		return rbAbsolutePath;
	}
}

// @jve:visual-info decl-index=0 visual-constraint="10,10"
