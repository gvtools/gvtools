package org.gvsig.legend;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.StringWriter;

import javax.xml.transform.TransformerException;

import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.styling.SLDTransformer;
import org.geotools.styling.Style;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public abstract class AbstractLegendTest extends GVSIGTestCase {
	protected static final String FIELD_NAME = "value";

	protected static final double[] values = new double[] { 10, 20, 20, 50 };

	@Inject
	protected LegendFactory legendFactory;

	@Inject
	protected LayerFactory layerFactory;

	protected GeometryFactory gf = new GeometryFactory();

	@Inject
	protected DefaultSymbols defaultSymbols;

	protected Layer mockLayer() throws Exception {
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

	protected String toString(Style style) throws TransformerException {
		StringWriter writer = new StringWriter();
		new SLDTransformer().transform(style, writer);
		return writer.toString();
	}
}
