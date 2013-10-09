package org.gvsig.legend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.SizeIntervalLegend;
import org.junit.Test;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class SizeIntervalLegendTest extends AbstractLegendTest {
	@Test
	public void testLegend() throws Exception {
		Interval size = new Interval(1, 7);
		int nIntervals = 4;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		Symbolizer template = defaultSymbols.createDefaultSymbol(Point.class,
				Color.lightGray, "");
		SizeIntervalLegend legend = legendFactory.createSizeIntervalLegend(
				size, Type.EQUAL, defaultSymbol, true, mockLayer(), FIELD_NAME,
				nIntervals, template, null);

		assertEquals(nIntervals, legend.getIntervals().length);
		assertEquals(nIntervals, legend.getSymbols().length);
		assertEquals(FIELD_NAME, legend.getFieldName());
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertEquals(size, legend.getSize());
		assertEquals(Type.EQUAL, legend.getType());

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		// +1 because of the default rule
		assertEquals(nIntervals + 1, featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			// 1 rule with two symbolizers: one for the proportional symbol, one
			// for the background
			List<Rule> rules = featureTypeStyle.rules();
			Rule rule = rules.get(0);
			assertEquals(1, rules.size());
			assertEquals(1, rule.symbolizers().size());
		}

		// Check the first and last rule sizes are the legend size limits
		PointSymbolizer first = (PointSymbolizer) legend.getSymbols()[0];
		PointSymbolizer last = (PointSymbolizer) legend.getSymbols()[legend
				.getSymbols().length - 1];
		double sizeFirst = first.getGraphic().getSize()
				.evaluate(null, Double.class);
		double sizeLast = last.getGraphic().getSize()
				.evaluate(null, Double.class);
		assertEquals(size.getMin(), sizeFirst, 1e-7);
		assertEquals(size.getMax(), sizeLast, 1e-7);
	}

	@Test
	public void testCustomSymbols() throws Exception {
		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();
		symbolsMap.put(new Interval(10, 20), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 1, ""));
		symbolsMap.put(new Interval(20, 40), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 3, ""));
		symbolsMap.put(new Interval(40, 50), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 5, ""));

		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		SizeIntervalLegend legend = legendFactory.createSizeIntervalLegend(
				symbolsMap, Type.EQUAL, defaultSymbol, false, mockLayer(),
				FIELD_NAME, null);

		assertEquals(symbolsMap.size(), legend.getIntervals().length);
		assertEquals(symbolsMap.size(), legend.getSymbols().length);
		assertEquals(FIELD_NAME, legend.getFieldName());
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertEquals(new Interval(1, 5), legend.getSize());

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		assertEquals(symbolsMap.size(), featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			// 1 rule with 1 symbolizer
			List<Rule> rules = featureTypeStyle.rules();
			Rule rule = rules.get(0);
			assertEquals(1, rules.size());
			assertEquals(1, rule.symbolizers().size());
		}
	}

	@Test
	public void testPersistence() throws Exception {
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		Symbolizer template = defaultSymbols.createDefaultSymbol(Point.class,
				Color.lightGray, "");
		Symbolizer background = defaultSymbols.createDefaultSymbol(
				Polygon.class, Color.blue, "");
		Layer layer = mockLayer();
		SizeIntervalLegend original = legendFactory.createSizeIntervalLegend(
				new Interval(1, 7), Type.EQUAL, defaultSymbol, true, layer,
				FIELD_NAME, 4, template, background);

		SizeIntervalLegend parsed = (SizeIntervalLegend) legendFactory
				.createLegend(original.getXML(), layer);

		assertEquals(original.getSize(), parsed.getSize());
		assertEquals(original.getFieldName(), parsed.getFieldName());
		assertEquals(original.getType(), parsed.getType());
		assertArrayEquals(original.getIntervals(), parsed.getIntervals());
		assertEquals(toString(original.getStyle()), toString(parsed.getStyle()));
	}

	@Test
	public void testPersistenceNullTemplate() throws Exception {
		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();
		symbolsMap.put(new Interval(10, 20), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 1, ""));
		symbolsMap.put(new Interval(20, 40), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 3, ""));
		symbolsMap.put(new Interval(40, 50), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 5, ""));

		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		Layer layer = mockLayer();
		SizeIntervalLegend original = legendFactory.createSizeIntervalLegend(
				symbolsMap, Type.EQUAL, defaultSymbol, false, layer,
				FIELD_NAME, null);

		SizeIntervalLegend parsed = (SizeIntervalLegend) legendFactory
				.createLegend(original.getXML(), layer);

		assertEquals(toString(original.getStyle()), toString(parsed.getStyle()));
	}
}
