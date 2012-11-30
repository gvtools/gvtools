package com.iver.andami.authentication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.gvsig.gui.beans.swing.GridBagLayoutPanel;
import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;

/**
 * Form to let the user log in the system.
 * 
 * @author laura
 * 
 */
public class LoginUI extends JDialog {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField serverURLField;
	private JLabel invalidLoginLabel;
	private JButton okButton;
	private JButton exitButton;
	private GridBagLayoutPanel mainPanel;
	private JPanel buttonPanel;
	private boolean isFirstLogin = true;

	// TODO:
	// Cambiar esto para que coja un icono de una ruta que se lee de un xml

	static private ImageIcon gvsigIcon = PluginServices.getIconTheme().get(
			"login-gvsig");

	IAuthentication authentication;

	public LoginUI(IAuthentication authentication) {
		Container container = getContentPane();
		mainPanel = new GridBagLayoutPanel();
		usernameField = new JTextField(25);
		passwordField = new JPasswordField(25);
		serverURLField = new JTextField(25);

		String server_text = authentication.get("server_url");
		if (server_text != null) {
			serverURLField.setText(server_text);
		}

		buttonPanel = new JPanel();
		okButton = new JButton(PluginServices.getText(this, "login_ok"));
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				OK();
			}
		});
		exitButton = new JButton(PluginServices.getText(this, "login_exit"));
		exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Cancel();
			}
		});

		buttonPanel.add(okButton);
		buttonPanel.add(exitButton);

		JPanel logoPanel = new JPanel();
		logoPanel.add(new JLabel(gvsigIcon));
		logoPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 10));
		container.add(logoPanel, BorderLayout.WEST);
		container.add(mainPanel, BorderLayout.CENTER);
		mainPanel.addComponent(PluginServices.getText(this, "login_name"),
				usernameField);
		mainPanel.addComponent(PluginServices.getText(this, "login_password"),
				passwordField);
		mainPanel.addComponent(PluginServices.getText(this, "login_name"),
				serverURLField);
		invalidLoginLabel = new JLabel(PluginServices.getText(this,
				"login_invalid_user"));
		invalidLoginLabel.setForeground(Color.RED);
		invalidLoginLabel.setVisible(false);
		mainPanel.addComponent(invalidLoginLabel, buttonPanel);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.authentication = authentication;
		this.setBounds(400, 400, this.getWidth(), this.getHeight());
		this.setTitle("Login");
		this.setModal(true);
		this.setSize(400, 200);
		this.setResizable(false);
		pack();
		setVisible(true);
	}

	public void OK() {
		if (serverURLField.getText() == null
				|| serverURLField.getText().length() == 0) {
			return;
		}

		authentication.put("user", usernameField.getText());
		authentication.put("pwd", new String(passwordField.getPassword()));
		authentication.put("server", serverURLField.getText());

		if (authentication.isValidUser()) {
			authentication.setLogged(true);
			dispose();
		} else {
			usernameField.setText("");
			passwordField.setText("");
			if (isFirstLogin) {
				invalidLoginLabel.setVisible(true);
				isFirstLogin = false;
			}
			// JOptionPane.showMessageDialog((Component)PluginServices.getMainFrame(),"Invalid user/password");
		}
	}

	public void Cancel() {
		authentication.put("user", "");
		authentication.put("pwd", "");
		authentication.setLogged(false);
		dispose();
	}

}
