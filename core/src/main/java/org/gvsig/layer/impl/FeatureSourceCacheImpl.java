package org.gvsig.layer.impl;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureSource;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Source;

public class FeatureSourceCacheImpl implements FeatureSourceCache {
	@Override
	public SimpleFeatureSource getFeatureSource(Source source)
			throws IOException {
		return source.createFeatureSource();
	}
}
