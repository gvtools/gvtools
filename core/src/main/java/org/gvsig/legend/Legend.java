package org.gvsig.legend;

import java.io.IOException;

import org.geotools.styling.Style;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;

public interface Legend {
	Style getStyle() throws IOException;

	void updateSelection(Selection selection);

	void setXML(LegendType xml);

	LegendType getXML() throws PersistenceException;

	void setLayer(Layer layer);
}
