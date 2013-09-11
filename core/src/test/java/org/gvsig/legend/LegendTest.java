package org.gvsig.legend;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Symbolizer;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
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

public class LegendTest extends GVSIGTestCase {
	@Inject
	private LegendFactory factory;

	public void testDefaultLegendPoint() throws Exception {
		testDefaultLegend(Point.class, PointSymbolizer.class);
		testDefaultLegend(MultiPoint.class, PointSymbolizer.class);
		testDefaultLegend(LineString.class, LineSymbolizer.class);
		testDefaultLegend(MultiLineString.class, LineSymbolizer.class);
		testDefaultLegend(Polygon.class, PolygonSymbolizer.class);
		testDefaultLegend(MultiPolygon.class, PolygonSymbolizer.class);
	}

	private void testDefaultLegend(final Class<? extends Geometry> geomType,
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

		Legend legend = factory.createDefaultLegend(layer);
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
}
