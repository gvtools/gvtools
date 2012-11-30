package com.iver.andami.ui.wizard;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jwizardcomponent.CancelAction;
import jwizardcomponent.DefaultJWizardComponents;
import jwizardcomponent.FinishAction;
import jwizardcomponent.common.SimpleButtonPanel;

public class WizardPanelWithLogo extends JPanel {
	DefaultJWizardComponents wizardComponents;

	JPanel buttonPanel;
	JLabel statusLabel = new JLabel();

	ImageIcon logo;

	public WizardPanelWithLogo(ImageIcon logo) {
		this.logo = logo;
		wizardComponents = new DefaultJWizardComponents();
		init();
	}

	private void init() {

		JPanel logoPanel = new JPanel();

		String fileString;
		if (logo.toString().indexOf("file:") < 0
				&& logo.toString().indexOf("http:") < 0) {
			fileString = "file:///" + System.getProperty("user.dir") + "/"
					+ logo.toString();
			fileString = fileString.replaceAll("\\\\", "/");
		} else {
			fileString = logo.toString();
		}
		logoPanel.add(new JLabel(logo));
		logoPanel.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.add(logoPanel, BorderLayout.WEST);
		this.add(wizardComponents.getWizardPanelsContainer(),
				BorderLayout.CENTER);

		JPanel auxPanel = new JPanel(new BorderLayout());
		auxPanel.add(new JSeparator(), BorderLayout.NORTH);

		buttonPanel = new SimpleButtonPanel(wizardComponents);
		auxPanel.add(buttonPanel);
		this.add(auxPanel, BorderLayout.SOUTH);

		wizardComponents.setFinishAction(new FinishAction(wizardComponents) {
			public void performAction() {
				// dispose();
			}
		});
		wizardComponents.setCancelAction(new CancelAction(wizardComponents) {
			public void performAction() {
				// dispose();
			}
		});
	}

	public DefaultJWizardComponents getWizardComponents() {
		return wizardComponents;
	}

	public void setWizardComponents(DefaultJWizardComponents aWizardComponents) {
		wizardComponents = aWizardComponents;
	}

	public void show() {
		wizardComponents.updateComponents();
		super.setVisible(true);
	}

}
