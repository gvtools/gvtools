package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

public abstract class AbstractLegend implements Legend {
	private Rule selectionRule;
	private Filter selectionFilter;
	private Map<Rule, Filter> ruleFilters;
	private Style style;

	private Layer layer;

	@Inject
	protected StyleFactory styleFactory;
	@Inject
	protected FilterFactory2 filterFactory;
	@Inject
	protected DefaultSymbols defaultSymbols;

	protected void setLayer(Layer layer) {
		this.layer = layer;
	}

	protected Layer getLayer() {
		return layer;
	}

	@Override
	public final Style getStyle() throws IOException {
		if (layer == null) {
			throw new IllegalStateException("bug! You must initialize "
					+ "the legend first!");
		}

		if (style == null) {
			selectionFilter = filterFactory.id(layer.getSelection());
			ruleFilters = new HashMap<Rule, Filter>();

			style = createStyle();
			for (FeatureTypeStyle featureTypeStyle : style.featureTypeStyles()) {
				for (Rule rule : featureTypeStyle.rules()) {
					ruleFilters.put(rule, rule.getFilter());
				}
			}

			Symbolizer selectionSymbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), Color.yellow, null);
			selectionRule = rule(selectionFilter, selectionSymbol);
			style.featureTypeStyles().add(featureTypeStyle(selectionRule));
		}
		return style;
	}

	protected abstract Style createStyle() throws IOException;

	protected FeatureTypeStyle featureTypeStyle(Rule... rules) {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle();
		for (Rule rule : rules) {
			fts.rules().add(rule);
		}
		return fts;
	}

	protected Rule rule(Symbolizer... symbols) {
		Rule rule = createRule(symbols);
		rule.setElseFilter(true);
		return rule;
	}

	protected Rule rule(Filter filter, Symbolizer... symbols) {
		Rule rule = createRule(symbols);
		rule.setElseFilter(false);
		rule.setFilter(filter);
		return rule;
	}

	private Rule createRule(Symbolizer... symbols) {
		Rule rule = styleFactory.createRule();
		for (Symbolizer symbol : symbols) {
			rule.symbolizers().add(symbol);
		}
		return rule;
	}

	@Override
	public void updateSelection(Selection selection) {
		if (selectionRule != null) {
			selectionFilter = filterFactory.id(selection);

			selectionRule.setFilter(selectionFilter);
			for (Rule rule : ruleFilters.keySet()) {
				Filter filter = filterFactory.and(ruleFilters.get(rule),
						filterFactory.not(selectionFilter));
				rule.setFilter(filter);
			}
		}
	}
}
