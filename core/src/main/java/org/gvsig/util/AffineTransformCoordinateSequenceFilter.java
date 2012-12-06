package org.gvsig.util;

import java.awt.geom.AffineTransform;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFilter;

public final class AffineTransformCoordinateSequenceFilter implements
		CoordinateSequenceFilter {
	private AffineTransform at;
	private double[] src = new double[2];
	private double[] dst = new double[2];

	public AffineTransformCoordinateSequenceFilter(AffineTransform at) {
		this.at = at;
	}

	@Override
	public boolean isGeometryChanged() {
		return true;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void filter(CoordinateSequence seq, int i) {
		src[0] = seq.getOrdinate(i, 0);
		src[1] = seq.getOrdinate(i, 1);
		at.transform(src, 0, dst, 0, 1);
		seq.setOrdinate(i, 0, dst[0]);
		seq.setOrdinate(i, 1, dst[1]);
	}
}