/*
 * Created on 24-may-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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
package com.iver.cit.gvsig.project.documents.view.legend.edition.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gvsig.legend.Interval;
import org.gvsig.legend.impl.NumericInterval;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * DOCUMENT ME!
 * 
 * @author fjp To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelEditInterval extends JPanel implements IWindow {
	private JButton jButton = null;
	// private FIntervalCellEditor intervaleditor;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField m_txtMin1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel1 = null;
	private JTextField m_txtMax1 = null;
	private boolean ok = false;

	/**
	 * This is the default constructor
	 */
	public PanelEditInterval() {
		super();
		initialize();
		// intervaleditor=ice;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param i
	 *            DOCUMENT ME!
	 */
	public void setInterval(Interval i) {
		ok = false;
		m_txtMin1.setText(String.valueOf(i.getMin()));
		m_txtMax1.setText(String.valueOf(i.getMax()));
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Interval getInterval() {
		double from = 0;
		double to = 0;
		try {
			from = Double.parseDouble(m_txtMin1.getText());
			to = Double.parseDouble(m_txtMax1.getText());

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					PluginServices.getText(this, "Formato_de_numero_erroneo")
							+ ".");
		}
		Interval i = new NumericInterval(from, to);
		return i;
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setSize(316, 124);
		this.add(getJPanel1(), null);
		this.add(getJPanel2(), null);
		this.add(getJPanel(), null);

	}

	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
		m_viewinfo.setTitle(PluginServices.getText(this, "Intervalo"));
		m_viewinfo.setWidth(this.getWidth());
		m_viewinfo.setHeight(this.getHeight());
		return m_viewinfo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(PluginServices.getText(this, "aceptar"));
			jButton.setPreferredSize(new java.awt.Dimension(90, 26));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// intervaleditor.setCurrentInterval(getFInterval());
					ok = true;
					PluginServices.getMDIManager().closeWindow(
							PanelEditInterval.this);
				}
			});
		}
		return jButton;
	}

	public boolean isOK() {
		return ok;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setPreferredSize(new java.awt.Dimension(140, 36));
			jPanel.add(getJButton(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText("Valor minimo");
			jPanel1 = new JPanel();
			jPanel1.add(jLabel, null);
			jPanel1.add(getM_txtMin1(), null);

		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getM_txtMin1() {
		if (m_txtMin1 == null) {
			m_txtMin1 = new JTextField();
			m_txtMin1.setPreferredSize(new java.awt.Dimension(100, 20));
		}
		return m_txtMin1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Valor maximo");
			jPanel2 = new JPanel();
			jPanel2.add(jLabel1, null);
			jPanel2.add(getM_txtMax1(), null);

		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getM_txtMax1() {
		if (m_txtMax1 == null) {
			m_txtMax1 = new JTextField();
			m_txtMax1.setPreferredSize(new java.awt.Dimension(100, 20));
		}
		return m_txtMax1;
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
