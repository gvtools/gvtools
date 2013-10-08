package org.gvsig.legend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.IntervalLegend;
import org.junit.Test;

public class IntervalLegendTest extends AbstractLegendTest {
	@Test
	public void testEqual() throws Exception {
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
	public void testNatural() throws Exception {
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
	public void testQuantile() throws Exception {
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
	public void testSingleInterval() throws Exception {
		testSingleInterval(Type.EQUAL);
		testSingleInterval(Type.NATURAL);
		testSingleInterval(Type.QUANTILE);
	}

	private void testSingleInterval(Type type) throws Exception {
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
	public void testCustomIntervals() throws Exception {
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
				Type.EQUAL, defaultSymbol, false, layer, FIELD_NAME);

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
	public void testStyle() throws Exception {
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
	public void testDefaultSymbol() throws Exception {
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
	public void testPersistence() throws Exception {
		Color start = Color.red;
		Color end = Color.blue;
		Layer layer = mockLayer();
		int nIntervals = 3;
		Symbolizer defaultSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);

		IntervalLegend original = legendFactory.createIntervalLegend(start,
				end, Type.EQUAL, defaultSymbol, true, layer, FIELD_NAME,
				nIntervals);
		IntervalLegend parsed = (IntervalLegend) legendFactory.createLegend(
				original.getXML(), layer);

		assertEquals(original.getStartColor(), parsed.getStartColor());
		assertEquals(original.getEndColor(), parsed.getEndColor());
		assertEquals(original.getFieldName(), parsed.getFieldName());
		assertEquals(original.getType(), parsed.getType());
		assertArrayEquals(original.getIntervals(), parsed.getIntervals());
		assertEquals(toString(original.getStyle()), toString(parsed.getStyle()));
	}
}
