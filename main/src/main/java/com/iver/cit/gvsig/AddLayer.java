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
 */
package com.iver.cit.gvsig;

import geomatico.events.EventBus;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.gvsig.events.LayerAddedEvent;
import org.gvsig.events.LayerCreationErrorEvent;
import org.gvsig.events.LayerCreationErrorHandler;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.addlayer.AddFileLayerWizard;
import com.iver.cit.gvsig.addlayer.AddLayerDialog;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.gui.AddLayerWizard;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.view.gui.BaseView;

/**
 * Extensi�n que abre un di�logo para seleccionar la capa o capas que se quieren
 * a�adir a la vista.
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class AddLayer extends Extension {
	private static final Logger logger = Logger.getLogger(AddLayer.class);

	private static List<Class<? extends AddLayerWizard>> wizards = new ArrayList<Class<? extends AddLayerWizard>>();

	public static void addWizard(Class<? extends AddLayerWizard> wizard) {
		wizards.add(wizard);
	}

	public static AddLayerWizard getInstance(int i)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		AddLayerWizard wizard = InjectorSingleton.getInjector().getInstance(
				wizards.get(i));
		wizard.initWizard();
		return wizard;
	}

	@Override
	public boolean isVisible() {
		IWindow window = PluginServices.getMDIManager().getActiveWindow();
		// Any view derived from BaseView should have AddLayer available
		return window instanceof BaseView;
	}

	@Override
	public void execute(String actionCommand) {
		BaseView view = (BaseView) PluginServices.getMDIManager()
				.getActiveWindow();
		MapControl mapControl = view.getMapControl();
		addLayers(mapControl);
		((ProjectDocument) view.getModel()).setModified(true);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Creates FOpenDialog, and adds file tab, and additional registered tabs
	 * 
	 * @return FOpenDialog
	 */
	private AddLayerDialog createOpenDialog() {
		AddLayerDialog dialog = new AddLayerDialog();

		// after that, all registerez tabs (wizardpanels implementations)
		for (int i = 0; i < wizards.size(); i++) {
			AddLayerWizard wp;
			try {
				wp = AddLayer.getInstance(i);
				dialog.addWizardTab(wp.getTabName(), wp);
			} catch (Exception e) {
				logger.error("Cannot add wizard tab to dialog", e);
			}
		}
		return dialog;
	}

	public boolean addLayers(MapControl mapControl) {
		AddLayerDialog dialog = createOpenDialog();
		PluginServices.getMDIManager().addWindow(dialog);

		if (!dialog.isAccepted()) {
			return false;
		}

		AddLayerWizard wizard = dialog.getSelectedTab();
		if (wizard == null) {
			JOptionPane.showMessageDialog(
					(Component) PluginServices.getMainFrame(),
					PluginServices.getText(this, "ninguna_capa_seleccionada"));
			return false;
		}

		wizard.setMapCtrl(mapControl);
		wizard.execute();
		Layer[] layers = wizard.getLayers();
		Layer root = mapControl.getMapContext().getRootLayer();
		for (Layer layer : layers) {
			layer.setVisible(true);
			root.addLayer(layer);
			EventBus.getInstance().fireEvent(new LayerAddedEvent(layer));
		}
		return layers.length > 0;
	}

	@Override
	public void initialize() {
		PluginServices.getIconTheme().registerDefault(
				"layer-add",
				this.getClass().getClassLoader()
						.getResource("images/addlayer.png"));

		EventBus.getInstance().addHandler(LayerCreationErrorEvent.class,
				new LayerCreationErrorHandler() {
					@Override
					public void error(String message, Throwable cause) {
						JOptionPane.showMessageDialog(
								(Component) PluginServices.getMainFrame(),
								PluginServices.getText(this, "fallo_capas")
										+ " : \n" + message);
					}
				});

		// Add wizards
		addWizard(AddFileLayerWizard.class);
	}
}
