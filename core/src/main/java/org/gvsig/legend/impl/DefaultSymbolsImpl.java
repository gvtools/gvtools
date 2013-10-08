package org.gvsig.legend.impl;

import java.awt.Color;

import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.Stroke;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.legend.DefaultSymbols;
import org.opengis.filter.FilterFactory2;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class DefaultSymbolsImpl implements DefaultSymbols {
	@Inject
	private StyleFactory styleFactory;

	@Inject
	private FilterFactory2 filterFactory;

	@Override
	public Symbolizer createDefaultSymbol(Class<? extends Geometry> type,
			Color color, String description) {
		int size = (Point.class.isAssignableFrom(type) || MultiPoint.class
				.isAssignableFrom(type)) ? 5 : 1;
		return createDefaultSymbol(type, color, size, description);
	}

	@Override
	public Symbolizer createDefaultSymbol(Class<? extends Geometry> type,
			Color color, int size, String description) {
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
			graphic.setSize(filterFactory.literal(size));
			sym = styleFactory.createPointSymbolizer(graphic, null);
		} else if (LineString.class.isAssignableFrom(type)
				|| MultiLineString.class.isAssignableFrom(type)) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(color), filterFactory.literal(size));
			sym = styleFactory.createLineSymbolizer(stroke, null);
		} else if (Polygon.class.isAssignableFrom(type)
				|| MultiPolygon.class.isAssignableFrom(type)) {
			Stroke stroke = styleFactory.createStroke(
					filterFactory.literal(Color.gray),
					filterFactory.literal(size));
			Fill fill = styleFactory.createFill(filterFactory.literal(color));
			sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);
		} else {
			RuntimeException e = new RuntimeException(
					"bug! Unsupported shape type for symbology: " + type);
			throw e;
		}

		if (description != null) {
			sym.setDescription(new DescriptionImpl(description, description));
		} else {
			sym.setDescription(new DescriptionImpl("", ""));
		}
		return sym;
	}
}
