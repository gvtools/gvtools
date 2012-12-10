package com.iver.cit.gvsig.project.documents.view.toc.gui;

import geomatico.events.EventBus;

import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.gvsig.events.LayerAddedEvent;
import org.gvsig.events.LayerAddedHandler;
import org.gvsig.events.LayerNameChangeEvent;
import org.gvsig.events.LayerNameChangeHandler;
import org.gvsig.events.LayerRemovedEvent;
import org.gvsig.events.LayerRemovedHandler;
import org.gvsig.events.LayerVisibilityChangeEvent;
import org.gvsig.events.LayerVisibilityChangeHandler;
import org.gvsig.layer.Layer;

public class LayerTreeModel implements TreeModel, LayerAddedHandler,
		LayerRemovedHandler, LayerVisibilityChangeHandler,
		LayerNameChangeHandler {

	private ArrayList<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private Layer root;

	public LayerTreeModel(EventBus eventBus, Layer rootLayer) {
		eventBus.addHandler(LayerAddedEvent.class, this);
		eventBus.addHandler(LayerRemovedEvent.class, this);
		eventBus.addHandler(LayerVisibilityChangeEvent.class, this);
		eventBus.addHandler(LayerNameChangeEvent.class, this);
		this.root = rootLayer;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((Layer) parent).getChildren()[index];
	}

	@Override
	public int getChildCount(Object parent) {
		return ((Layer) parent).getChildren().length;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Layer[] children = ((Layer) parent).getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] == child) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((Layer) node).getChildren().length == 0;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		Layer layer = (Layer) path.getLastPathComponent();
		layer.setVisible((Boolean) newValue);
		for (TreeModelListener treeModelListener : listeners) {
			treeModelListener.treeStructureChanged(new TreeModelEvent(this,
					buildPath(layer).getParentPath()));
		}
	}

	@Override
	public void layerAdded(Layer layer) {
		structureChanged(layer, buildPath(layer).getParentPath());
	}

	private void structureChanged(Layer layer, TreePath pathToUpdate) {
		if (root.contains(layer)) {
			for (TreeModelListener treeModelListener : listeners) {
				treeModelListener.treeStructureChanged(new TreeModelEvent(this,
						pathToUpdate));
			}
		}
	}

	@Override
	public void layerRemoved(Layer parent, Layer removed) {
		structureChanged(parent, buildPath(parent));
	}

	@Override
	public void visibilityChanged(Layer source) {
		layerChanged(source);
	}

	private void layerChanged(Layer source) {
		if (root.contains(source)) {
			for (TreeModelListener treeModelListener : listeners) {
				treeModelListener.treeNodesChanged(new TreeModelEvent(this,
						buildPath(source)));
			}
		}
	}

	@Override
	public void layerNameChanged(Layer source) {
		layerChanged(source);
	}

	private TreePath buildPath(Layer layer) {
		Layer parent = layer.getParent();
		if (parent != null) {
			return buildPath(parent).pathByAddingChild(layer);
		} else {
			return new TreePath(layer);
		}
	}

}
