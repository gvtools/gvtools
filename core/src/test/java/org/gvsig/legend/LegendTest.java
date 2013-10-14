package org.gvsig.legend;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import javax.inject.Inject;

import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.TextSymbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.SingleSymbolLegend;
import org.junit.Test;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.PropertyName;

public class LegendTest extends AbstractLegendTest {
	@Inject
	private StyleFactory styleFactory;
	@Inject
	private FilterFactory2 filterFactory;

	@Test
	public void testLabeling() throws Exception {
		Layer layer = mockLayer();
		SingleSymbolLegend legend = legendFactory
				.createSingleSymbolLegend(layer);
		TextSymbolizer labeling = styleFactory.createTextSymbolizer();
		legend.setLabeling(labeling);

		Style style = legend.getStyle();
		// symbol + label
		assertEquals(2, style.featureTypeStyles().size());
	}

	@Test
	public void testLabelingPersistenceFixedSize() throws Exception {
		Layer layer = mockLayer();
		SingleSymbolLegend legend = legendFactory
				.createSingleSymbolLegend(layer);

		TextSymbolizer labeling = styleFactory.createTextSymbolizer();
		Color color = Color.yellow;
		int size = 12;
		labeling.setFill(styleFactory.createFill(filterFactory.literal(color)));
		labeling.setFont(new StyleBuilder().createFont("Arial", size));
		labeling.setLabel(filterFactory.property(FIELD_NAME));
		legend.setLabeling(labeling);

		Legend copy = legendFactory.createLegend(legend.getXML(), layer);
		TextSymbolizer copyLabeling = copy.getLabeling();
		assertEquals(color, SLD.color(copyLabeling.getFill()));
		Expression sizeExp = copyLabeling.getFont().getSize();
		assertEquals(size, (int) sizeExp.evaluate(null, Integer.class));
	}

	@Test
	public void testLabelingPersistenceFieldSize() throws Exception {
		Layer layer = mockLayer();
		SingleSymbolLegend legend = legendFactory
				.createSingleSymbolLegend(layer);

		TextSymbolizer labeling = styleFactory.createTextSymbolizer();
		labeling.setFill(styleFactory.createFill(filterFactory
				.literal(Color.yellow)));
		labeling.setFont(new StyleBuilder().createFont(
				filterFactory.literal("Arial"), filterFactory.literal(false),
				filterFactory.literal(false),
				filterFactory.property(FIELD_NAME)));
		labeling.setLabel(filterFactory.property(FIELD_NAME));
		legend.setLabeling(labeling);

		Legend copy = legendFactory.createLegend(legend.getXML(), layer);
		TextSymbolizer copyLabeling = copy.getLabeling();
		PropertyName label = (PropertyName) copyLabeling.getLabel();
		assertEquals(FIELD_NAME, label.getPropertyName());
	}
}
