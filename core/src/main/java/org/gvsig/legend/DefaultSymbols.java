package org.gvsig.legend;

import java.awt.Color;

import org.geotools.styling.Symbolizer;

import com.vividsolutions.jts.geom.Geometry;

public interface DefaultSymbols {
	Symbolizer createDefaultSymbol(Class<? extends Geometry> type, Color color,
			String description);

	Symbolizer createDefaultSymbol(Class<? extends Geometry> type, Color color,
			int size, String description);
}
