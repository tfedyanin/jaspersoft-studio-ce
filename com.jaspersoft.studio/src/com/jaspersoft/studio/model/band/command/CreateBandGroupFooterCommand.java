/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package com.jaspersoft.studio.model.band.command;

import org.eclipse.gef.commands.Command;

import com.jaspersoft.studio.model.band.MBandGroupFooter;

import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
/*
 * creates a band in a Group.
 * 
 * @author Chicu Veaceslav
 */
public class CreateBandGroupFooterCommand extends Command {

	/** The jr band. */
	private JRDesignBand jrBand;

	/** The jr design section. */
	private JRDesignSection jrDesignSection;

	/** The index. */
	private int index = -1;

	/**
	 * Instantiates a new creates the band group footer command.
	 * 
	 * @param destNode
	 *          the dest node
	 */
	public CreateBandGroupFooterCommand() {
		super();
	}

	/**
	 * Instantiates a new creates the band group footer command.
	 * 
	 * @param destNode
	 *          the dest node
	 */
	public CreateBandGroupFooterCommand(MBandGroupFooter destNode) {
		super();
		this.jrDesignSection = (JRDesignSection) (destNode.getJrGroup()).getGroupFooterSection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (jrBand == null) {
			jrBand = MBandGroupFooter.createJRBand();
		}
		if (jrBand != null && jrDesignSection != null) {
			if (index < 0 || index > jrDesignSection.getBandsList().size())
				jrDesignSection.addBand(jrBand);
			else
				jrDesignSection.addBand(index, jrBand);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (jrBand != null && jrDesignSection != null) {
			index = jrDesignSection.getBandsList().indexOf(jrBand);
			jrDesignSection.removeBand(jrBand);
		}
	}

	/**
	 * Return the element created on the execution of the command
	 * 
	 * @return the jr element created when the command is executed or null
	 */
	public JRChild getCreatedElement(){
		return jrBand;
	}
}
