package org.gvsig.layer;

import java.io.IOException;

import org.gvsig.persistence.generated.LayerType;

public interface LayerFactory {

	/**
	 * Creates a new Layer from the given {@link Source}
	 * 
	 * @param name
	 *            Name of the layer
	 * @param source
	 *            the source of the layer.
	 * @return the new layer
	 * @throws IOException
	 *             if the source is invalid or <code>null</code>.
	 */
	Layer createLayer(String name, Source source) throws IOException;

	Layer createLayer(String name, Layer... layers);

	Layer createLayer(LayerType xml);
}
