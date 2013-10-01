package org.gvsig.legend.impl;

import java.util.List;

import javax.inject.Inject;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.opengis.filter.Filter;

import com.google.inject.assistedinject.Assisted;

public abstract class AbstractDefaultSymbolLegend extends AbstractLegend {
	private boolean useDefault;
	private Symbolizer defaultSymbol;

	@Inject
	public AbstractDefaultSymbolLegend(@Assisted Layer layer,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault) {
		super(layer);
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
