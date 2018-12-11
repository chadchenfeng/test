package richword;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYZToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class CreateGraphByJfreechartApi {
	
	private static StandardChartTheme chartTheme;
	private int width; //图片宽度
	private int height; //图片高度
	static {
		chartTheme=new StandardChartTheme("CN");
		//设置标题字体
		chartTheme.setExtraLargeFont(new Font("宋体", Font.CENTER_BASELINE, 32));
		//设置轴向字体
		chartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 22));
		//设置图例字体
		chartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 25));
	}

	public CreateGraphByJfreechartApi() {
		this.width=640;
		this.height=480;

	}
	public CreateGraphByJfreechartApi(int width, int height) {
		this.width = width;
		this.height = height;
	}


	public void createBarChart(String title, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		createBarChart(title,"","",data,imagePath,true);
	}
	
	/*
	 * 创建柱状图
	 *data 传入的数据格式：
	 * {categorytype1:{x_label:["201805","201806","201807"],value:["2","8","6"]}
	 * ,categorytype2:{x_label:["201805","201806","201807"],value:["18","15","10"]}
	 * }
	 * 
	 */
	public void createBarChart(String title,String x_label_direction,String y_label_direction, Map<String,Map<String,List<String>>> data,String imagePath,boolean showvalue) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = ChartFactory.createBarChart(title, x_label_direction, y_label_direction, dataset);
		chart.setBackgroundPaint(Color.WHITE);
//		chart.setAntiAlias(false);  ////关闭全部反锯齿
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(null);
		
		BarRenderer renderer=(BarRenderer) plot.getRenderer();
		renderer.setItemMargin(0.05f);
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		if(showvalue) {
			renderer.setDefaultItemLabelsVisible(true);
			renderer.setDefaultSeriesVisible(true);
		}
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 22)); //设置x轴坐标值的字体
		for(Map<String,List<String>> map:data.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>7 && list.size()<=35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			}else if (list.size()>35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
			}
			break;
		}
		
		chart.getCategoryPlot().setRenderer(renderer);
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
		
	}
	
	/*
	 * 创建折线图，该方法适用于x轴坐标为日期值
	 * 
	 * @type  x轴显示是按日显示还是还月显示
	 * @x_number x轴显示个数
	 * @legendvisible 类别图例是否显示
	 */
	public void createSeriesChart(String title,String x_label_direction,String y_label_direction
			, Map<String,Map<String,List<String>>> data,String imagePath
			,boolean showvalue,String type,int x_number,boolean legendvisible) throws Exception {
		XYDataset paraseXYDateDataSet = paraseXYDateDataSet(data, type);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, x_label_direction, y_label_direction,paraseXYDateDataSet);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		XYPlot plot = (XYPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(null);
		
		XYItemRenderer renderer = plot.getRenderer();
		//设置线条的粗细
		for(int i=0;i<data.size();i++) {
			renderer.setSeriesStroke(i, new BasicStroke(3.0f));
		}
		//设置折线上显示数值
		if(showvalue) {
			renderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
			renderer.setDefaultItemLabelsVisible(true);
		}
		int maxXaxisCount = getMaxXaxisCount(data);
		DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
		if(maxXaxisCount>x_number) {
			Map<String, String> minMaxXValue = getMinMaxXValue(data);
			if(type.toLowerCase().equals("day")) {
				int dayInterval = getDayInterval(minMaxXValue.get("min"), minMaxXValue.get("max"));
				int unit=(int) Math.round(Double.valueOf(maxXaxisCount)/Double.valueOf(x_number));
				dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, unit*((dayInterval/maxXaxisCount)<1?1:(dayInterval/maxXaxisCount))));
				dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyyMMdd"));
			}else if (type.toLowerCase().equals("month")) {
				int unit=(int) Math.round(Double.valueOf(maxXaxisCount)/Double.valueOf(x_number));
				dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, unit));
				dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyyMM"));
			}
		}
		dateAxis.setUpperMargin(0.04);
		if(!legendvisible) {
			chart.getLegend().setVisible(false);
		}
		
		//输出到图片文件
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, width, height);
		
	}
	
	public void createSeriesChart(String title,String x_label_direction,String y_label_direction
			, Map<String,Map<String,List<String>>> data,String imagePath
			,boolean showvalue,int x_number) throws Exception{
		this.createSeriesChart(title, x_label_direction, y_label_direction, data, imagePath, showvalue,"day", x_number,true);
	}
	
	
	public void createLineChart(String title, Map<String,Map<String,List<String>>> data,String imagePath) throws IOException {
		this.createLineChart(title, "", "", data, imagePath,true);
	}
	/*
	 * 创建折线图
	 */
	public void createLineChart(String title,String x_label_direction,String y_label_direction, Map<String,Map<String,List<String>>> data,String imagePath,boolean showvalue) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = ChartFactory.createLineChart(title, x_label_direction, y_label_direction, dataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(null);
		
		CategoryItemRenderer renderer = plot.getRenderer();
		//设置线条的粗细
		for(int i=0;i<data.size();i++) {
			renderer.setSeriesStroke(i, new BasicStroke(3.0f));
		}
		
		
		//设置折线上显示数值
		if(showvalue) {
			renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setDefaultItemLabelsVisible(true);
		}
		//x轴label显示不全的时候换行显示
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 22)); //设置x轴坐标值的字体
		for(Map<String,List<String>> map:data.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>7 && list.size()<=35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			}else if (list.size()>35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
			}
			break;
		}
		
		domainAxis.setLowerMargin(0.02);
		domainAxis.setUpperMargin(0.02);

		//输出到图片文件
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, width, height);
	}
	
	
	public void createXYLineChart(String title, Map<String,Map<String,List<String>>> data,String imagePath,boolean showvalue) throws Exception {
		this.createXYLineChart(title,"","",data,imagePath,showvalue,10);
	}
	
	/*
	 * 创建折线图，x轴间隔显示，该方法适用于x轴坐标值为数值
	 * x_number  为x轴要显示的标签个数
	 */
	public void createXYLineChart(String title,String x_label_direction,String y_label_direction
			, Map<String,Map<String,List<String>>> data,String imagePath,boolean showvalue,int x_number) throws Exception {
		XYSeriesCollection parseXYDataset = parseXYDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart=ChartFactory.createXYLineChart(title, x_label_direction, y_label_direction, parseXYDataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		XYPlot plot = (XYPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(null);
		
		XYItemRenderer renderer = plot.getRenderer();
		//设置线条的粗细
		for(int i=0;i<data.size();i++) {
			renderer.setSeriesStroke(i, new BasicStroke(3.0f));
		}
		//设置折线上显示数值
		if(showvalue) {
			renderer.setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
			renderer.setDefaultItemLabelsVisible(true);
		}
		int maxXaxisCount = getMaxXaxisCount(data);
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		if(maxXaxisCount>x_number) {
			int unit=(int) Math.round(Double.valueOf(maxXaxisCount)/Double.valueOf(x_number));
			xAxis.setTickUnit(new NumberTickUnit(unit));  //设置显示间隔单位
		}
		final DecimalFormat format = new DecimalFormat("#");
		format.setMaximumFractionDigits(2);
		xAxis.setNumberFormatOverride(format);  //将x轴中的数字的逗号消除
		
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 22)); //设置x轴坐标值的字体
		plot.getDomainAxis().setLowerMargin(0.02);
		plot.getDomainAxis().setUpperMargin(0.02);
		
		//输出到图片文件
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, width, height);
	}
	
	//创建饼图
	public void createPieChart(String title,Map<String,String> data,String imagePath) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Entry<String, String> entry:data.entrySet()) {
			dataset.setValue(entry.getKey(), Double.valueOf(entry.getValue()));
		}
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		PiePlot plot= (PiePlot) chart.getPlot();
		plot.setNoDataMessage("无数据可供显示！"); // 没有数据的时候显示的内容
		//设置背景透明
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(null);
		
		DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题,表示小数点后保留两位。
		NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
		StandardPieSectionLabelGenerator sp = new StandardPieSectionLabelGenerator(
				"{0}:{2}", nf, df);
		plot.setLabelGenerator(sp);
		
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, width, height);
	}
	
	
	/*
	 * 柱状图与折线图合并
	 * bardata  柱状图数据
	 * linedata  折线图数据
	 * secondYaxis  标志折线是否与柱状使用的y轴区分开
	 * x_number x轴显示的刻度个数
	 * 该方法有限制，柱状图只能有一组数据，如果有多组数据显示时数据多的会覆盖数据少的
	 */
	public void createBarLineChar(String title,String x_label_direction,String y_label_direction,String y2_label_direction
			,Map<String,Map<String,List<String>>> bardata
			,Map<String,Map<String,List<String>>> linedata,String imagePath,boolean secondYaxis,int x_number) throws IOException {
		XYSeriesCollection barDataset = parseXYDataset(bardata);
		ChartFactory.setChartTheme(chartTheme);
		if(secondYaxis) {
			y_label_direction=y_label_direction+" (柱状图)";
			y2_label_direction=y2_label_direction+" (折线图)";
		}
		JFreeChart chart =ChartFactory.createXYBarChart(title, x_label_direction, false, y_label_direction, barDataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿
		chart.setBackgroundPaint(Color.WHITE);
		XYPlot plot=(XYPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.3f);
		plot.setOutlinePaint(null);
		
		int barcount = getMaxXaxisCount(bardata);
		int linecount = getMaxXaxisCount(linedata);
		int maxcount=Math.max(barcount,linecount);
		int unit=(int) Math.round(Double.valueOf(maxcount)/Double.valueOf(x_number));
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setTickUnit(new NumberTickUnit(unit<=0?1:unit));  //设置显示间隔单位
		
		final DecimalFormat format = new DecimalFormat("#");
		format.setMaximumFractionDigits(2);
		xAxis.setNumberFormatOverride(format);  //将x轴中的数字的逗号消除
		
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setMargin(0.8);   //设置柱间的间隔
	
		int barsize=bardata.size();
		int linesize=linedata.size();
		List<XYSeriesCollection> lineDataset = parseXYDatasetList(linedata);
		for(int i=barsize,j=0;i<barsize+linesize;i++,j++) {
			plot.setDataset(i, lineDataset.get(j));
			if(secondYaxis) {
				plot.mapDatasetToRangeAxis(i,1);  //设置该折线的y轴使用另一条y轴
			}
			
			XYItemRenderer lineandshaperenderer=new StandardXYItemRenderer();
			lineandshaperenderer.setDefaultToolTipGenerator(new StandardXYZToolTipGenerator());
			lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3.0f));//设置折线粗细
			plot.setRenderer(i,lineandshaperenderer);
			
		}
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);//折线在柱面前面显示
		if(secondYaxis) {
			final ValueAxis axis2 = new NumberAxis(y2_label_direction);
	        plot.setRangeAxis(1, axis2);
		}
		
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
	}
	public void createBarChartMergeLine(String title
			,Map<String,Map<String,List<String>>> bardata
			,Map<String,Map<String,List<String>>> linedata,String imagePath) throws IOException {
		this.createBarChartMergeLine(title, "", "","", bardata, linedata, imagePath,false);
	}
	public void createBarChartMergeLineIn2yaxis(String title
			,Map<String,Map<String,List<String>>> bardata
			,Map<String,Map<String,List<String>>> linedata,String ylabel,String imagePath) throws IOException {
		this.createBarChartMergeLine(title, "", "",ylabel, bardata, linedata, imagePath,true);
	}
	/*
	 * 柱状图与折线图合并
	 * bardata  柱状图数据
	 * linedata  折线图数据
	 * secondYaxis  标志折线是否与柱状使用的y轴区分开
	 */
	public void createBarChartMergeLine(String title,String x_label_direction,String y_label_direction,String y2_label_direction
			,Map<String,Map<String,List<String>>> bardata
			,Map<String,Map<String,List<String>>> linedata,String imagePath,boolean secondYaxis) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(bardata);
		ChartFactory.setChartTheme(chartTheme);
		if(secondYaxis) {
			y_label_direction=y_label_direction+" (柱状图)";
			y2_label_direction=y2_label_direction+" (折线图)";
		}
		JFreeChart chart = ChartFactory.createBarChart(title, x_label_direction, y_label_direction, dataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿

		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.3f);
		plot.setOutlinePaint(null);
		
		//x轴label显示不全的时候换行显示
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 22)); //设置x轴坐标值的字体
		for(Map<String,List<String>> map:bardata.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>7 && list.size()<=35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			}else if (list.size()>35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
			}
			break;
		}
		
		domainAxis.setLowerMargin(0.02);  
		domainAxis.setUpperMargin(0.02);
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setMaximumBarWidth(0.05);
		
		int barsize=bardata.size();
		int linesize=linedata.size();
		List<DefaultCategoryDataset> lineDataset = parseDataset(linedata);
		for(int i=barsize,j=0;i<barsize+linesize;i++,j++) {
			plot.setDataset(i, lineDataset.get(j));
			if(secondYaxis) {
				plot.mapDatasetToRangeAxis(i,1);  //设置该折线的y轴使用另一条y轴
			}
			LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
			lineandshaperenderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
			lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3.0f));//设置折线粗细
			plot.setRenderer(i, lineandshaperenderer);
			
		}
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);//折线在柱面前面显示
		if(secondYaxis) {
			final ValueAxis axis2 = new NumberAxis(y2_label_direction);
			axis2.setLabelFont(new Font("宋体", Font.PLAIN, 22));
			axis2.setTickLabelFont(new Font("宋体", Font.PLAIN, 22));
	        plot.setRangeAxis(1, axis2);
		}
		
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
	}
	
	
	//简单堆积柱状图
	public void createStackedBar(String title,String x_label_direction,String y_label_direction
			,Map<String,Map<String,List<String>>> bardata,String imagePath) throws IOException {
		DefaultCategoryDataset dataset=paraseDataset(bardata);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = 
				ChartFactory.createStackedBarChart(title, x_label_direction, y_label_direction, dataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿

		CategoryPlot plot=(CategoryPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.3f);
		plot.setOutlinePaint(null);
		
		//x轴label显示不全的时候换行显示
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setMaximumCategoryLabelLines(6);
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 22)); //设置x轴坐标值的字体
		for(Map<String,List<String>> map:bardata.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>7 && list.size()<=35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			}else if (list.size()>35) {
				domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
			}
			break;
		}
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setMaximumBarWidth(0.08);
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
	}
	
	
	 
	//堆积区域图
	public void createStackedArea(String title,String x_label_direction,String y_label_direction
			,Map<String,Map<String,List<String>>> data,String imagePath,int x_number) throws Exception {
		TableXYDataset dataset=paraseTableXYDataset(data);
		ChartFactory.setChartTheme(chartTheme);
		JFreeChart chart = 
				ChartFactory.createStackedXYAreaChart(title, x_label_direction, y_label_direction, dataset);
//		chart.setAntiAlias(false); 
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);	//关闭文字的抗锯齿

		XYPlot plot=(XYPlot) chart.getPlot();
		//设置背景透明
		plot.setBackgroundAlpha(0.3f);
		plot.setOutlinePaint(null);
		
		DateAxis dateAxis = new DateAxis();
		int maxXaxisCount = getMaxXaxisCount(data);
		if(maxXaxisCount>x_number) {
			Map<String, String> minMaxXValue = getMinMaxXValue(data);
			int dayInterval = getDayInterval(minMaxXValue.get("min"), minMaxXValue.get("max"));
			int unit=(int) Math.round(Double.valueOf(maxXaxisCount)/Double.valueOf(x_number));
			dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, unit*((dayInterval/maxXaxisCount)<1?1:(dayInterval/maxXaxisCount))));
			dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyyMMdd"));
		}
		dateAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 22));
		dateAxis.setUpperMargin(0.0);
		dateAxis.setLowerMargin(0.0);
		plot.setDomainAxis(dateAxis);
		plot.getRangeAxis().setUpperMargin(0.0);
		
		File file=new File(imagePath);
		ChartUtils.saveChartAsPNG(file, chart, this.width, this.height);
	}
	
	
	
	
	
	public  XYDataset paraseXYDateDataSet(Map<String,Map<String,List<String>>> data,String type) throws NumberFormatException, ParseException {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
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
			
			TimeSeries s1 = new TimeSeries(categoryType);
			
			for(int i=0;i<x_label_list.size();i++) {
				if(type.toLowerCase().equals("month")) {
					s1.add(new Month(new SimpleDateFormat("yyyyMM").parse(x_label_list.get(i))), Double.parseDouble(value_list.get(i)));
				}else{
					s1.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(x_label_list.get(i))), Double.parseDouble(value_list.get(i).replace("%", "")));
				}
			}
			
			dataset.addSeries(s1);
		}
		return dataset;
	}
	
	public TableXYDataset  paraseTableXYDataset(Map<String,Map<String,List<String>>> data) throws NumberFormatException, ParseException {
		TimeTableXYDataset  dataset=new TimeTableXYDataset();
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
				dataset.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(x_label_list.get(i))), Double.valueOf(value_list.get(i)),categoryType);
			}
		}
		return dataset;
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
					dataset.addValue(Double.parseDouble(value_list.get(i).replace("%", "")), categoryType, x_label_list.get(i));
				}
			}
		return dataset;
	}
	
	public List<DefaultCategoryDataset> parseDataset(Map<String,Map<String,List<String>>> data){
		List<DefaultCategoryDataset> list=new ArrayList<>();
		for(Map.Entry<String, Map<String,List<String>>> entry:data.entrySet()) {
			DefaultCategoryDataset dataset=new DefaultCategoryDataset();
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
				return null;
			}
			for(int i=0;i<x_label_list.size();i++) {
				dataset.addValue(Double.parseDouble(value_list.get(i).replace("%", "")), categoryType, x_label_list.get(i));
			}
			list.add(dataset);
		}
		return list;
		
	}
	public XYSeriesCollection parseXYDataset(Map<String,Map<String,List<String>>> data){
		XYSeriesCollection dataset = new XYSeriesCollection();
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
			XYSeries xyseries=new XYSeries(categoryType);
			for(int i=0;i<x_label_list.size();i++) {
				xyseries.add(Double.parseDouble(x_label_list.get(i)),Double.parseDouble(value_list.get(i).replace("%", "")));
			}
			
			dataset.addSeries(xyseries);
		}
		return dataset;
	}
	
	public List<XYSeriesCollection> parseXYDatasetList(Map<String,Map<String,List<String>>> data){
		List<XYSeriesCollection> list=new ArrayList<>();
		for(Map.Entry<String, Map<String,List<String>>> entry:data.entrySet()) {
			XYSeriesCollection dataset = new XYSeriesCollection();
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
			XYSeries xyseries=new XYSeries(categoryType);
			for(int i=0;i<x_label_list.size();i++) {
				xyseries.add(Double.parseDouble(x_label_list.get(i)),Double.parseDouble(value_list.get(i).replace("%", "")));
			}
			dataset.addSeries(xyseries);
			list.add(dataset);
		}
		return list;
	}
	
	
	public int getMaxXaxisCount(Map<String,Map<String,List<String>>> data) {
		int i=0;
		for(Map<String,List<String>> map:data.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>i) {
				i=list.size();
			}
		}
		return i;
	}
	
	public Map<String,String> getMinMaxXValue(Map<String,Map<String,List<String>>> data) {
		List<String> t=null;
		int i=0;
		for(Map<String,List<String>> map:data.values()) {
			List<String> list = map.get("x_label");
			if(list.size()>i) {
				i=list.size();
				t=list;
			}
		}
		Map<String,String> retmap=new HashMap<String,String>();
		for(int j=0;j<t.size();j++) {
			if(j==0) {
				retmap.put("min", t.get(j));
				retmap.put("max", t.get(j));
			}else {
				String str=t.get(j);
				
				if(str.compareTo(retmap.get("min"))<0) {
					retmap.put("min", str);
				}
				if(str.compareTo(retmap.get("max"))>0) {
					retmap.put("max", str);
				}
			}
		}
		
		return retmap;
	}
	
	public int getDayInterval(String day1,String day2) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date1 = simpleDateFormat.parse(day1.length()==6?day1+"01":day1);
		Date date2 = simpleDateFormat.parse(day2.length()==6?day2+"01":day2);
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
	}
	

	public static void main(String[] args) throws Exception {
		Map<String,Map<String,List<String>>> map=new HashMap<String,Map<String,List<String>>>();
		Map<String,List<String>> categroyMap1 =new HashMap<String,List<String>>();
		List<String> x_label_list1=new ArrayList<String>();
		x_label_list1.add("20180501");
		x_label_list1.add("20180502");
		x_label_list1.add("20180503");
		x_label_list1.add("20180504");
		x_label_list1.add("20180505");
		x_label_list1.add("20180506");
		
		List<String> value_list1=new ArrayList<String>();
		value_list1.add("2");
		value_list1.add("20.01");
		value_list1.add("6.18");
		value_list1.add("5.56");
		value_list1.add("3.56");
		value_list1.add("1.56");
		categroyMap1.put("x_label", x_label_list1);
		categroyMap1.put("value", value_list1);
		map.put("蓝莓优酪乳", categroyMap1);
		
		Map<String,List<String>> categroyMap2 =new HashMap<String,List<String>>();
		List<String> x_label_list2=new ArrayList<String>();
		x_label_list2.add("20180501");
		x_label_list2.add("20180502");
		x_label_list2.add("20180503");
		x_label_list2.add("20180504");
		x_label_list2.add("20180505");
		x_label_list2.add("20180506");
		
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
		
//		new CreateGraphByJfreechartApi(500,500).createBarChart("单UV成本柱状图",map,"F:/tmp/dataworks/单uv成本柱状图.png");
		
//		new CreateGraphByJfreechartApi().createLineChart("单UV成本折线图", map, "F:/tmp/dataworks/单uv成本折线图.png");
//		new CreateGraphByJfreechartApi().createXYLineChart("折线图_间隔显示x轴", map, "F:/tmp/dataworks/折线图_间隔显示x轴.png",true);
		
		//饼图
		Map<String,String> piemap=new HashMap<>();
		piemap.put("天水长城-陇上花牛苹果", "7135");
		piemap.put("东君乳业-红枣牛奶", "3062");
		piemap.put("维维乳业-维维原味酸", "2015");
		piemap.put("天水长城-陇上花牛苹果-清真版", "1651");
		piemap.put("临沂特仑-特仑牧场纯牛奶", "1373");
		piemap.put("其他扫码", "15573");
//		new CreateGraphByJfreechartApi().createPieChart("中间页-扫码项目分布", piemap, "F:/tmp/dataworks/中间页-扫码项目分布.png");
		
		//柱状图与折线图合并
		Map<String,Map<String,List<String>>> linemap=new HashMap<String,Map<String,List<String>>>();
		Map<String,List<String>> categroyMap_line =new HashMap<String,List<String>>();
		List<String> x_label_list_line=new ArrayList<String>();
		x_label_list_line.add("20180501");
		x_label_list_line.add("20180601");
		x_label_list_line.add("20180701");
		List<String> value_list_line=new ArrayList<String>();
		value_list_line.add("10");
		value_list_line.add("8.01");
		value_list_line.add("6.18");
		categroyMap_line.put("x_label", x_label_list_line);
		categroyMap_line.put("value", value_list_line);
		linemap.put("折线图1", categroyMap_line);
		Map<String,List<String>> categroyMap_line1 =new HashMap<String,List<String>>();
		List<String> x_label_list_line1=new ArrayList<String>();
		x_label_list_line1.add("20180601");
		x_label_list_line1.add("20180701");
		x_label_list_line1.add("20180801");
		List<String> value_list_line1=new ArrayList<String>();
		value_list_line1.add("120");
		value_list_line1.add("118.01");
		value_list_line1.add("116.18");
		categroyMap_line1.put("x_label", x_label_list_line1);
		categroyMap_line1.put("value", value_list_line1);
		linemap.put("折线图2", categroyMap_line1);
//		new CreateGraphByJfreechartApi().createBarLineChar("柱状图+折线图","","","",map, linemap, "F:/tmp/dataworks/柱状图+折线图.png",false,5);
//		new CreateGraphByJfreechartApi().createBarChartMergeLineIn2yaxis("柱状图+折线图",map, linemap,"百分比", "F:/tmp/dataworks/柱状图+折线图_双y轴.png");
		
		
		Map<String,Map<String,List<String>>> stackmap=new LinkedHashMap<String,Map<String,List<String>>>();
		Map<String,List<String>> categroyMap_s1 =new HashMap<String,List<String>>();
		List<String> x_label_list_s1=new ArrayList<String>();
		x_label_list_s1.add("销售营收");
		x_label_list_s1.add("新增用户");
		x_label_list_s1.add("运营营收");
		x_label_list_s1.add("广告营收");
		List<String> value_list_s1=new ArrayList<String>();
		value_list_s1.add("8.67");
		value_list_s1.add("7.55");
		value_list_s1.add("20.35");
		value_list_s1.add("3.84");
		categroyMap_s1.put("x_label", x_label_list_s1);
		categroyMap_s1.put("value", value_list_s1);
		Map<String,List<String>> categroyMap_s2 =new HashMap<String,List<String>>();
		List<String> x_label_list_s2=new ArrayList<String>();
		x_label_list_s2.add("销售营收");
		x_label_list_s2.add("新增用户");
		x_label_list_s2.add("运营营收");
		x_label_list_s2.add("广告营收");
		List<String> value_list_s2=new ArrayList<String>();
		value_list_s2.add("91.33");
		value_list_s2.add("92.45");
		value_list_s2.add("79.65");
		value_list_s2.add("96.16");
		categroyMap_s2.put("x_label", x_label_list_s2);
		categroyMap_s2.put("value", value_list_s2);
		stackmap.put("已完成", categroyMap_s1);
		stackmap.put("未完成", categroyMap_s2);
		
//		new CreateGraphByJfreechartApi().createStackedBar("堆积柱状图","","",stackmap,"F:/tmp/dataworks/堆积柱状图.png");
		
		new CreateGraphByJfreechartApi(1739,907).createStackedArea("堆积区域图", "", "", map, "F:/tmp/dataworks/堆积区域图.png",3);
		
//		new CreateGraphByJfreechartApi().createSeriesChart("时间折线图","","",map,"F:/tmp/dataworks/时间折线图.png",true,3);
		
	}
}
