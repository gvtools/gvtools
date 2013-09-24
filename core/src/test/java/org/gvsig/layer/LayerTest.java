package org.gvsig.layer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import geomatico.events.Event;
import geomatico.events.EventBus;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.identity.FeatureIdImpl;
import org.gvsig.GVSIGTestCase;
import org.gvsig.events.FeatureSelectionChangeEvent;
import org.gvsig.events.LayerAddedEvent;
import org.gvsig.events.LayerNameChangeEvent;
import org.gvsig.events.LayerSelectionChangeEvent;
import org.gvsig.events.LayerVisibilityChangeEvent;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.LayerType;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class LayerTest extends GVSIGTestCase {
	@Inject
	private LayerFactory layerFactory;

	@Inject
	private EventBus eventBus;

	@Override
	protected Module getOverridingModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(EventBus.class).toInstance(mock(EventBus.class));
			}
		};
	}

	@Test
	public void testGetParent() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		assertTrue(layer.getParent() == null);
		Layer folder = layerFactory.createLayer("folder");
		assertTrue(folder.getParent() == null);
	}

	@Test
	public void testParentOnAddRemove() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		Layer folder = layerFactory.createLayer("folder", layer);
		assertTrue(layer.getParent() == folder);

		folder.removeLayer(layer);
		assertTrue(layer.getParent() == null);

		folder.addLayer(layer);
		assertTrue(layer.getParent() == folder);
	}

	@Test
	public void testAddLayerToTwoParents() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		Layer folder1 = layerFactory.createLayer("folder1");
		Layer folder2 = layerFactory.createLayer("folder2");

		folder1.addLayer(layer);
		try {
			folder2.addLayer(layer);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testAddLayerToPosition() throws Exception {
		Layer layer1 = layerFactory.createLayer("leaf1", mock(Source.class));
		Layer folder = layerFactory.createLayer("folder");

		folder.addLayer(0, layer1);

		assertTrue(folder.getChildren()[0] == layer1);

		Layer layer2 = layerFactory.createLayer("leaf2", mock(Source.class));
		folder.addLayer(0, layer2);

		assertTrue(folder.indexOf(layer2) == 0);
		assertTrue(folder.indexOf(layer1) == 1);
	}

	@Test
	public void testIndexOfNonChild() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		Layer folder = layerFactory.createLayer("folder");

		assertTrue(folder.indexOf(layer) == -1);
	}

	@Test
	public void testContains() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer("l", mock(Source.class));
		assertFalse(layer.contains(mock(Layer.class)));
		assertTrue(layer.contains(layer));

		// Composite
		layer = layerFactory.createLayer("l");
		Layer l1 = mock(Layer.class);
		when(l1.contains(l1)).thenReturn(true);
		Layer l2 = mock(Layer.class);
		when(l2.contains(l2)).thenReturn(true);
		Layer l3 = mock(Layer.class);
		when(l3.contains(l3)).thenReturn(true);
		Layer composite = layerFactory.createLayer("l", l3);
		layer.addLayer(l1);
		layer.addLayer(composite);

		assertTrue(layer.contains(layer));
		assertTrue(layer.contains(l1));
		assertTrue(layer.contains(composite));
		assertTrue(layer.contains(l3));
		assertFalse(layer.contains(l2));
		assertFalse(layer.contains(null));
	}

	@Test
	public void testGetAllLayers() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer("l", mock(Source.class));
		Layer[] layers = layer.getAllLayersInTree();
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Empty composite
		layer = layerFactory.createLayer("l");
		layers = layer.getAllLayersInTree();
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Non-empty composite
		Layer l1 = layerFactory.createLayer("l", mock(Source.class));
		Layer l2 = layerFactory.createLayer("l", mock(Source.class));
		layer = layerFactory.createLayer("l", l1, l2);
		layers = layer.getAllLayersInTree();
		assertEquals(3, layers.length);
		assertEquals(layer, layers[0]);
		assertEquals(l1, layers[1]);
		assertEquals(l2, layers[2]);
	}

	@Test
	public void testAddLayer() throws Exception {
		// Feature
		Layer child = layerFactory.createLayer("l", mock(Source.class));
		try {
			layerFactory.createLayer("l", mock(Source.class)).addLayer(child);
			fail();
		} catch (UnsupportedOperationException e) {
		}

		// Composite
		Layer layer = layerFactory.createLayer("l");
		try {
			layer.addLayer(null);
			fail();
		} catch (IllegalArgumentException e) {
		}

		layer.addLayer(child);

		Layer[] layers = layer.getAllLayersInTree();
		assertEquals(2, layers.length);
		assertEquals(child, layers[1]);
	}

	@Test
	public void testFilter() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer("l", mock(Source.class));
		Layer[] layers = layer.filter(new LayerFilter() {
			@Override
			public boolean accepts(Layer layer) {
				return false;
			}
		});
		assertEquals(0, layers.length);

		layers = layer.filter(new LayerFilter() {
			@Override
			public boolean accepts(Layer layer) {
				return true;
			}
		});
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Composite
		Layer l1 = layerFactory.createLayer("l", mock(Source.class));
		Layer l2 = layerFactory.createLayer("l", mock(Source.class));
		layer = layerFactory.createLayer("l", l1, l2);

		layers = layer.filter(new LayerFilter() {
			@Override
			public boolean accepts(Layer layer) {
				return false;
			}
		});
		assertEquals(0, layers.length);

		LayerFilter filter = mock(LayerFilter.class);
		// true, false, true == root and second child accepted
		when(filter.accepts(any(Layer.class))).thenReturn(true)
				.thenReturn(false).thenReturn(true);
		layers = layer.filter(filter);
		assertEquals(2, layers.length);
		assertEquals(layer, layers[0]);
		assertEquals(l2, layers[1]);
	}

	@Ignore("Check default symbology for point layers, "
			+ "multipoint layers, line layers, etc. "
			+ "and heterogeneous layers. We'd better wait till"
			+ "symbology panels are migrated")
	@Test
	public void testSymbology() throws Exception {
		fail();
	}

	@Test
	public void testHasFeatures() throws Exception {
		assertTrue(layerFactory.createLayer("l", mock(Source.class))
				.hasFeatures());
		assertFalse(layerFactory.createLayer("l").hasFeatures());
	}

	@Test
	public void testIsActive() throws Exception {
		assertFalse(layerFactory.createLayer("l", mock(Source.class))
				.isActive());
		assertFalse(layerFactory.createLayer("l").isActive());
	}

	@Test
	public void testEditing() throws Exception {
		assertFalse(layerFactory.createLayer("l", mock(Source.class))
				.isEditing());
		assertFalse(layerFactory.createLayer("l").isEditing());
	}

	@Test
	public void testInitiallyVisible() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		assertTrue(layer.isVisible());
	}

	@Test
	public void testInvisibleLayersAreNotDrawn() throws Exception {
		Layer layer = mockLayerWithType("leaf");
		assertTrue(layer.getDrawingLayers().size() == 1);

		layer.setVisible(false);
		assertTrue(layer.getDrawingLayers().size() == 0);
	}

	@Test
	public void testVisibilityChangedEvent() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		layer.setVisible(false);

		verify(eventBus).fireEvent(any(LayerVisibilityChangeEvent.class));
	}

	@Test
	public void testSelectionChangedEvent() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		layer.setSelected(true);

		verify(eventBus).fireEvent(any(LayerSelectionChangeEvent.class));
	}

	@Test
	public void testSelectionCallWithSameValueRaisesNoEvent() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		layer.setSelected(layer.isSelected());

		verify(eventBus, never()).fireEvent(
				any(LayerSelectionChangeEvent.class));
	}

	@Test
	public void testLayerNameChangedEvent() throws Exception {
		Layer layer = layerFactory.createLayer("leaf", mock(Source.class));
		layer.setName("other");

		verify(eventBus).fireEvent(any(LayerNameChangeEvent.class));
	}

	@Test
	public void testDataLayerXML() throws Exception {
		Layer layer = mockLayerWithType("layer");
		layer.setSelected(!layer.isSelected());
		layer.setVisible(!layer.isVisible());
		Selection selection = new Selection();
		selection.add(new FeatureIdImpl("a"));
		layer.setSelection(selection);
		LayerType xml = layer.getXML();
		Layer copy = layerFactory.createLayer(xml);

		assertTrue(copy.getName().equals("layer"));
		assertTrue(copy.isVisible() == layer.isVisible());
		assertTrue(copy.isSelected() == layer.isSelected());
		assertTrue(copy.getSelection().equals(layer.getSelection()));
	}

	@Ignore
	@Test
	public void testLayerStyleXML() throws Exception {
	}

	@Test
	public void testCompositeLayerXML() throws Exception {
		Layer root = layerFactory.createLayer("root");
		Layer folder = layerFactory.createLayer("folder");
		Layer leaf = mockLayerWithType("leaf");
		folder.addLayer(leaf);
		root.addLayer(folder);

		LayerType xml = root.getXML();
		Layer copy = layerFactory.createLayer(xml);

		assertTrue(copy.getAllLayersInTree().length == 3);
		assertTrue(copy.getName().equals("root"));
		assertTrue(copy.getChildren()[0].getName().equals("folder"));
		assertTrue(copy.getChildren()[0].getChildren()[0].getName().equals(
				"leaf"));
		// For invocations, two at creation at two at copy
		verify(eventBus, times(4)).fireEvent(any(LayerAddedEvent.class));
	}

	@Test
	public void testRemoveLayer() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer("l", mock(Source.class));
		try {
			layer.removeLayer(mock(Layer.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}

		// Composite
		Layer l1 = layerFactory.createLayer("l", mock(Source.class));
		Layer l2 = layerFactory.createLayer("l", mock(Source.class));
		layer = layerFactory.createLayer("l", l1, l2);
		assertFalse(layer.removeLayer(mock(Layer.class)));
		assertTrue(layer.removeLayer(l1));
		Layer[] layers = layer.getAllLayersInTree();
		assertEquals(2, layers.length);
		assertEquals(layer, layers[0]);
		assertEquals(l2, layers[1]);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemoveLayerEvent() throws Exception {
		Layer leaf = layerFactory.createLayer("l", mock(Source.class));
		Layer folder = layerFactory.createLayer("l", leaf);
		/*
		 * Don't know why mockito does not check the class of the event. Let's
		 * just count invocations then
		 */
		verify(eventBus, times(1)).fireEvent(any(Event.class));

		folder.removeLayer(leaf);

		verify(eventBus, times(2)).fireEvent(any(Event.class));
	}

	@Ignore
	@Test
	public void testGetBounds() throws Exception {
		fail();
	}

	@Test
	public void testSetVisibleSetsChildren() throws Exception {
		Layer root = layerFactory.createLayer("l");
		Layer folder = layerFactory.createLayer("l");
		Layer leaf = mock(Layer.class);
		folder.addLayer(leaf);
		root.addLayer(folder);

		root.setVisible(false);
		verify(leaf).setVisible(false);

		root.setVisible(true);
		verify(leaf).setVisible(true);
	}

	@Test
	public void testSetSelectedDoesNotSetChildren() throws Exception {
		Layer root = layerFactory.createLayer("l");
		Layer folder = layerFactory.createLayer("l");
		Layer leaf = mock(Layer.class);
		folder.addLayer(leaf);
		root.addLayer(folder);

		root.setSelected(true);
		verify(leaf, never()).setVisible(anyBoolean());

		root.setSelected(false);
		verify(leaf, never()).setVisible(anyBoolean());
	}

	@Test
	public void testSelectionNotSupported() throws Exception {
		Layer root = layerFactory.createLayer("l");
		Selection sel = mock(Selection.class);
		try {
			root.setSelection(sel);
			fail();
		} catch (UnsupportedOperationException e) {
		}
	}

	@Test
	public void testSelectionInitialization() throws Exception {
		Layer layer = layerFactory.createLayer("l", mock(Source.class));

		assertTrue(layer.getSelection().isEmpty());
	}

	@Test
	public void testSelectionEvent() throws Exception {
		Layer layer = mockLayerWithType("l");

		layer.setSelection(new Selection());

		verify(eventBus).fireEvent(any(FeatureSelectionChangeEvent.class));
	}

	@Test
	public void testProcess() throws Exception {
		Layer l1 = layerFactory.createLayer("l", mock(Source.class));
		Layer l2 = layerFactory.createLayer("l", mock(Source.class));
		Layer layer = layerFactory.createLayer("l", l1, l2);

		layer.process(new LayerProcessor() {
			@Override
			public void process(Layer layer) {
				layer.setVisible(false);
			}
		});

		assertFalse(layer.isVisible());
		assertFalse(l1.isVisible());
		assertFalse(l2.isVisible());
	}

	@Test
	public void testProcessWithFilter() throws Exception {
		Layer l1 = layerFactory.createLayer("l1", mock(Source.class));
		Layer l2 = layerFactory.createLayer("l2", mock(Source.class));
		Layer layer = layerFactory.createLayer("l", l1, l2);

		// Set visible == false only for "l1"
		layer.process(new LayerFilter() {

			@Override
			public boolean accepts(Layer layer) {
				return layer.getName().equals("l1");
			}
		}, new LayerProcessor() {
			@Override
			public void process(Layer layer) {
				layer.setVisible(false);
			}
		});

		assertTrue(layer.isVisible());
		assertFalse(l1.isVisible());
		assertTrue(l2.isVisible());
	}

	private Layer mockLayerWithType(String name) throws Exception {
		return mockLayerWithType(name, Point.class);
	}

	private Layer mockLayerWithType(String name,
			final Class<? extends Geometry> type) throws Exception {
		GeometryType geomType = mock(GeometryType.class);
		when(geomType.getBinding()).then(
				new Answer<Class<? extends Geometry>>() {
					@Override
					public Class<? extends Geometry> answer(
							InvocationOnMock invocation) throws Throwable {
						return type;
					}
				});
		GeometryDescriptor geomDescriptor = mock(GeometryDescriptor.class);
		when(geomDescriptor.getType()).thenReturn(geomType);
		SimpleFeatureType schema = mock(SimpleFeatureType.class);
		when(schema.getGeometryDescriptor()).thenReturn(geomDescriptor);
		SimpleFeatureSource featureSource = mock(SimpleFeatureSource.class);
		when(featureSource.getSchema()).thenReturn(schema);
		Source source = mock(Source.class);
		when(source.createFeatureSource()).thenReturn(featureSource);
		return layerFactory.createLayer(name, source);
	}
}