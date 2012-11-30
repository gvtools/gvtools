package org.gvsig.inject;

import geomatico.events.EventBus;

import org.gvsig.layer.DataStoreFinder;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.SymbolFactoryFacade;
import org.gvsig.layer.impl.DataStoreFinderImpl;
import org.gvsig.layer.impl.FeatureSourceCacheImpl;
import org.gvsig.layer.impl.LayerFactoryImpl;
import org.gvsig.layer.impl.SourceFactoryImpl;
import org.gvsig.layer.impl.SymbolFactoryFacadeImpl;
import org.gvsig.map.MapContextFactory;
import org.gvsig.map.impl.MapContextFactoryImpl;

import com.google.inject.AbstractModule;

/**
 * Guice module to configure the injection of implementations
 */
public class LibModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataStoreFinder.class).to(DataStoreFinderImpl.class);
		bind(SourceFactory.class).to(SourceFactoryImpl.class);
		bind(FeatureSourceCache.class).to(FeatureSourceCacheImpl.class);
		bind(LayerFactory.class).to(LayerFactoryImpl.class);
		bind(MapContextFactory.class).to(MapContextFactoryImpl.class);
		bind(SymbolFactoryFacade.class).to(SymbolFactoryFacadeImpl.class);
		bind(EventBus.class).toInstance(EventBus.getInstance());
	}
}
