package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.gvsig.events.LayerAddedEvent;
import org.gvsig.events.LayerRemovedEvent;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Selection;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.legend.Legend;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.LayerType;

import com.vividsolutions.jts.geom.Geometry;

public class CompositeLayer extends AbstractLayer implements Layer {
	private List<Layer> layers = new ArrayList<Layer>();
	private LayerFactory layerFactory;

	public CompositeLayer(EventBus eventBus, LayerFactory layerFactory,
			String name) {
		super(eventBus, name);
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
			Layer[] childResults = layer.filter(filter);
			for (Layer childResult : childResults) {
				ret.add(childResult);
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
		addLayer(layers.size(), layer);
	}

	@Override
	public void addLayer(int position, Layer layer)
			throws UnsupportedOperationException, IllegalArgumentException {
		if (layer == null) {
			throw new IllegalArgumentException("Layer cannot be null");
		} else if (layer.getParent() != null) {
			throw new IllegalArgumentException("The layer already has a parent");
		}
		layers.add(position, layer);
		layer.setParent(this);
		eventBus.fireEvent(new LayerAddedEvent(layer));
	}

	@Override
	public int indexOf(Layer layer) {
		for (int i = 0; i < layers.size(); i++) {
			Layer testLayer = layers.get(i);
			if (testLayer == layer) {
				return i;
			}
		}

		return -1;
	}

	public boolean removeLayer(Layer layer) {
		boolean ret = layers.remove(layer);
		layer.setParent(null);
		eventBus.fireEvent(new LayerRemovedEvent(this, layer));
		return ret;
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

		super.fill(xml);

		List<LayerType> xmlLayers = xml.getLayers();
		xmlLayers.clear();
		for (Layer layer : layers) {
			xmlLayers.add(layer.getXML());
		}
		return xml;
	}

	void setXML(LayerType type) {
		assert type instanceof CompositeLayerType;

		super.read(type);

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
	public Selection getSelection() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelection(Selection newSelection)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SimpleFeatureSource getFeatureSource()
			throws UnsupportedOperationException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLegend(Legend style) {
		throw new UnsupportedOperationException("Cannot set style on groups");
	}

	@Override
	public Legend getLegend() {
		throw new UnsupportedOperationException("Cannot get style on groups");
	}

	@Override
	public Class<? extends Geometry> getShapeType() {
		throw new UnsupportedOperationException(
				"Cannot get shape type on groups");
	}
}