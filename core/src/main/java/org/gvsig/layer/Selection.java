package org.gvsig.layer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.opengis.filter.identity.FeatureId;

public class Selection implements Set<FeatureId> {

	private Set<FeatureId> fids = Collections
			.unmodifiableSet(new HashSet<FeatureId>());

	public boolean add(FeatureId arg0) {
		return fids.add(arg0);
	}

	public boolean addAll(Collection<? extends FeatureId> arg0) {
		return fids.addAll(arg0);
	}

	public void clear() {
		fids.clear();
	}

	public boolean contains(Object arg0) {
		return fids.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return fids.containsAll(arg0);
	}

	public boolean equals(Object arg0) {
		return fids.equals(arg0);
	}

	public int hashCode() {
		return fids.hashCode();
	}

	public boolean isEmpty() {
		return fids.isEmpty();
	}

	public Iterator<FeatureId> iterator() {
		return fids.iterator();
	}

	public boolean remove(Object arg0) {
		return fids.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return fids.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return fids.retainAll(arg0);
	}

	public int size() {
		return fids.size();
	}

	public Object[] toArray() {
		return fids.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return fids.toArray(arg0);
	}

}
