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
package com.iver.cit.gvsig.project;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.XMLException;
import org.geotools.referencing.CRS;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Source;
import org.gvsig.layer.FeatureSourceCache;
import org.gvsig.persistence.generated.DataSourceType;
import org.gvsig.persistence.generated.DocumentType;
import org.gvsig.persistence.generated.LabeledExtentType;
import org.gvsig.persistence.generated.StringPropertyType;
import org.gvsig.tools.file.PathGenerator;
import org.gvsig.units.Unit;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.ProjectExtension;
import com.iver.cit.gvsig.Version;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.ProjectDocumentFactory;
import com.iver.cit.gvsig.project.documents.exceptions.OpenException;
import com.iver.cit.gvsig.project.documents.exceptions.SaveException;
import com.iver.cit.gvsig.project.documents.gui.ProjectWindow;
import com.iver.cit.gvsig.project.documents.gui.WindowData;
import com.iver.cit.gvsig.project.documents.view.ProjectView;
import com.iver.cit.gvsig.project.documents.view.ProjectViewFactory;
import com.iver.utiles.PostProcessSupport;
import com.iver.utiles.StringUtilities;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * Clase que representa un proyecto de openSIG
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class Project implements Serializable, PropertyChangeListener {
	public static String VERSION = Version.format();

	private static final Logger logger = Logger.getLogger(Project.class);

	@Inject
	private FeatureSourceCache sourceCache;

	static private CoordinateReferenceSystem defaultCrs = null;

	static private CoordinateReferenceSystem defaultFactoryCrs;
	static {
		try {
			defaultFactoryCrs = CRS.decode("EPSG:23030");
		} catch (NoSuchAuthorityCodeException e) {
			throw new RuntimeException("Bug", e);
		} catch (FactoryException e) {
			throw new RuntimeException("Bug", e);
		}
	}
	/*
	 * distiguishing between a static field "defaultSelectionColor" and a
	 * selectionColor field will allow to define default color in a multiple
	 * project scenario
	 */
	static private Color defaultSelectionColor = Color.YELLOW;

	private static Unit defaultMapUnits = null;

	private static Unit defaultDistanceUnits = null;

	private static Unit defaultDistanceArea = null;

	private PropertyChangeSupport change;

	private boolean modified = false;

	private String name = PluginServices.getText(this, "untitled");

	// private String path;
	private String creationDate = new Date().toGMTString();

	private String modificationDate = new Date().toGMTString();

	private String owner = "";

	private String comments = "";

	private Color selectionColor = null;

	private boolean isAbsolutePath = true;

	// private ArrayList views = new ArrayList();
	// private ArrayList tables = new ArrayList();
	// private ArrayList maps = new ArrayList();
	private ArrayList<ProjectDocument> documents = new ArrayList<ProjectDocument>();

	private ArrayList<ProjectExtent> extents = new ArrayList<ProjectExtent>();

	// Lista de objetos del tipo camera. Necesarios para almacenar la posicion
	// del usuario haciendo uso de los marcadores
	private List<Object> cameras = new ArrayList<Object>();

	/**
	 * this is a runtime-calculated value, do NOT persist it!
	 */
	private CoordinateReferenceSystem crs;

	/**
	 * Stores the initial properties of the windows, to be restored just after
	 * the project is loaded. It's an ordered iterator of XMLEntity objects,
	 * each containing a XML version of a WindowInfo object.
	 */
	private Iterator<XMLEntity> initialWindowProperties = null;

	/**
	 * Creates a new Project object.
	 * 
	 */
	public Project() {
		change = new PropertyChangeSupport(this);

		// change.addPropertyChangeListener(this);
		creationDate = DateFormat.getDateInstance().format(new Date());
		modificationDate = creationDate;
		setSelectionColor(getDefaultSelectionColor());
		getDefaultCrs(); // For initialize it
		// signatureAtStartup = computeSignature();
	}

	/**
	 * Obtiene la fecha de creaci�n del proyecto
	 * 
	 * @return
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * Obtiene el nombre del proyecto
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	// /**
	// * Obtiene la ruta completa del fichero donde se guardo por �ltima vez
	// el
	// * proyecto
	// *
	// * @return
	// */
	// public String getPath() {
	// return path;
	// }

	/**
	 * Asigna la fecha de creaci�n del proyecto. Este m�todo tiene sentido
	 * s�lo por que al recuperar la fecha del XML hay que asignarla al objeto
	 * proyecto de alguna manera. La fecha se asigna en el constructor y no se
	 * deber�a de modificar nunca
	 * 
	 * @param string
	 */
	public void setCreationDate(String string) {
		creationDate = string;
		// modified = true;
		change.firePropertyChange("", null, null);
	}

	/**
	 * Establece el nombre del proyecto
	 * 
	 * @param string
	 */
	public void setName(String string) {
		name = string;
		// modified = true;
		change.firePropertyChange("", null, null);
	}

	/**
	 * Obtiene los comentarios
	 * 
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Obtiene la fecha de la �ltima modificaci�n
	 * 
	 * @return
	 */
	public String getModificationDate() {
		return modificationDate;
	}

	/**
	 * Obtiene el propietario del proyecto
	 * 
	 * @return
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Establece una cadena como comentarios al proyecto
	 * 
	 * @param string
	 */
	public void setComments(String string) {
		comments = string;
		// modified = true;
		change.firePropertyChange("", null, null);
	}

	/**
	 * Establece la fecha de la �ltima modificaci�n
	 * 
	 * @param string
	 */
	public void setModificationDate(String string) {
		modificationDate = string;
		// modified = true;
		change.firePropertyChange("", null, null);
	}

	/**
	 * Establece el propietario del proyecto
	 * 
	 * @param string
	 */
	public void setOwner(String string) {
		owner = string;
		// modified = true;
		change.firePropertyChange("", null, null);
	}

	/**
	 * Establece el flag de modificado del proyecto
	 * 
	 * @param b
	 */
	public void setModified(boolean b) {
		modified = b;
		if (modified == false) {
			for (ProjectDocument doc : getDocuments()) {
				doc.setModified(false);
			}
		}
	}

	/**
	 * Obtiene el color de selecci�n que se usar� en el proyecto
	 * 
	 * @return
	 */
	public Color getSelectionColor() {
		if (selectionColor == null) {
			selectionColor = defaultSelectionColor;
		}
		return selectionColor;
	}

	/**
	 * Establece el color de selecci�n
	 * 
	 * @param color
	 */
	public void setSelectionColor(Color color) {
		selectionColor = color;
		assert false : "Set the selection color to the MapContext";
		// modified = true;
		change.firePropertyChange("selectionColor", null, color);
	}

	/**
	 * Obtiene el color como un entero para su serializaci�n a XML
	 * 
	 * @return
	 */
	public String getColor() {
		return StringUtilities.color2String(selectionColor);
	}

	/**
	 * M�todo invocado al recuperar de XML para establecer el color de
	 * seleccion del proyecto
	 * 
	 * @param color
	 *            Entero que representa un color
	 */
	public void setColor(String color) {
		// modified = true;
		selectionColor = StringUtilities.string2Color(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// this.modified = true;
		change.firePropertyChange(evt);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg1
	 */
	public void addExtent(ProjectExtent arg1) {
		extents.add(arg1);
		// modified = true;
		change.firePropertyChange("addExtent", null, null);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg0
	 * 
	 * @return
	 */
	public Object removeExtent(int arg0) {
		// modified = true;
		change.firePropertyChange("delExtent", null, null);

		return extents.remove(arg0);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public ProjectExtent[] getExtents() {
		return extents.toArray(new ProjectExtent[0]);
	}

	/**
	 * Metodo que a�ade una nueva camera a la lista de cameras
	 * 
	 * @param arg1
	 *            camera introducida
	 */
	public void addCamera(Object arg1) {
		this.cameras.add(arg1);
		// modified = true;
		change.firePropertyChange("addCamera", null, null);
	}

	/**
	 * Metodo que borra de la lisat un elemento seleccionado
	 * 
	 * @param arg0
	 *            indice del elemento que se va a borrar
	 * 
	 * @return resultado de la operacion de borrado
	 */
	public Object removeCamera(int arg0) {
		// modified = true;
		change.firePropertyChange("delCamera", null, null);

		return this.cameras.remove(arg0);
	}

	/**
	 * Metodo que devuelve la lista de cameras
	 * 
	 * @return lista de objetos de tipo camera
	 */
	public Object[] getCameras() {
		return this.cameras.toArray();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param arg0
	 */
	public synchronized void addPropertyChangeListener(
			PropertyChangeListener arg0) {
		change.addPropertyChangeListener(arg0);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws DriverException
	 * @throws XMLException
	 */
	public org.gvsig.persistence.generated.Project getXMLEntity() {
		org.gvsig.persistence.generated.Project ret = new org.gvsig.persistence.generated.Project();
		ret.setVersion(VERSION);
		ret.setName(name);
		ret.setComments(getComments());
		ret.setOwner(owner);
		ret.setCreationDate(creationDate);
		ret.setModificationDate(modificationDate);
		ret.setAbsolutePath(isAbsolutePath);
		ret.setSelectionColor(StringUtilities.color2String(selectionColor));
		ret.setDefaultCrs(CRS.toSRS(crs));

		for (int i = 0; i < extents.size(); i++) {
			ret.getExtents().add(extents.get(i).getXMLEntity());
		}

		for (int i = 0; i < documents.size(); i++) {
			DocumentType xmlchild = documents.get(i).getXMLEntity();
			ret.getDocuments().add(xmlchild);
		}

		return ret;
	}

	/**
	 * Restores the size, position and order of the windows, according to
	 * variable initialWindowProperties. If this variable is null, the method
	 * just opens the project manager window.
	 * 
	 */
	public void restoreWindowProperties() {
		boolean projectWindowRestored = false;
		XMLEntity child;

		Iterator<XMLEntity> propertiesIterator = this.initialWindowProperties;
		if (propertiesIterator != null) {
			this.initialWindowProperties = null;

			while (propertiesIterator.hasNext()) {
				child = propertiesIterator.next();
				if (child.contains("name") // restore the position of the
						// document windows
						&& child.getStringProperty("name").equals(
								"ViewInfoProperties")
						&& child.contains("documentType")) {
					boolean isClosed = true;
					if (child.contains("isClosed"))
						isClosed = child.getBooleanProperty("isClosed");
					if (isClosed == false) {
						WindowInfo windowProps = WindowInfo
								.createFromXMLEntity(child);
						String documentName = child
								.getStringProperty("documentName");
						String documentType = child
								.getStringProperty("documentType");
						ProjectDocument pd = this.getProjectDocumentByName(
								documentName, documentType);
						if (pd == null)
							continue;
						IWindow win = null;
						if (child.getChildrenCount() > 0
								&& child.getChild(0).getName()
										.equals("windowData")) {
							// this will be generalized to all ProjectDocuments
							// as soon as possible
							WindowData windowData = new WindowData();
							windowData.setXMLEntity(child.getChild(0));
							pd.storeWindowData(windowData);
							win = pd.createWindow();
						} else {
							win = pd.createWindow();
						}
						if (win == null) {
							continue;
						}
						PluginServices.getMDIManager().addWindow(win);
						PluginServices.getMDIManager().changeWindowInfo(win,
								windowProps);
					}
				} else if (child.contains("className") // restore the position
						// of the project
						// manager window
						&& child.getStringProperty("className").equals(
								ProjectWindow.class.getCanonicalName())
						&& child.contains("name")
						&& child.getStringProperty("name").equals(
								"ViewInfoProperties")) {
					WindowInfo wi = WindowInfo.createFromXMLEntity(child);
					// don't restore size for ProjectManager window, as it's not
					// resizable
					wi.setHeight(-1);
					wi.setWidth(-1);
					ProjectExtension pe = (ProjectExtension) PluginServices
							.getExtension(com.iver.cit.gvsig.ProjectExtension.class);
					if (pe != null) {
						pe.setProject(this);
						pe.showProjectWindow(wi);
					}
					projectWindowRestored = true;
				}
			}
		}

		if (!projectWindowRestored) { // if the project window was not stored
			// in the project, open it now
			ProjectExtension pe = (ProjectExtension) PluginServices
					.getExtension(com.iver.cit.gvsig.ProjectExtension.class);

			if (pe != null) {
				pe.setProject(this);
				pe.showProjectWindow();
			}
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param xml
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws XMLException
	 * @throws DriverException
	 * @throws DriverIOException
	 * @throws OpenException
	 * @throws VersionException
	 */
	public static Project createFromXML(
			org.gvsig.persistence.generated.Project xml) throws OpenException {

		try {
			Project p = new Project();
			p.comments = xml.getComments();
			p.creationDate = xml.getCreationDate();
			PathGenerator pg = PathGenerator.getInstance();
			boolean absolutePath = xml.isAbsolutePath();
			p.isAbsolutePath = absolutePath;
			pg.setIsAbsolutePath(p.isAbsolutePath);

			for (LabeledExtentType extent : xml.getExtents()) {
				ProjectExtent pe = ProjectExtent.createFromXML(extent);
				p.extents.add(pe);
			}

			for (int i = 0; i < xml.getDocuments().size(); i++) {
				DocumentType document = xml.getDocuments().get(i);
				try {
					ProjectDocument pD = ProjectDocument.createFromXML(
							document.getClassName(), p);
					pD.setXMLEntity(document);
					p.addDocument(pD);
				} catch (OpenException e) {
					e.showMessageError(document.getName() + "\n  "
							+ document.getClassName());
				}
			}

			p.modificationDate = xml.getModificationDate();
			// p.modified = xml.getBooleanProperty("modified");
			p.name = xml.getName();
			p.owner = xml.getOwner();
			p.selectionColor = StringUtilities.string2Color(xml
					.getSelectionColor());

			String strProj = xml.getDefaultCrs();

			if (strProj != null) {
				p.setCrs(CRS.decode(strProj));
			}

			PostProcessSupport.executeCalls();
			return p;
		} catch (Exception e) {
			throw new OpenException(e, null);
		}

	}

	/**
	 * Obtiene la vista que contiene a la capa que se pasa como par�metro
	 * 
	 * @param layer
	 *            Capa cuya vista se quiere obtener
	 * 
	 * @return
	 * 
	 * @throws RuntimeException
	 *             Si la capa que se pasa como par�metro no se encuentra en
	 *             ninguna vista
	 */
	public String getView(Layer layer) {
		ArrayList<ProjectDocument> views = getDocumentsByType(ProjectViewFactory.registerName);
		for (int v = 0; v < views.size(); v++) {
			ProjectView pView = (ProjectView) views.get(v);
			Layer root = pView.getMapContext().getRootLayer();
			if (root.contains(layer)) {
				return pView.getName();
			}
		}

		throw new RuntimeException("The layer is not in a view");
	}

	/**
	 * Devuelve la vista cuyo nombre coincide (sensible a mayusculas) con el que
	 * se pasa como par�metro. Devuelve null si no hay ninguna vista con ese
	 * nombre
	 * 
	 * @param viewName
	 *            Nombre de la vista que se quiere obtener
	 * 
	 * @return DOCUMENT ME!
	 */
	/*
	 * public ProjectView getViewByName(String viewName) { ArrayList
	 * views=getDocuments(PluginServices.getText(this,"Vista")); Object o =
	 * getProjectDocumentByName(viewName, PluginServices.getText(this,"Vista"));
	 * 
	 * if (o == null) { return null; }
	 * 
	 * return (ProjectView) o; }
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public CoordinateReferenceSystem getCrs() {
		if (crs == null)
			crs = Project.defaultCrs;
		return crs;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param defaultProjection
	 *            DOCUMENT ME!
	 */
	public void setCrs(CoordinateReferenceSystem crs) {
		this.crs = crs;
	}

	/**
	 * Sets the projection used when no projection is defined
	 * 
	 * @param defaultCrs
	 *            DOCUMENT ME!
	 */
	public static void setDefaultCrs(CoordinateReferenceSystem defaultCrs) {
		Project.defaultCrs = defaultCrs;
	}

	public static CoordinateReferenceSystem getDefaultCrs() {
		if (defaultCrs == null) {
			// Default Projection
			CoordinateReferenceSystem crs = null;

			PluginServices plugin = PluginServices
					.getPluginServices("com.iver.cit.gvsig");
			if (plugin != null) {
				XMLEntity xml = plugin.getPersistentXML();

				if (xml != null && xml.contains("DefaultProjection")) {
					String projCode = xml
							.getStringProperty("DefaultProjection");
					try {
						crs = CRS.decode(projCode);
					} catch (NoSuchAuthorityCodeException e) {
						logger.debug("Cannot parse CRS", e);
					} catch (FactoryException e) {
						logger.debug("Cannot parse CRS", e);
					}
				}
			}
			if (crs == null) {
				crs = defaultFactoryCrs;
			}
			Project.setDefaultCrs(crs);

		}
		return Project.defaultCrs;
	}

	/**
	 * Obtiene un documento a partir de su nombre y el nombre de registro en el
	 * pointExtension, este �ltimo se puede obtener del
	 * Project****Factory.registerName.
	 * 
	 * @param name
	 *            Nombre del documento
	 * @param type
	 *            nombre de registro en el extensionPoint
	 * 
	 * @return Documento
	 */
	public ProjectDocument getProjectDocumentByName(String name, String type) {
		ArrayList<ProjectDocument> docs = getDocumentsByType(type);
		for (Iterator<ProjectDocument> iter = docs.iterator(); iter.hasNext();) {
			ProjectDocument elem = iter.next();
			if (elem.getName().equals(name)) {
				return elem;
			}
		}

		return null;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * 
	 * @return
	 */
	/*
	 * public ProjectTable getTableByName(String name) { ArrayList
	 * tables=getDocuments(PluginServices.getText(this,"Tabla")); Object o =
	 * getProjectElementByName(name, tables);
	 * 
	 * if (o == null) { return null; }
	 * 
	 * return (ProjectTable) o; }
	 */
	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 * 
	 * @return
	 */
	/*
	 * public ProjectMap getLayoutByName(String name) { Object o =
	 * getProjectElementByName(name, maps);
	 * 
	 * if (o == null) { return null; }
	 * 
	 * return (ProjectMap) o; }
	 */
	public void getDataSourceByLayer(Layer layer) {
		assert false : "Should just get the source of the associated table";
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * @throws SaveException
	 * @throws XMLException
	 * @throws SaveException
	 */
	public XMLEntity getWindowInfoXMLEntity(IWindow window)
			throws SaveException {
		if (window != null
				&& PluginServices.getMDIManager().getWindowInfo(window) != null) {
			WindowInfo vi = PluginServices.getMDIManager()
					.getWindowInfo(window);
			XMLEntity xml = new XMLEntity();
			// xml.putProperty("nameClass", this.getClass().getName());
			try {
				xml.setName("ViewInfoProperties");
				xml.putProperty("className", this.getClass().getName());
				xml.putProperty("X", vi.getX());
				xml.putProperty("Y", vi.getY());
				xml.putProperty("Width", vi.getWidth());
				xml.putProperty("Height", vi.getHeight());
				xml.putProperty("isVisible", vi.isVisible());
				xml.putProperty("isResizable", vi.isResizable());
				xml.putProperty("isMaximizable", vi.isMaximizable());
				xml.putProperty("isModal", vi.isModal());
				xml.putProperty("isModeless", vi.isModeless());
				xml.putProperty("isClosed", vi.isClosed());
				if (vi.isMaximized() == true) {
					xml.putProperty("isMaximized", vi.isMaximized());
					xml.putProperty("normalX", vi.getNormalX());
					xml.putProperty("normalY", vi.getNormalY());
					xml.putProperty("normalWidth", vi.getNormalWidth());
					xml.putProperty("normalHeight", vi.getNormalHeight());
				}
			} catch (Exception e) {
				throw new SaveException(e, this.getClass().getName());
			}
			return xml;
		}
		return null;
	}

	public static WindowInfo createWindowInfoFromXMLEntity(XMLEntity xml) {
		WindowInfo result = new WindowInfo();
		result.setX(xml.getIntProperty("X"));
		result.setY(xml.getIntProperty("Y"));
		result.setHeight(xml.getIntProperty("Height"));
		result.setWidth(xml.getIntProperty("Width"));
		result.setClosed(xml.getBooleanProperty("isClosed"));
		if (xml.contains("isMaximized")) {
			boolean maximized = xml.getBooleanProperty("isMaximized");
			result.setMaximized(maximized);
			if (maximized == true) {
				result.setNormalBounds(xml.getIntProperty("normalX"),
						xml.getIntProperty("normalY"),
						xml.getIntProperty("normalWidth"),
						xml.getIntProperty("normalHeight"));
			} else {
				result.setNormalBounds(result.getBounds());
			}
		}

		return result;
	}

	public DataSourceType getSourceInfoXMLEntity(Source di) {
		DataSourceType ret = new DataSourceType();
		Map<String, String> properties = di.getPersistentProperties();
		Iterator<String> keyIterator = properties.keySet().iterator();
		while (keyIterator.hasNext()) {
			String persistencePropertyKey = keyIterator.next();
			String persistencePropertyValue = properties
					.get(persistencePropertyKey);
			StringPropertyType property = new StringPropertyType();
			property.setPropertyName(persistencePropertyKey);
			property.setPropertyValue(persistencePropertyValue);
			ret.getProperties().add(property);
		}

		return ret;
	}

	/**
	 * Devuelve un arrayList con todos los documentos del tipo especificado como
	 * par�metro.
	 * 
	 * @param registerName
	 *            nombre de registro en el extensionPoint
	 * 
	 * @return Documentos del tipo especificado
	 */
	public ArrayList<ProjectDocument> getDocumentsByType(String registerName) {
		ArrayList<ProjectDocument> docuArray = new ArrayList<ProjectDocument>();
		for (int i = 0; i < documents.size(); i++) {
			ProjectDocument projectDocument = documents.get(i);
			ProjectDocumentFactory pdf = projectDocument
					.getProjectDocumentFactory();
			if (pdf == null)
				continue;
			if (pdf.getRegisterName().equals(registerName)) {
				docuArray.add(projectDocument);
			}
		}
		return docuArray;
	}

	/**
	 * Devuelve un arrayList con todos los documentos.
	 * 
	 * @return Documentos
	 */
	public ArrayList<ProjectDocument> getDocuments() {
		ArrayList<ProjectDocument> docuArray = new ArrayList<ProjectDocument>();
		for (int i = 0; i < documents.size(); i++) {
			docuArray.add(documents.get(i));
		}
		return docuArray;
	}

	/**
	 * Inserta un documento.
	 * 
	 * @param doc
	 *            Documento
	 */
	public void addDocument(ProjectDocument doc) {
		documents.add(doc);
		doc.addPropertyChangeListener(this);
		// modified = true;
		change.firePropertyChange("addDocument", "", doc);
		doc.setProject(this, 0);
		doc.afterAdd();
	}

	/**
	 * Borra un documento.
	 * 
	 * @param doc
	 *            Documento
	 */
	public void delDocument(ProjectDocument doc) {
		documents.remove(doc);
		// modified = true;
		change.firePropertyChange("", null, null);
		doc.afterRemove();
	}

	/**
	 * Sets the default selection color that will be used in subsequent
	 * projects.
	 * 
	 * @param color
	 */
	public static void setDefaultSelectionColor(Color color) {
		defaultSelectionColor = color;
	}

	/**
	 * Returns the current default selection color defined which is the color
	 * defined when the user does not define any other one
	 * 
	 * @return java.awt.Color
	 */
	public static Color getDefaultSelectionColor() {
		// TODO es millorable?
		PluginServices plugin = PluginServices
				.getPluginServices("com.iver.cit.gvsig");
		if (plugin != null) {
			XMLEntity xml = plugin.getPersistentXML();
			if (xml.contains("DefaultSelectionColor"))
				defaultSelectionColor = StringUtilities.string2Color(xml
						.getStringProperty("DefaultSelectionColor"));
		}
		return defaultSelectionColor;
	}

	/**
	 * Returns the user's default map units. This is the cartography data
	 * distance units.
	 */
	public static Unit getDefaultMapUnits() {
		if (defaultMapUnits == null) {
			PluginServices plugin = PluginServices
					.getPluginServices("com.iver.cit.gvsig");
			if (plugin != null) {
				XMLEntity xml = plugin.getPersistentXML();
				if (xml.contains("DefaultMapUnits")) {
					defaultMapUnits = Unit.fromName(xml
							.getStringProperty("DefaultMapUnits"));
				}
			}
			if (defaultMapUnits == null) {
				defaultMapUnits = Unit.METERS;
			}
		}
		return defaultMapUnits;
	}

	/**
	 * Returns the user's default view units for measuring distances. This is
	 * the units that the user will see in the status bar of the view.
	 * 
	 */
	public static Unit getDefaultDistanceUnits() {
		if (defaultDistanceUnits == null) {
			PluginServices plugin = PluginServices
					.getPluginServices("com.iver.cit.gvsig");
			if (plugin != null) {
				XMLEntity xml = plugin.getPersistentXML();
				if (xml.contains("DefaultDistanceUnits")) {
					defaultDistanceUnits = Unit.fromName(xml
							.getStringProperty("DefaultDistanceUnits"));
				}
			}
			if (defaultDistanceUnits == null) {
				defaultDistanceUnits = Unit.METERS;
			}
		}
		return defaultDistanceUnits;
	}

	/**
	 * Returns the user's default view units for measuring areas. This is the
	 * units that the user will see in the status bar of the view.
	 * 
	 */
	public static Unit getDefaultDistanceArea() {
		if (defaultDistanceArea == null) {
			PluginServices plugin = PluginServices
					.getPluginServices("com.iver.cit.gvsig");
			if (plugin != null) {
				XMLEntity xml = plugin.getPersistentXML();
				if (xml.contains("DefaultDistanceArea")) {
					defaultDistanceArea = Unit.fromName(xml
							.getStringProperty("DefaultDistanceArea"));
				}
			}
			if (defaultDistanceArea == null) {
				defaultDistanceArea = Unit.METERS;
			}
		}
		return defaultDistanceArea;
	}

	/**
	 * Sets the default map unit (the units used by the data).
	 * 
	 * @param mapUnits
	 */
	public static void setDefaultMapUnits(Unit mapUnits) {
		defaultMapUnits = mapUnits;
	}

	/**
	 * Sets the default distance units (the units shown in the status bar)
	 * 
	 * @param distanceUnits
	 */
	public static void setDefaultDistanceUnits(Unit distanceUnits) {
		defaultDistanceUnits = distanceUnits;
	}

	/**
	 * Sets the default distance area (the units shown in the status bar)
	 * 
	 * @param distanceUnits
	 */
	public static void setDefaultDistanceArea(Unit distanceArea) {
		defaultDistanceArea = distanceArea;
	}

	public boolean isValidXMLForImport(String xml) {
		XMLEntity xmlEntity;
		try {
			xmlEntity = XMLEntity.parse(xml);
		} catch (Exception e) {
			return false;
		}

		return checkExportXMLRootNode(xmlEntity);
	}

	public boolean isValidXMLForImport(String xml, String type) {
		XMLEntity xmlEntity;
		try {
			xmlEntity = XMLEntity.parse(xml);
		} catch (Exception e) {
			return false;
		}

		if (!checkExportXMLRootNode(xmlEntity)) {
			return false;
		}

		XMLEntity typeRoot = xmlEntity.firstChild("type", type);

		if (typeRoot == null) {
			return false;
		}

		return (typeRoot.getChildrenCount() > 0);
	}

	private boolean checkExportXMLRootNode(XMLEntity xml) {
		if (!xml.contains("applicationName"))
			return false;
		if (!xml.getStringProperty("applicationName").equalsIgnoreCase("gvSIG"))
			return false;

		if (!xml.contains("version"))
			return false;
		if (!xml.getStringProperty("version")
				.equalsIgnoreCase(Version.format()))
			return false;

		return true;
	}

	public XMLEntity getExportXMLTypeRootNode(XMLEntity root, String type) {
		XMLEntity typeRoot = root.firstChild("type", type);
		if (typeRoot == null) {
			typeRoot = this.newExportXMLTypeNode(type);
			root.addChild(typeRoot);
		}
		return typeRoot;
	}

	private XMLEntity newExportXMLTypeNode(String type) {
		XMLEntity xmlDataSources = new XMLEntity();
		xmlDataSources.putProperty("type", type);
		return xmlDataSources;
	}

	public static ProjectDocumentFactory getProjectDocumentFactory(String type) {
		ProjectDocumentFactory pde = null;
		try {
			ExtensionPoints extensionPoints = ExtensionPointsSingleton
					.getInstance();
			ExtensionPoint extPoint = extensionPoints.get("Documents");
			try {
				pde = (ProjectDocumentFactory) extPoint.create(type);
			} catch (InstantiationException e) {
				NotificationManager.addError(
						"Clase de ProjectDocument no reconocida", e);
			} catch (IllegalAccessException e) {
				NotificationManager.addError(
						"Clase de ProjectDocument no reconocida", e);
			}

		} catch (Exception e1) {
			return null;
		}
		return pde;
	}

	public boolean hasChanged() {
		// we return true if the project is not empty (until we have a better
		// method...)
		if ((this.getDocuments().size() != 0) || modified) {
			return true;
		}
		return false;
		// return signatureAtStartup != getXMLEntity().hash();
	}

	public void setSignature(long hash) {
	}

	public boolean isAbsolutePath() {
		return isAbsolutePath;
	}

	public void setIsAbsolutePath(boolean selected) {
		isAbsolutePath = selected;
	}
}
