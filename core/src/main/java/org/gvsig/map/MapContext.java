package org.gvsig.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.gvsig.layer.Layer;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.MapType;
import org.gvsig.units.Unit;
import org.gvsig.util.ProcessContext;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public interface MapContext {
	/**
	 * Get the root of the layer tree
	 * 
	 * @return
	 */
	Layer getRootLayer();

	/**
	 * Gets this map {@link CoordinateReferenceSystem}
	 * 
	 * @return
	 */
	CoordinateReferenceSystem getCRS();

	/**
	 * Set this map {@link CoordinateReferenceSystem}
	 * 
	 * @param crs
	 */
	void setCRS(CoordinateReferenceSystem crs);

	void setBackgroundColor(Color c);

	Color getBackgroundColor();

	MapType getXML();

	void setXML(MapType mainMap) throws PersistenceException;

	void setDistanceUnits(Unit unit);

	void setAreaUnits(Unit unit);

	void setMapUnits(Unit unit);

	Unit getMapUnits();

	Unit getDistanceUnits();

	Unit getAreaUnits();

	/**
	 * Returns the <code>extent</code> passed as a parameter in the last call to
	 * {@link #draw(BufferedImage, Graphics2D, Rectangle2D, ProcessContext)}
	 * 
	 * @return
	 */
	Rectangle2D getLastDrawnArea();

	/**
	 * @param image
	 * @param g
	 * @param extent
	 * @param scaleDenominator
	 * @param processContext
	 * @throws IOException
	 *             If some error makes the drawing fail completely
	 */
	void draw(BufferedImage image, Graphics2D g, Rectangle2D extent,
			ProcessContext processContext) throws IOException;
}
