package org.gvsig.legend.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class QuantityByCategoryLegend extends AbstractLegend {
	private IntervalLegend colorLegend;
	private SizeIntervalLegend sizeLegend;

	@AssistedInject
	public QuantityByCategoryLegend(@Assisted Layer layer,
			@Assisted IntervalLegend colorLegend,
			@Assisted SizeIntervalLegend sizeLegend) {
		super(layer);
		this.colorLegend = colorLegend;
		this.sizeLegend = sizeLegend;
	}

	public IntervalLegend getColorIntervalLegend() {
		return colorLegend;
	}

	public SizeIntervalLegend getSizeIntervalLegend() {
		return sizeLegend;
	}

	public Symbolizer[] getSymbols() throws IOException {
		List<Symbolizer> list = new ArrayList<Symbolizer>();
		Collections.addAll(list, colorLegend.getSymbols());
		Collections.addAll(list, sizeLegend.getSymbols());
		return list.toArray(new Symbolizer[list.size()]);
	}

	public Interval[] getIntervals() throws IOException {
		List<Interval> list = new ArrayList<Interval>();
		Collections.addAll(list, colorLegend.getIntervals());
		Collections.addAll(list, sizeLegend.getIntervals());
		return list.toArray(new Interval[list.size()]);
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = styleFactory.createStyle();
		List<FeatureTypeStyle> styles = style.featureTypeStyles();
		styles.addAll(sizeLegend.createStyle().featureTypeStyles());
		styles.addAll(colorLegend.createStyle().featureTypeStyles());
		return style;
	}
}
