package com.iver.cit.gvsig.project.documents.view.toc.gui;

import geomatico.events.EventBus;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.gvsig.events.LayerSelectionChangeEvent;
import org.gvsig.events.LayerSelectionChangeHandler;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.map.MapContext;

public class TOC extends JTree implements LayerSelectionChangeHandler {

	private static final long serialVersionUID = 1L;
	private EventBus eventBus;
	private LayerCellRenderer renderer;
	private FPopupMenu popmenu;
	private MapContext mapContext;

	public TOC(EventBus eventBus, LayerFactory layerFactory) {
		assert false : "Uncomment following line";
		/*
		 * gtintegration This line was removed from View. I mean: don't forget
		 * to listen legend changes in TOC
		 */
		// m_MapControl.getMapContext().getLayers().addLegendListener(m_TOC);

		this.eventBus = eventBus;
		popmenu = new FPopupMenu();

		this.setRootVisible(false);
		this.setModel(new LayerTreeModel(eventBus, layerFactory
				.createLayer("root")));
		renderer = new LayerCellRenderer();
		this.setCellRenderer(renderer);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				int row = getRowForLocation(p.x, p.y);
				TreePath path = getPathForRow(row);
				Rectangle pathBounds = getPathBounds(path);
				if (pathBounds != null) {
					Point layerNodeLocation = pathBounds.getLocation();
					renderer.checkClick(layerNodeLocation, e.getPoint(),
							path.getLastPathComponent());
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					popmenu.update(mapContext);
					popmenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});

		this.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		this.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {

					@Override
					public void valueChanged(TreeSelectionEvent e) {
						TreePath[] paths = e.getPaths();
						for (TreePath path : paths) {
							Layer layer = (Layer) path.getLastPathComponent();
							layer.setSelected(getSelectionModel()
									.isPathSelected(path));
						}
					}
				});
		eventBus.addHandler(LayerSelectionChangeEvent.class, this);
	}

	public void setMapContext(MapContext fmap) {
		this.mapContext = fmap;
		this.setModel(new LayerTreeModel(eventBus, fmap.getRootLayer()));
	}

	@Override
	public void layerSelectionChanged(Layer source) {
		this.repaint();
	}

}
