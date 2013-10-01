package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Interval;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.iver.cit.gvsig.fmap.rendering.NaturalIntervalGenerator;
import com.iver.cit.gvsig.fmap.rendering.QuantileIntervalGenerator;

public class IntervalLegend extends AbstractDefaultSymbolLegend {
	private static final Logger logger = Logger.getLogger(IntervalLegend.class);

	public static enum Type {
		EQUAL, NATURAL, QUANTILE
	}

	private Color start, end;
	private Type type;
	private Map<Interval, Symbolizer> symbolsMap;
	private Interval[] orderedIntervals;
	private Symbolizer[] orderedSymbols;
	private int nIntervals;
	private String fieldName;

	@Inject
	private FilterFactory2 filterFactory;

	@AssistedInject
	public IntervalLegend(@Assisted("start") Color start,
			@Assisted("end") Color end, @Assisted Type intervalType,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Layer layer, @Assisted String fieldName,
			@Assisted int nIntervals, DefaultSymbols defaultSymbols) {
		super(layer, defaultSymbol, useDefault);
		this.start = start;
		this.end = end;
		this.type = intervalType;
		this.fieldName = fieldName;
		this.nIntervals = nIntervals;
	}

	@AssistedInject
	public IntervalLegend(@Assisted Map<Interval, Symbolizer> symbolsMap,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Layer layer, @Assisted String fieldName) {
		super(layer, defaultSymbol, useDefault);
		this.type = null;
		this.fieldName = fieldName;
		this.symbolsMap = symbolsMap;

		Interval[] intervals = getOrderedIntervals(symbolsMap);
		this.nIntervals = intervals.length;
		if (intervals.length > 0) {
			this.start = getColor(symbolsMap.get(intervals[0]));
			this.end = getColor(symbolsMap.get(intervals[intervals.length - 1]));
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

	public Interval[] getIntervals() throws IOException {
		return getOrderedIntervals(symbols());
	}

	private Interval[] getOrderedIntervals(Map<Interval, Symbolizer> symbolsMap) {
		if (orderedIntervals == null) {
			List<Interval> list = new ArrayList<Interval>();
			list.addAll(symbolsMap.keySet());
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

			orderedIntervals = list.toArray(new Interval[list.size()]);
		}
		return orderedIntervals;
	}

	public Symbolizer[] getSymbols() throws IOException {
		return getOrderedSymbols(symbols());
	}

	private Symbolizer[] getOrderedSymbols(Map<Interval, Symbolizer> symbolsMap) {
		if (orderedSymbols == null) {
			Interval[] intervals = getOrderedIntervals(symbolsMap);
			orderedSymbols = new Symbolizer[symbolsMap.size()];
			for (int i = 0; i < orderedSymbols.length; i++) {
				orderedSymbols[i] = symbolsMap.get(intervals[i]);
			}
			return orderedSymbols;
		}
		return orderedSymbols;
	}

	@Override
	protected Object[] getSymbolizerKeys() throws IOException {
		return getIntervals();
	}

	@Override
	protected Symbolizer[] getSymbols(Object value) throws IOException {
		return new Symbolizer[] { symbols().get(value) };
	}

	public Color getStartColor() {
		return start;
	}

	public Color getEndColor() {
		return end;
	}

	public Type getType() {
		return type;
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	protected Filter getFilter(Object key) {
		Interval interval = (Interval) key;
		return filterFactory.between(filterFactory.property(fieldName),
				filterFactory.literal(interval.getMin()),
				filterFactory.literal(interval.getMax()));
	}

	private Map<Interval, Symbolizer> symbols() throws IOException {
		if (symbolsMap == null) {
			symbolsMap = createSymbols();
		}

		return symbolsMap;
	}

	private Map<Interval, Symbolizer> createSymbols() throws IOException {
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

		double r = start.getRed();
		double g = start.getGreen();
		double b = start.getBlue();
		double stepR = (end.getRed() - r) / (nIntervals - 1.0);
		double stepG = (end.getGreen() - g) / (nIntervals - 1.0);
		double stepB = (end.getBlue() - b) / (nIntervals - 1.0);

		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();

		for (Interval interval : intervals) {
			int red = Math.max(0, Math.min(255, (int) r));
			int green = Math.max(0, Math.min(255, (int) g));
			int blue = Math.max(0, Math.min(255, (int) b));
			Symbolizer symbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), new Color(red, green, blue),
					interval.toString());

			r += stepR;
			g += stepG;
			b += stepB;

			symbolsMap.put(interval, symbol);
		}

		return symbolsMap;
	}

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
