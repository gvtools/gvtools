package org.gvsig.layer.impl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureSource;
import org.gvsig.layer.DataStoreFinder;
import org.gvsig.layer.DefaultLegend;
import org.gvsig.layer.Source;
import org.gvsig.persistence.generated.DataSourceType;
import org.gvsig.persistence.generated.StringPropertyType;

public class SourceImpl implements Source {

	private Map<String, String> properties;
	private String typeName;
	private DataStore dataStore;
	private DataStoreFinder dataStoreFinder;

	public SourceImpl(DataStoreFinder dataStoreFinder, String typeName,
			Map<String, String> properties) {
		this.dataStoreFinder = dataStoreFinder;
		this.typeName = typeName;
		this.properties = properties;
	}

	@Override
	public URL getURL() {
		assert false;
		return null;
	}

	@Override
	public DefaultLegend getDefaultLegend() {
		assert false;
		return null;
	}

	@Override
	public DataSourceType getXML() {
		DataSourceType xml = new DataSourceType();
		List<StringPropertyType> xmlProps = xml.getProperties();
		xmlProps.clear();
		for (String name : properties.keySet()) {
			StringPropertyType xmlProp = new StringPropertyType();
			xmlProp.setPropertyName(name);
			xmlProp.setPropertyValue(properties.get(name));

			xmlProps.add(xmlProp);
		}
		return xml;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SourceImpl) {
			SourceImpl that = (SourceImpl) obj;
			return this.properties.equals(that.properties);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return properties.hashCode();
	}

	@Override
	public SimpleFeatureSource createFeatureSource() throws IOException {
		DataStore dataStore = getDataStore();
		if (dataStore == null) {
			throw new IOException("Cannot obtain data store from source");
		}

		String actualTypeName = typeName;
		if (actualTypeName == null) {
			String[] typeNames = dataStore.getTypeNames();
			assert typeNames.length > 0;
			actualTypeName = typeNames[0];
		}
		return dataStore.getFeatureSource(actualTypeName);
	}

	private DataStore getDataStore() throws IOException {
		if (dataStore == null) {
			dataStore = dataStoreFinder.getDataStore(properties);
		}

		return dataStore;
	}

	@Override
	public void dispose() {
		if (dataStore == null) {
			dataStore.dispose();
		}
	}
}
