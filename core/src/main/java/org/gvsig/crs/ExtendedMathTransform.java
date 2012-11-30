package org.gvsig.crs;

import java.awt.geom.Point2D;

import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.Matrix;
import org.opengis.referencing.operation.NoninvertibleTransformException;
import org.opengis.referencing.operation.TransformException;

/**
 * Wrapper for MathTransform that provides some convenience methods
 * 
 */
public class ExtendedMathTransform implements MathTransform {
	private MathTransform mathTransform;

	public ExtendedMathTransform(MathTransform mathTransform) {
		super();
		this.mathTransform = mathTransform;
	}

	public Point2D transform(Point2D source) throws TransformException {
		double[] srcCoords = new double[] { source.getX(), source.getY() };
		double[] targetCoords = new double[srcCoords.length];
		transform(srcCoords, 0, targetCoords, 0, srcCoords.length / 2);
		return new Point2D.Double(targetCoords[0], targetCoords[1]);
	}

	public int getSourceDimensions() {
		return mathTransform.getSourceDimensions();
	}

	public int getTargetDimensions() {
		return mathTransform.getTargetDimensions();
	}

	public DirectPosition transform(DirectPosition ptSrc, DirectPosition ptDst)
			throws MismatchedDimensionException, TransformException {
		return mathTransform.transform(ptSrc, ptDst);
	}

	public void transform(double[] srcPts, int srcOff, double[] dstPts,
			int dstOff, int numPts) throws TransformException {
		mathTransform.transform(srcPts, srcOff, dstPts, dstOff, numPts);
	}

	public void transform(float[] srcPts, int srcOff, float[] dstPts,
			int dstOff, int numPts) throws TransformException {
		mathTransform.transform(srcPts, srcOff, dstPts, dstOff, numPts);
	}

	public void transform(float[] srcPts, int srcOff, double[] dstPts,
			int dstOff, int numPts) throws TransformException {
		mathTransform.transform(srcPts, srcOff, dstPts, dstOff, numPts);
	}

	public void transform(double[] srcPts, int srcOff, float[] dstPts,
			int dstOff, int numPts) throws TransformException {
		mathTransform.transform(srcPts, srcOff, dstPts, dstOff, numPts);
	}

	public Matrix derivative(DirectPosition point)
			throws MismatchedDimensionException, TransformException {
		return mathTransform.derivative(point);
	}

	public MathTransform inverse() throws NoninvertibleTransformException {
		return mathTransform.inverse();
	}

	public boolean isIdentity() {
		return mathTransform.isIdentity();
	}

	public String toWKT() throws UnsupportedOperationException {
		return mathTransform.toWKT();
	}

}
