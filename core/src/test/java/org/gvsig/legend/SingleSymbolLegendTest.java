package org.gvsig.legend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.legend.impl.SingleSymbolLegend;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class SingleSymbolLegendTest extends AbstractLegendTest {
	@Inject
	private LegendFactory legendFactory;

	@Test
	public void testPoint() throws Exception {
		testSingleSymbolLegend(Point.class, PointSymbolizer.class);
	}

	@Test
	public void testMultiPoint() throws Exception {
		testSingleSymbolLegend(MultiPoint.class, PointSymbolizer.class);
	}

	@Test
	public void testLineString() throws Exception {
		testSingleSymbolLegend(LineString.class, LineSymbolizer.class);
	}

	@Test
	public void testMultiLineString() throws Exception {
		testSingleSymbolLegend(MultiLineString.class, LineSymbolizer.class);
	}

	@Test
	public void tesPolygon() throws Exception {
		testSingleSymbolLegend(Point.class, PointSymbolizer.class);
		testSingleSymbolLegend(MultiPoint.class, PointSymbolizer.class);
		testSingleSymbolLegend(LineString.class, LineSymbolizer.class);
		testSingleSymbolLegend(MultiLineString.class, LineSymbolizer.class);
		testSingleSymbolLegend(Polygon.class, PolygonSymbolizer.class);
		testSingleSymbolLegend(MultiPolygon.class, PolygonSymbolizer.class);
	}

	@Test
	public void testMultiPolygon() throws Exception {
		testSingleSymbolLegend(MultiPolygon.class, PolygonSymbolizer.class);
	}

	private void testSingleSymbolLegend(
			final Class<? extends Geometry> geomType,
			Class<? extends Symbolizer> symbolType) throws IOException {
		Legend legend = legendFactory
				.createSingleSymbolLegend(mockLayer(geomType));
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
	public void testPersistence() throws Exception {
		Layer layer = mockLayer();
		SingleSymbolLegend original = legendFactory
				.createSingleSymbolLegend(layer);
		SingleSymbolLegend parsed = (SingleSymbolLegend) legendFactory
				.createLegend(original.getXML(), layer);
		assertEquals(toString(original.getStyle()), toString(parsed.getStyle()));
	}

	private Layer mockLayer(final Class<? extends Geometry> geomType) {
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
		return layer;
	}
}
