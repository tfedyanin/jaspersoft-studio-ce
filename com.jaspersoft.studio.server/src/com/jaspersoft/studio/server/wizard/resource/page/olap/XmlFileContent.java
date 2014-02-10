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
package com.jaspersoft.studio.server.wizard.resource.page.olap;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.server.model.MResource;
import com.jaspersoft.studio.server.model.MXmlFile;
import com.jaspersoft.studio.server.wizard.resource.APageContent;
import com.jaspersoft.studio.server.wizard.resource.page.selector.SelectorXml;

public class XmlFileContent extends APageContent {
	private SelectorXml scompo;

	public XmlFileContent(ANode parent, MResource resource, DataBindingContext bindingContext) {
		super(parent, resource, bindingContext);
	}

	private String title;

	public XmlFileContent(ANode parent, MResource resource, String title) {
		super(parent, resource);
		this.title = title;
	}

	@Override
	public String getName() {
		if (title != null)
			return title;
		return MXmlFile.getIconDescriptor().getTitle();
	}

	@Override
	public String getPageName() {
		return "com.jaspersoft.studio.server.page.xmlfile";
	}

	@Override
	public String getHelpContext() {
		return "com.jaspersoft.studio.doc.editFile";
	}

	@Override
	public Control createContent(Composite parent) {
		scompo = new SelectorXml();
		scompo.addPageCompleteListener(this);
		rebind();
		return scompo.createControls(parent, pnode, res);
	}

	@Override
	protected void rebind() {
	}

	@Override
	public boolean isPageComplete() {
		return scompo != null && scompo.isPageComplete();
	}

}
