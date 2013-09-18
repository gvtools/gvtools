package org.gvsig.legend.impl;

import java.awt.Color;

import javax.inject.Inject;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Legend;
import org.opengis.filter.FilterFactory2;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.vividsolutions.jts.geom.Geometry;

public class SingleSymbolLegend implements Legend {
	private Rule defaultRule, selectionRule;

	@Inject
	private StyleFactory styleFactory;

	@Inject
	private FilterFactory2 filterFactory;

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer, StyleFactory styleFactory,
			FilterFactory2 filterFactory, DefaultSymbols defaultSymbols) {
		this(layer, null, styleFactory, filterFactory, defaultSymbols);
	}

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer,
			@Assisted Symbolizer symbol, StyleFactory styleFactory,
			FilterFactory2 filterFactory, DefaultSymbols defaultSymbols) {
		Class<? extends Geometry> type = layer.getShapeType();
		if (symbol == null) {
			symbol = defaultSymbols.createDefaultSymbol(type, Color.blue, null);
		}

		defaultRule = styleFactory.createRule();
		defaultRule.symbolizers().add(symbol);
		defaultRule.setElseFilter(true);

		selectionRule = styleFactory.createRule();
		selectionRule.symbolizers().add(
				defaultSymbols.createDefaultSymbol(type, Color.yellow, null));

		selectionRule.setFilter(filterFactory.id(layer.getSelection()));
	}

	@Override
	public Style getStyle() {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] {
				defaultRule, selectionRule });
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}

	public Rule getSelectionRule() {
		return selectionRule;
	}

	@Override
	public void updateSelection(Selection selection) {
		selectionRule.setFilter(filterFactory.id(selection));
	}
}
