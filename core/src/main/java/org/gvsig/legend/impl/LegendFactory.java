package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.legend.impl.IntervalLegend.Type;

import com.google.inject.assistedinject.Assisted;

/**
 * @author Víctor González
 * 
 *         Factory for assisted injection.
 */
public interface LegendFactory {
	SingleSymbolLegend createSingleSymbolLegend(Layer layer);

	SingleSymbolLegend createSingleSymbolLegend(Layer layer, Symbolizer symbol);

	IntervalLegend createIntervalLegend(@Assisted("start") Color start,
			@Assisted("end") Color end, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, int nIntervals) throws IOException;

	IntervalLegend createIntervalLegend(Map<Interval, Symbolizer> symbols,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			String fieldName) throws IOException;
}
