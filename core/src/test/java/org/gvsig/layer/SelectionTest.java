package org.gvsig.layer;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.geotools.filter.identity.FeatureIdImpl;
import org.opengis.filter.identity.FeatureId;

public class SelectionTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testCannotModifySelectionSet() throws Exception {
		Selection selection = new Selection();
		try {
			selection.add(mock(FeatureId.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}
		try {
			selection.addAll(mock(Collection.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}
		try {
			selection.clear();
			fail();
		} catch (UnsupportedOperationException e) {
		}
		try {
			selection.remove(mock(FeatureId.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}
		try {
			selection.removeAll(mock(Collection.class));
			fail();
		} catch (UnsupportedOperationException e) {
		}
	}

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
		Set<FeatureId> selection = new HashSet<FeatureId>();
		for (String fid : fids) {
			selection.add(new FeatureIdImpl(fid));
		}
		return new Selection(selection);
	}
}
