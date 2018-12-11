package richword;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFUtil {

	public static void main(String[] args) {
		List<String> csvfiles=new ArrayList<>();
		csvfiles.add("F:/tmp/dataworks/图文分析.csv");
		csvfiles.add("F:/tmp/dataworks/周报_本周中间页新增用户.csv");
		csvfiles.add("F:/tmp/dataworks/周报_新媒体-扫码项目分布.csv");
		csvfiles.add("F:/tmp/dataworks/周报_扫码趋势.csv");
		csvfiles.add("F:/tmp/dataworks/广告发行.csv");
		csvfiles.add("F:/tmp/dataworks/周报_中间页.csv");
		csvfiles.add("F:/tmp/dataworks/周报_销售周数据.csv");
		csvToXLSX(csvfiles,"F:/tmp/dataworks/运营数据周报数据.xlsx");
	}
	public static void csvToXLSX(List<String> csvfiles,String xlsxfile) {
	    try {
	        XSSFWorkbook workBook = new XSSFWorkbook();
	        for(String csvfile:csvfiles) {
	        	String t=csvfile.substring(csvfile.lastIndexOf("/")+1);
	        	String sheetname=t.substring(0, t.lastIndexOf("."));
	        	XSSFSheet sheet = workBook.createSheet(sheetname);
	        	String currentLine=null;
		        int RowNum=0;
		        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvfile), "gbk"));
		        while ((currentLine = br.readLine()) != null) {
		            String str[] = currentLine.split(",");
		            XSSFRow currentRow=sheet.createRow(RowNum);
		            for(int i=0;i<str.length;i++){
		                currentRow.createCell(i).setCellValue(str[i]);
		            }
		            RowNum++;
		        }
		        br.close();
	        }
	        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxfile);
	        workBook.write(fileOutputStream);
	        fileOutputStream.close();
	        System.out.println("csv 转换成 xlsx 完成");
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage()+"Exception in try");
	    }
	}
}
