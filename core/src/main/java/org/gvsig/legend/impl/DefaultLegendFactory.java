package org.gvsig.legend.impl;

import geomatico.events.EventBus;
import geomatico.events.EventHandler;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.events.FeatureSelectionChangeEvent;
import org.gvsig.events.FeatureSelectionChangeHandler;
import org.gvsig.inject.LibModule;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Legend;
import org.gvsig.legend.LegendFactory;
import org.opengis.filter.FilterFactory2;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class DefaultLegendFactory implements LegendFactory {
	private static final Logger logger = Logger
			.getLogger(DefaultLegendFactory.class);

	private static final DefaultLegendFactory instance;

	static {
		instance = new DefaultLegendFactory();
		Injector injector = Guice.createInjector(new LibModule());
		injector.injectMembers(instance);
	}

	public static DefaultLegendFactory getInstance() {
		return instance;
	}

	@Inject
	private FilterFactory2 filterFactory;

	@Inject
	private StyleFactory styleFactory;

	@Inject
	private EventBus eventBus;

	private List<EventHandler> handlers = new ArrayList<EventHandler>();

	@Override
	public Legend createDefaultLegend(Layer layer) {
		Class<? extends Geometry> type = layer.getShapeType();
		Rule defaultRule = createRule(type, Color.BLUE, getDefaultWidth(type));
		defaultRule.setElseFilter(true);

		Rule selectionRule = createRule(type, Color.YELLOW,
				getDefaultWidth(type));
		selectionRule.setFilter(filterFactory.id(layer.getSelection()));

		/*
		 * We need to update the rule to style selected features
		 */
		FeatureSelectionChangeHandler handler = new FeatureSelectionHandler(
				layer, selectionRule);
		handlers.add(handler);
		eventBus.addHandler(FeatureSelectionChangeEvent.class, handler);

		return new SingleSymbolLegend(defaultRule, selectionRule, styleFactory);
	}

	@Override
	public Legend createSingleSymbolLegend(Layer layer, Symbolizer symbol) {
		Rule defaultRule = styleFactory.createRule();
		defaultRule.symbolizers().add(symbol);
		defaultRule.setElseFilter(true);

		Class<? extends Geometry> type = layer.getShapeType();
		Rule selectionRule = createRule(type, Color.yellow,
				getDefaultWidth(type));
		selectionRule.setFilter(filterFactory.id(layer.getSelection()));

		FeatureSelectionChangeHandler handler = new FeatureSelectionHandler(
				layer, selectionRule);
		handlers.add(handler);
		eventBus.addHandler(FeatureSelectionChangeEvent.class, handler);

		return new SingleSymbolLegend(defaultRule, selectionRule, styleFactory);
	}

	private int getDefaultWidth(Class<? extends Geometry> type) {
		if (Point.class.isAssignableFrom(type)
				|| MultiPoint.class.isAssignableFrom(type)) {
			return 5;
		} else {
			return 1;
		}
	}

	private Rule createRule(Class<? extends Geometry> type, Color color,
			int width) {
		/*
		 * gtintegration Look here to complete
		 * 
		 * http://docs.geotools.org/latest/userguide/tutorial/map/style.html#
		 * creating-a-style-based-on-the-selection
		 */

		Symbolizer sym;

		if (Point.class.isAssignableFrom(type)
				|| MultiPoint.class.isAssignableFrom(type)) {
			Mark mark = styleFactory.getCircleMark();
			mark.setStroke(styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(0)));
			mark.setFill(styleFactory.createFill(filterFactory.literal(color)));

			Graphic graphic = styleFactory.createDefaultGraphic();
			graphic.graphicalSymbols().clear();
			graphic.graphicalSymbols().add(mark);
			graphic.setSize(filterFactory.literal(width));
			sym = styleFactory.createPointSymbolizer(graphic, null);
		} else if (LineString.class.isAssignableFrom(type)
				|| MultiLineString.class.isAssignableFrom(type)) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(width));
			sym = styleFactory.createLineSymbolizer(stroke, null);
		} else if (Polygon.class.isAssignableFrom(type)
				|| MultiPolygon.class.isAssignableFrom(type)) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(width));
			Fill fill = styleFactory.createFill(filterFactory.literal(color),
					filterFactory.literal(0));
			sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);
		} else {
			RuntimeException e = new RuntimeException(
					"bug! Unsupported shape type for symbology: " + type);
			logger.error(e);
			throw e;
		}
		sym.setDescription(new DescriptionImpl("", ""));

		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(sym);
		return rule;
	}

	private class FeatureSelectionHandler implements
			FeatureSelectionChangeHandler {
		private Layer layer;
		private Rule selectionRule;

		public FeatureSelectionHandler(Layer layer, Rule selectionRule) {
			this.layer = layer;
			this.selectionRule = selectionRule;
		}

		@Override
		public void featureSelectionChange(Layer source) {
			if (source == layer) {
				selectionRule.setFilter(filterFactory.id(layer.getSelection()));
			}
		}
	}
}
