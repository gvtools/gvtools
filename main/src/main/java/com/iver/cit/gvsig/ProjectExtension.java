package com.iver.cit.gvsig;

/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004-2007 IVER T.I. and Generalitat Valenciana.
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

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.gvsig.gui.beans.swing.JFileChooser;
import org.gvsig.inject.ExtModule;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.tools.file.PathGenerator;

import com.google.inject.Guice;
import com.iver.andami.Launcher;
import com.iver.andami.Launcher.TerminationProcess;
import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;
import com.iver.andami.plugins.IExtension;
import com.iver.andami.plugins.status.IExtensionStatus;
import com.iver.andami.plugins.status.IUnsavedData;
import com.iver.andami.plugins.status.UnsavedData;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.andami.ui.wizard.UnsavedDataPanel;
import com.iver.cit.gvsig.project.Project;
import com.iver.cit.gvsig.project.ProjectFactory;
import com.iver.cit.gvsig.project.documents.ProjectDocument;
import com.iver.cit.gvsig.project.documents.contextMenu.AbstractDocumentContextMenuAction;
import com.iver.cit.gvsig.project.documents.exceptions.OpenException;
import com.iver.cit.gvsig.project.documents.gui.ProjectWindow;
import com.iver.cit.gvsig.project.documents.view.ProjectViewFactory;
import com.iver.utiles.GenericFileFilter;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;
import com.iver.utiles.save.AfterSavingListener;
import com.iver.utiles.save.BeforeSavingListener;
import com.iver.utiles.save.SaveEvent;
import com.iver.utiles.swing.threads.IMonitorableTask;
import com.iver.utiles.xml.XMLEncodingUtils;

/**
 * Extension que proporciona controles para crear proyectos nuevos, abrirlos y
 * guardarlos. Adem�s los tipos de tabla que soporta el proyecto son
 * a�adidos en esta clase.
 * 
 * @author Fernando Gonz�lez Cort�s
 * @author Pablo Piqueras Bartolom� (pablo.piqueras@iver.es)
 */
public class ProjectExtension extends Extension implements IExtensionStatus {
	private static String projectPath = null;
	// private ProjectWindow projectFrame;
	private ProjectWindow projectFrame;
	private Project p;
	private String lastSavePath;
	private WindowInfo seedProjectWindow;
	public static final String LAYOUT_TEMPLATE_FILECHOOSER_ID = "LAYOUT_TEMPLATE_FILECHOOSER_ID";
	public static final String PROJECT_FILE_CHOOSER_ID = "PROJECT_FILECHOOSER_ID";

	private static PathGenerator pathGenerator = PathGenerator.getInstance();
	/**
	 * Use UTF-8 for encoding, as it can represent characters from any language.
	 * 
	 * Another sensible option would be encoding =
	 * System.getProperty("file.encoding"); but this would need some extra
	 * testing.
	 */
	public static String PROJECTENCODING = "UTF-8";

	/**
	 * <p>
	 * Identifier of the extension point used by <code>ProjectExtension</code>
	 * to manage {@link BeforeSavingListener BeforeSavingListener}s.
	 * </p>
	 */
	public static final String BEFORE_SAVING_ID = "Before_Saving_PrjExt";

	/**
	 * <p>
	 * Identifier of the extension point used by <code>ProjectExtension</code>
	 * to manage {@link BeforeSavingListener BeforeSavingListener}s.
	 * </p>
	 */
	public static final String AFTER_SAVING_ID = "After_Saving_PrjExt";

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#initialize()
	 */
	public void initialize() {

		InjectorSingleton.setInjector(Guice.createInjector(new ExtModule()));

		try {
			Class.forName("javax.media.jai.EnumeratedParameter");
		} catch (ClassNotFoundException e) {
			NotificationManager
					.addError(
							"La m�quina virtual con la que se ejecuta gvSIG no tiene JAI instalado",
							e);
		}

		initializeDocumentActionsExtensionPoint();
		registerDocuments();

		// Recuperamos el �ltimo argumento, que se supone
		// que ser� el fichero .gvp que queremos abrir.
		// (por enmedio pueden venir o no otros argumentos,
		// por ejemplo el idioma)
		// TODO: Aqu� Jaume podr�a meter lo del backup del proyecto
		// que ha hecho para ValenciaUrban�stica

		registerIcons();

	}

	private void registerIcons() {
		PluginServices.getIconTheme().registerDefault("project-new",
				this.getClass().getClassLoader().getResource("images/new.png"));

		PluginServices.getIconTheme()
				.registerDefault(
						"project-open",
						this.getClass().getClassLoader()
								.getResource("images/open.png"));

		PluginServices.getIconTheme()
				.registerDefault(
						"project-save",
						this.getClass().getClassLoader()
								.getResource("images/save.png"));

		PluginServices.getIconTheme()
				.registerDefault(
						"project-save-as",
						this.getClass().getClassLoader()
								.getResource("images/save.png"));

		PluginServices.getIconTheme().registerDefault(
				"layout-template-open",
				this.getClass().getClassLoader()
						.getResource("images/opentemplate.png"));

		PluginServices.getIconTheme().registerDefault(
				"application-exit",
				this.getClass().getClassLoader()
						.getResource("images/salir.png"));
	}

	private void loadInitialProject() {
		String[] theArgs = PluginServices.getArguments();
		String lastArg = theArgs[theArgs.length - 1];
		if ((lastArg.endsWith(".gvp")) || (lastArg.endsWith(".GVP"))) {
			PluginServices.getLogger().debug(
					"Intentando cargar el proyecto " + lastArg);
			// File projectFile = new File(lastArg);
			setProject(readProject(lastArg));
			PluginServices.getMainFrame().setTitle(p.getName());
			projectPath = lastArg;
		} else {
			setProject(ProjectFactory.createProject());
			p.setName(PluginServices.getText(this, "untitled"));
			p.setModified(false);
			PluginServices.getMainFrame().setTitle(
					PluginServices.getText(this, "sin_titulo"));
		}
	}

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#postInitialize()
	 */
	public void postInitialize() {
		loadInitialProject();
		getProjectFrame().setProject(p);
		p.restoreWindowProperties();
	}

	public ProjectWindow getProjectFrame() {
		if (projectFrame == null)
			projectFrame = new ProjectWindow();
		return projectFrame;
	}

	/**
	 * Muestra la ventana con el gestor de proyectos.
	 */
	public void showProjectWindow() {
		if (seedProjectWindow != null) {
			if (seedProjectWindow.isClosed()) {
				// if it was closed, we just don't open the window now
				seedProjectWindow.setClosed(false);
				return;
			}
			WindowInfo winProps = seedProjectWindow;
			seedProjectWindow = null;
			PluginServices.getMDIManager().addWindow(getProjectFrame());
			PluginServices.getMDIManager().changeWindowInfo(getProjectFrame(),
					winProps);
		} else
			PluginServices.getMDIManager().addWindow(getProjectFrame());
	}

	/**
	 * Muestra la ventana con el gestor de proyectos, con las propiedades de
	 * ventana especificadas.
	 */
	public void showProjectWindow(WindowInfo wi) {
		seedProjectWindow = wi;
		showProjectWindow();
	}

	/**
	 * Guarda el proyecto actual en disco.
	 */
	private boolean guardar() {
		boolean saved = false;
		// if (p.getPath() == null) {
		if (projectPath == null) {
			saved = guardarDialogo();
		} else {
			long t1, t2;
			t1 = System.currentTimeMillis();
			saved = writeProject(new File(projectPath), p, false);
			t2 = System.currentTimeMillis();
			PluginServices.getLogger().info(
					"Project saved. " + (t2 - t1) + " miliseconds");
			getProjectFrame().refreshControls();
		}
		return saved;
	}

	private boolean guardarDialogo() {
		boolean saved = false;

		if (lastSavePath == null)
			lastSavePath = projectPath;

		Preferences prefs = Preferences.userRoot().node("gvsig.foldering");
		JFileChooser jfc = new JFileChooser(PROJECT_FILE_CHOOSER_ID, prefs.get(
				"ProjectsFolder", null));

		jfc.setDialogTitle(PluginServices.getText(this, "guardar_proyecto"));
		jfc.addChoosableFileFilter(new GenericFileFilter("gvp", PluginServices
				.getText(this, "tipo_fichero_proyecto")));

		if (jfc.showSaveDialog((Component) PluginServices.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (!(file.getPath().toLowerCase().endsWith(".gvp"))) {
				file = new File(file.getPath() + ".gvp");
			}
			saved = writeProject(file, p);
			String filePath = file.getAbsolutePath();
			lastSavePath = filePath.substring(0,
					filePath.lastIndexOf(File.separatorChar));

			getProjectFrame().refreshControls();
		}
		return saved;
	}

	/**
	 * Checks whether the project and related unsaved data is modified, and
	 * allows the user to save it.
	 * 
	 * @return true if the data has been correctly saved, false otherwise
	 */
	private boolean askSave() {
		if (p != null && p.hasChanged()) {
			TerminationProcess process = Launcher.getTerminationProcess();
			UnsavedDataPanel panel = process.getUnsavedDataPanel();
			panel.setHeaderText(PluginServices.getText(this,
					"Select_resources_to_save_before_closing_current_project"));
			panel.setAcceptText(
					PluginServices.getText(this, "save_resources"),
					PluginServices
							.getText(this,
									"Save_the_selected_resources_and_close_current_project"));
			panel.setCancelText(PluginServices.getText(this, "Dont_close"),
					PluginServices.getText(this, "Return_to_current_project"));
			int closeCurrProj = process.manageUnsavedData();
			if (closeCurrProj == JOptionPane.NO_OPTION) {
				// the user chose to return to current project
				return false;
			}
		}
		return true;
	}

	/**
	 * @see com.iver.mdiApp.plugins.IExtension#updateUI(java.lang.String)
	 */
	public void execute(String actionCommand) {
		if (actionCommand.equals("NUEVO")) {
			if (!askSave()) {
				return;
			}

			projectPath = null;
			ProjectDocument.initializeNUMS();
			PluginServices.getMDIManager().closeAllWindows();
			setProject(ProjectFactory.createProject());
			getProjectFrame().setProject(p);
			showProjectWindow();
			PluginServices.getMainFrame().setTitle(
					PluginServices.getText(this, "sin_titulo"));
		} else if (actionCommand.equals("ABRIR")) {
			if (!askSave())
				return;

			Preferences prefs = Preferences.userRoot().node("gvsig.foldering");
			JFileChooser jfc = new JFileChooser(PROJECT_FILE_CHOOSER_ID,
					prefs.get("ProjectsFolder", null));
			jfc.addChoosableFileFilter(new GenericFileFilter("gvp",
					PluginServices.getText(this, "tipo_fichero_proyecto")));

			if (jfc.showOpenDialog((Component) PluginServices.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
				ProjectDocument.initializeNUMS();
				PluginServices.getMDIManager().closeAllWindows();

				File projectFile = jfc.getSelectedFile();
				Project o = readProject(projectFile);
				setPath(projectFile.getAbsolutePath());
				if (o != null) {
					setProject(o);
				}

				getProjectFrame().setProject(p);
				PluginServices.getMainFrame().setTitle(p.getName());
				getProjectFrame().refreshControls();

				// jaume p.setModified(true);
				p.restoreWindowProperties();
			}
		} else if (actionCommand.equals("GUARDAR")) {
			guardar();
			// jaume p.setModified(false);
		} else if (actionCommand.equals("GUARDAR_COMO")) {
			guardarDialogo();
			// jaume p.setModified(false);
		} else if (actionCommand.equals("SALIR")) {
			Launcher.closeApplication();
		} else if (actionCommand.compareTo("OPENTEMPLATE") == 0) {
			openLayout();
			// jaume p.setModified(true);
		}
	}

	public void openLayout() {
		assert false;
		// gtrefactor Commented because we don't want Layouts yet
		// // Project project = ((ProjectExtension)
		// // PluginServices.getExtension(ProjectExtension.class)).getProject();
		// Layout layout = null;
		//
		// if (templatesPath == null) {
		// Preferences prefs = Preferences.userRoot().node("gvsig.foldering");
		// templatesPath = prefs.get("TemplatesFolder", null);
		// }
		//
		// JFileChooser jfc = new JFileChooser(LAYOUT_TEMPLATE_FILECHOOSER_ID,
		// templatesPath);
		// jfc.addChoosableFileFilter(new GenericFileFilter("gvt",
		// PluginServices
		// .getText(this, "plantilla")));
		//
		// if (jfc.showOpenDialog((Component) PluginServices.getMainFrame()) ==
		// JFileChooser.APPROVE_OPTION) {
		// File file = jfc.getSelectedFile();
		// if (!(file.getPath().endsWith(".gvt") || file.getPath().endsWith(
		// ".GVT"))) {
		// file = new File(file.getPath() + ".gvt");
		// }
		// try {
		// File xmlFile = new File(file.getAbsolutePath());
		// FileInputStream is = new FileInputStream(xmlFile);
		// Reader reader = XMLEncodingUtils.getReader(is);
		//
		// XmlTag tag = (XmlTag) XmlTag.unmarshal(reader);
		// try {
		// XMLEntity xml = new XMLEntity(tag);
		// if (xml.contains("followHeaderEncoding")) {
		// layout = Layout.createLayout(xml, p);
		// } else {
		// reader = new FileReader(xmlFile);
		// tag = (XmlTag) XmlTag.unmarshal(reader);
		// xml = new XMLEntity(tag);
		// layout = Layout.createLayout(xml, p);
		// }
		//
		// } catch (OpenException e) {
		// e.showError();
		// }
		// // fPanelLegendManager.setRenderer(LegendFactory.createFromXML(new
		// // XMLEntity(tag)));
		// } catch (FileNotFoundException e) {
		// NotificationManager.addError(
		// PluginServices.getText(this, "Al_leer_la_leyenda"), e);
		// }
		// ProjectMap pmap = ProjectFactory.createMap(file.getName());
		// pmap.setModel(layout);
		// pmap.getModel().setProjectMap(pmap);
		// p.addDocument(pmap);
		// PluginServices.getMDIManager().addWindow(layout);
		//
		// }
	}

	/**
	 * Escribe el proyecto en XML.
	 * 
	 * @param file
	 *            Fichero.
	 * @param p
	 *            Proyecto.
	 */
	public boolean writeProject(File file, Project p) {
		return writeProject(file, p, true);
	}

	/**
	 * Escribe el proyecto en XML. Pero permite decidir si se pide
	 * confirmaci�n para sobreescribir
	 * 
	 * @param file
	 *            Fichero.
	 * @param p
	 *            Proyecto.
	 * @param askConfirmation
	 *            boolean
	 */
	public boolean writeProject(File file, Project p, boolean askConfirmation) {
		pathGenerator.setBasePath(file.getParent());
		if (askConfirmation && file.exists()) {
			int resp = JOptionPane.showConfirmDialog((Component) PluginServices
					.getMainFrame(), PluginServices.getText(this,
					"fichero_ya_existe_seguro_desea_guardarlo"), PluginServices
					.getText(this, "guardar"), JOptionPane.YES_NO_OPTION);
			if (resp != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		NotificationManager.addInfo(PluginServices.getText(this,
				"writinng_project") + ": " + file.getName());
		// write it out as XML
		try {
			fireBeforeSavingFileEvent(new SaveEvent(this,
					SaveEvent.BEFORE_SAVING, file));
			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
			OutputStreamWriter writer = new OutputStreamWriter(fos,
					PROJECTENCODING);
			p.setName(file.getName());
			// p.setPath(file.toString());
			p.setModificationDate(DateFormat.getDateInstance().format(
					new Date()));
			p.setModified(false);
			org.gvsig.persistence.generated.Project xml = p.getXMLEntity();
			JAXBContext context = JAXBContext.newInstance(xml.getClass()
					.getPackage().getName(), xml.getClass().getClassLoader());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(xml, writer);
			fireAfterSavingFileEvent(new SaveEvent(this,
					SaveEvent.AFTER_SAVING, file));
			PluginServices.getMainFrame().setTitle(file.getName());
			setPath(file.toString());

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							(Component) PluginServices.getMainFrame(),
							PluginServices.getText(this,
									"error_writing_project")
									+ ":\n-"
									+ PluginServices
											.getText(this,
													"the_user_cannot_edit_the_project_because_it_has_not_write_permissions")
									+ ".", PluginServices.getText(this,
									"warning"), JOptionPane.OK_OPTION);
			// NotificationManager.addError(PluginServices.getText(this,"error_writing_project")+
			// ": "+file.getName(), e);
			return false;
		}
		NotificationManager.addInfo(PluginServices.getText(this,
				"wrote_project") + ": " + file.getName());
		return true;
	}

	public Project readProject(String path) {
		pathGenerator.setBasePath(new File(path).getParent());
		BufferedReader reader = null;
		try {
			URL url = null;
			url = new URL(path);
			String encoding = XMLEncodingUtils.getEncoding(url.openStream());
			InputStream is = url.openStream();
			if (encoding != null) {
				Project proj = null;
				try {
					reader = new BufferedReader(new InputStreamReader(is,
							encoding));
					proj = readProject(reader, true);
				} catch (UnsupportedEncodingException e) {
					reader = new BufferedReader(new InputStreamReader(is));
					try {
						readProject(reader, false);
					} catch (UnsupportedEncodingException e1) {
						JOptionPane.showMessageDialog(
								(Component) PluginServices.getMainFrame(),
								PluginServices.getText(this,
										e1.getLocalizedMessage()));
						return null;
					}
				}
				ProjectExtension.setPath(path);
				return proj;
			} else {
				reader = new BufferedReader(new InputStreamReader(is));
				Project p;
				try {
					p = readProject(reader, false);
				} catch (UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(
							(Component) PluginServices.getMainFrame(),
							PluginServices.getText(this,
									e.getLocalizedMessage()));
					return null;
				}
				ProjectExtension.setPath(path); // p.setPath(null);
				return p;
			}
		} catch (MalformedURLException e) {
			File file = new File(path);
			return readProject(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lee del XML el proyecto.<br>
	 * <br>
	 * 
	 * Reads the XML of the project.<br>
	 * It returns a project object holding all needed info that is not linked to
	 * the Project Dialog. <br>
	 * In case you want the project to be linked to the window you must set this
	 * object to the extension:<br>
	 * 
	 * <b>Example:</b><br>
	 * 
	 * ...<br>
	 * ...<br>
	 * Project p = ProjectExtension.readProject(projectFile);<br>
	 * ProjectExtension.setProject(p); ...<br>
	 * ...<br>
	 * 
	 * @param file
	 *            Fichero.
	 * 
	 * @return Project
	 * 
	 */
	public Project readProject(File file) {
		pathGenerator.setBasePath(file.getParent());
		File xmlFile = new File(file.getAbsolutePath());
		try {
			String encoding = XMLEncodingUtils.getEncoding(new FileInputStream(
					xmlFile));
			InputStreamReader reader = null;
			if (encoding != null) {
				try {
					reader = new InputStreamReader(
							new FileInputStream(xmlFile), encoding);
					return readProject(reader, true);
				} catch (UnsupportedEncodingException e) {
					reader = new InputStreamReader(new FileInputStream(xmlFile));
					try {
						return readProject(reader, false);
					} catch (UnsupportedEncodingException e1) {
						JOptionPane.showMessageDialog(
								(Component) PluginServices.getMainFrame(),
								PluginServices.getText(this,
										e1.getLocalizedMessage()));
						return null;
					}
				}
			} else {
				reader = new InputStreamReader(new FileInputStream(xmlFile));
				try {
					return readProject(reader, false);
				} catch (UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(
							(Component) PluginServices.getMainFrame(),
							PluginServices.getText(this,
									e1.getLocalizedMessage()));
					return null;
				}
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(
					(Component) PluginServices.getMainFrame(),
					PluginServices.getText(this, "fichero_incorrecto"));
			return null;
		}
	}

	/**
	 * Lee del XML el proyecto.<br>
	 * <br>
	 * 
	 * Reads the XML of the project.<br>
	 * It returns a project object holding all needed info that is not linked to
	 * the Project Dialog. <br>
	 * In case you want the project to be linked to the window you must set this
	 * object to the extension:<br>
	 * 
	 * <b>Example:</b><br>
	 * 
	 * ...<br>
	 * ...<br>
	 * Project p = ProjectExtension.readProject(projectFile);<br>
	 * ProjectExtension.setProject(p); ...<br>
	 * ...<br>
	 * 
	 * @param reader
	 *            File reader
	 * @param encodingFollowed
	 *            Whether the encoded specified in the xml header was followed
	 *            when creating the reader. If the property followHeaderEncoding
	 *            is false or not set, then the encoding should have not been
	 *            used when creating the reader, so it must be recreated.
	 * 
	 * @return Project
	 * @throws UnsupportedEncodingException
	 * 
	 */
	public Project readProject(Reader reader, boolean encodingFollowed)
			throws UnsupportedEncodingException {
		Project proj = null;

		try {
			Class<org.gvsig.persistence.generated.Project> persistenceClass = org.gvsig.persistence.generated.Project.class;
			JAXBContext context = JAXBContext.newInstance(persistenceClass
					.getPackage().getName(), persistenceClass.getClassLoader());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			org.gvsig.persistence.generated.Project project = (org.gvsig.persistence.generated.Project) unmarshaller
					.unmarshal(reader);

			String VERSION = project.getVersion();
			NotificationManager.addInfo(PluginServices.getText(this,
					"openning_project") + ": " + project.getName());

			try {
				// if ((VERSION!=null) && (VERSION.equals("0.5") ||
				// VERSION.equals("0.4") || (VERSION.indexOf("GISPLANET") !=
				// -1))){
				if (VERSION != null) {
					proj = Project.createFromXML(project);
				} else {
					NotificationManager.showMessageInfo(
							PluginServices.getText(this,
									"0.3 project?? come on! I'm not "
											+ "even translating this message"),
							null);
				}
				if (!VERSION.equals(Version.format())) {
					NotificationManager.showMessageInfo(PluginServices.getText(
							this, "this_project_is_a_previous_version"), null);
				}
				return proj;
			} catch (OpenException e) {
				e.showError();
				// NotificationManager.addInfo("Al leer el proyecto", e);
			}
		} catch (JAXBException e) {
			PluginServices.getLogger().error(
					PluginServices.getText(this, "formato_incorrecto"), e);
			JOptionPane.showMessageDialog(
					(Component) PluginServices.getMainFrame(),
					PluginServices.getText(this, "formato_incorrecto"));
			// NotificationManager.addError("Al leer el proyecto", e);
		}
		NotificationManager.addInfo(PluginServices.getText(this,
				"error_openning_project"));

		return null;
	}

	/**
	 * Devuelve el proyecto.
	 * 
	 * @return Proyecto.
	 */
	public Project getProject() {
		return p;
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @see com.iver.andami.plugins.IExtension#isVisible()
	 */
	public boolean isVisible() {
		return true;
	}

	/**
	 * Sets the project
	 * 
	 * @param p
	 */
	public void setProject(Project p) {
		getProjectFrame().setProject(p);
		this.p = p;
	}

	private void registerDocuments() {
		ProjectViewFactory.register();
		// gtrefactor We don't want layouts nor tables yet
		// ProjectTableFactory.register();
		// ProjectMapFactory.register();
	}

	private void initializeDocumentActionsExtensionPoint() {
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();
		String className = AbstractDocumentContextMenuAction.class
				.getCanonicalName();
		if (!extensionPoints.containsKey("DocumentActions_View")) {
			extensionPoints.put(new ExtensionPoint("DocumentActions_View",
					"Context menu options of the view document list"
							+ " in the project window "
							+ "(register instances of " + className + ")"));
		}
		if (!extensionPoints.containsKey("DocumentActions_Table")) {
			extensionPoints.put(new ExtensionPoint("DocumentActions_Table",
					"Context menu options of the table document list"
							+ " in the project window "
							+ "(register instances of " + className + ")"));
		}
		if (!extensionPoints.containsKey("DocumentActions_Map")) {
			extensionPoints.put(new ExtensionPoint("DocumentActions_Map",
					"Context menu options of the map document list"
							+ " in the project window "
							+ "(register instances of " + className + ")"));
		}
	}

	public static String getPath() {
		return projectPath;
	}

	public static void setPath(String path) {
		projectPath = path;
	}

	public IWindow getProjectWindow() {
		return getProjectFrame();
	}

	public IExtensionStatus getStatus() {
		return this;
	}

	public boolean hasUnsavedData() {
		return p.hasChanged();
	}

	public IUnsavedData[] getUnsavedData() {
		if (hasUnsavedData()) {
			UnsavedProject data = new UnsavedProject(this);
			IUnsavedData[] dataArray = { data };
			return dataArray;
		} else
			return null;
	}

	/**
	 * Implements the IUnsavedData interface to show unsaved projects in the
	 * Unsavad Data dialog.
	 * 
	 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
	 */
	public class UnsavedProject extends UnsavedData {

		public UnsavedProject(IExtension extension) {
			super(extension);
		}

		public String getDescription() {
			if (getPath() == null) {
				return PluginServices.getText(ProjectExtension.this,
						"Unnamed_new_gvsig_project_");
			} else {
				return PluginServices.getText(ProjectExtension.this,
						"Modified_project_");
			}
		}

		public String getResourceName() {
			if (getPath() == null) {
				return PluginServices.getText(ProjectExtension.this, "Unnamed");
			} else {
				return getPath();
			}

		}

		public boolean saveData() {
			return guardar();
		}

		public ImageIcon getIcon() {
			try {
				URL imagePath = PluginServices
						.getPluginServices(ProjectExtension.this)
						.getClassLoader().getResource("images/logoGVA.gif");
				return new ImageIcon(imagePath);
			} catch (Exception ex) {
			}
			return null;
		}
	}

	public IMonitorableTask[] getRunningProcesses() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasRunningProcesses() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Adds the specified before saving listener to receive
	 * "before saving file events" from this component. If l is null, no
	 * exception is thrown and no action is performed.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param l
	 *            the before saving listener.
	 * @see SaveEvent
	 * @see BeforeSavingListener
	 * @see #removeListener(BeforeSavingListener)
	 * @see #getBeforeSavingListeners
	 */
	public synchronized void addListener(BeforeSavingListener l) {
		if (l == null) {
			return;
		}

		ExtensionPoints ePs = ExtensionPointsSingleton.getInstance();

		if (ePs.get(BEFORE_SAVING_ID) == null) {
			List<BeforeSavingListener> aL = new ArrayList<BeforeSavingListener>();
			aL.add(l);

			ePs.add(BEFORE_SAVING_ID, "", aL);
			return;
		}

		ArrayList<BeforeSavingListener> list = (ArrayList<BeforeSavingListener>) (ePs
				.get(BEFORE_SAVING_ID)).get("");
		list.add(l);
	}

	/**
	 * Adds the specified after saving listener to receive
	 * "after saving file events" from this component. If l is null, no
	 * exception is thrown and no action is performed.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param l
	 *            the after saving listener.
	 * @see SaveEvent
	 * @see AfterSavingListener
	 * @see #removeListener(AfterSavingListener)
	 * @see #getAfterSavingListeners()
	 */
	public synchronized void addListener(AfterSavingListener l) {
		if (l == null) {
			return;
		}

		ExtensionPoints ePs = ExtensionPointsSingleton.getInstance();

		if (ePs.get(AFTER_SAVING_ID) == null) {
			List<AfterSavingListener> aL = new ArrayList<AfterSavingListener>();
			aL.add(l);

			ePs.add(AFTER_SAVING_ID, "", aL);
			return;
		}

		ArrayList<AfterSavingListener> arrayList = (ArrayList<AfterSavingListener>) (ePs
				.get(AFTER_SAVING_ID)).get("");
		arrayList.add(l);
	}

	/**
	 * Returns an array of all the before saving listeners registered on this
	 * component.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @return all of this component's <code>BeforeSavingListener</code>s or an
	 *         empty array if no key listeners are currently registered
	 * 
	 * @see #addBeforeSavingListener(BeforeSavingListener)
	 * @see #removeBeforeSavingListener(BeforeSavingListener)
	 */
	public synchronized BeforeSavingListener[] getBeforeSavingListeners() {
		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				BEFORE_SAVING_ID);

		if (eP == null)
			return null;

		ArrayList<BeforeSavingListener> list = (ArrayList<BeforeSavingListener>) eP
				.get("");
		return eP.get("") == null ? null : list
				.toArray(new BeforeSavingListener[0]);
	}

	/**
	 * Returns an array of all the after saving listeners registered on this
	 * component.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @return all of this component's <code>AfterSavingListener</code>s or an
	 *         empty array if no key listeners are currently registered
	 * 
	 * @see #addAfterSavingListener(AfterSavingListener)
	 * @see #removeAfterSavingListener
	 */
	public synchronized AfterSavingListener[] getAfterSavingListeners() {
		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				AFTER_SAVING_ID);

		if (eP == null)
			return null;

		ArrayList<AfterSavingListener> list = (ArrayList<AfterSavingListener>) eP
				.get("");
		return eP.get("") == null ? null : list
				.toArray(new AfterSavingListener[0]);
	}

	/**
	 * Removes the specified before saving listener so that it no longer
	 * receives save file events from this component. This method performs no
	 * function, nor does it throw an exception, if the listener specified by
	 * the argument was not previously added to this component. If listener
	 * <code>l</code> is <code>null</code>, no exception is thrown and no action
	 * is performed.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param l
	 *            the before saving listener
	 * @see SaveEvent
	 * @see BeforeSavingListener
	 * @see #addListener(BeforeSavingListener)
	 * @see #getBeforeSavingListeners()
	 */
	public synchronized void removeListener(BeforeSavingListener l) {
		if (l == null) {
			return;
		}

		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				BEFORE_SAVING_ID);

		if (eP != null) {
			((ArrayList<BeforeSavingListener>) eP.get("")).remove(l);
		}
	}

	/**
	 * Removes the specified after saving listener so that it no longer receives
	 * save file events from this component. This method performs no function,
	 * nor does it throw an exception, if the listener specified by the argument
	 * was not previously added to this component. If listener <code>l</code> is
	 * <code>null</code>, no exception is thrown and no action is performed.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param l
	 *            the after saving listener
	 * @see SaveEvent
	 * @see AfterSavingListener
	 * @see #addListener(AfterSavingListener)
	 * @see #getAfterSavingListeners()
	 */
	public synchronized void removeListener(AfterSavingListener l) {
		if (l == null) {
			return;
		}

		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				AFTER_SAVING_ID);

		if (eP != null) {
			((ArrayList<AfterSavingListener>) eP.get("")).remove(l);
		}
	}

	/**
	 * Reports a before saving file event.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param evt
	 *            the before saving file event
	 */
	protected void fireBeforeSavingFileEvent(SaveEvent evt) {
		if ((evt.getID() != SaveEvent.BEFORE_SAVING) || (evt.getFile() == null)) {
			return;
		}

		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				BEFORE_SAVING_ID);

		if (eP != null) {
			ArrayList<BeforeSavingListener> listeners = ((ArrayList<BeforeSavingListener>) eP
					.get(""));
			for (BeforeSavingListener listener : listeners) {
				listener.beforeSaving(evt);
			}
		}
	}

	/**
	 * Reports a after saving file event.
	 * 
	 * @author Pablo Piqueras Bartolom� <pablo.piqueras@iver.es>
	 * 
	 * @param evt
	 *            the after saving file event
	 */
	protected void fireAfterSavingFileEvent(SaveEvent evt) {
		if ((evt.getID() != SaveEvent.AFTER_SAVING) || (evt.getFile() == null)) {
			return;
		}

		ExtensionPoint eP = ExtensionPointsSingleton.getInstance().get(
				AFTER_SAVING_ID);

		if (eP != null) {
			ArrayList<AfterSavingListener> listeners = ((ArrayList<AfterSavingListener>) eP
					.get(""));
			for (AfterSavingListener listener : listeners) {
				listener.afterSaving(evt);
			}
		}
	}
}
