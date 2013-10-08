package org.gvsig.legend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IntervalTest {
	@Test
	public void testParseInteger() {
		assertEquals(new Interval(10, 20), Interval.parseInterval("10 - 20"));
		assertEquals(new Interval(10, 20),
				Interval.parseInterval("  10  -  20  "));
	}

	@Test
	public void testParseDouble() {
		assertEquals(new Interval(10.5, 20.5),
				Interval.parseInterval(10.5 + " - " + 20.5));
	}

	@Test
	public void testScientificNotation() {
		assertEquals(new Interval(0.01, 0.1),
				Interval.parseInterval("1e-2 - 1e-1"));
	}

	@Test
	public void testParseToStringInteger() {
		Interval original = new Interval(10, 20);
		Interval parsed = Interval.parseInterval(original.toString());
		assertEquals(original, parsed);
	}

	@Test
	public void testParseToStringDouble() {
		Interval original = new Interval(10.5, 20.5);
		Interval parsed = Interval.parseInterval(original.toString());
		assertEquals(original, parsed);
	}

	@Test
	public void testParseInvalid() {
		try {
			Interval.parseInterval("10-20");
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			Interval.parseInterval("invalid");
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			Interval.parseInterval("10-20-30");
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
