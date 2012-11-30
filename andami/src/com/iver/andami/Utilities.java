/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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
package com.iver.andami;

import java.awt.Component;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;
import javax.swing.RootPaneContainer;

import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.splash.MultiSplashWindow;

/**
 * This class offers several general purpose method, to perform common tasks in
 * an easy way.
 * 
 * @version $Revision: 11996 $
 */
public class Utilities {
	/**
	 * <b>key</b>: URL, <b>value</b>: path to the downloaded file.
	 */
	private static Hashtable<URL, String> downloadedFiles;
	/** DOCUMENT ME! */
	public static final String TEMPDIRECTORYPATH = System
			.getProperty("java.io.tmpdir") + "/tmp-andami";

	/**
	 * Creates an icon from an image path.
	 * 
	 * @param path
	 *            Path to the image to be loaded
	 * 
	 * @return ImageIcon if the image is found, null otherwise
	 */
	public static ImageIcon createImageIcon(String path) {
		URL imgURL = null;

		try {
			imgURL = new URL("file:" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			return null;
		}
	}

	/**
	 * Method which frees the memory from JInternalFrames
	 * 
	 * @param baseComponent
	 *            JInternalFrame whose memory is to be freed
	 */
	public static void cleanComponent(Component baseComponent) {
		try {
			cleanComponent(baseComponent, 0);
		} catch (Exception ignore) { // give some exception handling...
		}
	}

	/*
	 * * The "depth" parameter was being used for text output debugging. * But
	 * isn't essential now. I'll keep it anyways, as it avoids * calling the
	 * garbage collector every recursion.
	 */
	protected static void cleanComponent(Component baseComponent, int depth) {
		if (baseComponent == null) // recursion terminating clause
		{
			return;
		}

		if (baseComponent instanceof IWindow) {
			return;
		}

		Container cont;
		Component[] childComponents;
		int numChildren; // clean up component containers

		if (baseComponent instanceof Container) { // now clean up container
													// instance variables

			if (baseComponent instanceof RootPaneContainer) { // Swing
																// specialised
																// container
				cont = (Container) baseComponent;
				numChildren = cont.getComponentCount();
				childComponents = cont.getComponents();

				for (int i = 0; i < numChildren; i++) { // remove each component
														// from the current
														// container

					// each child component may be a container itself
					cleanComponent(childComponents[i], depth + 1);
					((RootPaneContainer) cont).getContentPane().remove(
							childComponents[i]);
				}

				((RootPaneContainer) cont).getContentPane().setLayout(null);
			} else { // General Swing, and AWT, Containers
				cont = (Container) baseComponent;
				numChildren = cont.getComponentCount();
				childComponents = cont.getComponents();

				for (int i = 0; i < numChildren; i++) // for(int i = 0;i <
														// numChildren;i++)
				{
					// remove each component from the current container // each
					// child component may be a container itself
					cleanComponent(childComponents[i], depth + 1);
					cont.remove(childComponents[i]);
				}

				cont.setLayout(null);
			}
		}

		// if component is also a container
	}

	/**
	 * Extracts a ZIP file in the provided directory
	 * 
	 * @param file
	 *            Compressed file
	 * @param dir
	 *            Directory to extract the files
	 * @param splash
	 *            The splash window to show the extraction progress
	 * 
	 * @throws ZipException
	 *             If there is some problem in the file format
	 * @throws IOException
	 *             If there is a problem reading the file
	 */
	public static void extractTo(File file, File dir, MultiSplashWindow splash)
			throws ZipException, IOException {
		ZipFile zip = new ZipFile(file);
		Enumeration<? extends ZipEntry> e = zip.entries();

		// Pasada para crear las carpetas
		while (e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();

			if (entry.isDirectory()) {
				File directorio = new File(dir.getAbsolutePath()
						+ File.separator + entry.getName());

				directorio.mkdirs();
			}

		}

		// Pasada para crear los ficheros
		e = zip.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
			splash.process(30, "Procesando " + entry.getName() + "...");
			if (!entry.isDirectory()) {
				InputStream in = zip.getInputStream(entry);
				OutputStream out = new FileOutputStream(dir + File.separator
						+ entry.getName());
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bout = new BufferedOutputStream(out);

				int i;

				while ((i = bin.read()) != -1) {
					bout.write(i);
				}

				bout.flush();
				bout.close();
				bin.close();

			}

		}

		zip.close();
		zip = null;
		System.gc();

	}

	/**
	 * Returns the content of this URL as a file from the file system.<br>
	 * <p>
	 * If the URL has been already downloaded in this session and notified to
	 * the system using the static <b>Utilities.addDownloadedURL(URL)</b>
	 * method, it can be restored faster from the file system avoiding to
	 * download it again.
	 * </p>
	 * 
	 * @param url
	 * @return File containing this URL's content or null if no file was found.
	 */
	private static File getPreviousDownloadedURL(URL url) {
		File f = null;
		if (downloadedFiles != null && downloadedFiles.containsKey(url)) {
			String filePath = downloadedFiles.get(url);
			f = new File(filePath);
		}
		return f;
	}

	/**
	 * Adds an URL to the table of downloaded files for further uses. If the URL
	 * already exists in the table its filePath value is updated to the new one
	 * and the old file itself is removed from the file system.
	 * 
	 * @param url
	 * @param filePath
	 */
	private static void addDownloadedURL(URL url, String filePath) {
		if (downloadedFiles == null)
			downloadedFiles = new Hashtable<URL, String>();
		// JMV: No se puede eliminar el anterior porque puede que alguien lo
		// este usando
		/*
		 * if (fileName!=null){ File f = new File(fileName); if (f.exists())
		 * f.delete(); }
		 */
	}

	/**
	 * Downloads an URL into a temporary file that is removed the next time the
	 * tempFileManager class is called, which means the next time gvSIG is
	 * launched.
	 * 
	 * @param url
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws ServerErrorResponseException
	 * @throws ConnectException
	 * @throws UnknownHostException
	 */
	public static File downloadFile(URL url, String name) throws IOException,
			ConnectException, UnknownHostException {
		File f = null;

		try {
			if ((f = getPreviousDownloadedURL(url)) == null) {
				File tempDirectory = new File(TEMPDIRECTORYPATH);
				if (!tempDirectory.exists())
					tempDirectory.mkdir();

				f = new File(TEMPDIRECTORYPATH + "/" + name
						+ System.currentTimeMillis());

				System.out.println("downloading '" + url.toString() + "' to: "
						+ f.getAbsolutePath());

				f.deleteOnExit();
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(f)));
				byte[] buffer = new byte[1024 * 256];
				InputStream is = url.openStream();
				for (int i = is.read(buffer); i > 0; i = is.read(buffer)) {
					dos.write(buffer, 0, i);
				}
				dos.close();
				addDownloadedURL(url, f.getAbsolutePath());
			}
		} catch (IOException io) {
			io.printStackTrace();
		}

		return f;
	}

	/**
	 * Cleans every temporal file previously downloaded.
	 */
	public static void cleanUpTempFiles() {
		try {
			File tempDirectory = new File(TEMPDIRECTORYPATH);

			File[] files = tempDirectory.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					// sólo por si en un futuro se necesitan crear directorios
					// temporales
					if (files[i].isDirectory())
						deleteDirectory(files[i]);
					files[i].delete();
				}
			}
			tempDirectory.delete();
		} catch (Exception e) {
		}

	}

	/**
	 * Recursive directory delete.
	 * 
	 * @param f
	 */
	private static void deleteDirectory(File f) {
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory())
				deleteDirectory(files[i]);
			files[i].delete();
		}

	}

	/**
	 * Creates a temporary file with a the provided name and data. The file will
	 * be automatically deleted when the application exits.
	 * 
	 * @param fileName
	 *            Name of the temporary file to create
	 * @param data
	 *            The data to store in the file
	 */
	public static File createTemp(String fileName, String data)
			throws IOException {
		File f = new File(fileName);
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(f)));
		dos.writeBytes(data);
		dos.close();
		f.deleteOnExit();
		return f;
	}

	/**
	 * Remove an URL from the system cache. The file will remain in the file
	 * system for further eventual uses.
	 * 
	 * @param request
	 */
	public static void removeURL(URL url) {
		if (downloadedFiles != null && downloadedFiles.containsKey(url))
			downloadedFiles.remove(url);
	}

	/**
	 * Creates the directory for temporary files, and returns the path of this
	 * directory. If the directory already exists, it just returns its path. Any
	 * file or directory created in this special directory will be delete when
	 * the application finishes.
	 * 
	 * @return An String containing the full path to the temporary directory
	 */
	public static String createTempDirectory() {
		File tempDirectory = new File(TEMPDIRECTORYPATH);
		if (!tempDirectory.exists())
			tempDirectory.mkdir();
		return TEMPDIRECTORYPATH;
	}
}
