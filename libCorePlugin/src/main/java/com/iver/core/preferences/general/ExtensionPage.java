package com.iver.core.preferences.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.plugins.config.generate.Extension;
import com.iver.andami.plugins.config.generate.PluginConfig;
import com.iver.andami.preferences.AbstractPreferencePage;
import com.iver.andami.preferences.StoreException;

public class ExtensionPage extends AbstractPreferencePage {
	private Extension extension;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JTextField jTextField = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JCheckBox chbActivar = null;
	private ImageIcon icon;
	private boolean chkSelected;
	private int txtPriority;
	private boolean changed = false;
	private MyAction myAction = new MyAction();

	/**
	 * This is the default constructor
	 */
	public ExtensionPage(Extension extension) {
		super();
		icon = PluginServices.getIconTheme().get("emblem-work");
		this.extension = extension;
		setParentID(ExtensionsPage.class.getName());
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(451, 234);
		this.add(getJPanel(), java.awt.BorderLayout.NORTH);
		this.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		this.add(getJPanel2(), java.awt.BorderLayout.SOUTH);

	}

	public String getID() {
		return extension.getClassName();
	}

	public String getTitle() {
		return extension.getClassName();
	}

	public JPanel getPanel() {
		return this;
	}

	public void initializeValues() {
		getChbActivar().setSelected(
				((Extension) Launcher.getExtension(extension.getClassName()))
						.getActive());
		getJTextField().setText(String.valueOf(extension.getPriority()));
		getJTextArea().setText(
				format(((Extension) Launcher.getExtension(extension
						.getClassName())).getDescription(), 40));
		chkSelected = getChbActivar().isSelected();
		txtPriority = extension.getPriority();
	}

	public void storeValues() throws StoreException {
		int pri;
		try {
			pri = Integer.parseInt(getJTextField().getText());
		} catch (Exception e) {
			throw new StoreException(PluginServices.getText(this,
					"invalid_priority_value"), e);
		}
		extension.setActive(chbActivar.isSelected());
		extension.setPriority(pri);
		// Se escribe el config de los plugins
		marshalPlugins();
	}

	/**
	 * Escribe sobre el config.xml, la nueva configuración.
	 */
	public void marshalPlugins() {
		HashMap pc = Launcher.getPluginConfig();
		Iterator iter = pc.keySet().iterator();

		while (iter.hasNext()) {
			Object obj = iter.next();
			PluginConfig pconfig = (PluginConfig) pc.get(obj);
			Writer writer;

			try {
				FileOutputStream fos = new FileOutputStream(Launcher
						.getAndamiConfig().getPluginsDirectory()
						+ File.separator
						+ (String) obj
						+ File.separator
						+ "config.xml");
				// castor uses xerces, and xerces uses UTF-8 by default, so we
				// should use
				// UTF-8 to create the writer, as long as we continue using
				// castor+xerces
				writer = new BufferedWriter(
						new OutputStreamWriter(fos, "UTF-8"));

				try {
					pconfig.marshal(writer);
				} catch (MarshalException e1) {
					e1.printStackTrace();
				} catch (ValidationException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// hay que refrescar la aplicación
			// /((App)App.instance).run();
		}
	}

	/**
	 * Lee del config.xml la configuración.
	 */
	public void unmarshalPlugins() {
		HashMap<String, PluginConfig> pc = Launcher.getPluginConfig();
		Iterator<String> iter = pc.keySet().iterator();

		while (iter.hasNext()) {
			String obj = iter.next();
			PluginConfig pconfig = pc.get(obj);

			try {
				String fileName = Launcher.getAndamiConfig()
						.getPluginsDirectory()
						+ File.separator
						+ obj
						+ File.separator + "config.xml";
				FileInputStream is = new FileInputStream(fileName);
				Reader reader = com.iver.utiles.xml.XMLEncodingUtils
						.getReader(is);
				if (reader == null) {
					// the encoding was not correctly detected, use system
					// default
					reader = new FileReader(fileName);
				} else {
					// use a buffered reader to improve performance
					reader = new BufferedReader(reader);
				}
				pconfig = (PluginConfig) PluginConfig.unmarshal(reader);
				Launcher.getPluginConfig().put(obj, pconfig);
			} catch (Exception e) {
				System.out.println("Exception unmarshalPlugin " + e);
			}
		}

		// hay que refrescar la aplicación
		// /((App)App.instance).run();
	}

	public void initializeDefaults() {
		unmarshalPlugins();
		getChbActivar().setSelected(
				((Extension) Launcher.getExtension(extension.getClassName()))
						.getActive());
		getJTextField().setText(String.valueOf(extension.getPriority()));
		getJTextArea().setText(
				format(((Extension) Launcher.getExtension(extension
						.getClassName())).getDescription(), 40));
	}

	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					PluginServices.getText(this, "descripcion"),
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel.add(getJScrollPane(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEnabled(false);
			jTextArea.setText("");
			jTextArea.setEditable(false);
			jTextArea.setLineWrap(true);
			jTextArea.setWrapStyleWord(true);
			jTextArea.setPreferredSize(new Dimension(320, 170));
			jTextArea.addKeyListener(myAction);
		}
		return jTextArea;
	}

	/**
	 * This method initializes chbActivar
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getChbActivar() {
		if (chbActivar == null) {
			chbActivar = new JCheckBox();
			chbActivar.setSelected(true);
			chbActivar.setText(PluginServices.getText(this,
					"extension_activada"));
			chbActivar.addActionListener(myAction);
		}

		return chbActivar;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText(PluginServices.getText(this, "prioridad"));
			jPanel1 = new JPanel();
			jPanel1.add(getChbActivar(), null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(jLabel, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText(PluginServices
					.getText(
							this,
							"Los_cambios_efectuados_sobre_estos_valores_se_aplicaran_al_reiniciar_la_aplicacion"));
			jPanel2 = new JPanel();
			jPanel2.add(jLabel1, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(40, 20));
			jTextField.addKeyListener(myAction);
		}
		return jTextField;
	}

	/**
	 * Cuts the message text to force its lines to be shorter or equal to
	 * lineLength.
	 * 
	 * @param message
	 *            , the message.
	 * @param lineLength
	 *            , the max line length in number of characters.
	 * @return the formated message.
	 */
	private static String format(String message, int lineLength) {
		if (message == null)
			return "";
		if (message.length() <= lineLength)
			return message;
		String[] lines = message.split(" ");
		String theMessage = "";
		String line = "";
		int i = 0;
		while (i < lines.length) {
			while (i < lines.length && lineLength > line.length()) {
				line = line.concat(lines[i] + " ");
				i++;
			}
			theMessage = theMessage.concat(line + "\n");
			line = "";
		}
		return theMessage;
	}

	private class MyAction implements ActionListener, KeyListener {
		public void actionPerformed(ActionEvent e) {
			changed = true;
			System.out.println("actionperformed");
		}

		public void keyPressed(KeyEvent e) {
			changed = true;
			System.out.println("keypressed");
		}

		public void keyReleased(KeyEvent e) {
			changed = true;
			System.out.println("keyreleased");
		}

		public void keyTyped(KeyEvent e) {
			changed = true;
			System.out.println("keytyped");
		}
	}

	public boolean isValueChanged() {
		return changed;
	}

	public void setChangesApplied() {
		changed = false;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
