package org.gvsig.layer.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.Style;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.SymbolFactoryFacade;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;

public class FeatureLayer implements Layer {
	private boolean editing, active;
	private Source source;
	private Style style;

	private SourceFactory sourceFactory;
	private FeatureSourceCache featureSourceCache;
	private SymbolFactoryFacade symbolFactoryFacade;

	FeatureLayer(FeatureSourceCache featureSourceCache,
			SymbolFactoryFacade symbolFactoryFacade, SourceFactory sourceFactory) {
		this.featureSourceCache = featureSourceCache;
		this.symbolFactoryFacade = symbolFactoryFacade;
		this.sourceFactory = sourceFactory;
	}

	void setSource(Source source) {
		this.source = source;
	}

	@Override
	public boolean contains(Layer layer) {
		return this == layer;
	}

	@Override
	public Layer[] getChildren() {
		return new Layer[0];
	}

	@Override
	public Layer[] getAllLayersInTree() {
		return new Layer[] { this };
	}

	@Override
	public Layer[] filter(LayerFilter filter) {
		if (filter.accepts(this)) {
			return new Layer[] { this };
		} else {
			return new Layer[0];
		}
	}

	@Override
	public boolean isEditing() {
		return editing;
	}

	@Override
	public boolean hasFeatures() {
		return true;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setStyle(Style style) {
		this.style = style;
	}

	@Override
	public Style getStyle() {
		if (style == null) {
			style = symbolFactoryFacade.newLineStyle(Color.BLUE, 1);
		}

		return style;
	}

	@Override
	public Collection<org.geotools.map.Layer> getDrawingLayers()
			throws IOException {
		return Collections.singletonList(getGTLayer());
	}

	private org.geotools.map.Layer getGTLayer() throws IOException {
		SimpleFeatureSource featureSource = featureSourceCache
				.getFeatureSource(source);
		org.geotools.map.Layer layer = new org.geotools.map.FeatureLayer(
				featureSource, getStyle());
		return layer;
	}

	@Override
	public void addLayer(Layer testLayer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ReferencedEnvelope getBounds() throws IOException {
		return getGTLayer().getBounds();
	}

	@Override
	public boolean removeLayer(Layer layer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public LayerType getXML() {
		DataLayerType xml = new DataLayerType();
		xml.setSource(source.getXML());
		return xml;
	}

	void setXML(LayerType layer) {
		assert layer instanceof DataLayerType;

		DataLayerType dataLayerType = (DataLayerType) layer;
		source = sourceFactory.createSource(dataLayerType.getSource());
	}

}
