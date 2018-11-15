package richword;

import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.sl.usermodel.TableShape;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

public class CreatePPTByPoiApi {

	public static void main(String[] args) throws Exception {
		List<Map<String,String>> data=new ArrayList<>();
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("product_name", "蓝莓优酪乳");
		map1.put("order_quantity", "600000");
		map1.put("shipment", "292786");
		map1.put("landing_quantity", "278919");
		map1.put("estimated_landing_quantity", "277727");
		Map<String,String> map2=new HashMap<String,String>();
		map2.put("product_name", "纯牛奶");
		map2.put("order_quantity", "1500000");
		map2.put("shipment", "1085805");
		map2.put("landing_quantity", "975556");
		map2.put("estimated_landing_quantity", "277727");
		data.add(map1);
		data.add(map2);
		
		List<Map<String,String>> data1=new ArrayList<>();
		Map<String,String> map3=new HashMap<String,String>();
		map3.put("product_name", "蓝莓优酪乳");
		map3.put("scan_pv", "2646");
		map3.put("scan_uv", "951");
		map3.put("scan_codes", "1689");
		Map<String,String> map4=new HashMap<String,String>();
		map4.put("product_name", "纯牛奶");
		map4.put("scan_pv", "4609");
		map4.put("scan_uv", "1690");
		map4.put("scan_codes", "3042");
		data1.add(map3);
		data1.add(map4);
		
		List<Map<String,String>> data2=new ArrayList<>();
		Map<String,String> map5=new HashMap<String,String>();
		map5.put("product_name", "蓝莓优酪乳");
		map5.put("conversion_rate", "0.6082%");
		map5.put("uv_cost", "￥5.56");
		Map<String,String> map6=new HashMap<String,String>();
		map6.put("product_name", "纯牛奶");
		map6.put("conversion_rate", "0.3360%");
		map6.put("uv_cost", "￥9.63");
		data2.add(map5);
		data2.add(map6);
		
		Map<String,Object> map=new HashMap<>();
		map.put("history", "F:/tmp/dataworks/单uv成本折线图.png");
		map.put("bar_image", "F:/tmp/dataworks/单uv成本.png");
		map.put("company_name", "山东伊怡");
		map.put("total_cooperation", "1000");
		map.put("proportion", "1/3");
		map.put("price", "0.05");
		map.put("product_name_list", "蓝莓优酪乳、纯牛奶、黑牛奶、鲜态度纯牛奶、每日坚果、鲜态度-中老年高钙低脂牛奶、小熊贝贝乳酸菌饮品");
		map.put("online_product_name", "蓝莓优酪乳、小熊贝贝乳酸菌饮品");
		map.put("product_name_count","8");
		map.put("online_product_name_count","8");
		map.put("max_month", "201811");
		map.put("order", data);
		map.put("scan", data1);
		map.put("rate", data2);
		
		pptHandler("F:/tmp/ppt_template.pptx",map,"F:/tmp/ppt_template_out.pptx");
	}
	
	/*
	 * ppt处理入口
	 * 传入数据规则：对普通的字符串参数替换，占位符参数使用text_开头，如：${text_product_name}
	 * 对图片使用image_开头，如：${image_history_rend}
	 * 对表格中的值参数，以对象名称开头，如${order|product_name},则数据取自map中的order对象中的数据
	 * 对ppt模板规则：每个幻灯片中文字单独占一个shape，图片占位符单独占一个shape，表格单独占一个shape
	 * 
	 * 处理逻辑：读取ppt文件，获取幻灯片数组( xslfSlide.getShapes()),对每个幻灯片中的每个层（shape）再循环处理
	 */
	public static void pptHandler(String pptPath,Map<String,Object> map,String outPpt) throws FileNotFoundException, IOException {
		XMLSlideShow ppt=new XMLSlideShow(new FileInputStream(pptPath));
		List<XSLFSlide> slides = ppt.getSlides();
		for(XSLFSlide xslfSlide:slides) {
			List<XSLFShape> shapes = xslfSlide.getShapes();
			System.out.println("shape大小："+shapes.size());
			for(XSLFShape shape:shapes) {
				if(shape instanceof XSLFTextShape) {
					String text = ((XSLFTextShape) shape).getText();
					//提取字符串中的参数
					List<String> params = getSubUtil(text,"\\$\\{(.+?)\\}");
					//如果参数是以image_开头的，则为图片的占位符
					if(params.size()>0 && params.get(0).startsWith("image_"))
					{
						for(String param:trimPrefix(params)) {
							String image=(String) map.get(param);
							pictureHandler(xslfSlide,shape,image);
						}
						
					}else if(params.size()>0 && params.get(0).startsWith("text_")) {
						textParagraphHandler(shape,trimPrefix(params),map);
					}
					
				}else if (shape instanceof TableShape) {
					tableHandler(shape,map);
				}
			}
		}
		
		//保存文件
		FileOutputStream fileOutputStream = new FileOutputStream(outPpt);
		ppt.write(fileOutputStream);
		fileOutputStream.close();
	}
	
	//将文本占位符参数的前缀去除
	public static List<String> trimPrefix(List<String> params){
		if(params.size()==0)
			return null;
		if(params.get(0).startsWith("text_")) {
			for(int i=0;i<params.size();i++) {
				params.set(i, params.get(i).substring(5));
			}
		}
		
		if(params.get(0).startsWith("image_")) {
			for(int i=0;i<params.size();i++) {
				params.set(i, params.get(i).substring(6));
			}
		}
		
		return params;
	}
	
	/*
	 * 文本段落处理，将文本中的参数替换为对应的值
	 */
	public static void textParagraphHandler(XSLFShape shape,List<String> params,Map<String,Object> datamap) {
		String text = ((XSLFTextShape) shape).getText();
		for(String key:params) {
			String value=(String) datamap.get(key);
			text=text.replaceAll("\\$\\{text_" + key + "\\}", value);
		}
		((XSLFTextShape) shape).setText(text);
		
	}
	
	/*处理表格：先查询出表格中的参数存储，然后再删除表格最后一行参数行，再往表格中添加数据
	 * shape:表格对象
	 * data：数据对象
	 */
	public static void tableHandler(XSLFShape shape,Map<String,Object> datamap) {
		XSLFTable table=(XSLFTable)shape;
		List<XSLFTableRow> rows = table.getRows();
		List<String> paramList=new ArrayList<String>();//存储表格中的占位符参数
		//查询出表格中的占位符参数
		XSLFTableRow xslfTableRow = rows.get(rows.size()-1);
		double rowHeitht = xslfTableRow.getHeight();
		List<XSLFTableCell> cells = xslfTableRow.getCells();
		String cellparam=cells.get(0).getText();
		String substring = cellparam.substring(2, cellparam.length()-1);
		String key=substring.split("\\|")[0].trim();//查询对应的表格数据的数据来源是哪个key
		
		for(XSLFTableCell cell:cells) {
			paramList.add(cell.getText().substring(2, cell.getText().length()-1).split("\\|")[1].trim());
		}
		//删除最后一行
		table.getCTTable().getTrList().remove(rows.size()-1);
		
		//往表格中按参数新添加数据
		@SuppressWarnings("unchecked")
		List<Map<String,String>> data =(List<Map<String, String>>) datamap.get(key);
		for(Map<String,String> map:data) {
			//增加一行
			XSLFTableRow addRow = table.addRow();
			addRow.setHeight(rowHeitht);
			//每行增加每个cell
			for(int i=0;i<paramList.size();i++) {
				XSLFTableCell addCell = addRow.addCell();
				XSLFTextParagraph addNewTextParagraph = addCell.addNewTextParagraph();
				addNewTextParagraph.setTextAlign(TextAlign.CENTER);
				XSLFTextRun addNewTextRun = addNewTextParagraph.addNewTextRun();
				String key1=paramList.get(i);
				addNewTextRun.setText(map.get(key1));
			}
		}
	}
	
	/*增加图片：对占位符所在的位置插入相应的图片，先删除占位符的shape，然后新增图片
	 * 
	 */
	public static void pictureHandler(XSLFSlide slide,XSLFShape shape,String imagePath) throws FileNotFoundException, IOException {
		XMLSlideShow ppt = slide.getSlideShow();
		//获取占位符的位置信息
		Rectangle2D anchor = shape.getAnchor();
		//删除占位符所在的位置
		slide.removeShape(shape);
		//新增图片
		byte[] byteArray = IOUtils.toByteArray(new FileInputStream(imagePath));
		XSLFPictureData addPicture = ppt.addPicture(byteArray, getPictureType(imagePath));
		XSLFPictureShape createPicture = slide.createPicture(addPicture);
		createPicture.setAnchor(anchor);
	}
	
	public static PictureType getPictureType(String image) {
		PictureType type=null;
		if(image.endsWith("png")) {
			type=XSLFPictureData.PictureType.PNG;
		}else if(image.endsWith("jpeg")) {
			type=XSLFPictureData.PictureType.JPEG;
		}
		return type;
	}
	/*
	 * 字符串匹配工具
	 * 返回匹配相应字符之间的字符串
	 */
	public static List<String> getSubUtil(String soap, String rgex){
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

	
}
