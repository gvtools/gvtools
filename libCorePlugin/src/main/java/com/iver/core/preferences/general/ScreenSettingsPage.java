package com.iver.core.preferences.general;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.gvsig.gui.beans.swing.JBlank;
import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;

/**
 * Page to calculate correctly all the scales of screen. In it we introduce the
 * values of our screen just as we see it.
 * 
 * @author Vicente Caballero Navarro
 */
public class ScreenSettingsPage extends AbstractPreferencePage {
	protected static String id = ScreenSettingsPage.class.getName();
	private static final long serialVersionUID = 6012279465073443753L;
	private static final double MILLIMETERS_PER_INCH = 2.54;
	private static Preferences prefs = Preferences.userRoot().node(
			"gvsig.configuration.screen");
	private ImageIcon icon;
	private JPanel pTestMeasure;
	private JTextField txtResolution;
	private JTextField txtMeasure;
	private JComboBox cmbUnits;
	private JButton btnRefresh;

	public ScreenSettingsPage() {
		super();
		setParentID(GeneralPage.id);
		icon = PluginServices.getIconTheme().get(
				"aplication-preferences-screensetting");
		addComponent(PluginServices.getText(this, "resolution") + ":",
				txtResolution = new JTextField("", 15));

		pTestMeasure = new TestMeasurePanel();
		addComponent(pTestMeasure);
		addComponent(new JLabel(PluginServices.getText(this,
				"the_length_of_the_line_above_is") + ":"));
		cmbUnits = new JComboBox();
		cmbUnits.addItem(PluginServices.getText(this, "centimeters"));
		cmbUnits.addItem(PluginServices.getText(this, "inches"));
		cmbUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * double
				 * d=Double.parseDouble(txtMeasure.getText().replace(',','.'));
				 * if (cmbUnits.getSelectedIndex()==0) {
				 * txtResolution.setText(String
				 * .valueOf((int)((210*MILLIMETERS_PER_INCH)/d))); }else {
				 * txtResolution.setText(String.valueOf((int)(210/d))); }
				 */
			}
		});
		txtMeasure = new JTextField();
		addComponent(txtMeasure, cmbUnits);

		addComponent(new JBlank(1, 1));

		btnRefresh = new JButton(PluginServices.getText(this,
				"button.resolution.calculate"));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				double d = Double.parseDouble(txtMeasure.getText().replace(',',
						'.'));
				if (cmbUnits.getSelectedIndex() == 0) {
					txtResolution.setText(String
							.valueOf((int) ((210 * MILLIMETERS_PER_INCH) / d)));
				} else {
					txtResolution.setText(String.valueOf((int) (210 / d)));
				}

			}

		});
		addComponent(btnRefresh);

		initialize();
	}

	private class TestMeasurePanel extends JPanel {

		private static final long serialVersionUID = -8307475893309753439L;

		public TestMeasurePanel() {
			setPreferredSize(new Dimension(250, 60));
			Border border = BorderFactory.createTitledBorder(PluginServices
					.getText(this, "test_measure"));
			setBorder(border);
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.setColor(Color.black);
			g.drawLine(20, 30, 230, 30);
			g.drawLine(20, 20, 20, 40);
			g.drawLine(230, 20, 230, 40);
		}

	}

	private void initialize() {
		// this.setSize(394, 248);
	}

	public void storeValues() throws StoreException {
		int dpi = Integer.parseInt(txtResolution.getText());
		prefs.putInt("dpi", dpi);
	}

	public void setChangesApplied() {
		setChanged(false);
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return PluginServices.getText(this, "options.configuration.screen");
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		double dpi = kit.getScreenResolution();
		int resDPI = prefs.getInt("dpi", (int) dpi);

		txtResolution.setText(String.valueOf(resDPI));
		txtMeasure.setText(String.valueOf(format(210 * MILLIMETERS_PER_INCH
				/ resDPI)));
		cmbUnits.setSelectedIndex(0);
	}

	public void initializeDefaults() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		int dpi = kit.getScreenResolution();
		txtResolution.setText(String.valueOf(dpi));
		txtMeasure.setText(String.valueOf(format(210 * MILLIMETERS_PER_INCH
				/ dpi)));
		cmbUnits.setSelectedIndex(0);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public boolean isValueChanged() {
		return super.hasChanged();
	}

	private String format(double d) {
		NumberFormat nf = NumberFormat.getInstance();

		if ((d % (long) d) != 0) {
			nf.setMaximumFractionDigits(2);
		} else {
			nf.setMaximumFractionDigits(0);
		}

		return nf.format(d); // (Double.valueOf(s).doubleValue());
	}
} // @jve:decl-index=0:visual-constraint="10,10"
