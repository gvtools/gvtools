package com.iver.cit.gvsig.project.documents.view;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

import org.gvsig.inject.InjectorSingleton;
import org.gvsig.map.MapContext;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.ProjectDocumentFactory;
import com.iver.cit.gvsig.project.documents.view.gui.View;

/**
 * Factory of View.
 * 
 * @author Vicente Caballero Navarro
 */
public class ProjectViewFactory extends ProjectDocumentFactory {
	public static String registerName = "ProjectView";

	/**
	 * Returns image of button.
	 * 
	 * @return Image button.
	 */
	public ImageIcon getButtonIcon() {
		return PluginServices.getIconTheme().get("document-view-icon");
	}

	/**
	 * Returns image of selected button.
	 * 
	 * @return Image button.
	 */
	public ImageIcon getSelectedButtonIcon() {
		return PluginServices.getIconTheme().get("document-view-icon-sel");
	}

	/**
	 * Create a new ProjectDocument.
	 * 
	 * @param project
	 *            Opened project.
	 * 
	 * @return ProjectDocument.
	 */
	public ProjectDocument create(Project project) {
		String viewName = "";
		String aux = PluginServices.getText(this, "untitled");
		int numViews = ProjectDocument.NUMS.get(registerName);

		viewName = aux + " - " + numViews++;

		if (project != null) {
			// Buscamos si alguna vista ya ten�a este nombre:
			while (existName(project, viewName)) {
				viewName = aux + " - " + numViews++;
			}
		}
		ProjectDocument.NUMS.put(registerName, new Integer(numViews));
		ProjectView vista = createView(viewName);
		vista.setProject(project, 0);
		vista.setProjectDocumentFactory(this);

		return vista;
	}

	/**
	 * Create a new ProjectView.
	 * 
	 * @param baseName
	 *            name
	 * 
	 * @return ProjectView.
	 */
	private static ProjectView createView(String viewName) {
		ProjectView v = InjectorSingleton.getInjector().getInstance(
				ProjectView.class);
		MapContext viewMapContext = v.getMapContext();
		viewMapContext.setBackgroundColor(View.getDefaultBackColor());
		viewMapContext.setDistanceUnits(Project.getDefaultDistanceUnits());
		viewMapContext.setAreaUnits(Project.getDefaultDistanceArea());
		viewMapContext.setMapUnits(Project.getDefaultMapUnits());

		/*
		 * jaume. ?no puedo definir color de fondo en localizador?
		 * 
		 * v.getMapOverViewContext().setProjection(v.getMapContext().getProjection
		 * ()); v.getMapOverViewContext(). getViewPort(). setBackColor(
		 * Project.getDefaultMapOverViewBackColor() );
		 */
		v.setName(viewName);
		v.setCreationDate(DateFormat.getInstance().format(new Date()));

		return v;
	}

	/**
	 * Returns the name of registration in the point of extension.
	 * 
	 * @return Name of registration
	 */
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * Returns the name of ProjectDocument.
	 * 
	 * @return Name of ProjectDocument.
	 */
	public String getNameType() {
		return PluginServices.getText(this, "Vista");
	}

	/**
	 * Registers in the points of extension the Factory with alias.
	 * 
	 */
	public static void register() {
		register(registerName, new ProjectViewFactory(),
				ProjectView.class.getCanonicalName());

		PluginServices.getIconTheme().registerDefault("document-view-icon",
				ProjectView.class.getResource("/images/Vista.png"));
		PluginServices.getIconTheme().registerDefault("document-view-icon-sel",
				ProjectView.class.getResource("/images/Vista_sel.png"));

		PluginServices.getIconTheme()
				.registerDefault(
						"cursor-query-distance",
						ProjectViewFactory.class
								.getResource("/images/RulerCursor.gif"));

		PluginServices.getIconTheme().registerDefault(
				"cursor-query-information",
				ProjectViewFactory.class.getResource("/images/InfoCursor.gif"));
		PluginServices.getIconTheme().registerDefault("cursor-hiperlink",
				ProjectViewFactory.class.getResource("/images/LinkCursor.gif"));
		PluginServices.getIconTheme().registerDefault(
				"cursor-zoom-in",
				ProjectViewFactory.class
						.getResource("/images/ZoomInCursor.gif"));
		PluginServices.getIconTheme().registerDefault(
				"cursor-zoom-in",
				ProjectViewFactory.class
						.getResource("/images/ZoomInCursor.gif"));

		PluginServices.getIconTheme().registerDefault(
				"single-symbol",
				ProjectViewFactory.class
						.getResource("/images/single-symbol.png"));
		PluginServices.getIconTheme().registerDefault("vectorial-interval",
				ProjectViewFactory.class.getResource("/images/Intervalos.png"));
		PluginServices.getIconTheme().registerDefault(
				"vectorial-unique-value",
				ProjectViewFactory.class
						.getResource("/images/ValoresUnicos.png"));
		PluginServices.getIconTheme().registerDefault(
				"vectorial-unique-value",
				ProjectViewFactory.class
						.getResource("/images/ValoresUnicos.png"));
		PluginServices.getIconTheme().registerDefault("crux-cursor",
				ProjectViewFactory.class.getResource("/images/CruxCursor.png"));

	}

	/**
	 * Returns the priority of de ProjectDocument.
	 * 
	 * @return Priority.
	 */
	public int getPriority() {
		return 0;
	}

}
