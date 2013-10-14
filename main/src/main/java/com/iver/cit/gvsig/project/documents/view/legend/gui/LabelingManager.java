/*
 * Created on 01-jun-2004
 *
 */
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
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;

/**
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class LabelingManager extends AbstractThemeManagerPage implements
		ActionListener {
	private static final long serialVersionUID = 856162295985695717L;
	// TODO gtintegration
	// private static ArrayList<Class<? extends ILabelingStrategyPanel>>
	// installedPanels = new ArrayList<Class<? extends
	// ILabelingStrategyPanel>>();
	// private Comparator<Class<?>> comparator = new Comparator<Class<?>>() {
	//
	// public int compare(Class<?> o1, Class<?> o2) {
	// return o1.getName().compareTo(o2.getName());
	// }
	//
	// };
	// private TreeMap<Class<?>, ILabelingStrategyPanel> strategyPanels = new
	// TreeMap<Class<?>, ILabelingStrategyPanel>(
	// comparator);
	// private JComboBox cmbStrategy;
	private JCheckBox chkApplyLabels;
	private Layer layer;
	private JPanel content;
	private JPanel pnlNorth;
	private AttrInTableLabeling labelingPanel;

	public LabelingManager() {
		super();
		initialize();
	}

	// TODO gtintegration
	// private class LabelingStrategyItem {
	// private ILabelingStrategyPanel strategyPanel;
	//
	// private LabelingStrategyItem(ILabelingStrategyPanel strategyPanel) {
	// this.strategyPanel = strategyPanel;
	// }
	//
	// public String toString() {
	// return strategyPanel.getLabelingStrategyName();
	// }
	//
	// public boolean equals(Object obj) {
	// if (obj instanceof LabelingStrategyItem) {
	// LabelingStrategyItem item = (LabelingStrategyItem) obj;
	// return this.strategyPanel.getClass().equals(
	// item.strategyPanel.getClass());
	//
	// }
	// return super.equals(obj);
	// }
	//
	// }

	private void initialize() {
		setLayout(new BorderLayout());

		// TODO gtintegration
		// for (Iterator<Class<? extends ILabelingStrategyPanel>> it =
		// installedPanels
		// .iterator(); it.hasNext();) {
		// try {
		// ILabelingStrategyPanel pnl = (ILabelingStrategyPanel) it.next()
		// .newInstance();
		// strategyPanels.put(pnl.getLabelingStrategyClass(), pnl);
		// } catch (Exception e) {
		// /*
		// * can't happen this should never happen since instantiation and
		// * access exceptions have been controlled in the
		// * addLabelingStrategy method
		// */
		// NotificationManager.addError(e);
		// }
		// }
		content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEtchedBorder());
		add(getPnlNorth(), BorderLayout.NORTH);
		labelingPanel = InjectorSingleton.getInjector().getInstance(
				AttrInTableLabeling.class);
		content.add(labelingPanel, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);

	}

	private JPanel getPnlNorth() {
		if (pnlNorth == null) {
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			JPanel aux = new JPanel(new FlowLayout(FlowLayout.LEFT));

			aux.add(getChkApplyLabels());
			pnlNorth.add(aux, BorderLayout.NORTH);
			aux = new JPanel(new FlowLayout(FlowLayout.LEFT));
			// TODO gtintegration
			// aux.add(new JLabel(PluginServices.getText(this, "general") +
			// ":"));
			// aux.add(getCmbStrategy());
			pnlNorth.add(aux, BorderLayout.CENTER);

		}
		return pnlNorth;
	}

	// TODO gtintegration
	// private JComboBox getCmbStrategy() {
	// if (cmbStrategy == null) {
	// Iterator<ILabelingStrategyPanel> it = strategyPanels.values()
	// .iterator();
	// final ArrayList<LabelingStrategyItem> aux = new
	// ArrayList<LabelingStrategyItem>();
	// while (it.hasNext()) {
	// aux.add(new LabelingStrategyItem(it.next()));
	// }
	// final LabelingStrategyItem items[] = aux
	// .toArray(new LabelingStrategyItem[aux.size()]);
	//
	// cmbStrategy = new JComboBox(items) {
	// private static final long serialVersionUID = 7506754097091500846L;
	//
	// @Override
	// public void setSelectedItem(Object anObject) {
	// if (anObject == null)
	// return;
	// if (anObject instanceof ILabelingStrategy) {
	// ILabelingStrategy st = (ILabelingStrategy) anObject;
	// for (ILabelingStrategyPanel pnl : strategyPanels
	// .values()) {
	// if (pnl.getLabelingStrategyClass() != null
	// && pnl.getLabelingStrategyClass().equals(
	// st.getClass())) {
	// super.setSelectedItem(new LabelingStrategyItem(
	// pnl));
	// return;
	// }
	// }
	// } else {
	// super.setSelectedItem(anObject);
	// }
	// }
	// };
	//
	// cmbStrategy.setName("CMBMODE");
	// cmbStrategy.addActionListener(this);
	// }
	//
	// return cmbStrategy;
	// }

	private JCheckBox getChkApplyLabels() {
		if (chkApplyLabels == null) {
			chkApplyLabels = new JCheckBox(PluginServices.getText(this,
					"enable_labeling"));
			chkApplyLabels.setName("CHKAPPLYLABELS");
			chkApplyLabels.addActionListener(this);
		}
		return chkApplyLabels;
	}

	// TODO gtintegration
	// public static void addLabelingStrategy(
	// Class<? extends ILabelingStrategyPanel> iLabelingStrategyPanelClass) {
	// installedPanels.add(iLabelingStrategyPanelClass);
	// }

	private void setComponentEnabled(Component c, boolean b) {
		c.setEnabled(b);
	}

	public void setModel(Layer layer) throws IOException {
		// get the labeling strategy
		this.layer = layer;
		labelingPanel.setModel(layer);

		setComponentEnabled(this, true);
		refreshControls();

		ActionEvent evt = new ActionEvent(chkApplyLabels, 0, null);
		evt.setSource(chkApplyLabels);
		actionPerformed(evt);

		// TODO gtintegration
		// cmbStrategy.setSelectedItem(this.layer.getLabelingStrategy());
		//
		// evt.setSource(getCmbStrategy());
		// actionPerformed(evt);
	}

	private void refreshControls() {
		if (layer == null)
			return;

		// enables labeling
		JCheckBox applyLabels = getChkApplyLabels();
		applyLabels.setSelected(layer.getLegend().getLabeling() != null);
	}

	public void actionPerformed(ActionEvent e) {
		JComponent c = (JComponent) e.getSource();

		if (c.equals(chkApplyLabels)) {
			boolean b = chkApplyLabels.isSelected();
			// enables/disables all components
			// getCmbStrategy().setEnabled(b);
			for (int i = 0; i < content.getComponentCount(); i++) {
				Component c1 = content.getComponent(i);
				if (!c1.equals(c))
					setComponentEnabled(c1, b);
			}
			// TODO gtintegration
			// } else if (c.equals(cmbStrategy)) {
			// ILabelingStrategyPanel panel = ((LabelingStrategyItem)
			// cmbStrategy
			// .getSelectedItem()).strategyPanel;
			// if (panel != null) {
			// try {
			// remove(content);
			// content.removeAll();
			// content.add((Component) panel);
			// add(content, BorderLayout.CENTER);
			// actionPerformed(new ActionEvent(chkApplyLabels, 0, null));
			// revalidate();
			// paintImmediately(getBounds());
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			// }
		}
	}

	public void acceptAction() {

	}

	public void cancelAction() {

	}

	public void applyAction() {
		if (getChkApplyLabels().isSelected()) {
			layer.getLegend().setLabeling(labelingPanel.getLabelingStyle());
		} else {
			layer.getLegend().setLabeling(null);
		}
	}

	public String getName() {
		return PluginServices.getText(this, "Etiquetados");
	}

	@Override
	public boolean accepts(Layer layer) {
		return layer.hasFeatures();
	}
}
