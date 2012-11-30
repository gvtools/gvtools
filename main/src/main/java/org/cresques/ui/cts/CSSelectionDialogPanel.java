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
package org.cresques.ui.cts;

import javax.swing.JPanel;

import org.cresques.Messages;
import org.cresques.ui.DefaultDialogPanel;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

/**
 * Dialogo para abrir fichero.
 * 
 * @author "Luis W. Sevilla" <sevilla_lui@gva.es>
 */
public class CSSelectionDialogPanel extends DefaultDialogPanel {
	final private static long serialVersionUID = -3370601314380922368L;

	public CSSelectionDialogPanel() {
		super();
		init();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void init() {
		this.setBounds(0, 0, 350, 260);
	}

	public CSSelectionPanel getProjPanel() {
		return (CSSelectionPanel) getContentPanel();
	}

	protected JPanel getContentPanel() {
		if (contentPane == null) {
			contentPane = new CSSelectionPanel(
					Messages.getText("reference_system"));
			contentPane.setBounds(14, 12, 280, 163);

			try {
				((CSSelectionPanel) contentPane).setCrs(CRS
						.decode("EPSG:32619"));
			} catch (NoSuchAuthorityCodeException e) {
				throw new RuntimeException("bug", e);
			} catch (FactoryException e) {
				throw new RuntimeException("bug", e);
			}
		}

		return contentPane;
	}
}
