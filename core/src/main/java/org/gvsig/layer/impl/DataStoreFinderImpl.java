package org.gvsig.layer.impl;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.DataStore;
import org.gvsig.layer.DataStoreFinder;

public class DataStoreFinderImpl implements DataStoreFinder {

	@Override
	public DataStore getDataStore(Map<String, String> properties)
			throws IOException {
		return org.geotools.data.DataStoreFinder.getDataStore(properties);
	}

}
