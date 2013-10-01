package org.gvsig.legend;

import java.io.IOException;

import org.geotools.styling.Style;
import org.gvsig.layer.Selection;

public interface Legend {
	Style getStyle() throws IOException;

	void updateSelection(Selection selection);
}
