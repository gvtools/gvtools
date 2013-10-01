package org.gvsig.legend.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class SizeIntervalLegend extends AbstractIntervalLegend {
	private Interval size;
	private Symbolizer template, background;
	private boolean useBackground;

	@AssistedInject
	public SizeIntervalLegend(@Assisted Interval size,
			@Assisted Type intervalType, @Assisted Symbolizer defaultSymbol,
			@Assisted("usedefault") boolean useDefault, @Assisted Layer layer,
			@Assisted String fieldName, @Assisted int nIntervals,
			@Assisted("template") Symbolizer template,
			@Assisted("background") Symbolizer background,
			@Assisted("usebackground") boolean useBackground) {
		super(intervalType, defaultSymbol, useDefault, layer, fieldName,
				nIntervals);
		this.size = size;
		this.template = template;
		this.background = background;
		this.useBackground = useBackground;
	}

	@AssistedInject
	public SizeIntervalLegend(@Assisted Map<Interval, Symbolizer> symbolsMap,
			@Assisted Symbolizer defaultSymbol,
			@Assisted("usedefault") boolean useDefault, @Assisted Layer layer,
			@Assisted String fieldName,
			@Assisted("background") Symbolizer background,
			@Assisted("usebackground") boolean useBackground) {
		super(symbolsMap, defaultSymbol, useDefault, layer, fieldName);
		this.size = new Interval(1, 7);
		this.background = background;
		this.useBackground = useBackground;
	}

	public Interval getSize() {
		return size;
	}

	public Symbolizer getBackground() {
		return background;
	}

	@Override
	protected Symbolizer[] getSymbolsForInterval(Interval interval)
			throws IOException {
		Symbolizer symbol = symbolsMap().get(interval);
		if (useBackground) {
			return new Symbolizer[] { background, symbol };
		} else {
			return new Symbolizer[] { symbol };
		}
	}

	@Override
	protected Map<Interval, Symbolizer> getSymbols(Interval[] intervals) {
		double range = size.getMax() - size.getMin();
		double step = range / nIntervals;

		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();

		double symbolSize = size.getMin();
		for (Interval interval : intervals) {
			ResizeCopyStyleVisitor visitor = new ResizeCopyStyleVisitor(
					filterFactory.literal(symbolSize), styleFactory,
					filterFactory);
			template.accept(visitor);
			Symbolizer copy = (Symbolizer) visitor.getCopy();
			copy.setDescription(new DescriptionImpl(interval.toString(),
					interval.toString()));
			symbolsMap.put(interval, copy);
			symbolSize += step;
		}

		return symbolsMap;
	}
}
