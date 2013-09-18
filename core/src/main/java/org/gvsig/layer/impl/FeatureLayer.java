package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.geotools.data.Base64;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.FeatureTypeConstraint;
import org.geotools.styling.SLDTransformer;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyledLayerDescriptor;
import org.geotools.styling.UserLayer;
import org.gvsig.events.FeatureSelectionChangeEvent;
import org.gvsig.events.LayerLegendChangeEvent;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Legend;
import org.gvsig.legend.impl.SingleSymbolLegend;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;
import org.opengis.filter.FilterFactory2;

import com.vividsolutions.jts.geom.Geometry;

public class FeatureLayer extends AbstractLayer implements Layer {
	private static final Logger logger = Logger.getLogger(FeatureLayer.class);

	private boolean editing, active;
	private Source source;
	private Legend legend;
	private Selection selection = new Selection();

	private SourceFactory sourceFactory;
	private FeatureSourceCache featureSourceCache;
	private StyleFactory styleFactory;
	private FilterFactory2 filterFactory;
	private DefaultSymbols defaultSymbols;

	FeatureLayer(EventBus eventBus, FeatureSourceCache featureSourceCache,
			SourceFactory sourceFactory, StyleFactory styleFactory,
			FilterFactory2 filterFactory, DefaultSymbols defaultSymbols,
			String name) {
		super(eventBus, name);
		this.featureSourceCache = featureSourceCache;
		this.sourceFactory = sourceFactory;
		this.styleFactory = styleFactory;
		this.defaultSymbols = defaultSymbols;
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
		legend.updateSelection(this.selection);
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
		legend = new SingleSymbolLegend(this, styleFactory, filterFactory,
				defaultSymbols);
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

		try {
			UserLayer layer = styleFactory.createUserLayer();
			layer.setLayerFeatureConstraints(new FeatureTypeConstraint[] { null });
			layer.addUserStyle(legend.getStyle());

			StyledLayerDescriptor sld = styleFactory
					.createStyledLayerDescriptor();
			sld.addStyledLayer(layer);

			String styleEncoded = Base64.encodeBytes(new SLDTransformer()
					.transform(sld).getBytes());
			xml.setStyle(styleEncoded);
		} catch (TransformerException e) {
			logger.error("Cannot store SLD style", e);
		}

		return xml;
	}

	void setXML(LayerType layer) {
		assert layer instanceof DataLayerType;

		super.read(layer);

		DataLayerType dataLayerType = (DataLayerType) layer;
		source = sourceFactory.createSource(dataLayerType.getSource());
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
