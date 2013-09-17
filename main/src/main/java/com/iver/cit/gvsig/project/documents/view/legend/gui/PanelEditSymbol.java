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
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.geotools.styling.Symbolizer;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Panel creado para editar el s�mbolo seleccionado.
 * 
 * @author Vicente Caballero Navarro
 */
public class PanelEditSymbol extends JPanel implements IWindow {
	private SingleSymbol panel = null;
	private JButton bAceptar = null; // @jve:decl-index=0:visual-constraint="198,310"
	private JPanel panelbutton = null;
	private boolean ok = false;

	// private FSymbol symbol;

	public PanelEditSymbol() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(450, 343);
		this.add(getPanel(), java.awt.BorderLayout.NORTH);
		this.add(getPanelbutton(), BorderLayout.SOUTH);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG);
		m_viewinfo.setTitle(PluginServices.getText(this, "simbolo"));
		m_viewinfo.setWidth(getWidth() + 10);
		m_viewinfo.setHeight(getHeight());
		return m_viewinfo;
	}

	/**
	 * This method initializes panel
	 * 
	 * @return javax.swing.JPanel
	 */
	private SingleSymbol getPanel() {
		if (panel == null) {
			panel = new SingleSymbol();
			panel.setPreferredSize(new Dimension(400, 300));
			panel.setVisible(true);
		}
		return panel;
	}

	/**
	 * This method initializes bAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBAceptar() {
		if (bAceptar == null) {
			bAceptar = new JButton();
			bAceptar.setPreferredSize(new java.awt.Dimension(80, 20));
			bAceptar.setText(PluginServices.getText(this, "Aceptar"));
			bAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = true;
					PluginServices.getMDIManager().closeWindow(
							PanelEditSymbol.this);
				}
			});
			bAceptar.setSize(35, 20);
		}
		return bAceptar;
	}

	public Symbolizer getSymbol() {
		return panel.getSymbol();
	}

	public void setSymbol(Symbolizer s) {
		ok = false;
		panel.setSymbol(s);
	}

	public boolean isOK() {
		return ok;
	}

	/**
	 * This method initializes panelbutton
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelbutton() {
		if (panelbutton == null) {
			panelbutton = new JPanel();
			panelbutton.add(getBAceptar(), null);
		}
		return panelbutton;
	}

	public void setShapeType(Class<? extends Geometry> shapeType) {
		getPanel().setShapeType(shapeType);
	}

	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
