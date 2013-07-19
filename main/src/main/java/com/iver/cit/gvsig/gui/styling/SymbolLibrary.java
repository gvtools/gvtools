/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
package com.iver.cit.gvsig.gui.styling;

import java.awt.Component;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.exolab.castor.xml.Marshaller;
import org.geotools.styling.Symbolizer;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.iver.utiles.XMLEntity;

/**
 * 
 * SymbolLibrary.java
 * 
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es Dec 7, 2007
 * 
 */
public class SymbolLibrary extends DefaultTreeModel implements ILibraryModel {
	static protected String rootDirString;
	private File rootDir;
	private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();
	private static SymbolLibrary instance = null;

	public static SymbolLibrary getInstance() {
		if (instance == null
				|| rootDirString != SymbologyUtils.SymbolLibraryPath) {
			rootDirString = SymbologyUtils.SymbolLibraryPath;
			instance = new SymbolLibrary(new File(rootDirString));
		}
		return instance;
	}

	protected SymbolLibrary(File rootDir) {
		super(new DefaultMutableTreeNode(rootDir));
		rootDirString = PluginServices.getText(this, "symbol_library");
		this.rootDir = rootDir;
	}

	final class MyFile extends File {
		private static final long serialVersionUID = 6332989815291224773L;

		public MyFile(String pathname) {
			super(pathname);
		}

		public String toString() {

			String path = getAbsolutePath();
			if (path.equals(rootDir.getAbsolutePath()))
				return rootDirString;
			return path.substring(path.lastIndexOf(File.separator) + 1,
					path.length());
		}
	};

	private FileFilter ff = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	public static final String SYMBOL_FILE_EXTENSION = ".sym";

	@Override
	public Object getRoot() {
		return new DefaultMutableTreeNode(new MyFile(rootDir.getAbsolutePath()));
	}

	@Override
	public int getChildCount(Object parent) {
		File f = null;
		if (parent instanceof DefaultMutableTreeNode) {
			f = (File) ((DefaultMutableTreeNode) parent).getUserObject();

		} else {
			f = (File) parent;
		}
		File[] files = f.listFiles(ff);
		return files == null ? 0 : files.length;
	}

	@Override
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public Object getChild(Object parent, int index) {
		File file = ((File) (((DefaultMutableTreeNode) parent).getUserObject()))
				.listFiles(ff)[index];
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new MyFile(
				file.getAbsolutePath()));
		node.setParent((DefaultMutableTreeNode) parent);
		return node;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == null)
			return -1;
		File[] files = ((File) ((DefaultMutableTreeNode) parent)
				.getUserObject()).listFiles(ff);
		for (int i = 0; i < files.length; i++) {
			if (files[i].equals(child))
				return i;
		}
		return -1;
	}

	private Object getChildFile(Object parent, int index) {
		File file = ((File) (((DefaultMutableTreeNode) parent).getUserObject()))
				.listFiles()[index];
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new MyFile(
				file.getAbsolutePath()));
		node.setParent((DefaultMutableTreeNode) parent);
		return node;
	}

	private int getIndexOfChildFiles(Object parent, Object child) {
		if (parent == null)
			return -1;
		File[] files = ((File) ((DefaultMutableTreeNode) parent)
				.getUserObject()).listFiles();
		for (int i = 0; files != null && i < files.length; i++) {
			if (files[i].getName().equals(child))
				return i;
		}
		return -1;
	}

	public Object getElement(Object containerFolder, String elementName) {
		if (containerFolder instanceof File) {
			containerFolder = new DefaultMutableTreeNode(containerFolder);
		}
		int index = getIndexOfChildFiles(containerFolder, elementName);
		if (index != -1) {
			return getChildFile(containerFolder, index);
		} else {
			for (int i = 0; i < getChildCount(containerFolder); i++) {
				Object o = getElement(getChildFile(containerFolder, i),
						elementName);
				if (o != null) {
					return o;
				}
			}
		}
		return null;
	}

	public void addElement(Object element, String elementName,
			Object containerFolder) {
		if (element instanceof Symbolizer) {
			Symbolizer sym = (Symbolizer) element;
			if (containerFolder == null) {
				containerFolder = rootDir;
			}

			String fExtension = SymbolLibrary.SYMBOL_FILE_EXTENSION;
			File targetFile = new File(
					((File) containerFolder).getAbsolutePath() + File.separator
							+ elementName);
			// save it
			XMLEntity xml = SymbologyUtils.getXMLEntity(sym);
			if (!targetFile.getAbsolutePath().toLowerCase()
					.endsWith(fExtension))
				targetFile = new File(targetFile.getAbsolutePath() + fExtension);
			if (targetFile.exists()) {
				int resp = JOptionPane.showConfirmDialog(
						(Component) PluginServices.getMainFrame(),
						PluginServices.getText(this,
								"fichero_ya_existe_seguro_desea_guardarlo"),
						PluginServices.getText(this, "guardar"),
						JOptionPane.YES_NO_OPTION);
				if (resp != JOptionPane.YES_OPTION) {
					return;
				}
			}
			FileWriter writer;
			try {
				writer = new FileWriter(targetFile.getAbsolutePath());
				Marshaller m = new Marshaller(writer);
				m.setEncoding("ISO-8859-1");
				m.marshal(xml.getXmlTag());

			} catch (Exception ex) {
				NotificationManager.addError(
						PluginServices.getText(this, "save_error"), ex);
			}
		} else {
			throw new IllegalArgumentException(PluginServices.getText(this,
					"adding_a_non_symbol_as_element"));
		}
	}

	public void addFolder(Object parentFolder, String folderName) {
		if (parentFolder == null) {
			parentFolder = rootDir;
		}
		try {
			File fParentFolder = (File) ((DefaultMutableTreeNode) parentFolder)
					.getUserObject();
			File f = new File(fParentFolder.getAbsolutePath() + File.separator
					+ folderName);
			if (!f.exists())
				f.mkdir();
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).treeNodesInserted(null);
			}
		} catch (ConcurrentModificationException cme) {
			cme.printStackTrace();
		} catch (Exception e) {
			throw new IllegalArgumentException(PluginServices.getText(this,
					"invalid_folder_name"), e);
		}
	}

	public void removeElement(Object element, Object containerFolder) {
		try {
			File fParentFolder = (File) containerFolder;
			File f = new File(fParentFolder.getAbsolutePath() + File.separator
					+ (String) element);
			if (f.exists())
				deleteRecursively(f);

		} catch (Exception e) {
			throw new IllegalArgumentException(PluginServices.getText(this,
					"invalid_folder_name"));
		}
	}

	public void removeFolder(Object folderToRemove) {
		try {
			File f = (File) folderToRemove;

			TreePath tp = treePathNode(f, rootDir);
			if (f.exists())
				f.delete();
		} catch (Exception e) {
			throw new IllegalArgumentException(PluginServices.getText(this,
					"invalid_folder_name"));
		}
	}

	private void deleteRecursively(File f) {
		if (f.isDirectory()) {
			for (int i = f.list().length - 1; i >= 0; i--) {
				deleteRecursively(new File(f.getAbsolutePath() + File.separator
						+ f.list()[i]));
			}
		}
		f.delete();
	}

	private TreePath treePathNode(File f, File startingNode) {
		for (int i = 0; i < getChildCount(startingNode); i++) {
			File child = (File) getChild(startingNode, i);
			TreePath tp = treePathNode(f, child);
			if (tp == null) {
				// it is not in this directory tree
				continue;
			} else {
				if (startingNode != rootDir)
					tp = tp.pathByAddingChild(startingNode);
				return tp;
			}
		}

		if (f.equals(startingNode)) {
			return new TreePath(f);
		}
		return null;
	}

	private void fireTreeNodesRemoved(TreePath removedObjectTreePath) {
		TreeModelEvent e = new TreeModelEvent(this, removedObjectTreePath);
		for (Iterator<TreeModelListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			iterator.next().treeNodesRemoved(e);
		}
	}

}
