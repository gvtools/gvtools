package org.gvsig.layer;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureSource;

public interface FeatureSourceCache {
	SimpleFeatureSource getFeatureSource(Source source) throws IOException;
}
