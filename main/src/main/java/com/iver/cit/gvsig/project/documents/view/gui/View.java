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
package com.iver.cit.gvsig.project.documents.view.gui;

import geomatico.events.EventBus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JSplitPane;

import org.apache.log4j.Logger;
import org.geotools.referencing.CRS;
import org.gvsig.events.CRSChangeEvent;
import org.gvsig.events.CRSChangeHandler;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.main.events.ExtentChangeEvent;
import org.gvsig.main.events.ExtentChangeHandler;
import org.gvsig.map.MapContext;
import org.gvsig.map.MapContextFactory;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.NewStatusBar;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.ZoomOutRightButtonListener;
import com.iver.cit.gvsig.fmap.tools.Behavior.Behavior;
import com.iver.cit.gvsig.fmap.tools.Behavior.MouseMovementBehavior;
import com.iver.cit.gvsig.fmap.tools.Behavior.MoveBehavior;
import com.iver.cit.gvsig.fmap.tools.Behavior.PointBehavior;
import com.iver.cit.gvsig.fmap.tools.Behavior.PolygonBehavior;
import com.iver.cit.gvsig.fmap.tools.Behavior.RectangleBehavior;
import com.iver.cit.gvsig.project.documents.view.ProjectView;
import com.iver.cit.gvsig.project.documents.view.ProjectViewBase;
import com.iver.cit.gvsig.project.documents.view.toolListeners.PanListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.PointSelectListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.PolygonSelectListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.RectangleSelectListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.StatusBarListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.ZoomInListener;
import com.iver.cit.gvsig.project.documents.view.toolListeners.ZoomOutListener;
import com.iver.utiles.StringUtilities;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.console.JConsole;
import com.iver.utiles.console.JDockPanel;
import com.iver.utiles.console.ResponseListener;
import com.iver.utiles.console.jedit.JEditTextArea;

/**
 * <p>
 * <b>Class View</b>. This class represents the gvSIG specific internal window
 * where the maps are displayed and where the events coming from the user are
 * captured.
 * </p>
 * <p>
 * It is composed by three main visual areas:
 * </p>
 * <ol>
 * <li>
 * <b>Map control</b>: the map area located in the right area of the window. It
 * takes up the biggest part of the window.</li>
 * <li>
 * <b>Table of contents (TOC)</b>: is a list of layers displayed in the view.
 * The TOC is located on the left-top corner of the View and is the place where
 * the user can modify the order, the legends, the visibility and other
 * properties of the layers.</li>
 * <li>
 * <b>Map overview</b>: is a small MapControl located in the left-bottom corner
 * of the View where the user can put some layers which summarizes the view. It
 * is used to make the navigation easier and faster.</li>
 * </ol>
 * 
 * @see com.iver.cit.gvsig.fmap.MapControl.java <br>
 *      com.iver.cit.gvsig.gui.toc.TOC.java <br>
 *      com.iver.cit.gvsig.gui.MapOverview.java <br>
 * @author vcn
 */
public class View extends BaseView {
	private static final Logger logger = Logger.getLogger(View.class);

	static private Color defaultViewBackColor = Color.WHITE;

	/** DOCUMENT ME! */

	private JConsole console;
	private JDockPanel dockConsole = null;
	protected ResponseAdapter consoleResponseAdapter = new ResponseAdapter();
	protected boolean isShowConsole = false;

	private EventBus eventBus;

	/**
	 * Creates a new View object. Before being used, the object must be
	 * initialized.
	 * 
	 * @see initialize()
	 */
	public View() {
		super();
		this.eventBus = InjectorSingleton.getInjector().getInstance(
				EventBus.class);
		this.setName("View");
	}

	/**
	 * Create the internal componentes and populate the window with them. If the
	 * layout properties were set using the
	 * <code>setWindowData(WindowData)</code> method, the window will be
	 * populated according to this properties.
	 */
	public void initialize() {
		super.initialize();
		initComponents();
		hideConsole();
		getConsolePanel().addResponseListener(consoleResponseAdapter);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param model
	 *            DOCUMENT ME!
	 */
	public void setModel(ProjectViewBase model) {
		this.modelo = model;
		// Se registra como listener de cambios en FMap
		MapContext fmap = modelo.getMapContext();

		Layer root = fmap.getRootLayer();
		Layer[] vectorialInEdition = root.filter(LayerFilter.FEATURE_EDITING);

		if (vectorialInEdition.length > 0) {
			this.showConsole();
		}

		// Se configura el mapControl
		m_MapControl.setMapContext(fmap);
		m_TOC.setMapContext(fmap);

		m_MapControl.setBackground(new Color(255, 255, 255));
		if (modelo.getMapOverViewContext() != null) {
			m_MapLoc.setModel(modelo.getMapOverViewContext());
		}
		model.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("name")) {
					PluginServices.getMDIManager().getWindowInfo(View.this)
							.setTitle("Vista: " + (String) evt.getNewValue());
				}
			}
		});
		eventBus.addHandler(ExtentChangeEvent.class, new ExtentChangeHandler() {

			@Override
			public void extentChanged(MapContext source) {
				if (PluginServices.getMainFrame() != null) {
					NewStatusBar status = PluginServices.getMainFrame()
							.getStatusBar();
					try {
						long scale = (long) m_MapControl.getViewPort()
								.getScaleView();
						status.setControlValue("scale", String.valueOf(scale));
					} catch (Exception exc) {
						logger.warn("Cannot get viewport scale", exc);
					}
					status.setMessage("projection",
							CRS.toSRS(getMapControl().getMapContext().getCRS()));
				}
			}
		});
		eventBus.addHandler(CRSChangeEvent.class, new CRSChangeHandler() {

			@Override
			public void crsChanged(MapContext source) {
				if (source == getModel().getMapContext()) {
					m_MapLoc.setCrs(source.getCRS());
				}
			}
		});

	}

	public JConsole getConsolePanel() {
		if (console == null) {
			console = new JConsole();
			// Para distinguir cuando se est� escribiendo sobre la consola y
			// cuando no.
			console.setJTextName("CADConsole");
		}
		return console;
	}

	private JDockPanel getDockConsole() {
		if (dockConsole == null) {
			dockConsole = new JDockPanel(getConsolePanel());
		}
		return dockConsole;
	}

	public void addConsoleListener(String prefix, ResponseListener listener) {
		consoleResponseAdapter.putSpaceListener(prefix, listener);

	}

	public void removeConsoleListener(ResponseListener listener) {
		consoleResponseAdapter.deleteListener(listener);

	}

	public void focusConsole(String text) {
		getConsolePanel().addResponseText(text);
		// TODO: HACE ESTO CON EL KEYBOARDFOCUSMANAGER
		// KeyboardFocusManager kfm =
		// KeyboardFocusManager.getCurrentKeyboardFocusManager();
		// kfm.focusNextComponent(getConsolePanel());
		System.err.println("Asigno el foco a la consola");

		JEditTextArea jeta = getConsolePanel().getTxt();
		jeta.requestFocusInWindow();
		jeta.setCaretPosition(jeta.getText().length());

		// FocusManager fm=FocusManager.getCurrentManager();
		// fm.focusNextComponent(getConsolePanel());

	}

	public void hideConsole() {
		isShowConsole = false;
		/*
		 * removeAll(); //JSplitPane split = new
		 * JSplitPane(JSplitPane.VERTICAL_SPLIT); JSplitPane tempMainSplit = new
		 * JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		 * tempMainSplit.setPreferredSize(new Dimension(500, 300)); if
		 * (!isPalette()){ JSplitPane tempSplitToc = new
		 * JSplitPane(JSplitPane.VERTICAL_SPLIT);
		 * tempSplitToc.setTopComponent((TOC) m_TOC);
		 * tempSplitToc.setBottomComponent(m_MapLoc);
		 * tempSplitToc.setResizeWeight(0.7);
		 * tempMainSplit.setLeftComponent(tempSplitToc); }else{
		 * tempMainSplit.setLeftComponent(m_TOC); } m_TOC.setVisible(true);
		 * tempMainSplit.setRightComponent(m_MapControl);
		 * //split.setBottomComponent(getConsolePanel());
		 * //split.setTopComponent(tempMainSplit); //
		 * split.setResizeWeight(0.9); this.setLayout(new BorderLayout());
		 * this.add(tempMainSplit, BorderLayout.CENTER);
		 */
		getDockConsole().setVisible(false);

	}

	public void showConsole() {
		if (isShowConsole)
			return;
		isShowConsole = true;
		/*
		 * removeAll(); JSplitPane split = new
		 * JSplitPane(JSplitPane.VERTICAL_SPLIT); JSplitPane tempMainSplit = new
		 * JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		 * tempMainSplit.setPreferredSize(new Dimension(500, 300)); if
		 * (!isPalette()){ JSplitPane tempSplitToc = new
		 * JSplitPane(JSplitPane.VERTICAL_SPLIT);
		 * tempSplitToc.setTopComponent((TOC) m_TOC);
		 * tempSplitToc.setBottomComponent(m_MapLoc);
		 * tempSplitToc.setResizeWeight(0.7);
		 * tempMainSplit.setLeftComponent(tempSplitToc); }else{
		 * tempMainSplit.setLeftComponent(m_TOC); } m_TOC.setVisible(true);
		 * tempMainSplit.setRightComponent(m_MapControl);
		 * split.setBottomComponent(getConsolePanel());
		 * split.setTopComponent(tempMainSplit); split.setResizeWeight(0.9);
		 * this.setLayout(new BorderLayout()); this.add(split,
		 * BorderLayout.CENTER);
		 */
		getMapControl().remove(getDockConsole());
		// getConsolePanel().setPreferredSize(new Dimension(200, 150));
		getMapControl().setLayout(new BorderLayout());
		getMapControl().add(getDockConsole(), BorderLayout.SOUTH);
		getDockConsole().setVisible(true);

	}

	private class ResponseAdapter implements ResponseListener {

		private HashMap<String, ResponseListener> spaceListener = new HashMap<String, ResponseListener>();

		public void putSpaceListener(String namespace, ResponseListener listener) {
			spaceListener.put(namespace, listener);
		}

		/**
		 * @see com.iver.utiles.console.ResponseListener#acceptResponse(java.lang.String)
		 */
		public void acceptResponse(String response) {
			boolean nameSpace = false;
			int n = -1;
			if (response != null) {
				if ((n = response.indexOf(':')) != -1) {
					nameSpace = true;
				}
			}

			if (nameSpace) {
				ResponseListener listener = spaceListener.get(response
						.substring(0, n));
				if (listener != null)
					listener.acceptResponse(response.substring(n + 1));
			} else {
				Iterator<ResponseListener> i = spaceListener.values()
						.iterator();
				while (i.hasNext()) {
					i.next().acceptResponse(response);
				}
			}
		}

		/**
		 * @param listener
		 */
		public void deleteListener(ResponseListener listener) {
			Iterator<String> i = spaceListener.keySet().iterator();
			while (i.hasNext()) {
				String namespace = i.next();
				ResponseListener l = spaceListener.get(namespace);
				if (l == listener) {
					spaceListener.remove(namespace);
				}
			}
		}

	}

	/**
	 * DOCUMENT ME!
	 */
	protected void initComponents() { // GEN-BEGIN:initComponents
		MapContextFactory factory = InjectorSingleton.getInjector()
				.getInstance(MapContextFactory.class);
		m_MapControl = new MapControl(eventBus, factory,
				ProjectView.DEFAULT_MAP_UNITS, ProjectView.DEFAULT_AREA_UNITS,
				ProjectView.DEFAULT_DISTANCE_UNITS, ProjectView.DEFAULT_CRS);

		m_MapControl.addExceptionListener(mapControlExceptionListener);
		m_TOC = new TOC();

		// Ponemos el localizador
		m_MapLoc = new MapOverview(m_MapControl);
		removeAll();
		tempMainSplit = new ViewSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		if (windowData == null) {
			m_MapLoc.setPreferredSize(new Dimension(150, 200));
			tempMainSplit.setPreferredSize(new Dimension(500, 300));
		}

		if (!isPalette()) {
			tempSplitToc = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			tempSplitToc.setTopComponent(m_TOC);
			tempSplitToc.setBottomComponent(m_MapLoc);
			tempSplitToc.setResizeWeight(0.7);
			tempMainSplit.setLeftComponent(tempSplitToc);
		} else {
			tempMainSplit.setLeftComponent(m_TOC);
		}
		m_TOC.setVisible(true);
		tempMainSplit.setRightComponent(m_MapControl);
		// split.setBottomComponent(getConsolePanel());
		// split.setTopComponent(tempMainSplit);
		// split.setResizeWeight(0.9);
		this.setLayout(new BorderLayout());
		this.add(tempMainSplit, BorderLayout.CENTER);

		if (windowData != null) {
			try {
				tempMainSplit.setDividerLocation(Integer.valueOf(
						windowData.get("MainDivider.Location")).intValue());
				if (windowData.get("TOCDivider.Location") != null) {
					tempSplitToc.setDividerLocation(Integer.valueOf(
							windowData.get("TOCDivider.Location")).intValue());
				}
			} catch (NumberFormatException ex) {
				PluginServices.getLogger().error(
						"Error restoring View properties");
			}
		}

		StatusBarListener sbl = new StatusBarListener(m_MapControl);

		ZoomOutListener zol = new ZoomOutListener(m_MapControl);
		m_MapControl.addMapTool("zoomOut", new Behavior[] {
				new PointBehavior(zol), new MouseMovementBehavior(sbl) });

		// pan

		PanListener pl = new PanListener(m_MapControl);
		m_MapControl.addMapTool("pan", new Behavior[] { new MoveBehavior(pl),
				new MouseMovementBehavior(sbl) });

		// Seleccion por punto
		PointSelectListener psl = new PointSelectListener(m_MapControl);
		m_MapControl.addMapTool("pointSelection", new Behavior[] {
				new PointBehavior(psl), new MouseMovementBehavior(sbl) });

		// Selecci�n por rect�ngulo
		RectangleSelectListener rsl = new RectangleSelectListener(m_MapControl);
		m_MapControl.addMapTool("rectSelection", new Behavior[] {
				new RectangleBehavior(rsl), new MouseMovementBehavior(sbl) });

		// Selecci�n por pol�gono
		PolygonSelectListener poligSel = new PolygonSelectListener(m_MapControl);
		m_MapControl
				.addMapTool("polSelection", new Behavior[] {
						new PolygonBehavior(poligSel),
						new MouseMovementBehavior(sbl) });

		// Zoom por rect�ngulo
		ZoomOutRightButtonListener zoil = new ZoomOutRightButtonListener(
				m_MapControl);
		ZoomInListener zil = new ZoomInListener(m_MapControl);
		m_MapControl.addMapTool("zoomIn", new Behavior[] {
				new RectangleBehavior(zil), new PointBehavior(zoil),
				new MouseMovementBehavior(sbl) });

		m_MapControl.setTool("zoomIn"); // Por defecto
	}

	/**
	 * DOCUMENT ME!
	 */
	/*
	 * public void openPropertiesWindow() { }
	 */
	/**
	 * DOCUMENT ME!
	 */
	/*
	 * public void openQueryWindow() { }
	 */

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#windowActivated()
	 */
	public void windowActivated() {
		NewStatusBar status = PluginServices.getMainFrame().getStatusBar();
		status.setMessage("units", PluginServices.getText(this, getMapControl()
				.getViewPort().getDistanceUnits().name));
		try {
			long scale = (long) m_MapControl.getViewPort().getScaleView();
			status.setControlValue("scale", String.valueOf(scale));
		} catch (Exception e) {
			logger.warn("Cannot get viewport scale", e);
		}
		status.setMessage("projection",
				CRS.toSRS(getMapControl().getMapContext().getCRS()));
	}

	public void toPalette() {
		isPalette = true;
		m_MapLoc.setPreferredSize(new Dimension(200, 150));
		m_MapLoc.setSize(new Dimension(200, 150));
		movp = new MapOverViewPalette(m_MapLoc, this);
		PluginServices.getMDIManager().addWindow(movp);
		Layer root = getModel().getMapContext().getRootLayer();
		Layer[] layers = root.filter(LayerFilter.ACTIVE);
		if (layers.length > 0 && layers[0].isEditing()) {
			showConsole();
		} else {
			hideConsole();
		}
	}

	public void restore() {
		isPalette = false;
		PluginServices.getMDIManager().closeWindow(movp);
		Layer root = getModel().getMapContext().getRootLayer();
		Layer[] layers = root.filter(LayerFilter.ACTIVE);
		if (layers.length > 0 && layers[0].isEditing()) {
			showConsole();
		} else {
			hideConsole();
			JSplitPane tempSplitToc = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			tempSplitToc.setTopComponent(m_TOC);
			tempSplitToc.setBottomComponent(m_MapLoc);
			tempSplitToc.setResizeWeight(0.7);
			tempMainSplit.setLeftComponent(tempSplitToc);
		}
	}

	/**
	 * Sets the default map overview background color that will be used in
	 * subsequent projects.
	 * 
	 * @param color
	 */
	public static void setDefaultMapOverViewBackColor(Color color) {
		throw new Error(
				"support for map overview back color not yet implemented");
	}

	/**
	 * Returns the current default map overview background color defined which
	 * is the color defined when the user does not define any other one
	 * 
	 * @return java.awt.Color
	 */
	public static Color getDefaultMapOverViewBackColor() {
		throw new Error(
				"support for map overview back color not yet implemented");
	}

	/**
	 * Returns the current default view background color defined which is the
	 * color defined when the user does not define any other one
	 * 
	 * @return java.awt.Color
	 */
	public static Color getDefaultBackColor() {
		// TODO es millorable?
		PluginServices plugin = PluginServices
				.getPluginServices("com.iver.cit.gvsig");
		if (plugin != null) {
			XMLEntity xml = plugin.getPersistentXML();
			if (xml.contains("DefaultViewBackColor"))
				defaultViewBackColor = StringUtilities.string2Color(xml
						.getStringProperty("DefaultViewBackColor"));
		}
		return defaultViewBackColor;
	}

	/**
	 * Sets the default view background color that will be used in subsequent
	 * projects.
	 * 
	 * @param color
	 */
	public static void setDefaultBackColor(Color color) {
		defaultViewBackColor = color;
	}

	public Object getWindowProfile() {
		return WindowInfo.EDITOR_PROFILE;
	}

}
