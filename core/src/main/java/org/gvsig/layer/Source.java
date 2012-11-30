package org.gvsig.layer;

import java.io.IOException;
import java.net.URL;

import org.geotools.data.simple.SimpleFeatureSource;
import org.gvsig.persistence.generated.DataSourceType;

/**
 * Abstraction of a data source that is accessed through layers. Any
 * implementation must implement the {@link #equals(Object)} method (and
 * therefore, implement {@link #hashCode()} accordingly)
 * 
 * @author fergonco
 */
public interface Source {

	URL getURL();

	/**
	 * Returns a {@link DefaultLegend} instance if this source provides one.
	 * Null if the layers created from this source should have a default legend
	 * initially
	 * 
	 * @return
	 */
	DefaultLegend getDefaultLegend();

	/**
	 * Returns a XML object that can be used to rebuild this instance in the
	 * future by calling to the
	 * {@link SourceFactory#createSource(DataSourceType)} method
	 * 
	 * @return
	 */
	DataSourceType getXML();

	/**
	 * Creates and returns a {@link SimpleFeatureSource} for this Source. All
	 * resources allocated for this can be kept since a call to
	 * {@link #dispose()} is assured in the future
	 * 
	 * @return
	 * @throws IOException
	 *             If the SimpleFeatureSource cannot be instantiated
	 */
	SimpleFeatureSource createFeatureSource() throws IOException;

	/**
	 * Frees all resources allocated in a call to {@link #createFeatureSource()}
	 * . This method may be called without calling
	 * {@link #createFeatureSource()} before
	 */
	void dispose();

}
