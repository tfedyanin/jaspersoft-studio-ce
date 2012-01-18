/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.editor.gef.parts.handles;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.swt.graphics.Color;
/*
 * The Class BandResizeHandle2.
 */
public class CellResizeHandle2 extends ResizeHandle {

	/** The tracker. */
	CellResizeTracker tracker = null;

	/**
	 * Constructor for SectionResizeHandle.
	 * 
	 * @param owner
	 *          the owner
	 * @param direction
	 *          the direction
	 */
	public CellResizeHandle2(GraphicalEditPart owner, int direction) {
		super(owner, direction);
		setLocator(new CellResizeHandleLocator(owner.getFigure(), direction));
		setPreferredSize(2, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.handles.SquareHandle#getFillColor()
	 */
	@Override
	protected Color getFillColor() {
		if (getOwner().getFigure().getBounds().height == 0)
			return ColorConstants.red;
		return super.getFillColor();
	}

}
