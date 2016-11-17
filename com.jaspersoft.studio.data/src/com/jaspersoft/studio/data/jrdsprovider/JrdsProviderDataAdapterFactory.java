/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.jrdsprovider;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.data.provider.DataSourceProviderDataAdapterImpl;
import net.sf.jasperreports.engine.JasperReportsContext;

import org.eclipse.swt.graphics.Image;

import com.jaspersoft.studio.data.Activator;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterFactory;
import com.jaspersoft.studio.data.adapter.IDataAdapterCreator;
import com.jaspersoft.studio.data.messages.Messages;

public class JrdsProviderDataAdapterFactory implements DataAdapterFactory {

	public DataAdapterDescriptor createDataAdapter() {
		return new JrdsProviderDataAdapterDescriptor();
	}

	public String getDataAdapterClassName() {
		return DataSourceProviderDataAdapterImpl.class.getName();
	}

	public String getLabel() {
		return Messages.JrdsProviderDataAdapterFactory_label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getDescription()
	 */
	public String getDescription() {
		return Messages.JrdsProviderDataAdapterFactory_description;
	}

	/**
	 */
	public Image getIcon(int size) {
		if (size == 16) {
			return Activator.getDefault().getImage("icons/bean-green.png"); //$NON-NLS-1$
		}
		return null;
	}

	public DataAdapterService createDataAdapterService(JasperReportsContext jasperReportsContext, DataAdapter dataAdapter) {
		return null;
	}

	@Override
	public IDataAdapterCreator iReportConverter() {
		return null;
	}

	@Override
	public boolean isDeprecated() {
		return false;
	}

}
