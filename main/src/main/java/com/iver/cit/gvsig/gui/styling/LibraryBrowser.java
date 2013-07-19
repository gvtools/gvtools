/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
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
package com.iver.cit.gvsig.gui.styling;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiFrame.JMenuItem;
import com.iver.andami.ui.mdiFrame.JPopUpMenu;

/**
 * 
 * LibraryBrowser.java
 * 
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es Dec 7, 2007
 * 
 */
public class LibraryBrowser extends JPanel implements TreeModelListener {
	private static final long serialVersionUID = 4322139976928871347L;
	private ILibraryModel model;
	private JTree tree;
	private JPopUpMenu popUpMenu;
	private JMenuItem newFolder;
	private JMenuItem removeFolder;
	private ActionListener popUpMenuActions = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object selectedObject = tree.getLastSelectedPathComponent();
			Object source = e.getSource();
			removeFolder.setEnabled(selectedObject != null);
			try {
				if (source.equals(newFolder)) {
					String newFolderName = JOptionPane
							.showInputDialog(PluginServices.getText(this,
									"new_name"));
					if (newFolderName != null)
						model.addFolder(selectedObject, newFolderName);
				} else if (source.equals(removeFolder)) {
					model.removeFolder(selectedObject);
				}
			} catch (Exception ex) {
				NotificationManager.addError(
						PluginServices.getText(
								this,
								"notification_text"
										+ " ["
										+ new Date(System.currentTimeMillis())
												.toString() + "]"), ex);
			} finally {
				popUpMenu.setVisible(false);
			}
		}
	};
	private ArrayList<TreeSelectionListener> treeListeners = new ArrayList<TreeSelectionListener>();
	private ArrayList<MouseListener> treeMouseListeners = new ArrayList<MouseListener>();
	private ArrayList<MouseMotionListener> treeMouseMotionListeners = new ArrayList<MouseMotionListener>();

	public LibraryBrowser(ILibraryModel model) {
		setLayout(new BorderLayout());
		model.addTreeModelListener(this);

		popUpMenu = new JPopUpMenu();
		newFolder = new JMenuItem(PluginServices.getText(this, "new_folder"));
		newFolder.addActionListener(popUpMenuActions);

		removeFolder = new JMenuItem(PluginServices.getText(this,
				"remove_folder"));
		removeFolder.addActionListener(popUpMenuActions);

		popUpMenu.add(newFolder);
		this.model = model;
		setModel(model);
	}

	private void setModel(final ILibraryModel model) {
		if (tree != null) {
			remove(tree);
		}
		tree = new JTree();
		tree.setModel(model);
		for (int i = 0; i < treeListeners.size(); i++) {
			tree.addTreeSelectionListener(treeListeners.get(i));
		}

		for (int i = 0; i < treeMouseListeners.size(); i++) {
			tree.addMouseListener(treeMouseListeners.get(i));
		}

		for (int i = 0; i < treeMouseMotionListeners.size(); i++) {
			tree.addMouseMotionListener(treeMouseMotionListeners.get(i));
		}

		add(tree, BorderLayout.CENTER);
		validate();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			Point p = new Point();
			p.x = getLocationOnScreen().x + e.getPoint().x;
			p.y = getLocationOnScreen().y + e.getPoint().y;
			popUpMenu.setLocation(p);
			popUpMenu.setVisible(true);
		}
	}

	public void treeNodesChanged(TreeModelEvent e) {
		refresh();
	}

	public void treeNodesInserted(TreeModelEvent e) {
		refresh();
	}

	public void treeNodesRemoved(TreeModelEvent e) {
		refresh();
	}

	public void treeStructureChanged(TreeModelEvent e) {
		refresh();
	}

	public Object getLastSelectedPathComponent() {
		return tree.getLastSelectedPathComponent();
	}

	public void addTreeSelectionListener(
			TreeSelectionListener treeSelectionListener) {
		treeListeners.add(treeSelectionListener);
		tree.addTreeSelectionListener(treeSelectionListener);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		treeMouseListeners.add(l);
		tree.addMouseListener(l);
	}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		treeMouseMotionListeners.add(l);
		tree.addMouseMotionListener(l);
	}

	public void setSelectionRow(int i) {
		tree.setSelectionRow(i);
	}

	public File getElementBellow(Point point) {
		TreePath tp = tree.getPathForLocation(point.x, point.y);
		if (tp == null)
			return null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp
				.getLastPathComponent();
		if (node == null)
			return null;

		if (node.getUserObject() instanceof File) {
			return (File) node.getUserObject();
		}
		return null;
	}

	public void setSelectedElementBellow(Point point) {
		TreePath tp = tree.getPathForLocation(point.x, point.y);
		if (tp != null)
			tree.setSelectionPath(tp);
	}

	public void refresh() {
		setModel(model);
	}
}
