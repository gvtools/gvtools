package com.iver.cit.gvsig.addlayer;

import geomatico.events.EventBus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.gvsig.events.LayerCreationErrorEvent;
import org.gvsig.layer.Layer;
import org.gvsig.layer.LayerFactory;
import org.gvsig.layer.SourceFactory;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.gui.AddLayerWizard;
import com.iver.cit.gvsig.gui.panels.CRSSelectPanel;

public class AddFileLayerWizard extends AddLayerWizard {
	private static final Logger logger = Logger
			.getLogger(AddFileLayerWizard.class);

	private static String lastPath;

	@Inject
	private SourceFactory sourceFactory;
	@Inject
	private LayerFactory layerFactory;

	private AddFileLayerWizardModel model;
	private JList<String> list;
	private JButton add, remove, up, down;

	@Override
	public void initWizard() {
		// Center list panel
		JPanel listPanel = new JPanel(new BorderLayout());
		model = new AddFileLayerWizardModel();
		list = new JList<String>(model);

		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		add = new JButton(PluginServices.getText(this, "Anadir"));
		remove = new JButton(PluginServices.getText(this, "Eliminar"));
		remove.setEnabled(false);
		up = new JButton(PluginServices.getText(this, "arriba"));
		up.setEnabled(false);
		down = new JButton(PluginServices.getText(this, "abajo"));
		down.setEnabled(false);
		buttonsPanel.add(add, c);
		c.gridy = 1;
		buttonsPanel.add(remove, c);
		c.gridy = 2;
		buttonsPanel.add(up, c);
		c.gridy = 3;
		buttonsPanel.add(down, c);
		c.gridy = 4;
		c.weighty = 1;
		buttonsPanel.add(new JPanel(), c);

		listPanel.add(new JScrollPane(list), BorderLayout.CENTER);
		listPanel.add(buttonsPanel, BorderLayout.EAST);

		// Projection panel
		final CRSSelectPanel projectionPanel = CRSSelectPanel
				.getPanel(AddLayerDialog.getLastCrs());
		projectionPanel.setTransPanelActive(true);
		projectionPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (projectionPanel.isOkPressed()) {
					AddLayerDialog.setLastCrs(projectionPanel.getCurrentCrs());
				}
			}
		});

		// Put together
		setTabName(PluginServices.getText(this, "Fichero"));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(PluginServices.getText(this,
				"Capas")));
		setPreferredSize(new Dimension(500, 280));
		add(projectionPanel, BorderLayout.SOUTH);
		add(listPanel, BorderLayout.CENTER);

		// Add listeners
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				model.setSelected(list.getSelectedIndices());
				updateButtons();
			}
		});
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.remove();
				list.setSelectedIndex(-1);
			}
		});
		up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.up();
				list.setSelectedIndex(list.getSelectedIndex() - 1);
			}
		});
		down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.down();
				list.setSelectedIndex(list.getSelectedIndex() + 1);
			}
		});
	}

	private void updateButtons() {
		remove.setEnabled(model.isRemoveEnabled());
		up.setEnabled(model.isUpEnabled());
		down.setEnabled(model.isDownEnabled());
	}

	private void add() {
		JFileChooser fileChooser = new JFileChooser(lastPath);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(null);

		int result = fileChooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		lastPath = fileChooser.getCurrentDirectory().getAbsolutePath();
		File[] files = fileChooser.getSelectedFiles();
		List<String> errorLayers = new ArrayList<String>();
		for (File file : files) {
			String name = file.getName();
			try {
				Map<String, String> properties = new HashMap<String, String>();
				String url = file.toURI().toURL().toExternalForm();
				properties.put("url", url);
				Layer layer = layerFactory.createLayer(file.getName(),
						sourceFactory.createSource(properties));
				model.add(name, layer);
			} catch (Exception e) {
				logger.error("Cannot create layer", e);
				errorLayers.add(name);
			}
		}

		if (errorLayers.size() > 0) {
			String[] layerNames = errorLayers.toArray(new String[errorLayers
					.size()]);
			EventBus.getInstance().fireEvent(
					new LayerCreationErrorEvent(layerNames));
		}

		callStateChanged(model.getSize() > 0);
		updateButtons();
		list.invalidate();
	}

	@Override
	public void execute() {
		// do nothing
	}

	@Override
	public Layer[] getLayers() throws IllegalStateException {
		return model.getLayers().toArray(new Layer[0]);
	}
}
