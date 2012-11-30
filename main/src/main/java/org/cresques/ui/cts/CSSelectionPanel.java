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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.cresques.Messages;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

//import es.gva.cit.geoexplorer.ui.LoadableComboBox;

/**
 * Panel de edici�n de Sistemas de referencia
 * 
 * @author "Luis W. Sevilla" <sevilla_lui@gva.es>
 */
public class CSSelectionPanel extends JPanel implements IWindow {
	final private static long serialVersionUID = -3370601314380922368L;
	private static final Logger logger = Logger
			.getLogger(CSSelectionPanel.class);
	private JList<CRSInfo> lstCodes = null;
	private JTextField txtFilter;
	private FilterModel filterModel;
	private JPanel pnlButtons;
	private JButton btnCancel;
	private JButton btnOk;
	private JPanel pnlFilter;
	private boolean okPressed = false;
	private JPanel pnlCenter;

	/**
	 * Constructor de la clase.
	 */
	public CSSelectionPanel(String title) {
		super();

		if (title == null) {
			// tit = "Sistema de referencia";
			// TODO: add com.iver.andami.PluginServices to this project
			// change all the labels from fix text got from the
			// internationalitation
			title = Messages.getText("reference_system");
			if (title == null)
				title = "Reference System";
		}

		initialize();
	}

	/**
	 * Inicializa el panel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private void initialize() {
		setLayout(new BorderLayout());
		add(getCenterPanel(), BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getCenterPanel() {
		if (pnlCenter == null) {
			pnlCenter = new JPanel();
			pnlCenter.setLayout(new BorderLayout());
			pnlCenter.add(new JScrollPane(getLstCodes()), BorderLayout.CENTER);
			pnlCenter.add(getPnlFilter(), BorderLayout.SOUTH);
		}

		return pnlCenter;
	}

	private JPanel getButtonPanel() {
		if (pnlButtons == null) {
			pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			pnlButtons.add(getAcceptButton(), c);
			c.gridx = 1;
			pnlButtons.add(getCancelButton(), c);
		}

		return pnlButtons;
	}

	public JButton getAcceptButton() {
		if (btnOk == null) {
			btnOk = new JButton("Aceptar");
			btnOk.setText(Messages.getText("Aceptar"));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PluginServices.getMDIManager().closeWindow(
							CSSelectionPanel.this);
					okPressed = true;
				}
			});
		}

		return btnOk;
	}

	public JButton getCancelButton() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancelar");
			btnCancel.setText(Messages.getText("Cancelar"));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PluginServices.getMDIManager().closeWindow(
							CSSelectionPanel.this);
					okPressed = false;
				}
			});
		}

		return btnCancel;
	}

	private JPanel getPnlFilter() {
		if (pnlFilter == null) {
			pnlFilter = new JPanel();
			pnlFilter.add(new JLabel("Filter:"));
			pnlFilter.add(getTxtFilter());
		}

		return pnlFilter;
	}

	private JTextField getTxtFilter() {
		if (txtFilter == null) {
			txtFilter = new JTextField(30);
			txtFilter.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent documentEvent) {
					changedUpdate(documentEvent);
				}

				@Override
				public void insertUpdate(DocumentEvent documentEvent) {
					changedUpdate(documentEvent);
				}

				@Override
				public void changedUpdate(DocumentEvent documentEvent) {
					filterModel.refilter(txtFilter.getText());
				}
			});

		}

		return txtFilter;
	}

	public void setCrs(CoordinateReferenceSystem crs) {
		getLstCodes().setSelectedValue(crs, true);
	}

	private JList<CRSInfo> getLstCodes() {
		if (lstCodes == null) {
			lstCodes = new JList<CRSInfo>();
			filterModel = new FilterModel();
			lstCodes.setModel(filterModel);
			lstCodes.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						private CRSInfo previous;

						private void setPrevious() {
							if (previous != null) {
								lstCodes.setSelectedValue(previous, false);
								previous = null;
							}
						}

						@Override
						public void valueChanged(ListSelectionEvent event) {
							if (!event.getValueIsAdjusting()) {
								try {
									CRSInfo selectedValue = lstCodes
											.getSelectedValue();
									CoordinateReferenceSystem crs = selectedValue
											.getCRS();
									System.out.println(crs);
									previous = selectedValue;
								} catch (NoSuchAuthorityCodeException e) {
									setPrevious();
								} catch (FactoryException e) {
									setPrevious();
								}

							}
						}
					});
		}

		return lstCodes;
	}

	public CoordinateReferenceSystem getCrs() {
		try {
			return lstCodes.getSelectedValue().getCRS();
		} catch (NoSuchAuthorityCodeException e) {
			// Should never fail since the CRS is created when selected
			throw new RuntimeException("bug", e);
		} catch (FactoryException e) {
			// Should never fail since the CRS is created when selected
			throw new RuntimeException("bug", e);
		}
	}

	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.add(new CSSelectionPanel("tits everywhere"));
		frm.setLocationRelativeTo(null);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.pack();
		frm.setVisible(true);
	}

	@Override
	public WindowInfo getWindowInfo() {
		WindowInfo m_viewinfo = new WindowInfo(WindowInfo.MODALDIALOG
				| WindowInfo.RESIZABLE);
		m_viewinfo.setTitle(PluginServices.getText(this,
				"selecciona_sistema_de_referencia"));
		m_viewinfo.setWidth(500);
		m_viewinfo.setHeight(300);
		return m_viewinfo;
	}

	@Override
	public Object getWindowProfile() {
		return WindowInfo.DIALOG_PROFILE;
	}

	public boolean isOkPressed() {
		return okPressed;
	}

	private class FilterModel extends AbstractListModel<CRSInfo> {
		private static final long serialVersionUID = 1L;
		private ArrayList<CRSInfo> items = new ArrayList<CRSInfo>();
		private ArrayList<CRSInfo> filterItems = new ArrayList<CRSInfo>();

		public FilterModel() {
			for (CRSAuthorityFactory factory : ReferencingFactoryFinder
					.getCRSAuthorityFactories(null)) {
				try {
					Set<String> codes = factory
							.getAuthorityCodes(CoordinateReferenceSystem.class);
					for (String code : codes) {
						items.add(new CRSInfo(factory.getDescriptionText(code)
								.toString(), code, factory));
					}
				} catch (FactoryException e) {
					logger.debug("Cannot instantiate CRS factory", e);
				}
			}

			refilter("");
		}

		public CRSInfo getElementAt(int index) {
			if (index < filterItems.size()) {
				return filterItems.get(index);
			} else {
				return null;
			}
		}

		public int getSize() {
			return filterItems.size();
		}

		private void refilter(String text) {
			String[] texts = text.split("\\s");
			for (int i = 0; i < texts.length; i++) {
				texts[i] = texts[i].trim().toLowerCase();
			}
			filterItems.clear();
			for (CRSInfo filterItem : items) {
				boolean matches = true;
				for (String match : texts) {
					if (filterItem.getSearchString().toLowerCase()
							.indexOf(match) == -1) {
						matches = false;
						break;
					}
				}
				if (matches) {
					filterItems.add(filterItem);
				}
			}
			fireContentsChanged(this, 0, getSize());
		}
	}

	private class CRSInfo {
		private String description;
		private String code;
		private CRSAuthorityFactory factory;

		public CRSInfo(String description, String code,
				CRSAuthorityFactory factory) {
			this.description = description;
			this.code = code;
			this.factory = factory;
		}

		public CoordinateReferenceSystem getCRS()
				throws NoSuchAuthorityCodeException, FactoryException {
			return factory.createCoordinateReferenceSystem(code);
		}

		private String getSearchString() {
			return code + " " + description;
		}

		@Override
		public String toString() {
			return description;
		}
	}
}
