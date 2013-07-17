package org.gvsig.layer;

import java.io.IOException;
import java.util.Collection;

import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.styling.Style;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;
import org.gvsig.persistence.generated.LayerType;

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
	 * Processes all the layers in this tree using the specified
	 * <code>processor</code>
	 * 
	 * @param processor
	 * @return
	 */
	void process(LayerProcessor processor);

	/**
	 * Processes all the layers in this tree that are filtered by
	 * <code>layerFilter</code> using the specified <code>processor</code>
	 * 
	 * @param layerFilter
	 * @param processor
	 * @return
	 */
	void process(LayerFilter layerFilter, LayerProcessor processor);

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
	 * Gets the visibility property
	 * 
	 * @return
	 */
	boolean isVisible();

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
	 * @param layer
	 * @throws UnsupportedOperationException
	 *             If this layer is not a collection
	 * @throws IllegalArgumentException
	 *             If <code>layer</code> is null or is already added to another
	 *             parent ( {@link #getParent()} is not null)
	 */
	void addLayer(Layer layer) throws UnsupportedOperationException,
			IllegalArgumentException;

	/**
	 * The same as {@link #addLayer(Layer)} but specifying the position where
	 * the layer will be added. The current layer at that position and all the
	 * following ones will be shifted one place.
	 * 
	 * @param position
	 * @param layer
	 * @throws UnsupportedOperationException
	 *             if {@link #addLayer(Layer)} raises the exception
	 * @throws IllegalArgumentException
	 *             if the position does not exist or {@link #addLayer(Layer)}
	 *             raises the exception
	 */
	void addLayer(int position, Layer layer)
			throws UnsupportedOperationException, IllegalArgumentException;

	/**
	 * Convenience method that returns the index of the layer in the array
	 * returned by {@link #getChildren()} or -1 if the layers is not a children
	 * of this layer
	 * 
	 * @param layer
	 * @return
	 */
	int indexOf(Layer layer);

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
	Selection getSelection() throws UnsupportedOperationException;

	/**
	 * Sets the ids of the selected features
	 * 
	 * @param newSelection
	 * @throws UnsupportedOperationException
	 *             If this is not a feature layer
	 */
	void setSelection(Selection newSelection)
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

	String getName();

	void setName(String name);

	Layer getParent();

	/**
	 * Sets the parent of the layer. Should not be called directly
	 * 
	 * @param parent
	 */
	void setParent(Layer parent);

	void setStyle(Style style);

	Style getStyle();
}
