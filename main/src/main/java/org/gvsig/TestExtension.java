package org.gvsig;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.Source;
import org.gvsig.layer.SourceFactory;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.project.documents.view.gui.BaseView;

public class TestExtension extends Extension {

	@Inject
	private SourceFactory sourceFactory;
	@Inject
	private LayerFactory layerFactory;

	@Override
	public void initialize() {
		InjectorSingleton.getInjector().injectMembers(this);
	}

	@Override
	public void execute(String actionCommand) {
		Map<String, String> properties = new HashMap<String, String>();
		// properties.put("url", "file:///home/fergonco/carto/ggl/paises.shp");
		properties.put("url", "file:///home/fergonco/carto/vias.shp");
		// properties.put("url", "file:///home/victorzinho/workspace/"
		// + "cursos/gvsig_2012/datos/vias.shp");
		Source source = sourceFactory.createSource(properties);
		Layer testLayer = layerFactory.createLayer(source);
		InjectorSingleton.getInjector().injectMembers(testLayer);

		com.iver.andami.ui.mdiManager.IWindow frame = PluginServices
				.getMDIManager().getActiveWindow();

		if (frame instanceof BaseView) {
			MapContext mapContext = ((BaseView) frame).getModel()
					.getMapContext();
			mapContext.getRootLayer().addLayer(testLayer);
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}
