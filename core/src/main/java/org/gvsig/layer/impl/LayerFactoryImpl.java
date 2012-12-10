package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import java.io.IOException;

import javax.inject.Inject;

import org.geotools.styling.StyleFactory;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;
import org.opengis.filter.FilterFactory2;

import com.google.inject.Provider;

public class LayerFactoryImpl implements LayerFactory {
	@Inject
	private Provider<FeatureSourceCache> featureSourceCache;

	@Inject
	private Provider<StyleFactory> styleFactoryProvider;

	@Inject
	private Provider<FilterFactory2> filterFactoryProvider;

	@Inject
	private Provider<SourceFactory> sourceFactoryProvider;

	@Inject
	private Provider<EventBus> eventBusProvider;

	@Override
	public Layer createLayer(Source source) throws IOException {
		if (source == null) {
			throw new IOException("Source cannot be null");
		}
		FeatureLayer ret = newFeatureLayer();
		ret.setSource(source);

		// Just to check the feature source is valid
		ret.getFeatureSource();

		return ret;
	}

	private FeatureLayer newFeatureLayer() {
		return new FeatureLayer(eventBusProvider.get(),
				featureSourceCache.get(), sourceFactoryProvider.get(),
				styleFactoryProvider.get(), filterFactoryProvider.get());
	}

	@Override
	public Layer createLayer(Layer... layers) {
		CompositeLayer composite = newCompositeLayer();
		for (Layer layer : layers) {
			composite.addLayer(layer);
		}
		return composite;
	}

	private CompositeLayer newCompositeLayer() {
		return new CompositeLayer(eventBusProvider.get(), this);
	}

	@Override
	public Layer createLayer(LayerType xml) {
		if (xml instanceof CompositeLayerType) {
			CompositeLayer composite = newCompositeLayer();
			composite.setXML(xml);

			return composite;
		} else if (xml instanceof DataLayerType) {
			FeatureLayer layer = newFeatureLayer();
			layer.setXML(xml);

			return layer;
		} else {
			throw new RuntimeException("bug");
		}
	}

}
