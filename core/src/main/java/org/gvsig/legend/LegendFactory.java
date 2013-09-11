package org.gvsig.legend;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;

public interface LegendFactory {
	Legend createDefaultLegend(Layer layer);

	Legend createSingleSymbolLegend(Layer layer, Symbolizer symbolizer);
}
