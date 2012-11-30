package org.gvsig.layer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.geotools.data.simple.SimpleFeatureSource;
import org.gvsig.GVSIGTestCase;

public class FeatureSourceCacheTest extends GVSIGTestCase {
	@Inject
	private FeatureSourceCache sourceCache;

	public void testGetFeatureSource() throws Exception {
		Source source = mock(Source.class);
		SimpleFeatureSource featureSource = mock(SimpleFeatureSource.class);
		when(source.createFeatureSource()).thenReturn(featureSource);

		SimpleFeatureSource featureSourceResult = sourceCache
				.getFeatureSource(source);
		assertTrue(featureSource == featureSourceResult);
	}
}
