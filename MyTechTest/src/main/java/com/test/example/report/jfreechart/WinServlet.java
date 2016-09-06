package com.test.example.report.jfreechart;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("serial")
public class WinServlet extends HttpServlet {

	private void init(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/jpeg");

		CategoryDataset dataset = getDataset();

		JFreeChart chart = ChartFactory.createBarChart3D("圆柱",// 标题
				"应报与实报对照", // x轴显示
				"人数", // y轴显示
				dataset, // 数据源
				PlotOrientation.VERTICAL,// 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false);// 是否生成URL链接

		CategoryPlot plot = chart.getCategoryPlot();

		CategoryAxis axis = plot.getDomainAxis();// 获取x轴

		ValueAxis numberAxis = plot.getRangeAxis();// 获取y轴

		axis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
		axis.setUpperMargin(0.1);// 设置距离图片右端距离此时为百分之10
		axis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)
		axis.setCategoryMargin(0.2);// 横轴标签之间的距离20%

		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));// 设置底部中文乱码

		axis.setTickLabelFont(new Font("黑体", Font.BOLD, 12));// 设置X轴坐标上的文字
		axis.setLabelFont(new Font("黑体", Font.BOLD, 12));// 设置X轴的标题文字
		numberAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 12));// 设置X轴坐标上的文字
		numberAxis.setLabelFont(new Font("黑体", Font.BOLD, 12));// 设置X轴的标题文字
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 18));// 设置标题文字
		// ChartUtilities.writeChartAsJPEG(response.getOutputStream(), chart,
		// 400,
		// 300);

		String path = request.getPathTranslated() + "tempImg" + File.separator;

		File file = new File(path);
		file.mkdirs();
		file = new File(path + "tempFile.jpeg");
		ChartUtilities.saveChartAsJPEG(file, chart, 400, 300);
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 400,
				300);
	}

	/**
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		init(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		init(request, response);

	}

	private CategoryDataset getDataset() {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(100, "计划", "清华大学");

		dataset.addValue(Math.round(Math.random() * 150), "实报", "清华大学");

		dataset.addValue(200, "计划", "天津大学");

		dataset.addValue(Math.round(Math.random() * 300), "实报", "天津大学");

		dataset.addValue(130, "计划", "郑州大学");

		dataset.addValue(Math.round(Math.random() * 180), "实报", "郑州大学");

		return dataset;

	}

}
