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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.help.Help;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.ProjectExtension;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.ProjectDocumentFactory;
import com.iver.cit.gvsig.project.documents.contextMenu.gui.DocumentContextMenu;
import com.iver.utiles.DefaultListModel;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * Clase principal del proyecto donde se puede operar para crear cualquier tipo
 * de documento.
 * 
 * @author Vicente Caballero Navarro
 */
public class ProjectWindow extends JPanel implements PropertyChangeListener,
		IWindow, SingletonWindow {
	private JPanel jPanel = null;
	private JRadioButton[] btnsDocuments = null;
	private ButtonGroup grupo = new ButtonGroup();
	private JPanel jPanel1 = null;
	private JList<ProjectDocument> lstDocs = null;
	private JPanel jPanel2 = null;
	private JButton btnNuevo = null;
	private JButton btnPropiedades = null;
	private JButton btnAbrir = null;
	private JButton btnBorrar = null;
	private JButton btnRenombrar = null;
	private JPanel jPanel3 = null;
	private JLabel jLabel = null;
	private JLabel lblNombreSesion = null;
	private JLabel jLabel1 = null;
	private JLabel lblGuardado = null;
	private JLabel jLabel2 = null;
	private JLabel lblFecha = null;
	private JButton btnImportar = null;
	private JButton btnExportar = null;
	private JButton btnEditar = null;
	// The properties of the Project Manager window (size, position, etc):
	private WindowInfo m_viewInfo = null;

	/** Proyecto representado en la vista */
	private Project p;

	private JScrollPane jScrollPane = null;
	private JPanel jPanel4 = null;
	private JScrollPane jScrollPane1 = null;

	/**
	 * This is the default constructor
	 * 
	 * @param project
	 *            Extension
	 */
	public ProjectWindow() {
		super();
		initialize();
		refreshControls();
		registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Help.show();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	/**
	 * Asigna el proyecto a la ventana
	 * 
	 * @param p
	 *            Proyecto con el que se operar� a trav�s de este di�logo
	 */
	public void setProject(Project p) {
		this.p = p;
		p.addPropertyChangeListener(this);
		refreshControls();
	}

	/**
	 * Activa los botones de la botonera del medio o los desactiva en funci�n de
	 * que est� seleccionado o no un elemento de proyecto en la lista del medio
	 */
	private void activarBotones() {
		if (lstDocs.getSelectedIndex() != -1) {
			btnAbrir.setEnabled(true);
			btnBorrar.setEnabled(true);
			if (lstDocs.getSelectedIndices().length == 1) {
				btnRenombrar.setEnabled(true);
				btnPropiedades.setEnabled(true);
			} else {
				btnRenombrar.setEnabled(false);
				btnPropiedades.setEnabled(false);
			}
		} else {
			btnAbrir.setEnabled(false);
			btnBorrar.setEnabled(false);
			btnRenombrar.setEnabled(false);
			btnPropiedades.setEnabled(false);
		}
	}

	/**
	 * Refresca la lista de elementos de proyecto con vistas, mapas o tablas,
	 * seg�n la opci�n activada
	 */
	private void refreshList() {
		if (p != null) {
			DefaultListModel model = null;
			String tituloMarco = PluginServices.getText(this,
					"documentos_existentes");

			String doc = getDocumentSelected();
			model = new DefaultListModel(p.getDocumentsByType(doc));
			tituloMarco = getDocumentSelectedName();

			lstDocs.setModel(model);
			((TitledBorder) getJPanel1().getBorder()).setTitle(tituloMarco);
			getJPanel1().repaint(1);
			activarBotones();
		}
	}

	/**
	 * Devuelve el nombre del tipo de documento activo (el mismo que
	 * ProjectDocumentFactory.getRegisterName)
	 * 
	 * 
	 * @return
	 */

	public String getDocumentSelected() {
		JRadioButton btnSelected = null;
		for (int i = 0; i < btnsDocuments.length; i++) {
			if (btnsDocuments[i].isSelected()) {
				btnSelected = btnsDocuments[i];
				return btnSelected.getName();
			}
		}

		return null;
	}

	private String getDocumentSelectedName() {
		JRadioButton btnSelected = null;
		for (int i = 0; i < btnsDocuments.length; i++) {
			if (btnsDocuments[i].isSelected()) {
				btnSelected = btnsDocuments[i];
				return btnSelected.getText();
			}
		}

		return null;
	}

	/**
	 * Refresca las etiquetas con la informaci�n del proyecto
	 */
	private void refreshProperties() {
		if (p != null) {
			lblNombreSesion.setText(p.getName());
			String path = ProjectExtension.getPath();
			if (path != null) {
				File f = new File(path);
				lblGuardado.setText(f.toString());
			} else {
				lblGuardado.setText("");
			}
			lblFecha.setText(p.getCreationDate());
		}
	}

	/**
	 * Refresca todo el di�logo
	 */
	public void refreshControls() {
		refreshList();
		refreshProperties();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0;
		c.gridy = 0;
		c.insets = new Insets(2, 5, 2, 5);

		layout.setConstraints(getJPanel(), c);
		add(getJPanel(), null);

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		layout.setConstraints(getJPanel1(), c);
		add(getJPanel1(), null);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 2;
		layout.setConstraints(getJPanel3(), c);
		add(getJPanel3(), null);

		this.setSize(430, 475);
		this.setPreferredSize(new java.awt.Dimension(430, 489));
		this.setMinimumSize(new java.awt.Dimension(430, 489));
		this.add(getJScrollPane1(), gridBagConstraints);
		for (int i = 0; i < btnsDocuments.length; i++) {
			grupo.add(btnsDocuments[i]);
		}
		if (btnsDocuments.length > 0)
			btnsDocuments[0].setSelected(true);
	}

	private ProjectDocument createDocument(String s) {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();

		ExtensionPoint extensionPoint = extensionPoints.get("Documents");
		Iterator<String> iterator = extensionPoint.keySet().iterator();
		while (iterator.hasNext()) {
			try {
				ProjectDocumentFactory documentFactory = (ProjectDocumentFactory) extensionPoint
						.create(iterator.next());
				if (documentFactory.getRegisterName().equals(s)) {
					ProjectDocument document = documentFactory.createFromGUI(p);
					if (document == null)
						return null;
					document.setProjectDocumentFactory(documentFactory);
					p.setModified(true);
					return document;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Crea un nuevo project element
	 * 
	 * @throws Exception
	 *             DOCUMENT ME!
	 */
	private void newProjectDocument() throws Exception {
		String s = getDocumentSelected();
		ProjectDocument doc = createDocument(s);
		if (doc != null) {
			p.addDocument(doc);
			p.setModified(true);
		}
	}

	/**
	 * Abre la ventana de un nuevo project element
	 */
	private void abrir() {
		int[] indexes = lstDocs.getSelectedIndices();
		for (int i = indexes.length - 1; i >= 0; i--) {
			int index = indexes[i];
			if (index == -1) {
				return;
			}
			String s = getDocumentSelected();
			ProjectDocument doc = p.getDocumentsByType(s).get(index);
			IWindow window = doc.createWindow();
			if (window == null) {
				JOptionPane.showMessageDialog((Component) PluginServices
						.getMainFrame(), PluginServices.getText(this,
						"error_opening_the_document"));
				return;
			}
			PluginServices.getMDIManager().addWindow(window);
		}
		p.setModified(true);
	}

	/**
	 * Cambia el nombre de un project element
	 */
	private void renombrar() {
		int index = lstDocs.getSelectedIndex();

		if (index == -1) {
			return;
		}
		String s = getDocumentSelected();
		ProjectDocument doc = p.getDocumentsByType(s).get(index);

		if (doc.isLocked()) {
			JOptionPane.showMessageDialog(this, PluginServices.getText(this,
					"locked_element_it_cannot_be_renamed"));
			return;
		}

		JOptionPane pane = new JOptionPane();
		pane.setMessage(PluginServices.getText(this, "introduce_nombre"));
		pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		pane.setWantsInput(true);
		pane.setInitialSelectionValue(doc.getName());
		pane.setInputValue(doc.getName());
		JDialog dlg = pane.createDialog(
				(Component) PluginServices.getMainFrame(),
				PluginServices.getText(this, "renombrar"));
		dlg.setModal(true);
		dlg.setVisible(true);

		String nuevoNombre = pane.getInputValue().toString().trim();

		if (nuevoNombre.length() == 0) {
			return;
		}

		for (int i = 0; i < lstDocs.getModel().getSize(); i++) {
			if (i == index)
				continue;
			if (lstDocs.getModel().getElementAt(i).getName()
					.equals(nuevoNombre)) {
				JOptionPane.showMessageDialog(
						(Component) PluginServices.getMainFrame(),
						PluginServices.getText(this, "elemento_ya_existe"));
				return;
			}

		}
		doc.setName(nuevoNombre);

		refreshList();
		p.setModified(true);
	}

	/**
	 * Borra un project element
	 */
	private void borrar() {
		int res = JOptionPane.showConfirmDialog(
				(Component) PluginServices.getMainFrame(),
				PluginServices.getText(this, "confirmar_borrar"),
				PluginServices.getText(this, "borrar"),
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

		if (res == JOptionPane.YES_OPTION) {
			int[] indexes = lstDocs.getSelectedIndices();
			for (int i = indexes.length - 1; i >= 0; i--) {
				int index = indexes[i];
				String s = getDocumentSelected();
				ProjectDocument doc = p.getDocumentsByType(s).get(index);
				if (doc.isLocked()) {
					JOptionPane.showMessageDialog(this, PluginServices.getText(
							this, "locked_element_it_cannot_be_deleted"));
					return;
				}
				PluginServices.getMDIManager().closeSingletonWindow(doc);
				p.delDocument(doc);
			}
			refreshList();
			p.setModified(true);
		}
	}

	/**
	 * Muestra el di�logo de propiedades de un project element
	 */
	private void propiedades() {
		int index = lstDocs.getSelectedIndex();

		if (index == -1) {
			return;
		}

		IWindow dlg = null;
		String s = getDocumentSelected();
		ProjectDocument doc = p.getDocumentsByType(s).get(index);
		if (doc.isLocked()) {
			JOptionPane.showMessageDialog(this,
					PluginServices.getText(this, "locked_element"));
			return;
		}
		dlg = doc.getProperties();
		PluginServices.getMDIManager().addWindow(dlg);

		refreshControls();
		lstDocs.setSelectedIndex(index);
		p.setModified(true);
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
			layFlowLayout1.setHgap(15);
			jPanel.setLayout(layFlowLayout1);
			JRadioButton[] btns = getBtnDocuments();
			for (int i = 0; i < btns.length; i++) {
				jPanel.add(btns[i], null);
			}
			jPanel.setName("tipoDocPanel");

			/*
			 * jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
			 * null, PluginServices.getText(this, "tipos_de_documentos"),
			 * javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
			 * javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			 */
		}

		return jPanel;
	}

	/**
	 * This method initializes btnVistas
	 * 
	 * @return JRadioButton
	 */
	private JRadioButton[] getBtnDocuments() {
		if (btnsDocuments == null) {
			ExtensionPoints extensionPoints = ExtensionPointsSingleton
					.getInstance();

			ExtensionPoint extensionPoint = extensionPoints.get("Documents");
			ArrayList<JRadioButton> btns = new ArrayList<JRadioButton>();
			ArrayList<Integer> priorities = new ArrayList<Integer>();
			Iterator<String> iterator = extensionPoint.keySet().iterator();
			while (iterator.hasNext()) {
				try {
					ProjectDocumentFactory documentFactory = (ProjectDocumentFactory) extensionPoint
							.create(iterator.next());
					JRadioButton rb = new JRadioButton();

					rb.setIcon(documentFactory.getButtonIcon());
					rb.setSelectedIcon(documentFactory.getSelectedButtonIcon());
					rb.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
					rb.setText(documentFactory.getNameType());
					rb.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
					rb.setName(documentFactory.getRegisterName());
					rb.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							refreshList();
						}
					});
					// place in the right order according to list priority
					int priority = documentFactory.getListPriority();
					int pos;
					for (pos = 0; pos < btns.size(); pos++) {
						if (priorities.get(pos) > priority) {
							priorities.add(pos, new Integer(priority));
							btns.add(pos, rb);
							break;
						}
					}
					if (pos >= btns.size()) {
						priorities.add(new Integer(priority));
						btns.add(rb);
					}

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}

			btnsDocuments = btns.toArray(new JRadioButton[0]);
		}

		return btnsDocuments;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();

			GridBagLayout layout2 = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();

			jPanel1.setLayout(layout2);
			c.insets = new Insets(2, 5, 2, 5);
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1.0;
			c.weighty = 1.0;
			layout2.setConstraints(getJScrollPane(), c);
			jPanel1.add(getJScrollPane());

			c.anchor = GridBagConstraints.EAST;

			c.fill = GridBagConstraints.NONE;
			c.weightx = 0;
			c.weighty = 0;
			layout2.setConstraints(getJPanel2(), c);
			jPanel1.add(getJPanel2());

			jPanel1.setPreferredSize(new java.awt.Dimension(430, 170));
			jPanel1.setMinimumSize(new java.awt.Dimension(430, 160));

			/* jPanel1.setMinimumSize(new java.awt.Dimension(430,145)); */
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null,
					PluginServices.getText(this, "documentos_existentes"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
		}

		return jPanel1;
	}

	/**
	 * This method initializes lstDocs
	 * 
	 * @return JList
	 */
	private JList<ProjectDocument> getLstDocs() {
		if (lstDocs == null) {
			lstDocs = new JList<ProjectDocument>();
			lstDocs.addMouseListener(new java.awt.event.MouseAdapter() {
				public ProjectDocument[] getSelecteds() {
					if (lstDocs.getSelectedIndex() < 0) {
						return new ProjectDocument[0];
					}
					List<ProjectDocument> selected = lstDocs
							.getSelectedValuesList();
					ProjectDocument[] returnValue = new ProjectDocument[selected
							.size()];
					System.arraycopy(selected.toArray(new ProjectDocument[0]),
							0, returnValue, 0, selected.size());
					return returnValue;
				}

				public ProjectDocument getItem(java.awt.event.MouseEvent e) {
					if (lstDocs.getSelectedIndex() < 0) {
						return null;
					}
					return null;
				}

				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
						String docType = getDocumentSelected();
						ProjectDocument[] selecteds = this.getSelecteds();

						if (selecteds == null)
							return;
						DocumentContextMenu menu = new DocumentContextMenu(
								docType, this.getItem(e), selecteds);
						if (menu.getActionsVisibles() < 1)
							return;
						lstDocs.add(menu);
						menu.show(e.getComponent(), e.getX(), e.getY());
						return;
					}

					if (e.getClickCount() == 2) {
						abrir();
					}

				}
			});
			lstDocs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					activarBotones();
				}
			});
		}

		return lstDocs;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();

			// FlowLayout layout = new FlowLayout();
			GridLayout layout = new GridLayout(5, 1);
			layout.setVgap(7);

			jPanel2.setLayout(layout);
			jPanel2.add(getBtnNuevo(), null);
			jPanel2.add(getBtnAbrir(), null);
			jPanel2.add(getBtnRenombrar(), null);
			jPanel2.add(getBtnBorrar(), null);
			jPanel2.add(getBtnPropiedades(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(100, 150));
			jPanel2.setMinimumSize(new java.awt.Dimension(100, 150));
			jPanel2.setMaximumSize(new java.awt.Dimension(100, 150));
		}

		return jPanel2;
	}

	/**
	 * This method initializes btnNuevo
	 * 
	 * @return JButton
	 */
	private JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton();
			btnNuevo.setName("btnNuevo");
			btnNuevo.setText(PluginServices.getText(this, "nuevo"));
			btnNuevo.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnNuevo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						newProjectDocument();
					} catch (Exception e1) {
						NotificationManager.addError(e1.getLocalizedMessage(),
								e1);
					}
				}
			});
		}

		return btnNuevo;
	}

	/**
	 * This method initializes btnPropiedades
	 * 
	 * @return JButton
	 */
	private JButton getBtnPropiedades() {
		if (btnPropiedades == null) {
			btnPropiedades = new JButton();
			btnPropiedades.setText(PluginServices.getText(this, "propiedades"));
			btnPropiedades.setName("btnPropiedades");
			btnPropiedades.setEnabled(false);
			btnPropiedades.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnPropiedades
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							propiedades();
						}
					});
		}

		return btnPropiedades;
	}

	/**
	 * This method initializes btnAbrir
	 * 
	 * @return JButton
	 */
	private JButton getBtnAbrir() {
		if (btnAbrir == null) {
			btnAbrir = new JButton();
			btnAbrir.setName("btnAbrir");
			btnAbrir.setText(PluginServices.getText(this, "abrir"));
			btnAbrir.setEnabled(false);
			btnAbrir.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnAbrir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					abrir();
				}
			});
		}

		return btnAbrir;
	}

	/**
	 * This method initializes btnBorrar
	 * 
	 * @return JButton
	 */
	private JButton getBtnBorrar() {
		if (btnBorrar == null) {
			btnBorrar = new JButton();
			btnBorrar.setText(PluginServices.getText(this, "borrar"));
			btnBorrar.setName("btnBorrar");
			btnBorrar.setEnabled(false);
			btnBorrar.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnBorrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					borrar();
				}
			});
		}

		return btnBorrar;
	}

	/**
	 * This method initializes btnRenombrar
	 * 
	 * @return JButton
	 */
	private JButton getBtnRenombrar() {
		if (btnRenombrar == null) {
			btnRenombrar = new JButton();
			btnRenombrar.setName("btnRenombrar");
			btnRenombrar.setText(PluginServices.getText(this, "renombrar"));
			btnRenombrar.setEnabled(false);
			btnRenombrar.setMargin(new java.awt.Insets(2, 2, 2, 2));
			btnRenombrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					renombrar();
				}
			});
		}

		return btnRenombrar;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();

			GridBagLayout layout3 = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			jPanel3.setLayout(layout3);
			c.insets = new Insets(2, 5, 0, 5);
			c.anchor = GridBagConstraints.WEST;
			c.gridx = 0;
			c.gridy = 0;
			layout3.setConstraints(getJLabel(), c);
			jPanel3.add(getJLabel(), null);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			c.gridx = 1;
			layout3.setConstraints(getLblNombreSesion(), c);
			jPanel3.add(getLblNombreSesion(), null);

			c.gridx = 0;
			c.gridy = 1;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			layout3.setConstraints(getJLabel1(), c);
			jPanel3.add(getJLabel1(), null);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			c.gridx = 1;
			layout3.setConstraints(getLblGuardado(), c);
			jPanel3.add(getLblGuardado(), null);

			c.gridx = 0;
			c.gridy = 2;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			layout3.setConstraints(getJLabel2(), c);
			jPanel3.add(getJLabel2(), null);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			c.gridx = 1;
			layout3.setConstraints(getLblFecha(), c);
			jPanel3.add(getLblFecha(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(430, 125));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, PluginServices.getText(this, "propiedades_sesion"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			c.gridx = 1;
			c.gridy = 4;
			c.anchor = GridBagConstraints.EAST;
			layout3.setConstraints(getJPanel4(), c);
			jPanel3.add(getJPanel4(), null);
		}

		return jPanel3;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return JLabel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText(PluginServices.getText(this, "nombre_sesion") + ":");
			jLabel.setPreferredSize(new java.awt.Dimension(150, 16));
		}

		return jLabel;
	}

	/**
	 * This method initializes lblNombreSesion
	 * 
	 * @return JLabel
	 */
	private JLabel getLblNombreSesion() {
		if (lblNombreSesion == null) {
			lblNombreSesion = new JLabel();
			lblNombreSesion.setText("");
			lblNombreSesion.setName("lblNombreSesion");
			lblNombreSesion.setPreferredSize(new java.awt.Dimension(200, 16));
			lblNombreSesion.setAutoscrolls(true);
		}

		return lblNombreSesion;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText(PluginServices.getText(this, "guardado") + ":");
			jLabel1.setPreferredSize(new java.awt.Dimension(150, 16));
		}

		return jLabel1;
	}

	/**
	 * This method initializes lblGuardado
	 * 
	 * @return JLabel
	 */
	private JLabel getLblGuardado() {
		if (lblGuardado == null) {
			lblGuardado = new JLabel();
			lblGuardado.setText("");
			lblGuardado.setPreferredSize(new java.awt.Dimension(200, 16));
			lblGuardado.setAutoscrolls(true);
		}

		return lblGuardado;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return JLabel
	 */
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText(PluginServices.getText(this, "creation_date") + ":");
			jLabel2.setPreferredSize(new java.awt.Dimension(150, 16));
		}

		return jLabel2;
	}

	/**
	 * This method initializes lblFecha
	 * 
	 * @return JLabel
	 */
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel();
			lblFecha.setText("");
			lblFecha.setPreferredSize(new java.awt.Dimension(200, 16));
			lblFecha.setAutoscrolls(true);
		}

		return lblFecha;
	}

	/**
	 * This method initializes btnImportar
	 * 
	 * @return JButton
	 */
	private JButton getBtnImportar() {
		if (btnImportar == null) {
			btnImportar = new JButton();
			btnImportar.setPreferredSize(new java.awt.Dimension(80, 23));
			btnImportar.setText(PluginServices.getText(this, "importar"));
			btnImportar.setName("btnImportar");
			btnImportar.setMargin(new java.awt.Insets(2, 2, 2, 2));
			// TODO implement import
			btnImportar.setVisible(false);
		}

		return btnImportar;
	}

	/**
	 * This method initializes btnExportar
	 * 
	 * @return JButton
	 */
	private JButton getBtnExportar() {
		if (btnExportar == null) {
			btnExportar = new JButton();
			btnExportar.setPreferredSize(new java.awt.Dimension(80, 23));
			btnExportar.setText(PluginServices.getText(this, "exportar"));
			btnExportar.setName("btnExportar");
			btnExportar.setMargin(new java.awt.Insets(2, 2, 2, 2));
			// TODO implement export
			btnExportar.setVisible(false);
		}

		return btnExportar;
	}

	/**
	 * This method initializes btnEditar
	 * 
	 * @return JButton
	 */
	private JButton getBtnEditar() {
		if (btnEditar == null) {
			btnEditar = new JButton();
			btnEditar.setPreferredSize(new java.awt.Dimension(80, 23));
			btnEditar.setText(PluginServices.getText(this, "propiedades"));

			btnEditar.setName("btnEditar");
			btnEditar.setMargin(new java.awt.Insets(2, 2, 2, 2));

			btnEditar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ProjectProperties dlg = new ProjectProperties(p);
					PluginServices.getMDIManager().addWindow(dlg);
					refreshProperties();
				}
			});
		}

		return btnEditar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLstDocs());
			jScrollPane.setPreferredSize(new java.awt.Dimension(200, 100));
			jScrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

		return jScrollPane;
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.SingletonWindow#getWindowModel()
	 */
	public Object getWindowModel() {
		return p;
	}

	/**
	 * This method is used to get <strong>an initial</strong> ViewInfo object
	 * for this Project Manager window. It is not intended to retrieve the
	 * ViewInfo object in a later time. <strong>Use
	 * PluginServices.getMDIManager().getViewInfo(view) to retrieve the ViewInfo
	 * object at any time after the creation of the object.
	 * 
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		if (m_viewInfo == null) {
			m_viewInfo = new WindowInfo(WindowInfo.MAXIMIZABLE);

			/*
			 * m_viewInfo = new
			 * WindowInfo(WindowInfo.MAXIMIZABLE+WindowInfo.RESIZABLE);
			 */

			m_viewInfo.setWidth(this.getWidth());
			m_viewInfo.setHeight(this.getHeight());
			m_viewInfo.setNormalWidth(this.getPreferredSize().width);
			m_viewInfo.setNormalHeight(this.getPreferredSize().height);

			m_viewInfo.setTitle(PluginServices.getText(this, "titulo"));
		}
		return m_viewInfo;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		refreshControls();
	}

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void viewActivated() {
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();

			jPanel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
			jPanel4.setPreferredSize(new java.awt.Dimension(414, 30));
			jPanel4.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
			jPanel4.add(getBtnImportar(), null);
			jPanel4.add(getBtnExportar(), null);
			jPanel4.add(getBtnEditar(), null);
		}

		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1
					.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			jScrollPane1.setPreferredSize(new java.awt.Dimension(433, 200));
			jScrollPane1.setViewportView(getJPanel());
		}
		return jScrollPane1;
	}

	public Object getWindowProfile() {
		return WindowInfo.PROJECT_PROFILE;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
