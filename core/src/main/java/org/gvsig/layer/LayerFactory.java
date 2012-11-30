package org.gvsig.layer;

import org.gvsig.persistence.generated.LayerType;

public interface LayerFactory {

	Layer createLayer(Source source);

	Layer createLayer(Layer... layers);

	Layer createLayer(LayerType xml);
}
