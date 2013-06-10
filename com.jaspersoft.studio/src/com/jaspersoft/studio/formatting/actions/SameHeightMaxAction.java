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
package com.jaspersoft.studio.formatting.actions;

import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.SetValueCommand;
import com.jaspersoft.studio.messages.Messages;

public class SameHeightMaxAction extends AbstractFormattingAction{

	/** The Constant ID. */
	public static final String ID = "matchheightmax"; //$NON-NLS-1$
	
	public SameHeightMaxAction(IWorkbenchPart part) {
		super(part);
		setText(Messages.SameHeightMaxAction_actionName);
		setToolTipText(Messages.SameHeightMaxAction_actionDescription);
		setId(ID);
		setImageDescriptor(JaspersoftStudioPlugin.getInstance().getImageDescriptor("icons/resources/matchheightmax.png"));  //$NON-NLS-1$
	}

	@Override
	protected boolean calculateEnabled() {
		return getOperationSet().size()>1;
	}
	
	public static CompoundCommand generateCommand(List<APropertyNode> nodes){
		CompoundCommand command = new CompoundCommand();
		
		int height = (Integer) nodes.get(0).getPropertyValue(JRDesignElement.PROPERTY_HEIGHT);
    
    // Find the highest one...
    for (int i=1; i<nodes.size(); ++i)
    {
    		JRDesignElement element = (JRDesignElement)nodes.get(i).getValue();
        if (height < element.getHeight())
        {
            height = element.getHeight();
        }
    }
    
    for (APropertyNode node : nodes)
    {
  			SetValueCommand setCommand = new SetValueCommand();
  			setCommand.setTarget(node);
  			setCommand.setPropertyId(JRDesignElement.PROPERTY_HEIGHT);
  			setCommand.setPropertyValue(height);
	      command.add(setCommand);
    }
		
		return command;
	}

	protected Command createAlignmentCommand() {
			List<APropertyNode> nodes = getOperationSet();
			CompoundCommand command = null;
			if (nodes.isEmpty()) 
				command = new CompoundCommand();
			else {
				command = generateCommand(nodes);
			}
			command.setDebugLabel(getText());
			return command;
	}
	
}
