package org.gvsig.layer;

import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStoreFinder;
import org.gvsig.persistence.generated.DataSourceType;

/**
 * Interface to create {@link Source} instances
 * 
 * @author Fernando González Cortés
 * @author Víctor González Cortés
 */
public interface SourceFactory {

	/**
	 * Creates a {@link Source} that accesses the specified <code>path</code>
	 * 
	 * @param path
	 * @return
	 */
	Source createFileSource(String path);

	/**
	 * Creates a {@link Source} that accesses the specified database
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param password
	 * @param dbName
	 * @param tableName
	 * @param driverInfo
	 *            Name of the old gvSIG driver names. They no longer exist but
	 *            their names are used to know the vendor of the database we are
	 *            trying to connect
	 * @return
	 * @deprecated This method is only used to support old persistence. Use
	 *             {@link #createSource(HashMap)} instead
	 */
	Source createDBSource(String host, int port, String user, String password,
			String dbName, String tableName, String driverInfo);

	/**
	 * Creates a new Source.
	 * 
	 * @param featureName
	 *            The name of the type inside the DataStore that this Source
	 *            will reference. null for the first type
	 * @param properties
	 *            just passed to
	 *            {@link DataStoreFinder#getDataStore(java.util.Map)}
	 * @return
	 */
	Source createSource(String featureName, Map<String, String> properties);

	/**
	 * The same as createSource(null, Map)
	 * 
	 * @param properties
	 * @return
	 * @see #createSource(String, Map)
	 */
	Source createSource(Map<String, String> properties);

	Source createSource(DataSourceType xml);
}
