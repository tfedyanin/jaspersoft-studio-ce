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
package com.jaspersoft.studio.components.chart.model.dataset;

import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.design.JRDesignGanttDataset;
import net.sf.jasperreports.charts.design.JRDesignHighLowDataset;
import net.sf.jasperreports.charts.design.JRDesignPieDataset;
import net.sf.jasperreports.charts.design.JRDesignTimePeriodDataset;
import net.sf.jasperreports.charts.design.JRDesignTimeSeriesDataset;
import net.sf.jasperreports.charts.design.JRDesignValueDataset;
import net.sf.jasperreports.charts.design.JRDesignXyDataset;
import net.sf.jasperreports.charts.design.JRDesignXyzDataset;
import net.sf.jasperreports.engine.JRChartDataset;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.dataset.MElementDataset;

public class ChartDatasetFactory {
	public static MElementDataset getChartDataset(ANode parent, JRChartDataset jrObject, int newIndex) {
		if (jrObject instanceof JRDesignCategoryDataset)
			return new MChartCategoryDataset(parent, (JRDesignCategoryDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignGanttDataset)
			return new MChartGanttDataset(parent, (JRDesignGanttDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignHighLowDataset)
			return new MChartHighLowDataset(parent, (JRDesignHighLowDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignPieDataset)
			return new MChartPieDataset(parent, (JRDesignPieDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignTimePeriodDataset)
			return new MChartTimePeriodDataset(parent, (JRDesignTimePeriodDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignTimeSeriesDataset)
			return new MChartTimeSeriesDataset(parent, (JRDesignTimeSeriesDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignValueDataset)
			return new MChartValueDataset(parent, (JRDesignValueDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignXyDataset)
			return new MChartXYDataset(parent, (JRDesignXyDataset) jrObject, parent.getJasperDesign());
		if (jrObject instanceof JRDesignXyzDataset)
			return new MChartXYZDataset(parent, (JRDesignXyzDataset) jrObject, parent.getJasperDesign());

		return new MChartDataset(parent, jrObject, parent.getJasperDesign());
	}
}
