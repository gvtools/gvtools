package org.gvsig.legend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Selection;
import org.gvsig.layer.Source;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.LegendFactory;
import org.gvsig.legend.impl.ProportionalLegend;
import org.gvsig.legend.impl.SizeIntervalLegend;
import org.gvsig.legend.impl.UniqueValueLegend;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class LegendTest extends GVSIGTestCase {
	private static final String FIELD_NAME = "value";

	private static final double[] values = new double[] { 10, 20, 20, 50 };

	@Inject
	private LegendFactory legendFactory;

	@Inject
	private LayerFactory layerFactory;

	private GeometryFactory gf = new GeometryFactory();

	@Inject
	private DefaultSymbols defaultSymbols;

	@Test
	public void testSingleSymbolLegend() throws Exception {
		testSingleSymbolLegend(Point.class, PointSymbolizer.class);
		testSingleSymbolLegend(MultiPoint.class, PointSymbolizer.class);
		testSingleSymbolLegend(LineString.class, LineSymbolizer.class);
		testSingleSymbolLegend(MultiLineString.class, LineSymbolizer.class);
		testSingleSymbolLegend(Polygon.class, PolygonSymbolizer.class);
		testSingleSymbolLegend(MultiPolygon.class, PolygonSymbolizer.class);
	}

	private void testSingleSymbolLegend(
			final Class<? extends Geometry> geomType,
			Class<? extends Symbolizer> symbolType) throws IOException {
		Layer layer = mock(Layer.class);
		when(layer.getShapeType()).then(
				new Answer<Class<? extends Geometry>>() {
					@Override
					public Class<? extends Geometry> answer(
							InvocationOnMock invocation) throws Throwable {
						return geomType;
					}
				});
		when(layer.getSelection()).thenReturn(new Selection());

		Legend legend = legendFactory.createSingleSymbolLegend(layer);
		List<FeatureTypeStyle> styles = legend.getStyle().featureTypeStyles();
		// 2 feature type styles (each one with a single rule): default and
		// selected
		assertEquals(2, styles.size());
		for (FeatureTypeStyle style : styles) {
			List<Rule> rules = style.rules();
			assertEquals(1, rules.size());
			List<Symbolizer> symbolizers = rules.get(0).symbolizers();
			assertEquals(1, symbolizers.size());
			assertTrue(symbolType.isAssignableFrom(symbolizers.get(0)
					.getClass()));
		}
	}

	@Test
	public void testEqualIntervalLegend() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory
				.createIntervalLegend(start, end, Type.EQUAL, defaultSymbol,
						false, layer, FIELD_NAME, nIntervals);

		checkIntervalLegend(legend, start, end, nIntervals, defaultSymbol);

		Interval[] intervals = legend.getIntervals();
		Interval first = intervals[0];
		Interval last = intervals[intervals.length - 1];
		double step = (values[values.length - 1] - values[0]) / nIntervals;
		assertEquals(values[0], first.getMin(), 1e-10);
		assertEquals(values[0] + step, first.getMax(), 1e-10);
		assertEquals(values[values.length - 1], last.getMax(), 1e-10);
		assertEquals(values[values.length - 1] - step, last.getMin(), 1e-10);
	}

	@Test
	public void testNaturalIntervalLegend() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory.createIntervalLegend(start, end,
				Type.NATURAL, defaultSymbol, false, layer, FIELD_NAME,
				nIntervals);

		checkIntervalLegend(legend, start, end, nIntervals, defaultSymbol);
		checkIntervalLegendValues(legend.getIntervals());
	}

	@Test
	public void testQuantileIntervalLegend() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory.createIntervalLegend(start, end,
				Type.QUANTILE, defaultSymbol, false, layer, FIELD_NAME,
				nIntervals);

		checkIntervalLegend(legend, start, end, nIntervals, defaultSymbol);
		checkIntervalLegendValues(legend.getIntervals());
	}

	private void checkIntervalLegend(IntervalLegend legend, Color start,
			Color end, int nIntervals, Symbolizer defaultSymbol)
			throws Exception {
		assertEquals(start, legend.getStartColor());
		assertEquals(end, legend.getEndColor());
		assertEquals(nIntervals, nIntervals);
		assertEquals(defaultSymbol, legend.getDefaultSymbol());

		PointSymbolizer startSymbol = (PointSymbolizer) legend.getSymbols()[0];
		PointSymbolizer endSymbol = (PointSymbolizer) legend.getSymbols()[nIntervals - 1];
		assertEquals(start, SLD.color(startSymbol));
		assertEquals(end, SLD.color(endSymbol));
	}

	private void checkIntervalLegendValues(Interval[] intervals) {
		Interval first = intervals[0];
		Interval last = intervals[intervals.length - 1];
		assertEquals(values[0], first.getMin(), 1e-10);
		assertTrue(values[0] <= first.getMax());
		assertEquals(values[values.length - 1], last.getMax(), 1e-10);
		assertTrue(values[values.length - 1] >= last.getMin());
	}

	@Test
	public void testSingleIntervalLegend() throws Exception {
		testSingleIntervalLegend(Type.EQUAL);
		testSingleIntervalLegend(Type.NATURAL);
		testSingleIntervalLegend(Type.QUANTILE);
	}

	private void testSingleIntervalLegend(Type type) throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 1;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory.createIntervalLegend(start, end,
				type, defaultSymbol, false, layer, FIELD_NAME, nIntervals);

		assertEquals(1, legend.getIntervals().length);
		assertEquals(1, legend.getSymbols().length);
		assertEquals(start, SLD.color((PointSymbolizer) legend.getSymbols()[0]));
		assertEquals(values[0], legend.getIntervals()[0].getMin(), 0.0);
		assertEquals(values[values.length - 1],
				legend.getIntervals()[0].getMax(), 0.0);
	}

	@Test
	public void testCustomIntervalLegend() throws Exception {
		Layer layer = mockLayer();

		// Symbols
		Symbolizer red = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.red, null);
		Symbolizer green = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.green, null);
		Symbolizer blue = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.blue, null);
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		// Intervals
		Interval i1 = new Interval(values[0], values[1]);
		Interval i2 = new Interval(values[1], values[2]);
		Interval i3 = new Interval(values[2], values[values.length - 1]);

		Map<Interval, Symbolizer> symbols = new HashMap<Interval, Symbolizer>();
		symbols.put(i1, red);
		symbols.put(i2, green);
		symbols.put(i3, blue);

		IntervalLegend legend = legendFactory.createIntervalLegend(symbols,
				defaultSymbol, false, layer, FIELD_NAME);

		assertEquals(symbols.size(), legend.getIntervals().length);
		assertEquals(symbols.size(), legend.getSymbols().length);
		assertEquals(Color.red, legend.getStartColor());
		assertEquals(Color.blue, legend.getEndColor());
		assertEquals(red, legend.getSymbols()[0]);
		assertEquals(green, legend.getSymbols()[1]);
		assertEquals(blue, legend.getSymbols()[2]);
		assertEquals(i1, legend.getIntervals()[0]);
		assertEquals(i2, legend.getIntervals()[1]);
		assertEquals(i3, legend.getIntervals()[2]);
	}

	@Test
	public void testIntervalLegendStyle() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory
				.createIntervalLegend(start, end, Type.EQUAL, defaultSymbol,
						false, layer, FIELD_NAME, nIntervals);

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		// +1 because of the selection rule
		assertEquals(nIntervals + 1, featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			assertEquals(1, featureTypeStyle.rules().size());
		}
	}

	@Test
	public void testIntervalLegendDefaultSymbol() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend legend = legendFactory.createIntervalLegend(start, end,
				Type.EQUAL, defaultSymbol, true, layer, FIELD_NAME, nIntervals);

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		// +2 for selection and default symbol
		assertEquals(nIntervals + 2, featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			assertEquals(1, featureTypeStyle.rules().size());
		}
	}

	@Test
	public void testUniqueValueLegend() throws Exception {
		testUniqueValueLegend(false);
	}

	@Test
	public void testUniqueValueLegendDefaultSymbol() throws Exception {
		testUniqueValueLegend(true);
	}

	private void testUniqueValueLegend(boolean useDefault) throws Exception {
		Layer layer = mockLayer();
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);
		Color[] scheme = new Color[] { Color.red, Color.green, Color.blue };
		UniqueValueLegend legend = legendFactory.createUniqueValueLegend(layer,
				FIELD_NAME, defaultSymbol, useDefault, scheme, comparator());

		int numUniqueValues = 3;
		assertEquals(numUniqueValues, legend.getSymbols().length);
		assertEquals(numUniqueValues, legend.getValues().length);
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertArrayEquals(scheme, legend.getColorScheme());

		// +1 for selection style, +1 for default style
		int nStyles = useDefault ? (numUniqueValues + 2)
				: (numUniqueValues + 1);
		List<FeatureTypeStyle> styles = legend.getStyle().featureTypeStyles();
		assertEquals(nStyles, styles.size());
		for (FeatureTypeStyle style : styles) {
			assertEquals(1, style.rules().size());
		}
	}

	@Test
	public void testUniqueValueLegendCustomValues() throws Exception {
		Layer layer = mockLayer();
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);
		Color[] scheme = new Color[] { Color.red, Color.green, Color.blue };
		Map<Object, Symbolizer> symbols = new HashMap<Object, Symbolizer>();
		symbols.put(20, defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.gray, ""));
		UniqueValueLegend legend = legendFactory.createUniqueValueLegend(layer,
				FIELD_NAME, defaultSymbol, true, scheme, symbols, comparator());

		assertEquals(symbols.size(), legend.getSymbols().length);
		assertEquals(symbols.size(), legend.getValues().length);
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertArrayEquals(scheme, legend.getColorScheme());

		// +1 for selection style, +1 for default style
		List<FeatureTypeStyle> styles = legend.getStyle().featureTypeStyles();
		assertEquals(symbols.size() + 2, styles.size());
		for (FeatureTypeStyle style : styles) {
			assertEquals(1, style.rules().size());
		}
	}

	@Test
	public void testProportionalLegend() throws Exception {
		Layer layer = mockLayer();
		Symbolizer template = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);
		Symbolizer background = defaultSymbols.createDefaultSymbol(
				Polygon.class, Color.black, null);
		Interval size = new Interval(1, 10);
		ProportionalLegend legend = legendFactory.createProportionalLegend(
				layer, FIELD_NAME, FIELD_NAME, template, background, false,
				size);

		assertEquals(FIELD_NAME, legend.getNormalizationField());
		assertEquals(FIELD_NAME, legend.getValueField());
		assertEquals(template, legend.getTemplate());
		assertEquals(background, legend.getBackground());
		assertEquals(size, legend.getSize());

		// +1 for selection style, +1 for the proportional style
		List<FeatureTypeStyle> styles = legend.getStyle().featureTypeStyles();
		assertEquals(2, styles.size());
		for (FeatureTypeStyle style : styles) {
			assertEquals(1, style.rules().size());
		}
	}

	@Test
	public void testSizeIntervalLegend() throws Exception {
		Interval size = new Interval(1, 7);
		int nIntervals = 4;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		Symbolizer template = defaultSymbols.createDefaultSymbol(Point.class,
				Color.lightGray, "");
		Symbolizer background = defaultSymbols.createDefaultSymbol(
				Polygon.class, Color.blue, "");
		SizeIntervalLegend legend = legendFactory.createSizeIntervalLegend(
				size, Type.EQUAL, defaultSymbol, true, mockLayer(), FIELD_NAME,
				nIntervals, template, background, true);

		assertEquals(nIntervals, legend.getIntervals().length);
		assertEquals(nIntervals, legend.getSymbols().length);
		assertEquals(FIELD_NAME, legend.getFieldName());
		assertEquals(background, legend.getBackground());
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertEquals(size, legend.getSize());
		assertEquals(Type.EQUAL, legend.getType());

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		// +1 because of the selection rule, +1 because of the default rule
		assertEquals(nIntervals + 2, featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			// 1 rule with two symbolizers: one for the proportional symbol, one
			// for the background
			List<Rule> rules = featureTypeStyle.rules();
			Rule rule = rules.get(0);
			assertEquals(1, rules.size());

			if (rule.symbolizers().size() == 1) {
				// selection rule
			} else {
				assertEquals(2, rule.symbolizers().size());
				assertTrue(rule.symbolizers().contains(background));
			}
		}
	}

	@Test
	public void testSizeIntervalLegendWithGivenSymbols() throws Exception {
		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();
		symbolsMap.put(new Interval(10, 20), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 1, ""));
		symbolsMap.put(new Interval(20, 40), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 3, ""));
		symbolsMap.put(new Interval(40, 50), defaultSymbols
				.createDefaultSymbol(Point.class, Color.red, 5, ""));

		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				Point.class, Color.red, "");
		Symbolizer background = defaultSymbols.createDefaultSymbol(
				Polygon.class, Color.blue, "");
		SizeIntervalLegend legend = legendFactory.createSizeIntervalLegend(
				symbolsMap, defaultSymbol, false, mockLayer(), FIELD_NAME,
				background, false);

		assertEquals(symbolsMap.size(), legend.getIntervals().length);
		assertEquals(symbolsMap.size(), legend.getSymbols().length);
		assertEquals(FIELD_NAME, legend.getFieldName());
		assertEquals(background, legend.getBackground());
		assertEquals(defaultSymbol, legend.getDefaultSymbol());
		assertEquals(new Interval(1, 5), legend.getSize());

		Style style = legend.getStyle();
		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		// We only check the number of feature type styles, not the styles
		// themselves
		// +1 because of the selection rule
		assertEquals(symbolsMap.size() + 1, featureTypeStyles.size());
		for (FeatureTypeStyle featureTypeStyle : featureTypeStyles) {
			// 1 rule with 1 symbolizer
			List<Rule> rules = featureTypeStyle.rules();
			Rule rule = rules.get(0);
			assertEquals(1, rules.size());
			assertEquals(1, rule.symbolizers().size());
		}
	}

	private Layer mockLayer() throws Exception {
		String name = "mylayer";
		SimpleFeatureTypeBuilder buildType = new SimpleFeatureTypeBuilder();
		buildType.setName(name);
		buildType.add("geom", Point.class);
		buildType.add(FIELD_NAME, Double.class);

		SimpleFeatureType schema = buildType.buildFeatureType();
		MemoryDataStore dataStore = new MemoryDataStore();
		dataStore.createSchema(schema);
		for (int i = 0; i < values.length; i++) {
			dataStore.addFeature(buildFeature(schema, Integer.toString(i), 0.0,
					0.0, values[i]));
		}

		Source source = mock(Source.class);
		when(source.createFeatureSource()).thenReturn(
				dataStore.getFeatureSource(name));
		return layerFactory.createLayer(name, source);
	}

	private SimpleFeature buildFeature(SimpleFeatureType schema, String id,
			double x, double y, double value) {
		Point geom = gf.createPoint(new Coordinate(x, y));
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(schema);
		return featureBuilder.buildFeature(id, new Object[] { geom, value });
	}

	private Comparator<Object> comparator() {
		return new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		};
	}
}
