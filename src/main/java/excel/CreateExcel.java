package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class CreateExcel {
	private String date;
	private String scan_pv;
	private String day_vistor_scan_count;
	private String day_new_user_scan_count;
	
	
	
	public CreateExcel(String date, String scan_pv, String day_vistor_scan_count, String day_new_user_scan_count) {
		this.date = date;
		this.scan_pv = scan_pv;
		this.day_vistor_scan_count = day_vistor_scan_count;
		this.day_new_user_scan_count = day_new_user_scan_count;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getScan_pv() {
		return scan_pv;
	}



	public void setScan_pv(String scan_pv) {
		this.scan_pv = scan_pv;
	}



	public String getDay_vistor_scan_count() {
		return day_vistor_scan_count;
	}



	public void setDay_vistor_scan_count(String day_vistor_scan_count) {
		this.day_vistor_scan_count = day_vistor_scan_count;
	}



	public String getDay_new_user_scan_count() {
		return day_new_user_scan_count;
	}



	public void setDay_new_user_scan_count(String day_new_user_scan_count) {
		this.day_new_user_scan_count = day_new_user_scan_count;
	}



	public static void main(String[] args) throws IOException {
		//第一步创建workbook
		HSSFWorkbook wb=new HSSFWorkbook();
		//第二步创建sheet
		HSSFSheet jichusheet = wb.createSheet("基础数据");
		HSSFSheet pinxiangsheet = wb.createSheet("品项数据数据");
		//第三步创建行row:添加表头0行
		HSSFRow jichurow = jichusheet.createRow(0);
		HSSFRow pinxiangrow = pinxiangsheet.createRow(0);
		//设置单元格式
		HSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER); //居中
		
		//第四步创建单元格
		Properties properties=null;
		InputStreamReader inStream=null;
		try {
			inStream = new InputStreamReader(new FileInputStream("src/main/java/dailyreportexcelhead.properties"));
			properties=new Properties();
			properties.load(inStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String jcsjhead = properties.getProperty("jcsj");
		String[] jcsjheadlist = jcsjhead.split(",");
		String pxsjhead = properties.getProperty("pxsj");
		String[] pxsjheadlist = pxsjhead.split(",");
		for(int i=0;i<jcsjheadlist.length;i++) {
			jichusheet.setColumnWidth(i, 18*256);
			HSSFCell cell = jichurow.createCell(i);
			cell.setCellValue(jcsjheadlist[i]);
			cell.setCellStyle(style);
		}
		for(int i=0;i<pxsjheadlist.length;i++) {
			pinxiangsheet.setColumnWidth(i, 18*256);
			HSSFCell cell = pinxiangrow.createCell(i);
			cell.setCellValue(pxsjheadlist[i]);
			cell.setCellStyle(style);
		}
		
		//第五步插入数据
		CreateExcel data1=new CreateExcel("20180925","10","11","12");
		CreateExcel data2=new CreateExcel("20180926","9","11","12");
		CreateExcel data3=new CreateExcel("20180927","8","11","12");
		CreateExcel data4=new CreateExcel("20180928","7","11","12");
		List<CreateExcel> datalist=new ArrayList<CreateExcel>();
		datalist.add(data1);
		datalist.add(data2);
		datalist.add(data3);
		datalist.add(data4);
		for(int i=0;i<datalist.size();i++) {
			CreateExcel value = datalist.get(i);
			HSSFRow datarow = jichusheet.createRow(i+1);
			datarow.createCell(0).setCellValue(value.getDate());
			datarow.createCell(1).setCellValue(value.getScan_pv());
			datarow.createCell(2).setCellValue(value.getDay_vistor_scan_count());
			datarow.createCell(3).setCellValue(value.getDay_new_user_scan_count());
		}
		
		for(int i=0;i<datalist.size();i++) {
			CreateExcel value = datalist.get(i);
			HSSFRow datarow = pinxiangsheet.createRow(i+1);
			datarow.createCell(0).setCellValue(value.getDate());
			datarow.createCell(1).setCellValue(value.getScan_pv());
			datarow.createCell(2).setCellValue(value.getDay_vistor_scan_count());
			datarow.createCell(3).setCellValue(value.getDay_new_user_scan_count());
		}
		
		//第六步将生成excel文件保存到指定路径下
		try {
			FileOutputStream fout=new FileOutputStream("F:/tmp/中间页数据报表.xlsx");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inStream.close();
		System.out.println("生成文件成功，请查看……");
	}
	
	
}
