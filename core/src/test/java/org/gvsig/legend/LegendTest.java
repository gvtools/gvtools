package org.gvsig.legend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
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
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.IntervalLegend.Type;
import org.gvsig.legend.impl.LegendFactory;
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
			Class<? extends Symbolizer> symbolType) {
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
		assertEquals(1, styles.size());
		List<Rule> rules = styles.get(0).rules();
		// 2 rules: default and selected
		assertEquals(2, rules.size());
		for (Rule rule : rules) {
			List<Symbolizer> symbolizers = rule.symbolizers();
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
		assertEquals(1, featureTypeStyles.size());

		// We only check the number of rules, not the style itself
		// +1 because of the selection rule
		List<Rule> rules = featureTypeStyles.get(0).rules();
		assertEquals(nIntervals + 1, rules.size());
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
		assertEquals(1, featureTypeStyles.size());

		// We only check the number of rules, not the style itself
		// +2 for selection and default symbol
		List<Rule> rules = featureTypeStyles.get(0).rules();
		assertEquals(nIntervals + 2, rules.size());
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

		// +1 for selection rule, +1 for default rule
		int nRules = useDefault ? (numUniqueValues + 2) : (numUniqueValues + 1);
		List<Rule> rules = legend.getStyle().featureTypeStyles().get(0).rules();
		assertEquals(nRules, rules.size());
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

		// +1 for selection rule, +1 for default rule
		List<Rule> rules = legend.getStyle().featureTypeStyles().get(0).rules();
		assertEquals(symbols.size() + 2, rules.size());
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
