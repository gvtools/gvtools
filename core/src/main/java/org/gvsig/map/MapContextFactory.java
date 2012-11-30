package org.gvsig.map;

import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.MapType;
import org.gvsig.units.Unit;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public interface MapContextFactory {

	MapContext createMapContext(Unit mapUnits, Unit distanceUnits,
			Unit areaUnits, CoordinateReferenceSystem crs);

	/**
	 * Creates a map context from a XML object
	 * 
	 * @param xml
	 * @return
	 * @throws PersistenceException
	 *             If the instance cannot be created
	 */
	MapContext createMapContext(MapType xml) throws PersistenceException;

}
