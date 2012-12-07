package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.gvsig.layer.Layer;

public class LayerCellRenderer extends JPanel implements TreeCellRenderer {
	private static final long serialVersionUID = 1L;
	private JCheckBox check;
	private JLabel label;

	public LayerCellRenderer() {
		check = new JCheckBox();
		label = new JLabel();
		this.add(check);
		this.add(label);
		this.setOpaque(false);
		check.setOpaque(false);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		Layer layer = (Layer) value;
		label.setText(layer.getName());
		check.setSelected(layer.isVisible());

		if (layer.isSelected()) {
			this.setBorder(BorderFactory.createEtchedBorder());
		} else {
			this.setBorder(null);
		}

		if (layer.isEditing()) {
			this.label.setForeground(Color.RED);
		} else {
			this.label.setForeground(Color.BLACK);
		}
		return this;
	}

	public void checkClick(Point layerNodeLocation, Point clickPoint,
			Object object) {
		Rectangle bounds = check.getBounds();
		bounds.translate((int) layerNodeLocation.getX(),
				(int) layerNodeLocation.getY());

		Layer layer = (Layer) object;

		if (bounds.contains(clickPoint)) {
			layer.setVisible(!layer.isVisible());
		}
	}
}
