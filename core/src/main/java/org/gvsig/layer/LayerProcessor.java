package org.gvsig.layer;

/**
 * Processor of layers.
 * 
 * @see Layer#process(LayerProcessor)
 * @see Layer#process(org.gvsig.layer.filter.LayerFilter, LayerProcessor)
 * 
 * @author Fernando González Cortés
 */
public interface LayerProcessor {

	void process(Layer layer);
}
