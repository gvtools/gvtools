package org.gvsig.legend;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.UniqueValueLegend;
import org.junit.Test;

public class UniqueValuesLegendTest extends AbstractLegendTest {
	@Test
	public void testDefaultSymbol() throws Exception {
		testUniqueValueLegend(false);
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
	public void testCustomValues() throws Exception {
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

	private Comparator<Object> comparator() {
		return new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		};
	}

}
