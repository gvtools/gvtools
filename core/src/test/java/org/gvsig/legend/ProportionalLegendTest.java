package org.gvsig.legend;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.ProportionalLegend;
import org.junit.Test;

public class ProportionalLegendTest extends AbstractLegendTest {
	@Test
	public void testLegend() throws Exception {
		Layer layer = mockLayer();
		Symbolizer template = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);
		Interval size = new Interval(1, 10);
		ProportionalLegend legend = legendFactory.createProportionalLegend(
				layer, FIELD_NAME, FIELD_NAME, template, null, size);

		assertEquals(FIELD_NAME, legend.getNormalizationField());
		assertEquals(FIELD_NAME, legend.getValueField());
		assertEquals(template, legend.getTemplate());
		assertEquals(size, legend.getSize());

		// +1 for selection style, +1 for the proportional style
		List<FeatureTypeStyle> styles = legend.getStyle().featureTypeStyles();
		assertEquals(2, styles.size());
		for (FeatureTypeStyle style : styles) {
			assertEquals(1, style.rules().size());
		}
	}

	@Test
	public void testPersistence() throws Exception {
		Layer layer = mockLayer();
		Symbolizer template = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.black, null);
		Interval size = new Interval(1, 10);
		ProportionalLegend original = legendFactory.createProportionalLegend(
				layer, FIELD_NAME, FIELD_NAME, template, null, size);

		ProportionalLegend parsed = (ProportionalLegend) legendFactory
				.createLegend(original.getXML(), layer);

		assertEquals(original.getValueField(), parsed.getValueField());
		assertEquals(original.getNormalizationField(),
				parsed.getNormalizationField());
		assertEquals(original.getSize(), parsed.getSize());
		assertEquals(toString(original.getStyle()), toString(parsed.getStyle()));
	}
}
