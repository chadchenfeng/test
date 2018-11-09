package richword;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class CreateGraphByJfreechartApi {
	
	private static StandardChartTheme chartTheme;
	private int width; //图片宽度
	private int height; //图片高度
	static {
		chartTheme=new StandardChartTheme("CN");
		//设置标题字体
		chartTheme.setExtraLargeFont(new Font("黑体", Font.BOLD, 20));
		//设置轴向字体
		chartTheme.setLargeFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		//设置图例字体
		chartTheme.setRegularFont(new Font("宋体", Font.CENTER_BASELINE, 15));
	}

	public CreateGraphByJfreechartApi() {
		this.width=640;
		this.height=480;

	}
	public CreateGraphByJfreechartApi(int width, int height) {
		this.width = width;
		this.height = height;
	}



	public static void main(String[] args) throws Exception {
		Map<String,Map<String,List<String>>> map=new HashMap<String,Map<String,List<String>>>();
		Map<String,List<String>> categroyMap1 =new HashMap<String,List<String>>();
		List<String> x_label_list1=new ArrayList<String>();
		x_label_list1.add("2018年05月");
		x_label_list1.add("2018年06月");
		x_label_list1.add("2018年07月");
		x_label_list1.add("2018年08月");
		x_label_list1.add("2018年09月");
		x_label_list1.add("2018年10月");
		
		List<String> value_list1=new ArrayList<String>();
		value_list1.add("2");
		value_list1.add("8.01");
		value_list1.add("6.18");
		value_list1.add("5.56");
		value_list1.add("3.56");
		value_list1.add("1.56");
		categroyMap1.put("x_label", x_label_list1);
		categroyMap1.put("value", value_list1);
		map.put("蓝莓优酪乳", categroyMap1);
		
		Map<String,List<String>> categroyMap2 =new HashMap<String,List<String>>();
		List<String> x_label_list2=new ArrayList<String>();
		x_label_list2.add("2018年05月");
		x_label_list2.add("2018年06月");
		x_label_list2.add("2018年07月");
		x_label_list2.add("2018年08月");
		x_label_list2.add("2018年09月");
		x_label_list2.add("2018年10月");
		
		List<String> value_list2=new ArrayList<String>();
		value_list2.add("18.73");
		value_list2.add("15.58");
		value_list2.add("10.32");
		value_list2.add("9.63");
		value_list2.add("8");
		value_list2.add("7.63");
		categroyMap2.put("x_label", x_label_list2);
		categroyMap2.put("value", value_list2);
		map.put("纯牛奶111", categroyMap2);
		
		new CreateGraphByJfreechartApi(500,500).createBarChart("单UV成本柱状图",map,"F:/tmp/dataworks/单uv成本柱状图.png");
		
		new CreateGraphByJfreechartApi().createLineChart("单UV成本折线图", map, "F:/tmp/dataworks/单uv成本折线图.png");
	}
	
	
	
	public void createBarChart(String title, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		createBarChart(title,"","",data,imagePath);
	}
	/*
	 * 创建柱状图
	 *data 传入的数据格式：
	 * {categorytype1:{x_label:["201805","201806","201807"],value:["2","8","6"]}
	 * ,categorytype2:{x_label:["201805","201806","201807"],value:["18","15","10"]}
	 * }
	 * 
	 */
	public void createBarChart(String title,String x_label_direction,String y_label_direction, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = ChartFactory.createBarChart(title, x_label_direction, y_label_direction, dataset);
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		BarRenderer renderer=(BarRenderer) plot.getRenderer();
		renderer.setItemMargin(0.05f);
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultSeriesVisible(true);
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		chart.getCategoryPlot().setRenderer(renderer);
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
		
	}
	
	public DefaultCategoryDataset paraseDataset(Map<String,Map<String,List<String>>> data) {
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		//解析数据
			for(Map.Entry<String, Map<String,List<String>>> entry:data.entrySet()) {
				String categoryType=entry.getKey();
				List<String> x_label_list=null;
				List<String> value_list=null;
				for(Map.Entry<String,List<String>> datavalue:entry.getValue().entrySet()) {
					if("x_label".equals(datavalue.getKey())) {
						x_label_list=datavalue.getValue();
					}else if("value".equals(datavalue.getKey())) {
						value_list=datavalue.getValue();
					}
				}
				if(x_label_list==null || value_list==null || x_label_list.size()!=value_list.size()) {
					return dataset;
				}
				for(int i=0;i<x_label_list.size();i++) {
					dataset.addValue(Double.parseDouble(value_list.get(i)), categoryType, x_label_list.get(i));
				}
			}
		return dataset;
	}
	
	public void createLineChart(String title, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		this.createLineChart(title, "", "", data, imagePath);
	}
	/*
	 * 创建折线图
	 */
	public void createLineChart(String title,String x_label_direction,String y_label_direction, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = ChartFactory.createLineChart(title, x_label_direction, y_label_direction, dataset);
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		CategoryItemRenderer renderer = plot.getRenderer();
		//设置折线上显示数值
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelsVisible(true);
		
		//x轴label显示不全的时候换行显示
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		
		//输出到图片文件
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, width, height);
	}
}
