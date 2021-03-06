package org.gvsig.legend;

import java.io.IOException;

import org.geotools.styling.Style;
import org.geotools.styling.TextSymbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;

public interface Legend {
	Style getStyle() throws IOException;

	void setXML(LegendType xml);

	LegendType getXML() throws PersistenceException;

	void setLayer(Layer layer);

	TextSymbolizer getLabeling();

	void setLabeling(TextSymbolizer labeling);
}
