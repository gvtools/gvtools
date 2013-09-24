package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.identity.FeatureIdImpl;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.gvsig.events.FeatureSelectionChangeEvent;
import org.gvsig.events.LayerLegendChangeEvent;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.legend.Legend;
import org.gvsig.legend.impl.LegendFactory;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;
import org.opengis.filter.identity.FeatureId;

import com.vividsolutions.jts.geom.Geometry;

public class FeatureLayer extends AbstractLayer implements Layer {
	private boolean editing, active;
	private Source source;
	private Legend legend;
	private Selection selection = new Selection();

	private SourceFactory sourceFactory;
	private FeatureSourceCache featureSourceCache;
	private LegendFactory legendFactory;

	FeatureLayer(EventBus eventBus, FeatureSourceCache featureSourceCache,
			SourceFactory sourceFactory, LegendFactory legendFactory,
			String name) {
		super(eventBus, name);
		this.featureSourceCache = featureSourceCache;
		this.sourceFactory = sourceFactory;
		this.legendFactory = legendFactory;
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
	public void setSelection(Selection newSelection)
			throws UnsupportedOperationException {
		this.selection = newSelection;
		getLegend().updateSelection(this.selection);
		eventBus.fireEvent(new FeatureSelectionChangeEvent(this));
	}

	@Override
	public Selection getSelection() throws UnsupportedOperationException {
		return selection;
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
	public Legend getLegend() {
		if (legend == null) {
			buildLegend();
		}

		return legend;
	}

	@Override
	public void setLegend(Legend legend) {
		if (this.legend != legend) {
			this.legend = legend;
			eventBus.fireEvent(new LayerLegendChangeEvent(this));
		}
	}

	private void buildLegend() {
		legend = legendFactory.createSingleSymbolLegend(this);
	}

	@Override
	public Collection<org.geotools.map.Layer> getDrawingLayers()
			throws IOException {
		if (isVisible()) {
			return Collections.singletonList(getGTLayer());
		} else {
			return Collections.emptyList();
		}
	}

	private org.geotools.map.Layer getGTLayer() throws IOException {
		org.geotools.map.Layer layer = new org.geotools.map.FeatureLayer(
				getFeatureSource(), getLegend().getStyle());
		return layer;
	}

	@Override
	public void addLayer(Layer testLayer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLayer(int position, Layer testLayer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Layer layer) {
		return -1;
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

		super.fill(xml);

		xml.setSource(source.getXML());
		xml.getSelection().clear();
		Iterator<FeatureId> iterator = selection.iterator();
		while (iterator.hasNext()) {
			xml.getSelection().add(iterator.next().getID());
		}

		return xml;
	}

	void setXML(LayerType layer) {
		assert layer instanceof DataLayerType;

		super.read(layer);

		DataLayerType dataLayerType = (DataLayerType) layer;
		source = sourceFactory.createSource(dataLayerType.getSource());

		dataLayerType.getSelection();
		this.selection = new Selection();
		for (String id : dataLayerType.getSelection()) {
			this.selection.add(new FeatureIdImpl(id));
		}
	}

	@Override
	public SimpleFeatureSource getFeatureSource()
			throws UnsupportedOperationException, IOException {
		return featureSourceCache.getFeatureSource(source);
	}

	@Override
	public Class<? extends Geometry> getShapeType() {
		// TODO Auto-generated method stub
		try {
			return (Class<? extends Geometry>) getFeatureSource().getSchema()
					.getGeometryDescriptor().getType().getBinding();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
