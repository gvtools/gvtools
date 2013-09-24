package org.gvsig.layer;

import static org.junit.Assert.assertTrue;

import org.geotools.filter.identity.FeatureIdImpl;
import org.junit.Test;

public class SelectionTest {

	@Test
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
