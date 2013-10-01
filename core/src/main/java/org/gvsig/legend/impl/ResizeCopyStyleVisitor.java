package org.gvsig.legend.impl;

import org.geotools.styling.Graphic;
import org.geotools.styling.Stroke;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.visitor.DuplicatingStyleVisitor;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;

public class ResizeCopyStyleVisitor extends DuplicatingStyleVisitor {
	private Expression size;

	public ResizeCopyStyleVisitor(Expression size, StyleFactory styleFactory,
			FilterFactory2 filterFactory) {
		super(styleFactory, filterFactory);
		this.size = size;
	}

	@Override
	public void visit(Graphic gr) {
		super.visit(gr);
		Graphic copy = (Graphic) pages.peek();
		copy.setSize(size);
	}

	@Override
	public void visit(Stroke stroke) {
		super.visit(stroke);
		Stroke copy = (Stroke) pages.peek();
		copy.setWidth(size);
	}
}
