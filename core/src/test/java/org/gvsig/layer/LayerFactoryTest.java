package org.gvsig.layer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.gvsig.GVSIGTestCase;

import com.google.inject.Inject;

public class LayerFactoryTest extends GVSIGTestCase {
	@Inject
	private LayerFactory factory;
	@Inject
	private SourceFactory sourceFactory;

	public void testCreateFromNullSource() throws Exception {
		try {
			factory.createLayer("l", (Source) null);
			fail();
		} catch (IOException e) {
		}
	}

	public void testCreateFromInvalidSource() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "invalid_url");
		Source source = sourceFactory.createSource(properties);
		try {
			factory.createLayer("l", source);
			fail();
		} catch (IOException e) {
		}
	}

	public void testThinkMoreTests() throws Exception {
		fail();
	}
}
