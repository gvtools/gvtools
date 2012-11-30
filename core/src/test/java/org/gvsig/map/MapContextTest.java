package org.gvsig.map;

import static org.mockito.Mockito.mock;

import java.awt.Color;

import org.geotools.referencing.CRS;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.MapType;
import org.gvsig.units.Unit;

import com.google.inject.Inject;

public class MapContextTest extends GVSIGTestCase {
	private static final Unit DEFAULT_UNIT = Unit.METERS;

	@Inject
	private MapContextFactory factory;

	@Inject
	private LayerFactory layerFactory;

	private MapContext mapContext;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mapContext = factory.createMapContext(DEFAULT_UNIT, DEFAULT_UNIT,
				DEFAULT_UNIT, CRS.decode("EPSG:4326"));
	}

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

	public void testMapUnits() throws Exception {
		mapContext.setMapUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getMapUnits());
		mapContext.setMapUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getMapUnits());
	}

	public void testAreaUnits() throws Exception {
		mapContext.setAreaUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getAreaUnits());
		mapContext.setAreaUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getAreaUnits());
	}

	public void testDistanceUnits() throws Exception {
		mapContext.setDistanceUnits(Unit.CENTIMETERS);
		assertEquals(Unit.CENTIMETERS, mapContext.getDistanceUnits());
		mapContext.setDistanceUnits(Unit.METERS);
		assertEquals(Unit.METERS, mapContext.getDistanceUnits());
	}

	public void testRootLayer() throws Exception {
		Layer root = mapContext.getRootLayer();
		assertEquals(1, root.getAllLayersInTree().length);
		assertEquals(root, root.getAllLayersInTree()[0]);
	}

	public void testCRS() throws Exception {
		mapContext.setCRS(CRS.decode("EPSG:4326"));
		assertEquals(CRS.decode("EPSG:4326"), mapContext.getCRS());
		mapContext.setCRS(CRS.decode("EPSG:25830"));
		assertEquals(CRS.decode("EPSG:25830"), mapContext.getCRS());
	}

	public void testBGColor() throws Exception {
		mapContext.setBackgroundColor(Color.black);
		assertEquals(Color.black, mapContext.getBackgroundColor());
		mapContext.setBackgroundColor(Color.red);
		assertEquals(Color.red, mapContext.getBackgroundColor());
	}

	public void testDraw() throws Exception {
		fail();
	}

	public void testMapPersistence() throws Exception {
		mapContext.setBackgroundColor(Color.red);
		mapContext.setCRS(CRS.decode("EPSG:23030"));
		mapContext.getRootLayer().addLayer(
				layerFactory.createLayer(mock(Source.class)));

		MapType xml = mapContext.getXML();
		MapContext copy = factory.createMapContext(xml);

		assertTrue(mapContext.getAreaUnits() == copy.getAreaUnits());
		assertTrue(mapContext.getDistanceUnits() == copy.getDistanceUnits());
		assertTrue(mapContext.getMapUnits() == copy.getMapUnits());
		assertTrue(mapContext.getBackgroundColor().equals(
				copy.getBackgroundColor()));
		assertTrue(mapContext.getRootLayer().getAllLayersInTree().length == copy
				.getRootLayer().getAllLayersInTree().length);
	}

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
}
