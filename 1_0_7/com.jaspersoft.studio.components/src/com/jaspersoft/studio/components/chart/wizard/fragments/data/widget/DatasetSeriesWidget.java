/*
 * JasperReports - Free Java Reporting Library. Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of JasperReports.
 * 
 * JasperReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JasperReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with JasperReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.components.chart.wizard.fragments.data.widget;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignGanttDataset;
import net.sf.jasperreports.charts.design.JRDesignHighLowDataset;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.design.JRDesignValueDataset;
import net.sf.jasperreports.charts.design.JRDesignXyDataset;
import net.sf.jasperreports.charts.design.JRDesignXyzDataset;
import net.sf.jasperreports.components.spiderchart.StandardSpiderDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignElementDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.draw.DrawVisitor;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.components.chart.wizard.fragments.data.ADSComponent;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSCategory;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSGantt;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSHighLow;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSPie;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSTimePeriod;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSTimeSeries;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSValue;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSXy;
import com.jaspersoft.studio.components.chart.wizard.fragments.data.DSXyz;
import com.jaspersoft.studio.components.chartspider.wizard.action.DSSpider;
import com.jaspersoft.studio.utils.SelectionHelper;

public class DatasetSeriesWidget {
	private JRDesignElementDataset eDataset;
	private JasperDesign jrDesign;
	private JRDesignElement jrChart;
	private Map<Class<? extends JRDesignElementDataset>, ADSComponent> map = new HashMap<Class<? extends JRDesignElementDataset>, ADSComponent>();
	private StackLayout stacklayout;
	private Composite sComposite;
	private DrawVisitor dv;
	private SimpleFileResolver sfResolver;

	public DatasetSeriesWidget(Composite parent) {
		createDataset(parent);
		sfResolver = SelectionHelper.getFileResolver();
	}

	public void createDataset(Composite composite) {
		sComposite = new Composite(composite, SWT.NONE);
		stacklayout = new StackLayout();
		sComposite.setLayout(stacklayout);
		sComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		map.put(JRDesignCategoryDataset.class, new DSCategory(sComposite, this));
		map.put(JRDesignGanttDataset.class, new DSGantt(sComposite, this));
		map.put(JRDesignHighLowDataset.class, new DSHighLow(sComposite, this));
		map.put(JRDesignPieDataset.class, new DSPie(sComposite, this));
		map.put(JRDesignTimePeriodDataset.class, new DSTimePeriod(sComposite,
				this));
		map.put(JRDesignTimeSeriesDataset.class, new DSTimeSeries(sComposite,
				this));
		map.put(JRDesignValueDataset.class, new DSValue(sComposite, this));
		map.put(JRDesignXyDataset.class, new DSXy(sComposite, this));
		map.put(JRDesignXyzDataset.class, new DSXyz(sComposite, this));
		// here we can add other datasources ...
		map.put(StandardSpiderDataset.class, new DSSpider(sComposite, this));

		stacklayout.topControl = map.get(JRDesignCategoryDataset.class)
				.getControl();
	}

	public String getName(Class<? extends JRDesignElementDataset> key) {
		ADSComponent c = map.get(key);
		if (c != null) {
			return c.getName();
		}
		return "noname";
	}

	public void setDataset(JasperDesign jrDesign, JRDesignElement jrChart,
			JRDesignElementDataset eDataset) {
		this.eDataset = eDataset;
		if (jrDesign != null && this.jrDesign != jrDesign) {
			this.jrDesign = jrDesign;
			dv = new DrawVisitor(jrDesign, null);
		}
		this.jrChart = jrChart;
		fillData();
	}

	private void fillData() {
		ADSComponent c = null;
		if (eDataset != null)
			c = map.get(eDataset.getClass());
		if (c != null) {
			c.setData(dv, jrChart, eDataset, sfResolver);
			stacklayout.topControl = c.getControl();
		} else {
			// a label, with not implemented ...
		}
		sComposite.layout(true);
	}

}
