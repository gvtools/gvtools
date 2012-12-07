package org.gvsig.layer;

import junit.framework.TestCase;

import org.geotools.filter.identity.FeatureIdImpl;

public class SelectionTest extends TestCase {

	public void testXor() throws Exception {
		Selection res = createSelection("a", "b")
				.xor(createSelection("b", "c"));
		assertTrue(res.equals(createSelection("a", "c")));

		res = createSelection("a", "b").xor(createSelection("c", "d"));
		assertTrue(res.equals(createSelection("a", "b", "c", "d")));

		res = createSelection("a").xor(createSelection("a"));
		assertTrue(res.equals(createSelection()));
	}

	private Selection createSelection(String... fids) {
		Selection selection = new Selection();
		for (String fid : fids) {
			selection.add(new FeatureIdImpl(fid));
		}
		return selection;
	}
}
