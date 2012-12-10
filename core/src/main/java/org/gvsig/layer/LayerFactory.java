package org.gvsig.layer;

import java.io.IOException;

import org.gvsig.persistence.generated.LayerType;

public interface LayerFactory {

	/**
	 * Creates a new Layer from the given {@link Source}
	 * 
	 * @param source
	 *            the source of the layer.
	 * @return the new layer
	 * @throws IOException
	 *             if the source is invalid or <code>null</code>.
	 */
	Layer createLayer(Source source) throws IOException;

	Layer createLayer(Layer... layers);

	Layer createLayer(LayerType xml);
}
