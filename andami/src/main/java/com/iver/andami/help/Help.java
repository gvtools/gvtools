package com.iver.andami.help;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;

import org.apache.log4j.Logger;

import com.iver.utiles.BrowserControl;

public class Help {
	private static final Logger logger = Logger.getLogger("org.gvsig");
	private static final String WIKI_URL = "http://gvsigce.sourceforge.net/wiki";

	public static void show() {
		logger.info("show()");
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Action.BROWSE)) {
					desktop.browse(new URI(WIKI_URL));
				} else {
					BrowserControl.displayURL(WIKI_URL);
				}
			} else {
				BrowserControl.displayURL(WIKI_URL);
			}
		} catch (Exception e) {
			logger.error("An exception has occurred while trying to show help",
					e);
			BrowserControl.displayURL(WIKI_URL);
		}
	}
}
