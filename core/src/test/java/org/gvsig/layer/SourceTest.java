package org.gvsig.layer;

import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureSource;
import org.gvsig.GVSIGTestCase;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

public class SourceTest extends GVSIGTestCase {

	@Inject
	private SourceFactory sourceFactory;

	private DataStoreFinder mockFinder = mock(DataStoreFinder.class);

	@Override
	protected Module getOverridingModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				try {
					DataStore mockDataStore = mock(DataStore.class);
					when(mockDataStore.getFeatureSource((String) null))
							.thenThrow(new NullPointerException());
					when(mockDataStore.getFeatureSource("name1")).thenReturn(
							mock(SimpleFeatureSource.class));
					when(mockDataStore.getTypeNames()).thenReturn(
							new String[] { "name1", "name2" });
					when(
							mockFinder.getDataStore(anyMapOf(String.class,
									String.class))).thenReturn(mockDataStore);
					bind(DataStoreFinder.class).toInstance(mockFinder);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public void testSourceGetDefaultTypeName() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "file:///tmp/my.shp");
		Source source = sourceFactory.createSource(properties);

		SimpleFeatureSource featureSource = source.createFeatureSource();

		assertTrue(featureSource != null);
	}

	/**
	 * source must implement equals
	 */
	public void testEquals() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "file:///tmp/my.shp");
		properties.put("foo", "bar");
		Source s1 = sourceFactory.createSource(properties);
		Source s2 = sourceFactory.createSource(properties);
		properties = new HashMap<String, String>();
		properties.put("url", "file:///tmp/my.shp");
		Source s3 = sourceFactory.createSource(properties);

		assertTrue(s1.equals(s2));
		assertTrue(!s1.equals(s3));
		assertTrue(!s2.equals(s3));
	}

	public void testPersistence() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "file:///tmp/my.shp");
		properties.put("foo", "bar");
		Source s1 = sourceFactory.createSource(properties);
		Source s2 = sourceFactory.createSource(s1.getXML());

		assertTrue(s1.equals(s2));
	}
}
