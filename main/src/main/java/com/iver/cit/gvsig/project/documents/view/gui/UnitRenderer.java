package com.iver.cit.gvsig.project.documents.view.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.gvsig.units.Unit;

import com.iver.andami.PluginServices;

public class UnitRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;
	private boolean square;

	public UnitRenderer(boolean square) {
		this.square = square;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Unit unit = (Unit) value;
		String text = PluginServices.getText(this, unit.name);
		if (square) {
			text += unit.getSquareSuffix();
		}

		return super.getListCellRendererComponent(list, text, index,
				isSelected, cellHasFocus);
	}

}