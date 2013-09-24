package org.gvsig.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.referencing.CRS;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.MapType;
import org.gvsig.units.Unit;
import org.gvsig.util.ProcessContext;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;

import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class MapContextTest extends GVSIGTestCase {
	private static final Unit DEFAULT_UNIT = Unit.METERS;

	@Inject
	private MapContextFactory factory;

	@Inject
	private LayerFactory layerFactory;

	private MapContext mapContext;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		mapContext = factory.createMapContext(DEFAULT_UNIT, DEFAULT_UNIT,
				DEFAULT_UNIT, CRS.decode("EPSG:4326"));
	}

	@Test
	public void testNotNullInContructor() throws Exception {
		try {
			factory.createMapContext(null, Unit.METERS, Unit.METERS,
					CRS.decode("EPSG:4326"));
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			factory.createMapContext(Unit.METERS, null, Unit.METERS,
					CRS.decode("EPSG:4326"));
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			factory.createMapContext(Unit.METERS, Unit.METERS, null,
					CRS.decode("EPSG:4326"));
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			factory.createMapContext(Unit.METERS, Unit.METERS, Unit.METERS,
					null);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testNonNullSetters() throws Exception {
		try {
			mapContext.setMapUnits(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			mapContext.setAreaUnits(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			mapContext.setDistanceUnits(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			mapContext.setCRS(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testMapUnits() throws Exception {
		mapContext.setMapUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getMapUnits());
		mapContext.setMapUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getMapUnits());
	}

	@Test
	public void testAreaUnits() throws Exception {
		mapContext.setAreaUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getAreaUnits());
		mapContext.setAreaUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getAreaUnits());
	}

	@Test
	public void testDistanceUnits() throws Exception {
		mapContext.setDistanceUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getDistanceUnits());
		mapContext.setDistanceUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getDistanceUnits());
	}

	@Test
	public void testRootLayer() throws Exception {
		Layer root = mapContext.getRootLayer();
		assertEquals(1, root.getAllLayersInTree().length);
		assertEquals(root, root.getAllLayersInTree()[0]);
	}

	@Test
	public void testCRS() throws Exception {
		mapContext.setCRS(CRS.decode("EPSG:4326"));
		assertEquals(CRS.decode("EPSG:4326"), mapContext.getCRS());
		mapContext.setCRS(CRS.decode("EPSG:25830"));
		assertEquals(CRS.decode("EPSG:25830"), mapContext.getCRS());
	}

	@Test
	public void testBGColor() throws Exception {
		mapContext.setBackgroundColor(Color.black);
		assertEquals(Color.black, mapContext.getBackgroundColor());
		mapContext.setBackgroundColor(Color.red);
		assertEquals(Color.red, mapContext.getBackgroundColor());
	}

	@Ignore
	@Test
	public void testDraw() throws Exception {
		fail();
	}

	@Test
	public void testMapPersistence() throws Exception {
		// Mock source with Point geometry type
		GeometryType type = mock(GeometryType.class);
		when(type.getBinding()).then(new Answer<Class<? extends Geometry>>() {
			@Override
			public Class<? extends Geometry> answer(InvocationOnMock invocation)
					throws Throwable {
				return Point.class;
			}
		});

		GeometryDescriptor geomDesc = mock(GeometryDescriptor.class);
		when(geomDesc.getType()).thenReturn(type);

		SimpleFeatureType schema = mock(SimpleFeatureType.class);
		when(schema.getGeometryDescriptor()).thenReturn(geomDesc);

		SimpleFeatureSource featureSource = mock(SimpleFeatureSource.class);
		when(featureSource.getSchema()).thenReturn(schema);
		Source source = mock(Source.class);
		when(source.createFeatureSource()).thenReturn(featureSource);

		mapContext.setBackgroundColor(Color.red);
		mapContext.setCRS(CRS.decode("EPSG:23030"));
		mapContext.getRootLayer().addLayer(
				layerFactory.createLayer("l", source));
		mapContext.draw(mock(BufferedImage.class), mock(Graphics2D.class),
				new Rectangle(0, 0, 10, 10), mock(ProcessContext.class));

		MapType xml = mapContext.getXML();
		MapContext copy = factory.createMapContext(xml);

		assertTrue(mapContext.getAreaUnits() == copy.getAreaUnits());
		assertTrue(mapContext.getDistanceUnits() == copy.getDistanceUnits());
		assertTrue(mapContext.getMapUnits() == copy.getMapUnits());
		assertTrue(mapContext.getBackgroundColor().equals(
				copy.getBackgroundColor()));
		assertTrue(mapContext.getRootLayer().getAllLayersInTree().length == copy
				.getRootLayer().getAllLayersInTree().length);
		assertTrue(mapContext.getLastDrawnArea()
				.equals(copy.getLastDrawnArea()));
	}

	@Test
	public void testPersistenceWithNullDrawnArea() throws Exception {
		MapType xml = mapContext.getXML();
		MapContext copy = factory.createMapContext(xml);

		assertTrue(copy.getLastDrawnArea() == null);
	}

	@Test
	public void testSetXMLInvalidCRS() throws Exception {
		MapType xml = new MapType();
		xml.setMapUnits(Unit.KILOMETERS.ordinal());
		xml.setAreaUnits(Unit.KILOMETERS.ordinal());
		xml.setDistanceUnits(Unit.KILOMETERS.ordinal());
		xml.setColor(Color.green.getRGB());
		xml.setCrs("invalid_crs_code");
		xml.setRootLayer(new CompositeLayerType());

		try {
			factory.createMapContext(xml);
			fail();
		} catch (PersistenceException e) {
		}
	}

	@Test
	public void testLastDrawnArea() throws Exception {
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
		mapContext.draw(mock(BufferedImage.class), mock(Graphics2D.class),
				rectangle, mock(ProcessContext.class));

		assertTrue(mapContext.getLastDrawnArea().equals(rectangle));
	}
}
