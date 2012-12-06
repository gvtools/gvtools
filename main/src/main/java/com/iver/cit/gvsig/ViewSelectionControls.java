/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
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

/* CVS MESSAGES:
 *
 * $Id: ViewSelectionControls.java 15647 2007-10-30 12:03:52Z jmvivo $
 * $Log$
 * Revision 1.16  2007-03-06 16:34:02  caballero
 * Exceptions
 *
 * Revision 1.15  2007/01/04 07:24:32  caballero
 * isModified
 *
 * Revision 1.14  2006/12/27 11:08:56  fdiaz
 * Arreglado el metodo hasVectorLayers para que no se pare en los grupos de capas cuando estos no tienen capas vectoriales.
 *
 * Revision 1.13  2006/09/25 16:54:12  caballero
 * invert selection
 *
 * Revision 1.12  2006/09/20 13:17:40  jaume
 * *** empty log message ***
 *
 * Revision 1.11  2006/09/20 10:56:56  sbayarri
 * Refactorizaci�n clases View y ProjectView
 *
 * Revision 1.10  2006/09/15 10:40:03  caballero
 * extensibilidad de documentos
 *
 * Revision 1.9  2006/09/11 16:14:15  fjp
 * Invertir seleccion. Falta subirlo al branch de la 1.0
 *
 * Revision 1.8  2006/09/11 06:53:13  fjp
 * Antes del parto
 *
 * Revision 1.7  2006/09/04 16:26:54  fjp
 * Seleccionar por pol�gono
 *
 * Revision 1.6  2006/08/29 07:56:27  cesar
 * Rename the *View* family of classes to *Window* (ie: SingletonView to SingletonWindow, ViewInfo to WindowInfo, etc)
 *
 * Revision 1.5  2006/08/29 07:21:08  cesar
 * Rename com.iver.cit.gvsig.fmap.Fmap class to com.iver.cit.gvsig.fmap.MapContext
 *
 * Revision 1.4  2006/08/29 07:13:53  cesar
 * Rename class com.iver.andami.ui.mdiManager.View to com.iver.andami.ui.mdiManager.IWindow
 *
 * Revision 1.3  2006/08/18 08:40:04  jmvivo
 * Actualizado para que el isEnabled tenga en cuenta que las capas esten 'avialable'
 *
 * Revision 1.2  2006/07/27 06:20:28  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/07/26 09:53:05  jaume
 * separadas las herramientas de selecci�n del viewControls al ViewSelectionControls
 *
 *
 */
package com.iver.cit.gvsig;

import geomatico.events.EventBus;
import geomatico.events.ExceptionEvent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.layer.filter.AndLayerFilter;
import org.gvsig.layer.filter.LayerFilter;
import org.gvsig.map.MapContext;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.identity.FeatureId;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.gui.selectionByTheme.DefaultSelectionByThemeModel;
import com.iver.cit.gvsig.gui.selectionByTheme.MySelectionByThemeListener;
import com.iver.cit.gvsig.gui.selectionByTheme.SelectionByTheme;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.IProjectView;
import com.iver.cit.gvsig.project.documents.view.gui.View;

/**
 * Extension that handles the selection tools, selection tools have sense on
 * vectorial layers only.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class ViewSelectionControls extends Extension {
	private static Logger logger = Logger.getLogger(ViewControls.class
			.getName());

	public void initialize() {
		// TODO Auto-generated method stub
		registerIcons();
	}

	private void registerIcons() {

		PluginServices.getIconTheme().registerDefault(
				"view-select-geometry",
				this.getClass().getClassLoader()
						.getResource("images/Select.png"));

		PluginServices.getIconTheme().registerDefault(
				"view-select-by-rectangle",
				this.getClass().getClassLoader()
						.getResource("images/SelEspacial2b.png"));

		PluginServices.getIconTheme().registerDefault(
				"view-select-by-polygon",
				this.getClass().getClassLoader()
						.getResource("images/SelectByPolygon.png"));

		PluginServices.getIconTheme().registerDefault(
				"view-select-invert",
				this.getClass().getClassLoader()
						.getResource("images/invertSelection.png"));
	}

	public void execute(String actionCommand) {
		View vista = (View) PluginServices.getMDIManager().getActiveWindow();
		IProjectView model = vista.getModel();
		MapContext mapa = model.getMapContext();
		MapControl mapCtrl = vista.getMapControl();
		logger.debug("Comand : " + actionCommand);
		if (actionCommand.equals("SELRECT")) {
			mapCtrl.setTool("rectSelection");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (actionCommand.equals("SELPOINT")) {
			mapCtrl.setTool("pointSelection");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (actionCommand.equals("SELPOL")) {
			mapCtrl.setTool("polSelection");
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (actionCommand.equals("SELECTIONBYSHAPE")) {
			SelectionByTheme dlg = new SelectionByTheme();
			// FLayer[] layers = mapa.getLayers().getActives();
			// int count = 0;
			dlg.setModel(new DefaultSelectionByThemeModel());
			dlg.addSelectionListener(new MySelectionByThemeListener());
			PluginServices.getMDIManager().addWindow(dlg);
			((ProjectDocument) vista.getModel()).setModified(true);
		} else if (actionCommand.equals("INVERT_SELECTION")) {
			Layer[] selectedFeatureLayers = mapa.getRootLayer().filter(
					new AndLayerFilter(LayerFilter.FEATURE,
							LayerFilter.SELECTED));
			for (Layer layer : selectedFeatureLayers) {
				try {
					SimpleFeatureCollection collection = layer
							.getFeatureSource().getFeatures();
					SimpleFeatureIterator iterator = collection.features();
					Set<FeatureId> currentSelection = layer.getSelection();
					Set<FeatureId> newSelection = new HashSet<FeatureId>();
					while (iterator.hasNext()) {
						SimpleFeature feature = iterator.next();
						FeatureId featureId = feature.getIdentifier();
						if (!currentSelection.contains(featureId)) {
							newSelection.add(featureId);
						}
					}
					layer.setSelection(newSelection);
					iterator.close();
				} catch (IOException e) {
					EventBus eventBus = InjectorSingleton.getInjector()
							.getInstance(EventBus.class);
					eventBus.fireEvent(new ExceptionEvent(
							"Cannot process layer: " + layer, e));
				}
			}
			((ProjectDocument) vista.getModel()).setModified(true);
		}
	}

	public boolean isEnabled() {
		IWindow f = PluginServices.getMDIManager().getActiveWindow();
		if (f instanceof View) {

			View vista = (View) f;
			IProjectView model = vista.getModel();
			MapContext mapa = model.getMapContext();

			return mapa.getRootLayer().filter(LayerFilter.FEATURE).length > 0;
		}
		return false;
	}

	public boolean isVisible() {
		return isEnabled();
	}
}
