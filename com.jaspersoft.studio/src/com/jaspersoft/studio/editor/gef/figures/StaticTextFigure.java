/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.editor.gef.figures;

import java.awt.Graphics2D;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRStaticText;

import com.jaspersoft.studio.editor.java2d.StackGraphics2D;
import com.jaspersoft.studio.jasper.JSSDrawVisitor;
import com.jaspersoft.studio.model.text.MStaticText;
/*
 * The Class StaticTextFigure.
 */
public class StaticTextFigure extends FrameFigure {

	private MStaticText staticTextModel = null;
	
	private StackGraphics2D cachedGraphics = null;
	/**
	 * Instantiates a new static text figure.
	 */
	public StaticTextFigure(MStaticText model) {
		super();
		this.staticTextModel = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jaspersoft.studio.editor.gef.figures.GenericFigure#draw(net.sf.jasperreports.engine.export.draw.DrawVisitor,
	 * net.sf.jasperreports.engine.JRElement)
	 */
	@Override
	protected void draw(JSSDrawVisitor drawVisitor, JRElement jrElement) {
		if (cachedGraphics == null || staticTextModel.hasChangedProperty()){
			staticTextModel.setChangedProperty(false);
			Graphics2D oldGraphics = drawVisitor.getGraphics2d();
			cachedGraphics = new StackGraphics2D(oldGraphics);
			drawVisitor.setGraphics2D(cachedGraphics);
			drawVisitor.visitStaticText((JRStaticText) jrElement);
			drawVisitor.setGraphics2D(oldGraphics);
		}
		cachedGraphics.setRealDrawer(drawVisitor.getGraphics2d());
		cachedGraphics.paintStack();
	}

}
