package org.gvsig.legend;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.IntervalLegend.Type;

import com.vividsolutions.jts.geom.Geometry;

public interface LegendFactory {
	Legend createDefaultLegend(Layer layer);

	Legend createSingleSymbolLegend(Layer layer, Symbolizer symbolizer);

	Symbolizer createDefaultSymbol(Class<? extends Geometry> type, Color color,
			String description);

	IntervalLegend createIntervalLegend(Color start, Color end, Type type,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String field, int nIntervals) throws IOException;

	IntervalLegend createIntervalLegend(Map<Interval, Symbolizer> symbols,
			Type type, Symbolizer defaultSymbol, boolean useDefault,
			String field) throws IOException;
}
