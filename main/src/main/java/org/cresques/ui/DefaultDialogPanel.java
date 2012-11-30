/*
 * Cresques Mapping Suite. Graphic Library for constructing mapping applications.
 *
 * Copyright (C) 2004-5.
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
 * cresques@gmail.com
 */
package org.cresques.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cresques.Messages;

/**
 * @author Luis W. Sevilla (sevilla_lui@gva.es)
 */
public class DefaultDialogPanel extends JPanel implements ComponentListener {
	final private static long serialVersionUID = -3370601314380922368L;
	protected JPanel contentPane = null;
	private JPanel tabPane = null;
	protected JPanel buttonPane = null;
	private JButton acceptButton = null;
	private JButton cancelButton = null;
	private JButton applyButton = null;
	private int flag = 0;
	protected int cWidth = 0, difWidth = 0;
	protected int cHeight = 0, difHeight = 0;
	protected JPanel pButton = null;

	/**
	 * Constructor
	 * 
	 * @param init
	 */
	public DefaultDialogPanel(boolean init) {
		if (init)
			initialize();
	}

	/**
	 * Constructor
	 */
	public DefaultDialogPanel() {
		super();
		this.initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		// setBounds(0,0,321,230);
		// javax.swing.BoxLayout(jContentPane, javax.swing.BoxLayout.Y_AXIS);
		// jContentPane.setLayout(new java.awt.GridLayout(2,1));
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.insets = new java.awt.Insets(4, 0, 0, 0);
		gridBagConstraints11.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 2, 0);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		setLayout(new GridBagLayout());
		setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// jContentPane.setSize(30, 24);
		this.setPreferredSize(new java.awt.Dimension(320, 165));
		this.add(getTabPanel(), gridBagConstraints);
		this.add(getPButton(), gridBagConstraints11);
		this.addComponentListener(this);

	}

	/**
	 * Obtiene el panel general
	 * 
	 * @return
	 */
	protected JPanel getContentPanel() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(new GridBagLayout());
			contentPane.setBounds(6, 200, 310, 125);
			contentPane.setPreferredSize(new java.awt.Dimension(310, 100));
		}

		return contentPane;
	}

	public JPanel getTabPanel() {
		if (tabPane == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new java.awt.Insets(5, 0, 0, 0);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;
			tabPane = new JPanel();
			tabPane.setLayout(new GridBagLayout());

			// tabPane.setBounds(6, 7, 309, 189);
			tabPane.setBorder(new javax.swing.border.SoftBevelBorder(
					javax.swing.border.SoftBevelBorder.RAISED));
			tabPane.add(getContentPanel(), gridBagConstraints2);
		}

		return tabPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPButton() {
		if (pButton == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			pButton = new JPanel();
			pButton.setPreferredSize(new java.awt.Dimension(320, 25));
			pButton.setLayout(flowLayout1);
			pButton.add(getButtonPanel(), null);
		}
		return pButton;
	}

	/**
	 * Obtiene el panel que contiene los botones de Aceptar, Cancelar y Aplicar
	 * 
	 * @return
	 */
	protected JPanel getButtonPanel() {
		if (buttonPane == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.insets = new java.awt.Insets(0, 3, 0, 0);
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.insets = new java.awt.Insets(0, 3, 0, 3);
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 3);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			buttonPane = new JPanel();
			buttonPane.setLayout(new GridBagLayout());
			buttonPane.add(getAcceptButton(), gridBagConstraints1);
			buttonPane.add(getCancelButton(), gridBagConstraints3);
			buttonPane.add(getApplyButton(), gridBagConstraints4);
		}

		return buttonPane;
	}

	/**
	 * This method initializes Accept button
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getAcceptButton() {
		if (acceptButton == null) {
			acceptButton = new JButton("Aceptar");
			acceptButton.setText(Messages.getText("Aceptar"));
		}

		return acceptButton;
	}

	/**
	 * This method initializes Cancel Button
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancelar");
			cancelButton.setText(Messages.getText("Cancelar"));
		}

		return cancelButton;
	}

	/**
	 * This method initializes Apply Button
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getApplyButton() {
		if (applyButton == null) {
			applyButton = new JButton(Messages.getText("Aplicar"));
		}

		return applyButton;
	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentResized(ComponentEvent e) {
		if (e.getSource() == this)
			this.resizeWindow();
	}

	public void resizeWindow() {
		if (flag == 0) {
			cWidth = this.getSize().width;
			cHeight = this.getSize().height;
			flag++;
		}

		int nWidth = getWidth();
		int nHeight = getHeight();
		difWidth = nWidth - cWidth;
		difHeight = nHeight - cHeight;
		this.tabPane.setSize(this.tabPane.getSize().width + difWidth,
				this.tabPane.getSize().height + difHeight);
		this.tabPane.setLocation(this.tabPane.getLocation().x - difWidth / 2,
				this.tabPane.getLocation().y - difHeight / 2);

		this.contentPane.setSize(this.contentPane.getSize().width + difWidth,
				this.contentPane.getSize().height + difHeight);
		// this.pButton.setSize(this.getPButton().getWidth() + difWidth/2, 25);
		// this.buttonPane.setLocation(this.buttonPane.getLocation().x +
		// difWidth/2, this.buttonPane.getLocation().y + difHeight/2);
		this.pButton.setLocation(this.pButton.getLocation().x + difWidth / 2,
				this.pButton.getLocation().y + difHeight / 2);

	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

} // @jve:decl-index=0:visual-constraint="12,13"
