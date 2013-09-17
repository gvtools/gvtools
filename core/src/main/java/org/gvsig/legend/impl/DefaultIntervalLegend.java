package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.legend.IntervalLegend;
import org.gvsig.legend.LegendFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.FilterFactory2;

public class DefaultIntervalLegend implements IntervalLegend {
	private static final Logger logger = Logger
			.getLogger(DefaultIntervalLegend.class);

	private Color start, end;
	private Symbolizer defaultSymbol;
	private boolean useDefault;
	private Type type;
	private Map<Interval, Symbolizer> symbols;

	private String fieldName;

	private LegendFactory legendFactory;
	private StyleFactory styleFactory;
	private FilterFactory2 filterFactory;

	public DefaultIntervalLegend(Color start, Color end, int nIntervals,
			Symbolizer defaultSymbol, boolean useDefault, Type intervalType,
			Layer layer, String fieldName, LegendFactory legendFactory,
			StyleFactory styleFactory, FilterFactory2 filterFactory)
			throws IOException {
		this.start = start;
		this.end = end;
		this.defaultSymbol = defaultSymbol;
		this.useDefault = useDefault;
		this.type = intervalType;
		this.fieldName = fieldName;
		this.legendFactory = legendFactory;
		this.styleFactory = styleFactory;
		this.filterFactory = filterFactory;

		createSymbols(nIntervals, layer, fieldName);
	}

	public DefaultIntervalLegend(Map<Interval, Symbolizer> symbols,
			Symbolizer defaultSymbol, boolean useDefault, Type intervalType,
			String fieldName, LegendFactory legendFactory,
			StyleFactory styleFactory, FilterFactory2 filterFactory)
			throws IOException {
		this.defaultSymbol = defaultSymbol;
		this.useDefault = useDefault;
		this.type = intervalType;
		this.fieldName = fieldName;
		this.legendFactory = legendFactory;
		this.styleFactory = styleFactory;
		this.filterFactory = filterFactory;
		this.symbols = symbols;

		Interval[] intervals = getIntervals();
		if (intervals.length > 0) {
			this.start = getColor(symbols.get(intervals[0]));
			this.end = getColor(symbols.get(intervals[intervals.length - 1]));
		}
	}

	private Color getColor(Symbolizer symbolizer) {
		if (symbolizer instanceof PointSymbolizer) {
			return SLD.color((PointSymbolizer) symbolizer);
		} else if (symbolizer instanceof LineSymbolizer) {
			return SLD.color((LineSymbolizer) symbolizer);
		} else if (symbolizer instanceof PolygonSymbolizer) {
			return SLD.color(((PolygonSymbolizer) symbolizer).getFill());
		} else {
			throw new IllegalArgumentException("bug! Unrecognized symbolizer");
		}
	}

	private void createSymbols(int nIntervals, Layer layer, String fieldName)
			throws IOException {
		double min = Double.MAX_VALUE;
		double max = Double.NEGATIVE_INFINITY;

		SimpleFeatureIterator features = layer.getFeatureSource().getFeatures()
				.features();

		while (features.hasNext()) {
			SimpleFeature feature = features.next();
			Object value = feature.getAttribute(fieldName);
			double doubleValue;
			try {
				doubleValue = Double.parseDouble(value.toString());
			} catch (NumberFormatException e) {
				logger.error(e);
				continue;
			}

			if (doubleValue < min) {
				min = doubleValue;
			} else if (doubleValue > max) {
				max = doubleValue;
			}
		}

		Interval[] intervals = null;
		switch (type) {
		case NATURAL:
			intervals = computeNaturalIntervals(min, max);
			break;
		case EQUAL:
			intervals = computeEqualIntervals(nIntervals, min, max);
			break;
		case QUANTILE:
			intervals = computeQuantileIntervals();
			break;
		}

		if (intervals == null) {
			throw new IOException("bug! Unsupported interval type: " + type);
		}

		int r = start.getRed();
		int g = start.getGreen();
		int b = start.getBlue();
		int stepR = (end.getRed() - r) / nIntervals;
		int stepG = (end.getGreen() - g) / nIntervals;
		int stepB = (end.getBlue() - b) / nIntervals;

		symbols = new HashMap<Interval, Symbolizer>();

		for (Interval interval : intervals) {
			String description = NumberFormat.getInstance().format(
					interval.getMin())
					+ " - "
					+ NumberFormat.getInstance().format(interval.getMax());
			Symbolizer symbol = legendFactory.createDefaultSymbol(
					layer.getShapeType(), new Color(r, g, b), description);

			r = r + stepR;
			g = g + stepG;
			b = b + stepB;

			symbols.put(interval, symbol);
		}
	}

	private Interval[] computeEqualIntervals(int nIntervals, double min,
			double max) {
		Interval[] intervals = new Interval[nIntervals];
		double step = (max - min) / nIntervals;

		if (nIntervals > 1) {
			intervals[0] = new Interval(min, min + step);

			for (int i = 1; i < (nIntervals - 1); i++) {
				intervals[i] = new Interval(min + (i * step), min
						+ ((i + 1) * step));
			}

			intervals[nIntervals - 1] = new Interval(min
					+ ((nIntervals - 1) * step), max);
		} else {
			intervals[0] = new Interval(min, max);
		}

		return intervals;
	}

	private Interval[] computeNaturalIntervals(double min, double max) {
		throw new UnsupportedOperationException("Unsupported natural intervals");
	}

	private Interval[] computeQuantileIntervals() {
		throw new UnsupportedOperationException(
				"Unsupported quantile intervals");
	}

	@Override
	public Style getStyle() {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle();
		for (Interval interval : symbols.keySet()) {
			Rule rule = styleFactory.createRule();
			rule.symbolizers().add(symbols.get(interval));
			rule.setFilter(filterFactory.between(
					filterFactory.property(fieldName),
					filterFactory.literal(interval.getMin()),
					filterFactory.literal(interval.getMax())));
			rule.setElseFilter(false);
			fts.rules().add(rule);
		}

		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}

	@Override
	public boolean usesDefaultSymbol() {
		return useDefault;
	}

	@Override
	public void setUseDefault(boolean useDefault) {
		this.useDefault = useDefault;
	}

	@Override
	public Interval[] getIntervals() {
		List<Interval> orderedIntervals = new ArrayList<Interval>();
		orderedIntervals.addAll(symbols.keySet());
		Collections.sort(orderedIntervals, new Comparator<Interval>() {
			@Override
			public int compare(Interval o1, Interval o2) {
				if (o1.getMin() < o2.getMax()) {
					return -1;
				} else if (o1.getMin() == o2.getMax()) {
					return 0;
				} else {
					return 1;
				}
			}
		});

		return orderedIntervals.toArray(new Interval[orderedIntervals.size()]);
	}

	@Override
	public Symbolizer[] getSymbols() {
		Interval[] intervals = getIntervals();
		Symbolizer[] ret = new Symbolizer[symbols.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = symbols.get(intervals[i]);
		}
		return ret;
	}

	@Override
	public Color getStartColor() {
		return start;
	}

	@Override
	public Color getEndColor() {
		return end;
	}

	@Override
	public Symbolizer getDefaultSymbol() {
		return defaultSymbol;
	}

	@Override
	public Type getType() {
		return type;
	}
}
