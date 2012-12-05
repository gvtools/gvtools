package org.gvsig.layer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import geomatico.events.EventBus;

import org.gvsig.GVSIGTestCase;
import org.gvsig.events.LayerAddedEvent;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.persistence.generated.LayerType;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;

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

	public void testContains() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer(mock(Source.class));
		assertFalse(layer.contains(mock(Layer.class)));
		assertTrue(layer.contains(layer));

		// Composite
		layer = layerFactory.createLayer();
		Layer l1 = mock(Layer.class);
		when(l1.contains(l1)).thenReturn(true);
		Layer l2 = mock(Layer.class);
		when(l2.contains(l2)).thenReturn(true);
		Layer l3 = mock(Layer.class);
		when(l3.contains(l3)).thenReturn(true);
		Layer composite = layerFactory.createLayer(l3);
		layer.addLayer(l1);
		layer.addLayer(composite);

		assertTrue(layer.contains(layer));
		assertTrue(layer.contains(l1));
		assertTrue(layer.contains(composite));
		assertTrue(layer.contains(l3));
		assertFalse(layer.contains(l2));
		assertFalse(layer.contains(null));
	}

	public void testGetAllLayers() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer(mock(Source.class));
		Layer[] layers = layer.getAllLayersInTree();
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Empty composite
		layer = layerFactory.createLayer();
		layers = layer.getAllLayersInTree();
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Non-empty composite
		Layer l1 = layerFactory.createLayer(mock(Source.class));
		Layer l2 = layerFactory.createLayer(mock(Source.class));
		layer = layerFactory.createLayer(l1, l2);
		layers = layer.getAllLayersInTree();
		assertEquals(3, layers.length);
		assertEquals(layer, layers[0]);
		assertEquals(l1, layers[1]);
		assertEquals(l2, layers[2]);
	}

	public void testAddLayer() throws Exception {
		// Feature
		Layer child = layerFactory.createLayer(mock(Source.class));
		try {
			layerFactory.createLayer(mock(Source.class)).addLayer(child);
			fail();
		} catch (UnsupportedOperationException e) {
		}

		// Composite
		Layer layer = layerFactory.createLayer();
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

	public void testFilter() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer(mock(Source.class));
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
		Layer l1 = mock(Layer.class);
		Layer l2 = mock(Layer.class);
		layer = layerFactory.createLayer(l1, l2);

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

	public void testSymbology() throws Exception {
		fail("Check default symbology for point layers, "
				+ "multipoint layers, line layers, etc. "
				+ "and heterogeneous layers. We'd better wait till"
				+ "symbology panels are migrated");
	}

	public void testHasFeatures() throws Exception {
		assertTrue(layerFactory.createLayer(mock(Source.class)).hasFeatures());
		assertFalse(layerFactory.createLayer().hasFeatures());
	}

	public void testIsActive() throws Exception {
		assertFalse(layerFactory.createLayer(mock(Source.class)).isActive());
		assertFalse(layerFactory.createLayer().isActive());
	}

	public void testEditing() throws Exception {
		assertFalse(layerFactory.createLayer(mock(Source.class)).isEditing());
		assertFalse(layerFactory.createLayer().isEditing());
	}

	public void testDataLayerXML() throws Exception {
		Layer layer = layerFactory.createLayer(mock(Source.class));
		LayerType xml = layer.getXML();
		Layer copy = layerFactory.createLayer(xml);

		fail("Should compare layer == copy when there are some getters");
	}

	public void testCompositeLayerXML() throws Exception {
		Layer root = layerFactory.createLayer();
		Layer folder = layerFactory.createLayer();
		Layer leaf = layerFactory.createLayer(mock(Source.class));
		folder.addLayer(leaf);
		root.addLayer(folder);

		LayerType xml = root.getXML();
		Layer copy = layerFactory.createLayer(xml);

		assertTrue(copy.getAllLayersInTree().length == 3);
		// For invocations, two at creation at two at copy
		verify(eventBus, times(4)).fireEvent(any(LayerAddedEvent.class));
	}

	public void testRemoveLayer() throws Exception {
		// Feature
		Layer layer = layerFactory.createLayer(mock(Source.class));
		try {
			layer.removeLayer(mock(Layer.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}

		// Composite
		Layer l1 = layerFactory.createLayer(mock(Source.class));
		Layer l2 = layerFactory.createLayer(mock(Source.class));
		layer = layerFactory.createLayer(l1, l2);
		assertFalse(layer.removeLayer(mock(Layer.class)));
		assertTrue(layer.removeLayer(l1));
		Layer[] layers = layer.getAllLayersInTree();
		assertEquals(2, layers.length);
		assertEquals(layer, layers[0]);
		assertEquals(l2, layers[1]);
	}

	public void testGetBounds() throws Exception {
		fail();
	}
}
