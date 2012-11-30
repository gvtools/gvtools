package org.gvsig.inject;

import com.google.inject.Injector;

public class InjectorSingleton {

	private static Injector injector;

	public static void setInjector(Injector newInjector) {
		injector = newInjector;
	}
	
	public static Injector getInjector() {
		return injector;
	}
	
}
