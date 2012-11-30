package com.iver.cit.gvsig.project.documents.view;

import java.beans.PropertyChangeListener;

import org.gvsig.map.MapContext;

import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.gui.WindowData;

public interface IProjectView {

	/**
	 * Gets the FMap's contexts of the main map in the view.
	 * 
	 * @return
	 */
	public MapContext getMapContext();

	/**
	 * Gets the FMap's context from the locator, which is the small map in the
	 * left-bottom corner of the View.
	 * 
	 * @return
	 */
	public MapContext getMapOverViewContext();

	/**
	 * @see com.iver.cit.gvsig.project.documents.view.ProjectView#setMapContext(com.iver.cit.gvsig.fmap.MapContext)
	 */
	public void setMapContext(MapContext fmap);

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fmap
	 *            DOCUMENT ME!
	 */
	public void setMapOverViewContext(MapContext fmap);

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fmap
	 *            DOCUMENT ME!
	 */
	public String getName();

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fmap
	 *            DOCUMENT ME!
	 */
	public Project getProject();

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fmap
	 *            DOCUMENT ME!
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fmap
	 *            DOCUMENT ME!
	 */
	public String getExtLink();

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getTypeLink();

	/**
	 * Se selecciona la extensi�n para realizar cuando se quiera el link.
	 * 
	 * @param s
	 *            nombre del campo.
	 */
	public void setExtLink(String s);

	/**
	 * Se selecciona el tipo de fichero para realizar cuando se quiera el link.
	 * 
	 * @param i
	 *            tipo de fichero.
	 */
	public void setTypeLink(int i);

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public void setSelectedField(String s);

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getSelectedField();

	/**
	 * Store the properties of the window associated with this IProjectView.
	 */
	public void storeWindowData(WindowData data);

}