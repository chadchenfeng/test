package richword;
/**
 * 使用lowagie拼接pdf
 * @author chenfeng
 * @date 2019年3月21日
 */

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class CreatePdfByLowagie extends PdfPageEventHelper{

	private static final Logger logger=LoggerFactory.getLogger(CreatePdfByLowagie.class);
	public static final int TITLE1=16;
	public static final int SUBTITLE1=14;
	public static final int TITLE2=14;
	public static final int SUBTITLE2=12;
	public static final int TABLETITLE=10;
	public static final int TABLEFONT=8;
	
	private BaseFont bfChinese;
	private Document document;
	
	/**
	 * 
	 * @param paperType 0-纵向打印  1-横向打印
	 * @param filePath  生成的文件路径
	 * @throws IOException 
	 * @throws  
	 */
	public void openDocument(int paperType, String filePath) throws Exception {
		try {
			if(System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
				bfChinese=BaseFont.createFont("C:/Windows/Fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}else {
				bfChinese=BaseFont.createFont("/usr/share/fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
		} catch (Exception e) {
			logger.error("中文字体文件simsun.ttc未找到");
		}

		if(paperType==0) {
			Rectangle pageSize=new Rectangle(677, 960);
			this.document=new Document(pageSize);
		}else {
			Rectangle pageSize=new Rectangle(960, 677);
			this.document=new Document(pageSize);
		}
		this.document.setMargins(this.document.leftMargin(), this.document.rightMargin(), 10, this.document.bottomMargin());
		
		File file=new File(filePath);
		if(!file.exists() && file.createNewFile()) {
			PdfWriter writer=PdfWriter.getInstance(this.document, new FileOutputStream(file));
			writer.setPageEvent(this);
			//设置页头
			insertHeader(paperType);
		}
		
		this.document.open();
	}
	
	public void insertHeader(int paperType) throws Exception {
		Image headImager1 = Image.getInstance("http://a1.gystatic.com/mgt/v2/img/logo1.gif");
		headImager1.scalePercent(70);
		headImager1.setAlignment(Image.ALIGN_LEFT);
		Image headImager2 = Image.getInstance("http://a1.gystatic.com/mgt/v2/img/print-title-bg.png");
		if(paperType==0) {
			headImager2.scalePercent(86,50);
		}else {
			headImager2.scalePercent(125,50);
		}
		
		
		Paragraph headerParagraph=new Paragraph();
		headerParagraph.add(headImager1);
		headerParagraph.add(headImager2);
		
		HeaderFooter header=new HeaderFooter(headerParagraph, false);
		header.setBorder(0);
		this.document.setHeader(header);
	}
	
	public PdfPCell newCell(String text, int fontsize, int colspan, int fontStyle)
	{
		Font font = new Font(bfChinese, fontsize, fontStyle);
		PdfPCell pCell = new PdfPCell(new Phrase(text, font));

		pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pCell.setColspan(colspan);
		pCell.setPadding(3);
		pCell.setBorderWidth(1);

		return pCell;
	}
	
	public PdfPCell newCellNoBorder(String context,int colspan,int fontsize
			,int fontStyle,int elementAlignType,int paddingTop,int paddingBottom) {
		PdfPCell cell=newCell(context,fontsize,colspan,fontStyle);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(elementAlignType);
		cell.setPaddingBottom(paddingBottom);
		cell.setPaddingTop(paddingTop);
		return cell;
	}
	
	public Chunk getChunk(String context, int fontsize, int fontStyle)
	{
		Chunk chunk = new Chunk();

		Font font = new Font(bfChinese, fontsize, fontStyle);

		chunk.append(context);
		chunk.setFont(font);
		return chunk;
	}
	
	
	
	
	
	
	public void insertContext(Map<String,Object> map) throws Exception {
		PdfPTable table=new PdfPTable(12);
		table.setWidthPercentage(100);
		table.setWidths(new int[] {12,10,10,15,10,6,10,10,10,6,15,10});
		
		//拼接表头
		String titleName1="param无锡市XX钢材加工有限公司";
		String subTitle1="("+"param无锡城南库"+")";
		/*PdfPCell cell=newCell(titleName1,CreatePdfByLowagie.TITLE2,8,Font.BOLD);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingBottom(10);
		table.addCell(cell);*/
		
		table.addCell(newCellNoBorder(titleName1,8,CreatePdfByLowagie.TITLE2,Font.BOLD,Element.ALIGN_RIGHT,0,10));
		
		/*cell=newCell(subTitle1,CreatePdfByLowagie.SUBTITLE2,4,Font.NORMAL);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorderWidth(0);
		cell.setPaddingBottom(10);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(subTitle1,4,CreatePdfByLowagie.SUBTITLE2,Font.NORMAL,Element.ALIGN_CENTER,0,10));
		
		String titleName2="加工单";
		String subTitle2="NO:"+"paramJG10190210002";
		/*cell=newCell(titleName2,CreatePdfByLowagie.TITLE1,7,Font.BOLD);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingBottom(10);
		cell.setPaddingRight(20);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(titleName2,7,CreatePdfByLowagie.TITLE1,Font.BOLD,Element.ALIGN_RIGHT,0,10));
		/*cell=newCell(subTitle2,CreatePdfByLowagie.TABLETITLE,5,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingBottom(10);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(subTitle2,5,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_CENTER,0,10));
		
		
		String ownerName="货主："+"param江苏省无锡市通达钢铁集团有限公司";
		/*cell=newCell(ownerName,CreatePdfByLowagie.TABLETITLE,4,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(ownerName,4,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		String ownerContact="货主联系人:"+"王小亮";
		/*cell=newCell(ownerContact,CreatePdfByLowagie.TABLETITLE,3,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(ownerContact,3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_CENTER,0,5));
		
		String ownerContactPhone="联系人手机:"+"13538167890";
		/*cell=newCell(ownerContactPhone,CreatePdfByLowagie.TABLETITLE,3,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(ownerContactPhone,3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_CENTER,0,5));
		
		String expectedDeliveryDate="期望交货日期:"+"2019/12/29";
		/*cell=newCell(expectedDeliveryDate,CreatePdfByLowagie.TABLETITLE,2,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder(expectedDeliveryDate,3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_RIGHT,0,5));
		
		/*cell=newCell("加工方式：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("加工方式：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("纵剪",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		
		table.addCell(newCellNoBorder("纵剪",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("机组：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("机组：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("paramA01机",CreatePdfByLowagie.TABLETITLE,9,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("paramA01机",9,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		PdfPCell cell=newCell("货物编号",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("产地",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("品名",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("规格",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("材质",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("件数",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("重量",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("计重",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("用料",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("分段",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("加工规格",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("库区库位",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		table.addCell(cell);
		
		//插入表格数据
		@SuppressWarnings("unchecked")
		List<Map<String,String>> list = (List<Map<String, String>>) map.get("datalist");
		for(Map<String,String> record:list) {
			cell=newCell(record.get("inventory_code"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("brand_name"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("breed_name"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("spec_name"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("material_name"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("number"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("weight"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("jz"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("process_weight"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("section_number"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("jggg"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
			cell=newCell(record.get("storage"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
			table.addCell(cell);
		}
		cell=newCell("合计：",CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("",CreatePdfByLowagie.TABLEFONT,4,Font.NORMAL);
		table.addCell(cell);
		cell=newCell((String) map.get("totalNumber"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell((String) map.get("totalWeight"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("",CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell((String) map.get("totalProcessWeight"),CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell("",CreatePdfByLowagie.TABLEFONT,3,Font.NORMAL);
		table.addCell(cell);
		
		cell=newCell("加工要求：",CreatePdfByLowagie.TABLEFONT,1,Font.NORMAL);
		table.addCell(cell);
		cell=newCell((String) map.get("remark"),CreatePdfByLowagie.TABLEFONT,11,Font.NORMAL);
		table.addCell(cell);
		
		
		/*cell=newCell("制单：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("制单：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
		/*cell=newCell((String) map.get("creator"),CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder((String) map.get("creator"),1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
		/*cell=newCell("制单时间："+(String) map.get("create_time"),CreatePdfByLowagie.TABLETITLE,3,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("制单时间："+(String) map.get("create_time"),3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
/*		cell=newCell("审核："+(String) map.get("audit_name"),CreatePdfByLowagie.TABLETITLE,2,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("审核："+(String) map.get("audit_name"),2,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
		/*cell=newCell("签字：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("签字：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
		/*cell=newCell("",CreatePdfByLowagie.TABLETITLE,4,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingTop(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("",4,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,5,5));
		
		/*cell=newCell("仓库地址：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("仓库地址：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell((String) map.get("warehouse_name"),CreatePdfByLowagie.TABLETITLE,3,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder((String) map.get("warehouse_name"),3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("联系人：",CreatePdfByLowagie.TABLETITLE,1,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("联系人：",1,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell((String) map.get("warehouseContract"),CreatePdfByLowagie.TABLETITLE,2,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder((String) map.get("warehouseContract"),2,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("手机："+(String) map.get("warehouseContractMobile"),CreatePdfByLowagie.TABLETITLE,2,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("手机："+(String) map.get("warehouseContractMobile"),2,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		/*cell=newCell("电话："+(String) map.get("warehouseContractPhone"),CreatePdfByLowagie.TABLETITLE,3,Font.NORMAL);
		cell.setBorderWidth(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		table.addCell(cell);*/
		table.addCell(newCellNoBorder("电话："+(String) map.get("warehouseContractPhone"),3,CreatePdfByLowagie.TABLETITLE,Font.NORMAL,Element.ALIGN_LEFT,0,5));
		
		this.document.add(table);
	}
	
	public void closeDocument() {
		this.document.close();
	}
	
	public static void main(String[] args) throws Exception {
		CreatePdfByLowagie createPdfByLowagie = new CreatePdfByLowagie();
		createPdfByLowagie.openDocument(0, "F:/tmp/手动拼接.pdf");
		Map<String,Object> map=new HashMap<>();
		List<Map<String,String>> list=new ArrayList<>();
		Map<String,String> m=new HashMap<>();
		m.put("inventory_code", "AT001");
		m.put("brand_name", "沙钢");
		m.put("breed_name", "热卷");
		m.put("spec_name", "3.0*1250");
		m.put("material_name", "Q235B");
		m.put("number", "1");
		m.put("weight", "28.09");
		m.put("jz", "抄码");
		m.put("process_weight", "28.09");
		m.put("section_number", "2");
		m.put("jggg", "3000*100件\r\n" + 
				" 2500*100件   \r\n" + 
				"2800*50件");
		m.put("storage", "A仓01");
		list.add(m);
		m=new HashMap<>();
		m.put("inventory_code", "AT002");
		m.put("brand_name", "沙钢");
		m.put("breed_name", "热卷");
		m.put("spec_name", "3.0*1250");
		m.put("material_name", "Q235B");
		m.put("number", "1");
		m.put("weight", "28.09");
		m.put("jz", "抄码");
		m.put("process_weight", "28.09");
		m.put("section_number", "2");
		m.put("jggg", "4000");
		m.put("storage", "A仓01");
		list.add(m);
		m=new HashMap<>();
		m.put("inventory_code", "AT003");
		m.put("brand_name", "沙钢");
		m.put("breed_name", "热轧花纹卷");
		m.put("spec_name", "3.0*1250");
		m.put("material_name", "H－Q235B");
		m.put("number", "1");
		m.put("weight", "28.09");
		m.put("jz", "抄码");
		m.put("process_weight", "28.09");
		m.put("section_number", "2");
		m.put("jggg", "6000*80件\r\n" + 
				"余卷入库");
		m.put("storage", "A仓01");
		list.add(m);
		map.put("datalist", list);
		map.put("totalNumber", "3");
		map.put("totalWeight", "168.635");
		map.put("totalProcessWeight", "168.635");
		map.put("remark", "每包重量不超6吨，成品过磅，注意平直度，表面有生锈需通知");
		map.put("creator", "王美丽");
		map.put("audit_name", "王美丽");
		map.put("warehouse_name", "无锡市xxx区xxx路xxx号");
		map.put("create_time", "2018/3/5 12:00:00");
		map.put("warehouseContract", "林经理");
		map.put("warehouseContractMobile", "13444456789");
		map.put("warehouseContractPhone", "0531-33300222");
		createPdfByLowagie.insertContext(map);
		createPdfByLowagie.closeDocument();
	}
}
