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

import com.google.inject.assistedinject.Assisted;

public abstract class AbstractLegend implements Legend {
	private Rule selectionRule;
	private Map<Rule, Object> ruleValues;

	protected Layer layer;

	@Inject
	protected StyleFactory styleFactory;

	@Inject
	protected FilterFactory2 filterFactory;

	@Inject
	protected DefaultSymbols defaultSymbols;

	@Inject
	public AbstractLegend(@Assisted Layer layer) {
		this.layer = layer;
	}

	@Override
	public Style getStyle() throws IOException {
		Style style = styleFactory.createStyle();

		Filter selectionFilter = filterFactory.id(layer.getSelection());
		ruleValues = new HashMap<Rule, Object>();

		Object[] keys = getSymbolizerKeys();
		// From last to first the implement the painter's model
		for (int i = keys.length - 1; i >= 0; i--) {
			Object value = keys[i];

			Rule rule = addRule(style, doGetFilter(value, selectionFilter),
					false, getSymbols(value));
			ruleValues.put(rule, value);
		}

		Symbolizer selectionSymbol = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.yellow, null);
		selectionRule = addRule(style, selectionFilter, false, selectionSymbol);

		Symbolizer[] symbols = getSymbolsForElseFilter();
		if (symbols != null) {
			addRule(style, null, true, symbols);
		}

		return style;
	}

	private Rule addRule(Style style, Filter filter, boolean elseFilter,
			Symbolizer... symbols) {
		Rule rule = styleFactory.createRule();
		for (Symbolizer symbol : symbols) {
			rule.symbolizers().add(symbol);
		}
		rule.setFilter(filter);
		rule.setElseFilter(elseFilter);

		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle();
		fts.rules().add(rule);
		style.featureTypeStyles().add(fts);

		return rule;
	}

	/**
	 * Gets the symbolizers for the given key.
	 * 
	 * @param key
	 *            The key to obtain the symbolizers. It will always be one of
	 *            the objects returned by {@link #getSymbolizerKeys()}, so it is
	 *            safe to cast it to the type of objects returned by
	 *            {@link #getSymbolizerKeys()}.
	 * @return the symbolizer for the style rule
	 */
	protected abstract Symbolizer[] getSymbols(Object key) throws IOException;

	/**
	 * Gets the array of values to style.
	 * 
	 * @return the array of values to style.
	 */
	protected abstract Object[] getSymbolizerKeys() throws IOException;

	/**
	 * Gets the filter for the given key.
	 * 
	 * @param key
	 *            The key to obtain the filter. It will always be one of the
	 *            objects returned by {@link #getSymbolizerKeys()}, so it is
	 *            safe to cast it to the type of objects returned by
	 *            {@link #getSymbolizerKeys()}.
	 * @return the filter for the style rule
	 */
	protected abstract Filter getFilter(Object key);

	protected abstract Symbolizer[] getSymbolsForElseFilter()
			throws IOException;

	@Override
	public void updateSelection(Selection selection) {
		if (selectionRule != null) {
			Filter selectionFilter = filterFactory.id(selection);
			selectionRule.setFilter(selectionFilter);
			for (Rule rule : ruleValues.keySet()) {
				rule.setFilter(doGetFilter(ruleValues.get(rule),
						selectionFilter));
			}
		}
	}

	private Filter doGetFilter(Object key, Filter selectionFilter) {
		return filterFactory.and(getFilter(key),
				filterFactory.not(selectionFilter));
	}
}
