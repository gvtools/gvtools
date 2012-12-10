package com.iver.cit.gvsig.addlayer;

import geomatico.events.EventBus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.gvsig.events.LayerCreationErrorEvent;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.SourceFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.AddLayerWizard;
import com.iver.cit.gvsig.gui.panels.CRSSelectPanel;
import com.iver.cit.gvsig.project.documents.gui.ListManagerSkin;
import com.iver.utiles.listManager.ListManagerListener;

public class AddFileLayerWizard extends AddLayerWizard implements
		ListManagerListener {
	private static final Logger logger = Logger
			.getLogger(AddFileLayerWizard.class);

	private static String lastPath;

	@Inject
	private SourceFactory sourceFactory;
	@Inject
	private LayerFactory layerFactory;
	@Inject
	private EventBus eventBus;

	private Layer[] layers = null;

	private ListManagerSkin listManagerSkin;

	@Override
	public void initWizard() {
		setTabName(PluginServices.getText(this, "Fichero"));
		setSize(514, 280);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(PluginServices
				.getText(this, "Capas")));
		panel.setPreferredSize(new Dimension(380, 200));
		panel.setBounds(2, 2, 506, 472);

		CoordinateReferenceSystem crs = AddLayerDialog.getLastCrs();
		final CRSSelectPanel projectionPanel = CRSSelectPanel.getPanel(crs);
		projectionPanel.setTransPanelActive(true);
		projectionPanel.setBounds(11, 400, 448, 35);
		projectionPanel.setPreferredSize(new Dimension(448, 35));
		projectionPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (projectionPanel.isOkPressed()) {
					AddLayerDialog.setLastCrs(projectionPanel.getCurrentCrs());
				}
			}
		});

		panel.add(projectionPanel, null);

		listManagerSkin = new ListManagerSkin(false);
		listManagerSkin.setBounds(11, 21, 491, 363);
		listManagerSkin.getListManager().setListener(this);
		panel.add(listManagerSkin, null);
		add(panel, null);
	}

	@Override
	public Object[] addObjects() {
		callStateChanged(true);

		JFileChooser fileChooser = new JFileChooser(lastPath);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(null);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			lastPath = fileChooser.getCurrentDirectory().getAbsolutePath();
			return fileChooser.getSelectedFiles();
		} else {
			return new Object[0];
		}
	}

	@Override
	public Object getProperties(Object arg0) {
		return null;
	}

	@Override
	public void execute() {
		List<Layer> layerList = new ArrayList<Layer>();
		Object[] files = listManagerSkin.getListManager().getListModel()
				.getObjects().toArray();
		for (Object object : files) {
			try {
				// We are forced to do this dirty cast due to the ListManager
				// implementation
				File file = (File) object;
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("url", file.toURI().toURL().toExternalForm());
				layerList.add(layerFactory.createLayer(sourceFactory
						.createSource(properties)));
			} catch (Exception e) {
				logger.error("Cannot create layer", e);
				eventBus.fireEvent(new LayerCreationErrorEvent(
						"Cannot create layer", e));
			}
		}
		layers = layerList.toArray(new Layer[layerList.size()]);
	}

	@Override
	public Layer[] getLayers() throws IllegalStateException {
		if (layers == null) {
			throw new IllegalStateException();
		}
		return layers;
	}
}
