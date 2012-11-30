package org.gvsig.layer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.gvsig.layer.DataStoreFinder;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.persistence.generated.DataSourceType;
import org.gvsig.persistence.generated.StringPropertyType;

public class SourceFactoryImpl implements SourceFactory {

	@Inject
	private DataStoreFinder dataStoreFinder;

	@Override
	public Source createFileSource(String path) {
		assert false;
		return null;
	}

	@Override
	public Source createDBSource(String host, int port, String user,
			String password, String dbName, String tableName, String driverInfo) {
		assert false;
		return null;
	}

	@Override
	public Source createSource(Map<String, String> properties) {
		return createSource(null, properties);
	}

	@Override
	public Source createSource(String typeName, Map<String, String> properties) {
		return new SourceImpl(dataStoreFinder, typeName, properties);
	}

	@Override
	public Source createSource(DataSourceType xml) {
		Map<String, String> properties = new HashMap<String, String>();
		if (xml != null) {
			List<StringPropertyType> xmlProperties = xml.getProperties();
			for (StringPropertyType xmlProperty : xmlProperties) {
				properties.put(xmlProperty.getPropertyName(),
						xmlProperty.getPropertyValue());
			}
			return createSource(properties);
		} else {
			return null;
		}
	}
}
