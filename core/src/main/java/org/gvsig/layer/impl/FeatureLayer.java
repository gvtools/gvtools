package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.events.FeatureSelectionChangeEvent;
import org.gvsig.events.LayerLegendChangeEvent;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;
import org.opengis.filter.FilterFactory2;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class FeatureLayer extends AbstractLayer implements Layer {
	private static final Logger logger = Logger.getLogger(FeatureLayer.class);

	private boolean editing, active;
	private Source source;
	private Style style;
	private Selection selection = new Selection();

	private SourceFactory sourceFactory;
	private FeatureSourceCache featureSourceCache;
	private StyleFactory styleFactory;
	private FilterFactory2 filterFactory;
	private Rule selectionRule;

	FeatureLayer(EventBus eventBus, FeatureSourceCache featureSourceCache,
			SourceFactory sourceFactory, StyleFactory styleFactory,
			FilterFactory2 filterFactory, String name) {
		super(eventBus, name);
		this.featureSourceCache = featureSourceCache;
		this.sourceFactory = sourceFactory;
		this.styleFactory = styleFactory;
		this.filterFactory = filterFactory;
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

		/*
		 * We need to update the rule to style selected features
		 */
		getSelectionRule().setFilter(filterFactory.id(this.selection));
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

	public Rule getSelectionRule() {
		if (selectionRule == null) {
			buildStyle();
		}

		return selectionRule;
	}

	@Override
	public Style getStyle() {
		if (style == null) {
			buildStyle();
		}

		return style;
	}

	@Override
	public void setStyle(Style style) {
		if (this.style != style) {
			this.style = style;
			eventBus.fireEvent(new LayerLegendChangeEvent(this));
		}
	}

	private void buildStyle() {
		int width;
		if (Point.class.isAssignableFrom(getShapeType())
				|| MultiPoint.class.isAssignableFrom(getShapeType())) {
			width = 5;
		} else {
			width = 1;
		}

		Rule defaultRule = createRule(Color.BLUE, width);
		defaultRule.setElseFilter(true);

		selectionRule = createRule(Color.YELLOW, width);
		selectionRule.setFilter(filterFactory.id(getSelection()));

		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] {
				defaultRule, selectionRule });
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		this.style = style;
	}

	private Rule createRule(Color color, int width) {
		/*
		 * gtintegration Look here to complete
		 * 
		 * http://docs.geotools.org/latest/userguide/tutorial/map/style.html#
		 * creating-a-style-based-on-the-selection
		 */

		Symbolizer sym;

		if (Point.class.isAssignableFrom(getShapeType())
				|| MultiPoint.class.isAssignableFrom(getShapeType())) {
			Mark mark = styleFactory.getCircleMark();
			mark.setStroke(styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(0)));
			mark.setFill(styleFactory.createFill(filterFactory.literal(color)));

			Graphic graphic = styleFactory.createDefaultGraphic();
			graphic.graphicalSymbols().clear();
			graphic.graphicalSymbols().add(mark);
			graphic.setSize(filterFactory.literal(width));
			sym = styleFactory.createPointSymbolizer(graphic, null);
		} else if (LineString.class.isAssignableFrom(getShapeType())
				|| MultiLineString.class.isAssignableFrom(getShapeType())) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(width));
			sym = styleFactory.createLineSymbolizer(stroke, null);
		} else if (Polygon.class.isAssignableFrom(getShapeType())
				|| MultiPolygon.class.isAssignableFrom(getShapeType())) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(width));
			Fill fill = styleFactory.createFill(filterFactory.literal(color),
					filterFactory.literal(0));
			sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);
		} else {
			logger.warn("Unsupported shape type for symbology: "
					+ getShapeType() + ". Falling back to point style.");
			Mark mark = styleFactory.getCircleMark();
			mark.setStroke(styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(1)));
			mark.setFill(styleFactory.createFill(filterFactory.literal(color)));

			Graphic graphic = styleFactory.createDefaultGraphic();
			graphic.graphicalSymbols().clear();
			graphic.graphicalSymbols().add(mark);
			graphic.setSize(filterFactory.literal(5));
			sym = styleFactory.createPointSymbolizer(graphic, null);
		}
		sym.setDescription(new DescriptionImpl("", ""));

		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(sym);
		return rule;
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
				getFeatureSource(), getStyle());
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
