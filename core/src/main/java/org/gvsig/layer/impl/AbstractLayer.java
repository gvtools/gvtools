package org.gvsig.layer.impl;

import org.gvsig.layer.Layer;

public abstract class AbstractLayer implements Layer {

	private boolean visible;
	private boolean selected;

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
