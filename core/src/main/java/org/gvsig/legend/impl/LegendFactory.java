package org.gvsig.legend.impl;

import java.awt.Color;
import java.util.Comparator;
import java.util.Map;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;

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
			String fieldName, int nIntervals);

	IntervalLegend createIntervalLegend(Map<Interval, Symbolizer> symbols,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName);

	UniqueValueLegend createUniqueValueLegend(Layer layer, String fieldName,
			Symbolizer defaultSymbol, boolean useDefault, Color[] colorScheme,
			Comparator<Object> order);

	UniqueValueLegend createUniqueValueLegend(Layer layer, String fieldName,
			Symbolizer defaultSymbol, boolean useDefault, Color[] colorScheme,
			Map<Object, Symbolizer> symbols, Comparator<Object> order);

	ProportionalLegend createProportionalLegend(Layer layer,
			@Assisted("value") String valueField,
			@Assisted("normalization") String normalizationField,
			@Assisted("template") Symbolizer template,
			@Assisted("background") Symbolizer background,
			boolean useBackground, Interval size);

	SizeIntervalLegend createSizeIntervalLegend(Interval size,
			Type intervalType, Symbolizer defaultSymbol,
			@Assisted("usedefault") boolean useDefault, Layer layer,
			String fieldName, int nIntervals,
			@Assisted("template") Symbolizer template,
			@Assisted("background") Symbolizer background,
			@Assisted("usebackground") boolean useBackground);

	SizeIntervalLegend createSizeIntervalLegend(
			Map<Interval, Symbolizer> symbolsMap, Symbolizer defaultSymbol,
			@Assisted("usedefault") boolean useDefault, Layer layer,
			String fieldName, @Assisted("background") Symbolizer background,
			@Assisted("usebackground") boolean useBackground);
}
