package org.gvsig.layer;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.DataStore;

public interface DataStoreFinder {

	DataStore getDataStore(Map<String, String> properties) throws IOException;

}
