package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.drivers.legend.IFMapLegendDriver;

/**
 * 
 * JLegendFileChooser.java
 * 
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es Jul 22, 2008
 * 
 */
public class JLegendFileChooser extends JFileChooser {
	private static final long serialVersionUID = 8855060580358068594L;
	private static String lastPath;
	private IFMapLegendDriver[] drivers;
	private boolean write_mode = false;
	public static String driverVersion;

	public JLegendFileChooser() {
		super();
	}

	public JLegendFileChooser(IFMapLegendDriver[] legendDrivers) {
		this(legendDrivers, false);
	}

	public JLegendFileChooser(final IFMapLegendDriver[] legendDrivers,
			boolean write_mode) {
		super(lastPath);
		this.write_mode = write_mode;
		this.drivers = legendDrivers;

		setMultiSelectionEnabled(false);

		if (!this.write_mode) {// load method->"all the drivers in one"

			this.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					for (int i = 0; i < legendDrivers.length; i++) {
						if (legendDrivers[i].accept(f))
							return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					String strLegendFormats = "";
					for (int i = 0; i < legendDrivers.length; i++) {

						strLegendFormats += "*."
								+ legendDrivers[i].getFileExtension();
						if (i < legendDrivers.length - 1)
							strLegendFormats += ", ";
					}

					return PluginServices.getText(this,
							"all_supported_legend_formats")
							+ " ("
							+ strLegendFormats + ")";
				}
			});
		} else {// write method->all drivers are separated
			for (int i = 0; i < legendDrivers.length; i++) {
				final IFMapLegendDriver driver = legendDrivers[i];

				if (driver.getSupportedVersions() != null) {

					for (int j = 0; j < driver.getSupportedVersions().size(); j++) {

						final String version = driver.getSupportedVersions()
								.get(j);

						FileFilter aFilter = new FileFilter() {

							@Override
							public boolean accept(File f) {
								return driver.accept(f);
							}

							@Override
							public String getDescription() {
								return PluginServices.getText(
										this,
										driver.getDescription() + " " + version
												+ " (*."
												+ driver.getFileExtension()
												+ ")");
							}

						};
						this.addChoosableFileFilter(aFilter);
					}
				} else {

					FileFilter aFilter = new FileFilter() {

						@Override
						public boolean accept(File f) {
							return driver.accept(f);
						}

						@Override
						public String getDescription() {
							return PluginServices.getText(
									this,
									driver.getDescription() + " (*."
											+ driver.getFileExtension() + ")");
						}

					};
					this.addChoosableFileFilter(aFilter);
				}

			}
		}
	}

	public IFMapLegendDriver getSuitableDriver() {
		File f = getSelectedFile();

		for (int i = 0; drivers != null && i < drivers.length; i++) {
			if (drivers[i].accept(f))
				return drivers[i];
		}
		return null;
	}

	public IFMapLegendDriver getDriverFromDescription(File file) {
		if (getFileFilter() != null) {
			String descripFile = getFileFilter().getDescription();
			IFMapLegendDriver myDriver = null;

			for (int i = 0; i < drivers.length; i++) {

				myDriver = drivers[i];
				String driverDesc;

				if (myDriver.getSupportedVersions() != null) {
					for (int j = 0; j < myDriver.getSupportedVersions().size(); j++) {
						driverDesc = myDriver.getDescription() + " "
								+ myDriver.getSupportedVersions().get(j)
								+ " (*." + myDriver.getFileExtension() + ")";
						if (driverDesc.equals(descripFile)) {
							driverVersion = myDriver.getSupportedVersions()
									.get(j);
							return myDriver;
						}
					}
				} else {
					driverDesc = myDriver.getDescription() + " (*."
							+ myDriver.getFileExtension() + ")";
					if (driverDesc.equals(descripFile)) {
						driverVersion = "";
						return myDriver;
					}
				}
			}
		}
		return null;
	}

	public File getSelectedFile() {
		File f = super.getSelectedFile();

		IFMapLegendDriver myDriver = getDriverFromDescription(f);
		if (f != null) {
			if (myDriver != null
					&& !(f.getPath().toLowerCase().endsWith("."
							+ myDriver.getFileExtension()))) {
				f = new File(f.getPath() + "." + myDriver.getFileExtension());
			}
			lastPath = f.getAbsolutePath().substring(0,
					f.getAbsolutePath().lastIndexOf(File.separator));
		}
		return f;
	}

	public static String getDriverVersion() {
		return driverVersion;
	}

}
