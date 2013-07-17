package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.fmap.layers.FLayers;
import com.iver.cit.gvsig.fmap.rendering.ILegend;

public interface IFMapLegendDriver {

	boolean accept(File f);

	String getDescription();

	String getFileExtension();

	ArrayList<String> getSupportedVersions();

	void write(FLayers layers, FLayer layer, ILegend legend, File file,
			String version) throws LegendDriverException;

	Hashtable<FLayer, ILegend> read(FLayers layers, FLayer layer, File file)
			throws LegendDriverException;
}
