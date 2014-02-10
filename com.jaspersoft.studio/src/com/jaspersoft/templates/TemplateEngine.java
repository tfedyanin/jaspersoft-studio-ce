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
package com.jaspersoft.templates;

import java.util.Map;

import net.sf.jasperreports.engine.JasperReportsContext;

/**
 * A TemplateEngine produces a ReportBundle starting from a TemplateBundle and a set of user settings provided
 * trough a Map.
 * 
 * 
 * @author gtoffoli
 *
 */
public interface TemplateEngine {

	/**
	 * 
	 * Generates a ReportBundle based on the input.
	 *  
	 * @param template
	 * @param settings
	 * @return
	 * @throws TemplateEngineException
	 */
	public ReportBundle generateReportBundle(TemplateBundle template, Map<String, Object> settings,
			JasperReportsContext jContext) throws TemplateEngineException;
	
}
