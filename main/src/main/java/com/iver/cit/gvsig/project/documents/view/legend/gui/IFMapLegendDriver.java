package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.geotools.styling.Style;
import org.gvsig.layer.Layer;

public interface IFMapLegendDriver {

	boolean accept(File f);

	String getDescription();

	String getFileExtension();

	ArrayList<String> getSupportedVersions();

	void write(Layer layers, Layer layer, Style legend, File file,
			String version);

	Hashtable<Layer, Style> read(Layer layers, Layer layer, File file);
}
