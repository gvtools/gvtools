package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Interval;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.iver.cit.gvsig.fmap.rendering.NaturalIntervalGenerator;
import com.iver.cit.gvsig.fmap.rendering.QuantileIntervalGenerator;

public abstract class AbstractIntervalLegend extends
		AbstractDefaultSymbolLegend {
	private static final Logger logger = Logger
			.getLogger(AbstractIntervalLegend.class);

	public static enum Type {
		EQUAL, NATURAL, QUANTILE
	}

	private Type type;
	private Map<Interval, Symbolizer> symbolsMap;
	private Interval[] intervals;
	private Symbolizer[] symbols;
	protected int nIntervals;
	private String fieldName;

	@AssistedInject
	public AbstractIntervalLegend(@Assisted Type intervalType,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Layer layer, @Assisted String fieldName,
			@Assisted int nIntervals) {
		super(layer, defaultSymbol, useDefault);
		this.type = intervalType;
		this.fieldName = fieldName;
		this.nIntervals = nIntervals;
	}

	@AssistedInject
	public AbstractIntervalLegend(
			@Assisted Map<Interval, Symbolizer> symbolsMap,
			@Assisted Type intervalType, @Assisted Symbolizer defaultSymbol,
			@Assisted boolean useDefault, @Assisted Layer layer,
			@Assisted String fieldName) {
		super(layer, defaultSymbol, useDefault);
		this.type = intervalType;
		this.fieldName = fieldName;
		this.symbolsMap = symbolsMap;
		this.nIntervals = symbolsMap.size();
	}

	@AssistedInject
	public AbstractIntervalLegend(@Assisted Layer layer,
			@Assisted String fieldName, DefaultSymbols defaultSymbols) {
		super(layer, defaultSymbols.createDefaultSymbol(layer.getShapeType(),
				Color.blue, ""), false);
		this.type = Type.EQUAL;
		this.fieldName = fieldName;
		this.nIntervals = 0;
		this.symbolsMap = new HashMap<Interval, Symbolizer>();
	}

	public Interval[] getIntervals() throws IOException {
		if (intervals == null) {
			List<Interval> list = new ArrayList<Interval>();
			list.addAll(symbolsMap().keySet());
			Collections.sort(list, new Comparator<Interval>() {
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

			intervals = list.toArray(new Interval[list.size()]);
		}
		return intervals;
	}

	public Symbolizer[] getSymbols() throws IOException {
		if (symbols == null) {
			Interval[] intervals = getIntervals();
			symbols = new Symbolizer[symbolsMap().size()];
			for (int i = 0; i < symbols.length; i++) {
				symbols[i] = symbolsMap().get(intervals[i]);
			}
			return symbols;
		}
		return symbols;
	}

	public Type getType() {
		return type;
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = styleFactory.createStyle();
		List<Filter> filters = new ArrayList<Filter>();
		for (Interval interval : symbolsMap().keySet()) {
			Filter filter = filterFactory.between(
					filterFactory.property(fieldName),
					filterFactory.literal(interval.getMin()),
					filterFactory.literal(interval.getMax()));
			filters.add(filter);
			FeatureTypeStyle fts = featureTypeStyle(rule(filter,
					getSymbolsForInterval(interval)));
			style.featureTypeStyles().add(fts);
		}

		addDefaultStyleIfNeeded(style, filters);
		return style;
	}

	protected abstract Symbolizer[] getSymbolsForInterval(Interval interval)
			throws IOException;

	protected Map<Interval, Symbolizer> symbolsMap() throws IOException {
		if (symbolsMap == null) {
			symbolsMap = createSymbols();
		}
		return symbolsMap;
	}

	protected Map<Interval, Symbolizer> createSymbols() throws IOException {
		double min = Double.MAX_VALUE;
		double max = Double.NEGATIVE_INFINITY;
		List<Double> valueList = new ArrayList<Double>();

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

			valueList.add(doubleValue);

			if (doubleValue < min) {
				min = doubleValue;
			} else if (doubleValue > max) {
				max = doubleValue;
			}
		}

		double[] values = new double[valueList.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = valueList.get(i);
		}

		Interval[] intervals = null;
		switch (type) {
		case NATURAL:
			intervals = computeNaturalIntervals(values, min, max, nIntervals);
			break;
		case EQUAL:
			intervals = computeEqualIntervals(min, max, nIntervals);
			break;
		case QUANTILE:
			intervals = computeQuantileIntervals(values, min, max, nIntervals);
			break;
		}

		if (intervals == null) {
			throw new IOException("bug! Unsupported interval type: " + type);
		}

		return getSymbols(intervals);
	}

	protected abstract Map<Interval, Symbolizer> getSymbols(Interval[] intervals);

	private Interval[] computeEqualIntervals(double min, double max,
			int nIntervals) {
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

	private Interval[] computeNaturalIntervals(double[] values, double min,
			double max, int nIntervals) {
		NaturalIntervalGenerator generator = new NaturalIntervalGenerator(min,
				max, values, nIntervals);
		return getIntervalArray(generator.getIntervals());
	}

	private Interval[] computeQuantileIntervals(double[] values, double min,
			double max, int nIntervals) {
		QuantileIntervalGenerator generator = new QuantileIntervalGenerator(
				min, max, values, nIntervals);
		return getIntervalArray(generator.getIntervals());
	}

	private Interval[] getIntervalArray(double[][] intervals) {
		Interval[] ret = new Interval[intervals.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new Interval(intervals[i][0], intervals[i][1]);
		}
		return ret;
	}
}
