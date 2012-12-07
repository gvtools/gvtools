package org.gvsig.layer;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;
import org.gvsig.persistence.generated.LayerType;
import org.opengis.filter.identity.FeatureId;

public interface Layer {

	/**
	 * If the layer is a single layer returns true if the <code>layer</code>
	 * parameter is this.
	 * 
	 * If the layer is a collection, returns true in case the tree contains the
	 * specified instance.
	 * 
	 * False otherwise
	 * 
	 * @param layer
	 * @return
	 */
	boolean contains(Layer layer);

	/**
	 * Returns this layers along with all the descendant layers, if any, as an
	 * array
	 * 
	 * @return
	 */
	Layer[] getAllLayersInTree();

	/**
	 * Returns the children of this layer. Empty array if this layer is not a
	 * collection and has no children
	 * 
	 * @return
	 */
	Layer[] getChildren();

	/**
	 * Obtains an array of all layers that all match the filter condition. This
	 * layer and all the descendant are tested
	 * 
	 * @param filter
	 * @return
	 */
	Layer[] filter(LayerFilter filter);

	/**
	 * Determines if the layer is being edited. A layer is being edited if there
	 * are any editing tasks being performed. In any other case (including
	 * composite layers), this method returns <code>false</code>.
	 * 
	 * @return <code>true</code> if the layer is being edited,
	 *         <code>false</code> otherwise.
	 */
	boolean isEditing();

	/**
	 * Determines if this layer has features
	 * 
	 * @return <code>true</code> if the layer has features, <code>false</code>
	 *         otherwise (including composite layers).
	 */
	boolean hasFeatures();

	/**
	 * Determines if the layer is active. A composite layer is never active.
	 * 
	 * @return <code>true</code> if the layer is active, <code>false</code>
	 *         otherwise.
	 */
	boolean isActive();

	/**
	 * Sets the visibility property on the layer. If the layer visibility is set
	 * to false,
	 * {@link MapContext#draw(java.awt.image.BufferedImage, java.awt.Graphics2D, java.awt.geom.Rectangle2D, org.gvsig.util.ProcessContext)}
	 * will ignore the layer
	 * 
	 * @param visible
	 */
	void setVisible(boolean visible);

	/**
	 * Sets the <code>selected</code> property. For general use by clients.
	 * 
	 * @param selected
	 */
	void setSelected(boolean selected);

	/**
	 * Gets the <code>selected</code> property.
	 * 
	 * @return
	 */
	boolean isSelected();

	/**
	 * Adds a layer as a child of this one.
	 * 
	 * @param testLayer
	 * @throws UnsupportedOperationException
	 *             If this layer is not a collection
	 */
	void addLayer(Layer testLayer) throws UnsupportedOperationException;

	boolean removeLayer(Layer layer);

	/**
	 * Returns a collection of GeoTools layers that will be used to draw.
	 * 
	 * @return
	 * @throws IOException
	 *             If the geotools layer cannot be built
	 */
	Collection<org.geotools.map.Layer> getDrawingLayers() throws IOException;

	/**
	 * Get the bounds of this layer. Null if this layer contains no data (e.g.:
	 * it is an empty collection)
	 * 
	 * @return
	 * @throws IOException
	 */
	ReferencedEnvelope getBounds() throws IOException;

	LayerType getXML();

	/**
	 * Gets the ids of the selected features.
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 *             If this is not a feature layer
	 */
	Set<FeatureId> getSelection() throws UnsupportedOperationException;

	/**
	 * Sets the ids of the selected features
	 * 
	 * @param newSelection
	 * @throws UnsupportedOperationException
	 *             If this is not a feature layer
	 */
	void setSelection(Set<FeatureId> newSelection)
			throws UnsupportedOperationException;

	/**
	 * Gets this layer's data
	 * 
	 * @return
	 * @throws UnsupportedOperationException
	 *             If this is not a feature layer
	 * @throws IOException
	 *             If data access errors occur
	 */
	SimpleFeatureSource getFeatureSource()
			throws UnsupportedOperationException, IOException;

}
