package org.gvsig.legend;

import org.geotools.styling.Style;
import org.gvsig.layer.Selection;

public interface Legend {
	Style getStyle();

	void updateSelection(Selection selection);
}
