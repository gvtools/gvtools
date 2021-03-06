package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import org.gvsig.events.LayerNameChangeEvent;
import org.gvsig.events.LayerSelectionChangeEvent;
import org.gvsig.events.LayerVisibilityChangeEvent;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerProcessor;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.LayerType;

public abstract class AbstractLayer implements Layer {
	protected EventBus eventBus;

	private boolean visible = true;
	private boolean selected;
	private String name;
	private Layer parent;

	public AbstractLayer(EventBus eventBus, String name) {
		this.eventBus = eventBus;
		this.name = name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		eventBus.fireEvent(new LayerNameChangeEvent(this));
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Layer getParent() {
		return parent;
	}

	@Override
	public void setParent(Layer parent) {
		this.parent = parent;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		eventBus.fireEvent(new LayerVisibilityChangeEvent(this));
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public void setSelected(boolean selected) {
		if (selected != this.selected) {
			this.selected = selected;
			eventBus.fireEvent(new LayerSelectionChangeEvent(this));
		}
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	void fill(LayerType xml) {
		xml.setName(name);
		xml.setVisible(visible);
		xml.setSelected(selected);
	}

	void read(LayerType type) {
		this.name = type.getName();
		this.visible = type.isVisible();
		this.selected = type.isSelected();
	}

	@Override
	public void process(LayerFilter layerFilter, LayerProcessor processor) {
		if (layerFilter.accepts(this)) {
			processor.process(this);
		}
		Layer[] children = getChildren();
		for (Layer layer : children) {
			layer.process(layerFilter, processor);
		}
	}

	@Override
	public void process(LayerProcessor processor) {
		processor.process(this);
		Layer[] children = getChildren();
		for (Layer layer : children) {
			layer.process(processor);
		}
	}
}
