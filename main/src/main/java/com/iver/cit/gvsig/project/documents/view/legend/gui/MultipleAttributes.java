package com.iver.cit.gvsig.project.documents.view.legend.gui;

import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;

public class MultipleAttributes extends AbstractParentPanel {
	@Override
	public String getDescription() {
		return PluginServices.getText(this, "multiple_atributes");
	}

	@Override
	public String getTitle() {
		return PluginServices.getText(this, "multiple_atributes");
	}

	@Override
	public boolean isSuitableFor(Layer layer) {
		return layer.hasFeatures();
	}
}
