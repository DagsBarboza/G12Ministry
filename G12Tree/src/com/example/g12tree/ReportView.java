package com.example.g12tree;

import java.util.HashMap;

import org.apache.tomcat.util.digester.SetRootRule;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.vaadin.addon.JFreeChartWrapper;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class ReportView extends CustomComponent implements View {

	public ReportView(HashMap<String, SQLContainer> sqlContainer, String selectionStr) {
		
		System.out.println("selected "+ selectionStr);
		XYSeries series = new XYSeries("Sprint 1");
		series.add(0, 165);
		series.add(1, 150);
		series.add(2, 130);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart("Burn Down Chart", // Title
				"days", // x-axis Label
				"Esimated Effort", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		JFreeChartWrapper wrapper = new JFreeChartWrapper(chart);
		// ///////////////////////////////////////////
		VerticalLayout v = new VerticalLayout();
		v.addComponent(wrapper);
		v.setWidth("100%");
		v.setMargin(true);
		
		setCompositionRoot(v);
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
