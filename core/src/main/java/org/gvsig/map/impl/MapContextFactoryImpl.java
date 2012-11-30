package org.gvsig.map.impl;

import geomatico.events.EventBus;

import javax.inject.Inject;

import org.gvsig.layer.LayerFactory;
import org.gvsig.map.MapContext;
import org.gvsig.map.MapContextFactory;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.MapType;
import org.gvsig.units.Unit;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.google.inject.Provider;

public class MapContextFactoryImpl implements MapContextFactory {

	@Inject
	private Provider<EventBus> eventBusProvider;

	@Inject
	private Provider<LayerFactory> layerFactoryProvider;

	@Override
	public MapContext createMapContext(Unit mapUnits, Unit distanceUnits,
			Unit areaUnits, CoordinateReferenceSystem crs) {
		MapContextImpl ret = newMapContext();

		ret.setMapUnits(mapUnits);
		ret.setDistanceUnits(distanceUnits);
		ret.setAreaUnits(areaUnits);
		ret.setCRS(crs);

		return ret;
	}

	private MapContextImpl newMapContext() {
		return new MapContextImpl(eventBusProvider.get(),
				layerFactoryProvider.get());
	}

	@Override
	public MapContext createMapContext(MapType xml) throws PersistenceException {
		MapContextImpl ret = newMapContext();
		ret.setXML(xml);
		return ret;
	}
}
