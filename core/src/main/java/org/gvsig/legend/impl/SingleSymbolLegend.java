package org.gvsig.legend.impl;

import java.awt.Color;

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

import com.vividsolutions.jts.geom.Geometry;

public class SingleSymbolLegend implements Legend {
	private Rule defaultRule, selectionRule;
	private StyleFactory styleFactory;
	private FilterFactory2 filterFactory;

	public SingleSymbolLegend(Layer layer, StyleFactory styleFactory,
			FilterFactory2 filterFactory, DefaultSymbols defaultSymbols) {
		this(layer, defaultSymbols.createDefaultSymbol(layer.getShapeType(),
				Color.blue, null), styleFactory, filterFactory, defaultSymbols);
	}

	public SingleSymbolLegend(Layer layer, Symbolizer symbol,
			StyleFactory styleFactory, FilterFactory2 filterFactory,
			DefaultSymbols defaultSymbols) {
		Rule defaultRule = styleFactory.createRule();
		defaultRule.symbolizers().add(symbol);
		defaultRule.setElseFilter(true);

		Class<? extends Geometry> type = layer.getShapeType();
		Rule selectionRule = styleFactory.createRule();
		defaultRule.symbolizers().add(
				defaultSymbols.createDefaultSymbol(type, Color.yellow, null));

		selectionRule.setFilter(filterFactory.id(layer.getSelection()));

		this.defaultRule = defaultRule;
		this.selectionRule = selectionRule;
		this.styleFactory = styleFactory;
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
