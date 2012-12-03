/*
 * Created on 10-mar-2005
 *
 * gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, 
USA.
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
package com.iver.core.mdiManager;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.iver.utiles.console.JConsole;

public class ComplexDesktopPane extends JPanel {

	private JSplitPane jSplitPane = null;
	private JDesktopPane jDesktopPane = null;
	private JConsole jConsole = null;

	/**
	 * This is the default constructor
	 */
	public ComplexDesktopPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setResizeWeight(0.8);
			jSplitPane.setTopComponent(getDesktopPane());
			jSplitPane.setBottomComponent(getJConsole());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jDesktopPane
	 * 
	 * @return javax.swing.JDesktopPane
	 */
	public JDesktopPane getDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
		}
		return jDesktopPane;
	}

	/**
	 * This method initializes jConsole
	 * 
	 * @return com.iver.utiles.console.JConsole
	 */
	private JConsole getJConsole() {
		if (jConsole == null) {
			jConsole = new JConsole();
		}
		return jConsole;
	}
}
