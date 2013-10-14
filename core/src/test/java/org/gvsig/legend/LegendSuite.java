package org.gvsig.legend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ IntervalTest.class, IntervalLegendTest.class,
		ProportionalLegendTest.class, SingleSymbolLegendTest.class,
		SizeIntervalLegendTest.class, UniqueValuesLegendTest.class })
public class LegendSuite {
	// nothing
}