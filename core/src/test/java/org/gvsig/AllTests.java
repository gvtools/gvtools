package org.gvsig;

import org.gvsig.layer.FeatureSourceCacheTest;
import org.gvsig.layer.FilterTest;
import org.gvsig.layer.LayerTest;
import org.gvsig.layer.SourceTest;
import org.gvsig.legend.LegendSuite;
import org.gvsig.map.MapContextTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LayerTest.class, FilterTest.class,
		FeatureSourceCacheTest.class, SourceTest.class, LegendSuite.class,
		MapContextTest.class })
public class AllTests {
	// nothing
}
