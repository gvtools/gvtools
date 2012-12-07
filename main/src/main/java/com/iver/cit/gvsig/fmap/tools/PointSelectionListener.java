/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
package com.iver.cit.gvsig.fmap.tools;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.process.ProcessException;
import org.geotools.process.feature.gs.QueryProcess;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.layer.filter.AndLayerFilter;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.util.AffineTransformCoordinateSequenceFilter;
import org.gvsig.util.EnvelopeUtils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PointListener;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * <p>
 * Listener that selects all features of the active, and vector layers of the
 * associated <code>MapControl</code> that their area intersects with the point
 * selected by a single click of any button of the mouse.
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class PointSelectionListener implements PointListener {
	/**
	 * The image to display when the cursor is active.
	 */
	private final Image img = new ImageIcon(
			MapControl.class.getResource("/images/PointSelectCursor.gif"))
			.getImage();

	/**
	 * The cursor used to work with this tool listener.
	 * 
	 * @see #getCursor()
	 */
	private Cursor cur = Toolkit.getDefaultToolkit().createCustomCursor(img,
			new Point(16, 16), "");

	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	protected MapControl mapCtrl;

	/**
	 * <p>
	 * Creates a new <code>PointSelectionListener</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public PointSelectionListener(MapControl mc) {
		this.mapCtrl = mc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.
	 * cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point(PointEvent event) throws BehaviorException {
		// mapCtrl.getMapContext().selectByPoint(event.getPoint(), 1);
		Point2D p = event.getPoint();
		int tolerance = 3;
		Rectangle2D.Double recPoint = new Rectangle2D.Double(p.getX()
				- (tolerance / 2), p.getY() - (tolerance / 2), tolerance,
				tolerance);

		Geometry geometry = new GeometryFactory().toGeometry(EnvelopeUtils
				.toEnvelope(recPoint));
		try {
			geometry.apply(new AffineTransformCoordinateSequenceFilter(mapCtrl
					.getViewPort().getAffineTransform().createInverse()));
		} catch (NoninvertibleTransformException e) {
			throw new BehaviorException("Cannot prepare the query", e);
		}

		Layer[] actives = mapCtrl
				.getMapContext()
				.getRootLayer()
				.filter(new AndLayerFilter(LayerFilter.FEATURE,
						LayerFilter.SELECTED));
		for (int i = 0; i < actives.length; i++) {
			try {
				Selection oldSelection = actives[i].getSelection();
				QueryProcess qp = new QueryProcess();
				FilterFactory2 ff = CommonFactoryFinder
						.getFilterFactory2(GeoTools.getDefaultHints());
				SimpleFeatureSource featureSource = actives[i]
						.getFeatureSource();
				Filter filter = ff.intersects(
						ff.property(featureSource.getSchema()
								.getGeometryDescriptor().getName()),
						ff.literal(geometry));
				SimpleFeatureCollection result = qp.execute(
						featureSource.getFeatures(), null, filter);
				SimpleFeatureIterator iterator = result.features();
				Selection newSelection = new Selection();
				while (iterator.hasNext()) {
					SimpleFeature feature = iterator.next();
					newSelection.add(feature.getIdentifier());
				}
				iterator.close();

				// if (event.getEvent().isControlDown()) {
				// newSelection.xor(oldSelection);
				// }
				actives[i].setSelection(newSelection);
			} catch (ProcessException e) {
				throw new BehaviorException("Cannot filter layer", e);
			} catch (IOException e) {
				throw new BehaviorException("Read error", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#getCursor()
	 */
	public Cursor getCursor() {
		return cur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#cancelDrawing()
	 */
	public boolean cancelDrawing() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#pointDoubleClick
	 * (com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void pointDoubleClick(PointEvent event) throws BehaviorException {

	}
}
