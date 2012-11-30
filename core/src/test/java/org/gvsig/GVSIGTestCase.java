package org.gvsig;

import junit.framework.TestCase;

import org.gvsig.inject.LibModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public abstract class GVSIGTestCase extends TestCase {

	@Override
	protected void setUp() throws Exception {
		Module libModule = new LibModule();
		Module overridingModule = getOverridingModule();
		if (overridingModule != null) {
			libModule = Modules.override(libModule).with(overridingModule);
		}
		Injector injector = Guice.createInjector(libModule);
		injector.injectMembers(this);

		super.setUp();
	}

	protected Module getOverridingModule() {
		return null;
	}
}
