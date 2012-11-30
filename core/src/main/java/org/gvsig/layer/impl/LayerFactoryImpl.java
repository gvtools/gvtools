package org.gvsig.layer.impl;

import geomatico.events.EventBus;

import javax.inject.Inject;

import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.layer.SymbolFactoryFacade;
import org.gvsig.persistence.generated.CompositeLayerType;
import org.gvsig.persistence.generated.DataLayerType;
import org.gvsig.persistence.generated.LayerType;

import com.google.inject.Provider;

public class LayerFactoryImpl implements LayerFactory {
	@Inject
	private Provider<FeatureSourceCache> featureSourceCache;

	@Inject
	private Provider<SymbolFactoryFacade> symbolFactoryFacadeProvider;

	@Inject
	private Provider<SourceFactory> sourceFactoryProvider;

	@Inject
	private Provider<EventBus> eventBusProvider;

	@Override
	public Layer createLayer(Source source) {
		FeatureLayer ret = newFeatureLayer();
		ret.setSource(source);

		return ret;
	}

	private FeatureLayer newFeatureLayer() {
		return new FeatureLayer(featureSourceCache.get(),
				symbolFactoryFacadeProvider.get(), sourceFactoryProvider.get());
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
