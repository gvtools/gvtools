package com.iver.cit.gvsig.project.documents;

import java.security.KeyException;
import java.util.Map;

import javax.swing.ImageIcon;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.contextMenu.AbstractDocumentContextMenuAction;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;
import com.iver.utiles.extensionPoints.IExtensionBuilder;

/**
 * Factory of ProjectDocument.
 * 
 * @author Vicente Caballero Navarro
 */
public abstract class ProjectDocumentFactory implements IExtensionBuilder {
	/**
	 * Returns the load priority of the ProjectDocument.
	 * 
	 * @return Priority.
	 */
	public int getPriority() {
		return 10;
	}

	/**
	 * Returns the priority of the ProjectDocument in the project window list.
	 * 
	 * @return Priority.
	 */
	public int getListPriority() {
		return 10;
	}

	/**
	 * Returns image of button.
	 * 
	 * @return Image button.
	 */
	public abstract ImageIcon getButtonIcon();

	/**
	 * Returns image of selected button.
	 * 
	 * @return Image button.
	 */
	public abstract ImageIcon getSelectedButtonIcon();

	/**
	 * Returns the name of ProjectDocument.
	 * 
	 * @return Name of ProjectDocument.
	 */
	public String getNameType() {
		return PluginServices.getText(this, "documento");
	}

	/**
	 * Create a new ProjectDocument.
	 * 
	 * @param project
	 *            Opened project.
	 * 
	 * @return ProjectDocument.
	 */
	public abstract ProjectDocument create(Project project);

	/**
	 * Introdece a gui to be able from the characteristics that we want a
	 * ProjectDocument
	 * 
	 * @param project
	 *            present Project.
	 * 
	 * @return new ProjectDocument.
	 */
	public ProjectDocument createFromGUI(Project project) {
		return create(project);
	}

	/**
	 * Returns the name of registration in the point of extension.
	 * 
	 * @return Name of registration
	 */
	public abstract String getRegisterName();

	/**
	 * Create a ProjectDocumentFactory.
	 * 
	 * @return ProjectDocumentFactory.
	 */
	public Object create() {
		return this;
	}

	/**
	 * Create a ProjectDocumentFactory.
	 * 
	 * @param args
	 * 
	 * @return ProjectDocumentFactory.
	 */
	public Object create(Object[] args) {
		return this;
	}

	/**
	 * Create a ProjectDocumentFactory.
	 * 
	 * @param args
	 * 
	 * @return ProjectDocumentFactory.
	 */
	public Object create(Map<Object, Object> args) {
		return this;
	}

	/**
	 * Registers in the points of extension the Factory with alias.
	 * 
	 * @param registerName
	 *            Register name.
	 * @param obj
	 *            Class of register.
	 * @param alias
	 *            Alias.
	 */
	public static void register(String registerName, Object obj, String alias) {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();
		extensionPoints.add("Documents", registerName, obj);
		ProjectDocument.NUMS.put(registerName, new Integer(0));
		ExtensionPoint extPoint = extensionPoints.get("Documents");

		try {
			extPoint.addAlias(registerName, alias);
		} catch (KeyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers in the points of extension the Factory
	 * 
	 * @param registerName
	 *            Register name.
	 * @param obj
	 *            Class of register.
	 */
	public static void register(String registerName, Object obj) {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();
		extensionPoints.add("Documents", registerName, obj);
		ProjectDocument.NUMS.put(registerName, new Integer(0));
	}

	/**
	 * Register an action for the document.
	 * 
	 * This actions will be appears in the context menu of the project document
	 * list.
	 * 
	 * 
	 * @param documentRegisterName
	 * @param actionName
	 * @param action
	 */
	public static void registerAction(String documentRegisterName,
			String actionName, AbstractDocumentContextMenuAction action) {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();
		extensionPoints.add("DocumentActions_" + documentRegisterName,
				actionName, action);
	}

	/**
	 * Return true if the name exists to another document.
	 * 
	 * @param project
	 * @param documentName
	 * 
	 * @return True if the name exists.
	 */
	public boolean existName(Project project, String documentName) {
		for (ProjectDocument pd : project.getDocumentsByType(getRegisterName())) {
			String title = pd.getName();
			if (title.compareTo(documentName) == 0) {
				return true;
			}
		}

		return false;
	}
}
