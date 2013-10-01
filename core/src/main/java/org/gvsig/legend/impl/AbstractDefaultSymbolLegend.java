package org.gvsig.legend.impl;

import javax.inject.Inject;

import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;

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

	@Override
	protected Symbolizer[] getSymbolsForElseFilter() {
		return useDefault ? new Symbolizer[] { defaultSymbol } : null;
	}
}
