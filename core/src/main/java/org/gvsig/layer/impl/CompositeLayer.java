package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.Style;
import org.gvsig.events.LayerAddedEvent;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.LayerType;

public class CompositeLayer extends AbstractLayer implements Layer {
	private List<Layer> layers = new ArrayList<Layer>();
	private EventBus eventBus;
	private LayerFactory layerFactory;

	public CompositeLayer(EventBus eventBus, LayerFactory layerFactory) {
		this.eventBus = eventBus;
		this.layerFactory = layerFactory;
	}

	@Override
	public boolean contains(Layer layer) {
		if (layer == this) {
			return true;
		} else {
			for (Layer l : layers) {
				if (l.contains(layer)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public Layer[] getChildren() {
		return this.layers.toArray(new Layer[this.layers.size()]);
	}

	@Override
	public Layer[] getAllLayersInTree() {
		ArrayList<Layer> ret = new ArrayList<Layer>();
		ret.add(this);
		for (Layer layer : this.layers) {
			Collections.addAll(ret, layer.getAllLayersInTree());
		}
		return ret.toArray(new Layer[ret.size()]);
	}

	@Override
	public Layer[] filter(LayerFilter filter) {
		List<Layer> ret = new ArrayList<Layer>();
		if (filter.accepts(this)) {
			ret.add(this);
		}

		for (Layer layer : layers) {
			if (filter.accepts(layer)) {
				ret.add(layer);
			}
		}
		return ret.toArray(new Layer[ret.size()]);
	}

	@Override
	public boolean isEditing() {
		return false;
	}

	@Override
	public boolean hasFeatures() {
		return false;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	public void addLayer(Layer layer) {
		if (layer == null) {
			throw new IllegalArgumentException("Layer cannot be null");
		}
		layers.add(layer);
		eventBus.fireEvent(new LayerAddedEvent(layer));
	}

	public boolean removeLayer(Layer layer) {
		return layers.remove(layer);
	}

	@Override
	public Style getStyle() {
		throw new UnsupportedOperationException(
				"Layer groups do not have style property");
	}

	@Override
	public void setStyle(Style style) {
		throw new UnsupportedOperationException(
				"Layer groups do not have style property");
	}

	@Override
	public Collection<org.geotools.map.Layer> getDrawingLayers()
			throws IOException {
		ArrayList<org.geotools.map.Layer> ret = new ArrayList<org.geotools.map.Layer>();
		for (Layer layer : this.layers) {
			ret.addAll(layer.getDrawingLayers());
		}

		return ret;
	}

	@Override
	public ReferencedEnvelope getBounds() throws IOException {
		ReferencedEnvelope ret = null;
		for (Layer layer : this.layers) {
			if (ret == null) {
				ret = new ReferencedEnvelope(layer.getBounds());
			} else {
				ret.expandToInclude(layer.getBounds());
			}
		}

		return ret;
	}

	@Override
	public LayerType getXML() {
		CompositeLayerType xml = new CompositeLayerType();

		List<LayerType> xmlLayers = xml.getLayers();
		xmlLayers.clear();
		for (Layer layer : layers) {
			xmlLayers.add(layer.getXML());
		}
		return xml;
	}

	void setXML(LayerType type) {
		assert type instanceof CompositeLayerType;

		CompositeLayerType compositeLayerType = (CompositeLayerType) type;
		List<LayerType> xmlLayers = compositeLayerType.getLayers();
		for (LayerType layer : xmlLayers) {
			addLayer(layerFactory.createLayer(layer));
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		for (Layer layer : this.layers) {
			layer.setVisible(visible);
		}
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		for (Layer layer : this.layers) {
			layer.setSelected(selected);
		}
	}
}