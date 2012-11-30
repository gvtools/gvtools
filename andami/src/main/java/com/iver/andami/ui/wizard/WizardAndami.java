package com.iver.andami.ui.wizard;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jwizardcomponent.CancelAction;
import jwizardcomponent.DefaultJWizardComponents;
import jwizardcomponent.FinishAction;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

public class WizardAndami extends JPanel implements IWindow {
	WindowInfo viewInfo = null;
	WizardPanelWithLogo wizardPanel;

	// No deberían necesitarse un FinishAction y un CancelAction, pero bueno,
	// lo mantengo por ahora.
	private class CloseAction extends FinishAction {
		IWindow v;

		public CloseAction(IWindow view) {
			super(wizardPanel.getWizardComponents());
			v = view;
		}

		public void performAction() {
			PluginServices.getMDIManager().closeWindow(v);
		}

	}

	private class CloseAction2 extends CancelAction {

		IWindow v;

		public CloseAction2(IWindow view) {
			super(wizardPanel.getWizardComponents());
			v = view;
		}

		public void performAction() {
			PluginServices.getMDIManager().closeWindow(v);
		}

	}

	public WizardAndami(ImageIcon logo) {
		wizardPanel = new WizardPanelWithLogo(logo);
		CloseAction closeAction = new CloseAction(this);
		CloseAction2 closeAction2 = new CloseAction2(this);
		wizardPanel.getWizardComponents().setFinishAction(closeAction);
		wizardPanel.getWizardComponents().setCancelAction(closeAction2);

		this.setLayout(new BorderLayout());
		this.add(wizardPanel, BorderLayout.CENTER);
	}

	public DefaultJWizardComponents getWizardComponents() {
		return wizardPanel.getWizardComponents();
	}

	public WindowInfo getWindowInfo() {
		if (viewInfo == null) {
			viewInfo = new WindowInfo(WindowInfo.MODALDIALOG
					| WindowInfo.RESIZABLE);
		}
		return viewInfo;
	}

	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return WindowInfo.DIALOG_PROFILE;
	}

}
