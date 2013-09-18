package org.gvsig.inject;

import geomatico.events.EventBus;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.StyleFactory;
import org.gvsig.layer.DataStoreFinder;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.impl.DataStoreFinderImpl;
import org.gvsig.layer.impl.FeatureSourceCacheImpl;
import org.gvsig.layer.impl.LayerFactoryImpl;
import org.gvsig.layer.impl.SourceFactoryImpl;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.impl.DefaultSymbolsImpl;
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.LegendFactory;
import org.gvsig.legend.impl.SingleSymbolLegend;
import org.gvsig.map.MapContextFactory;
import org.gvsig.map.impl.MapContextFactoryImpl;
import org.opengis.filter.FilterFactory2;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

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
		bind(EventBus.class).toInstance(EventBus.getInstance());
		bind(StyleFactory.class).toInstance(
				CommonFactoryFinder.getStyleFactory());
		bind(FilterFactory2.class).toInstance(
				CommonFactoryFinder.getFilterFactory2());
		bind(DefaultSymbols.class).to(DefaultSymbolsImpl.class);
		install(new FactoryModuleBuilder()
				.implement(SingleSymbolLegend.class, SingleSymbolLegend.class)
				.implement(IntervalLegend.class, IntervalLegend.class)
				.build(LegendFactory.class));
	}
}
