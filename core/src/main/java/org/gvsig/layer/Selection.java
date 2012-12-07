package org.gvsig.layer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.opengis.filter.identity.FeatureId;

/**
 * Represents a set of {@link FeatureId} and includes methods to operate on
 * selection sets, like {@link #xor(Selection)}. Instances of this object are
 * mutable, but no event is triggered on doing that. In order to raise events,
 * the instance must be the object containing this instance, for example
 * {@link Layer#setSelection(Selection)}
 * 
 * @author Fernando González Cortés
 * @author Víctor González Cortés
 */
public class Selection implements Set<FeatureId> {

	private Set<FeatureId> fids;

	public Selection() {
		this.fids = new HashSet<FeatureId>();
	}

	public Selection xor(Selection that) {
		Selection ret = new Selection();
		addNotContained(that, this.iterator(), ret);
		addNotContained(this, that.iterator(), ret);
		return ret;
	}

	private void addNotContained(Selection that,
			Iterator<FeatureId> thisIterator, Selection ret) {
		while (thisIterator.hasNext()) {
			FeatureId featureId = thisIterator.next();
			if (!that.contains(featureId)) {
				ret.add(featureId);
			}
		}
	}

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
