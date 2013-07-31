/*
 * Created on 08-feb-2005
 *
 * gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactoryImpl;
import org.geotools.styling.Symbolizer;
import org.gvsig.gui.beans.swing.JButton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;

/**
 * Implements the panel which allows the user to control all the information
 * about the legends of a layer in order to improve the information that it
 * offers to the user. There are options to create, save or load an existing
 * legend.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 */
public class LegendManager extends AbstractThemeManagerPage {
	private static final long serialVersionUID = 7989057553773181019L;
	private static ArrayList<Class<? extends ILegendPanel>> legendPool = new ArrayList<Class<? extends ILegendPanel>>();
	private static ArrayList<Class<? extends IFMapLegendDriver>> legendDriverPool = new ArrayList<Class<? extends IFMapLegendDriver>>();

	private Layer layer;
	private Symbolizer legend; // Le asignaremos la leyenda del primer tema
								// activo.
	private Hashtable<Class<? extends ILegendPanel>, ILegendPanel> pages = new Hashtable<Class<? extends ILegendPanel>, ILegendPanel>();
	private JPanel topPanel = null;
	private JTextArea titleArea = null;
	private JPanel preview = null;
	private JScrollPane jTitleScrollPane = null;
	private JTree jTreeLegends;
	private ILegendPanel activePanel;
	private JScrollPane legendTreeScrollPane;
	private boolean dirtyTree;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
	private DefaultTreeModel treeModel;
	private JScrollPane jPanelContainer;
	private JPanel jCentralPanel;
	private JSplitPane jSplitPane;
	private boolean isTreeListenerDisabled;
	private JButton btnSaveLegend;
	private JButton btnLoadLegend;
	private boolean empty = true;
	private JLabel iconLabel;

	public static String defaultLegendFolderPath;
	{

		Preferences prefs = Preferences.userRoot().node("gvsig.foldering");
		defaultLegendFolderPath = prefs.get("LegendsFolder", "");
	}

	// private ActionListener loadSaveLegendAction = new ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// JComponent c = (JComponent) e.getSource();
	// if (c.equals(getBtnSaveLegend())) {
	//
	// JLegendFileChooser jfc = new JLegendFileChooser(
	// getLegendDrivers(), true);
	// jfc.setAcceptAllFileFilterUsed(false);
	//
	// File basedir = null;
	//
	// jfc.setCurrentDirectory(basedir);
	// if (jfc.showSaveDialog((Component) PluginServices
	// .getMainFrame()) == JFileChooser.APPROVE_OPTION) {
	//
	// File file = jfc.getSelectedFile();
	// String version = JLegendFileChooser.getDriverVersion();
	// IFMapLegendDriver driver = jfc.getSuitableDriver();
	//
	// if (file.exists()) {
	// Object[] options = {
	// PluginServices.getText(this, "yes"),
	// PluginServices.getText(this, "no"),
	// PluginServices.getText(this, "Cancel") };
	//
	// int answer = JOptionPane
	// .showOptionDialog(
	// (Component) PluginServices
	// .getMainFrame(),
	// PluginServices
	// .getText(this,
	// "fichero_ya_existe_seguro_desea_guardarlo"),
	// PluginServices.getText(this,
	// "confirmation_dialog"),
	// JOptionPane.YES_NO_CANCEL_OPTION,
	// JOptionPane.QUESTION_MESSAGE, null,
	// options, options[1]);
	// if (answer != JOptionPane.OK_OPTION) {
	// // 'Cancel' pressed or window closed: don't save
	// // anythig, exit save dialog
	// return;
	// }
	// }
	//
	// try {
	// driver.write(layer.getMapContext().getLayers(), layer,
	// activePanel.getLegend(), file, version);
	//
	// } catch (LegendDriverException e1) {
	// int type = e1.getType();
	// String message = PluginServices.getText(this,
	// "could_not_save_legend") + ":\n";
	//
	// if ((type & LegendDriverException.SAVE_LEGEND_ERROR) != 0) {
	// type = type
	// & ~LegendDriverException.SAVE_LEGEND_ERROR;
	// message += PluginServices.getText(this,
	// "error_writing_file") + ".\n";
	// }
	// if ((type & LegendDriverException.UNSUPPORTED_LEGEND_CREATION) != 0) {
	// type = type
	// & ~LegendDriverException.UNSUPPORTED_LEGEND_CREATION;
	// message += "-"
	// + PluginServices.getText(this,
	// "legend_format_not_yet_supported")
	// + "\n";
	// }
	// if (type != 0) {
	// message = "-"
	// + PluginServices.getText(this,
	// "unknown_error") + "\n";
	// }
	// JOptionPane.showMessageDialog(
	// (Component) PluginServices.getMainFrame(),
	// message);
	// }
	// }
	// } else if (c.equals(getBtnLoadLegend())) {
	//
	// JLegendFileChooser jfc = new JLegendFileChooser(
	// getLegendDrivers());
	// jfc.setAcceptAllFileFilterUsed(false);
	//
	// if (jfc.showOpenDialog((Component) PluginServices
	// .getMainFrame()) == JFileChooser.APPROVE_OPTION) {
	// File file = jfc.getSelectedFile();
	// IFMapLegendDriver driver = jfc.getSuitableDriver();
	// String path = file.getAbsolutePath();
	// defaultLegendFolderPath = path.substring(0,
	// path.lastIndexOf(File.separator));
	//
	// try {
	// table = driver.read(layer.getMapContext().getLayers(),
	// layer, file);
	//
	// if (table.containsKey(layer))
	// applyLegend((ILegend) table.get(layer));
	//
	// } catch (LegendDriverException e1) {
	// int type = e1.getType();
	// String message = PluginServices.getText(this,
	// "the_legend_will_not_be_applied") + ":\n";
	// boolean hasReason = false;
	//
	// if ((type & LegendDriverException.CLASSIFICATION_FIELDS_NOT_FOUND) != 0)
	// {
	// type = type
	// & ~LegendDriverException.CLASSIFICATION_FIELDS_NOT_FOUND;
	// message += "-"
	// + PluginServices
	// .getText(this,
	// "classification_field_does_not_exists")
	// + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.LEGEND_TYPE_NOT_YET_SUPPORTED) != 0) {
	// type = type
	// & ~LegendDriverException.LEGEND_TYPE_NOT_YET_SUPPORTED;
	// message += "-"
	// + PluginServices.getText(this,
	// "legend_type_not_yet_supported")
	// + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.SYMBOL_TYPE_NOT_YET_SUPPORTED) != 0) {
	// type = type
	// & ~LegendDriverException.SYMBOL_TYPE_NOT_YET_SUPPORTED;
	// message += "-"
	// + PluginServices.getText(this,
	// "unsupported_symbol_type") + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.LAYER_SHAPETYPE_MISMATCH) != 0) {
	// type = type
	// & ~LegendDriverException.LAYER_SHAPETYPE_MISMATCH;
	// message += "-"
	// + PluginServices
	// .getText(this,
	// "layer_geometry_and_legend_types_are_incompatible")
	// + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.PARSE_LEGEND_FILE_ERROR) != 0) {
	// type = type
	// & ~LegendDriverException.PARSE_LEGEND_FILE_ERROR;
	// message += "-"
	// + PluginServices.getText(this,
	// "failed_reading_file") + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.UNSUPPORTED_LEGEND_FILE_VERSION) != 0)
	// {
	// type = type
	// & ~LegendDriverException.UNSUPPORTED_LEGEND_FILE_VERSION;
	// message += "-"
	// + PluginServices.getText(this,
	// "unsupported_legend_file_version")
	// + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.UNSUPPORTED_LEGEND_READING) != 0) {
	// type = type
	// & ~LegendDriverException.UNSUPPORTED_LEGEND_READING;
	// message += "-"
	// + PluginServices.getText(this,
	// "unsupported_legend_file_format")
	// + "\n";
	// hasReason = true;
	// }
	// if ((type & LegendDriverException.LAYER_NAME_NOT_FOUND) != 0) {
	// type = type
	// & ~LegendDriverException.LAYER_NAME_NOT_FOUND;
	// message += "-"
	// + PluginServices.getText(this,
	// "could_not_find_layer") + "\n";
	// hasReason = true;
	// }
	// if (!hasReason) {
	// message = "-"
	// + PluginServices.getText(this,
	// "unknown_error") + "\n";
	// }
	// JOptionPane.showMessageDialog(
	// (Component) PluginServices.getMainFrame(),
	// message);
	// }
	// getBtnSaveLegend().setEnabled(activePanel != null);
	// }
	// }
	// }
	//
	// private IFMapLegendDriver[] getLegendDrivers() {
	// Class<?>[] legendDriverClasses = legendDriverPool
	// .toArray(new Class[0]);
	// ArrayList<IFMapLegendDriver> drivers = new
	// ArrayList<IFMapLegendDriver>();
	// for (int i = 0; i < legendDriverClasses.length; i++) {
	// String message = PluginServices.getText(this,
	// "adding_legend_file_format_support") + ": ";
	// try {
	// Class<?> c = legendDriverClasses[i];
	// drivers.add((IFMapLegendDriver) c.newInstance());
	// } catch (Exception e) {
	// NotificationManager
	// .addError(
	// message
	// + PluginServices.getText(this,
	// "failed"), e);
	// }
	// NotificationManager.addInfo(message
	// + PluginServices.getText(this, "OK"));
	//
	// }
	// return (IFMapLegendDriver[]) drivers
	// .toArray(new IFMapLegendDriver[0]);
	// };
	// };

	public LegendManager() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(getTopPanel(), BorderLayout.NORTH);
		add(getSplitPane(), BorderLayout.CENTER);
		setSize(500, 360);
		treeModel = new DefaultTreeModel(root);
	}

	private JSplitPane getSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			JPanel aux = new JPanel(new BorderLayout(0, 5));
			aux.add(getLegendTreeScrollPane(), BorderLayout.CENTER);
			aux.add(getPreviewPanel(), BorderLayout.SOUTH);
			jSplitPane.setLeftComponent(aux);
			jSplitPane.setRightComponent(getCentralPanel());
			jSplitPane.setDividerLocation(150);
		}
		return jSplitPane;
	}

	private JPanel getCentralPanel() {
		if (jCentralPanel == null) {
			jCentralPanel = new JPanel(new BorderLayout(0, 10));
			jCentralPanel.add(getTitleScroll(), BorderLayout.NORTH);
			jCentralPanel.add(getJPanelContainer(), BorderLayout.CENTER);
		}
		return jCentralPanel;
	}

	private JScrollPane getJPanelContainer() {
		if (jPanelContainer == null) {
			jPanelContainer = new JScrollPane();
		}
		return jPanelContainer;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
			topPanel.setPreferredSize(new Dimension(638, 31));
			topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			topPanel.add(getBtnSaveLegend(), null);
			topPanel.add(getBtnLoadLegend(), null);
		}
		return topPanel;
	}

	private JButton getBtnSaveLegend() {
		if (btnSaveLegend == null) {
			btnSaveLegend = new JButton(PluginServices.getText(this,
					"Guardar_leyenda") + "...");
			btnSaveLegend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					throw new UnsupportedOperationException();
				}
			});
		}
		return btnSaveLegend;
	}

	private JButton getBtnLoadLegend() {
		if (btnLoadLegend == null) {
			btnLoadLegend = new JButton(PluginServices.getText(this,
					"Recuperar_leyenda") + "...");
			btnLoadLegend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					throw new UnsupportedOperationException();
				}
			});
		}
		return btnLoadLegend;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTitleArea() {
		if (titleArea == null) {
			titleArea = new JTextArea();
			titleArea.setBackground(java.awt.SystemColor.control);
			titleArea.setLineWrap(true);
			titleArea.setRows(0);
			titleArea.setWrapStyleWord(false);
			titleArea.setEditable(false);
			titleArea.setPreferredSize(new java.awt.Dimension(495, 40));
		}
		return titleArea;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPreviewPanel() {
		if (preview == null) {
			preview = new JPanel();
			preview.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			preview.setBackground(java.awt.SystemColor.text);
			preview.setLayout(new BorderLayout(5, 5));
			preview.add(getIconLabel());
			preview.setPreferredSize(new Dimension(getSplitPane()
					.getDividerLocation(), 130));
			preview.setBackground(Color.white);
		}
		return preview;
	}

	private JLabel getIconLabel() {
		if (iconLabel == null) {
			iconLabel = new JLabel();
			iconLabel.setVerticalAlignment(JLabel.CENTER);
			iconLabel.setHorizontalAlignment(JLabel.CENTER);
		}

		return iconLabel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getLegendTreeScrollPane() {
		if (legendTreeScrollPane == null) {
			legendTreeScrollPane = new JScrollPane();
			legendTreeScrollPane.setViewportView(getJTreeLegends());
		}
		return legendTreeScrollPane;
	}

	/**
	 * <p>
	 * Adds a new fully-featured legend panel to the LegendManager.<br>
	 * </p>
	 * 
	 * <p>
	 * <b>CAUTION:</b> Trying to add a child page whose parent hasn't been added
	 * yet causes the application to fall in an infinite loop. This is a known
	 * bug, sorry. Just avoid this case or try to fix it (lol).<br>
	 * </p>
	 * 
	 * <p>
	 * <b>Notice</b> that there is no type check so if you add a
	 * non-ILegendPanel class, you'll have a runtime error later.
	 * </p>
	 * 
	 * @param page
	 *            , Class of type ILegendPanel
	 */
	public static void addLegendPage(
			Class<? extends ILegendPanel> iLegendPanelClass) {
		if (!legendPool.contains(iLegendPanelClass)) {
			legendPool.add(iLegendPanelClass);
		}
	}

	/**
	 * Causes the component to be autofilled with the legend pages that were
	 * added through the static method addLegendPage(ILegendPanel page)
	 */
	private void fillDialog() {
		if (empty) {
			for (int i = 0; i < legendPool.size(); i++) {
				Class<?> pageClass = (Class<?>) legendPool.get(i);
				ILegendPanel page;
				try {
					try {
						page = (ILegendPanel) pageClass.newInstance();
					} catch (NoClassDefFoundError ex) {
						NotificationManager.addWarning(
								"Trying to look for this class:"
										+ pageClass.getName(), ex);
						continue;
					}
					if (page.isSuitableFor(layer)) {
						// this legend can be applied to this layer
						pages.put(page.getClass(), page);

						if (dirtyTree) {
							// rebuild page tree
							dirtyTree = false;

							ArrayList<ILegendPanel> legList = new ArrayList<ILegendPanel>(
									pages.values());
							ArrayList<ILegendPanel> alreadyAdded = new ArrayList<ILegendPanel>();
							DefaultTreeModel model = new DefaultTreeModel(root);
							while (legList.size() > 0) {
								ILegendPanel legend = (ILegendPanel) legList
										.get(0);
								Class<? extends ILegendPanel> parent = legend
										.getParentClass();
								while (parent != null
										&& !alreadyAdded.contains(pages
												.get(parent))) {
									legend = (ILegendPanel) pages.get(parent);
								}
								doInsertNode(model, legend);
								legList.remove(legend);
								alreadyAdded.add(legend);
							}
							treeModel = model;
							jTreeLegends.setModel(model);
						}
						doInsertNode(treeModel, page);

					}
					getJTreeLegends().setModel(treeModel);

				} catch (InstantiationException e) {
					NotificationManager.addError(
							"Trying to instantiate an interface"
									+ " or abstract class + "
									+ pageClass.getName(), e);
				} catch (IllegalAccessException e) {
					NotificationManager.addError(
							"IllegalAccessException: does "
									+ pageClass.getName()
									+ " class have an anonymous"
									+ " constructor?", e);
				}
			}
			getJTreeLegends().repaint();
			empty = false;
		}
	}

	private DefaultMutableTreeNode findNode(Class searchID) {
		String title;
		try {
			title = ((ILegendPanel) Class.forName(searchID.getName())
					.newInstance()).getTitle();
		} catch (Exception e) {
			// this should be impossible, but anyway this case will be treat as
			// the node does not
			// exist.
			return null;
		}

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode nodeAux = (DefaultMutableTreeNode) e
					.nextElement();
			if (nodeAux != null) {
				ILegendPanel legend = (ILegendPanel) nodeAux.getUserObject();
				if (legend == null)
					continue; // Root node
				if (legend.getTitle().equals(title)) {
					return nodeAux;
				}
			}
		}
		return null;
	}

	private void doInsertNode(DefaultTreeModel treeModel, ILegendPanel page) {
		dirtyTree = ((page.getParentClass() != null) && (findNode(page
				.getParentClass()) == null));
		if (findNode(page.getClass()) != null) // It is already added
			return;
		if (page.getParentClass() != null) {
			if (pages.containsKey(page.getParentClass())) {
				ILegendPanel parent = (ILegendPanel) pages.get(page
						.getParentClass());
				DefaultMutableTreeNode nodeParent = findNode(parent.getClass());
				if (nodeParent == null) {
					// the parent is empty
					// Recursively add it
					doInsertNode(treeModel, parent);
				} else {
					DefaultMutableTreeNode nodeValue = new DefaultMutableTreeNode(
							page);
					int children = nodeParent.getChildCount();
					int pos = 0;
					for (int i = 0; i < children; i++) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel
								.getChild(nodeParent, i);
						if (node.getUserObject() instanceof ILegendPanel) {
							String pageTitle = ((ILegendPanel) node
									.getUserObject()).getTitle();
							if (pageTitle.compareTo(page.getTitle()) < 0)
								++pos;
						}
					}
					treeModel.insertNodeInto(nodeValue, nodeParent, pos);
				}
			}
		} else {
			// First level node
			DefaultMutableTreeNode nodeValue = new DefaultMutableTreeNode(page);
			int children = root.getChildCount();
			int pos = 0;
			for (int i = 0; i < children; i++) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel
						.getChild(root, i);
				if (node.getUserObject() instanceof ILegendPanel) {
					String pageTitle = ((ILegendPanel) node.getUserObject())
							.getTitle();
					if (pageTitle.compareTo(page.getTitle()) < 0)
						++pos;
				}
			}
			treeModel.insertNodeInto(nodeValue, root, pos);
		}
	}

	private JScrollPane getTitleScroll() {
		if (jTitleScrollPane == null) {
			jTitleScrollPane = new JScrollPane();
			jTitleScrollPane.setBounds(2, 2, 498, 42);
			jTitleScrollPane.setViewportView(getTitleArea());
		}
		return jTitleScrollPane;
	}

	private JTree getJTreeLegends() {
		if (jTreeLegends == null) {
			jTreeLegends = new JTree();
			jTreeLegends.setRootVisible(false);
			MyTreeCellRenderer treeCellRenderer = new MyTreeCellRenderer();
			treeCellRenderer.setOpenIcon(null);
			treeCellRenderer.setClosedIcon(null);
			treeCellRenderer.setLeafIcon(null);

			jTreeLegends.setCellRenderer(treeCellRenderer);
			jTreeLegends.setShowsRootHandles(true);
			jTreeLegends
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							if (isTreeListenerDisabled)
								return;
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTreeLegends
									.getLastSelectedPathComponent();

							if (node == null)
								return;
							setActivePage((ILegendPanel) node.getUserObject());
							getBtnSaveLegend().setEnabled(activePanel != null);
						}
					});
			jTreeLegends.putClientProperty("JTree.linestyle", "Angled");
			jTreeLegends.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
		}
		return jTreeLegends;
	}

	private void setActivePage(ILegendPanel page) {
		if (page.getPanel() == null) {
			// this is what happens when the user clicked in a parent node
			// which only acts as a folder, and does not manage any legend
			// then it expands and selects the first child
			DefaultMutableTreeNode node = findNode(page.getClass());
			if (treeModel.getChildCount(node) > 0) {
				DefaultMutableTreeNode dmn = (DefaultMutableTreeNode) treeModel
						.getChild(node, 0);
				page = (ILegendPanel) dmn.getUserObject();
				setActivePage(page);
				expandAndSelect(page);
			} else {
				activePanel = null;
				getTitleArea().setText("");
				jPanelContainer.setViewportView(null);
				setIcon(null);
			}
		} else {
			// show the page
			activePanel = page;
			setIcon(activePanel.getIcon());

			activePanel.setData(layer, legend);
			getTitleArea().setText(activePanel.getDescription());
			jPanelContainer.setViewportView(activePanel.getPanel());
		}
	}

	private void setIcon(ImageIcon icon) {
		getIconLabel().setIcon(icon);
	}

	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = -6013698992263578041L;

		public MyTreeCellRenderer() {
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				if (node.getUserObject() instanceof ILegendPanel) {
					ILegendPanel legend = (ILegendPanel) node.getUserObject();
					this.setText(legend.getPanel() == null ? "<html><b>"
							+ legend.getTitle() + "</b></html>" : legend
							.getTitle());
				}
			}
			return this;
		}

	}

	private void expandAndSelect(Object node) {
		isTreeListenerDisabled = true;
		// will expand the tree and select the node
		int i = 0;
		boolean exit = false;

		TreePath tp = null;
		// find the page in the tree
		while (i < jTreeLegends.getRowCount() && !exit) {
			// see if this row is the node that we are looking for

			tp = jTreeLegends.getPathForRow(i);
			Object[] obj = tp.getPath();
			for (int j = 0; j < obj.length && !exit; j++) {
				Object o = ((DefaultMutableTreeNode) obj[j]).getUserObject();

				if (o != null && o.getClass().equals(node.getClass())
						&& o.equals(node)) {
					// found it! collapse the tree
					while (i >= 0) {
						jTreeLegends.collapseRow(i);
						i--;
					}
					exit = true;
				}
			}
			jTreeLegends.expandRow(i);
			i++;
		}

		// expand the tree and set the selection
		if (tp != null) {
			jTreeLegends.expandPath(tp);
			jTreeLegends.setSelectionPath(tp);
		}
		isTreeListenerDisabled = false;
	}

	public String getName() {
		return PluginServices.getText(this, "Simbologia");
	}

	public void acceptAction() {
		// automatically handled by the ThemeManagerWindow
	}

	public void cancelAction() {
		// does nothing
	}

	public void applyAction() {
		if (activePanel != null) {
			legend = activePanel.getLegend();

			StyleFactoryImpl factory = new StyleFactoryImpl();
			Rule rule = factory.createRule();
			rule.symbolizers().add(legend);
			FeatureTypeStyle fts = factory
					.createFeatureTypeStyle(new Rule[] { rule });
			Style style = factory.createStyle();
			style.featureTypeStyles().add(fts);

			layer.setStyle(style);
		}
	}

	@Override
	public void setModel(Layer layer) {
		this.layer = layer;
		applyLegend(layer.getStyle());
	}

	/**
	 * Applies the legend to the layer.
	 * 
	 * @param aLegend
	 *            , legend that the user wants to apply
	 */
	private void applyLegend(Style aLegend) {
		// TODO gtintegration: get first symbolizer arbitrarily
		List<FeatureTypeStyle> featureTypeStyles = aLegend.featureTypeStyles();
		boolean set = false;
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			for (Rule rule : featureTypeStyle.rules()) {
				for (Symbolizer symbolizer : rule.symbolizers()) {
					this.legend = symbolizer;
					set = true;
					break;
				}
				if (set) {
					break;
				}
			}
			if (set) {
				break;
			}
		}

		fillDialog();
		Enumeration<Class<? extends ILegendPanel>> en = pages.keys();
		while (en.hasMoreElements()) {
			ILegendPanel page = (ILegendPanel) pages.get(en.nextElement());
			if (Symbolizer.class.isAssignableFrom(legend.getClass())) {
				setActivePage(page);
				expandAndSelect(page);
				return;
			}
		}
		NotificationManager.addWarning(PluginServices.getText(this,
				"caution_no_registered_panel_associated_to_"
						+ "loaded_legend_the_legend_wont_be_applied"));
	}

	public static void addLegendDriver(
			Class<? extends IFMapLegendDriver> legendDriverClass) {
		if (!legendDriverPool.contains(legendDriverClass)) {
			legendDriverPool.add(legendDriverClass);
		}
	}

	@Override
	public boolean accepts(Layer layer) {
		return true;
	}
}