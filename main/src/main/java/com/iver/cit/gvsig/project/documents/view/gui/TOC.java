package com.iver.cit.gvsig.project.documents.view.gui;

import geomatico.events.EventBus;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.gvsig.layer.LayerFactory;
import org.gvsig.map.MapContext;

public class TOC extends JTree {

	private static final long serialVersionUID = 1L;
	private EventBus eventBus;
	private LayerCellRenderer renderer;

	public TOC(EventBus eventBus, LayerFactory layerFactory) {
		assert false : "Uncomment following line";
		/*
		 * gtintegration This line was removed from View. I mean: don't forget
		 * to listen legend changes in TOC
		 */
		// m_MapControl.getMapContext().getLayers().addLegendListener(m_TOC);

		this.eventBus = eventBus;

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
			}

		});
	}

	public void setMapContext(MapContext fmap) {
		this.setModel(new LayerTreeModel(eventBus, fmap.getRootLayer()));
	}

}
