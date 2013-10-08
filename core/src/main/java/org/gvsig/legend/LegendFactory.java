package org.gvsig.legend;

import java.awt.Color;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;
import org.gvsig.legend.impl.IntervalLegend;
import org.gvsig.legend.impl.ProportionalLegend;
import org.gvsig.legend.impl.QuantityByCategoryLegend;
import org.gvsig.legend.impl.SingleSymbolLegend;
import org.gvsig.legend.impl.SizeIntervalLegend;
import org.gvsig.legend.impl.UniqueValueLegend;

public interface LegendFactory {
	SingleSymbolLegend createSingleSymbolLegend(Layer layer);

	SingleSymbolLegend createSingleSymbolLegend(Layer layer, Symbolizer symbol);

	IntervalLegend createIntervalLegend(Color start, Color end,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			Layer layer, String fieldName, int nIntervals);

	IntervalLegend createIntervalLegend(Map<Interval, Symbolizer> symbols,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			Layer layer, String fieldName) throws IOException;

	IntervalLegend createIntervalLegend(Layer layer, String fieldName);

	UniqueValueLegend createUniqueValueLegend(Layer layer, String fieldName,
			Symbolizer defaultSymbol, boolean useDefault, Color[] colorScheme,
			Comparator<Object> order);

	UniqueValueLegend createUniqueValueLegend(Layer layer, String fieldName,
			Symbolizer defaultSymbol, boolean useDefault, Color[] colorScheme,
			Map<Object, Symbolizer> symbols, Comparator<Object> order);

	ProportionalLegend createProportionalLegend(Layer layer, String valueField,
			String normalizationField, Symbolizer template,
			Symbolizer background, Interval size);

	ProportionalLegend createProportionalLegend(Layer layer, String valueField,
			Symbolizer template, Symbolizer background, Interval size);

	SizeIntervalLegend createSizeIntervalLegend(Interval size,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			Layer layer, String fieldName, int nIntervals, Symbolizer template,
			Symbolizer background);

	SizeIntervalLegend createSizeIntervalLegend(
			Map<Interval, Symbolizer> symbolsMap, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, Symbolizer background) throws IOException;

	SizeIntervalLegend createSizeIntervalLegend(Layer layer, String fieldName);

	QuantityByCategoryLegend createQuantityByCategoryLegend(Layer layer,
			IntervalLegend colorLegend, SizeIntervalLegend sizeLegend);
}
