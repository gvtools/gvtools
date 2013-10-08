package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.legend.LegendFactory;
import org.gvsig.legend.impl.AbstractIntervalLegend.Type;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class LegendFactoryImpl implements LegendFactory {
	@Inject
	private Provider<SingleSymbolLegend> singleSymbolLegendProvider;
	@Inject
	private Provider<IntervalLegend> intervalLegendProvider;
	@Inject
	private Provider<UniqueValueLegend> uniqueValueLegendProvider;
	@Inject
	private Provider<ProportionalLegend> proportionalLegendProvider;
	@Inject
	private Provider<SizeIntervalLegend> sizeIntervalLegendProvider;

	@Override
	public SingleSymbolLegend createSingleSymbolLegend(Layer layer) {
		SingleSymbolLegend legend = singleSymbolLegendProvider.get();
		legend.init(layer);
		return legend;
	}

	@Override
	public SingleSymbolLegend createSingleSymbolLegend(Layer layer,
			Symbolizer symbol) {
		SingleSymbolLegend legend = singleSymbolLegendProvider.get();
		legend.init(layer, symbol);
		return legend;
	}

	@Override
	public IntervalLegend createIntervalLegend(Color start, Color end,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			Layer layer, String fieldName, int nIntervals) {
		IntervalLegend legend = intervalLegendProvider.get();
		legend.init(start, end, intervalType, defaultSymbol, useDefault, layer,
				fieldName, nIntervals);
		return legend;
	}

	@Override
	public IntervalLegend createIntervalLegend(
			Map<Interval, Symbolizer> symbols, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName) throws IOException {
		IntervalLegend legend = intervalLegendProvider.get();
		legend.init(symbols, intervalType, defaultSymbol, useDefault, layer,
				fieldName);
		return legend;
	}

	@Override
	public IntervalLegend createIntervalLegend(Layer layer, String fieldName) {
		IntervalLegend legend = intervalLegendProvider.get();
		legend.init(layer, fieldName);
		return legend;
	}

	@Override
	public UniqueValueLegend createUniqueValueLegend(Layer layer,
			String fieldName, Symbolizer defaultSymbol, boolean useDefault,
			Color[] colorScheme, Comparator<Object> order) {
		UniqueValueLegend legend = uniqueValueLegendProvider.get();
		legend.init(layer, fieldName, defaultSymbol, useDefault, colorScheme,
				order);
		return legend;
	}

	@Override
	public UniqueValueLegend createUniqueValueLegend(Layer layer,
			String fieldName, Symbolizer defaultSymbol, boolean useDefault,
			Color[] colorScheme, Map<Object, Symbolizer> symbols,
			Comparator<Object> order) {
		UniqueValueLegend legend = uniqueValueLegendProvider.get();
		legend.init(layer, fieldName, defaultSymbol, useDefault, colorScheme,
				symbols, order);
		return legend;
	}

	@Override
	public ProportionalLegend createProportionalLegend(Layer layer,
			String valueField, String normalizationField, Symbolizer template,
			Symbolizer background, Interval size) {
		ProportionalLegend legend = proportionalLegendProvider.get();
		legend.init(layer, valueField, normalizationField, template,
				background, size);
		return legend;
	}

	@Override
	public ProportionalLegend createProportionalLegend(Layer layer,
			String valueField, Symbolizer template, Symbolizer background,
			Interval size) {
		return createProportionalLegend(layer, valueField, null, template,
				background, size);
	}

	@Override
	public SizeIntervalLegend createSizeIntervalLegend(Interval size,
			Type intervalType, Symbolizer defaultSymbol, boolean useDefault,
			Layer layer, String fieldName, int nIntervals, Symbolizer template,
			Symbolizer background) {
		SizeIntervalLegend legend = sizeIntervalLegendProvider.get();
		legend.init(size, intervalType, defaultSymbol, useDefault, layer,
				fieldName, nIntervals, template, background);
		return legend;
	}

	@Override
	public SizeIntervalLegend createSizeIntervalLegend(
			Map<Interval, Symbolizer> symbols, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, Symbolizer background) throws IOException {
		SizeIntervalLegend legend = sizeIntervalLegendProvider.get();
		legend.init(symbols, intervalType, defaultSymbol, useDefault, layer,
				fieldName, background);
		return legend;
	}

	@Override
	public SizeIntervalLegend createSizeIntervalLegend(Layer layer,
			String fieldName) {
		SizeIntervalLegend legend = sizeIntervalLegendProvider.get();
		legend.init(layer, fieldName);
		return legend;
	}

	@Override
	public QuantityByCategoryLegend createQuantityByCategoryLegend(Layer layer,
			IntervalLegend colorLegend, SizeIntervalLegend sizeLegend) {
		return createQuantityByCategoryLegend(layer, colorLegend, sizeLegend);
	}
}
