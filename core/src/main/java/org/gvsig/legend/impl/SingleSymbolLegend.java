package org.gvsig.legend.impl;

import java.awt.Color;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.opengis.filter.Filter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class SingleSymbolLegend extends AbstractLegend {
	private Symbolizer symbol;

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer,
			DefaultSymbols defaultSymbols) {
		this(layer, null, defaultSymbols);
	}

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer,
			@Assisted Symbolizer symbol, DefaultSymbols defaultSymbols) {
		super(layer);
		if (symbol != null) {
			this.symbol = symbol;
		} else {
			this.symbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), Color.blue, null);
		}
	}

	@Override
	protected Symbolizer[] getSymbols(Object key) {
		return null;
	}

	public Symbolizer getSymbol() {
		return symbol;
	}

	@Override
	protected Object[] getSymbolizerKeys() {
		return new Object[0];
	}

	@Override
	protected Filter getFilter(Object key) {
		return null;
	}

	@Override
	protected Symbolizer[] getSymbolsForElseFilter() {
		return new Symbolizer[] { symbol };
	}
}
