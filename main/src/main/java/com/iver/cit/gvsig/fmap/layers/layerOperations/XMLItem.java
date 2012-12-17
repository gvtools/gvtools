package com.iver.cit.gvsig.fmap.layers.layerOperations;

import org.gvsig.layer.Layer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface XMLItem {
	public void parse(ContentHandler handler) throws SAXException;

	public Layer getLayer();
}
