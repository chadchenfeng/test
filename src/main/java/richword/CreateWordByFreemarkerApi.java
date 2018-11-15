package richword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/*
 * 
 */
public class CreateWordByFreemarkerApi {

	public static void main(String[] args) throws IOException {
		String currentday = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("company_name", "山东伊怡");
		map.put("total", "1000");
		map.put("price", "0.05");
		map.put("product_names", "蓝莓优酪乳、纯牛奶、黑牛奶、鲜态度纯牛奶、每日坚果、鲜态度-中老年高钙低脂牛奶、小熊贝贝乳酸菌饮品");
		map.put("num","8");
		map.put("current_date", currentday);
		
		List<XDObject> list=new ArrayList<XDObject>();
		XDObject data1=new XDObject();
		data1.setProduct("蓝莓优酪乳");
		data1.setNumber("600000");
		data1.setCount("292786");
		data1.setCccount("278919");
		data1.setGjcount("277727");
		list.add(data1);
		XDObject data2=new XDObject();
		data2.setProduct("纯牛奶");
		data2.setNumber("1500000");
		data2.setCount("1085805");
		data2.setCccount("975556");
		data2.setGjcount("905274");
		list.add(data2);
		/*List<Map<String,String>> list=new ArrayList<>();
		Map<String,String> map1=new HashMap<>();
		map1.put("product", "蓝莓优酪乳");
		map1.put("number", "600000");
		map1.put("count", "292786");
		map1.put("cccount", "278919");
		map1.put("gjcount", "277727");
		Map<String,String> map2=new HashMap<>();
		map2.put("product", "纯牛奶");
		map2.put("number", "1500000");
		map2.put("count", "1085805");
		map2.put("cccount", "975556");
		map2.put("gjcount", "277727");
		list.add(map1);
		list.add(map2);*/
		map.put("datalist", list);
		InputStream in=new FileInputStream("F:/tmp/dataworks/单uv成本柱状图.png");
		String imageString = getImageString(in);
		map.put("historical_trend_image", imageString);
		String filename="F:/tmp/dataworks/山东1.docx";
		String templatepath="/richword";
		String templatename="fp2_template.ftl";
		new CreateWordByFreemarkerApi().createDocumentBytemplate(map,templatepath,templatename,filename);
	}
	
	public void createDocumentBytemplate(Map<String,Object> map,String templatePath,String templateName,String documentPath) {
		Configuration conf=new Configuration(Configuration.VERSION_2_3_28);
		conf.setEncoding(Locale.getDefault(), "utf-8");
		conf.setClassForTemplateLoading(this.getClass(), templatePath);
		try {
			Template template = conf.getTemplate(templateName);
			File file=new File(documentPath);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			template.process(map, bufferedWriter);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
	
	//将图片转换成BASE64字符串
	public static String getImageString(InputStream in) throws IOException {
		byte[] data = null;
		try {
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null)
				in.close();
		}
		return data != null ? Base64.getEncoder().encodeToString(data): "";
	}

}


