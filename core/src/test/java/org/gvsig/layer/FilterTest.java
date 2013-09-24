package org.gvsig.layer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.geotools.filter.identity.FeatureIdImpl;
import org.gvsig.GVSIGTestCase;
import org.gvsig.layer.filter.AndLayerFilter;
import org.gvsig.layer.filter.LayerFilter;
import org.junit.Test;

import com.google.inject.Inject;

public class FilterTest extends GVSIGTestCase {
	@Inject
	private LayerFactory layerFactory;

	@Test
	public void testActive() throws Exception {
		// Feature active
		Layer layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isActive()).thenReturn(true);

		Layer[] layers = layer.filter(LayerFilter.ACTIVE);
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Feature not active
		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isActive()).thenReturn(false);
		layers = layer.filter(LayerFilter.ACTIVE);
		assertEquals(0, layers.length);

		// Composite
		Layer l1 = spy(layerFactory.createLayer("l1", mock(Source.class)));
		Layer l2 = spy(layerFactory.createLayer("l2", mock(Source.class)));
		when(l1.isActive()).thenReturn(false);
		when(l2.isActive()).thenReturn(true);
		layer = layerFactory.createLayer("l", l1, l2);

		layers = layer.filter(LayerFilter.ACTIVE);
		assertEquals(1, layers.length);
		assertEquals(l2, layers[0]);
	}

	@Test
	public void testComposite() throws Exception {
		// Active and feature filter
		AndLayerFilter filter = new AndLayerFilter(LayerFilter.ACTIVE,
				LayerFilter.FEATURE);

		// Feature and active
		Layer l1 = spy(layerFactory.createLayer("l1", mock(Source.class)));
		when(l1.hasFeatures()).thenReturn(true);
		when(l1.isActive()).thenReturn(true);
		// Not feature and active
		Layer l2 = spy(layerFactory.createLayer("l2", mock(Source.class)));
		when(l2.hasFeatures()).thenReturn(false);
		when(l2.isActive()).thenReturn(true);
		// Feature and not active
		Layer l3 = spy(layerFactory.createLayer("l3", mock(Source.class)));
		when(l3.hasFeatures()).thenReturn(true);
		when(l3.isActive()).thenReturn(false);

		Layer layer = layerFactory.createLayer("l", l1, l2, l3);

		Layer[] layers = layer.filter(filter);
		assertEquals(1, layers.length);
		assertEquals(l1, layers[0]);
	}

	@Test
	public void testEditing() throws Exception {
		// Composite layer
		Layer l1 = spy(layerFactory.createLayer("l1", mock(Source.class)));
		when(l1.hasFeatures()).thenReturn(true);
		when(l1.isEditing()).thenReturn(true);
		Layer l2 = spy(layerFactory.createLayer("l2", mock(Source.class)));
		when(l2.hasFeatures()).thenReturn(false);
		when(l2.isEditing()).thenReturn(true);
		Layer l3 = spy(layerFactory.createLayer("l3", mock(Source.class)));
		when(l3.hasFeatures()).thenReturn(true);
		when(l3.isEditing()).thenReturn(false);

		Layer layer = layerFactory.createLayer("l", l1, l2, l3);
		Layer[] layers = layer.filter(LayerFilter.FEATURE_EDITING);
		assertEquals(1, layers.length);
		assertEquals(l1, layers[0]);

		// Feature layer
		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isEditing()).thenReturn(true);
		layers = layer.filter(LayerFilter.FEATURE_EDITING);
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isEditing()).thenReturn(false);
		layers = layer.filter(LayerFilter.FEATURE_EDITING);
		assertEquals(0, layers.length);
	}

	@Test
	public void testFeature() throws Exception {
		// Feature layer
		Layer layer = layerFactory.createLayer("l", mock(Source.class));
		Layer[] layers = layer.filter(LayerFilter.FEATURE);
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Composite layer
		Layer l1 = spy(layerFactory.createLayer("l1", mock(Source.class)));
		Layer l2 = spy(layerFactory.createLayer("l2", mock(Source.class)));
		when(l1.hasFeatures()).thenReturn(false);
		when(l2.hasFeatures()).thenReturn(true);
		layer = layerFactory.createLayer("l", l1, l2);

		layers = layer.filter(LayerFilter.FEATURE);
		assertEquals(1, layers.length);
		assertEquals(l2, layers[0]);
	}

	@Test
	public void testSelected() throws Exception {
		// Feature selected
		Layer layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isSelected()).thenReturn(true);

		Layer[] layers = layer.filter(LayerFilter.SELECTED);
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Feature not selected
		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.isSelected()).thenReturn(false);
		layers = layer.filter(LayerFilter.SELECTED);
		assertEquals(0, layers.length);

		// Composite
		Layer l1 = spy(layerFactory.createLayer("l1", mock(Source.class)));
		Layer l2 = spy(layerFactory.createLayer("l2", mock(Source.class)));
		when(l1.isSelected()).thenReturn(false);
		when(l2.isSelected()).thenReturn(true);
		layer = layerFactory.createLayer("l", l1, l2);

		layers = layer.filter(LayerFilter.SELECTED);
		assertEquals(1, layers.length);
		assertEquals(l2, layers[0]);
	}

	@Test
	public void testFeatureSelected() throws Exception {
		// Feature selected
		Layer layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.hasFeatures()).thenReturn(true);
		Selection selection = new Selection();
		selection.add(new FeatureIdImpl("a"));
		when(layer.getSelection()).thenReturn(selection);

		Layer[] layers = layer.filter(LayerFilter.FEATURE_SELECTED);
		assertEquals(1, layers.length);
		assertEquals(layer, layers[0]);

		// Feature not selected
		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.getSelection()).thenReturn(new Selection());
		when(layer.hasFeatures()).thenReturn(true);
		layers = layer.filter(LayerFilter.FEATURE_SELECTED);
		assertEquals(0, layers.length);

		// Not feature selected
		layer = spy(layerFactory.createLayer("l", mock(Source.class)));
		when(layer.hasFeatures()).thenReturn(false);
		when(layer.getSelection()).thenReturn(selection);
		layers = layer.filter(LayerFilter.FEATURE_SELECTED);
		assertEquals(0, layers.length);
	}

	@Test
	public void testFilterExploresAllTree() throws Exception {
		Layer root = layerFactory.createLayer("l");
		Layer folder = layerFactory.createLayer("l");
		Layer leaf = layerFactory.createLayer("leaf", mock(Source.class));
		folder.addLayer(leaf);
		root.addLayer(folder);

		assertTrue(root.filter(LayerFilter.FEATURE).length == 1);
	}
}
