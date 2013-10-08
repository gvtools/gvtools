package org.gvsig.legend.impl;

import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.opengis.filter.Filter;

public abstract class AbstractDefaultSymbolLegend extends AbstractLegend {
	private boolean useDefault;
	private Symbolizer defaultSymbol;

	protected void initialize(Layer layer, Symbolizer defaultSymbol,
			boolean useDefault) {
		super.setLayer(layer);
		this.defaultSymbol = defaultSymbol;
		this.useDefault = useDefault;
	}

	public boolean useDefaultSymbol() {
		return useDefault;
	}

	public Symbolizer getDefaultSymbol() {
		return defaultSymbol;
	}

	protected void addDefaultStyleIfNeeded(Style style, List<Filter> filters) {
		if (useDefault) {
			Filter filter = filterFactory.not(filterFactory.or(filters));
			FeatureTypeStyle fts = featureTypeStyle(rule(filter, defaultSymbol));
			style.featureTypeStyles().add(fts);
		}
	}
}
